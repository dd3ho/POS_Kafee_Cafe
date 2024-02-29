package ku.cs.controllers.old;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;

import ku.cs.controllers.FaceRecognitionController;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;


public class SignupController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ImageView imageView;

    private VideoCapture capture;

    private Mat frame;

    public void initialize() {
        initCamera();
    }

    private void initCamera() {
        capture = new VideoCapture(0);
        frame = new Mat();
        new Thread(this::captureFrame).start();
    }

    private void captureFrame() {
        while (true) {
            capture.read(frame);
            updateImageView(frame);
        }
    }

    private void updateImageView(Mat frame) {
        imageView.setImage(mat2Image(frame));
    }

    private javafx.scene.image.Image mat2Image(Mat frame) {
        return new javafx.scene.image.Image(new ByteArrayInputStream(convertImageToBytes(frame)));
    }

    private byte[] convertImageToBytes(Mat image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(matToBufferedImage(image), "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        matrix.get(0, 0, data);
        BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_3BYTE_BGR);
        image.getRaster().setDataElements(0, 0, cols, rows, data);
        return image;
    }

    @FXML
    private void takePhoto() {
        updateImageView(frame);
        byte[] faceImageBytes = convertImageToBytes(frame);
        saveMemberImage(faceImageBytes);
    }

    @FXML
    private void signup() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();

        try {
            String memberId = addMember(firstName, lastName, phoneNumber);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("facerecognition.fxml"));
            Parent root = loader.load();
            FaceRecognitionController faceRecognitionController = loader.getController();
            faceRecognitionController.setMemberId(memberId);

            Stage stage = (Stage) vbox.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private String addMember(String firstName, String lastName, String phoneNumber) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password")) {
            String sql = "INSERT INTO members (first_name, last_name, phone_number, face_image) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, phoneNumber);
                statement.setBytes(4, convertImageToBytes(frame));

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 1) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getString(1);
                    } else {
                        throw new SQLException("Creating member failed, no ID obtained.");
                    }
                } else {
                    throw new SQLException("Creating member failed, no rows affected.");
                }
            }
        }
    }

    private void saveMemberImage(byte[] imageBytes) {
        // Implement your logic to save the imageBytes (face image) to the desired location or database.
        // This method depends on how you want to handle and store the captured images.
    }
}

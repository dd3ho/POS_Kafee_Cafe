package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

import ku.cs.models.Member;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.videoio.VideoCapture;

public class FaceRecognitionController {

    @FXML
    private VBox vbox;

    @FXML
    private ImageView imageView;

    private VideoCapture capture;

    private String memberId;

    public void initialize() {
        initCamera();
    }

    private void initCamera() {
        capture = new VideoCapture(0);
        new Thread(this::captureFrame).start();
    }

    private void captureFrame() {
        Mat frame = new Mat();
        while (true) {
            capture.read(frame);
            updateImageView(frame);
        }
    }

    private void updateImageView(Mat frame) {
        imageView.setImage(mat2Image(frame));
    }

    private javafx.scene.image.Image mat2Image(Mat frame) {
        BufferedImage image = matToBufferedImage(frame);
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

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @FXML
    private void recognizeFace() {
        // ดูตอบก่อน
    }

    private void recognizeFace(Mat frame, Rect face) throws IOException, SQLException {
        Mat croppedImage = new Mat(frame, face);

        byte[] faceImageBytes = convertImageToBytes(croppedImage);
        Member member = findMember(faceImageBytes);

//        if (member != null) {
//            resultLabel.setText("Member ID: " + member.getMemberId() +
//                    "\nName: " + member.getFirstName() + " " + member.getLastName() +
//                    "\nPhone Number: " + member.getPhoneNumber());
//        } else {
//            resultLabel.setText("Unknown Member");
//        }

        updateImageView(croppedImage);
    }

    private Member findMember(byte[] faceImageBytes) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "")) {
            String sql = "SELECT * FROM members WHERE face_image = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setBytes(1, faceImageBytes);

                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        Member member = new Member();
//                        member.setMemberId(resultSet.getString("member_id"));
//                        member.setFirstName(resultSet.getString("first_name"));
//                        member.setLastName(resultSet.getString("last_name"));
//                        member.setPhoneNumber(resultSet.getString("phone_number"));
//                        member.setFaceImage(resultSet.getBytes("face_image"));
//                        return member;
//                    } else {
                        return null;
//                    }
                }
            }
        }
    }
}


package ku.cs.models.old;

import java.util.ArrayList;

public class DocumentTOBList {

    private ArrayList<DocumentTOB> documents;
    public DocumentTOBList() { documents = new ArrayList<>();}

    //เพิ่ม document
    public void addDocumentTrans(DocumentTOB document) {
        documents.add(document);
    }

    public ArrayList<DocumentTOB> getDocuments() {
        return documents;
    }

    //นับ element ที่อยู่ใน list
    public int countDocTOBElement(){
        return documents.size();
    }

    public String toCsv() {
        String result = "";
        for (DocumentTOB document : this.documents){
            result += document.toCsv() + "\n";
        }
        return result;
    }


    public DocumentTOB getDtbRecord(int i) {
        DocumentTOB doc = documents.get(i);
        return doc;
    }

//    public DocumentTOB getDtbRecordById(String id) {
//        for (DocumentTOB doc : documents){
//            if (doc.checkId(id)){
//                return doc;
//            }
//        }
//        return null;
//    }


}

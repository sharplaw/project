//package cc.mrbird.common.pdfUtils;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//
//
//
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//
//public class PdfUtils {
//
//    private static final String DEST = "d:/HelloWorld.pdf";
//    private static final String FONT = "simhei.ttf";
//
//    public static void main(String[] args) throws FileNotFoundException, DocumentException {
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
//        document.open();
//        Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        document.add(new Paragraph("hello world,我是鲁家宁", f1));
//        Image jpg = null;
//        try {
//            jpg = Image.getInstance("d:/123.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        jpg.setAbsolutePosition(
//                (PageSize.POSTCARD.getWidth() - jpg.getScaledWidth()) / 2,
//                (PageSize.POSTCARD.getHeight() - jpg.getScaledHeight()) / 2);
//        jpg.setAlignment(Image.ALIGN_CENTER);
//
//        document.add(jpg);
//        document.close();
//        writer.close();
//    }
//
//}

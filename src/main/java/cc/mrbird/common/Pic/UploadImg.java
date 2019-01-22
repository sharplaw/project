package cc.mrbird.common.Pic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class UploadImg {
    String fromFileStr;
    String saveToFileStr;
    String sysimgfile;
    int width;
    int height;
    String suffix;
    /**
     * @param fromFileStr
     *            原始图片完整路径
     * @param saveToFileStr
     *            缩略图片保存路径
     * @param sysimgfilenNow
     *            处理后的图片文件名前缀
     *
     */
    public UploadImg(String fromFileStr, String saveToFileStr, String sysimgfile,String suffix,int width,int height) {
        this.fromFileStr = fromFileStr;
        this.saveToFileStr = saveToFileStr;
        this.sysimgfile = sysimgfile;
        this.width=width;
        this.height=height;
        this.suffix=suffix;
    }
    public boolean createThumbnail() throws Exception {
        // fileExtNmae是图片的格式 gif JPG 或png
        // String fileExtNmae="";
        double Ratio = 0.0;
        File F = new File(fromFileStr);
        if (!F.isFile())
            throw new Exception(F
                    + " is not image file error in CreateThumbnail!");
        File ThF = new File(saveToFileStr, sysimgfile +"."+suffix);
        BufferedImage Bi = ImageIO.read(F);
        Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);
        if ((Bi.getHeight() > width) || (Bi.getWidth() > height)) {
            if (Bi.getHeight() > Bi.getWidth())
                Ratio = (double)width / Bi.getHeight();
            else
                Ratio = (double)height / Bi.getWidth();
        }
        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(Ratio, Ratio), null);
        Itemp = op.filter(Bi, null);
        try {
            ImageIO.write((BufferedImage) Itemp, suffix, ThF);
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: "
                    + ex.getMessage());
        }
        return (true);
    }
    public static void main(String[] args) {
        UploadImg UI;
        boolean ss = false;
        try {
            UI = new UploadImg("d://test//zhiwen.png", "d://test", "ps_low1","png",150,200);
            ss = UI.createThumbnail();
            if (ss) {
                System.out.println("Success");
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}

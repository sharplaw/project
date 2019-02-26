package cc.mrbird.common.base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
public class Test{
    public  String main(String img,String imge) throws Exception{

        //读取文件
        Image src = Toolkit.getDefaultToolkit().getImage(img);
        Image srce = Toolkit.getDefaultToolkit().getImage(imge);
        BufferedImage bi_0 =this.toBufferedImage(src);
        BufferedImage bi_1 =this.toBufferedImage(srce);
        //假设图片0 和图片1 宽度相同，上下合成
        //new 一个新的图像

        int w = bi_0.getWidth();
        int h_0 = bi_0.getHeight();
        int h_1 = bi_1.getHeight();
        int h = h_0 + h_1;
        BufferedImage bi=new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);

        //像素一个一个复制过来
        for(int y=0; y<h_0; y++){
            for(int x=0;x<w;x++){
                bi.setRGB(x,y,bi_0.getRGB(x,y));
            }
        }
        for(int y=0;y<h_1;y++){
            for(int x=0;x<w;x++){
                bi.setRGB(x,h_0+y,bi_1.getRGB(x,y));
            }
        }

        //输出文件
        try{
            ImageIO.write(bi,"PNG",new File(img));
            return img;
        }catch(IOException ex){}
        return null;
    }
    public BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

}
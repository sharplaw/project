package cc.mrbird.common.Pic;


import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;


public class PicBuffer {
//改变图片颜色
    public static String  changeRGB (String  path) {
        String color1 = "FF4500";
        //String base = "";
        File file = new File(path);
        BufferedImage bi = null;
        BufferedImage bi1 = null;
        try {
            bi = ImageIO.read(file);
            int width = bi.getWidth();
            int height = bi.getHeight();
            int minx = bi.getMinX();
            int miny = bi.getMinY();
            ByteArrayOutputStream outputStream = null;
            int pixel2 = Integer.valueOf(color1, 16);//转换成10进制颜色
            bi1 = new BufferedImage(width, height, 6);//新建一个BufferedImage 防止某些PNG图片无法修改
            for (int i = minx; i < width; i++) {  //循环每个像素 然后修改
                for (int j = miny; j < height; j++) {
                    int pixel = bi.getRGB(i, j);
                    if (pixel <-592138 ) {
                        bi1.setRGB(i, j, pixel2 - 16777216);

                    } else {
                        bi1.setRGB(i, j, pixel);
                    }
                }
            }

            outputStream = new ByteArrayOutputStream();

            ImageIO.write(bi1, "png", outputStream);
            ImageIO.write(bi1, "png", new File(path));
            // base = new BASE64Encoder().encode(outputStream.toByteArray());
            outputStream.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        bi.flush();
        bi1.flush();
        return path;

    }


    public static void main(String[] args){
        PicBuffer.changeRGB("d://4.png");
    }
}

package cc.mrbird.common.base64;


import java.io.*;
import java.util.Base64;
 //本地图片转换成base64字符串
public class Base {
    public static String imageToBase64Str(String imgFile) {
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(data);
    }
 // base64字符串转换成图片
    public static boolean CreateImage(String imgStr,String path,String filePath) {
        if (imgStr == null) // 图像数据为空
            return false;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            // Base64解码
            byte[] b = decoder.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = path;// 新生成的图片
            File outDir =new File(filePath);
            outDir.mkdirs();
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
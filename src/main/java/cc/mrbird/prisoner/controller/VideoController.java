package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.vedio.Contants;
import cc.mrbird.common.util.vedio.ConverVideoTest;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.domain.JzVideo;
import cc.mrbird.prisoner.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoController  extends BaseController {


    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private VideoService videoService;

    @Log("跳转视频页")
    @RequestMapping("video/index")
    public String index() {
        return "web/main/video";
    }

    @Log("跳转视频设置页")
    @RequestMapping("video/set")
    public String indexSetting() {
        return "web/main/videoSetting";
    }

    @Log("查询视频信息")
    @RequestMapping("video/select")
    @ResponseBody
    public Map<String, Object> selectTask(QueryRequest request, JzVideo jzVideo){

        return super.selectByPageNumSize(request, () -> this.videoService.findVideo(jzVideo,request));
    }

    @Log("查询单个视频信息")
    @RequestMapping("video/selectSingle")
    @ResponseBody
    public ResponseBo selectVideoSingle(QueryRequest request, JzVideo jzVideo){
        List<JzVideo> result= videoService.findSingleVideo(jzVideo);
        return ResponseBo.ok(result);

    }

    @Log("更新视频信息")
    @RequestMapping("video/update")
    @ResponseBody
    public ResponseBo updateVideo(JzVideo jzVideo){
        try {
            videoService.updateNotNull(jzVideo);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseBo.error("更新失败！");
        }


    }


    @Log("添加视频信息")
    @RequestMapping("video/add")
    @ResponseBody
    public ResponseBo addVideo(@RequestParam("files") MultipartFile files, JzVideo jzVideo, QueryRequest request){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jzVideo.setCreateTime(sdf.format(new Date()));
            jzVideo.setFlag("0");
            jzVideo.getCreateTime();
            videoService.save(jzVideo);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("添加失败", e);
            return ResponseBo.error("添加失败！");
        }


    }



    @Log("上传视频信息")
    @RequestMapping("video/upload")
    @ResponseBody
    public ResponseBo uploadVideo(@RequestParam("file") MultipartFile file, JzVideo jzVideo, QueryRequest request){
        if (file.getSize() != 0) {
            //上传的多格式的视频文件-作为临时路径保存，转码以后删除-路径不能写//
            String path = "E:/Projectpicture/websiteimages/temp/";

            File TempFile = new File(path);
            if (TempFile.exists()) {
                if (TempFile.isDirectory()) {
                    System.out.println("该文件夹存在。");
                }else {
                    System.out.println("同名的文件存在，不能创建文件夹。");
                }
            }else {
                System.out.println("文件夹不存在，创建该文件夹。");
                TempFile.mkdir();
            }

            // 获取上传时候的文件名
            String filename = file.getOriginalFilename();

            // 获取文件后缀名
            String filename_extension = filename.substring(filename
                    .lastIndexOf(".") + 1);
            System.out.println("视频的后缀名:"+filename_extension);

            //时间戳做新的文件名，避免中文乱码-重新生成filename
            long filename1 = new Date().getTime();
            filename = Long.toString(filename1)+"."+filename_extension;

            //去掉后缀的文件名
            String filename2 = filename.substring(0, filename.lastIndexOf("."));
            System.out.println("视频名为:"+filename2);

            //源视频地址+重命名后的视频名+视频后缀
            String yuanPATH =(path+filename);

            System.out.println("视频的完整文件名1:"+filename);
            System.out.println("源视频路径为:"+yuanPATH);

            //上传到本地磁盘/服务器
            try {
                System.out.println("写入本地磁盘/服务器");
                InputStream is = file.getInputStream();
                OutputStream os = new FileOutputStream(new File(path, filename));
                int len = 0;
                byte[] buffer = new byte[2048];

                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.close();
                os.flush();
                is.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("========上传完成，开始调用转码工具类=======");
            //调用转码机制flv mp4 f4v m3u8 webm ogg放行直接播放，
            //asx，asf，mpg，wmv，3gp，mov，avi，wmv9，rm，rmvb等进行其他转码为mp4

            if (filename_extension.equals("avi") || filename_extension.equals("rm")
                    || filename_extension.equals("rmvb") || filename_extension.equals("wmv")
                    || filename_extension.equals("3gp")  || filename_extension.equals("mov")
                    ||filename_extension.equals("flv")   || filename_extension.equals("ogg")

            ) {

                ConverVideoTest c = new ConverVideoTest();
                c.run(yuanPATH);   //调用转码
                System.out.println("=================转码过程彻底结束=====================");
            }

            //获取转码后的mp4文件名
            String Mp4path = "E://Projectpicture/websiteimages/finshvideo/";
            String picname=filename2+".jpg";
            filename2 = filename2+".mp4";

            String NewVideopath =Mp4path +filename2;
            System.out.println("新视频的url:"+NewVideopath);

            //删除临时文件
            File file2 = new File(path);
            if (!file2.exists()) {
                System.out.println("没有该文件");
            }
            if (!file2.isDirectory()) {
                System.out.println("没有该文件夹");
            }
            String[] tempList = file2.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile() || temp.isDirectory()) {
                    temp.delete();     //删除文件夹里面的文件
                }
            }
            System.out.println("所有的临时视频文件删除成功");
            Map map=new HashMap();

            map.put("pic", Contants.imageRealPath+picname);
            map.put("path",NewVideopath);

            return ResponseBo.ok(map);

    }

        return ResponseBo.error();



    }
}

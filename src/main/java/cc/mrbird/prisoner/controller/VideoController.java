package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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



    @Log("更新任务信息")
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
}

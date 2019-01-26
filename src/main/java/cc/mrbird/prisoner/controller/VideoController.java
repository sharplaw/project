package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.domain.JzVideo;
import cc.mrbird.prisoner.service.TaskService;
import cc.mrbird.prisoner.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class VideoController  extends BaseController {

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


}

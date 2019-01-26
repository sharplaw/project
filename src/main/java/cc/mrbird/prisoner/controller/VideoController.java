package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VideoController  extends BaseController {


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

}

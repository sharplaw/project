package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PadController extends BaseController {


    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("跳转报道页")
    @RequestMapping("pad/index")
    public String index() {
        return "ipad/index";
    }

    @Log("跳转报道页")
    @RequestMapping("pad/login")
    public String login() {
        return "ipad/login";
    }
}

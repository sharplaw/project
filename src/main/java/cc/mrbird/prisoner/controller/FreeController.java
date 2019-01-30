package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class FreeController extends BaseController {

    @Log("跳转报道页")
    @RequestMapping("free/index")
    public String index() {
        return "web/main/free";
    }
}

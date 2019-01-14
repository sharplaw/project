package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PrisonerController {


    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Log("跳转报道页")
    @RequestMapping("prisoner/report")
    public String index() {
        return "web/main/report";
    }
}

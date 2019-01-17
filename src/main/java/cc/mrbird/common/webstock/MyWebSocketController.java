package cc.mrbird.common.webstock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/demo")
public class MyWebSocketController {

    /**
     * @param userId 模拟登录，登录的用户名称
     */
    @RequestMapping(value = "/webSocketTest/{userId}")
    public String test(@PathVariable("userId") String userId, Model model) throws Exception {
        //把登录名称传给jsp页面
        model.addAttribute("userId", userId);
        return "web/demo/test";
    }
}

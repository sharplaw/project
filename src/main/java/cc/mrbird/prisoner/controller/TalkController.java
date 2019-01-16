package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.prisoner.domain.JzTalk;
import cc.mrbird.prisoner.service.TalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TalkController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TalkService talkService;

    @Log("查询社区服刑人员谈话记录表")
    @RequestMapping("talk/select")
    @ResponseBody
    public ResponseBo selectPrisonerTalk(JzTalk jzTalk){
        try {
            List<JzTalk> result= talkService.findPrisonerTalk(jzTalk);
            return ResponseBo.ok(result);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseBo.error("查询失败！");
        }


    }

    @Log("更新社区服刑人员谈话记录表")
    @RequestMapping("talk/update")
    @ResponseBody
    public ResponseBo updatePrisonerTalk(JzTalk jzTalk){
        try {
             talkService.updateNotNull(jzTalk);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseBo.error("查询失败！");
        }


    }
}

package cc.mrbird.prisoner.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.prisoner.domain.JzPrisoner;
import cc.mrbird.prisoner.service.PrisonerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PrisonerController {

    @Autowired
    private PrisonerService prisonerService;
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Log("跳转报道页")
    @RequestMapping("prisoner/report")
    public String index() {
        return "web/main/report";
    }

    @Log("跳转社区服刑人员管理页")
    @RequestMapping("prisoner/list")
    public String indexList() {
        return "web/main/peopleSetting";
    }

    @Log("添加社区服刑人员信息")
    @RequestMapping("prisoner/add")
    @ResponseBody
    public ResponseBo addPrisoner(JzPrisoner jzPrisoner){
        try {
        prisonerService.addPrisoner(jzPrisoner);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("添加失败", e);
            return ResponseBo.error("添加失败！");
        }


    }


    @Log("查询社区服刑人员信息")
    @RequestMapping("prisoner/select")
    @ResponseBody
    public ResponseBo selectPrisoner(JzPrisoner jzPrisoner){
        try {
         List<JzPrisoner> result= prisonerService.selectPrisoner(jzPrisoner);
            return ResponseBo.ok(result);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseBo.error("查询失败！");
        }


    }


    @Log("更新社区服刑人员信息")
    @RequestMapping("prisoner/update")
    @ResponseBody
    public ResponseBo updatePrisoner(JzPrisoner jzPrisoner){
        try {
            prisonerService.updateNotNull(jzPrisoner);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseBo.error("更新失败！");
        }


    }

}

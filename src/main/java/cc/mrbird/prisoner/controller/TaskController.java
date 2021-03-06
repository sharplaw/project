package cc.mrbird.prisoner.controller;


import cc.mrbird.common.Pic.ImageMarkLogoByIcon;
import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.prisoner.domain.JzPrisoner;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.service.PrisonerService;
import cc.mrbird.prisoner.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController  extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskService taskService;

    @Autowired
    private PrisonerService prisonerService;

    @Log("查询任务信息")
    @RequestMapping("task/select")
    @ResponseBody
    public Map<String, Object> selectTask(QueryRequest request, JzTask jzTask){

        return super.selectByPageNumSize(request, () -> this.taskService.findTask(jzTask,request));
    }


    @Log("查询任务信息")
    @RequestMapping("task/selectSingle")
    @ResponseBody
    public ResponseBo selectSingleTask(QueryRequest request, JzTask jzTask){
     List<JzTask> result=  taskService.findSingleTask(jzTask);
         return ResponseBo.ok(result);
    }


    @Log("添加社区服刑人员信息")
    @RequestMapping("task/add")
    @ResponseBody
    public ResponseBo addTask(JzTask jzTask,QueryRequest request) {
        try {
            Date date = new Date();
            String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
            int month = date.getMonth();
            int months = month + 1;
            String mon = String.valueOf(months);
            jzTask.setStatue("0");
            jzTask.setYear(strNow[0]);
            jzTask.setMonth(mon);
            List<JzTask> res = taskService.findSingleTask(jzTask);

                if (res.size() > 0) {
                    return ResponseBo.ok();
                } else {
                    taskService.save(jzTask);
                    return ResponseBo.ok();
                }

        } catch (Exception e) {
            log.error("添加失败", e);
            return ResponseBo.error("添加失败！");
        }


    }

    @Log("更新任务信息")
    @RequestMapping("task/update")
    @ResponseBody
    public ResponseBo updateTask(JzTask jzTask){

        System.out.println(jzTask.getFingerUrl());
        try {

            if (StringUtils.isNotBlank(jzTask.getSminute())) {
                Date date = new Date();
                String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
                int month = date.getMonth();
                int months = month + 1;
                String mon = String.valueOf(months);
                jzTask.setStatue("0");
                jzTask.setYear(strNow[0]);
                jzTask.setMonth(mon);
                List<JzTask> res = taskService.findSingleTask(jzTask);
                jzTask.setId(res.get(0).getId());
                if (StringUtils.isNotBlank(res.get(0).getSminute())) {
                 int oldminute=  Integer.valueOf( res.get(0).getSminute());
                 int newminute=Integer.valueOf( jzTask.getSminute());
                    newminute= newminute+oldminute;
                    jzTask.setSminute(String.valueOf(newminute));
                    taskService.updateNotNull(jzTask);
                    return ResponseBo.ok();
                } else {
                    taskService.updateNotNull(jzTask);
                    return ResponseBo.ok();
                }
            }
            taskService.updateNotNull(jzTask);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseBo.error("更新失败！");
        }


    }

    @Log("跳转任务页")
    @RequestMapping("task/policeJob")
    public String index() {
        return "web/main/policeJob";
    }


    @Log("完成任务")
    @RequestMapping("task/finish")
    @ResponseBody
    public ResponseBo finishTask(JzTask jzTask){

        try {
            taskService.finishTask(jzTask);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("完成失败", e);
            return ResponseBo.error("完成失败！");
        }


    }


}

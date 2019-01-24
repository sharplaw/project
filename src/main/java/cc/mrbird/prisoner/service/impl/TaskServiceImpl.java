package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.Pic.ImageMarkLogoByIcon;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.prisoner.dao.taskMapper;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("taskService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TaskServiceImpl extends BaseService<JzTask> implements TaskService {

    @Autowired
    private taskMapper task;
    @Override
    public List<JzTask> findTask(JzTask jzTask, QueryRequest request) {
        List<JzTask> result=task.findTask(jzTask);
        return result;
    }

    @Override
    public String finishTask(JzTask jzTask) {

        List<JzTask> result=task.findTask(jzTask);
        if(result.size()>0){
           String iconPath= result.get(0).getActiivityUrl();
           String targerPath=result.get(0).getFingerUrl();
            ImageMarkLogoByIcon.markImageByIcon(iconPath, iconPath, targerPath);
            jzTask.setStatue("1");
           int rs= this.updateNotNull(jzTask);
           if(rs!=0){
               return "success";
           }else{
               return "error";
           }

        }

        return "error";
    }
}

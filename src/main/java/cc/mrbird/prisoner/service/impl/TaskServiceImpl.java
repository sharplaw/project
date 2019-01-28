package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.Pic.ImageMarkLogoByIcon;
import cc.mrbird.common.base64.Base;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.prisoner.dao.taskMapper;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cc.mrbird.common.base64.Base.imageToBase64Str;

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
           String targerPath= result.get(0).getActiivityUrl();
           String iconPath=result.get(0).getFingerUrl();
            String targersPath= result.get(0).getTalkUrl();
            ImageMarkLogoByIcon.markImageByIcon(iconPath, targerPath, targerPath);
            ImageMarkLogoByIcon.markImageByIcon(iconPath, targersPath, targersPath);
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

    @Override
    public List<JzTask> findSingleTask(JzTask jzTask) {
        List<JzTask> result= task.findTask(jzTask);
        String actUrl = null;
        String talkUrl= null;
        String fingerUrl= null;
        String  freeUrl=null;
        if(result.size()>0){
            if(StringUtils.isNotBlank(result.get(0).getActiivityUrl())){
                actUrl=result.get(0).getActiivityUrl();
                String act=  Base.imageToBase64Str(actUrl);
                result.get(0).setActiivityUrl(act);
            }
             if(StringUtils.isNotBlank(result.get(0).getTalkUrl())){
                 talkUrl=result.get(0).getTalkUrl();
                 String talk=  Base.imageToBase64Str(talkUrl);
                 result.get(0).setTalkUrl(talk);
             }
            if(StringUtils.isNotBlank(result.get(0).getFingerUrl())){
                fingerUrl=result.get(0).getFingerUrl();
                String finger=  Base.imageToBase64Str(fingerUrl);
                result.get(0).setFingerUrl(finger);
            }
            if(StringUtils.isNotBlank(result.get(0).getFreedayUrl())){
                freeUrl=result.get(0).getFreedayUrl();
                String free=Base.imageToBase64Str(freeUrl);
                result.get(0).setFreedayUrl(free);
            }
        }




        return result;
    }
}

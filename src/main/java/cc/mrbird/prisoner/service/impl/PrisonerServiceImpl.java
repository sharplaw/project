package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.prisoner.dao.prisonerMapper;
import cc.mrbird.prisoner.domain.JzPrisoner;
import cc.mrbird.prisoner.domain.JzTalk;
import cc.mrbird.prisoner.service.PrisonerService;

import cc.mrbird.prisoner.service.TalkService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("prisonerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PrisonerServiceImpl extends BaseService<JzPrisoner> implements PrisonerService {

    @Autowired
    private TalkService talkService;
    @Autowired
    private cc.mrbird.prisoner.dao.prisonerMapper prisoner;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<JzPrisoner> selectPrisoner(JzPrisoner jzPrisoner, QueryRequest request) {
        List<JzPrisoner> result=prisoner.findPrisoner(jzPrisoner);
            return result;



    }

    @Override
    public int addPrisoner(JzPrisoner jzPrisoner) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jzPrisoner.setCreateTime(sdf.format(new Date()));
           int result= this.save(jzPrisoner);
           if(result>0){
               JzTalk jzTalk=new JzTalk();
               jzTalk.setPrisonerNo(jzPrisoner.getPrisonerNo());
               jzTalk.setCreateTime(sdf.format(new Date()));
               talkService.save(jzTalk);
               return 0;
           }else{
               return 1;
           }

        }catch (Exception e){
            log.error("获取信息失败", e);
            return 1;
        }



    }
}

package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.prisoner.dao.talkMapper;
import cc.mrbird.prisoner.domain.JzTalk;
import cc.mrbird.prisoner.service.TalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("talkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TalkServiceImpl extends BaseService<JzTalk> implements TalkService {
    @Autowired
    private talkMapper talk;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<JzTalk> findPrisonerTalk(JzTalk jzTalk) {

        try {
            return talk.findPrisonerTalk(jzTalk);

        }catch (Exception e){
            log.error("获取信息失败", e);
            return new ArrayList<>();
        }

    }
}

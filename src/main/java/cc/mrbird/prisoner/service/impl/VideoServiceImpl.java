package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;

import cc.mrbird.prisoner.dao.VideoMapper;
import cc.mrbird.prisoner.dao.taskMapper;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.domain.JzVideo;

import cc.mrbird.prisoner.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("videoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VideoServiceImpl  extends BaseService<JzVideo> implements VideoService {

    @Autowired
    private VideoMapper video;
    @Override
    public List<JzVideo> findVideo(JzVideo jzVideo, QueryRequest request) {
        List<JzVideo> result= video.findVideo(jzVideo);

        return result;
    }
}

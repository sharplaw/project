package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.base64.Base;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;

import cc.mrbird.prisoner.dao.VideoMapper;
import cc.mrbird.prisoner.domain.JzVideo;

import cc.mrbird.prisoner.service.VideoService;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public List<JzVideo> findSingleVideo(JzVideo jzVideo) {
        List<JzVideo> result= video.findVideo(jzVideo);
        String photo=null;
        if(result.size()>0){
            if(StringUtils.isNotBlank(result.get(0).getPhotoUrl())){
                photo=result.get(0).getPhotoUrl();
                String act=  Base.imageToBase64Str(photo);
                result.get(0).setPhotoUrl(act);
            }

        }

        return result;
    }
}

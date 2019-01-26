package cc.mrbird.prisoner.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.domain.JzVideo;

import java.util.List;

public interface VideoService  extends IService<JzVideo> {

    List<JzVideo> findVideo(JzVideo jzVideo, QueryRequest request);
}

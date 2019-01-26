package cc.mrbird.prisoner.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.prisoner.domain.JzVideo;

import java.util.List;

public interface VideoMapper extends MyMapper<JzVideo> {


    List<JzVideo> findVideo(JzVideo jzVideo);
}

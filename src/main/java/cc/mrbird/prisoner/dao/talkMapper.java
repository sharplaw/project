package cc.mrbird.prisoner.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.prisoner.domain.JzTalk;

import java.util.List;

public interface talkMapper extends MyMapper<JzTalk> {

   List<JzTalk > findPrisonerTalk(JzTalk jzTalk);
}

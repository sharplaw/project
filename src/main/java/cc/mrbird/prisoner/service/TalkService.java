package cc.mrbird.prisoner.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.prisoner.domain.JzTalk;

import java.util.List;

public interface TalkService  extends IService<JzTalk> {

   public  List<JzTalk > findPrisonerTalk(JzTalk jzTalk);
}

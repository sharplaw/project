package cc.mrbird.prisoner.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.prisoner.domain.JzPrisoner;
import cc.mrbird.prisoner.domain.JzTalk;

import java.util.List;


public interface prisonerMapper extends MyMapper<JzPrisoner> {


    List<JzPrisoner > findPrisoner(JzPrisoner jzPrisoner);

}

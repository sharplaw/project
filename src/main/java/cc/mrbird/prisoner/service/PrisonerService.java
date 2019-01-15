package cc.mrbird.prisoner.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.prisoner.domain.JzPrisoner;


import java.util.List;
import java.util.Map;

public interface PrisonerService extends IService<JzPrisoner> {

     List<JzPrisoner> selectPrisoner(JzPrisoner jzPrisoner);

     int addPrisoner(JzPrisoner jzPrisoner);
}

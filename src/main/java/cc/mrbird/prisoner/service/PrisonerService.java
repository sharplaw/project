package cc.mrbird.prisoner.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.prisoner.domain.JzPrisoner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrisonerService extends IService<JzPrisoner> {

    public List<JzPrisoner> selectPrisoner(JzPrisoner jzPrisoner);
}

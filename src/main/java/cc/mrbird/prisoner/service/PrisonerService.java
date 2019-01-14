package cc.mrbird.prisoner.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.prisoner.domain.JzPrisoner;
import org.springframework.transaction.annotation.Transactional;

public interface PrisonerService extends IService<JzPrisoner> {
    @Transactional
    void savePrisoner(JzPrisoner jzPrisoner);
}

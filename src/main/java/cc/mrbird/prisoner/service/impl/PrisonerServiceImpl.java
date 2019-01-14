package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.prisoner.domain.JzPrisoner;
import cc.mrbird.prisoner.service.PrisonerService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("prisonerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PrisonerServiceImpl extends BaseService<JzPrisoner> implements PrisonerService {

    @Override
    @Transactional
    public void savePrisoner(JzPrisoner jzPrisoner) {
        this.save(jzPrisoner);
    }

}

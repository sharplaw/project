package cc.mrbird.prisoner.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.prisoner.domain.JzTask;
import cc.mrbird.prisoner.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("taskService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TaskServiceImpl extends BaseService<JzTask> implements TaskService {
}

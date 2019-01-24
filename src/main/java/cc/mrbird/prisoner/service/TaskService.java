package cc.mrbird.prisoner.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.prisoner.domain.JzTask;

import java.util.List;

public interface TaskService extends IService<JzTask> {


    List<JzTask> findTask(JzTask jzTask, QueryRequest request);

    String finishTask(JzTask jzTask);

    List<JzTask> findSingleTask(JzTask jzTask);
}

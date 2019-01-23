package cc.mrbird.prisoner.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.prisoner.domain.JzTask;

import java.util.List;

public interface taskMapper extends MyMapper<JzTask> {

    List<JzTask > findTask(JzTask jzTask);
}

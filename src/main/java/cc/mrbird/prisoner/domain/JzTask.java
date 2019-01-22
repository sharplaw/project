package cc.mrbird.prisoner.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "jz_task")
public class JzTask  implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ExportConfig(value = "主键ID")
    private int  id;

    @Column(name = "prisonerNo")
    @ExportConfig(value = "人员编号")
    private String   prisonerNo;


    @Column(name = "taskType")
    @ExportConfig(value = "任务类型")
    private String taskType;

    @Column(name = "taskType")
    @ExportConfig(value = "活动情况")
    private String  actiivityUrl;

    @Column(name = "talkUrl")
    @ExportConfig(value = "谈话记录")
    private String  talkUrl;

    @Column(name = "freedayUrl")
    @ExportConfig(value = "请假单")
    private String   freedayUrl;

    @Column(name = "fingerUrl")
    @ExportConfig(value = "指纹信息")
    private String fingerUrl;

    @ExportConfig(value = "负责人")
    private String leader;

    @ExportConfig(value = "任务状态")
    private String     statue;

    @ExportConfig(value = "年")
    private String year;
    @ExportConfig(value = "月")
    private String   month;

    @Column(name = "createTime")
    @ExportConfig(value = "创建时间")
    private String createTime;

}

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


    @ExportConfig(value = "任务状态")
    private String     statue;

    @ExportConfig(value = "年")
    private String year;
    @ExportConfig(value = "月")
    private String   month;

    @Column(name = "createTime")
    @ExportConfig(value = "创建时间")
    private String createTime;

    //他表字段
    @Column(insertable=false)
    @ExportConfig(value = "姓名")
    private String name;

    @Column(insertable=false)
    @ExportConfig(value = "负责人")
    private String leader;

    @Column(insertable=false)
    @ExportConfig(value = "负责人姓名")
    private String username;

    @Column(insertable=false)
    @ExportConfig(value = "身份证")
    private String card;

    @Column(insertable=false)
    @ExportConfig(value = "电话")
    private String telephone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrisonerNo() {
        return prisonerNo;
    }

    public void setPrisonerNo(String prisonerNo) {
        this.prisonerNo = prisonerNo;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getActiivityUrl() {
        return actiivityUrl;
    }

    public void setActiivityUrl(String actiivityUrl) {
        this.actiivityUrl = actiivityUrl;
    }

    public String getTalkUrl() {
        return talkUrl;
    }

    public void setTalkUrl(String talkUrl) {
        this.talkUrl = talkUrl;
    }

    public String getFreedayUrl() {
        return freedayUrl;
    }

    public void setFreedayUrl(String freedayUrl) {
        this.freedayUrl = freedayUrl;
    }

    public String getFingerUrl() {
        return fingerUrl;
    }

    public void setFingerUrl(String fingerUrl) {
        this.fingerUrl = fingerUrl;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

package cc.mrbird.prisoner.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "jz_talk")
public class JzTalk implements Serializable {


    @Id
    @GeneratedValue(generator = "JDBC")
    @ExportConfig(value = "主键ID")
    private int id	;

    @Column(name = "prisonerNo")
    @ExportConfig(value = "人员编号")
    private String prisonerNo	;

    @ExportConfig(value = "谈话人")
    private String talker;

    @ExportConfig(value = "记录人")
    private String recorder;

    @ExportConfig(value = "谈话人单位及职务")
    private String talkerUnit;

    @ExportConfig(value = "记录人单位及职务")
    private String recorderUnit;

    @ExportConfig(value = "谈话内容")
    private String conment;

    @Column(name = "talkStartTime")
    @ExportConfig(value = "谈话开始时间")
    private String talkStartTime;

    @Column(name = "talkEndTime")
    @ExportConfig(value = "谈话结束时间")
    private String talkEndTime;

    @Column(name = "createTime")
    @ExportConfig(value = "创建时间")
    private String createTime	;
    //他表字段
    @ExportConfig(value = "姓名")
    private String name;

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

    public String getTalker() {
        return talker;
    }

    public void setTalker(String talker) {
        this.talker = talker;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getTalkerUnit() {
        return talkerUnit;
    }

    public void setTalkerUnit(String talkerUnit) {
        this.talkerUnit = talkerUnit;
    }

    public String getRecorderUnit() {
        return recorderUnit;
    }

    public void setRecorderUnit(String recorderUnit) {
        this.recorderUnit = recorderUnit;
    }

    public String getConment() {
        return conment;
    }

    public void setConment(String conment) {
        this.conment = conment;
    }

    public String getTalkStartTime() {
        return talkStartTime;
    }

    public void setTalkStartTime(String talkStartTime) {
        this.talkStartTime = talkStartTime;
    }

    public String getTalkEndTime() {
        return talkEndTime;
    }

    public void setTalkEndTime(String talkEndTime) {
        this.talkEndTime = talkEndTime;
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
}

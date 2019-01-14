package cc.mrbird.prisoner.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Table(name = "jz_prisoner")
public class JzPrisoner implements Serializable {


    @Id
    @ExportConfig(value = "主键ID")
    private int id	;

    @ExportConfig(value = "人员编号")
    private String prisonerNo	;

    @ExportConfig(value = "矫正类别")
    private String corrective	;

    @ExportConfig(value = "姓名")
    private String name	;

    @ExportConfig(value = "性别")
    private String sex	;

    @ExportConfig(value = "身份证号")
    private String card	;

    @ExportConfig(value = "电话号码")
    private String telephone;

    @ExportConfig(value = "地址")
    private String address;

    @ExportConfig(value = "签字")
    private String  signature;

    @ExportConfig(value = "指纹信息")
    private String  fingerprints;

    @ExportConfig(value = "服刑开始时间")
    private String servingStartTime;

    @ExportConfig(value = "服刑结束时间")
    private String servingEndTime;

    @ExportConfig(value = "状态")
    private String status;

    @ExportConfig(value = "创建时间")
    private String createTime	;

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

    public String getCorrective() {
        return corrective;
    }

    public void setCorrective(String corrective) {
        this.corrective = corrective;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(String fingerprints) {
        this.fingerprints = fingerprints;
    }

    public String getServingStartTime() {
        return servingStartTime;
    }

    public void setServingStartTime(String servingStartTime) {
        this.servingStartTime = servingStartTime;
    }

    public String getServingEndTime() {
        return servingEndTime;
    }

    public void setServingEndTime(String servingEndTime) {
        this.servingEndTime = servingEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

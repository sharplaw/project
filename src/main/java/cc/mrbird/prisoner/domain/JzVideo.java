package cc.mrbird.prisoner.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jz_video")
public class JzVideo {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ExportConfig(value = "主键ID")
    private int id;

    @ExportConfig(value = "标题")
    private String  title;

    @ExportConfig(value = "描述")
    private String describle;

    @Column(name = "videoUrl")
    @ExportConfig(value = "路径地址")
    private String  videoUrl;

    @ExportConfig(value = "视频类型")
    private String  type;

    @Column(name = "createTime")
    @ExportConfig(value = "创建时间")
    private String  createTime;

    @ExportConfig(value = "状态")
    private String  flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

package me.victoriest.photio.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Evaluation implements Serializable {
    private Long id;

    private Long userId;

    private Long evatuateUserId;

    private Integer score;

    private Date createDate;

    private Long creatorId;

    private Date updateDate;

    private Long updaterId;

    private String msg;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEvatuateUserId() {
        return evatuateUserId;
    }

    public void setEvatuateUserId(Long evatuateUserId) {
        this.evatuateUserId = evatuateUserId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", evatuateUserId=").append(evatuateUserId);
        sb.append(", score=").append(score);
        sb.append(", createDate=").append(createDate);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", msg=").append(msg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
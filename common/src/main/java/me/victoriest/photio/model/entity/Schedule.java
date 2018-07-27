package me.victoriest.photio.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Schedule implements Serializable {
    private Long id;

    private Long userId;

    private Date freeDate;

    private Integer isScheduled;

    private Date createDate;

    private Long creatorId;

    private Date updateDate;

    private Long updaterId;

    private String tags;

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

    public Date getFreeDate() {
        return freeDate;
    }

    public void setFreeDate(Date freeDate) {
        this.freeDate = freeDate;
    }

    public Integer getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(Integer isScheduled) {
        this.isScheduled = isScheduled;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", freeDate=").append(freeDate);
        sb.append(", isScheduled=").append(isScheduled);
        sb.append(", createDate=").append(createDate);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", tags=").append(tags);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
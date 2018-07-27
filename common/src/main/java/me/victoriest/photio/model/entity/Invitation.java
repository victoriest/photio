package me.victoriest.photio.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Invitation implements Serializable {
    private Long id;

    private Long invitorId;

    private Long targetUserId;

    private Integer state;

    private Date createDate;

    private Long creatorId;

    private Date updateDate;

    private Long updaterId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvitorId() {
        return invitorId;
    }

    public void setInvitorId(Long invitorId) {
        this.invitorId = invitorId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", invitorId=").append(invitorId);
        sb.append(", targetUserId=").append(targetUserId);
        sb.append(", state=").append(state);
        sb.append(", createDate=").append(createDate);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
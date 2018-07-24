package me.victoriest.photio.model.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;

    private String account;

    private String phone;

    private String pwd;

    private String userName;

    private Integer type;

    private Integer state;

    private Long score;

    private Date createDate;

    private Date creatorId;

    private Date updateDate;

    private Date updaterId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Date creatorId) {
        this.creatorId = creatorId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Date updaterId) {
        this.updaterId = updaterId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", phone=").append(phone);
        sb.append(", pwd=").append(pwd);
        sb.append(", userName=").append(userName);
        sb.append(", type=").append(type);
        sb.append(", state=").append(state);
        sb.append(", score=").append(score);
        sb.append(", createDate=").append(createDate);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
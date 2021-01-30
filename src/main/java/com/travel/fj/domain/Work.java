package com.travel.fj.domain;

import java.util.Date;

public class Work  extends ValueObject{
    private Integer workId;
    private String workTitle;
    private Date workCreateTime;
    private User workAuthor;
    private NoteType workType;
    private Attraction workAttrac;
    private String workText;
    private Integer workStatus;
    private String workPicList;
    private Integer workLikeCount;

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public Date getWorkCreateTime() {
        return workCreateTime;
    }

    public void setWorkCreateTime(Date workCreateTime) {
        this.workCreateTime = workCreateTime;
    }

    public User getWorkAuthor() {
        return workAuthor;
    }

    public void setWorkAuthor(User workAuthor) {
        this.workAuthor = workAuthor;
    }

    public NoteType getWorkType() {
        return workType;
    }

    public void setWorkType(NoteType workType) {
        this.workType = workType;
    }

    public Attraction getWorkAttrac() {
        return workAttrac;
    }

    public void setWorkAttrac(Attraction workAttrac) {
        this.workAttrac = workAttrac;
    }

    public String getWorkText() {
        return workText;
    }

    public void setWorkText(String workText) {
        this.workText = workText;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkPicList() {
        return workPicList;
    }

    public void setWorkPicList(String workPicList) {
        this.workPicList = workPicList;
    }

    public Integer getWorkLikeCount() {
        return workLikeCount;
    }

    public void setWorkLikeCount(Integer workLikeCount) {
        this.workLikeCount = workLikeCount;
    }
}

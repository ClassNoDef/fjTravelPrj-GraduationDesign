package com.travel.fj.domain;

public class LikeObject extends ValueObject {

    private Integer likeId;
    private Integer likeWorkId;
    private Integer likeUserId;

    public LikeObject() {
    }

    public LikeObject( Integer likeUserId,Integer likeWorkId) {
        this.likeWorkId = likeWorkId;
        this.likeUserId = likeUserId;
    }

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Integer getLikeWorkId() {
        return likeWorkId;
    }

    public void setLikeWorkId(Integer likeWorkId) {
        this.likeWorkId = likeWorkId;
    }

    public Integer getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(Integer likeUserId) {
        this.likeUserId = likeUserId;
    }
}

package com.travel.fj.service;

import com.travel.fj.domain.LikeObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeObjectService {

    void createLikeRecord(LikeObject likeObject);
    LikeObject getUserLikeRecordByUserIdAndWorkId( Integer userId,Integer workId);
    List<LikeObject>  getUserLikeRecordByUserId(@Param("uid") Integer userId);
    void deleteLikeRecordById(Integer id);
}

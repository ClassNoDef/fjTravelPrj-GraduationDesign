package com.travel.fj.dao;

import com.travel.fj.domain.LikeObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeObjectDao {

    void createLikeRecord(LikeObject likeObject);
    LikeObject getUserLikeRecordByUserIdAndWorkId(@Param("uid") Integer userId,@Param("wid") Integer workId);
    List<LikeObject> getUserLikeRecordByUserId(@Param("uid") Integer userId);
    void deleteLikeRecordById(Integer id);
}

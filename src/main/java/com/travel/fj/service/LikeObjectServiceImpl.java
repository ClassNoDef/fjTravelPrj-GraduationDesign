package com.travel.fj.service;

import com.travel.fj.dao.LikeObjectDao;
import com.travel.fj.domain.LikeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeObjectServiceImpl implements LikeObjectService {

    @Autowired
    LikeObjectDao likeObjectDao;

    @Override
    public void createLikeRecord(LikeObject likeObject) {
        likeObjectDao.createLikeRecord(likeObject);
    }

    @Override
    public LikeObject getUserLikeRecordByUserIdAndWorkId(Integer userId, Integer workId) {
        return likeObjectDao.getUserLikeRecordByUserIdAndWorkId(userId,workId);
    }

    @Override
    public List<LikeObject> getUserLikeRecordByUserId(Integer userId) {
        return likeObjectDao.getUserLikeRecordByUserId(userId);
    }

    @Override
    public void deleteLikeRecordById(Integer id) {
        likeObjectDao.deleteLikeRecordById(id);
    }
}

package com.travel.fj.service;

import com.travel.fj.dao.AttractionDao;
import com.travel.fj.dao.NoteTypeDao;
import com.travel.fj.dao.UserDao;
import com.travel.fj.dao.WorkDao;
import com.travel.fj.domain.Attraction;
import com.travel.fj.domain.NoteType;
import com.travel.fj.domain.User;
import com.travel.fj.domain.Work;
import com.travel.fj.queryhelper.WorkQueryHelper;
import com.travel.fj.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    WorkDao workDao;

    @Autowired
    NoteTypeDao noteTypeDao;

    @Autowired
    AttractionDao attractionDao;

    @Autowired
    UserDao userDao;
    @Override
    public void createWork(Work work) {
            workDao.createWork(work);

            NoteType n=noteTypeDao.getNoteTypeById(work.getWorkType().getNoteTypeId());
            n.setNoteCount(n.getNoteCount()+1);
            noteTypeDao.updateNoteCountData(n);

            Attraction a=attractionDao.getAttractionById(work.getWorkAttrac().getAttracId());
            a.setAttracNoteCount(a.getAttracNoteCount()+1);
            attractionDao.updateAttractionData(a);

            User u=userDao.getUserById(work.getWorkAuthor().getUserId());
            u.setNoteCount(a.getAttracNoteCount()+1);
            userDao.updateUserData(u);

    }

    @Override
    public List<Work> loadCheckedWorks() {
        return workDao.loadCheckedWorks();
    }

    @Override
    public List<Work> loadNoCheckWorks() {
        return workDao.loadNoCheckWorks();
    }

    @Override
    public List<Work> loadBlockedWorks() {
        return workDao.loadBlockedWorks();
    }

    @Override
    public List<Work> loadQueryWorks(WorkQueryHelper helper) {
        return workDao.loadQueryWorks(helper);
    }

    @Override
    public Page loadQueryAndPagedWorks(WorkQueryHelper workQueryHelper, Page page) {
        page.setPageContent(workDao.loadQueryAndPagedWorks(workQueryHelper,page.getStartIndex(),page.getPageSize()));
        page.setTotalRecNum((long)workDao.getQueryWorkCount(workQueryHelper));
        return page;
    }

    @Override
    public Page loadQueryAndPagedWorksAdmin(WorkQueryHelper workQueryHelper, Page page) {
        page.setPageContent(workDao.loadQueryAndPagedWorksAdmin(workQueryHelper,page.getStartIndex(),page.getPageSize()));
        page.setTotalRecNum((long)workDao.getQueryWorkCountAdmin(workQueryHelper));
        return page;
    }


    @Override
    public Work getWorkById(int id) {
        return workDao.getWorkById(id);
    }

    @Override
    public Work getWorkByIdAdmin(int id) {
        return workDao.getWorkByIdAdmin(id);
    }

    @Override
    public List<Work> getWorkByUserId(int userId) {
        return workDao.getWorkByUserId(userId);
    }

    @Override
    public List<Work> getWorkByWorkTypeId(int typeId) {
        return workDao.getWorkByWorkTypeId(typeId);
    }

    @Override
    public List<Work> getWorkByAttracId(int AttracId) {
        return workDao.getWorkByAttracId(AttracId);
    }

    @Override
    public List<Work> getWorkByAttracIdU(int AttracId) {
        return workDao.getWorkByAttracIdU(AttracId);
    }

    @Override
    public List<Work> getRandomWork() {
        return workDao.getRandomWork();
    }

    @Override
    public List<Work> getRandomWorkU() {
        return workDao.getRandomWorkU();
    }

    @Override
    public List<Work> getPopularWork() {
        return workDao.getPopularWork();
    }

    @Override
    public List<Work> getPopularWorkU() {
        return workDao.getPopularWorkU();
    }

    @Override
    public String getWorkPicById(int id) {
        return workDao.getWorkPicById(id);
    }

    @Override
    public void updateWork(Work work) {
        NoteType oldNoteType=workDao.getWorkById(work.getWorkId()).getWorkType();

        //异常捕获？
        workDao.updateWork(work);
        //异常捕获？

        oldNoteType.setNoteCount(oldNoteType.getNoteCount()-1);
        noteTypeDao.updateNoteCountData(oldNoteType);

        NoteType newNoteType=noteTypeDao.getNoteTypeById(work.getWorkType().getNoteTypeId());
        newNoteType.setNoteCount(newNoteType.getNoteCount()+1);
        noteTypeDao.updateNoteCountData(newNoteType);

    }

    @Override
    public void updateWorkStatus(Work work) {

        workDao.updateWorkStatus(work);
    }

    @Override
    public void updateLikeCount(Work work) {
        workDao.updateLikeCount(work);
    }

    @Override
    public void blockFreezeUserWork(int uid) {
        workDao.blockFreezeUserWork(uid);
    }

    @Override
    public void unBlockFreezeUserWork(int uid) {
        workDao.unBlockFreezeUserWork(uid);
    }
}

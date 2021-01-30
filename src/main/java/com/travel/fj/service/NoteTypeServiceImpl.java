package com.travel.fj.service;

import com.travel.fj.dao.NoteTypeDao;
import com.travel.fj.domain.NoteType;
import com.travel.fj.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteTypeServiceImpl implements NoteTypeService {

    @Autowired
    NoteTypeDao noteTypeDao;
    @Override
    public void createNoteType(NoteType noteType) {
        try{
        noteTypeDao.createNoteType(noteType);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NOTE_TYPE_NAME"))
                throw  new DataAccessException("游记类型重复！");
        }
    }

    @Override
    public List<NoteType> loadAllNoteType() {
        return noteTypeDao.loadAllNoteType();
    }

    @Override
    public NoteType getNoteTypeById(int id) {
        NoteType result=noteTypeDao.getNoteTypeById(id);

        return result;
    }

    @Override
    public void updateNoteType(NoteType noteType) {

        try{
            noteTypeDao.updateNoteType(noteType);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NOTE_TYPE_NAME"))
                throw  new DataAccessException("游记类型重复！");
        }
    }

    @Override
    public void deleteNoteType(int id) {
        try {
            noteTypeDao.deleteNoteType(id);
        }catch (Exception e){
            if(e.getMessage().contains("FK_WORK_TYPE"))
                throw  new DataAccessException("该类型下存在游记，不可删除！");
        }
    }

    @Override
    public void updateNoteCountData(NoteType noteType) {
        noteTypeDao.updateNoteCountData(noteType);
    }
}

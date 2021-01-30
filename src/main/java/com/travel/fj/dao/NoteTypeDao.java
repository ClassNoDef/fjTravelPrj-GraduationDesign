package com.travel.fj.dao;

import com.travel.fj.domain.NoteType;

import java.util.List;

public interface NoteTypeDao {

    void createNoteType(NoteType noteType);
    List<NoteType> loadAllNoteType();
    NoteType getNoteTypeById(int id);
    void updateNoteType(NoteType noteType);
    void deleteNoteType(int id);
    void updateNoteCountData(NoteType noteType);
}

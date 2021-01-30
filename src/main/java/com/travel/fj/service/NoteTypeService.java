package com.travel.fj.service;

import com.travel.fj.domain.NoteType;

import java.util.List;

public interface NoteTypeService {

    void createNoteType(NoteType noteType);
    List<NoteType> loadAllNoteType();
    NoteType getNoteTypeById(int id);
    void updateNoteType(NoteType noteType);
    void deleteNoteType(int id);
    void updateNoteCountData(NoteType noteType);
}

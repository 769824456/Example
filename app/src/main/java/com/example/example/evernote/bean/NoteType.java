package com.example.example.evernote.bean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class NoteType extends DataSupport {

    private int noteType;
    private String noteTypeString;
    private List<Note> noteList = new ArrayList<Note>();

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public String getNoteTypeString() {
        return noteTypeString;
    }

    public void setNoteTypeString(String noteTypeString) {
        this.noteTypeString = noteTypeString;
    }
}

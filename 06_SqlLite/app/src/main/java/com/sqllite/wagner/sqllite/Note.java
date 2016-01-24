package com.sqllite.wagner.sqllite;

/**
 * Created by Wagner on 24/01/2016.
 */
public class Note {
    private long id;
    private String note;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    @Override
    public String toString(){
        return note;
    }
}

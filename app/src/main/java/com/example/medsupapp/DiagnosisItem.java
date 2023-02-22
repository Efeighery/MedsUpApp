package com.example.medsupapp;

public class DiagnosisItem {
    String hID, term;

    public DiagnosisItem() {
    }

    public DiagnosisItem(String hID, String term) {
        this.hID = hID;
        this.term = term;
    }

    public String gethID() {
        return hID;
    }

    public void sethID(String hID) {
        this.hID = hID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}

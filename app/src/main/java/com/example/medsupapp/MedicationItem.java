package com.example.medsupapp;

public class MedicationItem {

    String mID, name, rules;

    public MedicationItem() {
    }

    public MedicationItem(String mID, String name, String rules) {
        this.mID = mID;
        this.name = name;
        this.rules = rules;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}

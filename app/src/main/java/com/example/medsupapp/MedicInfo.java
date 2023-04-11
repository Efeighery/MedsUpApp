package com.example.medsupapp;

/*
 *  Class name: MedicInfo.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 06/04/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/MedicInfo.java
 *
 */

public class MedicInfo {

    // Variables needed to save a created note in the FireStore
    String title, content;

    // Empty constructor for initialisation
    public MedicInfo() {
    }

    // Another constructor containing the variables
    public MedicInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

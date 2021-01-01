package com.example.todo.Pages;


public class CheckTaskClass {
    String Title;
    Boolean isChecked;
    String id,Description;

    public CheckTaskClass() {

    }


    public CheckTaskClass(String id, String title, Boolean isChecked, String Description ) {
        this.Title = title;
        this.isChecked = isChecked;
        this.id=id;
        this.Description=Description;
    }

    public String getId() {
        return id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) { this.Description = Description;}

}
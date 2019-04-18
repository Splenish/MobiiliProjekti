package com.example.test;

public class Guide {
    private int Img;
    private String Title;
    private String text;

    // Constructor that is used to create an instance of the Movie object
    Guide(int Img, String Title, String text) {
        this.Img = Img;
        this.Title = Title;
        this.text = text;
    }

    int getImg() {
        return Img;
    }

    public void setImg(int Img) {
        this.Img = Img;
    }

    String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }
}

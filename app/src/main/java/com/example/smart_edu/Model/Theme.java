package com.example.smart_edu.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theme implements Serializable {
    @SerializedName("Id_theme")
    @Expose
    private Integer idTheme;
    @SerializedName("Name_theme")
    @Expose
    private String nameTheme;
    @SerializedName("Id_book")
    @Expose
    private Integer idBook;
    @SerializedName("Id_link_lesson")
    @Expose
    private String idLinkLesson;

    public Integer getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(Integer idTheme) {
        this.idTheme = idTheme;
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public void setNameTheme(String nameTheme) {
        this.nameTheme = nameTheme;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public String getIdLinkLesson() {
        return idLinkLesson;
    }

    public void setIdLinkLesson(String idLinkLesson) {
        this.idLinkLesson = idLinkLesson;
    }
}

package com.example.smart_edu.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Class implements Serializable {
    @SerializedName("Id_class")
    @Expose
    private Integer idClass;
    @SerializedName("Name_class")
    @Expose
    private String nameClass;
    @SerializedName("Id_book")
    @Expose
    private Integer idBook;
    @SerializedName("Date_study")
    @Expose
    private String dateStudy;
    @SerializedName("Hour_study")
    @Expose
    private String hourStudy;

    public Integer getIdClass() {
        return idClass;
    }

    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public String getDateStudy() {
        return dateStudy;
    }

    public void setDateStudy(String dateStudy) {
        this.dateStudy = dateStudy;
    }

    public String getHourStudy() {
        return hourStudy;
    }

    public void setHourStudy(String hourStudy) {
        this.hourStudy = hourStudy;
    }

    @NonNull
    @Override
    public String toString() {
        return this.idClass +" - "+ this.nameClass;
    }
}

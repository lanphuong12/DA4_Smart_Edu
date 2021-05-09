package com.example.smart_edu.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {

    @SerializedName("Id_HV")
    @Expose
    private Integer idHV;
    @SerializedName("Name_HV")
    @Expose
    private String nameHV;
    @SerializedName("Id_class")
    @Expose
    private Integer idClass;
    @SerializedName("Tuition")
    @Expose
    private Integer tuition;
    @SerializedName("Name_PH")
    @Expose
    private String namePH;
    @SerializedName("Phone_PH")
    @Expose
    private String phonePH;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Address_HV")
    @Expose
    private String addressHV;
    @SerializedName("Date_of_birth")
    @Expose
    private Object dateOfBirth;

    public Integer getIdHV() {
        return idHV;
    }

    public void setIdHV(Integer idHV) {
        this.idHV = idHV;
    }

    public String getNameHV() {
        return nameHV;
    }

    public void setNameHV(String nameHV) {
        this.nameHV = nameHV;
    }

    public Integer getIdClass() {
        return idClass;
    }

    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }

    public Integer getTuition() {
        return tuition;
    }

    public void setTuition(Integer tuition) {
        this.tuition = tuition;
    }

    public String getNamePH() {
        return namePH;
    }

    public void setNamePH(String namePH) {
        this.namePH = namePH;
    }

    public String getPhonePH() {
        return phonePH;
    }

    public void setPhonePH(String phonePH) {
        this.phonePH = phonePH;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressHV() {
        return addressHV;
    }

    public void setAddressHV(String addressHV) {
        this.addressHV = addressHV;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Student(Integer idHV, String nameHV, Integer idClass, Integer tuition, String namePH, String phonePH, String email, String addressHV, Object dateOfBirth) {
        this.idHV = idHV;
        this.nameHV = nameHV;
        this.idClass = idClass;
        this.tuition = tuition;
        this.namePH = namePH;
        this.phonePH = phonePH;
        this.email = email;
        this.addressHV = addressHV;
        this.dateOfBirth = dateOfBirth;
    }

    @NonNull
    @Override
    public String toString() {
        return this.idHV +" - "+ this.nameHV;
    }
}
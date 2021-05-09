package com.example.smart_edu.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vocab {
    @SerializedName("Id_vocab")
    @Expose
    private Integer idVocab;
    @SerializedName("Id_theme")
    @Expose
    private Integer idTheme;
    @SerializedName("Word")
    @Expose
    private String word;
    @SerializedName("Meaning")
    @Expose
    private String meaning;

    public Integer getIdVocab() {
        return idVocab;
    }

    public void setIdVocab(Integer idVocab) {
        this.idVocab = idVocab;
    }

    public Integer getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(Integer idTheme) {
        this.idTheme = idTheme;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Vocab(Integer idVocab, Integer idTheme, String word, String meaning) {
        this.idVocab = idVocab;
        this.idTheme = idTheme;
        this.word = word;
        this.meaning = meaning;
    }
}

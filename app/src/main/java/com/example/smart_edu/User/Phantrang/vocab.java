package com.example.smart_edu.User.Phantrang;

public class vocab {
    private String word, Meaning;

    public vocab(String word, String meaning) {
        this.word = word;
        Meaning = meaning;
    }
    public vocab() {
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }
}

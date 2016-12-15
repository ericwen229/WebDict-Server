package com.ericwen229.webdict.model;

public class Explanation {

    private String source;
    private String status;
    private String enPhonetic;
    private String usPhonetic;
    private String translation;
    private int likes;

    public Explanation(String source, String status, String enPhonetic, String usPhonetic, String translation, int likes) {
        this.source = source;
        this.status = status;
        this.enPhonetic = enPhonetic;
        this.usPhonetic = usPhonetic;
        this.translation = translation;
        this.likes = likes;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
        return status;
    }

    public String getEnPhonetic() {
        return enPhonetic;
    }

    public String getUsPhonetic() {
        return usPhonetic;
    }

    public String getTranslation() {
        return translation;
    }

    public int getLikes() { return likes; }

}

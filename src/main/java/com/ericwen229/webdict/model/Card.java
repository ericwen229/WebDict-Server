package com.ericwen229.webdict.model;

public class Card {

    private String from;
    private String word;
    private String youdao = "false";
    private String jinshan = "false";
    private String haici = "false";

    public Card(String from, String word, boolean youdao, boolean jinshan, boolean haici) {
        this.from = from;
        this.word = word;
        if (youdao) {
            this.youdao = "true";
        }
        if (jinshan) {
            this.jinshan = "true";
        }
        if (haici) {
            this.haici = "true";
        }
    }

    public String getFrom() {
        return from;
    }

    public String getWord() {
        return word;
    }

    public String getYoudao() {
        return youdao;
    }

    public String getJinshan() {
        return jinshan;
    }

    public String getHaici() {
        return haici;
    }

}

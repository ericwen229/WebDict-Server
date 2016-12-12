package com.ericwen229.webdict.model;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private String word;
    private List<Explanation> explanations;

    public Query(String word) {
        this.word = word;
        explanations = new ArrayList<>();
    }

    public void queryBaidu() {
        explanations.add(new Explanation("baidu", "success",
                "en baidu " + word, "us baidu " + word,
                "tr baidu " + word));
    }

    public void queryYoudao() {
        explanations.add(new Explanation("youdao", "success",
                "en youdao " + word, "us youdao " + word,
                "tr youdao " + word));
    }

    public void queryJinshan() {
        explanations.add(new Explanation("jinshan", "success",
                "en jinshan " + word, "us jinshan " + word,
                "tr jinshan " + word));
    }

    public String getWord() {
        return word;
    }

    public List<Explanation> getExplanations() {
        return explanations;
    }

}

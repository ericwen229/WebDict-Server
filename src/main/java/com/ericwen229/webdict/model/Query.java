package com.ericwen229.webdict.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (word.length() == 0) {
            return;
        }
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        String status = "fail", enPhonetic = "", usPhonetic = "", translation = "";
        try {
            String url = "http://fanyi.youdao.com/openapi.do?keyfrom=EasyDictGenerator&key=1503487301&type=data&doctype=json&version=1.1&q=" + word;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            String resultStr = result.toString();
            System.out.println(resultStr);
            Pattern statusPattern = Pattern.compile("\"errorCode\":(\\d+)");
            Matcher statusMatcher = statusPattern.matcher(resultStr);
            if (statusMatcher.find()) {
                status = statusMatcher.group(1);
            }
            if (status.equals("0")) {
                status = "success";
                Pattern enPhPattern = Pattern.compile("\"uk-phonetic\":\"([^\"]+)");
                Pattern usPhPattern = Pattern.compile("\"us-phonetic\":\"([^\"]+)");
                Pattern transPattern = Pattern.compile("\"explains\":\\[([^\\]]+)");
                Matcher enPhMatcher = enPhPattern.matcher(resultStr);
                Matcher usPhMatcher = usPhPattern.matcher(resultStr);
                Matcher transMatcher = transPattern.matcher(resultStr);
                if (enPhMatcher.find()) enPhonetic = enPhMatcher.group(1);
                if (usPhMatcher.find()) usPhonetic = usPhMatcher.group(1);
                if (transMatcher.find()) translation = transMatcher.group(1);
                translation = translation.replace("\"", "");
                translation = translation.replace(",", ";");
            }
            else {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        explanations.add(new Explanation("youdao", status,
                enPhonetic, usPhonetic,
                translation));
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

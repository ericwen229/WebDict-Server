package com.ericwen229.webdict.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
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

    public void queryHaici() {
        if (word.length() == 0) {
            return;
        }
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        String status = "fail", enPhonetic = "", usPhonetic = "", translation = "";
        int like = 0;
        try {
            String url = "http://dict.cn/apis/dict3.php?skin=false&q=" + word;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            String resultStr = result.toString();

            Pattern enPhPattern = Pattern.compile("英 <bdo>\\[([^\\]]+)");
            Pattern usPhPattern = Pattern.compile("美 <bdo>\\[([^\\]]+)");
            Pattern transPattern = Pattern.compile("<div id=\"exp\">(.+?)</div>");
            Matcher enPhMatcher = enPhPattern.matcher(resultStr);
            Matcher usPhMatcher = usPhPattern.matcher(resultStr);
            Matcher transMatcher = transPattern.matcher(resultStr);
            boolean success = false;
            if (enPhMatcher.find()) {
                enPhonetic = enPhMatcher.group(1);
                success = true;
            }
            if (usPhMatcher.find()) {
                usPhonetic = usPhMatcher.group(1);
                success = true;
            }
            if (transMatcher.find()) {
                translation = transMatcher.group(1);
                success = true;
            }
            if (success) {
                status = "success";
                translation = translation.replace("<br />", ";");
                translation = translation.trim();
            }
        }
        catch (Exception e) {
            status = "fail";
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
        if (status.equals("success")) {
            like = (int)(Math.random() * 100);
        }
        explanations.add(new Explanation("haici", status,
                enPhonetic, usPhonetic,
                translation, like));
    }

    public void queryYoudao() {
        if (word.length() == 0) {
            return;
        }
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        String status = "fail", enPhonetic = "", usPhonetic = "";
        StringBuffer translation = new StringBuffer();
        int like = 0;
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
            JSONObject resultJson = new JSONObject(resultStr);
            int errCode = resultJson.getInt("errorCode");
            if (errCode == 0) {
                status = "success";
                JSONObject basic = resultJson.getJSONObject("basic");
                enPhonetic = basic.getString("uk-phonetic");
                usPhonetic = basic.getString("us-phonetic");
                JSONArray explains = basic.getJSONArray("explains");
                for (int i = 0; i < explains.length(); ++ i) {
                    if (i != 0) {
                        translation.append(";");
                    }
                    translation.append(explains.getString(i));
                }
            }
        }
        catch (Exception e) {
            status = "fail";
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
        if (status.equals("success")) {
            like = (int)(Math.random() * 100);
        }
        explanations.add(new Explanation("youdao", status,
                enPhonetic, usPhonetic,
                translation.toString(), like));
    }

    public void queryJinshan() {
        if (word.length() == 0) {
            return;
        }
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        String status = "", enPhonetic = "", usPhonetic = "";
        StringBuffer translation = new StringBuffer();
        int like = 0;
        try {
            String url = "http://dict-co.iciba.com/api/dictionary.php?key=FD186122ADD1932EDD67B5E84AF2D90A&type=json&w=" + word;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            String resultStr = result.toString();
            JSONObject resultJson = new JSONObject(resultStr);
            resultJson = resultJson.getJSONArray("symbols").getJSONObject(0);
            enPhonetic = resultJson.getString("ph_en");
            usPhonetic = resultJson.getString("ph_am");
            JSONArray translationArray = resultJson.getJSONArray("parts");
            for (int i = 0; i < translationArray.length(); ++ i) {
                JSONObject part = translationArray.getJSONObject(i);
                if (i != 0) {
                    translation.append(";");
                }
                translation.append(part.getString("part"));
                translation.append(' ');
                JSONArray means = part.getJSONArray("means");
                for (int j = 0; j < means.length(); ++ j) {
                    if (j != 0) {
                        translation.append("，");
                    }
                    translation.append(means.getString(j));
                }
            }
            status = "success";
        }
        catch (Exception e) {
            status = "fail";
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e2) {
                status = "fail";
                e2.printStackTrace();
            }
        }
        if (status.equals("success")) {
            like = (int)(Math.random() * 100);
        }
        explanations.add(new Explanation("jinshan", status,
                enPhonetic, usPhonetic,
                translation.toString(), like));
    }

    public String getWord() {
        return word;
    }

    public List<Explanation> getExplanations() {
        return explanations;
    }

}

package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.Query;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
public class QueryController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/webdict";

    static final String USER = "eric";
    static final String PWD = "professional*";

    @RequestMapping(value = "/q", method = RequestMethod.GET)
    public Query query(@RequestParam(value = "word") String word,
                       @RequestParam(value = "haici", defaultValue = "false") String haici,
                       @RequestParam(value = "youdao", defaultValue = "false") String youdao,
                       @RequestParam(value = "jinshan", defaultValue = "false") String jinshan) {

        Connection conn = null;
        Statement stmt = null;
        int youdaoLike = 0, jinshanLike = 0, haiciLike = 0;
        boolean insert = false;
        Query q = new Query(word);

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            word = word.replace(" ", "");
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM likes WHERE word='%s' LIMIT 1", word));
            if (rs.next()) {
                youdaoLike = rs.getInt("youdao");
                jinshanLike = rs.getInt("jinshan");
                haiciLike = rs.getInt("haici");
            }
            else {
                insert = true;
            }

            if (!haici.equalsIgnoreCase("false")) {
                q.queryHaici(haiciLike);
            }
            if (!youdao.equalsIgnoreCase("false")) {
                q.queryYoudao(youdaoLike);
            }
            if (!jinshan.equalsIgnoreCase("false")) {
                q.queryJinshan(jinshanLike);
            }

            if (q.success() && insert) {
                stmt.executeUpdate(String.format("INSERT INTO likes VALUES ('%s', 0, 0, 0)", word));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            }
            catch (SQLException se) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return q;
    }

}

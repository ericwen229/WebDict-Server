package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.Card;
import com.ericwen229.webdict.model.CardList;
import com.ericwen229.webdict.model.RequestStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@RequestMapping(value = "/card")
public class CardController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/webdict";

    static final String USER = "eric";
    static final String PWD = "professional*";

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public RequestStatus sendCard(@RequestParam(value = "fromUser") String fromUser,
                                  @RequestParam(value = "toUser") String toUser,
                                  @RequestParam(value = "word") String word,
                                  @RequestParam(value = "youdao", defaultValue = "true") String youdao,
                                  @RequestParam(value = "jinshan", defaultValue = "true") String jinshan,
                                  @RequestParam(value = "haici", defaultValue = "true") String haici) {
        Connection conn = null;
        Statement stmt = null;
        boolean success = true;
        String msg = "";

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format("INSERT INTO cards VALUES ('%s', '%s', '%s', %s, %s, %s)", fromUser, toUser, word, youdao, jinshan, haici));
        }
        catch (Exception e) {
            success = false;
            msg = "internal exception";
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            }
            catch (SQLException se) {
                success = false;
                msg = "internal exception";
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException se) {
                success = false;
                msg = "internal exception";
                se.printStackTrace();
            }
        }
        return new RequestStatus(success, msg);
    }

    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public CardList receiveCards(@RequestParam(value = "username") String username) {
        Connection conn = null;
        Statement stmt = null;
        CardList list = new CardList();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT fromUser, word, youdao, jinshan, haici FROM cards WHERE toUser='%s'", username));
            while (rs.next()) {
                Card card = new Card(rs.getString("fromUser"),
                        rs.getString("word"),
                        rs.getBoolean("youdao"),
                        rs.getBoolean("jinshan"),
                        rs.getBoolean("haici"));
                list.addCard(card);
            }
            stmt.executeUpdate(String.format("DELETE FROM cards WHERE toUser='%s'", username));
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

        return list;
    }

}

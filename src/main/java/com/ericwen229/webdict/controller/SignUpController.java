package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.Status;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class SignUpController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/webdict";

    static final String USER = "eric";
    static final String PWD = "professional*";

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Status signUp(@RequestParam(value = "username") String username,
                         @RequestParam(value = "pwd") String password) {
        Connection conn = null;
        Statement stmt = null;
        boolean success = true;
        String message = "";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM users WHERE username='%s' LIMIT 1", username));
            if (rs.next()) {
                success = false;
                message = "user already exists";
            }
            else {
                stmt.executeUpdate(String.format("INSERT INTO users VALUES ('%s', '%s', FALSE)", username, password));
            }
        }
        catch (Exception e) {
            success = false;
            message = "internal exception";
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
                message = "internal exception";
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException se) {
                success = false;
                message = "internal exception";
                se.printStackTrace();
            }
        }

        return new Status(success, message);
    }

}

package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.UserList;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
public class UserListController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/webdict";

    static final String USER = "eric";
    static final String PWD = "professional*";

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public UserList users() {

        Connection conn = null;
        Statement stmt = null;
        UserList list = new UserList();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM users");
            while (rs.next()) {
                list.addUser(rs.getString("username"));
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

        return list;
    }

}

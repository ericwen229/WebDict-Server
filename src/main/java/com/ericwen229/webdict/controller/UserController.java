package com.ericwen229.webdict.controller;

import com.ericwen229.webdict.model.RequestStatus;
import com.ericwen229.webdict.model.UserList;
import com.ericwen229.webdict.model.UserStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/webdict";

    static final String USER = "eric";
    static final String PWD = "professional*";

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public UserList users() {

        Connection conn = null;
        Statement stmt = null;
        UserList list = new UserList();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, online FROM users");
            while (rs.next()) {
                boolean online = rs.getBoolean("online");
                if (online) {
                    list.addOnlineUser(rs.getString("username"));
                }
                else {
                    list.addOfflineUser(rs.getString("username"));
                }
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

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public UserStatus getStatus(@RequestParam(value = "username") String username) {
        Connection conn = null;
        Statement stmt = null;
        boolean success = true;
        boolean userOnline = false;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT username, online FROM users WHERE username='%s'", username));
            if (!rs.next()) {
                success = false;
            }
            else {
                userOnline = rs.getBoolean("online");
            }
        }
        catch (Exception e) {
            success = false;
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
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException se) {
                success = false;
                se.printStackTrace();
            }
        }

        return new UserStatus(success, userOnline);

    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public RequestStatus signUp(@RequestParam(value = "username") String username,
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

        return new RequestStatus(success, message);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RequestStatus login(@RequestParam(value = "username") String username,
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
            if (!rs.next()) {
                success = false;
                message = "user not found";
            }
            else {
                String pwd = rs.getString("pwd");
                if (pwd.equals(password)) {
                    stmt.executeUpdate(String.format("UPDATE users SET online=TRUE WHERE username='%s'", username));
                }
                else {
                    success = false;
                    message = "password incorrect";
                }
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

        return new RequestStatus(success, message);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public RequestStatus logout(@RequestParam(value = "username") String username,
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
            if (!rs.next()) {
                success = false;
                message = "user not found";
            }
            else {
                String pwd = rs.getString("pwd");
                if (pwd.equals(password)) {
                    stmt.executeUpdate(String.format("UPDATE users SET online=FALSE WHERE username='%s'", username));
                }
                else {
                    success = false;
                    message = "password incorrect";
                }
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

        return new RequestStatus(success, message);
    }

}

package com.tao.datat.service.database.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
    public static void closeConn(Connection connection) {
        // 关闭资源
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void closeConnStmt(Statement statement, Connection connection) {
        // 关闭资源
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if (statement != null) statement.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void closeConnStmtRest(ResultSet rest, Statement statement, Connection connection) {
        // 关闭资源
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if (statement != null) statement.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if (rest != null) rest.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

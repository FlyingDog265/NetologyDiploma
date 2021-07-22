package ru.netology.helpers;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    private final static String dbUrlDefault = "jdbc:mysql://host.docker.internal:3306/app";
    private final static String user = "app";
    private final static String password = "pass";

    private DbHelper() {
    }

    private static String getDbUrl() {
        String dbUrl = System.getProperty("db.url");
        if (dbUrl.isEmpty()) {
            dbUrl = dbUrlDefault;
        }
        return dbUrl;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getDbUrl(), user, password);
    }

    public static void clearTable() {
        var truncateOrderSQL = "TRUNCATE TABLE order_entity;";
        var truncatePaymentSQL = "TRUNCATE TABLE payment_entity;";
        var truncateCreditSQL = "TRUNCATE TABLE credit_request_entity;";
        var runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            runner.execute(conn, truncateOrderSQL, new ScalarHandler<>());
            runner.execute(conn, truncatePaymentSQL, new ScalarHandler<>());
            runner.execute(conn, truncateCreditSQL, new ScalarHandler<>());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static String getOperationStatus(int amount, String statusSQL) {
        var runner = new QueryRunner();
        String status = "";
        try (Connection conn = getConnection()) {
            status = runner.query(conn, statusSQL, new ScalarHandler<>(), amount);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static String getPaymentInfo(int amount) {
        String statusSQL = "SELECT payment_entity.status "
                + "FROM payment_entity "
                + "INNER JOIN order_entity "
                + "ON order_entity.payment_id = payment_entity.transaction_id "
                + "WHERE payment_entity.amount = ?;";
        return getOperationStatus(amount, statusSQL);
    }

    public static String getCreditInfo(int amount) {
        String statusSQL = "SELECT payment_entity.status "
                + "FROM payment_entity "
                + "INNER JOIN order_entity "
                + "ON payment_entity.id = order_entity.payment_id "
                + "INNER JOIN credit_request_entity "
                + "ON order_entity.credit_id = credit_request_entity.id "
                + "WHERE payment_entity.amount = ?;";
        return getOperationStatus(amount, statusSQL);
    }
}


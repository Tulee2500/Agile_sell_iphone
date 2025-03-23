/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1trangchu.DBConnect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NGUYEN QUANG HUY
 */
public class Dbconnect {

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE_NAME = "DA_QLBANDIENTHOAI_IPHONE_NHOM5";
    private static final boolean USING_SSL = true;

    // Khai báo CONNECT_STRING và khởi tạo trong khối tĩnh
    private static final String CONNECT_STRING;

    static {
        StringBuilder connectStringBuilder = new StringBuilder();
        connectStringBuilder.append("jdbc:sqlserver://")
                .append(SERVER).append(":").append(PORT).append(";")
                .append("databaseName=").append(DATABASE_NAME).append(";")
                .append("user=").append(USERNAME).append(";")
                .append("password=").append(PASSWORD).append(";");
        if (USING_SSL) {
            connectStringBuilder.append("encrypt=true;trustServerCertificate=true;");
        }
        CONNECT_STRING = connectStringBuilder.toString(); // Khởi tạo biến CONNECT_STRING
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECT_STRING); // Sử dụng CONNECT_STRING để lấy kết nối
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                DatabaseMetaData dbmt = conn.getMetaData();
                System.out.println("Driver Name: " + dbmt.getDriverName());
                System.out.println("Database Product Name: " + dbmt.getDatabaseProductName());
                System.out.println("Database Product Version: " + dbmt.getDatabaseProductVersion());
            } else {
                System.out.println("Kết nối không thành công.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

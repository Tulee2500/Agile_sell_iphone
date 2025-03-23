/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.KhachHang;
import Model.KhachHangBh;
import Model.KhachHang_Model;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nothank
 */
public class KhachHangbhRepository {

    public boolean isSdtExists(String sdt) {
        String sql = "SELECT COUNT(*) FROM [dbo].[KhachHang] WHERE [SĐT] = ?";
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu số điện thoại đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không có lỗi, trả về false (số điện thoại không tồn tại)
    }

    public boolean isMaKhachHangExists(String maKhachHang) {
        String sql = "SELECT COUNT(*) FROM [dbo].[KhachHang] WHERE [Ma_Khach_Hang] = ?";
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhachHang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu mã khách hàng đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không có lỗi, trả về false (mã khách hàng không tồn tại)
    }

    public Boolean Create(KhachHangBh kh) {
        // Kiểm tra trùng SDT và Ma_Khach_Hang
        if (isSdtExists(kh.getSDT())) {
            JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (isMaKhachHangExists(kh.getMa())) {
            JOptionPane.showMessageDialog(null, "Mã khách hàng đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO [dbo].[KhachHang]\n"
                + "           ([Ma_Khach_Hang]\n"
                + "           ,[Ten]\n"
                + "           ,[SĐT]\n"
                + "           ,[Email]\n"
                + "           ,[Gioi_Tinh]\n"
                + "           ,[Dia_Chi]\n"
                + "           ,[Trang_Thai])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,''\n"
                + "           ,?\n"
                + "           ,''\n"
                + "           ,0)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Dbconnect.getConnection();
            ps = conn.prepareStatement(sql);

            // Set the parameters for the prepared statement
            ps.setString(1, kh.getMa());
            ps.setString(2, kh.getTen());
            ps.setString(3, kh.getSDT());
            ps.setBoolean(4, kh.getGioiTinh());

            // Execute the insert
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions (log or throw)
            return false;
        } finally {
            // Close resources in finally block to ensure they are closed even if an exception occurs
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); // Handle exceptions during resource closing
            }
        }
    }

}

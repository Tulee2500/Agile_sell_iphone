/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import duan1trangchu.DBConnect.ShareUtil;
import Model.nhanvienLoginModel;
import duan1trangchu.DBConnect.Dbconnect;
import duan1trangchu.DBConnect.ShareUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hadac
 */
public class NhanvienLoginRepository {

    Connection conn = null;
    PreparedStatement sttm = null;
    private nhanvienLoginModel nhanvienLoginModel;

    public nhanvienLoginModel getByUsername(String taikhoan) {
        nhanvienLoginModel nhanvienLoginModel = null;

        conn = Dbconnect.getConnection();
        try {
            String sql = "SELECT * FROM NHANVIEN where Tai_khoan =?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, taikhoan);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Tạo một đối tượng nhanvienModel từ dữ liệu của ResultSet
                        nhanvienLoginModel = new nhanvienLoginModel();
                        nhanvienLoginModel.setHoten(resultSet.getString("ho_ten"));
                        nhanvienLoginModel.setDiachi(resultSet.getString("dia_chi"));
                        nhanvienLoginModel.setEmail(resultSet.getString("email"));
                        nhanvienLoginModel.setChucvu(resultSet.getString("chuc_vu"));

                        // Lấy thông tin về chức vụ
                        // Các trường khác...
                        ShareUtil.USER = nhanvienLoginModel;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nhanvienLoginModel;
    }
}

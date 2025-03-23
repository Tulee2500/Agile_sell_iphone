/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.CPU;
import Model.DungLuongPin;
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
public class DungLuongPinRepository {

    public ArrayList<DungLuongPin> getAll() {
        String sql = "SELECT [ID_Dung_Luong_Pin], [Dung_Luong_Pin] "
                + "FROM [dbo].[DungLuongPin] WHERE Trang_Thai = 0 "; // OFFSET và LIMIT

        ArrayList<DungLuongPin> arrDungLuongPin = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                int ID_Dung_Luong_Pin = rs.getInt("ID_Dung_Luong_Pin");
                String dungLuongPin = rs.getString("Dung_Luong_Pin");

                // Tạo đối tượng DungLuongPin
                DungLuongPin pin = new DungLuongPin(ID_Dung_Luong_Pin, dungLuongPin);
                arrDungLuongPin.add(pin); // Thêm vào danh sách
            }

            return arrDungLuongPin; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrDungLuongPin; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<DungLuongPin> getName() {
        String sql = "SELECT Dung_Luong_Pin FROM DungLuongPin WHERE Trang_Thai = 0";

        ArrayList<DungLuongPin> arrDLP = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng DungLuongPin
                DungLuongPin r = new DungLuongPin();
                r.setDung_Luong_Pin(rs.getString("Dung_Luong_Pin"));
                arrDLP.add(r); // Thêm vào danh sách
            }

            return arrDLP; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrDLP; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean delete(String ma) {
        String sql = "UPDATE [dbo].[DungLuongPin] SET [Trang_Thai] = 1 WHERE [Dung_Luong_Pin] = ?";

        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, ma);

            // Thực hiện UPDATE và lấy số hàng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate(); // Sử dụng executeUpdate()

            // Đóng PreparedStatement và Connection
            ps.close();
            conn.close();

            return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean insertinto(String maDungLuongPin) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[DungLuongPin] WHERE [Dung_Luong_Pin] = ?";
        String insertSql = "INSERT INTO [dbo].[DungLuongPin] ([Dung_Luong_Pin], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của maDungLuongPin
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, maDungLuongPin);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, maDungLuongPin);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu mã DungLuongPin đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean update(int ID, String ma) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[DungLuongPin] WHERE [Dung_Luong_Pin] = ? AND [ID_Dung_Luong_Pin] <> ?";
        String updateSql = "UPDATE [dbo].[DungLuongPin] SET [Dung_Luong_Pin] = ? WHERE [ID_Dung_Luong_Pin] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của mã DungLuongPin khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ma);
            checkStmt.setObject(2, ID);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu mã DungLuongPin đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã Dung Lượng Pin đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Nếu không tồn tại, thực hiện cập nhật
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setObject(1, ma);
            updateStmt.setObject(2, ID);

            // Thực hiện UPDATE và lấy số hàng bị ảnh hưởng
            int rowsAffected = updateStmt.executeUpdate(); // Sử dụng executeUpdate()

            return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        } finally {
            try {
                if (checkStmt != null) {
                    checkStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<DungLuongPin> search(String keyword) {
        ArrayList<DungLuongPin> result = new ArrayList<>();
        String sql = "SELECT * FROM DungLuongPin WHERE Dung_Luong_Pin LIKE ? AND Trang_Thai = 0";  // Thêm điều kiện Trang_Thai = 0

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                DungLuongPin dlp = new DungLuongPin();
                dlp.setID_Dung_Luong_Pin(rs.getInt("ID_Dung_Luong_Pin"));
                dlp.setDung_Luong_Pin(rs.getString("Dung_Luong_Pin"));
                result.add(dlp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }

}

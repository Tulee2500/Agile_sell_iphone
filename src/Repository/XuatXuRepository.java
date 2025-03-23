/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.XuatXu;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author nothank
 */
public class XuatXuRepository {

    public ArrayList<XuatXu> getAll() {
        String sql = "SELECT [ID_Xuat_Xu], [Xuat_Xu] "
                + "FROM [dbo].[XuatXu] WHERE [Trang_Thai] = 0 ";

        ArrayList<XuatXu> arrXuatXu = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                int ID_Xuat_Xu = rs.getInt("ID_Xuat_Xu");
                String xuatXu = rs.getString("Xuat_Xu");

                // Tạo đối tượng XuatXu
                XuatXu xu = new XuatXu(ID_Xuat_Xu, xuatXu);
                arrXuatXu.add(xu); // Thêm vào danh sách
            }

            return arrXuatXu; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrXuatXu; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<XuatXu> getName() {
        String sql = "SELECT Xuat_Xu FROM XuatXu WHERE Trang_Thai = 0";

        ArrayList<XuatXu> arrXuatXu = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng XuatXu
                XuatXu xu = new XuatXu();
                xu.setXuat_Xu(rs.getString("Xuat_Xu"));
                arrXuatXu.add(xu); // Thêm vào danh sách
            }

            return arrXuatXu; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrXuatXu; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean delete(String maXuatXu) {
        String sql = "UPDATE [dbo].[XuatXu] SET [Trang_Thai] = 1 WHERE [ID_Xuat_Xu] = ?";

        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, maXuatXu);

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

    // Thêm xuất xứ mới vào cơ sở dữ liệu
    public boolean insertInto(String xuatXu) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[XuatXu] WHERE [Xuat_Xu] = ?";
        String insertSql = "INSERT INTO [dbo].[XuatXu] ([Xuat_Xu], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của xuất xứ
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, xuatXu);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, xuatXu);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu xuất xứ đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    // Cập nhật xuất xứ
    public boolean update(int id, String xuatXu) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[XuatXu] WHERE [Xuat_Xu] = ? AND [ID_Xuat_Xu] <> ?";
        String updateSql = "UPDATE [dbo].[XuatXu] SET [Xuat_Xu] = ? WHERE [ID_Xuat_Xu] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của xuất xứ khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, xuatXu);
            checkStmt.setObject(2, id);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu xuất xứ đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Xuất xứ đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Nếu không tồn tại, thực hiện cập nhật
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setObject(1, xuatXu);
            updateStmt.setObject(2, id);

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

    public ArrayList<XuatXu> search(String keyword) {
        ArrayList<XuatXu> result = new ArrayList<>();
        // Giả sử bạn kết nối với cơ sở dữ liệu và thực hiện câu lệnh truy vấn
        String sql = "SELECT * FROM XuatXu WHERE Xuat_Xu LIKE ? And Trang_Thai = 0";

        try (Connection conn = Dbconnect.getConnection();PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                XuatXu xx = new XuatXu();
                xx.setID_Xuat_Xu(rs.getInt("ID_Xuat_Xu"));
                xx.setXuat_Xu(rs.getString("Xuat_Xu"));
                // Các trường khác nếu có
                result.add(xx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.Ram;
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
public class RamRepository {

    public ArrayList<Ram> getAll() {
        String sql = "SELECT [ID_Ram], [Ram] FROM [dbo].[Ram] WHERE [Trang_Thai] = 0";

        ArrayList<Ram> arrRam = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                int ID_Ram = rs.getInt("ID_Ram");
                String ram = rs.getString("Ram");

                // Tạo đối tượng Ram
                Ram r = new Ram(ID_Ram, ram);
                arrRam.add(r); // Thêm vào danh sách
            }

            return arrRam; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrRam; // Trả về danh sách rỗng nếu có lỗi
    }

    // Lấy tên RAM từ cơ sở dữ liệu
    public ArrayList<Ram> getName() {
        String sql = "SELECT [Ram] FROM [dbo].[Ram] WHERE [Trang_Thai] = 0";

        ArrayList<Ram> arrRam = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Ram
                Ram r = new Ram();
                r.setRam(rs.getString("Ram"));
                arrRam.add(r); // Thêm vào danh sách
            }

            return arrRam; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrRam; // Trả về danh sách rỗng nếu có lỗi
    }

    // Xóa RAM bằng mã
    public boolean delete(String maRam) {
        String sql = "UPDATE [dbo].[Ram] SET [Trang_Thai] = 1 WHERE [ID_Ram] = ?";

        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, maRam);

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

    // Thêm RAM mới vào cơ sở dữ liệu
    public boolean insertInto(String ram) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[Ram] WHERE [Ram] = ?";
        String insertSql = "INSERT INTO [dbo].[Ram] ([Ram], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của RAM
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ram);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, ram);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu RAM đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    // Cập nhật RAM
    public boolean update(int id, String ram) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[Ram] WHERE [Ram] = ? AND [ID_Ram] <> ?";
        String updateSql = "UPDATE [dbo].[Ram] SET [Ram] = ? WHERE [ID_Ram] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của RAM khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ram);
            checkStmt.setObject(2, id);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu RAM đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "RAM đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Nếu không tồn tại, thực hiện cập nhật
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setObject(1, ram);
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

    public ArrayList<Ram> search(String keyword) {
        ArrayList<Ram> result = new ArrayList<>();
        String sql = "SELECT * FROM Ram WHERE Ram LIKE ? AND Trang_Thai = 0";  // Thêm điều kiện Trang_Thai = 0

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                Ram ram = new Ram();
                ram.setID_Ram(rs.getInt("ID_Ram"));
                ram.setRam(rs.getString("Ram"));
                result.add(ram);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.KichThuoc;
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
public class KichThuocRepository {

    public ArrayList<KichThuoc> getAll() {
        String sql = "SELECT [ID_Kich_Thuoc], [Kich_Thuoc] "
                + "FROM [dbo].[KichThuoc] WHERE [Trang_Thai] = 0 ";

        ArrayList<KichThuoc> arrKichThuoc = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID_Kich_Thuoc = rs.getInt("ID_Kich_Thuoc");
                String kichThuoc = rs.getString("Kich_Thuoc");
                // Tạo đối tượng KichThuoc
                KichThuoc kt = new KichThuoc(ID_Kich_Thuoc, kichThuoc);
                arrKichThuoc.add(kt); // Thêm vào danh sách
            }

            return arrKichThuoc; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrKichThuoc; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<KichThuoc> getName() {
        String sql = "select Kich_Thuoc from KichThuoc WHERE Trang_Thai = 0";

        ArrayList<KichThuoc> arrKichThuoc = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                KichThuoc r = new KichThuoc();
                r.setKich_Thuoc(rs.getString("Kich_Thuoc"));
                arrKichThuoc.add(r); // Thêm vào danh sách
            }

            return arrKichThuoc; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrKichThuoc; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean delete(String ma) {
        String sql = "UPDATE [dbo].[KichThuoc] SET [Trang_Thai] = 1 WHERE [Kich_Thuoc] = ?";

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

    public boolean insertinto(String maKichThuoc) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[KichThuoc] WHERE [Kich_Thuoc] = ?";
        String insertSql = "INSERT INTO [dbo].[KichThuoc] ([Kich_Thuoc], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của maKichThuoc
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, maKichThuoc);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, maKichThuoc);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu mã KichThuoc đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean update(int ID, String ma) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[KichThuoc] WHERE [Kich_Thuoc] = ? AND [ID_Kich_Thuoc] <> ?";
        String updateSql = "UPDATE [dbo].[KichThuoc] SET [Kich_Thuoc] = ? WHERE [ID_Kich_Thuoc] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của mã KichThuoc khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ma);
            checkStmt.setObject(2, ID);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu mã KichThuoc đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã Kích Thước đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public ArrayList<KichThuoc> search(String keyword) {
        ArrayList<KichThuoc> result = new ArrayList<>();
        String sql = "SELECT * FROM KichThuoc WHERE Kich_Thuoc LIKE ? AND Trang_Thai = 0";  // Thêm điều kiện Trang_Thai = 0

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                KichThuoc kt = new KichThuoc();
                kt.setID_Kich_Thuoc(rs.getInt("ID_Kich_Thuoc"));
                kt.setKich_Thuoc(rs.getString("Kich_Thuoc"));
                result.add(kt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }

}

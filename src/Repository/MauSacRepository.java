/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.Imei;
import Model.KichThuoc;
import Model.MauSac;
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
public class MauSacRepository {

    public ArrayList<MauSac> getAll() {
        String sql = "SELECT [ID_Mau_Sac], [Mau_Sac] "
                + "FROM [dbo].[MauSac] WHERE [Trang_Thai] = 0 ";

        ArrayList<MauSac> arrMauSac = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID_Mau_Sac = rs.getInt("ID_Mau_Sac");
                String mauSac = rs.getString("Mau_Sac");
                // Tạo đối tượng MauSac
                MauSac ms = new MauSac(ID_Mau_Sac, mauSac);
                arrMauSac.add(ms); // Thêm vào danh sách
            }

            return arrMauSac; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrMauSac; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<MauSac> getName() {
        String sql = "SELECT Mau_Sac FROM MauSac WHERE Trang_Thai = 0";

        ArrayList<MauSac> arrMauSac = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng MauSac
                MauSac r = new MauSac();
                r.setMau_Sac(rs.getString("Mau_Sac"));
                arrMauSac.add(r); // Thêm vào danh sách
            }

            return arrMauSac; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrMauSac; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean delete(String ma) {
        String sql = "UPDATE [dbo].[MauSac] SET [Trang_Thai] = 1 WHERE [Mau_Sac] = ?";

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

    public boolean insertinto(String maMauSac) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[MauSac] WHERE [Mau_Sac] = ?";
        String insertSql = "INSERT INTO [dbo].[MauSac] ([Mau_Sac], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của maMauSac
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, maMauSac);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, maMauSac);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu mã MauSac đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean update(int ID, String ma) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[MauSac] WHERE [Mau_Sac] = ? AND [ID_Mau_Sac] <> ?";
        String updateSql = "UPDATE [dbo].[MauSac] SET [Mau_Sac] = ? WHERE [ID_Mau_Sac] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của mã MauSac khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ma);
            checkStmt.setObject(2, ID);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu mã MauSac đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã Màu Sắc đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public ArrayList<MauSac> search(String keyword) {
        ArrayList<MauSac> result = new ArrayList<>();
        String sql = "SELECT * FROM MauSac WHERE Mau_Sac LIKE ? And Trang_Thai = 0";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                MauSac ms = new MauSac();
                ms.setID_Mau_Sac(rs.getInt("ID_Mau_Sac"));
                ms.setMau_Sac(rs.getString("Mau_Sac"));
                result.add(ms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.Rom;
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
public class RomRepository {

    public ArrayList<Rom> getAll() {
        String sql = "SELECT [ID_Rom], [Rom] "
                + "FROM [dbo].[Rom] WHERE [Trang_Thai] = 0 ";

        ArrayList<Rom> arrRom = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                int ID_Rom = rs.getInt("ID_Rom");
                String rom = rs.getString("Rom");

                // Tạo đối tượng Rom
                Rom r = new Rom(ID_Rom, rom);
                arrRom.add(r); // Thêm vào danh sách
            }

            return arrRom; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrRom; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<Rom> getName() {
        String sql = "SELECT rom FROM rom WHERE Trang_Thai = 0";

        ArrayList<Rom> arrRom = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                Rom r = new Rom();
                r.setRom(rs.getString("rom"));
                arrRom.add(r); // Thêm vào danh sách
            }

            return arrRom; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrRom; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean delete(String ma) {
        String sql = "UPDATE [dbo].[Rom]\n"
                + "   SET [Trang_Thai] = 1\n"
                + " WHERE [Rom] = ?";

        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, ma);

            // Thực hiện UPDATE và lấy số hàng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();  // Sử dụng executeUpdate()

            // Đóng PreparedStatement và Connection
            ps.close();
            conn.close();

            return rowsAffected > 0;  // Trả về true nếu có hàng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Nếu có lỗi xảy ra
        }
    }

    public boolean insertinto(String maRom) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[Rom] WHERE [Rom] = ?";
        String insertSql = "INSERT INTO [dbo].[Rom] ([Rom], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của maRom
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, maRom);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) {  // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, maRom);
                int rowsAffected = insertStmt.executeUpdate();  // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0;  // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false;  // Nếu mã ROM đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Nếu có lỗi xảy ra
        }
    }

    public boolean update(int ID, String ma) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[Rom] WHERE [Rom] = ? AND [ID_Rom] <> ?";
        String updateSql = "UPDATE [dbo].[Rom] SET [Rom] = ? WHERE [ID_Rom] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của mã ROM khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ma);
            checkStmt.setObject(2, ID);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu mã ROM đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã ROM đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Nếu không tồn tại, thực hiện cập nhật
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setObject(1, ma);
            updateStmt.setObject(2, ID);

            // Thực hiện UPDATE và lấy số hàng bị ảnh hưởng
            int rowsAffected = updateStmt.executeUpdate();  // Sử dụng executeUpdate()

            return rowsAffected > 0;  // Trả về true nếu có hàng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Nếu có lỗi xảy ra
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

    public ArrayList<Rom> search(String keyword) {
        ArrayList<Rom> result = new ArrayList<>();
        String sql = "SELECT * FROM Rom WHERE Rom LIKE ? And Trang_Thai = 0";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                Rom rom = new Rom();
                rom.setID_Rom(rs.getInt("ID_Rom"));
                rom.setRom(rs.getString("Rom"));
                result.add(rom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.CPU;
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
public class CPURepository {

    public ArrayList<CPU> getAll() {
        String sql = "SELECT [ID_CPU], [CPU] "
                + "FROM [dbo].[CPU] WHERE Trang_Thai = 0 ";

        ArrayList<CPU> arrCPU = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                int ID_CPU = rs.getInt("ID_CPU");
                String tenCPU = rs.getString("CPU");

                // Tạo đối tượng CPU
                CPU cpu = new CPU(ID_CPU, tenCPU);
                arrCPU.add(cpu); // Thêm vào danh sách
            }

            return arrCPU; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrCPU; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<CPU> getName() {
        String sql = "select CPU from CPU WHERE Trang_Thai = 0";

        ArrayList<CPU> arrRom = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                CPU r = new CPU();
                r.setCPU(rs.getString("CPU"));
                arrRom.add(r); // Thêm vào danh sách
            }

            return arrRom; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrRom; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean delete(String ma) {
        String sql = "UPDATE [dbo].[CPU] SET [Trang_Thai] = 1 WHERE [CPU] = ?";

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

    public boolean insertinto(String maCPU) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[CPU] WHERE [CPU] = ?";
        String insertSql = "INSERT INTO [dbo].[CPU] ([CPU], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của maCPU
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, maCPU);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, maCPU);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu mã CPU đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean update(int ID, String ma) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[CPU] WHERE [CPU] = ? AND [ID_CPU] <> ?";
        String updateSql = "UPDATE [dbo].[CPU] SET [CPU] = ? WHERE [ID_CPU] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của mã CPU khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ma);
            checkStmt.setObject(2, ID);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu mã CPU đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã CPU đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public ArrayList<CPU> search(String keyword) {
        ArrayList<CPU> result = new ArrayList<>();
        String sql = "SELECT * FROM CPU WHERE CPU LIKE ? AND Trang_Thai = 0";  // Thêm điều kiện Trang_Thai = 0

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                CPU cpu = new CPU();
                cpu.setID_CPU(rs.getInt("ID_CPU"));
                cpu.setCPU(rs.getString("CPU"));
                result.add(cpu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }
}

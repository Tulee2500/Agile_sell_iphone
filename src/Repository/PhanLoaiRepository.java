/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.PhanLoai;
import Model.SanPhamBH;
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
public class PhanLoaiRepository {

    public ArrayList<PhanLoai> getAll() {
        String sql = "SELECT [ID_Phan_Loai], [Phan_Loai] "
                + "FROM [dbo].[PhanLoai] WHERE [Trang_Thai] = 0 ";

        ArrayList<PhanLoai> arrPhanLoai = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                int ID_Phan_Loai = rs.getInt("ID_Phan_Loai");
                String phanLoai = rs.getString("Phan_Loai");

                // Tạo đối tượng PhanLoai
                PhanLoai pl = new PhanLoai(ID_Phan_Loai, phanLoai);
                arrPhanLoai.add(pl); // Thêm vào danh sách
            }

            return arrPhanLoai; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrPhanLoai; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<PhanLoai> getName() {
        String sql = "select Phan_Loai from PhanLoai WHERE Trang_Thai = 0";

        ArrayList<PhanLoai> arrPhanLoai = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                PhanLoai r = new PhanLoai();
                r.setPhan_Loai(rs.getString("Phan_Loai"));
                arrPhanLoai.add(r); // Thêm vào danh sách
            }

            return arrPhanLoai; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrPhanLoai; // Trả về danh sách rỗng nếu có lỗi
    }

    public List<SanPhamBH> searchSanPham(String phanLoai) {
        List<SanPhamBH> products = new ArrayList<>();
        String sql;

        // Kiểm tra nếu phân loại là "All"
        if (phanLoai.equalsIgnoreCase("All")) {
            sql = "SELECT sp.ID_San_Pham, sp.Ten_San_Pham, sp.So_Luong, sp.Gia_Ban "
                    + "FROM SanPham sp "
                    + "JOIN PhanLoai pl ON sp.ID_Phan_Loai = pl.ID_Phan_Loai "
                    + "WHERE sp.Trang_Thai = 0"; // chỉ lấy sản phẩm còn hàng
        } else {
            // Tìm kiếm theo tên phân loại
            sql = "SELECT sp.ID_San_Pham, sp.Ten_San_Pham, sp.So_Luong, sp.Gia_Ban "
                    + "FROM SanPham sp "
                    + "JOIN PhanLoai pl ON sp.ID_Phan_Loai = pl.ID_Phan_Loai "
                    + "WHERE pl.Phan_Loai LIKE ? AND sp.Trang_Thai = 0"; // tìm theo tên phân loại
        }

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (!phanLoai.equalsIgnoreCase("All")) {
                pstmt.setString(1, "%" + phanLoai + "%"); // gán giá trị cho tên phân loại
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SanPhamBH sp = new SanPhamBH();
                sp.setIdSanPham(rs.getInt("ID_San_Pham"));
                sp.setTenSanPham(rs.getString("Ten_San_Pham"));
                sp.setSoLuong(rs.getInt("So_Luong"));
                sp.setGiaBan(rs.getFloat("Gia_Ban"));
                products.add(sp); // thêm sản phẩm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products; // trả về danh sách sản phẩm tìm được
    }

    public boolean delete(String ma) {
        String sql = "UPDATE [dbo].[PhanLoai] SET [Trang_Thai] = 1 WHERE [Phan_Loai] = ?";

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

    public boolean insertinto(String maPhanLoai) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[PhanLoai] WHERE [Phan_Loai] = ?";
        String insertSql = "INSERT INTO [dbo].[PhanLoai] ([Phan_Loai], [Trang_Thai]) VALUES (?, 0)";

        try {
            Connection conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của maPhanLoai
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, maPhanLoai);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu không tồn tại, thực hiện INSERT
            if (rs.next() && rs.getInt(1) == 0) { // Nếu COUNT(*) = 0
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setObject(1, maPhanLoai);
                int rowsAffected = insertStmt.executeUpdate(); // Thực hiện INSERT

                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return rowsAffected > 0; // Trả về true nếu có hàng bị ảnh hưởng
            } else {
                // Đóng ResultSet và PreparedStatement
                rs.close();
                checkStmt.close();
                return false; // Nếu mã PhanLoai đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean update(int ID, String ma) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[PhanLoai] WHERE [Phan_Loai] = ? AND [ID_Phan_Loai] <> ?";
        String updateSql = "UPDATE [dbo].[PhanLoai] SET [Phan_Loai] = ? WHERE [ID_Phan_Loai] = ?";

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Dbconnect.getConnection();

            // Kiểm tra sự tồn tại của mã PhanLoai khác với ID hiện tại
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setObject(1, ma);
            checkStmt.setObject(2, ID);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu mã PhanLoai đã tồn tại
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã Phân Loại đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public ArrayList<PhanLoai> search(String keyword) {
        ArrayList<PhanLoai> result = new ArrayList<>();
        String sql = "SELECT * FROM PhanLoai WHERE Phan_Loai LIKE ? AND Trang_Thai = 0";  // Thêm điều kiện Trang_Thai = 0

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Sử dụng toán tử LIKE để tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Xử lý kết quả trả về và thêm vào danh sách result
                PhanLoai pl = new PhanLoai();
                pl.setID_Phan_Loai(rs.getInt("ID_Phan_Loai"));
                pl.setPhan_Loai(rs.getString("Phan_Loai"));                
                result.add(pl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  // Trả về danh sách kết quả tìm kiếm
    }

}

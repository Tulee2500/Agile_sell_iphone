/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.Imei;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author nothank
 */
public class ImeiRepository {

    public ArrayList<Imei> getAll(int page, int limit) {
        String sql = "SELECT I.[ID_Imei], "
                + "       I.[Ma_Imei], "
                + "       I.Trang_Thai, "
                + "       SP.Ma_San_Pham, "
                + "       SP.Ten_San_Pham "
                + "FROM [dbo].[Imei] I "
                + "JOIN [dbo].[SanPham] SP ON I.ID_San_Pham = SP.ID_San_Pham "
                + "ORDER BY I.ID_Imei " // Thêm ORDER BY để tránh lỗi OFFSET
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<Imei> arrImei = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Dbconnect.getConnection();
            ps = conn.prepareStatement(sql);
            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                int ID_Imei = rs.getInt("ID_Imei");
                String Ma_Imei = rs.getString("Ma_Imei");
                int Trang_Thai = rs.getInt("Trang_Thai");
                String Ma_San_Pham = rs.getString("Ma_San_Pham");
                String Ten_San_Pham = rs.getString("Ten_San_Pham");

                // Tạo đối tượng Imei
                Imei imei = new Imei(ID_Imei, Ma_Imei, Trang_Thai, Ma_San_Pham, Ten_San_Pham);
                arrImei.add(imei); // Thêm đối tượng vào danh sách
            }

            return arrImei; // Trả về danh sách sau khi truy vấn kết thúc

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Imei> SearchTen(int page, int limit, String productName) {
        String sql = "SELECT I.[ID_Imei], "
                + "       I.[Ma_Imei], "
                + "       I.Trang_Thai, "
                + "       SP.Ma_San_Pham, "
                + "       SP.Ten_San_Pham "
                + "FROM [dbo].[Imei] I "
                + "JOIN [dbo].[SanPham] SP ON I.ID_San_Pham = SP.ID_San_Pham "
                + "WHERE SP.Ten_San_Pham LIKE ? " // Thêm điều kiện tìm kiếm
                + "ORDER BY I.ID_Imei "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<Imei> arrImei = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Dbconnect.getConnection();
            ps = conn.prepareStatement(sql);

            // Thiết lập tham số cho tìm kiếm tên sản phẩm
            ps.setString(1, "%" + productName + "%"); // Sử dụng LIKE với wildcard

            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(2, offset);
            ps.setInt(3, limit);

            rs = ps.executeQuery();

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                int ID_Imei = rs.getInt("ID_Imei");
                String Ma_Imei = rs.getString("Ma_Imei");
                int Trang_Thai = rs.getInt("Trang_Thai");
                String Ma_San_Pham = rs.getString("Ma_San_Pham");
                String Ten_San_Pham = rs.getString("Ten_San_Pham");

                // Tạo đối tượng Imei
                Imei imei = new Imei(ID_Imei, Ma_Imei, Trang_Thai, Ma_San_Pham, Ten_San_Pham);
                arrImei.add(imei); // Thêm đối tượng vào danh sách
            }

            return arrImei; // Trả về danh sách sau khi truy vấn kết thúc

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Imei> getNames() {
        String sql = "SELECT DISTINCT SP.Ten_San_Pham "
                + "FROM Imei I "
                + "JOIN SanPham SP ON I.ID_San_Pham = SP.ID_San_Pham";

        ArrayList<Imei> arrImei = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery(); // Thực hiện truy vấn

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Imei
                Imei imei = new Imei();
                imei.setTen_San_Pham(rs.getString("Ten_San_Pham")); // Lấy Ten_San_Pham từ bảng SanPham
                arrImei.add(imei);  // Thêm đối tượng Imei vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrImei;
    }

    public ArrayList<Imei> getName(String Ma_San_Pham) {
        String sql = "SELECT Ma_Imei FROM Imei WHERE ID_San_Pham = (Select ID_San_Pham from SanPham where Ma_San_Pham = ?) And Trang_Thai = 1";

        ArrayList<Imei> arrImei = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Ma_San_Pham); // Sử dụng setString để gán giá trị
            ResultSet rs = ps.executeQuery(); // Thực hiện truy vấn

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Imei
                Imei imei = new Imei();
                imei.setMa_Imei(rs.getString("Ma_Imei")); // Sửa tên cột để lấy đúng giá trị
                imei.setTrang_thai(1); // Giả sử trạng thái là 1
                arrImei.add(imei);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrImei;
    }

    public ArrayList<Imei> getTT() {
        String sql = "SELECT Trang_Thai FROM Imei";

        ArrayList<Imei> arrImei = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Imei
                Imei imei = new Imei();
                int Trang_Thai = rs.getInt("Trang_Thai");
                arrImei.add(imei);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrImei;
    }

    public ArrayList<Imei> Search(int page, int limit, String keyword) {
        String sql = "SELECT I.[ID_Imei], "
                + "       I.[Ma_Imei], "
                + "       I.Trang_Thai, "
                + "       SP.Ma_San_Pham, "
                + "       SP.Ten_San_Pham "
                + "FROM [dbo].[Imei] I "
                + "JOIN [dbo].[SanPham] SP ON I.ID_San_Pham = SP.ID_San_Pham "
                + "WHERE I.Ma_Imei LIKE ? OR SP.Ma_San_Pham LIKE ? OR SP.Ten_San_Pham LIKE ? "
                + "ORDER BY I.ID_Imei "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<Imei> arrImei = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Dbconnect.getConnection();
            ps = conn.prepareStatement(sql);

            // Tính toán offset
            int offset = (page - 1) * limit;

            // Thiết lập các tham số cho truy vấn
            ps.setString(1, "%" + keyword + "%"); // Tìm kiếm theo Ma_Imei
            ps.setString(2, "%" + keyword + "%"); // Tìm kiếm theo Ma_San_Pham
            ps.setString(3, "%" + keyword + "%"); // Tìm kiếm theo Ten_San_Pham
            ps.setInt(4, offset); // Offset
            ps.setInt(5, limit); // Limit

            rs = ps.executeQuery();

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                int ID_Imei = rs.getInt("ID_Imei");
                String Ma_Imei = rs.getString("Ma_Imei");
                int Trang_Thai = rs.getInt("Trang_Thai");
                String Ma_San_Pham = rs.getString("Ma_San_Pham");
                String Ten_San_Pham = rs.getString("Ten_San_Pham");

                // Tạo đối tượng Imei
                Imei imei = new Imei(ID_Imei, Ma_Imei, Trang_Thai, Ma_San_Pham, Ten_San_Pham);
                arrImei.add(imei); // Thêm đối tượng vào danh sách
            }

            return arrImei; // Trả về danh sách sau khi truy vấn kết thúc

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

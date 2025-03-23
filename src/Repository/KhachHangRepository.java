/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.KhachHang_Model;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class KhachHangRepository {

    private Connection conn;

    public KhachHangRepository() {
        try {
            this.conn = Dbconnect.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KhachHang_Model> fillAll() {
        ArrayList<KhachHang_Model> listKH = new ArrayList<>();
        String sql = "select * from KhachHang";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idKhachHang = rs.getInt("ID_Khach_Hang");
                String MaKhachHang = rs.getString("Ma_Khach_Hang");
                String TenKH = rs.getString("Ten");
                String SDT = rs.getString("SĐT");
                String Email = rs.getString("Email");
                boolean GioiTinh = rs.getBoolean("Gioi_Tinh");
                String DiaChi = rs.getString("Dia_Chi");
                KhachHang_Model KHM = new KhachHang_Model(idKhachHang, MaKhachHang, TenKH, SDT, Email, GioiTinh, DiaChi);
                listKH.add(KHM);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listKH;
    }
     public void insert(KhachHang_Model kh) {
        String generatedMaKH = generateMaKhachHang(); // Generate MaKH
        String sql = "INSERT INTO KhachHang (Ma_Khach_Hang, Ten, SĐT, Email, Gioi_Tinh, Dia_Chi) VALUES (?, ?, ?, ?, ?, ?)";
            try {
        PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, generatedMaKH); // Use generated MaKH
            ps.setString(2, kh.getTenKH());
            ps.setString(3, kh.getSDT());
            ps.setString(4, kh.getEmail());
            ps.setBoolean(5, kh.isGioiTinh());
            ps.setString(6, kh.getDiaChi());

            ps.executeUpdate(); // Use executeUpdate for insert statement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateMaKhachHang() {
    String sql = "SELECT MAX(CAST(SUBSTRING(Ma_Khach_Hang, 3, LEN(Ma_Khach_Hang)) AS INT)) AS maxMaKH FROM KhachHang";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int maxMaKH = rs.getInt("maxMaKH");
            return "KH00" + (maxMaKH + 1); // Tăng và nối với "KH"
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "KH1"; // Mặc định là KH1 nếu không có khách hàng nào
}

    public void update(KhachHang_Model kh) {
        String sql = "update KhachHang set Ma_Khach_Hang =?, Ten =?, SĐT = ?, Email = ?, Gioi_Tinh = ?, Dia_Chi = ? WHERE ID_Khach_Hang = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.setString(3, kh.getSDT());
            ps.setString(4, kh.getEmail());
            ps.setBoolean(5, kh.isGioiTinh());
            ps.setString(6, kh.getDiaChi());
            ps.setInt(7, kh.getIdKH());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMaKhachHangExists(String maKH) {
        String sql = "SELECT COUNT(*) FROM KhachHang WHERE Ma_Khach_Hang = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Không tìm thấy mã khách hàng
    }

    public ArrayList<KhachHang_Model> search(String keyword) {
        ArrayList<KhachHang_Model> result = new ArrayList<>();
        // Kết nối đến cơ sở dữ liệu và thực hiện truy vấn SQL
        String sql = "SELECT * FROM KhachHang WHERE Ma_Khach_Hang LIKE ? OR Ten LIKE ? OR SĐT LIKE ? OR Email LIKE ?";
        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setString(3, searchKeyword);
            ps.setString(4, searchKeyword);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Giả sử bạn đã có phương thức tạo đối tượng từ ResultSet
                KhachHang_Model kh = new KhachHang_Model(
                        rs.getInt("ID_Khach_Hang"),
                        rs.getString("Ma_Khach_Hang"),
                        rs.getString("Ten"),
                        rs.getString("SĐT"),
                        rs.getString("Email"),
                        rs.getBoolean("Gioi_Tinh"),
                        rs.getString("Dia_Chi")
                );
                result.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean isSoDienThoaiExists(String sdt) {
        String sql = "SELECT COUNT(*) FROM KhachHang WHERE SĐT = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM KhachHang WHERE Email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
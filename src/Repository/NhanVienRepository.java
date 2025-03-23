/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import java.util.ArrayList;
import Model.NhanVienModel;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Model.NhanVienModel;
import java.sql.SQLException;

public class NhanVienRepository {
    public ArrayList<NhanVienModel> getAll() {
        ArrayList<NhanVienModel> list = new ArrayList<>();
        String sql = "select * from NhanVien where Trang_Thai = 0";
        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                NhanVienModel rp = new NhanVienModel();
                rp.setID(rs.getInt(1));
                rp.setMaNV(rs.getString(2));
                rp.setTaiKhoan(rs.getString(3));
                rp.setMatKhau(rs.getString(4));
                rp.setHoTen(rs.getString(5));
                rp.setGioiTinh(rs.getBoolean(6));
                rp.setNgaySinh(rs.getDate(7));
                rp.setDiaChi(rs.getString(8));
                rp.setSDT(rs.getString(9));
                rp.setHinhanh(rs.getString(10));
                rp.setEmail(rs.getString(11));
                rp.setTrangThai(rs.getInt(12));
                rp.setChucVu(rs.getString(13));
                rp.setNgayBatDau(rs.getDate(14));
                list.add(rp);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };
    public ArrayList<NhanVienModel> getAll1() {
        ArrayList<NhanVienModel> list = new ArrayList<>();
        String sql = "select * from NhanVien where Trang_Thai = 1";
        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                NhanVienModel rp = new NhanVienModel();
                rp.setID(rs.getInt(1));
                rp.setMaNV(rs.getString(2));
                rp.setTaiKhoan(rs.getString(3));
                rp.setMatKhau(rs.getString(4));
                rp.setHoTen(rs.getString(5));
                rp.setGioiTinh(rs.getBoolean(6));
                rp.setNgaySinh(rs.getDate(7));
                rp.setDiaChi(rs.getString(8));
                rp.setSDT(rs.getString(9));
                rp.setHinhanh(rs.getString(10));
                rp.setEmail(rs.getString(11));
                rp.setTrangThai(rs.getInt(12));
                rp.setChucVu(rs.getString(13));
                rp.setNgayBatDau(rs.getDate(14));
                list.add(rp);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };
    public String generateMaNV() {
        String sql = "SELECT MAX(Ma_NV) FROM NhanVien";
        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String lastMaNV = rs.getString(1); 
                if (lastMaNV != null) {
                    int newMaNV = Integer.parseInt(lastMaNV.substring(2)) + 1;  
                    return "NV" + String.format("%03d", newMaNV); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NV001";
    }

    public int Them(NhanVienModel x) {
        x.setMaNV(generateMaNV());

        String sql = "insert into NhanVien(Ma_NV,Tai_Khoan,Mat_Khau,Ho_Ten,Gioi_Tinh,Ngay_Sinh,Dia_Chi,SDT,Hinh_Anh,Email,Trang_Thai,Chuc_Vu,Ngay_Bat_Dau) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, x.getMaNV()); 
            ps.setObject(2, x.getTaiKhoan());
            ps.setObject(3, x.getMatKhau());
            ps.setObject(4, x.getHoTen());
            ps.setObject(5, x.isGioiTinh());
            ps.setObject(6, x.getNgaySinh());
            ps.setObject(7, x.getDiaChi());
            ps.setObject(8, x.getSDT());
            ps.setObject(9, x.getHinhanh());
            ps.setObject(10, x.getEmail());
            ps.setObject(11, x.getTrangThai());
            ps.setObject(12, x.getChucVu());
            ps.setObject(13, x.getNgayBatDau());
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    } 

    public int Sua(String maNV, NhanVienModel nv) {
    if (nv == null) {
        return 0;  // Nếu đối tượng null, không thực hiện sửa
    }

    // Thực hiện cập nhật vào database
    String sql = "UPDATE NhanVien SET Tai_Khoan = ?, Mat_Khau = ?, Ho_Ten = ?, Gioi_Tinh = ?, Ngay_Sinh = ?, Dia_Chi = ?, SDT = ?, Hinh_Anh = ?, Email = ?, Trang_Thai = ?, Chuc_Vu = ?, Ngay_Bat_Dau = ? WHERE Ma_NV = ?";
    
    try (Connection con = Dbconnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nv.getTaiKhoan());
        ps.setString(2, nv.getMatKhau());
        ps.setString(3, nv.getHoTen());
        ps.setBoolean(4, nv.isGioiTinh());
        ps.setDate(5, new java.sql.Date(nv.getNgaySinh().getTime()));
        ps.setString(6, nv.getDiaChi());
        ps.setString(7, nv.getSDT());
        ps.setString(8, nv.getHinhanh());
        ps.setString(9, nv.getEmail());
        ps.setInt(10, nv.getTrangThai());
        ps.setString(11, nv.getChucVu());
        ps.setDate(12, new java.sql.Date(nv.getNgayBatDau().getTime()));
        ps.setString(13, maNV);

        return ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;
    }

    }
    
    public ArrayList<NhanVienModel> timKiem(String keyword) {
        ArrayList<NhanVienModel> list = new ArrayList<>();

        // Câu SQL với LIKE trên nhiều trường
        String sql = "SELECT * FROM NhanVien WHERE Trang_Thai = 0 AND ("
                + "Ma_NV LIKE ? OR "
                + "Tai_Khoan LIKE ? OR "
                + "Ho_Ten LIKE ? OR "
                + "Dia_Chi LIKE ? OR "
                + "SDT LIKE ? OR "
                + "Email LIKE ? ) ";
                
        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            // Sử dụng toán tử LIKE cho tất cả các cột cần tìm kiếm
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, searchKeyword);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienModel rp = new NhanVienModel();
                rp.setID(rs.getInt(1));
                rp.setMaNV(rs.getString(2));
                rp.setTaiKhoan(rs.getString(3));
                rp.setMatKhau(rs.getString(4));
                rp.setHoTen(rs.getString(5));
                rp.setGioiTinh(rs.getBoolean(6));
                rp.setNgaySinh(rs.getDate(7));
                rp.setDiaChi(rs.getString(8));
                rp.setSDT(rs.getString(9));
                rp.setHinhanh(rs.getString(10));
                rp.setEmail(rs.getString(11));
                rp.setTrangThai(rs.getInt(12));
                rp.setChucVu(rs.getString(13));
                rp.setNgayBatDau(rs.getDate(14));
                list.add(rp);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<NhanVienModel> LocChucVu( String chucvu) {
        ArrayList<NhanVienModel> list = new ArrayList<>();
        String sql = "select * from NhanVien where Trang_Thai = 0 and Chuc_Vu like ? ";
        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            // Sử dụng toán tử LIKE và thêm dấu % vào câu truy vấn
            ps.setString(1, "%"+chucvu + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienModel rp = new NhanVienModel();
                rp.setID(rs.getInt(1));
                rp.setMaNV(rs.getString(2));
                rp.setTaiKhoan(rs.getString(3));
                rp.setMatKhau(rs.getString(4));
                rp.setHoTen(rs.getString(5));
                rp.setGioiTinh(rs.getBoolean(6));
                rp.setNgaySinh(rs.getDate(7));
                rp.setDiaChi(rs.getString(8));
                rp.setSDT(rs.getString(9));
                rp.setHinhanh(rs.getString(10));
                rp.setEmail(rs.getString(11));
                rp.setTrangThai(rs.getInt(12));
                rp.setChucVu(rs.getString(13));
                rp.setNgayBatDau(rs.getDate(14));
                list.add(rp);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public ArrayList<NhanVienModel> LocGioiTinh(boolean gioiTinh) {
        ArrayList<NhanVienModel> list = new ArrayList<>();
        String sql = "select * from NhanVien where Trang_Thai = 0 and Gioi_Tinh = ?";
        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1,gioiTinh);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                NhanVienModel rp = new NhanVienModel();
                rp.setID(rs.getInt(1));
                rp.setMaNV(rs.getString(2));
                rp.setTaiKhoan(rs.getString(3));
                rp.setMatKhau(rs.getString(4));
                rp.setHoTen(rs.getString(5));
                rp.setGioiTinh(rs.getBoolean(6));
                rp.setNgaySinh(rs.getDate(7));
                rp.setDiaChi(rs.getString(8));
                rp.setSDT(rs.getString(9));
                rp.setHinhanh(rs.getString(10));
                rp.setEmail(rs.getString(11));
                rp.setTrangThai(rs.getInt(12));
                rp.setChucVu(rs.getString(13));
                rp.setNgayBatDau(rs.getDate(14));
                list.add(rp);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };
    public ArrayList<NhanVienModel> LocGioiTinhVaChucVu(boolean gioiTinh, String chucvu) {
        ArrayList<NhanVienModel> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE Trang_Thai = 0 AND Gioi_Tinh = ? AND Chuc_Vu LIKE ?";

        try {
            Connection con = Dbconnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, gioiTinh);
            ps.setString(2, "%" + chucvu + "%"); // Use LIKE for Chuc_Vu to allow partial matching

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienModel rp = new NhanVienModel();
                rp.setID(rs.getInt(1));
                rp.setMaNV(rs.getString(2));
                rp.setTaiKhoan(rs.getString(3));
                rp.setMatKhau(rs.getString(4));
                rp.setHoTen(rs.getString(5));
                rp.setGioiTinh(rs.getBoolean(6));
                rp.setNgaySinh(rs.getDate(7));
                rp.setDiaChi(rs.getString(8));
                rp.setSDT(rs.getString(9));
                rp.setHinhanh(rs.getString(10));
                rp.setEmail(rs.getString(11));
                rp.setTrangThai(rs.getInt(12));
                rp.setChucVu(rs.getString(13));
                rp.setNgayBatDau(rs.getDate(14));
                list.add(rp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public int Xoa(String maNV) {
    // Câu lệnh SQL cập nhật trạng thái từ 0 (đang làm) sang 1 (nghỉ làm)
    String sql = "UPDATE NhanVien SET Trang_Thai = 1 WHERE Ma_NV = ?";

    try (Connection con = Dbconnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        // Thiết lập mã nhân viên để cập nhật
        ps.setString(1, maNV);

        // Thực hiện câu lệnh update và trả về số dòng bị ảnh hưởng
        return ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;  // Trả về 0 nếu có lỗi xảy ra
    }
    
}
    public int KhoiPhuc(String maNV) {
    // Câu lệnh SQL cập nhật trạng thái từ 1 (nghỉ làm) sang 0 (đang làm)
    String sql = "UPDATE NhanVien SET Trang_Thai = 0 WHERE Ma_NV = ?";

    try (Connection con = Dbconnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        // Thiết lập mã nhân viên để cập nhật
        ps.setString(1, maNV);

        // Thực hiện câu lệnh update và trả về số dòng bị ảnh hưởng
        return ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;  // Trả về 0 nếu có lỗi xảy ra
    }
}



    
    
}

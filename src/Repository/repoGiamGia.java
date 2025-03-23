/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import duan1trangchu.DBConnect.Dbconnect;
import java.sql.*;
import java.util.ArrayList;
import Model.modelGiamGia;

/**
 *
 * @author Tu PC
 */
public class repoGiamGia {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<modelGiamGia> getAll() {
    ArrayList<modelGiamGia> listGG = new ArrayList<>();
    sql = "select Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam,\n"
            + "Gia_tri_DH_Toi_Thieu, Muc_Giam_Gia, Muc_Giam_Gia_Toi_Da, Trang_Thai from GiamGia";
    try {
        con = Dbconnect.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String maGiamGia;
            String tenChuongTrinh;
            String moTa;
            java.util.Date ngayBatDau;
            java.util.Date ngayKetThuc;
            int soLuong;
            int kieuGiam;
            float giaTriDHToiThieu;
            int mucGiamGia;
            int mucGiamGiaToiDa;
            int trangThai;

            maGiamGia = rs.getString(1);
            tenChuongTrinh = rs.getString(2);
            moTa = rs.getString(3);
            ngayBatDau = rs.getDate(4);
            ngayKetThuc = rs.getDate(5);
            soLuong = rs.getInt(6);
            kieuGiam = rs.getInt(7);
            giaTriDHToiThieu = rs.getFloat(8);
            mucGiamGia = rs.getInt(9);
            mucGiamGiaToiDa = rs.getInt(10);
            trangThai = rs.getInt(11);

            // Cập nhật modelGiamGia để không còn IDNhanVien
            modelGiamGia m = new modelGiamGia(maGiamGia, tenChuongTrinh, moTa, ngayBatDau, ngayKetThuc, soLuong, kieuGiam, giaTriDHToiThieu, mucGiamGia, mucGiamGiaToiDa, trangThai);
            listGG.add(m);
        }
        return listGG;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    public int them(modelGiamGia m) {
    sql = "Insert into GiamGia(Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam, Gia_tri_DH_Toi_Thieu, "
            + "Muc_Giam_Gia, Muc_Giam_Gia_Toi_Da, Trang_Thai) values(?,?,?,?,?,?,?,?,?,?,?);";
    try {
        con = Dbconnect.getConnection();
        ps = con.prepareStatement(sql);
        // Bỏ dòng này vì không còn ID_Nhan_Vien
        // ps.setObject(1, m.getIDNhanVien());
        ps.setObject(1, m.getMaGiamGia());
        ps.setObject(2, m.getTenChuongTrinh());
        ps.setObject(3, m.getMoTa());
        ps.setObject(4, m.getNgayBatDau());
        ps.setObject(5, m.getNgayKetThuc());
        ps.setObject(6, m.getSoLuong());
        ps.setObject(7, m.getKieuGiam());
        ps.setObject(8, m.getGiaTriDHToiThieu());
        ps.setObject(9, m.getMucGiamGia());
        ps.setObject(10, m.getMucGiamGiaToiDa());
        ps.setObject(11, m.getTrangThai());
        return ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}


//    public modelGiamGia checkTrung(String maGiamGiaMoi) {
//        // tìm mã nv mới xem nó đã tồn tại trong database chưa
//        // nếu đã có thì ; lấy ra dc đối ttuowngj trùng mã
//        // nếu  k có :thì null;không trùng mã
//        sql = "select ID_Nhan_Vien,Ma_Giam_Gia,Ten_Chuong_Trinh,Mo_Ta,Ngay_Bat_Dau,Ngay_Ket_Thuc,So_luong,Kieu_Giam,\n"
//                + "Gia_tri_DH_Toi_Thieu,Muc_Giam_Gia,Muc_Giam_Gia_Toi_Da,Trang_Thai from GiamGia where Ma_Giam_Gia = ?";
//        modelGiamGia GG = null;
//        try {
//            con = DBconnect.getConnection();
//            ps = con.prepareStatement(sql);
//            ps.setObject(1, maGiamGiaMoi);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int IDNhanVien;
//                String maGiamGia;
//                String tenChuongTrinh;
//                String moTa;
//                java.util.Date ngayBatDau;
//                java.util.Date ngayKetThuc;
//                int soLuong;
//                int kieuGiam;
//                float giaTriDHToiThieu;
//                int mucGiamGia;
//                int mucGiamGiaToiDa;
//                int trangThai;
//                IDNhanVien = rs.getInt(1);
//                maGiamGia = rs.getString(2);
//                tenChuongTrinh = rs.getString(3);
//                moTa = rs.getString(4);
//                ngayBatDau = rs.getDate(5);
//                ngayKetThuc = rs.getDate(6);
//                soLuong = rs.getInt(7);
//                kieuGiam = rs.getInt(8);
//                giaTriDHToiThieu = rs.getFloat(9);
//                mucGiamGia = rs.getInt(10);
//                mucGiamGiaToiDa = rs.getInt(11);
//                trangThai = rs.getInt(12);
//                
//                GG = (new modelGiamGia(IDNhanVien, maGiamGia, tenChuongTrinh, moTa, ngayBatDau, ngayKetThuc, soLuong, kieuGiam, giaTriDHToiThieu, mucGiamGia, mucGiamGiaToiDa, trangThai));
//            }
//            return GG;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public boolean checkTrungMa(String maGiamGiaMoi) {
    sql = "select Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam, "
            + "Gia_tri_DH_Toi_Thieu, Muc_Giam_Gia, Muc_Giam_Gia_Toi_Da, Trang_Thai from GiamGia where Ma_Giam_Gia = ?";
    try (Connection con = Dbconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setObject(1, maGiamGiaMoi);
        ResultSet rs = ps.executeQuery();
        return rs.next();  // Trả về true nếu có kết quả
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;  // Trả về false nếu có lỗi xảy ra
}


    public int sua(String ma, modelGiamGia m) {
    sql = "Update GiamGia set Ten_Chuong_Trinh=?, Mo_Ta=?, Ngay_Bat_Dau=?, Ngay_Ket_Thuc=?, So_luong=?, Kieu_Giam=?, "
            + "Gia_tri_DH_Toi_Thieu=?, Muc_Giam_Gia=?, Muc_Giam_Gia_Toi_Da=?, Trang_Thai=? where Ma_Giam_Gia = ?";
    try {
        con = Dbconnect.getConnection();
        ps = con.prepareStatement(sql);

        ps.setObject(1, m.getTenChuongTrinh());
        ps.setObject(2, m.getMoTa());
        ps.setObject(3, m.getNgayBatDau());
        ps.setObject(4, m.getNgayKetThuc());
        ps.setObject(5, m.getSoLuong());
        ps.setObject(6, m.getKieuGiam());
        ps.setObject(7, m.getGiaTriDHToiThieu());
        ps.setObject(8, m.getMucGiamGia());
        ps.setObject(9, m.getMucGiamGiaToiDa());
        ps.setObject(10, m.getTrangThai());
        ps.setObject(11, ma);  // Giữ lại tham số này để xác định bản ghi cần cập nhật
        return ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}


    public int xoa(String ma) {
        sql = "delete from GiamGia where Ma_Giam_Gia =?";
        try {
            con = Dbconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<modelGiamGia> getUpcomingEvents(ArrayList<modelGiamGia> allEvents) {
        ArrayList<modelGiamGia> upcomingEvents = new ArrayList<>();
        for (modelGiamGia event : allEvents) {
            if (event.getTrangThai() == 0) { // Trạng thái = 0 là sắp diễn ra
                upcomingEvents.add(event);
            }
        }
        return upcomingEvents;
    }

    public ArrayList<modelGiamGia> getAllS() {
    ArrayList<modelGiamGia> listGG = new ArrayList<>();
    sql = "select Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam, "
            + "Gia_tri_DH_Toi_Thieu, Muc_Giam_Gia, Muc_Giam_Gia_Toi_Da, Trang_Thai from GiamGia where Trang_Thai = 0";
    try {
        con = Dbconnect.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String maGiamGia;
            String tenChuongTrinh;
            String moTa;
            java.util.Date ngayBatDau;
            java.util.Date ngayKetThuc;
            int soLuong;
            int kieuGiam;
            float giaTriDHToiThieu;
            int mucGiamGia;
            int mucGiamGiaToiDa;
            int trangThai;

            maGiamGia = rs.getString(1);
            tenChuongTrinh = rs.getString(2);
            moTa = rs.getString(3);
            ngayBatDau = rs.getDate(4);
            ngayKetThuc = rs.getDate(5);
            soLuong = rs.getInt(6);
            kieuGiam = rs.getInt(7);
            giaTriDHToiThieu = rs.getFloat(8);
            mucGiamGia = rs.getInt(9);
            mucGiamGiaToiDa = rs.getInt(10);
            trangThai = rs.getInt(11);

            modelGiamGia m = new modelGiamGia(maGiamGia, tenChuongTrinh, moTa, ngayBatDau, ngayKetThuc, soLuong, kieuGiam, giaTriDHToiThieu, mucGiamGia, mucGiamGiaToiDa, trangThai);
            listGG.add(m);
        }
        return listGG;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    public ArrayList<modelGiamGia> getOngoingEvents(ArrayList<modelGiamGia> allEvents) {
        ArrayList<modelGiamGia> ongoingEvents = new ArrayList<>();
        for (modelGiamGia event : allEvents) {
            if (event.getTrangThai() == 1) { // Trạng thái = 1 là đang diễn ra
                ongoingEvents.add(event);
            }
        }
        return ongoingEvents;
    }

    public ArrayList<modelGiamGia> getAlld() {
    ArrayList<modelGiamGia> listGG = new ArrayList<>();
    sql = "select Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam, "
            + "Gia_tri_DH_Toi_Thieu, Muc_Giam_Gia, Muc_Giam_Gia_Toi_Da, Trang_Thai from GiamGia where Trang_Thai = 1";
    try {
        con = Dbconnect.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String maGiamGia;
            String tenChuongTrinh;
            String moTa;
            java.util.Date ngayBatDau;
            java.util.Date ngayKetThuc;
            int soLuong;
            int kieuGiam;
            float giaTriDHToiThieu;
            int mucGiamGia;
            int mucGiamGiaToiDa;
            int trangThai;

            maGiamGia = rs.getString(1);
            tenChuongTrinh = rs.getString(2);
            moTa = rs.getString(3);
            ngayBatDau = rs.getDate(4);
            ngayKetThuc = rs.getDate(5);
            soLuong = rs.getInt(6);
            kieuGiam = rs.getInt(7);
            giaTriDHToiThieu = rs.getFloat(8);
            mucGiamGia = rs.getInt(9);
            mucGiamGiaToiDa = rs.getInt(10);
            trangThai = rs.getInt(11);

            modelGiamGia m = new modelGiamGia(maGiamGia, tenChuongTrinh, moTa, ngayBatDau, ngayKetThuc, soLuong, kieuGiam, giaTriDHToiThieu, mucGiamGia, mucGiamGiaToiDa, trangThai);
            listGG.add(m);
        }
        return listGG;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


   public ArrayList<modelGiamGia> searchGiamGia(String keyword) {
    ArrayList<modelGiamGia> listGG = new ArrayList<>();
    String sql = "SELECT Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam, "
               + "Gia_tri_DH_Toi_Thieu, Muc_Giam_Gia, Muc_Giam_Gia_Toi_Da, Trang_Thai "
               + "FROM GiamGia "
               + "WHERE Ma_Giam_Gia LIKE ?";

    try (Connection con = Dbconnect.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        String searchKeyword = "%" + keyword + "%";
        ps.setString(1, searchKeyword);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String maGiamGia = rs.getString("Ma_Giam_Gia");
            String tenChuongTrinh = rs.getString("Ten_Chuong_Trinh");
            String moTa = rs.getString("Mo_Ta");
            java.util.Date ngayBatDau = rs.getDate("Ngay_Bat_Dau");
            java.util.Date ngayKetThuc = rs.getDate("Ngay_Ket_Thuc");
            int soLuong = rs.getInt("So_luong");
            int kieuGiam = rs.getInt("Kieu_Giam");
            float giaTriDHToiThieu = rs.getFloat("Gia_tri_DH_Toi_Thieu");
            int mucGiamGia = rs.getInt("Muc_Giam_Gia");
            int mucGiamGiaToiDa = rs.getInt("Muc_Giam_Gia_Toi_Da");
            int trangThai = rs.getInt("Trang_Thai");

            modelGiamGia m = new modelGiamGia(maGiamGia, tenChuongTrinh, moTa, ngayBatDau, ngayKetThuc, soLuong, kieuGiam, giaTriDHToiThieu, mucGiamGia, mucGiamGiaToiDa, trangThai);
            listGG.add(m);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listGG;
}


    // check id không tồn tại 
    public boolean checkNhanVienExist(int IDNhanVien) {
    boolean exists = false;
    String sql = "SELECT COUNT(*) FROM NhanVien WHERE ID_Nhan_Vien = ?"; // Sửa biến sql thành String

    try (Connection con = Dbconnect.getConnection(); // Sử dụng try-with-resources để tự động đóng kết nối
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, IDNhanVien);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            exists = rs.getInt(1) > 0; // Kiểm tra nếu kết quả trả về lớn hơn 0, nghĩa là ID tồn tại
        }
    } catch (Exception e) {
        e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi truy vấn cơ sở dữ liệu
    }
    
    return exists; // Trả về kết quả kiểm tra
}

    public void addEvent(modelGiamGia newEvent) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

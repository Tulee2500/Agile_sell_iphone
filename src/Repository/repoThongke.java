/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.ThongKeModel;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import raven.application.form.other.ThongKe;

/**
 *
 * @author Tu PC
 */
public class repoThongke {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<ThongKeModel> getAllTK() {
        ArrayList<ThongKeModel> list = new ArrayList<>();
        sql = "SELECT ID_Hoa_Don,Ma_Hoa_Don, Ngay_Thanh_Toan, Tong_Gia,Trang_Thai\n"
                + "FROM HoaDon\n"
                + "WHERE Trang_Thai = 1 ";
        try {
            con = Dbconnect.getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int IdHoaDon = rs.getInt(1);
                String maHoaDon = rs.getString(2);
                Date ngayThanhToan = rs.getDate(3);
                int tongGia = rs.getInt(4);
                int trangThai = rs.getInt(5);
                ThongKeModel tk = new ThongKeModel(IdHoaDon, maHoaDon, ngayThanhToan, tongGia, trangThai);
                list.add(tk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ThongKeModel> DoanhThuTheoNgay(java.util.Date tu, java.util.Date den) {
        ArrayList<ThongKeModel> list = new ArrayList<>();
        sql = "SELECT ID_Hoa_Don, Ma_Hoa_Don, Ngay_Thanh_Toan, Tong_Gia, Trang_Thai\n"
                + "FROM HoaDon\n"
                + "WHERE Trang_Thai = 1 \n"
                + "AND Ngay_Thanh_Toan BETWEEN ? AND ?";
        try {
            con = Dbconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tu);
            ps.setObject(2, den);
            rs = ps.executeQuery();
            while (rs.next()) {
                int IdHoaDon = rs.getInt(1);
                String maHoaDon = rs.getString(2);
                java.util.Date ngayThanhToan = rs.getDate(3);
                int tongGia = rs.getInt(4);
                int trangThai = rs.getInt(5);
                ThongKeModel tk = new ThongKeModel(IdHoaDon, maHoaDon, ngayThanhToan, tongGia, trangThai);
                list.add(tk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ThongKeModel> DoanhThuTongTheoNgay(java.util.Date tu, java.util.Date den) {
        ArrayList<ThongKeModel> list = new ArrayList<>();
        sql = "SELECT SUM(Tong_Gia) AS TongDoanhThu\n"
                + "FROM HoaDon\n"
                + "WHERE Trang_Thai = 1\n"
                + "AND Ngay_Thanh_Toan BETWEEN ? AND ?";
        try {
            con = Dbconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tu);
            ps.setObject(2, den);
            rs = ps.executeQuery();
            while (rs.next()) {

                int tongGia = rs.getInt(1);

                ThongKeModel tk = new ThongKeModel();
                tk.setTongGia(tongGia);
                list.add(tk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int SoHoaDonDaThanhToan(java.util.Date tu, java.util.Date den) {
        int tongSoHoaDon = 0;
        String sql = "SELECT COUNT(ID_Hoa_Don) AS TongSoHoaDon "
                + "FROM HoaDon "
                + "WHERE Trang_Thai = 1 "
                + "AND Ngay_Thanh_Toan BETWEEN ? AND ?";

        try {
            con = Dbconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tu);
            ps.setObject(2, den);
            rs = ps.executeQuery();
            if (rs.next()) {
                tongSoHoaDon = rs.getInt("TongSoHoaDon"); // Lấy tổng số hóa đơn
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongSoHoaDon; // Trả về tổng số hóa đơn
    }

    public ArrayList<ThongKeModel> TongDoanhThuTheoNam(java.util.Date namTimKiem) {
        ArrayList<ThongKeModel> list = new ArrayList<>();

        // Chuyển đổi java.util.Date thành Calendar để lấy năm
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(namTimKiem);
        int nam = calendar.get(Calendar.YEAR); // Lấy năm từ date

        String sql = "SELECT YEAR(Ngay_Thanh_Toan) AS Nam, SUM(Tong_Gia) AS TongDoanhThu "
                + "FROM HoaDon "
                + "WHERE YEAR(Ngay_Thanh_Toan) = ? "
                + "AND Trang_Thai = 1 "
                + "GROUP BY YEAR(Ngay_Thanh_Toan)";

        try (Connection con = Dbconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Thiết lập giá trị năm cần tìm kiếm
            ps.setInt(1, nam); // Sử dụng năm đã lấy từ Calendar

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Lấy năm từ ResultSet
                    int namFromDb = rs.getInt("Nam"); // Đảm bảo tên cột chính xác
                    int tongDoanhThu = rs.getInt("TongDoanhThu"); // Sử dụng đúng tên alias

                    ThongKeModel tk = new ThongKeModel();
                    // Sử dụng Calendar để chuyển đổi từ năm sang Date
                    Calendar resultCalendar = Calendar.getInstance();
                    resultCalendar.set(Calendar.YEAR, namFromDb);
                    resultCalendar.set(Calendar.MONTH, Calendar.JANUARY); // Tháng 1 (Tháng đầu tiên)
                    resultCalendar.set(Calendar.DAY_OF_MONTH, 1); // Ngày 1

                    // Lưu thông tin vào đối tượng ThongKeModel
                    tk.setNgayThanhToan(new java.sql.Date(resultCalendar.getTimeInMillis())); // Lưu dưới dạng java.sql.Date
                    tk.setTongGia(tongDoanhThu);

                    list.add(tk);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int TongHoaDonTheoNam(java.util.Date namTimKiem) {
        int tongHoaDon = 0;

        // Chuyển đổi java.util.Date thành Calendar để lấy năm
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(namTimKiem);
        int nam = calendar.get(Calendar.YEAR); // Lấy năm từ date

        String sql = "SELECT COUNT(ID_Hoa_Don) AS TongHoaDon "
                + "FROM HoaDon "
                + "WHERE Trang_Thai = 1 "
                + "AND YEAR(Ngay_Thanh_Toan) = ?";

        try (Connection con = Dbconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Thiết lập giá trị năm cần tìm kiếm
            ps.setInt(1, nam); // Sử dụng năm đã lấy từ Calendar
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tongHoaDon = rs.getInt("TongHoaDon");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongHoaDon;
    }

    public ArrayList<ThongKeModel> BangThongKeTheoNam(java.util.Date namTimKiem) {
    ArrayList<ThongKeModel> list = new ArrayList<>();
    String sql = "SELECT ID_Hoa_Don, Ma_Hoa_Don, Ngay_Thanh_Toan, Tong_Gia, Trang_Thai " +
                 "FROM HoaDon " +
                 "WHERE YEAR(Ngay_Thanh_Toan) = YEAR(?) AND Trang_Thai = 1"; // Trạng thái 1 cho hóa đơn đã thanh toán

    try (Connection con = Dbconnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        // Thiết lập giá trị năm cần tìm kiếm
        ps.setDate(1, new java.sql.Date(namTimKiem.getTime())); // Chuyển đổi java.util.Date thành java.sql.Date

        try (ResultSet rs = ps.executeQuery()) {
            // Sử dụng vòng lặp để lấy từng hàng dữ liệu từ ResultSet
            while (rs.next()) {
                // Khởi tạo đối tượng ThongKeModel
                int idHoaDon = rs.getInt("ID_Hoa_Don"); // Lấy ID hóa đơn
                String maHoaDon = rs.getString("Ma_Hoa_Don"); // Lấy mã hóa đơn
                java.util.Date ngayThanhToan = rs.getDate("Ngay_Thanh_Toan"); // Lấy ngày thanh toán
                int tongGia = rs.getInt("Tong_Gia"); // Lấy tổng giá
                int trangThai = rs.getInt("Trang_Thai"); // Lấy trạng thái

                // Tạo đối tượng ThongKeModel và thiết lập các thuộc tính
                ThongKeModel hoaDon = new ThongKeModel(idHoaDon, maHoaDon, ngayThanhToan, tongGia, trangThai);

                // Thêm hóa đơn vào danh sách
                list.add(hoaDon);
            }
        }
    } catch (Exception e) {
        e.printStackTrace(); // In lỗi nếu có
    }
    return list; // Trả về danh sách hóa đơn
}

}

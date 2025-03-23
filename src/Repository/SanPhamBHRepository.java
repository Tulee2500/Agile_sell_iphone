/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.HoaDon;
import Model.HoaDonCTHD;
import Model.Imei;
import Model.KhachHang;
import Model.MaGiamGia;
import Model.NhanVien;
import Model.SanPhamBH;
import Model.SanPhamModelBH;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.apache.poi.hpsf.Array;

/**
 *
 * @author nothank
 */
public class SanPhamBHRepository {

    public List<SanPhamModelBH> getAllSanPhamWithDetails() {
        List<SanPhamModelBH> sanPhams = new ArrayList<>();
        String sql = "SELECT "
                + "sp.ID_San_Pham, " // Thêm trường ID_San_Pham lên đầu
                + "sp.Ma_San_Pham, "
                + "sp.Ten_San_Pham, "
                + "sp.So_Luong, " // Thêm trường So_Luong
                + "sp.Gia_Ban, "
                + "cpu.CPU, "
                + "ram.Ram, "
                + "rom.Rom, "
                + "mau.Mau_Sac, "
                + "kich.Kich_Thuoc, "
                + "dung.Dung_Luong_Pin, "
                + "xuat.Xuat_Xu, "
                + "phan.Phan_Loai "
                + "FROM dbo.SanPham sp "
                + "INNER JOIN dbo.CPU cpu ON sp.ID_CPU = cpu.ID_CPU "
                + "INNER JOIN dbo.Ram ram ON sp.ID_Ram = ram.ID_Ram "
                + "INNER JOIN dbo.Rom rom ON sp.ID_Rom = rom.ID_Rom "
                + "INNER JOIN dbo.MauSac mau ON sp.ID_Mau_Sac = mau.ID_Mau_Sac "
                + "INNER JOIN dbo.KichThuoc kich ON sp.ID_Kich_Thuoc = kich.ID_Kich_Thuoc "
                + "INNER JOIN dbo.DungLuongPin dung ON sp.ID_Dung_Luong_Pin = dung.ID_Dung_Luong_Pin "
                + "INNER JOIN dbo.XuatXu xuat ON sp.ID_Xuat_Xu = xuat.ID_Xuat_Xu "
                + "INNER JOIN dbo.PhanLoai phan ON sp.ID_Phan_Loai = phan.ID_Phan_Loai "
                + "WHERE sp.Trang_Thai = 0"; // chỉ lấy sản phẩm còn hàng

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SanPhamModelBH sp = new SanPhamModelBH();
                sp.setID(rs.getInt("ID_San_Pham")); // Đảm bảo có phương thức setIdSanPham trong SanPhamModelBH
                sp.setMaSanPham(rs.getString("Ma_San_Pham"));
                sp.setTenSanPham(rs.getString("Ten_San_Pham"));
                sp.setSoLuong(rs.getInt("So_Luong")); // Đảm bảo có phương thức setSoLuong trong SanPhamModelBH
                sp.setGiaBan(rs.getFloat("Gia_Ban"));
                sp.setCpu(rs.getString("CPU"));
                sp.setRam(rs.getString("Ram"));
                sp.setRom(rs.getString("Rom"));
                sp.setMauSac(rs.getString("Mau_Sac"));
                sp.setKichThuoc(rs.getString("Kich_Thuoc"));
                sp.setDungLuongPin(rs.getString("Dung_Luong_Pin"));
                sp.setXuatXu(rs.getString("Xuat_Xu"));
                sp.setPhanLoai(rs.getString("Phan_Loai"));
                sanPhams.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPhams;
    }

    public boolean thanhToan(String maHoaDon, double tongGia, String hinhThucThanhToan, String sdt, String diaChi, String maKhachHang, String maGiamGia, String maNhanVien) {
        String updateQuery = "UPDATE HoaDon SET "
                + "Tong_Gia = ?, "
                + "Hinh_Thuc_Thanh_Toan = ?, "
                + "Ten_Nguoi_Nhan = (SELECT Ten_Nguoi_Nhan FROM KhachHang WHERE Ma_Khach_Hang = ?), "
                + "SĐT = ?, "
                + "Dia_Chi = ?, "
                + "ID_Khach_Hang = (SELECT ID_Khach_Hang FROM KhachHang WHERE Ma_Khach_Hang = ?), "
                + "ID_Giam_Gia = (SELECT ID_Giam_Gia FROM GiamGia WHERE Ma_Giam_Gia = ?), "
                + "ID_Nhan_Vien = (SELECT ID_Nhan_Vien FROM NhanVien WHERE Ma_NV = ?), "
                + "Ngay_Thanh_Toan = GETDATE(), "
                + "Trang_Thai = 1 "
                + "WHERE Ma_Hoa_Don = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Thiết lập giá trị cho câu lệnh cập nhật hóa đơn
            updateStmt.setDouble(1, tongGia);
            updateStmt.setString(2, hinhThucThanhToan);
            updateStmt.setString(3, maKhachHang);
            updateStmt.setString(4, sdt);
            updateStmt.setString(5, diaChi);
            updateStmt.setString(6, maKhachHang);
            updateStmt.setString(7, maGiamGia);
            updateStmt.setString(8, maNhanVien);
            updateStmt.setString(9, maHoaDon);

            // Thực hiện cập nhật hóa đơn
            int affectedRows = updateStmt.executeUpdate();

            if (affectedRows > 0) {
                // Nếu cập nhật hóa đơn thành công và có mã giảm giá
                if (maGiamGia != null && !maGiamGia.isEmpty()) {
                    // Kiểm tra số lượng mã giảm giá trước khi cập nhật
                    if (truSoLuongGiamGia(conn, maGiamGia)) {
                        System.out.println("Đã trừ số lượng mã giảm giá thành công.");
                    } else {
                        // Không cần thông báo lỗi nếu không cập nhật được số lượng mã giảm giá
                        // Có thể ghi log hoặc xử lý ở đây nếu cần
                    }
                }
                return true; // Cập nhật thành công
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn để cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return false; // Trả về false nếu cập nhật thất bại
    }

    private boolean truSoLuongGiamGia(Connection conn, String maGiamGia) {
        String updateGiamGiaQuery = "UPDATE GiamGia SET So_Luong = So_Luong - 1 WHERE Ma_Giam_Gia = ?";

        try (PreparedStatement updateGiamGiaStmt = conn.prepareStatement(updateGiamGiaQuery)) {
            // Kiểm tra số lượng mã giảm giá trước khi cập nhật
            String checkGiamGiaQuery = "SELECT So_Luong FROM GiamGia WHERE Ma_Giam_Gia = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkGiamGiaQuery)) {
                checkStmt.setString(1, maGiamGia);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    int soLuong = rs.getInt("So_Luong");
                    if (soLuong > 0) {
                        // Thiết lập giá trị cho câu lệnh cập nhật giảm giá
                        updateGiamGiaStmt.setString(1, maGiamGia);
                        int updatedGiamGiaRows = updateGiamGiaStmt.executeUpdate();
                        return updatedGiamGiaRows > 0; // Trả về true nếu cập nhật thành công
                    } else {
                        JOptionPane.showMessageDialog(null, "Mã giảm giá đã hết hạn sử dụng.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật số lượng mã giảm giá: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public String createHD() {
        String maHoaDon = null;  // Khởi tạo biến để lưu mã hóa đơn
        String insertQuery = "INSERT INTO HoaDon (Ngay_Tao, Tong_Gia, Hinh_Thuc_Thanh_Toan, Ten_Nguoi_Nhan, [SĐT], Dia_Chi, ID_Khach_Hang, ID_Giam_Gia, ID_Nhan_Vien, Trang_Thai) "
                + "OUTPUT INSERTED.Ma_Hoa_Don "
                + "VALUES (GETDATE(), 0, 'Tiền mặt', '', '', '', 1, 1, 1, 0);";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Thực hiện câu lệnh chèn và truy xuất mã hóa đơn mới
            ResultSet rs = insertStmt.executeQuery();

            // Kiểm tra nếu có kết quả trả về
            if (rs.next()) {
                maHoaDon = rs.getString("Ma_Hoa_Don");  // Lấy mã hóa đơn
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy mã hóa đơn mới tạo.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi tạo hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return maHoaDon;  // Trả về mã hóa đơn
    }

    public ArrayList<HoaDon> getHD() {
        String sql = "SELECT Ma_Hoa_Don FROM HoaDon WHERE Trang_Thai = 0;";
        ArrayList<HoaDon> arrHD = new ArrayList<>();

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng HoaDon
                HoaDon r = new HoaDon();
                r.setMaHoaDon(rs.getString("Ma_Hoa_Don"));
                arrHD.add(r); // Thêm vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrHD; // Trả về danh sách sau khi vòng lặp kết thúc
    }

    public int getIdHoaDonFromMaHoaDon(String maHoaDon) {
        String sql = "SELECT ID_Hoa_Don FROM HoaDon WHERE Ma_Hoa_Don = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHoaDon); // Gán giá trị cho tham số
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID_Hoa_Don"); // Trả về ID_Hoa_Don
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Nếu không tìm thấy
    }

    public ArrayList<HoaDonCTHD> getHoaDonChiTietByMaHoaDon(String maHoaDon) {
        ArrayList<HoaDonCTHD> list = new ArrayList<>();
        int idHoaDon = getIdHoaDonFromMaHoaDon(maHoaDon); // Lấy ID_Hoa_Don

        if (idHoaDon == -1) {
            System.out.println("Không tìm thấy ID_Hoa_Don cho mã hóa đơn: " + maHoaDon);
            return list; // Trả về danh sách rỗng
        }

        String sql = "SELECT sp.ID_San_Pham, sp.Ten_San_Pham, hct.So_Luong, hct.Gia, idb.Ma_Imei_Da_Ban "
                + "FROM dbo.HoaDon hd "
                + "INNER JOIN dbo.HoaDonChiTiet hct ON hd.ID_Hoa_Don = hct.ID_Hoa_Don "
                + "INNER JOIN dbo.SanPham sp ON hct.ID_San_Pham = sp.ID_San_Pham "
                + "INNER JOIN dbo.ImeiDaBan idb ON hct.ID_HDCT = idb.ID_HDCT " // Thêm JOIN
                + "WHERE hd.ID_Hoa_Don = ? AND hct.Trang_Thai = 0";  // Thêm điều kiện WHERE

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idHoaDon); // Gán giá trị cho tham số ID_Hoa_Don
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) { // Kiểm tra nếu không có bản ghi nào
                System.out.println("Không tìm thấy chi tiết hóa đơn cho mã hóa đơn: " + maHoaDon);
            } else {
                while (rs.next()) {
                    HoaDonCTHD hdct = new HoaDonCTHD(
                            0, // idHdct
                            rs.getInt("So_Luong"), // Sử dụng đúng tên cột
                            rs.getFloat("Gia"),
                            0, // TrangThai
                            idHoaDon, // Gán ID_Hoa_Don cho đối tượng
                            rs.getString("Ten_San_Pham"),
                            rs.getInt("ID_San_Pham"), // Gán ID_San_Pham
                            rs.getString("Ma_Imei_Da_Ban") // Lấy Ma_Imei_Da_Ban từ ResultSet
                    );
                    list.add(hdct); // Thêm vào danh sách
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<HoaDonCTHD> getHoaDonChiTietByMaHoaDon1(String maHoaDon) {
        ArrayList<HoaDonCTHD> list = new ArrayList<>();
        int idHoaDon = getIdHoaDonFromMaHoaDon(maHoaDon); // Lấy ID_Hoa_Don

        if (idHoaDon == -1) {
            System.out.println("Không tìm thấy ID_Hoa_Don cho mã hóa đơn: " + maHoaDon);
            return list; // Trả về danh sách rỗng
        }

        String sql = "SELECT sp.ID_San_Pham, sp.Ten_San_Pham, hct.So_Luong, hct.Gia, idb.Ma_Imei_Da_Ban "
                + "FROM dbo.HoaDon hd "
                + "INNER JOIN dbo.HoaDonChiTiet hct ON hd.ID_Hoa_Don = hct.ID_Hoa_Don "
                + "INNER JOIN dbo.SanPham sp ON hct.ID_San_Pham = sp.ID_San_Pham "
                + "INNER JOIN dbo.ImeiDaBan idb ON hct.ID_HDCT = idb.ID_HDCT " // Thêm JOIN
                + "WHERE hd.ID_Hoa_Don = ?";  // Thêm điều kiện WHERE

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idHoaDon); // Gán giá trị cho tham số ID_Hoa_Don
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) { // Kiểm tra nếu không có bản ghi nào
                System.out.println("Không tìm thấy chi tiết hóa đơn cho mã hóa đơn: " + maHoaDon);
            } else {
                while (rs.next()) {
                    HoaDonCTHD hdct = new HoaDonCTHD(
                            0, // idHdct
                            rs.getInt("So_Luong"), // Sử dụng đúng tên cột
                            rs.getFloat("Gia"),
                            0, // TrangThai
                            idHoaDon, // Gán ID_Hoa_Don cho đối tượng
                            rs.getString("Ten_San_Pham"),
                            rs.getInt("ID_San_Pham"), // Gán ID_San_Pham
                            rs.getString("Ma_Imei_Da_Ban") // Lấy Ma_Imei_Da_Ban từ ResultSet
                    );
                    list.add(hdct); // Thêm vào danh sách
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<SanPhamModelBH> searchSanPham(String keyword) {
        List<SanPhamModelBH> products = new ArrayList<>();

        String sql = "SELECT "
                + "sp.ID_San_Pham, " // Thêm trường ID_San_Pham
                + "sp.Ma_San_Pham, "
                + "sp.Ten_San_Pham, "
                + "sp.So_Luong, "
                + "sp.Gia_Ban, "
                + "cpu.CPU, "
                + "ram.Ram, "
                + "rom.Rom, "
                + "mau.Mau_Sac, "
                + "kich.Kich_Thuoc, "
                + "dung.Dung_Luong_Pin, "
                + "xuat.Xuat_Xu, "
                + "phan.Phan_Loai "
                + "FROM dbo.SanPham sp "
                + "INNER JOIN dbo.CPU cpu ON sp.ID_CPU = cpu.ID_CPU "
                + "INNER JOIN dbo.Ram ram ON sp.ID_Ram = ram.ID_Ram "
                + "INNER JOIN dbo.Rom rom ON sp.ID_Rom = rom.ID_Rom "
                + "INNER JOIN dbo.MauSac mau ON sp.ID_Mau_Sac = mau.ID_Mau_Sac "
                + "INNER JOIN dbo.KichThuoc kich ON sp.ID_Kich_Thuoc = kich.ID_Kich_Thuoc "
                + "INNER JOIN dbo.DungLuongPin dung ON sp.ID_Dung_Luong_Pin = dung.ID_Dung_Luong_Pin "
                + "INNER JOIN dbo.XuatXu xuat ON sp.ID_Xuat_Xu = xuat.ID_Xuat_Xu "
                + "INNER JOIN dbo.PhanLoai phan ON sp.ID_Phan_Loai = phan.ID_Phan_Loai "
                + "WHERE sp.Trang_Thai = 0 "
                + "AND (sp.Ten_San_Pham LIKE ? "
                + "OR cpu.CPU LIKE ? "
                + "OR ram.Ram LIKE ? "
                + "OR mau.Mau_Sac LIKE ? "
                + "OR kich.Kich_Thuoc LIKE ? "
                + "OR dung.Dung_Luong_Pin LIKE ? "
                + "OR rom.Rom LIKE ? "
                + "OR phan.Phan_Loai LIKE ? "
                + "OR xuat.Xuat_Xu LIKE ?)"; // chỉ lấy sản phẩm còn hàng

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Gán giá trị cho các điều kiện tìm kiếm
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 9; i++) {
                pstmt.setString(i, searchKeyword); // Gán từ khóa tìm kiếm cho tất cả các điều kiện
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SanPhamModelBH sp = new SanPhamModelBH();
                sp.setID(rs.getInt("ID_San_Pham")); // Đảm bảo có phương thức setIdSanPham trong SanPhamModelBH
                sp.setMaSanPham(rs.getString("Ma_San_Pham"));
                sp.setTenSanPham(rs.getString("Ten_San_Pham"));
                sp.setSoLuong(rs.getInt("So_Luong"));
                sp.setGiaBan(rs.getFloat("Gia_Ban"));
                sp.setCpu(rs.getString("CPU"));
                sp.setRam(rs.getString("Ram"));
                sp.setRom(rs.getString("Rom"));
                sp.setMauSac(rs.getString("Mau_Sac"));
                sp.setKichThuoc(rs.getString("Kich_Thuoc"));
                sp.setDungLuongPin(rs.getString("Dung_Luong_Pin"));
                sp.setXuatXu(rs.getString("Xuat_Xu"));
                sp.setPhanLoai(rs.getString("Phan_Loai"));
                // Thêm sản phẩm vào danh sách
                products.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra thông báo lỗi nếu có
        }
        return products; // Trả về danh sách sản phẩm tìm được
    }

    public ArrayList<KhachHang> getNameKH() {
        String sql = "select Ma_Khach_Hang from KhachHang WHERE Trang_Thai = 0";

        ArrayList<KhachHang> arrKichThuoc = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                KhachHang r = new KhachHang();
                r.setMaKhachHang(rs.getString("Ma_Khach_Hang"));
                arrKichThuoc.add(r); // Thêm vào danh sách
            }

            return arrKichThuoc; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrKichThuoc; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<NhanVien> getNameNV() {
        String sql = "select Ma_NV from NhanVien WHERE Trang_Thai = 0";

        ArrayList<NhanVien> arrNhanVien = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                NhanVien r = new NhanVien();
                r.setMaNhanVien(rs.getString("Ma_NV"));
                arrNhanVien.add(r); // Thêm vào danh sách
            }

            return arrNhanVien; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrNhanVien; // Trả về danh sách rỗng nếu có lỗi
    }

    public ArrayList<MaGiamGia> getNameGG() {
        // Câu lệnh SQL để lấy mã giảm giá có trạng thái còn hiệu lực và số lượng lớn hơn 0
        String sql = "SELECT Ma_Giam_Gia FROM GiamGia WHERE Trang_Thai = 1 AND So_Luong > 0";

        ArrayList<MaGiamGia> arrKichThuoc = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng MaGiamGia
                MaGiamGia r = new MaGiamGia();
                r.setMaGiamGia(rs.getString("Ma_Giam_Gia"));
                arrKichThuoc.add(r); // Thêm vào danh sách
            }

            return arrKichThuoc; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrKichThuoc; // Trả về danh sách rỗng nếu có lỗi
    }

    public Double layMucGiamGia(String maGiamGia) {
        String sql = "SELECT [Muc_Giam_Gia] FROM [dbo].[GiamGia] WHERE Ma_Giam_Gia = ?;";
        Double mucGiamGia = null; // Khởi tạo biến để lưu giá trị

        try (Connection conn = Dbconnect.getConnection(); // Kết nối đến cơ sở dữ liệu
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maGiamGia); // Đặt tham số cho Ma_Giam_Gia

            try (ResultSet rs = pstmt.executeQuery()) { // Thực hiện truy vấn
                if (rs.next()) { // Kiểm tra nếu có kết quả
                    mucGiamGia = rs.getDouble("Muc_Giam_Gia"); // Lấy giá trị Muc_Giam_Gia
                }
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xảy ra khi lấy mức giảm giá: " + e.getMessage());
        }

        return mucGiamGia; // Trả về giá trị Muc_Giam_Gia hoặc null nếu không tìm thấy
    }

    public ArrayList<Imei> getName() {
        String sql = "select Ma_Imei from Imei WHERE Trang_Thai = 0";

        ArrayList<Imei> arrImei = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                Imei r = new Imei();
                r.setMa_Imei(rs.getString("Ma_Imei"));
                arrImei.add(r); // Thêm vào danh sách
            }

            return arrImei; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrImei; // Trả về danh sách rỗng nếu có lỗi
    }

    public boolean addSP(String maHoaDon, float gia, int idSanPham) {
        String selectHoaDonIDQuery = "SELECT ID_Hoa_Don FROM HoaDon WHERE Ma_Hoa_Don = ?;";
        String checkSoLuongQuery = "SELECT So_Luong FROM SanPham WHERE ID_San_Pham = ?;";
        String insertHDCTQuery = "INSERT INTO HoaDonChiTiet (So_Luong, Gia, ID_Hoa_Don, ID_San_Pham) VALUES (1, ?, ?, ?);";
        String updateSoLuongSPQuery = "UPDATE SanPham SET So_Luong = So_Luong - 1 WHERE ID_San_Pham = ?;";
        String updateIMEIQuery = "UPDATE Imei SET Trang_Thai = 2 WHERE Ma_Imei = ?;";
        String insertImeiDaBanQuery = "INSERT INTO ImeiDaBan (Ma_Imei_Da_Ban, Trang_Thai, ID_HDCT) VALUES (?, 0, ?);";

        boolean isAdding = false;

        try (Connection conn = Dbconnect.getConnection()) {
            if (conn == null || conn.isClosed()) {
                JOptionPane.showMessageDialog(null, "Kết nối không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            conn.setAutoCommit(false); // Bắt đầu transaction

            // Bước 1: Lấy ID_Hoa_Don dựa vào Ma_Hoa_Don
            int idHoaDon = 0;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectHoaDonIDQuery)) {
                selectStmt.setString(1, maHoaDon);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    idHoaDon = rs.getInt("ID_Hoa_Don");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            // Bước 2: Kiểm tra số lượng sản phẩm
            int soLuongTrongKho = 0;
            try (PreparedStatement checkSoLuongStmt = conn.prepareStatement(checkSoLuongQuery)) {
                checkSoLuongStmt.setInt(1, idSanPham);
                ResultSet rsSL = checkSoLuongStmt.executeQuery();
                if (rsSL.next()) {
                    soLuongTrongKho = rsSL.getInt("So_Luong");
                }

                if (soLuongTrongKho <= 0) {
                    JOptionPane.showMessageDialog(null, "Sản phẩm này đã hết hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            // Bước 3: Hiển thị danh sách IMEI để chọn
            if (!isAdding) {
                isAdding = true;

                List<String> imeiList = getImeiBySanPhamId(idSanPham);
                String selectedImei = selectImeiFromList(imeiList); // Hộp thoại chọn IMEI

                if (selectedImei == null) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn IMEI.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                // Bước 4: Thêm chi tiết hóa đơn
                int idHDCT = -1; // Biến để lưu ID của hóa đơn chi tiết
                try (PreparedStatement insertHDCTStmt = conn.prepareStatement(insertHDCTQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertHDCTStmt.setFloat(1, gia);
                    insertHDCTStmt.setInt(2, idHoaDon);
                    insertHDCTStmt.setInt(3, idSanPham);
                    int rowsInsertedHDCT = insertHDCTStmt.executeUpdate();

                    if (rowsInsertedHDCT > 0) {
                        // Lấy ID_HDCT vừa thêm
                        ResultSet generatedKeys = insertHDCTStmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            idHDCT = generatedKeys.getInt(1); // Lưu ID_HDCT
                        }

                        // Cập nhật trạng thái IMEI
                        try (PreparedStatement updateIMEIStmt = conn.prepareStatement(updateIMEIQuery)) {
                            updateIMEIStmt.setString(1, selectedImei);
                            updateIMEIStmt.executeUpdate();
                        }

                        // Thêm IMEI vào bảng ImeiDaBan
                        try (PreparedStatement insertImeiDaBanStmt = conn.prepareStatement(insertImeiDaBanQuery)) {
                            insertImeiDaBanStmt.setString(1, selectedImei);
                            insertImeiDaBanStmt.setInt(2, idHDCT); // Sử dụng ID_HDCT đã lấy
                            insertImeiDaBanStmt.executeUpdate();
                        }

                        // Cập nhật số lượng sản phẩm
                        try (PreparedStatement updateSoLuongSPStmt = conn.prepareStatement(updateSoLuongSPQuery)) {
                            updateSoLuongSPStmt.setInt(1, idSanPham);
                            updateSoLuongSPStmt.executeUpdate();
                        }

                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm và IMEI thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        conn.commit(); // Commit tất cả các thay đổi
                        return true; // Trả về true khi thêm thành công
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm hóa đơn chi tiết thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        conn.rollback(); // Rollback transaction
                    }
                } catch (SQLException e) {
                    // Rollback nếu có lỗi xảy ra
                    try {
                        conn.rollback();
                    } catch (SQLException rollbackEx) {
                        JOptionPane.showMessageDialog(null, "Rollback không thành công: " + rollbackEx.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return false; // Trả về false nếu không thêm thành công
    }

// Phương thức để hiển thị hộp thoại chọn IMEI
    private String selectImeiFromList(List<String> imeiList) {
        return (String) JOptionPane.showInputDialog(null, "Chọn IMEI:", "Chọn IMEI",
                JOptionPane.QUESTION_MESSAGE, null, imeiList.toArray(), imeiList.get(0));
    }

//    public List<String> getImei() {
//        String selectImeiQuery = "SELECT Ma_Imei FROM Imei WHERE Trang_Thai = 1;";
//        List<String> imeiList = new ArrayList<>();
//
//        try (Connection conn = Dbconnect.getConnection(); PreparedStatement selectStmt = conn.prepareStatement(selectImeiQuery); ResultSet rs = selectStmt.executeQuery()) {
//            // Duyệt qua kết quả trả về
//            while (rs.next()) {
//                String maImei = rs.getString("Ma_Imei");
//                imeiList.add(maImei); // Thêm từng mã IMEI vào danh sách
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//
//        return imeiList; // Trả về danh sách mã IMEI
//    }
    public List<String> getImeiBySanPhamId(int idSanPham) {
        List<String> imeiList = new ArrayList<>();
        String query = "SELECT Ma_Imei FROM Imei WHERE ID_San_Pham = ? AND Trang_Thai = 1";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idSanPham);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    imeiList.add(rs.getString("Ma_Imei"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imeiList;
    }

    public void xoaHoaDonCT(String maHoaDon, int idSanPham, String maImeiDaBan) {
        try (Connection conn = Dbconnect.getConnection()) {
            conn.setAutoCommit(false); // Tắt chế độ tự động commit
            System.out.println("Kết nối đến cơ sở dữ liệu thành công.");

            // Bước 1: Xóa bản ghi trong ImeiDaBan
            if (xoaImeiDaBan(conn, maImeiDaBan)) {
                // Bước 2: Xóa bản ghi trong HoaDonChiTiet
                if (xoaHoaDonChiTiet(conn, maHoaDon, idSanPham, maImeiDaBan)) {
                    // Bước 3: Cập nhật lại số lượng sản phẩm
                    capNhatSoLuongSanPham(conn, idSanPham);
                    // Bước 4: Cập nhật trạng thái IMEI
                    capNhatTrangThaiImei(conn, maImeiDaBan);
                }
            }

            conn.commit(); // Commit giao dịch
            System.out.println("Giao dịch đã được commit thành công.");
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private boolean xoaImeiDaBan(Connection conn, String maImeiDaBan) {
        String sql = "DELETE FROM [dbo].[ImeiDaBan] WHERE Ma_Imei_Da_Ban = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maImeiDaBan); // Set tham số cho Ma_Imei_Da_Ban
            pstmt.executeUpdate();
            System.out.println("Đã xóa IMEI: " + maImeiDaBan);
            return true;
        } catch (SQLException e) {
            System.err.println("Có lỗi xảy ra khi xóa IMEI: " + e.getMessage());
            return false; // Trả về false nếu có lỗi
        }
    }

    private boolean xoaHoaDonChiTiet(Connection conn, String maHoaDon, int idSanPham, String maImeiDaBan) {
        String sql = "DELETE hdc FROM [dbo].[HoaDonChiTiet] hdc "
                + "JOIN ImeiDaBan im ON im.ID_HDCT = hdc.ID_HDCT "
                + "JOIN HoaDon hd ON hd.ID_Hoa_Don = hdc.ID_Hoa_Don "
                + "WHERE hdc.ID_San_Pham = ? AND hd.Ma_Hoa_Don = ? AND im.Ma_Imei_Da_Ban = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idSanPham); // Set tham số cho ID_San_Pham
            pstmt.setString(2, maHoaDon); // Set tham số cho Ma_Hoa_Don
            pstmt.setString(3, maImeiDaBan); // Set tham số cho Ma_Imei_Da_Ban
            pstmt.executeUpdate();
            System.out.println("Đã xóa hóa đơn chi tiết cho sản phẩm ID: " + idSanPham);
            return true;
        } catch (SQLException e) {
            System.err.println("Có lỗi xảy ra khi xóa hóa đơn chi tiết: " + e.getMessage());
            return false; // Trả về false nếu có lỗi
        }
    }

    private void capNhatSoLuongSanPham(Connection conn, int idSanPham) {
        String sql = "UPDATE [dbo].[SanPham] SET [So_Luong] = [So_Luong] + 1 WHERE [ID_San_Pham] = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idSanPham); // Set tham số cho ID_San_Pham
            pstmt.executeUpdate();
            System.out.println("Đã cập nhật số lượng sản phẩm ID: " + idSanPham);
        } catch (SQLException e) {
            System.err.println("Có lỗi xảy ra khi cập nhật số lượng sản phẩm: " + e.getMessage());
        }
    }

    private void capNhatTrangThaiImei(Connection conn, String maImeiDaBan) {
        String sql = "UPDATE [dbo].[Imei] SET [Trang_Thai] = 1 WHERE Ma_Imei = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maImeiDaBan); // Set tham số cho Ma_Imei
            pstmt.executeUpdate();
            System.out.println("Đã cập nhật trạng thái IMEI: " + maImeiDaBan);
        } catch (SQLException e) {
            System.err.println("Có lỗi xảy ra khi cập nhật trạng thái IMEI: " + e.getMessage());
        }
    }

    public void capNhatTrangThaiHoaDonChiTiet(String maHoaDon) {
        String sql = "UPDATE HoaDonChiTiet "
                + "SET Trang_Thai = 1 "
                + "WHERE ID_Hoa_Don = (SELECT ID_Hoa_Don FROM HoaDon WHERE Ma_Hoa_Don = ?);";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho câu lệnh SQL
            pstmt.setString(1, maHoaDon);

            // Thực thi câu lệnh cập nhật
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra nếu có bản ghi được cập nhật
            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy mã hóa đơn cần cập nhật.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String SDT(String SDT) {
        String maKhachHang = null;
        String sql = "SELECT [Ma_Khach_Hang] FROM [dbo].[KhachHang] WHERE SĐT = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán giá trị cho tham số truy vấn
            ps.setString(1, SDT);

            // Thực hiện truy vấn
            ResultSet rs = ps.executeQuery();

            // Kiểm tra kết quả truy vấn
            if (rs.next()) {
                maKhachHang = rs.getString("Ma_Khach_Hang"); // Lấy mã khách hàng
                // Hiển thị thông báo khi tìm thấy khách hàng
                JOptionPane.showMessageDialog(null, "Tìm thấy mã khách hàng: " + maKhachHang, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Hiển thị thông báo khi không tìm thấy khách hàng
                JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với số điện thoại: " + SDT, "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return maKhachHang;
    }

    public String[] timKhachHang(String maKhachHang) {
        String[] khachHang = new String[2]; // Chứa số điện thoại và tên
        String sql = "SELECT [SĐT], [Ten] FROM [dbo].[KhachHang] WHERE [Ma_Khach_Hang] = ?";

        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã khách hàng không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Gán giá trị cho tham số truy vấn
            ps.setString(1, maKhachHang);

            // Thực hiện truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                // Kiểm tra kết quả truy vấn
                if (rs.next()) {
                    khachHang[0] = rs.getString("SĐT"); // Lấy số điện thoại
                    khachHang[1] = rs.getString("Ten"); // Lấy tên khách hàng
                    // Hiển thị thông báo khi tìm thấy thông tin
                    JOptionPane.showMessageDialog(null, "Tìm thấy thông tin khách hàng:\nSố điện thoại: " + khachHang[0] + "\nTên: " + khachHang[1], "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Hiển thị thông báo khi không tìm thấy thông tin
                    JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin cho mã khách hàng: " + maKhachHang, "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return khachHang; // Trả về mảng chứa số điện thoại và tên
    }

    public boolean thanhToan(String maHoaDon, double tongGia, String hinhThuc, String sdt, String diaChi, String maKhachHang, String maNhanVien) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

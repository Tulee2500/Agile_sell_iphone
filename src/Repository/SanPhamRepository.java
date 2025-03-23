/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.ModelSanPham;
import Model.SanPhamModel;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author nothank
 */
public class SanPhamRepository {

    public ArrayList<ModelSanPham> getSanPhamDetails(int page, int limit) {
        String sql = "SELECT \n"
                + "    dbo.SanPham.Ma_San_Pham, \n"
                + "    dbo.SanPham.Ten_San_Pham, \n"
                + "    dbo.PhanLoai.Phan_Loai, \n"
                + "    dbo.SanPham.Ngay_Tao, \n"
                + "    dbo.SanPham.So_Luong, \n"
                + "    dbo.Rom.Rom, \n"
                + "    dbo.MauSac.Mau_Sac, \n"
                + "    dbo.Ram.Ram, \n"
                + "    dbo.KichThuoc.Kich_Thuoc, \n"
                + "    dbo.DungLuongPin.Dung_Luong_Pin,  -- Sử dụng bảng DungLuongPin chỉ một lần\n"
                + "    dbo.CPU.CPU, \n"
                + "    dbo.XuatXu.Xuat_Xu, \n"
                + "    dbo.SanPham.Gia_Ban\n"
                + "FROM \n"
                + "    dbo.SanPham \n"
                + "INNER JOIN dbo.PhanLoai ON dbo.SanPham.ID_Phan_Loai = dbo.PhanLoai.ID_Phan_Loai \n"
                + "INNER JOIN dbo.Rom ON dbo.SanPham.ID_Rom = dbo.Rom.ID_Rom \n"
                + "INNER JOIN dbo.MauSac ON dbo.SanPham.ID_Mau_Sac = dbo.MauSac.ID_Mau_Sac \n"
                + "INNER JOIN dbo.Ram ON dbo.SanPham.ID_Ram = dbo.Ram.ID_Ram \n"
                + "INNER JOIN dbo.KichThuoc ON dbo.SanPham.ID_Kich_Thuoc = dbo.KichThuoc.ID_Kich_Thuoc \n"
                + "INNER JOIN dbo.DungLuongPin ON dbo.SanPham.ID_Dung_Luong_Pin = dbo.DungLuongPin.ID_Dung_Luong_Pin \n"
                + "INNER JOIN dbo.CPU ON dbo.SanPham.ID_CPU = dbo.CPU.ID_CPU \n"
                + "INNER JOIN dbo.XuatXu ON dbo.SanPham.ID_Xuat_Xu = dbo.XuatXu.ID_Xuat_Xu \n"
                + "WHERE \n"
                + "    dbo.SanPham.Trang_Thai = 0\n"
                + "ORDER BY \n"
                + "    dbo.SanPham.ID_San_Pham \n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<ModelSanPham> sanPhamDetails = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("Ma_San_Pham");
                String tenSanPham = rs.getString("Ten_San_Pham");
                String phanLoai = rs.getString("Phan_Loai");
                Date ngayTao = rs.getDate("Ngay_Tao");
                int soLuong = rs.getInt("So_Luong");
                String rom = rs.getString("Rom");
                String mauSac = rs.getString("Mau_Sac");
                String ram = rs.getString("Ram");
                String kichThuoc = rs.getString("Kich_Thuoc");
                String dungLuongPin = rs.getString("Dung_Luong_Pin");
                String cpu = rs.getString("CPU");
                String xuatXu = rs.getString("Xuat_Xu");
                float giaBan = rs.getFloat("Gia_Ban");

                // Tạo đối tượng ModelSanPham và thêm vào danh sách
                ModelSanPham detail = new ModelSanPham(maSanPham, tenSanPham, phanLoai, ngayTao, soLuong,
                        rom, mauSac, ram, kichThuoc,
                        dungLuongPin, cpu, xuatXu, giaBan);
                sanPhamDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In thông báo lỗi
        }

        return sanPhamDetails;
    }

    public ArrayList<ModelSanPham> getSanPhamNgungBan(int page, int limit) {
        String sql = "SELECT \n"
                + "    dbo.SanPham.Ma_San_Pham, \n"
                + "    dbo.SanPham.Ten_San_Pham, \n"
                + "    dbo.PhanLoai.Phan_Loai, \n"
                + "    dbo.SanPham.Ngay_Tao, \n"
                + "    dbo.SanPham.So_Luong, \n"
                + "    dbo.Rom.Rom, \n"
                + "    dbo.MauSac.Mau_Sac, \n"
                + "    dbo.Ram.Ram, \n"
                + "    dbo.KichThuoc.Kich_Thuoc, \n"
                + "    dbo.DungLuongPin.Dung_Luong_Pin,  -- Sử dụng bảng DungLuongPin chỉ một lần\n"
                + "    dbo.CPU.CPU, \n"
                + "    dbo.XuatXu.Xuat_Xu, \n"
                + "    dbo.SanPham.Gia_Ban\n"
                + "FROM \n"
                + "    dbo.SanPham \n"
                + "INNER JOIN dbo.PhanLoai ON dbo.SanPham.ID_Phan_Loai = dbo.PhanLoai.ID_Phan_Loai \n"
                + "INNER JOIN dbo.Rom ON dbo.SanPham.ID_Rom = dbo.Rom.ID_Rom \n"
                + "INNER JOIN dbo.MauSac ON dbo.SanPham.ID_Mau_Sac = dbo.MauSac.ID_Mau_Sac \n"
                + "INNER JOIN dbo.Ram ON dbo.SanPham.ID_Ram = dbo.Ram.ID_Ram \n"
                + "INNER JOIN dbo.KichThuoc ON dbo.SanPham.ID_Kich_Thuoc = dbo.KichThuoc.ID_Kich_Thuoc \n"
                + "INNER JOIN dbo.DungLuongPin ON dbo.SanPham.ID_Dung_Luong_Pin = dbo.DungLuongPin.ID_Dung_Luong_Pin \n"
                + "INNER JOIN dbo.CPU ON dbo.SanPham.ID_CPU = dbo.CPU.ID_CPU \n"
                + "INNER JOIN dbo.XuatXu ON dbo.SanPham.ID_Xuat_Xu = dbo.XuatXu.ID_Xuat_Xu \n"
                + "WHERE \n"
                + "    dbo.SanPham.Trang_Thai = 1\n"
                + "ORDER BY \n"
                + "    dbo.SanPham.ID_San_Pham \n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<ModelSanPham> sanPhamDetails = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("Ma_San_Pham");
                String tenSanPham = rs.getString("Ten_San_Pham");
                String phanLoai = rs.getString("Phan_Loai");
                Date ngayTao = rs.getDate("Ngay_Tao");
                int soLuong = rs.getInt("So_Luong");
                String rom = rs.getString("Rom");
                String mauSac = rs.getString("Mau_Sac");
                String ram = rs.getString("Ram");
                String kichThuoc = rs.getString("Kich_Thuoc");
                String dungLuongPin = rs.getString("Dung_Luong_Pin");
                String cpu = rs.getString("CPU");
                String xuatXu = rs.getString("Xuat_Xu");
                float giaBan = rs.getFloat("Gia_Ban");

                // Tạo đối tượng ModelSanPham và thêm vào danh sách
                ModelSanPham detail = new ModelSanPham(maSanPham, tenSanPham, phanLoai, ngayTao, soLuong,
                        rom, mauSac, ram, kichThuoc,
                        dungLuongPin, cpu, xuatXu, giaBan);
                sanPhamDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In thông báo lỗi
        }

        return sanPhamDetails;
    }

    public ArrayList<ModelSanPham> selectTheoMa(int page, int limit, String maSP) {
        String sql = "SELECT \n"
                + "    dbo.SanPham.Ma_San_Pham, \n"
                + "    dbo.SanPham.Ten_San_Pham, \n"
                + "    dbo.SanPham.Mo_Ta, \n"
                + "    dbo.SanPham.Ngay_Tao, \n"
                + "    dbo.SanPham.So_Luong, \n"
                + "    dbo.SanPham.Gia_Nhap, \n" // Thêm dấu phẩy
                + "    dbo.SanPham.Gia_Ban, \n" // Thêm dấu phẩy
                + "    dbo.SanPham.Hinh_Anh, \n" // Thêm dấu phẩy
                + "    dbo.Rom.Rom, \n"
                + "    dbo.MauSac.Mau_Sac, \n"
                + "    dbo.Ram.Ram, \n"
                + "    dbo.KichThuoc.Kich_Thuoc, \n"
                + "    dbo.DungLuongPin.Dung_Luong_Pin,  \n"
                + "    dbo.CPU.CPU, \n"
                + "    dbo.XuatXu.Xuat_Xu, \n"
                + "    dbo.PhanLoai.Phan_Loai \n" // Sửa dấu phẩy thành kết thúc câu
                + "FROM \n"
                + "    dbo.SanPham \n"
                + "INNER JOIN dbo.PhanLoai ON dbo.SanPham.ID_Phan_Loai = dbo.PhanLoai.ID_Phan_Loai \n"
                + "INNER JOIN dbo.Rom ON dbo.SanPham.ID_Rom = dbo.Rom.ID_Rom \n"
                + "INNER JOIN dbo.MauSac ON dbo.SanPham.ID_Mau_Sac = dbo.MauSac.ID_Mau_Sac \n"
                + "INNER JOIN dbo.Ram ON dbo.SanPham.ID_Ram = dbo.Ram.ID_Ram \n"
                + "INNER JOIN dbo.KichThuoc ON dbo.SanPham.ID_Kich_Thuoc = dbo.KichThuoc.ID_Kich_Thuoc \n"
                + "INNER JOIN dbo.DungLuongPin ON dbo.SanPham.ID_Dung_Luong_Pin = dbo.DungLuongPin.ID_Dung_Luong_Pin \n"
                + "INNER JOIN dbo.CPU ON dbo.SanPham.ID_CPU = dbo.CPU.ID_CPU \n"
                + "INNER JOIN dbo.XuatXu ON dbo.SanPham.ID_Xuat_Xu = dbo.XuatXu.ID_Xuat_Xu \n"
                + "WHERE \n"
                + "    dbo.SanPham.Trang_Thai = 0 AND dbo.SanPham.ID_San_Pham = (SELECT ID_San_Pham FROM dbo.SanPham WHERE Ma_San_Pham = ?)\n"
                + "ORDER BY \n"
                + "    dbo.SanPham.ID_San_Pham \n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<ModelSanPham> sanPhamDetails = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (page - 1) * limit; // Tính toán offset
            ps.setString(1, maSP); // Đảm bảo sử dụng setString cho tham số Ma_San_Pham
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("Ma_San_Pham");
                String tenSanPham = rs.getString("Ten_San_Pham");
                String phanLoai = rs.getString("Phan_Loai");
                Date ngayTao = rs.getDate("Ngay_Tao");
                int soLuong = rs.getInt("So_Luong");
                String rom = rs.getString("Rom");
                String mauSac = rs.getString("Mau_Sac");
                String ram = rs.getString("Ram");
                String kichThuoc = rs.getString("Kich_Thuoc");
                String dungLuongPin = rs.getString("Dung_Luong_Pin");
                String cpu = rs.getString("CPU");
                String xuatXu = rs.getString("Xuat_Xu");
                String moTa = rs.getString("Mo_Ta");
                String hinhAnh = rs.getString("Hinh_Anh");
                float giaBan = rs.getFloat("Gia_Ban");
                float giaNhap = rs.getFloat("Gia_Nhap");

                // Tạo đối tượng ModelSanPham và thêm vào danh sách
                ModelSanPham detail = new ModelSanPham(maSanPham, tenSanPham, moTa, ngayTao, soLuong, giaBan, giaNhap, hinhAnh, rom, mauSac, ram, kichThuoc, dungLuongPin, cpu, xuatXu, phanLoai);
                sanPhamDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In thông báo lỗi
        }

        return sanPhamDetails;
    }

    public ArrayList<ModelSanPham> searchSanPham(int page, int limit, String keyword) {
        String sql = "SELECT \n"
                + "    sp.Ma_San_Pham, \n"
                + "    sp.Ten_San_Pham, \n"
                + "    sp.Mo_Ta, \n"
                + "    sp.Ngay_Tao, \n"
                + "    sp.So_Luong, \n"
                + "    sp.Gia_Nhap, \n"
                + "    sp.Gia_Ban, \n"
                + "    sp.Hinh_Anh, \n"
                + "    rom.Rom, \n"
                + "    ms.Mau_Sac, \n"
                + "    ram.Ram, \n"
                + "    kt.Kich_Thuoc, \n"
                + "    dlp.Dung_Luong_Pin, \n"
                + "    cpu.CPU, \n"
                + "    xx.Xuat_Xu, \n"
                + "    pl.Phan_Loai \n"
                + "FROM \n"
                + "    dbo.SanPham sp \n"
                + "INNER JOIN dbo.PhanLoai pl ON sp.ID_Phan_Loai = pl.ID_Phan_Loai \n"
                + "INNER JOIN dbo.Rom rom ON sp.ID_Rom = rom.ID_Rom \n"
                + "INNER JOIN dbo.MauSac ms ON sp.ID_Mau_Sac = ms.ID_Mau_Sac \n"
                + "INNER JOIN dbo.Ram ram ON sp.ID_Ram = ram.ID_Ram \n"
                + "INNER JOIN dbo.KichThuoc kt ON sp.ID_Kich_Thuoc = kt.ID_Kich_Thuoc \n"
                + "INNER JOIN dbo.DungLuongPin dlp ON sp.ID_Dung_Luong_Pin = dlp.ID_Dung_Luong_Pin \n"
                + "INNER JOIN dbo.CPU cpu ON sp.ID_CPU = cpu.ID_CPU \n"
                + "INNER JOIN dbo.XuatXu xx ON sp.ID_Xuat_Xu = xx.ID_Xuat_Xu \n"
                + "WHERE \n"
                + "    sp.Trang_Thai = 0 \n"
                + "    AND (sp.Ma_San_Pham LIKE ? \n" // Tìm theo mã sản phẩm
                + "    OR sp.Ten_San_Pham LIKE ? \n"
                + "    OR cpu.CPU LIKE ? \n"
                + "    OR ram.Ram LIKE ? \n"
                + "    OR ms.Mau_Sac LIKE ? \n"
                + "    OR kt.Kich_Thuoc LIKE ? \n"
                + "    OR dlp.Dung_Luong_Pin LIKE ? \n"
                + "    OR rom.Rom LIKE ? \n"
                + "    OR pl.Phan_Loai LIKE ? \n"
                + "    OR xx.Xuat_Xu LIKE ?) \n"
                + "ORDER BY \n"
                + "    sp.ID_San_Pham \n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<ModelSanPham> sanPhamDetails = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 10; i++) { // Thay đổi từ 9 thành 10 để bao gồm Ma_San_Pham
                ps.setString(i, searchKeyword); // Gán từ khóa tìm kiếm cho tất cả các điều kiện
            }

            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(11, offset);
            ps.setInt(12, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("Ma_San_Pham");
                String tenSanPham = rs.getString("Ten_San_Pham");
                String phanLoai = rs.getString("Phan_Loai");
                Date ngayTao = rs.getDate("Ngay_Tao");
                int soLuong = rs.getInt("So_Luong");
                String rom = rs.getString("Rom");
                String mauSac = rs.getString("Mau_Sac");
                String ram = rs.getString("Ram");
                String kichThuoc = rs.getString("Kich_Thuoc");
                String dungLuongPin = rs.getString("Dung_Luong_Pin");
                String cpu = rs.getString("CPU");
                String xuatXu = rs.getString("Xuat_Xu");
                String moTa = rs.getString("Mo_Ta");
                String hinhAnh = rs.getString("Hinh_Anh");
                float giaBan = rs.getFloat("Gia_Ban");
                float giaNhap = rs.getFloat("Gia_Nhap");

                // Tạo đối tượng ModelSanPham và thêm vào danh sách
                ModelSanPham detail = new ModelSanPham(maSanPham, tenSanPham, moTa, ngayTao, soLuong, giaBan, giaNhap, hinhAnh, rom, mauSac, ram, kichThuoc, dungLuongPin, cpu, xuatXu, phanLoai);
                sanPhamDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In thông báo lỗi
        }

        return sanPhamDetails;
    }

    public ArrayList<ModelSanPham> searchSanPhamNN(int page, int limit, String keyword) {
        String sql = "SELECT \n"
                + "    sp.Ma_San_Pham, \n"
                + "    sp.Ten_San_Pham, \n"
                + "    sp.Mo_Ta, \n"
                + "    sp.Ngay_Tao, \n"
                + "    sp.So_Luong, \n"
                + "    sp.Gia_Nhap, \n"
                + "    sp.Gia_Ban, \n"
                + "    sp.Hinh_Anh, \n"
                + "    rom.Rom, \n"
                + "    ms.Mau_Sac, \n"
                + "    ram.Ram, \n"
                + "    kt.Kich_Thuoc, \n"
                + "    dlp.Dung_Luong_Pin, \n"
                + "    cpu.CPU, \n"
                + "    xx.Xuat_Xu, \n"
                + "    pl.Phan_Loai \n"
                + "FROM \n"
                + "    dbo.SanPham sp \n"
                + "INNER JOIN dbo.PhanLoai pl ON sp.ID_Phan_Loai = pl.ID_Phan_Loai \n"
                + "INNER JOIN dbo.Rom rom ON sp.ID_Rom = rom.ID_Rom \n"
                + "INNER JOIN dbo.MauSac ms ON sp.ID_Mau_Sac = ms.ID_Mau_Sac \n"
                + "INNER JOIN dbo.Ram ram ON sp.ID_Ram = ram.ID_Ram \n"
                + "INNER JOIN dbo.KichThuoc kt ON sp.ID_Kich_Thuoc = kt.ID_Kich_Thuoc \n"
                + "INNER JOIN dbo.DungLuongPin dlp ON sp.ID_Dung_Luong_Pin = dlp.ID_Dung_Luong_Pin \n"
                + "INNER JOIN dbo.CPU cpu ON sp.ID_CPU = cpu.ID_CPU \n"
                + "INNER JOIN dbo.XuatXu xx ON sp.ID_Xuat_Xu = xx.ID_Xuat_Xu \n"
                + "WHERE \n"
                + "    sp.Trang_Thai = 1 \n"
                + "    AND (sp.Ma_San_Pham LIKE ? \n" // Tìm theo mã sản phẩm
                + "    OR sp.Ten_San_Pham LIKE ? \n"
                + "    OR cpu.CPU LIKE ? \n"
                + "    OR ram.Ram LIKE ? \n"
                + "    OR ms.Mau_Sac LIKE ? \n"
                + "    OR kt.Kich_Thuoc LIKE ? \n"
                + "    OR dlp.Dung_Luong_Pin LIKE ? \n"
                + "    OR rom.Rom LIKE ? \n"
                + "    OR pl.Phan_Loai LIKE ? \n"
                + "    OR xx.Xuat_Xu LIKE ?) \n"
                + "ORDER BY \n"
                + "    sp.ID_San_Pham \n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<ModelSanPham> sanPhamDetails = new ArrayList<>();
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 10; i++) { // Thay đổi từ 9 thành 10 để bao gồm Ma_San_Pham
                ps.setString(i, searchKeyword); // Gán từ khóa tìm kiếm cho tất cả các điều kiện
            }

            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(11, offset);
            ps.setInt(12, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("Ma_San_Pham");
                String tenSanPham = rs.getString("Ten_San_Pham");
                String phanLoai = rs.getString("Phan_Loai");
                Date ngayTao = rs.getDate("Ngay_Tao");
                int soLuong = rs.getInt("So_Luong");
                String rom = rs.getString("Rom");
                String mauSac = rs.getString("Mau_Sac");
                String ram = rs.getString("Ram");
                String kichThuoc = rs.getString("Kich_Thuoc");
                String dungLuongPin = rs.getString("Dung_Luong_Pin");
                String cpu = rs.getString("CPU");
                String xuatXu = rs.getString("Xuat_Xu");
                String moTa = rs.getString("Mo_Ta");
                String hinhAnh = rs.getString("Hinh_Anh");
                float giaBan = rs.getFloat("Gia_Ban");
                float giaNhap = rs.getFloat("Gia_Nhap");

                // Tạo đối tượng ModelSanPham và thêm vào danh sách
                ModelSanPham detail = new ModelSanPham(maSanPham, tenSanPham, moTa, ngayTao, soLuong, giaBan, giaNhap, hinhAnh, rom, mauSac, ram, kichThuoc, dungLuongPin, cpu, xuatXu, phanLoai);
                sanPhamDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In thông báo lỗi
        }

        return sanPhamDetails;
    }

    public Boolean create(
            String maSanPham,
            String tenSanPham,
            String moTa,
            Date ngayTao,
            List<String> imeiList, // Chỉ cần truyền danh sách IMEI
            float giaBan,
            float giaNhap,
            String hinhAnh,
            String rom,
            String mauSac,
            String ram,
            String kichThuoc,
            String dungLuongPin,
            String cpu,
            String xuatXu,
            String phanLoai
    ) {
        // Đếm số lượng IMEI trong danh sách
        int soLuong = imeiList.size();

        String checkSql = "SELECT COUNT(*) FROM [dbo].[SanPham] "
                + "WHERE Ten_San_Pham = ? "
                + "AND ID_Rom = (SELECT TOP 1 ID_Rom FROM Rom WHERE Rom = ?) "
                + "AND ID_Mau_Sac = (SELECT TOP 1 ID_Mau_Sac FROM MauSac WHERE Mau_Sac = ?) "
                + "AND ID_Ram = (SELECT TOP 1 ID_Ram FROM Ram WHERE Ram = ?) "
                + "AND ID_Kich_Thuoc = (SELECT TOP 1 ID_Kich_Thuoc FROM KichThuoc WHERE Kich_Thuoc = ?) "
                + "AND ID_Dung_Luong_Pin = (SELECT TOP 1 ID_Dung_Luong_Pin FROM DungLuongPin WHERE Dung_Luong_Pin = ?) "
                + "AND ID_CPU = (SELECT TOP 1 ID_CPU FROM CPU WHERE CPU = ?) "
                + "AND ID_Xuat_Xu = (SELECT TOP 1 ID_Xuat_Xu FROM XuatXu WHERE Xuat_Xu = ?) "
                + "AND ID_Phan_Loai = (SELECT TOP 1 ID_Phan_Loai FROM PhanLoai WHERE Phan_Loai = ?)";

        String insertSql = "INSERT INTO [dbo].[SanPham]\n"
                + "           ([Ten_San_Pham]\n"
                + "           ,[Mo_Ta]\n"
                + "           ,[Ngay_Tao]\n"
                + "           ,[So_Luong]\n"
                + "           ,[Gia_Nhap]\n"
                + "           ,[Gia_Ban]\n"
                + "           ,[Hinh_Anh]\n"
                + "           ,[ID_Rom]\n"
                + "           ,[ID_Mau_Sac]\n"
                + "           ,[ID_Ram]\n"
                + "           ,[ID_Kich_Thuoc]\n"
                + "           ,[ID_Dung_Luong_Pin]\n"
                + "           ,[ID_CPU]\n"
                + "           ,[ID_Xuat_Xu]\n"
                + "           ,[ID_Phan_Loai]\n"
                + "           ,[Trang_Thai])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,(SELECT TOP 1 ID_Rom FROM Rom WHERE Rom = ?) \n"
                + "           ,(SELECT TOP 1 ID_Mau_Sac FROM MauSac WHERE Mau_Sac = ?) \n"
                + "           ,(SELECT TOP 1 ID_Ram FROM Ram WHERE Ram = ?) \n"
                + "           ,(SELECT TOP 1 ID_Kich_Thuoc FROM KichThuoc WHERE Kich_Thuoc = ?) \n"
                + "           ,(SELECT TOP 1 ID_Dung_Luong_Pin FROM DungLuongPin WHERE Dung_Luong_Pin = ?) \n"
                + "           ,(SELECT TOP 1 ID_CPU FROM CPU WHERE CPU = ?) \n"
                + "           ,(SELECT TOP 1 ID_Xuat_Xu FROM XuatXu WHERE Xuat_Xu = ?) \n"
                + "           ,(SELECT TOP 1 ID_Phan_Loai FROM PhanLoai WHERE Phan_Loai = ?) \n"
                + "           ,0)";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            // Kiểm tra xem sản phẩm đã tồn tại chưa với các thuộc tính cụ thể
            checkPs.setString(1, tenSanPham);
            checkPs.setString(2, rom);
            checkPs.setString(3, mauSac);
            checkPs.setString(4, ram);
            checkPs.setString(5, kichThuoc);
            checkPs.setString(6, dungLuongPin);
            checkPs.setString(7, cpu);
            checkPs.setString(8, xuatXu);
            checkPs.setString(9, phanLoai);

            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Sản phẩm với các thuộc tính trên đã tồn tại.");
                return false; // Sản phẩm đã tồn tại
            }

            // Nếu sản phẩm không tồn tại, kiểm tra IMEI trước khi thêm mới
            for (String imei : imeiList) {
                if (isImeiExists(imei, conn)) {
                    JOptionPane.showMessageDialog(null, "IMEI đã tồn tại: " + imei);
                    return false; // Trả về false ngay lập tức nếu trùng IMEI
                }
            }

            // Nếu không có IMEI nào trùng lặp, tiếp tục thêm mới sản phẩm
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, tenSanPham);
                ps.setString(2, moTa);
                ps.setDate(3, new java.sql.Date(ngayTao.getTime())); // Chuyển đổi java.util.Date thành java.sql.Date
                ps.setInt(4, soLuong); // Sử dụng soLuong đã được tính từ imeiList
                ps.setFloat(5, giaNhap);
                ps.setFloat(6, giaBan);
                ps.setString(7, hinhAnh);
                ps.setString(8, rom);         // Rom
                ps.setString(9, mauSac);      // Mau Sac
                ps.setString(10, ram);        // Ram
                ps.setString(11, kichThuoc);  // Kich Thuoc
                ps.setString(12, dungLuongPin); // Dung Luong Pin
                ps.setString(13, cpu);        // CPU
                ps.setString(14, xuatXu);     // Xuat Xu
                ps.setString(15, phanLoai);   // Phan Loai

                int affectedRows = ps.executeUpdate(); // Thực thi câu lệnh INSERT

                if (affectedRows > 0) {
                    // Lấy ID của sản phẩm vừa được thêm
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int sanPhamId = generatedKeys.getInt(1);

                        // Thêm IMEI tương ứng với số lượng sản phẩm
                        String imeiSql = "INSERT INTO [dbo].[Imei] ([Ma_Imei], [Trang_Thai], [ID_San_Pham]) VALUES (?, 1, ?)";
                        try (PreparedStatement imeiPs = conn.prepareStatement(imeiSql)) {
                            for (String imei : imeiList) {
                                imeiPs.setString(1, imei);
                                imeiPs.setInt(2, sanPhamId);
                                imeiPs.addBatch();
                            }
                            imeiPs.executeBatch(); // Thực thi batch để thêm tất cả IMEI cùng lúc
                        }
                    }
                    return true; // Thêm sản phẩm thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isImeiExists(String imei, Connection conn) {
        String checkImeiSql = "SELECT COUNT(*) FROM Imei WHERE Ma_Imei = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkImeiSql)) {
            ps.setString(1, imei);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu IMEI đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // IMEI không tồn tại
    }

    public Boolean update(
            String maSanPham,
            String tenSanPham,
            String moTa,
            Date ngayTao,
            //            int soLuong,
            float giaBan,
            float giaNhap,
            String hinhAnh,
            String rom,
            String mauSac,
            String ram,
            String kichThuoc,
            String dungLuongPin,
            String cpu,
            String xuatXu,
            String phanLoai
    ) {
        // Truy vấn kiểm tra sự trùng lặp sản phẩm (ngoại trừ sản phẩm đang cập nhật)
        String checkDuplicateSql = "SELECT COUNT(*) FROM [dbo].[SanPham] "
                + "WHERE Ten_San_Pham = ? "
                + "AND ID_Rom = (SELECT ID_Rom FROM Rom WHERE Rom = ?) "
                + "AND ID_Mau_Sac = (SELECT ID_Mau_Sac FROM MauSac WHERE Mau_Sac = ?) "
                + "AND ID_Ram = (SELECT ID_Ram FROM Ram WHERE Ram = ?) "
                + "AND ID_Kich_Thuoc = (SELECT ID_Kich_Thuoc FROM KichThuoc WHERE Kich_Thuoc = ?) "
                + "AND ID_Dung_Luong_Pin = (SELECT ID_Dung_Luong_Pin FROM DungLuongPin WHERE Dung_Luong_Pin = ?) "
                + "AND ID_CPU = (SELECT ID_CPU FROM CPU WHERE CPU = ?) "
                + "AND ID_Xuat_Xu = (SELECT ID_Xuat_Xu FROM XuatXu WHERE Xuat_Xu = ?) "
                + "AND ID_Phan_Loai = (SELECT ID_Phan_Loai FROM PhanLoai WHERE Phan_Loai = ?) "
                + "AND Ma_San_Pham != ?"; // Không kiểm tra sản phẩm đang cập nhật

        String updateSql = "UPDATE [dbo].[SanPham] SET "
                + "[Ten_San_Pham] = ?, "
                + "[Mo_Ta] = ?, "
                + "[Ngay_Tao] = ?, "
                //                + "[So_Luong] = ?, "
                + "[Gia_Nhap] = ?, "
                + "[Gia_Ban] = ?, "
                + "[Hinh_Anh] = ?, "
                + "[ID_Rom] = (SELECT ID_Rom FROM Rom WHERE Rom = ?), "
                + "[ID_Mau_Sac] = (SELECT ID_Mau_Sac FROM MauSac WHERE Mau_Sac = ?), "
                + "[ID_Ram] = (SELECT ID_Ram FROM Ram WHERE Ram = ?), "
                + "[ID_Kich_Thuoc] = (SELECT ID_Kich_Thuoc FROM KichThuoc WHERE Kich_Thuoc = ?), "
                + "[ID_Dung_Luong_Pin] = (SELECT ID_Dung_Luong_Pin FROM DungLuongPin WHERE Dung_Luong_Pin = ?), "
                + "[ID_CPU] = (SELECT ID_CPU FROM CPU WHERE CPU = ?), "
                + "[ID_Xuat_Xu] = (SELECT ID_Xuat_Xu FROM XuatXu WHERE Xuat_Xu = ?), "
                + "[ID_Phan_Loai] = (SELECT ID_Phan_Loai FROM PhanLoai WHERE Phan_Loai = ?), "
                + "[Trang_Thai] = 0 "
                + "WHERE Ma_San_Pham = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement checkDuplicatePs = conn.prepareStatement(checkDuplicateSql)) {

            // Kiểm tra trùng lặp
            checkDuplicatePs.setString(1, tenSanPham);
            checkDuplicatePs.setString(2, rom);
            checkDuplicatePs.setString(3, mauSac);
            checkDuplicatePs.setString(4, ram);
            checkDuplicatePs.setString(5, kichThuoc);
            checkDuplicatePs.setString(6, dungLuongPin);
            checkDuplicatePs.setString(7, cpu);
            checkDuplicatePs.setString(8, xuatXu);
            checkDuplicatePs.setString(9, phanLoai);
            checkDuplicatePs.setString(10, maSanPham); // Bỏ qua sản phẩm hiện tại

            ResultSet rsDuplicate = checkDuplicatePs.executeQuery();
            if (rsDuplicate.next() && rsDuplicate.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Sản phẩm với các thuộc tính này đã tồn tại.");
                return false; // Có trùng lặp
            }

            // Nếu không trùng lặp, tiếp tục cập nhật sản phẩm
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, tenSanPham);
                ps.setString(2, moTa);
                ps.setDate(3, new java.sql.Date(ngayTao.getTime()));
//                ps.setInt(4, soLuong);
                ps.setFloat(4, giaNhap);
                ps.setFloat(5, giaBan);
                ps.setString(6, hinhAnh);
                ps.setString(7, rom);
                ps.setString(8, mauSac);
                ps.setString(9, ram);
                ps.setString(10, kichThuoc);
                ps.setString(11, dungLuongPin);
                ps.setString(12, cpu);
                ps.setString(13, xuatXu);
                ps.setString(14, phanLoai);
                ps.setString(15, maSanPham);

                int affectedRows = ps.executeUpdate();

                return affectedRows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateSoLuongAndGenerateImei(String maSanPham, List<String> imeiList) {
        // Kiểm tra xem danh sách IMEI có rỗng không
        if (imeiList == null || imeiList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Danh sách IMEI không được rỗng.");
            return false;
        }

        String updateSoLuongSql = "UPDATE [dbo].[SanPham] SET So_Luong = So_Luong + ? WHERE Ma_San_Pham = ?";
        String insertImeiSql = "INSERT INTO [dbo].[Imei] ([Ma_Imei], [Trang_Thai], [ID_San_Pham]) VALUES (?, 1, (SELECT ID_San_Pham FROM SanPham WHERE Ma_San_Pham = ?))";

        try (Connection conn = Dbconnect.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            try (PreparedStatement updateSoLuongPs = conn.prepareStatement(updateSoLuongSql); PreparedStatement insertImeiPs = conn.prepareStatement(insertImeiSql)) {

                // Bước 1: Cập nhật số lượng sản phẩm
                updateSoLuongPs.setInt(1, imeiList.size()); // Cập nhật số lượng theo số lượng IMEI mới
                updateSoLuongPs.setString(2, maSanPham);
                int affectedRows = updateSoLuongPs.executeUpdate();

                if (affectedRows == 0) {
                    conn.rollback(); // Quay lại nếu mã sản phẩm không tồn tại
                    JOptionPane.showMessageDialog(null, "Mã sản phẩm không tồn tại.");
                    return false;
                }

                // Bước 2: Thêm IMEI tương ứng với số lượng sản phẩm
                for (String imei : imeiList) {
                    if (isImeiExists(imei, conn)) { // Kiểm tra xem IMEI đã tồn tại
                        conn.rollback(); // Quay lại nếu trùng IMEI
                        JOptionPane.showMessageDialog(null, "IMEI đã tồn tại");
                        return false; // Trả về false ngay lập tức nếu trùng IMEI
                    }

                    insertImeiPs.setString(1, imei);
                    insertImeiPs.setString(2, maSanPham); // Truyền maSanPham để lấy ID sản phẩm
                    insertImeiPs.addBatch();
                }

                // Thực thi batch để thêm tất cả IMEI cùng lúc
                insertImeiPs.executeBatch();
                conn.commit(); // Cam kết giao dịch
                return true; // Cập nhật và thêm IMEI thành công
            } catch (SQLException e) {
                conn.rollback(); // Quay lại nếu có lỗi
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu có lỗi xảy ra
    }

    private String generateRandomImei(Random random) {
        StringBuilder imei = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            imei.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0-9
        }
        return imei.toString();
    }

    // Hàm tạo số IMEI ngẫu nhiên với 15 chữ số
    private String generateIMEI() {
        StringBuilder imei = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            imei.append(random.nextInt(10));
        }
        return imei.toString();
    }

    public Boolean delete(String maSanPham) {
        String sql = "UPDATE [dbo].[SanPham] SET [Trang_Thai] = 1 WHERE Ma_San_Pham = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSanPham);

            int affectedRows = ps.executeUpdate(); // Sử dụng executeUpdate cho câu lệnh UPDATE
            return affectedRows > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public Boolean deleteDB(String maSanPham) {
        String sql = "UPDATE [dbo].[SanPham] SET [Trang_Thai] = 0 WHERE Ma_San_Pham = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSanPham);

            int affectedRows = ps.executeUpdate(); // Sử dụng executeUpdate cho câu lệnh UPDATE
            return affectedRows > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

}

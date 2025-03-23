/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.HoaDonModel;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hadac
 */
public class HoaDonRepository {

    Connection conn = null;
    PreparedStatement sttm = null;
    private HoaDonModel hoaDonModel;
    private ResultSet rs = null;

    public ArrayList<HoaDonModel> getFilteredData(java.util.Date tu, java.util.Date den) {
        ArrayList<HoaDonModel> list = new ArrayList<>();
        String sSql = "SELECT hd.*, kh.Ten AS TenKhachHang, nv.Ho_Ten AS TenNhanVien "
                + "FROM HoaDon hd "
                + "LEFT JOIN KhachHang kh ON hd.ID_Khach_Hang = kh.ID_Khach_Hang "
                + "LEFT JOIN NhanVien nv ON hd.ID_Nhan_Vien = nv.ID_Nhan_Vien "
                + "WHERE hd.Ngay_Thanh_Toan >= ? AND hd.Ngay_Thanh_Toan <= ?";

        try {
            conn = Dbconnect.getConnection();
            sttm = conn.prepareStatement(sSql);

            sttm.setDate(1, new java.sql.Date(tu.getTime()));
            sttm.setDate(2, new java.sql.Date(den.getTime()));

            rs = sttm.executeQuery();

            while (rs.next()) {
                HoaDonModel nv = new HoaDonModel();
                nv.setIdHoaDon(rs.getInt(1));
                nv.setMaHoaDon(rs.getString(2));
                nv.setNgayTao(rs.getDate(3));
                nv.setNgayThanhToan(rs.getDate(4));
                nv.setTongGia(rs.getFloat(5));
                nv.setHinhThucThanhToan(rs.getString(6));
                nv.setTenNguoiNhan(rs.getString(7));
                nv.setSdt(rs.getString(8));
                nv.setDiaChi(rs.getString(9));
                nv.setTrangThai(rs.getInt(10));
                nv.setIdKhachHang(rs.getInt(11));
                nv.setIdNhanVien(rs.getInt(13));
                nv.setIdGiamGia(rs.getInt(12));

                nv.setTenKhachHang(rs.getString("TenKhachHang"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));

                list.add(nv);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDonModel> getAll(String keyword) {
        List<HoaDonModel> ls = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String sSql = "SELECT hd.*, kh.Ten AS TenKhachHang, nv.Ho_Ten AS TenNhanVien "
                    + "FROM HoaDon hd "
                    + "LEFT JOIN KhachHang kh ON hd.ID_Khach_Hang = kh.ID_Khach_Hang "
                    + "LEFT JOIN NhanVien nv ON hd.ID_Nhan_Vien = nv.ID_Nhan_Vien "
                    + "WHERE (hd.Ma_Hoa_Don LIKE ? OR "
                    + "nv.Ho_Ten LIKE ? OR "
                    + "kh.Ten LIKE ? OR "
                    + "hd.Trang_Thai LIKE ?)";

            conn = Dbconnect.getConnection();
            ps = conn.prepareStatement(sSql);

            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchKeyword);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel nv = new HoaDonModel();
                nv.setIdHoaDon(rs.getInt(1));
                nv.setMaHoaDon(rs.getString(2));
                nv.setNgayTao(rs.getDate(3));
                nv.setNgayThanhToan(rs.getDate(4));
                nv.setTongGia(rs.getFloat(5));
                nv.setHinhThucThanhToan(rs.getString(6));
                nv.setTenNguoiNhan(rs.getString(7));
                nv.setSdt(rs.getString(8));
                nv.setDiaChi(rs.getString(9));
                nv.setTrangThai(rs.getInt(10));
                nv.setIdKhachHang(rs.getInt(11));
                nv.setIdNhanVien(rs.getInt(13));
                nv.setIdGiamGia(rs.getInt(12));
                nv.setTenKhachHang(rs.getString("TenKhachHang"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                ls.add(nv);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        } finally {
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
        return ls;
    }

}

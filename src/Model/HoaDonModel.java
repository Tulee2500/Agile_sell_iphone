/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author hadac
 */
public class HoaDonModel {

    private int idHoaDon;
    private String maHoaDon;
    private Date ngayTao;
    private Date ngayThanhToan;
    private float tongGia;
    private String hinhThucThanhToan;
    private String tenNguoiNhan;
    private String sdt;
    private String diaChi;
    private int trangThai;
    private int idKhachHang;
    private int idGiamGia;
    private int idNhanVien;
    private String tenKhachHang;
    private String tenNhanVien;
    public HoaDonModel() {
    }

    public HoaDonModel(int idHoaDon, String maHoaDon, Date ngayTao, Date ngayThanhToan, float tongGia, String hinhThucThanhToan, String tenNguoiNhan, String sdt, String diaChi, int trangThai, int idKhachHang, int idGiamGia, int idNhanVien) {
        this.idHoaDon = idHoaDon;
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.tongGia = tongGia;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tenNguoiNhan = tenNguoiNhan;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.idKhachHang = idKhachHang;
        this.idGiamGia = idGiamGia;
        this.idNhanVien = idNhanVien;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public float getTongGia() {
        return tongGia;
    }

    public void setTongGia(float tongGia) {
        this.tongGia = tongGia;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public int getIdGiamGia() {
        return idGiamGia;
    }

    public void setIdGiamGia(int idGiamGia) {
        this.idGiamGia = idGiamGia;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    @Override
    public String toString() {
        return "HoaDonModel{" + "idKhachHang=" + idKhachHang + ", idNhanVien=" + idNhanVien + '}';
    }

}

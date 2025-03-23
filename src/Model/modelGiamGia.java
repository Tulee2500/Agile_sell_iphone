/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Tu PC
 */
public class modelGiamGia {

    private int IDNhanVien;
    private String maNhanVien;
    private int ID;
    private String maGiamGia;
    private String tenChuongTrinh;
    private String moTa;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int soLuong;
    private int kieuGiam;
    private float giaTriDHToiThieu;
    private int mucGiamGia;
    private int mucGiamGiaToiDa;
    private int trangThai;

    public modelGiamGia(int IDNhanVien, int ID, String maGiamGia, String tenChuongTrinh, String moTa, Date ngayBatDau, Date ngayKetThuc, int soLuong, int kieuGiam, float giaTriDHToiThieu, int mucGiamGia, int mucGiamGiaToiDa, int trangThai) {
        this.IDNhanVien = IDNhanVien;
        this.ID = ID;
        this.maGiamGia = maGiamGia;
        this.tenChuongTrinh = tenChuongTrinh;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.kieuGiam = kieuGiam;
        this.giaTriDHToiThieu = giaTriDHToiThieu;
        this.mucGiamGia = mucGiamGia;
        this.mucGiamGiaToiDa = mucGiamGiaToiDa;
        this.trangThai = trangThai;
    }

    public modelGiamGia() {
    }

    public modelGiamGia(int IDNhanVien, String maGiamGia, String tenChuongTrinh, String moTa, Date ngayBatDau, Date ngayKetThuc, int soLuong, int kieuGiam, float giaTriDHToiThieu, int mucGiamGia, int mucGiamGiaToiDa, int trangThai) {
        this.IDNhanVien = IDNhanVien;
        this.maGiamGia = maGiamGia;
        this.tenChuongTrinh = tenChuongTrinh;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.kieuGiam = kieuGiam;
        this.giaTriDHToiThieu = giaTriDHToiThieu;
        this.mucGiamGia = mucGiamGia;
        this.mucGiamGiaToiDa = mucGiamGiaToiDa;
        this.trangThai = trangThai;
    }

    public modelGiamGia(String maGiamGia, String tenChuongTrinh, String moTa, Date ngayBatDau, Date ngayKetThuc, int soLuong, int kieuGiam, float giaTriDHToiThieu, int mucGiamGia, int mucGiamGiaToiDa, int trangThai) {
        this.maGiamGia = maGiamGia;
        this.tenChuongTrinh = tenChuongTrinh;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.kieuGiam = kieuGiam;
        this.giaTriDHToiThieu = giaTriDHToiThieu;
        this.mucGiamGia = mucGiamGia;
        this.mucGiamGiaToiDa = mucGiamGiaToiDa;
        this.trangThai = trangThai;
    }

    
    public int getIDNhanVien() {
        return IDNhanVien;
    }

    public void setIDNhanVien(int IDNhanVien) {
        this.IDNhanVien = IDNhanVien;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public void setTenChuongTrinh(String tenChuongTrinh) {
        this.tenChuongTrinh = tenChuongTrinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getKieuGiam() {
        return kieuGiam;
    }

    public void setKieuGiam(int kieuGiam) {
        this.kieuGiam = kieuGiam;
    }

    public float getGiaTriDHToiThieu() {
        return giaTriDHToiThieu;
    }

    public void setGiaTriDHToiThieu(float giaTriDHToiThieu) {
        this.giaTriDHToiThieu = giaTriDHToiThieu;
    }

    public int getMucGiamGia() {
        return mucGiamGia;
    }

    public void setMucGiamGia(int mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }

    public int getMucGiamGiaToiDa() {
        return mucGiamGiaToiDa;
    }

    public void setMucGiamGiaToiDa(int mucGiamGiaToiDa) {
        this.mucGiamGiaToiDa = mucGiamGiaToiDa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    

    public Object[] toDataRow() {
        return new Object[]{this.getMaGiamGia(), this.getTenChuongTrinh(), this.getMoTa(), this.getNgayBatDau(), this.getNgayKetThuc(), this.getSoLuong(), this.getKieuGiam()==1?"Phần Trăm":"VND" , this.getGiaTriDHToiThieu(), this.getMucGiamGia(), this.getMucGiamGiaToiDa(), this.getTrangThai() == 1 ? "Đang diễn ra" : "Kết thúc"};
    }
}

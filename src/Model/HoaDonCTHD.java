/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class HoaDonCTHD {

    private int idHdct;          // ID_HDCT
    private int soLuong;         // So_Luong
    private double gia;          // Gia
    private int trangThai;       // Trang_Thai
    private int idHoaDon;        // ID_Hoa_Don
    private String TenSanPham;       // ID_San_Pham
    private int idSanPham;       // ID_San_Pham
    private String Ma_Imei;
    public HoaDonCTHD() {
    }

    public HoaDonCTHD(int idHdct, int soLuong, double gia, int trangThai, int idHoaDon, String TenSanPham, int idSanPham, String Ma_Imei) {
        this.idHdct = idHdct;
        this.soLuong = soLuong;
        this.gia = gia;
        this.trangThai = trangThai;
        this.idHoaDon = idHoaDon;
        this.TenSanPham = TenSanPham;
        this.idSanPham = idSanPham;
        this.Ma_Imei = Ma_Imei;
    }

    

    public HoaDonCTHD(int soLuong, double gia, String TenSanPham, int idSanPham, String Ma_Imei) {
        this.soLuong = soLuong;
        this.gia = gia;
        this.TenSanPham = TenSanPham;
        this.idSanPham = idSanPham;
        this.Ma_Imei = Ma_Imei;
    }
    
    

    public String getMa_Imei() {
        return Ma_Imei;
    }

    public void setMa_Imei(String Ma_Imei) {
        this.Ma_Imei = Ma_Imei;
    }

    

    public int getIdHdct() {
        return idHdct;
    }

    public void setIdHdct(int idHdct) {
        this.idHdct = idHdct;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }
    
    
}

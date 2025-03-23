/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.google.zxing.qrcode.decoder.Mode;
import java.util.Date;

/**
 *
 * @author nothank
 */
public class ModelSanPham {

    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private Date ngayTao;
    private int soLuong;
    private float giaBan;
    private float giaNhap;
    private String hinhAnh;
    private String rom;
    private String mauSac;
    private String ram;
    private String kichThuoc;
    private String dungLuongPin;
    private String cpu;
    private String xuatXu;
    private String phanLoai;

    public ModelSanPham() {
    }

    public ModelSanPham(String maSanPham, String tenSanPham, String phanLoai, Date ngayTao, int soLuong, String rom, String mauSac, String ram, String kichThuoc, String dungLuongPin, String cpu, String xuatXu, float giaBan) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.phanLoai = phanLoai;
        this.ngayTao = ngayTao;
        this.soLuong = soLuong;
        this.rom = rom;
        this.mauSac = mauSac;
        this.ram = ram;
        this.kichThuoc = kichThuoc;
        this.dungLuongPin = dungLuongPin;
        this.cpu = cpu;
        this.xuatXu = xuatXu;
        this.giaBan = giaBan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public ModelSanPham(String maSanPham, String tenSanPham, String moTa, Date ngayTao, int soLuong, float giaBan, float giaNhap, String hinhAnh, String rom, String mauSac, String ram, String kichThuoc, String dungLuongPin, String cpu, String xuatXu, String phanLoai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.hinhAnh = hinhAnh;
        this.rom = rom;
        this.mauSac = mauSac;
        this.ram = ram;
        this.kichThuoc = kichThuoc;
        this.dungLuongPin = dungLuongPin;
        this.cpu = cpu;
        this.xuatXu = xuatXu;
        this.phanLoai = phanLoai;
    }
    

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getDungLuongPin() {
        return dungLuongPin;
    }

    public void setDungLuongPin(String dungLuongPin) {
        this.dungLuongPin = dungLuongPin;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

}

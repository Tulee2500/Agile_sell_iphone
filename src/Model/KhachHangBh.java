/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javax.swing.JOptionPane;

/**
 *
 * @author nothank
 */
public class KhachHangBh {

    private String Ma;
    private String ten;
    private String SDT;
    private Boolean gioiTinh;

    public KhachHangBh() {
    }

    public KhachHangBh(String Ma, String ten, String SDT, Boolean gioiTinh) {
        this.Ma = Ma;
        this.ten = ten;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
    }
    
    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    

    public String getMa() {
        return Ma;
    }

    public void setMa(String Ma) {
        this.Ma = Ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        // Regex kiểm tra số điện thoại với 10 chữ số, bắt đầu bằng số 0
        String regex = "^0\\d{9}$";

        if (SDT.matches(regex)) {
            this.SDT = SDT; // Gán giá trị nếu đúng định dạng
        } else {
            // Hiển thị hộp thoại thông báo lỗi
            JOptionPane.showMessageDialog(null, "Số Điện Thoại Không Đúng Định Dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class PhanLoai {
    private int ID_Phan_Loai;
    private String Phan_Loai;

    public PhanLoai() {
    }

    public PhanLoai(int ID_Phan_Loai, String Phan_Loai) {
        this.ID_Phan_Loai = ID_Phan_Loai;
        this.Phan_Loai = Phan_Loai;
    }

    public int getID_Phan_Loai() {
        return ID_Phan_Loai;
    }

    public void setID_Phan_Loai(int ID_Phan_Loai) {
        this.ID_Phan_Loai = ID_Phan_Loai;
    }

    public String getPhan_Loai() {
        return Phan_Loai;
    }

    public void setPhan_Loai(String Phan_Loai) {
        this.Phan_Loai = Phan_Loai;
    }
    
}

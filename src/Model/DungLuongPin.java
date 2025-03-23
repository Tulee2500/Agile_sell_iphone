/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class DungLuongPin {
    private int ID_Dung_Luong_Pin;
    private String Dung_Luong_Pin;

    public DungLuongPin() {
    }

    public DungLuongPin(int ID_Dung_Luong_Pin, String Dung_Luong_Pin) {
        this.ID_Dung_Luong_Pin = ID_Dung_Luong_Pin;
        this.Dung_Luong_Pin = Dung_Luong_Pin;
    }

    public int getID_Dung_Luong_Pin() {
        return ID_Dung_Luong_Pin;
    }

    public void setID_Dung_Luong_Pin(int ID_Dung_Luong_Pin) {
        this.ID_Dung_Luong_Pin = ID_Dung_Luong_Pin;
    }

    public String getDung_Luong_Pin() {
        return Dung_Luong_Pin;
    }

    public void setDung_Luong_Pin(String Dung_Luong_Pin) {
        this.Dung_Luong_Pin = Dung_Luong_Pin;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class KichThuoc {
    private int ID_Kich_Thuoc;
    private String Kich_Thuoc;

    public KichThuoc() {
    }

    public KichThuoc(int ID_Kich_Thuoc, String Kich_Thuoc) {
        this.ID_Kich_Thuoc = ID_Kich_Thuoc;
        this.Kich_Thuoc = Kich_Thuoc;
    }

    public int getID_Kich_Thuoc() {
        return ID_Kich_Thuoc;
    }

    public void setID_Kich_Thuoc(int ID_Kich_Thuoc) {
        this.ID_Kich_Thuoc = ID_Kich_Thuoc;
    }

    public String getKich_Thuoc() {
        return Kich_Thuoc;
    }

    public void setKich_Thuoc(String Kich_Thuoc) {
        this.Kich_Thuoc = Kich_Thuoc;
    }
    
}

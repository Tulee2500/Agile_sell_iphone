/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class Imei {

    private int ID_Imei;
    private String Ma_Imei;
    private int Trang_thai;
    private String Ma_San_Pham;
    private String Ten_San_Pham;

    public Imei() {
    }

    public Imei(int ID_Imei, String Ma_Imei, int Trang_thai, String Ma_San_Pham, String Ten_San_Pham) {
        this.ID_Imei = ID_Imei;
        this.Ma_Imei = Ma_Imei;
        this.Trang_thai = Trang_thai;
        this.Ma_San_Pham = Ma_San_Pham;
        this.Ten_San_Pham = Ten_San_Pham;
    }

    public int getID_Imei() {
        return ID_Imei;
    }

    public void setID_Imei(int ID_Imei) {
        this.ID_Imei = ID_Imei;
    }

    public String getMa_Imei() {
        return Ma_Imei;
    }

    public void setMa_Imei(String Ma_Imei) {
        this.Ma_Imei = Ma_Imei;
    }

    public int getTrang_thai() {
        return Trang_thai;
    }

    public void setTrang_thai(int Trang_thai) {
        this.Trang_thai = Trang_thai;
    }

    public String getMa_San_Pham() {
        return Ma_San_Pham;
    }

    public void setMa_San_Pham(String Ma_San_Pham) {
        this.Ma_San_Pham = Ma_San_Pham;
    }

    public String getTen_San_Pham() {
        return Ten_San_Pham;
    }

    public void setTen_San_Pham(String Ten_San_Pham) {
        this.Ten_San_Pham = Ten_San_Pham;
    }

    

}

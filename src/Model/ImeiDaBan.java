 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class ImeiDaBan {
    private int ID_Imei_Da_Ban;
    private String Ma_Imei_Da_Ban;
    private int ID_HDCT;

    public ImeiDaBan() {
    }

    public ImeiDaBan(int ID_Imei_Da_Ban, String Ma_Imei_Da_Ban, int ID_HDCT) {
        this.ID_Imei_Da_Ban = ID_Imei_Da_Ban;
        this.Ma_Imei_Da_Ban = Ma_Imei_Da_Ban;
        this.ID_HDCT = ID_HDCT;
    }

    public int getID_Imei_Da_Ban() {
        return ID_Imei_Da_Ban;
    }

    public void setID_Imei_Da_Ban(int ID_Imei_Da_Ban) {
        this.ID_Imei_Da_Ban = ID_Imei_Da_Ban;
    }

    public String getMa_Imei_Da_Ban() {
        return Ma_Imei_Da_Ban;
    }

    public void setMa_Imei_Da_Ban(String Ma_Imei_Da_Ban) {
        this.Ma_Imei_Da_Ban = Ma_Imei_Da_Ban;
    }

    public int getID_HDCT() {
        return ID_HDCT;
    }

    public void setID_HDCT(int ID_HDCT) {
        this.ID_HDCT = ID_HDCT;
    }
    
}

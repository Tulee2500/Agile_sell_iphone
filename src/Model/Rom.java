/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class Rom {
    private int ID_Rom;
    private String Rom;

    public Rom(String Rom) {
        this.Rom = Rom;
    }

    public Rom() {
    }

    public Rom(int ID_Rom, String Rom) {
        this.ID_Rom = ID_Rom;
        this.Rom = Rom;
    }

    public int getID_Rom() {
        return ID_Rom;
    }

    public void setID_Rom(int ID_Rom) {
        this.ID_Rom = ID_Rom;
    }

    public String getRom() {
        return Rom;
    }

    public void setRom(String Rom) {
        this.Rom = Rom;
    }
    
}

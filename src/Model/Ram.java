/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class Ram {
    private int ID_Ram;
    private String Ram;

    public Ram() {
    }

    public Ram(int ID_Ram, String Ram) {
        this.ID_Ram = ID_Ram;
        this.Ram = Ram;
    }

    public int getID_Ram() {
        return ID_Ram;
    }

    public void setID_Ram(int ID_Ram) {
        this.ID_Ram = ID_Ram;
    }

    public String getRam() {
        return Ram;
    }

    public void setRam(String Ram) {
        this.Ram = Ram;
    }
    
}

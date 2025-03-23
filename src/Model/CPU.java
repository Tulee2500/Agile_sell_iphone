/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nothank
 */
public class CPU {
    private int ID_CPU;
    private String CPU;

    public CPU() {
    }

    public CPU(int ID_CPU, String CPU) {
        this.ID_CPU = ID_CPU;
        this.CPU = CPU;
    }

    public int getID_CPU() {
        return ID_CPU;
    }

    public void setID_CPU(int ID_CPU) {
        this.ID_CPU = ID_CPU;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }
    
}

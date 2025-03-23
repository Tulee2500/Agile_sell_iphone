/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1trangchu.DBConnect;

import Model.nhanvienLoginModel;


/**
 *
 * @author hadac
 */
public class ShareUtil {
    public static nhanvienLoginModel USER = null;
    
     public static void logoff() {
        ShareUtil.USER = null;
    }

    public static boolean authenticated() {
        return ShareUtil.USER != null;
    }
}

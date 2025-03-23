/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.Imei;
import Model.ImeiDaBan;
import Model.SanPhamModel;
import duan1trangchu.DBConnect.Dbconnect;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nothank
 */
public class ImeiDaBanRepository {

    public ArrayList<ImeiDaBan> getAll(int page, int limit) {
        String sql = "SELECT [ID_Imei_Da_Ban], "
                + "       [Ma_Imei_Da_Ban], "
                + "       [ID_HDCT] "
                + "FROM [dbo].[ImeiDaBan] "
                + "WHERE Trang_Thai = 0 "
                + "ORDER BY [ID_Imei_Da_Ban] " // Thêm ORDER BY để tránh lỗi OFFSET
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<ImeiDaBan> arrImeiDaBan = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Dbconnect.getConnection();
            ps = conn.prepareStatement(sql);
            int offset = (page - 1) * limit; // Tính toán offset
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                int ID_Imei_Da_Ban = rs.getInt("ID_Imei_Da_Ban");
                String MaImeiDaBan = rs.getString("Ma_Imei_Da_Ban");
                int idHDCT = rs.getInt("ID_HDCT");

                // Tạo đối tượng ImeiDaBan
                ImeiDaBan imeiDaBan = new ImeiDaBan(ID_Imei_Da_Ban, MaImeiDaBan, idHDCT);
                arrImeiDaBan.add(imeiDaBan); // Thêm đối tượng vào danh sách
            }

            return arrImeiDaBan; // Trả về danh sách sau khi truy vấn kết thúc

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }

    public ArrayList<ImeiDaBan> getName() {
        String sql = "select Ma_Imei_Da_Ban from ImeiDaBan WHERE Trang_Thai = 0";

        ArrayList<ImeiDaBan> arrImei = new ArrayList<>();
        try {
            Connection conn = Dbconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Rom
                ImeiDaBan r = new ImeiDaBan();
                r.setMa_Imei_Da_Ban(rs.getString("Ma_Imei_Da_Ban"));
                arrImei.add(r); // Thêm vào danh sách
            }

            return arrImei; // Trả về danh sách sau khi vòng lặp kết thúc

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrImei; // Trả về danh sách rỗng nếu có lỗi
    }

}

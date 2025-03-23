/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.HoaDonCTHD;
import Model.PhanLoai;
import Model.SanPhamBH;
import Model.SanPhamModelBH;
import Repository.PhanLoaiRepository;
import Repository.SanPhamBHRepository;
import Repository.SanPhamRepository;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.MultiFormatOneDReader;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import raven.application.form.MainForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hpsf.Array;

/**
 *
 * @author nothank
 */
public class BanHang extends javax.swing.JPanel implements Runnable, ThreadFactory {

    private SanPhamBHRepository repoSP = new SanPhamBHRepository();
    private PhanLoaiRepository pl = new PhanLoaiRepository();
    private DefaultTableModel dtm;
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    /**
     * Creates new form NewJPanel
     */
    public BanHang() {
        initComponents();

        dateTao.setDate(new Date());
        showNameKH(repoSP.getNameKH());
        showNameNV(repoSP.getNameNV());
        showNameGiamGia(repoSP.getNameGG());
        this.showHoaDonTreo(repoSP.getHD());
        getGioHang();
        getSP();
    }

    public void getGioHang() {

        dtm = (DefaultTableModel) tblGioHang.getModel();
        dtm.setRowCount(0);
        String ma = lblMaHoaDon.getText();
        for (HoaDonCTHD hd : repoSP.getHoaDonChiTietByMaHoaDon(ma)) {
            Object[] row = {
                hd.getIdSanPham(),
                hd.getTenSanPham(),
                hd.getSoLuong(),
                hd.getGia(),
                hd.getMa_Imei()
            };
            dtm.addRow(row);
        }
    }

    public void getSP() {
        dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0); // Xóa tất cả các hàng hiện có

        // Giả định rằng repoSP.getSanPhamWithImei() trả về danh sách các sản phẩm có thông tin IMEI
        for (SanPhamModelBH sp : repoSP.getAllSanPhamWithDetails()) {
            Object[] row = {
                sp.getID(),
                sp.getMaSanPham(),
                sp.getTenSanPham(),
                sp.getSoLuong(),
                sp.getGiaBan(),
                sp.getCpu(),
                sp.getRam(),
                sp.getRom(),
                sp.getMauSac(),
                sp.getKichThuoc(),
                sp.getDungLuongPin(),
                sp.getXuatXu(),
                sp.getPhanLoai()
            };
            dtm.addRow(row); // Thêm hàng vào bảng
        }
    }

    private void showNameKH(ArrayList<Model.KhachHang> arr) {
        for (Model.KhachHang kh : arr) {
            cbbKhachHang.addItem(kh.getMaKhachHang().toString());
        };
    }

    private void showNameNV(ArrayList<Model.NhanVien> arr) {
        for (Model.NhanVien kh : arr) {
            cbbNhanVien.addItem(kh.getMaNhanVien().toString());
        };
    }

    private void showNameGiamGia(ArrayList<Model.MaGiamGia> arr) {
        for (Model.MaGiamGia kh : arr) {
            cbbGiamGia.addItem(kh.getMaGiamGia().toString());
        };
    }

    private void showHoaDonTreo(ArrayList<Model.HoaDon> arr) {
        // Tạo một DefaultListModel để chứa các hóa đơn
        DefaultListModel<String> listModel = new DefaultListModel<>();

        // Lặp qua danh sách hóa đơn và thêm vào mô hình
        for (Model.HoaDon hoaDon : arr) {
            listModel.addElement(hoaDon.getMaHoaDon());
        }

        // Gán mô hình vào JList
        jList1.setModel(listModel);
    }

    private double tinhTongTien() {
        double total = 0.0; // Khởi tạo biến tổng
        int columnIndex = 3; // Chỉ số cột chứa giá trị cần tính (giá)

        // Lặp qua tất cả các hàng trong bảng để tính tổng
        for (int rowIndex = 0; rowIndex < tblGioHang.getRowCount(); rowIndex++) {
            Object value = tblGioHang.getValueAt(rowIndex, columnIndex);

            // Kiểm tra xem giá trị có phải là số hay không
            if (value instanceof Number) {
                total += ((Number) value).doubleValue(); // Cộng dồn tổng
            } else if (value instanceof String) {
                try {
                    total += Double.parseDouble((String) value); // Chuyển đổi chuỗi thành số và cộng
                } catch (NumberFormatException e) {
                    System.out.println("Giá trị không hợp lệ ở hàng " + rowIndex + ": " + value);
                }
            }
        }
        return total;
    }

    private double tinhTienSauGiamGia(double tongTien) {
        String keyword = (String) cbbGiamGia.getSelectedItem();
        if (keyword != null && !keyword.equalsIgnoreCase("All")) {
            Double mucGiamGia = this.repoSP.layMucGiamGia(keyword);

            if (mucGiamGia != null) {
                float GiamGia = mucGiamGia.floatValue();
                if (GiamGia > 1 && GiamGia < 100) {
                    return tongTien - (tongTien * GiamGia / 100); // Giảm giá theo phần trăm
                } else if (GiamGia >= 100) {
                    return tongTien - GiamGia; // Giảm giá theo số tiền cụ thể
                } else {
                    JOptionPane.showMessageDialog(this, "Giảm giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                lblSoDuocGiamr.setText("Không có mức giảm giá");
            }
        }
        return tongTien; // Nếu không có giảm giá, trả về tổng tiền ban đầu
    }

    private void capNhatGiaoDien() {
        double tongTien = tinhTongTien();
        lblTongTien.setText(String.format("%.2f", tongTien)); // Cập nhật tổng tiền

        double tienSauGiamGia = tinhTienSauGiamGia(tongTien);
        lblThanhtien.setText(String.format("%.2f", tienSauGiamGia)); // Cập nhật tiền sau giảm giá

        String tienKhachTraStr = txtTienKhachTra.getText();
        if (!tienKhachTraStr.isEmpty()) {
            try {
                double tienKhachTra = Double.parseDouble(tienKhachTraStr);
                lblTienKhach.setText(String.format("%.2f", tienKhachTra)); // Cập nhật tiền khách trả

                double traLai = tienKhachTra - tienSauGiamGia;
                if (traLai < 0) {
                    txtTraLai.setText("Thiếu tiền: " + String.format("%.2f", -traLai));
                } else {
                    txtTraLai.setText(String.format("%.2f", traLai)); // Bao gồm cả trường hợp traLai = 0
                }
            } catch (NumberFormatException e) {
                lblTienKhach.setText("Số tiền không hợp lệ");
                txtTraLai.setText("");
            }
        } else {
            lblTienKhach.setText("");
            txtTraLai.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        tblTao = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txaGhiChu = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cbbGiamGia = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        lblTienKhach = new javax.swing.JLabel();
        txtTraLai = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        btnThemSanPham = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txaDiaChi = new javax.swing.JTextArea();
        dateTao = new com.toedter.calendar.JDateChooser();
        txtTienKhachTra = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        PanelCam = new javax.swing.JPanel();
        btnTatCam = new javax.swing.JButton();
        btnBatCam = new javax.swing.JButton();
        lblMaHoaDon = new javax.swing.JLabel();
        btnXoaSanPham = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        lblThanhtien = new javax.swing.JLabel();
        lblSoDuocGiamr = new javax.swing.JLabel();
        cbbNhanVien = new javax.swing.JComboBox<>();
        cbbKhachHang = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cbbChuyenKhoan = new javax.swing.JComboBox<>();
        lblPrint = new javax.swing.JLabel();
        btnSearchSDT = new javax.swing.JButton();
        txtTenKhachHang = new javax.swing.JTextField();
        btnAddKH = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(719, 521));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Tìm kiếm sản phẩm");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 130, -1));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, 201, -1));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_Sản_Phẩm", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Giá Bán", "CPU", "Ram", "Rom", "Màu sắc", "Kích Thước", "Pin", "Xuất Xứ", "Phân Loại"
            }
        ));
        tblSanPham.setRowHeight(40);
        jScrollPane1.setViewportView(tblSanPham);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 720, 300));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Giá", "Imei"
            }
        ));
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 600, 233));

        jLabel4.setText("Giỏ Hàng Mã HD:  ");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 120, -1));

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList1);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 80, 680));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Hóa Đơn Treo");
        jLabel5.setAutoscrolls(true);
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setName(""); // NOI18N
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        tblTao.setText("Tạo Hóa Đơn");
        tblTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblTaoActionPerformed(evt);
            }
        });
        add(tblTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, -1, 27));

        jLabel6.setText("Khách Hàng");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, 90, -1));

        jLabel7.setText("Nhân Viên");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 120, 90, -1));

        jLabel8.setText("Ghi Chú");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 150, 80, -1));

        txaGhiChu.setColumns(20);
        txaGhiChu.setRows(5);
        jScrollPane5.setViewportView(txaGhiChu);

        add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 190, 50));

        jLabel11.setText("Ngày Bán");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 270, 80, -1));

        jLabel13.setText("Mã Giảm Giá");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, 80, -1));
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 340, 110, -1));

        jLabel14.setText("Số Điện Thoại");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 340, 90, 20));

        cbbGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Null" }));
        cbbGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiamGiaActionPerformed(evt);
            }
        });
        add(cbbGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 310, 130, -1));

        jLabel15.setText("Tổng Tiền:");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 450, 90, 26));

        jLabel16.setText("Giảm Giá:");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 480, 80, 26));

        jLabel17.setText("Tiền Khách Trả:");
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 540, 80, 26));

        jLabel18.setText("Trả Lại:");
        add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 580, 90, 26));

        lblTongTien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblTongTienKeyPressed(evt);
            }
        });
        add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 450, 140, 26));
        add(lblGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 480, 130, 26));
        add(lblTienKhach, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 540, 130, 26));
        add(txtTraLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 580, 140, 26));

        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 610, 140, 30));

        btnThemSanPham.setText("Thêm SP");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });
        add(btnThemSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 350, 80, 30));

        jLabel9.setText("Địa Chỉ");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, 80, -1));

        txaDiaChi.setColumns(20);
        txaDiaChi.setRows(5);
        jScrollPane6.setViewportView(txaDiaChi);

        add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 210, 190, 50));
        add(dateTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 270, 190, -1));

        txtTienKhachTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachTraActionPerformed(evt);
            }
        });
        txtTienKhachTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTienKhachTraKeyPressed(evt);
            }
        });
        add(txtTienKhachTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 420, 140, -1));

        jLabel23.setText("Tiền Khách Trả");
        add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 420, 90, 20));

        PanelCam.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelCam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(PanelCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 130, 60));

        btnTatCam.setText("Tắt Cam");
        btnTatCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTatCamActionPerformed(evt);
            }
        });
        add(btnTatCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 80, -1));

        btnBatCam.setText("Bật Cam");
        btnBatCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatCamActionPerformed(evt);
            }
        });
        add(btnBatCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 80, -1));
        add(lblMaHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 50, 20));

        btnXoaSanPham.setText("Xóa SP");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });
        add(btnXoaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 80, 30));

        jLabel19.setText("Thành Tiền");
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 510, 90, 26));
        add(lblThanhtien, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 510, 140, 26));
        add(lblSoDuocGiamr, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 310, 50, 20));

        add(cbbNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 120, 190, -1));

        cbbKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Null" }));
        cbbKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbKhachHangActionPerformed(evt);
            }
        });
        add(cbbKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 60, 190, -1));

        jLabel24.setText("Hình Thức TT");
        add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 390, 80, 20));

        cbbChuyenKhoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền Mặt", "Chuyển Khoản" }));
        add(cbbChuyenKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 390, 140, -1));
        add(lblPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 50, 20));

        btnSearchSDT.setText("Search");
        btnSearchSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSDTActionPerformed(evt);
            }
        });
        add(btnSearchSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 340, 90, -1));

        txtTenKhachHang.setEnabled(false);
        add(txtTenKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 90, 190, -1));

        btnAddKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/299068_add_sign_icon.png"))); // NOI18N
        btnAddKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKHActionPerformed(evt);
            }
        });
        add(btnAddKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 60, 50, 50));
    }// </editor-fold>//GEN-END:initComponents
private String selectImeiFromList(List<String> imeiList) {
        if (imeiList == null || imeiList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có IMEI nào khả dụng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        // Tạo JComboBox với các IMEI khả dụng
        JComboBox<String> imeiComboBox = new JComboBox<>(imeiList.toArray(new String[0]));
        int option = JOptionPane.showConfirmDialog(null, imeiComboBox, "Chọn IMEI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        // Nếu người dùng nhấn OK, trả về IMEI được chọn
        if (option == JOptionPane.OK_OPTION) {
            return (String) imeiComboBox.getSelectedItem();
        }

        return null; // Nếu người dùng hủy, trả về null
    }

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed

        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Lấy các giá trị từ bảng và nhãn
            String id = tblSanPham.getValueAt(row, 0).toString();
            String gia = tblSanPham.getValueAt(row, 4).toString();
            String ma = lblMaHoaDon.getText();
            String soLuong = tblSanPham.getValueAt(row, 3).toString();

            // Kiểm tra tính hợp lệ của mã hóa đơn
            if (ma == null || ma.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mã hóa đơn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chuyển đổi các giá trị cần thiết
            int idSanPham = Integer.parseInt(id);
            int soLuongSP = Integer.parseInt(soLuong);
            float giaSP = Float.parseFloat(gia);

            // Kiểm tra tính hợp lệ của số lượng và giá sản phẩm
            if (soLuongSP <= 0) {
                JOptionPane.showMessageDialog(null, "Sản phẩm này đang hết hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (giaSP <= 0) {
                JOptionPane.showMessageDialog(null, "Giá sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Gọi phương thức thêm sản phẩm
            boolean result = repoSP.addSP(ma, giaSP, idSanPham);
            if (result) {
                JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Lấy danh sách IMEI khả dụng
                List<String> imeiList = repoSP.getImeiBySanPhamId(idSanPham);
                System.out.println("Ban Hang Trc");
                // Hiển thị hộp thoại chọn IMEI
            } else {
                JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // Cập nhật giao diện sản phẩm
            this.getSP();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        getGioHang(); // Cập nhật giỏ hàng
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void tblTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblTaoActionPerformed
        // TODO add your handling code here:

        String maHoaDon = this.repoSP.createHD();
        if (maHoaDon != null) {
            lblMaHoaDon.setText(maHoaDon);  // Hiển thị mã hóa đơn mới tạo lên nhãn
            JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công! Mã hóa đơn: " + maHoaDon, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Không thể tạo hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.showHoaDonTreo(repoSP.getHD());
    }//GEN-LAST:event_tblTaoActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        int row = jList1.getSelectedIndex();
        if (row == -1) {
            return;
        }
        String maHoaDon = jList1.getModel().getElementAt(row).toString();
        this.lblMaHoaDon.setText(maHoaDon);

        getSP();
        getGioHang();
    }//GEN-LAST:event_jList1MouseClicked

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        int row = tblGioHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ID = tblGioHang.getValueAt(row, 0).toString();
        String Imei = tblGioHang.getValueAt(row, 5).toString(); // Lấy IMEI nếu cần thiết
        int IDSanPham;

        try {
            IDSanPham = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maHoaDon = this.lblMaHoaDon.getText();

        try {
            // Gọi phương thức xóa hóa đơn chi tiết
            this.repoSP.xoaHoaDonCT(maHoaDon, IDSanPham, Imei);

            // Thông báo thành công
            JOptionPane.showMessageDialog(null, "Sản phẩm đã được xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Bắt lỗi và hiển thị thông báo
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi xóa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Cập nhật lại danh sách sản phẩm và giỏ hàng
        getSP();
        getGioHang();
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

        // Lấy giá trị từ trường tìm kiếm
        String keyword = txtSearch.getText().trim(); // lấy văn bản hiện tại từ txtSearch và loại bỏ khoảng trắng thừa

        // Thực hiện tìm kiếm sản phẩm
        List<SanPhamModelBH> results = this.repoSP.searchSanPham(keyword);

        // Cập nhật giao diện người dùng với kết quả tìm kiếm
        DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0); // xóa tất cả hàng trong bảng

        // Kiểm tra nếu không có kết quả trả về
        if (results == null || results.isEmpty()) {
            return; // không làm gì nếu không có kết quả
        }

        // Thêm các hàng mới vào bảng với kết quả tìm kiếm
        for (SanPhamModelBH sp : results) {
            Object[] row = {
                sp.getID(),
                sp.getMaSanPham(),
                sp.getTenSanPham(),
                sp.getSoLuong(),
                sp.getGiaBan(),
                sp.getCpu(),
                sp.getRam(),
                sp.getRom(),
                sp.getMauSac(),
                sp.getKichThuoc(),
                sp.getDungLuongPin(),
                sp.getXuatXu(),
                sp.getPhanLoai()
            };
            dtm.addRow(row); // Thêm hàng vào bảng
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        capNhatGiaoDien();
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void txtTienKhachTraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTraKeyPressed
        capNhatGiaoDien();
    }//GEN-LAST:event_txtTienKhachTraKeyPressed

    private void lblTongTienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTongTienKeyPressed
        // Lấy giá trị giảm giá từ label

    }//GEN-LAST:event_lblTongTienKeyPressed
    public ArrayList<SanPhamBH> GioHang() {
        int rowCount = tblGioHang.getRowCount(); // Lấy tổng số hàng của bảng
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        ArrayList<SanPhamBH> gioHang = new ArrayList<>();

        // Lặp qua tất cả các hàng trong bảng giỏ hàng
        for (int i = 0; i < rowCount; i++) {
            int ID = Integer.parseInt(tblGioHang.getValueAt(i, 0).toString()); // Lấy ID sản phẩm
            String Ma = tblGioHang.getValueAt(i, 1).toString(); // Lấy mã sản phẩm
            int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 2).toString()); // Lấy số lượng
            double Gia = Double.parseDouble(tblGioHang.getValueAt(i, 3).toString().replaceAll("[^0-9.]", ""));

            // Tạo đối tượng SanPhamBH và gán giá trị
            SanPhamBH bh = new SanPhamBH();
            bh.setIdSanPham(ID);
            bh.setTenSanPham(Ma);
            bh.setSoLuong(soLuong);
            bh.setGiaBan(Gia);

            // Thêm sản phẩm vào danh sách giỏ hàng
            gioHang.add(bh);
        }

        return gioHang; // Trả về danh sách sản phẩm trong giỏ hàng
    }

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        String maHoaDon = lblMaHoaDon.getText();
        String tongG = lblThanhtien.getText().trim(); // Loại bỏ khoảng trắng thừa

        // Kiểm tra tổng giá trị rỗng
        if (tongG.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tổng giá trị không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double tongGia;
        try {
            // Loại bỏ ký tự không phải số
            tongGia = Double.parseDouble(tongG.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tổng giá trị không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy giá trị trả lại từ txtTraLai
        String traLaiText = txtTraLai.getText().trim();
        double traLai;
        try {
            // Chuyển đổi chuỗi trả lại thành số
            traLai = Double.parseDouble(traLaiText.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá trị trả lại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (traLai < tongGia) {
            JOptionPane.showMessageDialog(this, "Số tiền trả lại không đủ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Kiểm tra giá trị trả lại
//        if (traLai < tongGia) {
//            JOptionPane.showMessageDialog(this, "Số tiền trả lại không đủ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        // Lấy thông tin thanh toán
        String hinhThuc = cbbChuyenKhoan.getSelectedItem().toString();
        String sdt = txtSDT.getText().trim();
        String diaChi = txaDiaChi.getText().trim();
        String maKhachHang = cbbKhachHang.getSelectedItem().toString();
        String maNhanVien = cbbNhanVien.getSelectedItem().toString();
        String maGiamGia = cbbGiamGia.getSelectedItem().toString(); // Có thể xử lý mã giảm giá ở đây
        String tenKhachHang = txtTenKhachHang.getText();
        String SDT = txtSDT.getText();
        Date dateTao = new Date(); // Thay thế với cách bạn lấy ngày từ JDatePicker
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayThanhToan = dateFormat.format(dateTao);
        // Lấy danh sách sản phẩm từ giỏ hàng
        List<SanPhamBH> danhSachSanPham = GioHang();

        // Thực hiện thanh toán
        boolean thanhToanThanhCong = this.repoSP.thanhToan(maHoaDon, tongGia, hinhThuc, sdt, diaChi, maKhachHang, maGiamGia, maNhanVien);

        // Kiểm tra kết quả thanh toán
        if (thanhToanThanhCong) {
            repoSP.capNhatTrangThaiHoaDonChiTiet(maHoaDon);
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Tạo nội dung hóa đơn
            String hoaDonHtml = createHoaDonHtml(danhSachSanPham, tenKhachHang, SDT, dateTao, maHoaDon, hinhThuc, diaChi, maKhachHang, maNhanVien);

            // Hiển thị hóa đơn qua JLabel
            JLabel lblPrint = new JLabel(hoaDonHtml);
            lblPrint.setVerticalAlignment(JLabel.TOP);
            lblPrint.setPreferredSize(new Dimension(380, 500));

            // Sử dụng JScrollPane để cuộn
            JScrollPane scrollPane = new JScrollPane(lblPrint);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            // Tạo JFrame để hiển thị hóa đơn
            JFrame hoaDonFrame = new JFrame("Hóa Đơn");
            hoaDonFrame.add(scrollPane);
            hoaDonFrame.pack();
            hoaDonFrame.setLocationRelativeTo(null);
            hoaDonFrame.setVisible(true);

            // In hóa đơn
            printInvoice(lblPrint);

            // Cập nhật giao diện
            this.showHoaDonTreo(repoSP.getHD());
            getGioHang();
            getSP();
            showNameGiamGia(repoSP.getNameGG());
            cbbGiamGia.setSelectedIndex(0);
            lblMaHoaDon.setText("");
            lblThanhtien.setText("");
            lblTongTien.setText("");
            txtTraLai.setText("");
            lblTienKhach.setText("");
            lblGiamGia.setText("");
            txtTienKhachTra.setText("");
            txtSDT.setText("");
            txaDiaChi.setText("");
            cbbNhanVien.setSelectedIndex(0);
            cbbChuyenKhoan.setSelectedIndex(0);
            // Đặt lại các trường mặc định

        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed
    private String createHoaDonHtml(List<SanPhamBH> danhSachSanPham, String tenKhachHang, String SDT, Date ngayThanhToan, String maHoaDon, String hinhThuc, String diaChi, String maKhachHang, String maNhanVien) {
        String tongTien = lblTongTien.getText(); // Lấy tổng tiền từ JLabel
        String giamGia = lblGiamGia.getText();   // Lấy giảm giá từ JLabel
        String thanhTien = lblThanhtien.getText(); // Lấy thành tiền từ JLabel
        String tienKhachTra = lblTienKhach.getText(); // Lấy tiền khách trả từ JLabel
        String traLai = txtTraLai.getText(); // Lấy tiền trả lại từ JTextField
        StringBuilder danhSachHtml = new StringBuilder();
        double tongSoTien = 0; // Biến lưu tổng số tiền

        // Lặp qua danh sách sản phẩm và tạo HTML cho từng sản phẩm
        for (SanPhamBH sp : danhSachSanPham) {
            double thanhTienSanPham = sp.getGiaBan() * sp.getSoLuong(); // Tính thành tiền của từng sản phẩm
            tongSoTien += thanhTienSanPham; // Cộng dồn vào tổng tiền

            danhSachHtml.append("<tr>")
                    .append("<td>").append(sp.getTenSanPham()).append("</td>") // Tên sản phẩm
                    .append("<td>").append(sp.getSoLuong()).append("</td>") // Số lượng
                    .append("<td>").append(String.format("%.2f VNĐ", sp.getGiaBan())).append("</td>") // Đơn giá
                    .append("</tr>");
        }

        // Định dạng ngày thanh toán
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(ngayThanhToan);

        // Thay thế phần tổng tiền bằng giá trị tổng đã tính
        return "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; background-color: #f9f9f9; }"
                + ".container { max-width: 800px; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + "h1 { color: #2c3e50; text-align: center; margin-bottom: 10px; font-size: 28px; }"
                + "h2, h3 { text-align: center; color: #34495e; margin: 5px 0; }"
                + ".line { margin: 10px 0; text-align: center; font-size: 18px; font-weight: bold; color: #000; }"
                + "table { width: 100%; border-collapse: collapse; margin-top: 20px; }"
                + "table, th, td { border: 1px solid #2c3e50; }"
                + "th, td { padding: 10px; text-align: center; }"
                + "th { background-color: #2c3e50; color: white; }"
                + ".footer { margin-top: 20px; text-align: center; font-size: 14px; color: #7f8c8d; }"
                + ".info { margin: 5px 0; font-size: 16px; font-weight: bold; color: #333; }"
                + ".info span { font-weight: normal; color: #555; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<h1>HÓA ĐƠN BÁN HÀNG</h1>"
                + "<h2>Cửa Hàng BeePhones</h2>"
                + "<h3>Địa Chỉ Cửa Hàng: 1002 ĐƯỜNG LÁNG - ĐỐNG ĐA - HÀ NỘI</h3>"
                + "<div class='info'><strong>Mã Hóa Đơn:</strong> <span>" + maHoaDon + "</span></div>"
                + "<div class='info'><strong>Mã Khách Hàng:</strong> <span>" + maKhachHang + "</span></div>"
                + "<div class='info'><strong>Mã Nhân Viên:</strong> <span>" + maNhanVien + "</span></div>"
                + "<div class='info'><strong>Tên Khách Hàng:</strong> <span>" + tenKhachHang + "</span></div>"
                + "<div class='info'><strong>SĐT:</strong> <span>" + SDT + "</span></div>"
                + "<div class='info'><strong>Địa Chỉ:</strong> <span>" + diaChi + "</span></div>"
                + "<div class='info'><strong>Ngày Thanh Toán:</strong> <span>" + formattedDate + "</span></div>"
                + "<table>"
                + "<thead>"
                + "<tr>"
                + "<th>STT</th>"
                + "<th>Tên Hàng Hóa</th>"
                + "<th>Đơn Vị Tính</th>"
                + "<th>Số Lượng</th>"
                + "<th>Đơn Giá</th>"
                + "</tr>"
                + "</thead>"
                + "<tbody>"
                + danhSachHtml.toString() // Chèn danh sách sản phẩm đã tạo
                + "</tbody>"
                + "</table>"
                + "<div class='line'></div>"
                + "<div><strong>Tổng Tiền:</strong> " + String.format("%.2f VNĐ", tongSoTien) + "</div>" // Hiển thị tổng tiền đã tính
                + "<div><strong>Giảm Giá:</strong> " + giamGia + "</div>"
                + "<div><strong>Thành Tiền:</strong> " + thanhTien + "</div>"
                + "<div><strong>Tiền Khách Trả:</strong> " + tienKhachTra + "</div>"
                + "<div><strong>Trả Lại:</strong> " + traLai + "</div>"
                + "<div><strong>Hình Thức Thanh Toán:</strong> " + hinhThuc + "</div>"
                + "<div class='footer'>Công ty BeePhones</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

// Phương thức in hóa đơn
    private void printInvoice(JLabel lblPrint) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2d.scale(0.5, 0.5); // Điều chỉnh tỷ lệ in nếu cần

                // Vẽ nội dung HTML của lblPrint
                lblPrint.paint(g2d);
                return PAGE_EXISTS;
            }
        });

        boolean printDialogResult = printerJob.printDialog();
        if (printDialogResult) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    private void txtTienKhachTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTraActionPerformed

    private void btnSearchSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSDTActionPerformed
        // TODO add your handling code here:
        String SDT = txtSDT.getText().trim();

        // Kiểm tra xem số điện thoại có rỗng không
        if (SDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Gọi phương thức tìm kiếm khách hàng bằng số điện thoại
        String maKhachHang = repoSP.SDT(SDT); // Phương thức trả về mã khách hàng hoặc null nếu không tìm thấy

        // Nếu muốn sử dụng mã khách hàng sau khi tìm kiếm, bạn có thể xử lý thêm ở đây.
        if (maKhachHang != null) {
            // Xử lý gì đó với mã khách hàng nếu cần, ví dụ hiển thị lên giao diện.
            cbbKhachHang.setSelectedItem(maKhachHang);
        } else {
            // Nếu không tìm thấy khách hàng, có thể cập nhật giao diện tương ứng.
            cbbKhachHang.setSelectedItem(0); // Đặt lại nếu không tìm thấy
        }
    }//GEN-LAST:event_btnSearchSDTActionPerformed

    private void btnTatCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTatCamActionPerformed
        stopWebCam();
    }//GEN-LAST:event_btnTatCamActionPerformed

    private void btnBatCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatCamActionPerformed
        // TODO add your handling code here:
        initWebCam();
    }//GEN-LAST:event_btnBatCamActionPerformed

    private void cbbKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbKhachHangActionPerformed
        String maKhachHang = cbbKhachHang.getSelectedItem().toString().trim();

        // Kiểm tra xem mã khách hàng có hợp lệ không (không rỗng)
        if (maKhachHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khách hàng hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (maKhachHang.equals("Null")) {
            txtSDT.setText(""); // Đặt lại TextField nếu mã là "Null"
            return;
        }

        // Gọi phương thức tìm thông tin khách hàng dựa trên mã khách hàng
        String[] khachHangInfo = repoSP.timKhachHang(maKhachHang); // Phương thức trả về mảng chứa SĐT và tên

        // Xử lý nếu tìm thấy thông tin khách hàng
        if (khachHangInfo != null) {
            String SDT = khachHangInfo[0]; // Số điện thoại
            String tenKhachHang = khachHangInfo[1]; // Tên khách hàng

            // Hiển thị số điện thoại lên TextField hoặc Label
            txtSDT.setText(SDT); // Hiển thị số điện thoại
            // Nếu có một trường để hiển thị tên khách hàng, bạn có thể sử dụng:
            txtTenKhachHang.setText(tenKhachHang); // Hiển thị tên khách hàng
        } else {
            // Nếu không tìm thấy, có thể hiển thị thông báo hoặc đặt lại TextField
            txtSDT.setText("");
            // Đặt lại trường tên khách hàng nếu cần
            txtTenKhachHang.setText("");
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin cho mã khách hàng: " + maKhachHang, "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_cbbKhachHangActionPerformed

    private void btnAddKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKHActionPerformed
        View_Thuoc_Tinh.KhachHang kh = new View_Thuoc_Tinh.KhachHang();
        kh.setVisible(true);
        showNameKH(repoSP.getNameKH());
    }//GEN-LAST:event_btnAddKHActionPerformed

    private void cbbGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiamGiaActionPerformed

        // Lấy giá trị hiện tại từ JComboBox
        String keyword = (String) cbbGiamGia.getSelectedItem();

        // Kiểm tra nếu từ khóa là null hoặc "All"
        if (keyword == null || keyword.equalsIgnoreCase("All")) {
            lblGiamGia.setText(""); // Hoặc một giá trị nào đó nếu cần
            lblThanhtien.setText(lblTongTien.getText());
            return;
        }

        // Lấy mức giảm giá
        Double mucGiamGia = this.repoSP.layMucGiamGia(keyword);

        if (mucGiamGia != null) { // Kiểm tra nếu có giá trị trả về
            lblSoDuocGiamr.setText(String.valueOf(mucGiamGia)); // Hiển thị mức giảm giá
            lblGiamGia.setText(String.valueOf(mucGiamGia));

            try {
                // Lấy giá trị tổng tiền từ label và loại bỏ " VND"
                String tongTienStr = this.lblTongTien.getText().replaceAll("[^\\d.]", "");
                float tongTien = Float.parseFloat(tongTienStr);
                float thanhTien;

                // Kiểm tra điều kiện giảm giá
                float GiamGia = mucGiamGia.floatValue(); // Chuyển đổi Double thành float

                if (GiamGia > 1 && GiamGia < 100) {
                    // Trường hợp giảm giá theo phần trăm
                    thanhTien = tongTien - (tongTien * GiamGia / 100);
                } else if (GiamGia >= 100) {
                    // Trường hợp giảm giá theo số tiền cụ thể
                    thanhTien = tongTien - GiamGia;
                } else {
                    // Thông báo giảm giá không hợp lệ
                    JOptionPane.showMessageDialog(this, "Giảm giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; // Thoát ra nếu giảm giá không hợp lệ
                }

                // Định dạng và không thêm "VND"
                String thanhTienStr = String.format("%.0f", thanhTien);
                lblThanhtien.setText(thanhTienStr); // Hiển thị kết quả lên label thanh toán
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá trị tổng tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            lblSoDuocGiamr.setText("Không có mức giảm giá"); // Thông báo nếu không tìm thấy

            capNhatGiaoDien(); // Cập nhật giao diện
        }
    }//GEN-LAST:event_cbbGiamGiaActionPerformed
    private void stopWebCam() {
        if (webcam != null) {
            // Dừng webcam
            webcam.close();
            webcam = null;
        }
        if (panel != null) {
            // Xóa panel khỏi giao diện người dùng
            PanelCam.remove(panel);
            PanelCam.revalidate();
            PanelCam.repaint();
            panel = null;
        }
    }

    private void initWebCam() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }

        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        PanelCam.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 70));
        PanelCam.revalidate();
        PanelCam.repaint();

        executor.execute(this);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result != null) {
                txtSearch.setText(result.getText());
                JOptionPane.showConfirmDialog(PanelCam, result.getText());
            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCam;
    private javax.swing.JButton btnAddKH;
    private javax.swing.JButton btnBatCam;
    private javax.swing.JButton btnSearchSDT;
    private javax.swing.JButton btnTatCam;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JComboBox<String> cbbChuyenKhoan;
    private javax.swing.JComboBox<String> cbbGiamGia;
    private javax.swing.JComboBox<String> cbbKhachHang;
    private javax.swing.JComboBox<String> cbbNhanVien;
    private com.toedter.calendar.JDateChooser dateTao;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblPrint;
    private javax.swing.JLabel lblSoDuocGiamr;
    private javax.swing.JLabel lblThanhtien;
    private javax.swing.JLabel lblTienKhach;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JButton tblTao;
    private javax.swing.JTextArea txaDiaChi;
    private javax.swing.JTextArea txaGhiChu;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienKhachTra;
    private javax.swing.JLabel txtTraLai;
    // End of variables declaration//GEN-END:variables

    private void updateProductList(List<SanPhamBH> results) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.CPU;
import Model.DungLuongPin;
import Model.Imei;
import Model.KichThuoc;
import Model.MauSac;
import Model.ModelSanPham;
import Model.PhanLoai;
import Model.Ram;
import Model.Rom;
import Model.SanPhamModel;
import Model.SanPhamModelBH;
import Model.XuatXu;
import Repository.CPURepository;
import Repository.DungLuongPinRepository;
import Repository.ImeiRepository;
import Repository.KichThuocRepository;
import Repository.MauSacRepository;
import Repository.PhanLoaiRepository;
import Repository.RamRepository;
import Repository.RomRepository;
import Repository.SanPhamRepository;
import Repository.XuatXuRepository;
import View_Thuoc_Tinh.CPUView;
import View_Thuoc_Tinh.DungLuongPinView;
import View_Thuoc_Tinh.ImeiView;
import View_Thuoc_Tinh.KichThuocView;
import View_Thuoc_Tinh.MauSacView;
import View_Thuoc_Tinh.PhanLoaiView;
import View_Thuoc_Tinh.RamView;
import View_Thuoc_Tinh.RomView;
import View_Thuoc_Tinh.XuatXuView;
import ZxingHelper.ZXingHelper;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author nothank
 */
public class SanPham extends javax.swing.JPanel {

    private SanPhamRepository repo = new SanPhamRepository();
    private RomRepository repoRom = new RomRepository();
    private CPURepository repoCPU = new CPURepository();
    private RamRepository repoRam = new RamRepository();
    private XuatXuRepository repoXuatXu = new XuatXuRepository();
    private PhanLoaiRepository repoPhanLoai = new PhanLoaiRepository();
    private MauSacRepository repoMauSac = new MauSacRepository();
    private KichThuocRepository repoKT = new KichThuocRepository();
    private ImeiRepository repoImei = new ImeiRepository();
    private DungLuongPinRepository repoPin = new DungLuongPinRepository();
    private String path = "0/...";
    private int page = 1;
    private int limit = 10;
    DefaultTableModel dtm;

    /**
     * Creates new form NewJPanel
     */
    public SanPham() {
        initComponents();
        cbbImei.removeAllItems();
        showSanPham(repo.getSanPhamDetails(page, limit));
        showSanPhamNgungBan(repo.getSanPhamNgungBan(page, limit));
        showNameRom(repoRom.getName());
        showNameCPU(repoCPU.getName());
        showNameDungLuong(repoPin.getName());
        showNameRam(repoRam.getName());
        showNameXuatXu(repoXuatXu.getName());
        showNameMauSac(repoMauSac.getName());
        showNameKichThuoc(repoKT.getName());
//        showNameImei(repoImei.getName());
        showNamePhanLoai(repoPhanLoai.getName());
    }

    private void showSanPham(ArrayList<ModelSanPham> arr) {
        this.dtm = (DefaultTableModel) this.tblSanPham.getModel();
        dtm.setRowCount(0); // Xóa các dòng cũ trong bảng

        for (ModelSanPham sanPhamDetail : arr) {
            Object[] row = {
                sanPhamDetail.getMaSanPham(),
                sanPhamDetail.getTenSanPham(),
                sanPhamDetail.getPhanLoai(),
                sanPhamDetail.getNgayTao(),
                sanPhamDetail.getSoLuong(),
                sanPhamDetail.getRom(),
                sanPhamDetail.getMauSac(),
                sanPhamDetail.getRam(),
                sanPhamDetail.getKichThuoc(),
                sanPhamDetail.getDungLuongPin(),
                sanPhamDetail.getCpu(),
                sanPhamDetail.getXuatXu(),
                sanPhamDetail.getGiaBan()
            };

            dtm.addRow(row); // Thêm dòng mới vào bảng
        }
    }

    private void showSanPhamNgungBan(ArrayList<ModelSanPham> arr) {
        this.dtm = (DefaultTableModel) this.tblSanPham1.getModel();
        dtm.setRowCount(0); // Xóa các dòng cũ trong bảng

        for (ModelSanPham sanPhamDetail : arr) {
            Object[] row = {
                sanPhamDetail.getMaSanPham(),
                sanPhamDetail.getTenSanPham(),
                sanPhamDetail.getPhanLoai(),
                sanPhamDetail.getNgayTao(),
                sanPhamDetail.getSoLuong(),
                sanPhamDetail.getRom(),
                sanPhamDetail.getMauSac(),
                sanPhamDetail.getRam(),
                sanPhamDetail.getKichThuoc(),
                sanPhamDetail.getDungLuongPin(),
                sanPhamDetail.getCpu(),
                sanPhamDetail.getXuatXu(),
                sanPhamDetail.getGiaBan()
            };

            dtm.addRow(row); // Thêm dòng mới vào bảng
        }
    }

    private void showNameRom(ArrayList<Rom> arr) {
        cbbBoNhoTrong.removeAllItems();
        for (Rom rom : arr) {
            cbbBoNhoTrong.addItem(rom.getRom().toString());
        };
    }

    private void showNameCPU(ArrayList<CPU> arr) {
        cbbCPU.removeAllItems();
        for (CPU cpu : arr) {
            cbbCPU.addItem(cpu.getCPU().toString());
        };
    }

    private void showNameRam(ArrayList<Ram> arr) {
        cbbRam.removeAllItems();
        for (Ram ram : arr) {
            cbbRam.addItem(ram.getRam().toString());
        };
    }

    private void showNameXuatXu(ArrayList<XuatXu> arr) {
        cbbXuatXu.removeAllItems();
        for (XuatXu xuatXu : arr) {
            cbbXuatXu.addItem(xuatXu.getXuat_Xu().toString());
        };
    }

    private void showNamePhanLoai(ArrayList<PhanLoai> arr) {
        cbbPhanLoai.removeAllItems(); // Xóa tất cả các mục hiện có
        for (PhanLoai phanLoai : arr) {
            cbbPhanLoai.addItem(phanLoai.getPhan_Loai().toString());
        }
    }

    private void showNameMauSac(ArrayList<MauSac> arr) {
        cbbMauSac.removeAllItems();
        for (MauSac mauSac : arr) {
            cbbMauSac.addItem(mauSac.getMau_Sac().toString());
        };
    }

    private void showNameKichThuoc(ArrayList<KichThuoc> arr) {
        cbbManHinh.removeAllItems();
        for (KichThuoc kichThuoc : arr) {
            cbbManHinh.addItem(kichThuoc.getKich_Thuoc().toString());
        };
    }

    private void showNameImei(ArrayList<Imei> arr) {
        cbbImei.removeAllItems();
        for (Imei imei : arr) {
            cbbImei.addItem(imei.getMa_Imei().toString());
        };
    }

    private void showNameDungLuong(ArrayList<DungLuongPin> arr) {
        cbbDungLuongPin.removeAllItems();
        for (DungLuongPin dl : arr) {
            cbbDungLuongPin.addItem(dl.getDung_Luong_Pin().toString());
        };
    }

    public void clear() {
        txtMaSanPham.setText(""); // Đặt lại Mã sản phẩm
        txtSanPham.setText("");    // Đặt lại Tên sản phẩm
        txtSoLuong.setText("");    // Đặt lại Số lượng
        txtGiaBan.setText("");     // Đặt lại Giá bán
        txtGiaNhap.setText("");    // Đặt lại Giá nhập
        lblAnh.setText("");        // Đặt lại Hình ảnh
        txaMoTa.setText("");       // Đặt lại Mô tả
        dateNgayTao.setDate(null); // Đặt lại ngày tạo về null
        lblAnh.setText("");
        // Đặt lại các combo box
        cbbBoNhoTrong.setSelectedIndex(0); // Hoặc giá trị mặc định bạn muốn
        cbbMauSac.setSelectedIndex(0);
        cbbRam.setSelectedIndex(0);
        cbbManHinh.setSelectedIndex(0);
        cbbDungLuongPin.setSelectedIndex(0);
        cbbCPU.setSelectedIndex(0);
        cbbXuatXu.setSelectedIndex(0);
        cbbPhanLoai.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnNext = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        cbbBoNhoTrong = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        btnRom = new javax.swing.JButton();
        cbbManHinh = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        btnManHinh = new javax.swing.JButton();
        cbbDungLuongPin = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        btnPin = new javax.swing.JButton();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cbbCPU = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        btnCPU = new javax.swing.JButton();
        cbbRam = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        btnRam = new javax.swing.JButton();
        cbbXuatXu = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        btnXX = new javax.swing.JButton();
        cbbPhanLoai = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        btnPhanLoai = new javax.swing.JButton();
        btnMauSac = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSanPham = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaMoTa = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        dateNgayTao = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        cbbImei = new javax.swing.JComboBox<>();
        btnQR = new javax.swing.JButton();
        lblBarCode = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnSoLuong = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnCPU2 = new javax.swing.JButton();
        btnImei = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSanPham1 = new javax.swing.JTable();
        btnNext1 = new javax.swing.JButton();
        btnBack1 = new javax.swing.JButton();
        lblPage1 = new javax.swing.JLabel();
        btnXoa1 = new javax.swing.JButton();
        txtSearch1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(719, 521));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên SP", "Loại", "Ngày tạo", "Số Lượng", "Rom", "Màu Sắc", "Ram", "Kích Thước", "Dung Lượng Pin", "CPU", "Xuất Xứ", "Giá"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 39, 930, 280));

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 330, -1, -1));

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/back.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 330, -1, -1));

        lblPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPage.setText("0");
        jPanel1.add(lblPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 330, 26, 27));

        cbbBoNhoTrong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbBoNhoTrongActionPerformed(evt);
            }
        });
        jPanel1.add(cbbBoNhoTrong, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 506, 162, -1));

        jLabel14.setText("Bộ Nhớ Trong");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 476, 162, -1));

        btnRom.setText("+");
        btnRom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRomMouseClicked(evt);
            }
        });
        btnRom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRomActionPerformed(evt);
            }
        });
        jPanel1.add(btnRom, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 536, 162, -1));

        jPanel1.add(cbbManHinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 506, 160, -1));

        jLabel15.setText("Màn Hình");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 476, 160, -1));

        btnManHinh.setText("+");
        btnManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManHinhActionPerformed(evt);
            }
        });
        jPanel1.add(btnManHinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 536, 160, -1));

        cbbDungLuongPin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jPanel1.add(cbbDungLuongPin, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 506, 152, -1));

        jLabel16.setText("Dung Lượng Pin ");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 476, 152, -1));

        btnPin.setText("+");
        btnPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPinActionPerformed(evt);
            }
        });
        jPanel1.add(btnPin, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 536, 152, -1));

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbMauSac.setSelectedIndex(-1);
        jPanel1.add(cbbMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 606, 150, -1));

        jLabel17.setText("Màu Sắc");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 576, 150, -1));

        cbbCPU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbCPU.setSelectedIndex(-1);
        jPanel1.add(cbbCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 506, 150, -1));

        jLabel18.setText("CPU");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 476, 150, -1));

        btnCPU.setText("+");
        btnCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPUActionPerformed(evt);
            }
        });
        jPanel1.add(btnCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 536, 150, -1));

        cbbRam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbRam.setSelectedIndex(-1);
        jPanel1.add(cbbRam, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 606, 162, -1));

        jLabel19.setText("Ram");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 576, 162, -1));

        btnRam.setText("+");
        btnRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRamActionPerformed(evt);
            }
        });
        jPanel1.add(btnRam, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 636, 162, -1));

        cbbXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbXuatXu.setSelectedIndex(-1);
        jPanel1.add(cbbXuatXu, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 606, 160, -1));

        jLabel20.setText("Xuất Xứ");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 576, 160, -1));

        btnXX.setText("+");
        btnXX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXXActionPerformed(evt);
            }
        });
        jPanel1.add(btnXX, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 636, 160, -1));

        cbbPhanLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbPhanLoai.setSelectedIndex(-1);
        cbbPhanLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPhanLoaiActionPerformed(evt);
            }
        });
        jPanel1.add(cbbPhanLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 606, 152, -1));

        jLabel21.setText("Phân Loại");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 576, 152, -1));

        btnPhanLoai.setText("+");
        btnPhanLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanLoaiActionPerformed(evt);
            }
        });
        jPanel1.add(btnPhanLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 636, 152, -1));

        btnMauSac.setText("+");
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });
        jPanel1.add(btnMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 634, 150, -1));

        jLabel5.setText("Tên Sản Phẩm");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 80, -1));

        txtSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSanPhamActionPerformed(evt);
            }
        });
        jPanel1.add(txtSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, -1));

        jLabel22.setText("Giá Nhập");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 400, -1, -1));

        jLabel23.setText("Giá Bán");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 400, -1, -1));
        jPanel1.add(txtGiaNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 420, 100, -1));
        jPanel1.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, 100, -1));

        jLabel24.setText("Mô Tả");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 580, 73, -1));

        txaMoTa.setColumns(20);
        txaMoTa.setRows(5);
        jScrollPane3.setViewportView(txaMoTa);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 610, 150, 45));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, 110, -1));

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 90, 110, -1));

        btnXoa.setText("Ngưng Bán");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, 110, -1));

        jLabel6.setText("Ngày tạo");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 400, 90, -1));
        jPanel1.add(dateNgayTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, 210, -1));

        jLabel25.setText("Imei");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 480, 150, -1));

        cbbImei.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        cbbImei.setSelectedIndex(-1);
        jPanel1.add(cbbImei, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 510, 130, -1));

        btnQR.setText("QRCode");
        btnQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRActionPerformed(evt);
            }
        });
        jPanel1.add(btnQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 240, 110, -1));

        lblBarCode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(lblBarCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 560, 170, 100));

        jLabel7.setText("Tìm kiếm");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 120, -1));

        txtMaSanPham.setEnabled(false);
        txtMaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSanPhamActionPerformed(evt);
            }
        });
        jPanel1.add(txtMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 160, -1));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 270, -1));

        jLabel1.setText("Số Lượng Hàng");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, -1, -1));

        lblAnh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });
        jPanel1.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 320, 170, 230));

        txtSoLuong.setEnabled(false);
        jPanel1.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, -1, -1));

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel1.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 190, 110, -1));

        btnSoLuong.setText("Thêm số lượng");
        btnSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoLuongActionPerformed(evt);
            }
        });
        jPanel1.add(btnSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 290, -1, -1));

        jLabel8.setText("Mã Sản Phẩm");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 80, -1));

        btnCPU2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/7422413_exel_spreadsheet_sheets_table_icon.png"))); // NOI18N
        btnCPU2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPU2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCPU2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 510, 40, 20));

        btnImei.setText("+");
        btnImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImeiActionPerformed(evt);
            }
        });
        jPanel1.add(btnImei, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 540, 170, 20));

        jTabbedPane1.addTab("Sản Phẩm", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSanPham1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tblSanPham1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên SP", "Loại", "Ngày tạo", "Số Lượng", "Rom", "Màu Sắc", "Ram", "Kích Thước", "Dung Lượng Pin", "CPU", "Xuất Xứ", "Giá"
            }
        ));
        tblSanPham1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPham1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSanPham1);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 39, 930, 410));

        btnNext1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Next.png"))); // NOI18N
        btnNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnNext1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 470, -1, -1));

        btnBack1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/back.png"))); // NOI18N
        btnBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnBack1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 470, -1, -1));

        lblPage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPage1.setText("0");
        jPanel2.add(lblPage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 470, 26, 27));

        btnXoa1.setText("Bán Lại");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 110, -1));

        txtSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearch1ActionPerformed(evt);
            }
        });
        txtSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch1KeyReleased(evt);
            }
        });
        jPanel2.add(txtSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 270, -1));

        jLabel9.setText("Tìm kiếm");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 120, -1));

        jTabbedPane1.addTab("Ngưng bán", jPanel2);

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManHinhActionPerformed
        // TODO add your handling code here:
        KichThuocView kt = new KichThuocView();
        kt.setVisible(true);
    }//GEN-LAST:event_btnManHinhActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.page--;
        showSanPham(repo.getSanPhamDetails(page, limit));
        this.lblPage.setText(this.page + "");
        if (this.page == 1) {
            this.btnBack.setEnabled(false);
            btnNext.setEnabled(true);
        } else {
            this.btnBack.setEnabled(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.page++;
        this.lblPage.setText(this.page + "");
        showSanPham(repo.getSanPhamDetails(page, limit));

// Luôn vô hiệu hóa btnNext khi page == 2, mở lại cho các giá trị khác
        if (this.page == 2) {
            this.btnNext.setEnabled(false);
            btnBack.setEnabled(true);
        } else {
            this.btnNext.setEnabled(true);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void txtSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSanPhamActionPerformed

    private void btnRomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRomMouseClicked
        RomView rom = new RomView();
        rom.setVisible(true);
        this.dispose();
        showNameRom(repoRom.getName());
    }//GEN-LAST:event_btnRomMouseClicked

    private void btnPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPinActionPerformed
        // TODO add your handling code here:
        DungLuongPinView pin = new DungLuongPinView();
        pin.setVisible(true);
        showNameDungLuong(repoPin.getName());
    }//GEN-LAST:event_btnPinActionPerformed

    private void btnCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPUActionPerformed
        // TODO add your handling code here:
        CPUView cpu = new CPUView();
        cpu.setVisible(true);
        showNameCPU(repoCPU.getName());
    }//GEN-LAST:event_btnCPUActionPerformed

    private void btnRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRamActionPerformed
        // TODO add your handling code here:
        RamView ram = new RamView();
        ram.setVisible(true);
        showNameRam(repoRam.getName());
    }//GEN-LAST:event_btnRamActionPerformed

    private void btnXXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXXActionPerformed
        // TODO add your handling code here:
        XuatXuView xx = new XuatXuView();
        xx.setVisible(true);
        showNameXuatXu(repoXuatXu.getName());
    }//GEN-LAST:event_btnXXActionPerformed

    private void btnPhanLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhanLoaiActionPerformed
        // TODO add your handling code here:
        PhanLoaiView pl = new PhanLoaiView();
        pl.setVisible(true);
        showNamePhanLoai(repoPhanLoai.getName());
    }//GEN-LAST:event_btnPhanLoaiActionPerformed

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed
        // TODO add your handling code here:
        MauSacView ms = new MauSacView();
        ms.setVisible(true);
        showNameMauSac(repoMauSac.getName());
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void cbbPhanLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhanLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbPhanLoaiActionPerformed

    private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRActionPerformed
        // TODO add your handling code here:
        String productId = txtSanPham.getText();
        if (productId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product id bị trống");
        } else {
            byte[] result = ZXingHelper.getQRCodeImage(productId, 140, 87);
            lblBarCode.setIcon(new ImageIcon(result));
        }
        if (lblBarCode == null) {
            JOptionPane.showMessageDialog(null, "Chưa có tên sản phẩm để in.");
            return;
        }
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng ấn tạo QR để in.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

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
                lblBarCode.paint(g2d);
                return PAGE_EXISTS;
            }
        });

        boolean printDialogResult = printerJob.printDialog();
        if (printDialogResult) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_btnQRActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            return;
        }

        // Lấy mã sản phẩm từ dòng được chọn
        String maSanPham = tblSanPham.getValueAt(row, 0).toString(); // Cột 1 là Mã Sản Phẩm

        // Lấy dữ liệu sản phẩm từ cơ sở dữ liệu dựa trên mã sản phẩm
        ArrayList<ModelSanPham> sanPhamList = repo.selectTheoMa(page, limit, maSanPham);

        if (sanPhamList.size() > 0) {
            // Giả sử luôn chỉ có 1 sản phẩm được tìm thấy với mã sản phẩm cụ thể
            ModelSanPham sanPham = sanPhamList.get(0);

            // Gán giá trị vào các trường nhập liệu
            txtMaSanPham.setText(sanPham.getMaSanPham());
            txtSanPham.setText(sanPham.getTenSanPham());
            txaMoTa.setText(sanPham.getMoTa());

            // Chuyển đổi chuỗi ngày thành đối tượng Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateNgayTao.setDate(sdf.parse(sanPham.getNgayTao().toString()));
            } catch (ParseException e) {
                e.printStackTrace(); // Xử lý lỗi nếu định dạng ngày không hợp lệ
            }

            // Gán các giá trị khác vào trường nhập liệu
            txtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
            txtGiaBan.setText(String.valueOf(sanPham.getGiaBan()));
            txtGiaNhap.setText(String.valueOf(sanPham.getGiaNhap()));

            // Hiển thị hình ảnh sản phẩm
            String HinhAnh = sanPham.getHinhAnh();
            path = HinhAnh;
            ImageIcon img = new ImageIcon(path);
            lblAnh.setIcon(img); // Hiển thị hình ảnh trên `jLabel1`

            // Thiết lập giá trị cho JComboBox
            cbbRam.setSelectedItem(sanPham.getRam());
            cbbBoNhoTrong.setSelectedItem(sanPham.getRom());
            cbbCPU.setSelectedItem(sanPham.getCpu());
            cbbMauSac.setSelectedItem(sanPham.getMauSac());
            cbbManHinh.setSelectedItem(sanPham.getKichThuoc());
            cbbDungLuongPin.setSelectedItem(sanPham.getDungLuongPin());
            cbbXuatXu.setSelectedItem(sanPham.getXuatXu());
            cbbPhanLoai.setSelectedItem(sanPham.getPhanLoai());

            cbbImei.removeAllItems();
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked
    private void showImei(ArrayList<Imei> arr) {
        // Xóa tất cả các mục hiện có trong JComboBox
        cbbImei.removeAllItems();

        // Thêm các mã IMEI vào JComboBox
        for (Imei im : arr) {
            cbbImei.addItem(im.getMa_Imei()); // Thêm mã IMEI vào JComboBox
        }
    }

    private void txtMaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSanPhamActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnRomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRomActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (cbbImei.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Danh sách IMEI trống, không thể thêm sản phẩm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra các trường khác có bị trống không
        if (txtSanPham.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtGiaBan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá bán không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtGiaNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá nhập không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Chuyển đổi dữ liệu từ các trường văn bản
        String maSanPham = txtMaSanPham.getText().trim();
        String tenSanPham = txtSanPham.getText().trim();
        String moTa = txaMoTa.getText().trim();
        Date ngayTao = new Date();
        float giaBan;
        float giaNhap;

        // Kiểm tra và chuyển đổi giá bán và giá nhập
        try {
            giaBan = Float.parseFloat(txtGiaBan.getText().trim());
            giaNhap = Float.parseFloat(txtGiaNhap.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá bán hoặc giá nhập không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra giá nhập phải nhỏ hơn giá bán
        if (giaNhap >= giaBan) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải nhỏ hơn giá bán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String hinhAnh = lblAnh.getText().trim();
        String rom = cbbBoNhoTrong.getSelectedItem().toString();
        String mauSac = cbbMauSac.getSelectedItem().toString();
        String ram = cbbRam.getSelectedItem().toString();
        String kichThuoc = cbbManHinh.getSelectedItem().toString();
        String dungLuongPin = cbbDungLuongPin.getSelectedItem().toString();
        String cpu = cbbCPU.getSelectedItem().toString();
        String xuatXu = cbbXuatXu.getSelectedItem().toString();
        String phanLoai = cbbPhanLoai.getSelectedItem().toString();

        // Lấy danh sách IMEI từ cbbImei (ComboBox)
        List<String> imeiList = new ArrayList<>();
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            imeiList.add(cbbImei.getItemAt(i));
        }

        // Kiểm tra trùng lặp IMEI
        Set<String> uniqueImeiSet = new HashSet<>(imeiList);
        if (uniqueImeiSet.size() < imeiList.size()) {
            JOptionPane.showMessageDialog(this, "Danh sách IMEI có trùng lặp!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Gọi phương thức thêm sản phẩm
        boolean success = repo.create(maSanPham, tenSanPham, moTa, ngayTao, imeiList, giaBan, giaNhap, hinhAnh, rom, mauSac, ram, kichThuoc, dungLuongPin, cpu, xuatXu, phanLoai);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
            // Cập nhật lại bảng sản phẩm (nếu có)
            showSanPham(repo.getSanPhamDetails(page, limit));
            this.clear();
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình thêm sản phẩm!");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int ketqua = chooser.showOpenDialog(null);
        if (ketqua == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            ImageIcon icon = new ImageIcon(path);
            lblAnh.setIcon(icon);
            lblAnh.setText(path); // Cập nhật lblAnhG để chứa đường dẫn hình ảnh
        }
    }//GEN-LAST:event_lblAnhMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here
        this.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (txtMaSanPham.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtSanPham.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtGiaBan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá bán không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtGiaNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá nhập không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (lblAnh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hình ảnh không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra kiểu dữ liệu của số lượng, giá bán và giá nhập
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float giaBan;
        try {
            giaBan = Float.parseFloat(txtGiaBan.getText().trim());
            if (giaBan <= 0) {
                JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá bán phải là số thực hợp lệ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float giaNhap;
        try {
            giaNhap = Float.parseFloat(txtGiaNhap.getText().trim());
            if (giaNhap <= 0) {
                JOptionPane.showMessageDialog(this, "Giá nhập phải lớn hơn 0!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số thực hợp lệ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ các trường khác
        String maSanPham = txtMaSanPham.getText().trim();
        String tenSanPham = txtSanPham.getText().trim();
        String moTa = txaMoTa.getText().trim();
        Date ngayTao = dateNgayTao.getDate();  // Giả định bạn đang dùng một component nhập ngày
        String hinhAnh = lblAnh.getText().trim();
        String rom = cbbBoNhoTrong.getSelectedItem().toString();
        String mauSac = cbbMauSac.getSelectedItem().toString();
        String ram = cbbRam.getSelectedItem().toString();
        String kichThuoc = cbbManHinh.getSelectedItem().toString();
        String dungLuongPin = cbbDungLuongPin.getSelectedItem().toString();
        String cpu = cbbCPU.getSelectedItem().toString();
        String xuatXu = cbbXuatXu.getSelectedItem().toString();
        String phanLoai = cbbPhanLoai.getSelectedItem().toString();

        // Gọi phương thức create() từ repository
        boolean isAdded = repo.update(maSanPham, tenSanPham, moTa, ngayTao, giaBan, giaNhap, hinhAnh, rom, mauSac, ram, kichThuoc, dungLuongPin, cpu, xuatXu, phanLoai);

        // Thông báo kết quả thêm sản phẩm
        if (isAdded) {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Sau khi thêm thành công, có thể làm mới các trường dữ liệu nếu cần thiết
        } else {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại! Vui lòng thử lại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        showSanPham(repo.getSanPhamDetails(page, limit));
        this.clear();
    }//GEN-LAST:event_btnSuaActionPerformed
    private int showQuantityInputDialog() {
        JTextField quantityField = new JTextField();
        Object[] message = {
            "Nhập số lượng sản phẩm:", quantityField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Nhập số lượng", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                // Kiểm tra nếu số lượng là số âm hoặc bằng 0
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập một số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return -1; // Trả về -1 nếu số lượng không hợp lệ
                }
                return quantity; // Trả về số lượng hợp lệ
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập một số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return -1; // Trả về -1 nếu nhập không hợp lệ
            }
        }
        return -1; // Trả về -1 nếu người dùng hủy
    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        String ma = txtMaSanPham.getText(); // Lấy mã sản phẩm từ trường nhập
        boolean isDeleted = repo.delete(ma); // Gọi phương thức xóa

        if (isDeleted) {
            JOptionPane.showMessageDialog(this, "Ngưng bán sản phẩm thành công."); // Hiển thị thông báo thành công
        } else {
            JOptionPane.showMessageDialog(this, "Không thể ngưng bán sản phẩm. Vui lòng kiểm tra lại."); // Thông báo lỗi
        }
        showSanPham(repo.getSanPhamDetails(page, limit));
        showSanPhamNgungBan(repo.getSanPhamNgungBan(page, limit));
        this.clear();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoLuongActionPerformed
        if (txtMaSanPham.getText().trim().isEmpty()) {
            showMessage("Mã sản phẩm không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maSanPham = txtMaSanPham.getText().trim();

        if (cbbImei.getItemCount() == 0) {
            showMessage("Danh sách IMEI trống, không thể thêm sản phẩm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> imeiList = new ArrayList<>();
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            imeiList.add(cbbImei.getItemAt(i));
        }

        // Kiểm tra số lượng IMEI
        if (imeiList.isEmpty()) {
            showMessage("Không có IMEI nào để thêm sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = repo.updateSoLuongAndGenerateImei(maSanPham, imeiList);
        if (success) {
            showMessage("Cập nhật số lượng và thêm IMEI thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showMessage("Đã xảy ra lỗi khi cập nhật!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }

        showSanPham(repo.getSanPhamDetails(page, limit));
    }//GEN-LAST:event_btnSoLuongActionPerformed
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String keyword = txtSearch.getText().trim(); // Lấy văn bản hiện tại từ txtSearch và loại bỏ khoảng trắng thừa

        // Đặt giá trị cho page và limit (ví dụ: page là 1, limit là 10)
        int page = 1; // Hoặc lấy từ giao diện nếu có
        int limit = 10; // Hoặc lấy từ giao diện nếu có

        // Thực hiện tìm kiếm sản phẩm
        List<ModelSanPham> results = this.repo.searchSanPham(page, limit, keyword);

        // Cập nhật giao diện người dùng với kết quả tìm kiếm
        DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0); // Xóa tất cả hàng trong bảng

        // Kiểm tra nếu không có kết quả trả về
        if (results == null || results.isEmpty()) {
            return; // Không làm gì nếu không có kết quả
        }

        // Thêm kết quả tìm kiếm vào bảng
        for (ModelSanPham sanPhamDetail : results) { // Sử dụng 'results' thay vì 'arr'
            Object[] row = {
                sanPhamDetail.getMaSanPham(),
                sanPhamDetail.getTenSanPham(),
                sanPhamDetail.getPhanLoai(),
                sanPhamDetail.getNgayTao(),
                sanPhamDetail.getSoLuong(),
                sanPhamDetail.getRom(),
                sanPhamDetail.getMauSac(),
                sanPhamDetail.getRam(),
                sanPhamDetail.getKichThuoc(),
                sanPhamDetail.getDungLuongPin(),
                sanPhamDetail.getCpu(),
                sanPhamDetail.getXuatXu(),
                sanPhamDetail.getGiaBan()
            };

            dtm.addRow(row); // Thêm dòng mới vào bảng
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tblSanPham1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPham1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPham1MouseClicked

    private void btnNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNext1ActionPerformed

    private void btnBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBack1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        // TODO add your handling code here:
        String ma = txtMaSanPham.getText(); // Lấy mã sản phẩm từ trường nhập
        boolean isDeleted = repo.deleteDB(ma); // Gọi phương thức xóa

        if (isDeleted) {
            JOptionPane.showMessageDialog(this, "Bán lại sản phẩm thành công."); // Hiển thị thông báo thành công
        } else {
            JOptionPane.showMessageDialog(this, "Không thể bán lại sản phẩm. Vui lòng kiểm tra lại."); // Thông báo lỗi
        }
        showSanPhamNgungBan(repo.getSanPhamNgungBan(page, limit));
        showSanPham(repo.getSanPhamDetails(page, limit));
        this.clear();
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void txtSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch1ActionPerformed

    private void txtSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch1KeyReleased
        // TODO add your handling code here:
        String keyword = txtSearch.getText().trim(); // Lấy văn bản hiện tại từ txtSearch và loại bỏ khoảng trắng thừa

        // Đặt giá trị cho page và limit (ví dụ: page là 1, limit là 10)
        int page = 1; // Hoặc lấy từ giao diện nếu có
        int limit = 10; // Hoặc lấy từ giao diện nếu có

        // Thực hiện tìm kiếm sản phẩm
        List<ModelSanPham> results = this.repo.searchSanPhamNN(page, limit, keyword);

        // Cập nhật giao diện người dùng với kết quả tìm kiếm
        DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0); // Xóa tất cả hàng trong bảng

        // Kiểm tra nếu không có kết quả trả về
        if (results == null || results.isEmpty()) {
            return; // Không làm gì nếu không có kết quả
        }

        // Thêm kết quả tìm kiếm vào bảng
        for (ModelSanPham sanPhamDetail : results) { // Sử dụng 'results' thay vì 'arr'
            Object[] row = {
                sanPhamDetail.getMaSanPham(),
                sanPhamDetail.getTenSanPham(),
                sanPhamDetail.getPhanLoai(),
                sanPhamDetail.getNgayTao(),
                sanPhamDetail.getSoLuong(),
                sanPhamDetail.getRom(),
                sanPhamDetail.getMauSac(),
                sanPhamDetail.getRam(),
                sanPhamDetail.getKichThuoc(),
                sanPhamDetail.getDungLuongPin(),
                sanPhamDetail.getCpu(),
                sanPhamDetail.getXuatXu(),
                sanPhamDetail.getGiaBan()
            };

            dtm.addRow(row); // Thêm dòng mới vào bảng
        }
    }//GEN-LAST:event_txtSearch1KeyReleased

    private void btnCPU2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPU2ActionPerformed
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        String defaultCurrentDirectoryPath = "F:\\DA1";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        int excelChooser = excelFileChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = excelFileChooser.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                // Xóa hết các item hiện tại của comboBox trước khi thêm mới
                cbbImei.removeAllItems();

                // Sử dụng DataFormatter để định dạng giá trị từ các ô
                DataFormatter formatter = new DataFormatter();

                for (int row = 0; row <= excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    if (excelRow != null) {
                        XSSFCell excelCell = excelRow.getCell(0); // Lấy giá trị từ cột đầu tiên
                        if (excelCell != null) {
                            // Sử dụng DataFormatter để chuyển đổi giá trị sang chuỗi
                            String nameValue = formatter.formatCellValue(excelCell);

                            // Thêm giá trị vào comboBox
                            cbbImei.addItem(nameValue);
                            System.out.println(nameValue); // Hiển thị giá trị đã thêm
                        }
                    }
                }

                // Ví dụ về việc xóa một item cụ thể
                String itemToRemove = "Specific IMEI"; // Thay bằng IMEI bạn muốn xóa
                cbbImei.removeItem(itemToRemove); // Xóa item cụ thể

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelJTableImport != null) {
                        excelJTableImport.close();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnCPU2ActionPerformed

    private void btnImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImeiActionPerformed
        // TODO add your handling code here:
        ImeiView im = new ImeiView();
        im.setVisible(true);
    }//GEN-LAST:event_btnImeiActionPerformed

    private void cbbBoNhoTrongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbBoNhoTrongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbBoNhoTrongActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnCPU;
    private javax.swing.JButton btnCPU2;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnImei;
    private javax.swing.JButton btnManHinh;
    private javax.swing.JButton btnMauSac;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNext1;
    private javax.swing.JButton btnPhanLoai;
    private javax.swing.JButton btnPin;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnRam;
    private javax.swing.JButton btnRom;
    private javax.swing.JButton btnSoLuong;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXX;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JComboBox<String> cbbBoNhoTrong;
    private javax.swing.JComboBox<String> cbbCPU;
    private javax.swing.JComboBox<String> cbbDungLuongPin;
    private javax.swing.JComboBox<String> cbbImei;
    private javax.swing.JComboBox<String> cbbManHinh;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbPhanLoai;
    private javax.swing.JComboBox<String> cbbRam;
    private javax.swing.JComboBox<String> cbbXuatXu;
    private com.toedter.calendar.JDateChooser dateNgayTao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblBarCode;
    private javax.swing.JLabel lblPage;
    private javax.swing.JLabel lblPage1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPham1;
    private javax.swing.JTextArea txaMoTa;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSanPham;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables

    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

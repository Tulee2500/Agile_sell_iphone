CREATE DATABASE DA_QLBANDIENTHOAI_IPHONE_NHOM5
GO
USE DA_QLBANDIENTHOAI_IPHONE_NHOM5
GO 
select * from nhanVien
CREATE TABLE NhanVien(
	ID_Nhan_Vien int IDENTITY(1,1) PRIMARY KEY, 
	Ma_NV varchar(50)   UNIQUE not null,
	Tai_Khoan varchar(max) not null, 
	Mat_Khau varchar(max) not null,
	Ho_Ten nvarchar(50) not null,
	Gioi_Tinh bit, -- 0 false nam , 1 true nữ
	Ngay_Sinh date , 
	Dia_Chi nvarchar(100), 
	SDT  varchar(30),
	Hinh_Anh nvarchar(200),
	Email varchar(50),
	Trang_Thai int DEFAULT 0,-- 0 đang làm , 1 nghỉ việc
	Chuc_Vu nvarchar(50), -- Nhân Viên , Trưởng Phòng
	Ngay_Bat_Dau date
)
GO
CREATE TABLE KhachHang(
	ID_Khach_Hang int IDENTITY(1,1) PRIMARY KEY , 
	Ma_Khach_Hang varchar(30) UNIQUE,
	Ten nvarchar(50), 
	SĐT varchar(30), 
	Email varchar(50),	
	Gioi_Tinh bit,	
	Dia_Chi nvarchar(50),
	Trang_Thai int DEFAULT 0 -- 0 tồn tại , 1 đã xóa
)
GO
CREATE TABLE GiamGia(
	ID_Giam_Gia int IDENTITY(1,1) PRIMARY KEY, 
	Ma_Giam_Gia varchar(50) UNIQUE,
	Ten_Chuong_Trinh nvarchar(50),
	Mo_Ta nvarchar(100),
	Ngay_Tao DATETIME DEFAULT GETDATE(), 
	Ngay_Bat_Dau Date, 
	Ngay_Ket_Thuc Date, 
	So_luong int , 
	Kieu_Giam int , -- phần trăm hoặc tiền 0 phần trăm , 1 tiền
	Gia_tri_DH_Toi_Thieu float , -- điều kiện giảm giá
	Muc_Giam_Gia int,      -- mức tiền sẽ giảm theo phần trăm hoặc tiền 
	Muc_Giam_Gia_Toi_Da int ,
	Trang_Thai int DEFAULT 0, 
	ID_Nhan_Vien int
)
GO
-- Thông tin sản phẩm ct
CREATE TABLE XuatXu(
	ID_Xuat_Xu int IDENTITY(1,1) PRIMARY KEY , 
	Xuat_Xu nvarchar(50)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
) 
GO
CREATE TABLE PhanLoai(
	ID_Phan_Loai int IDENTITY(1,1) PRIMARY KEY , 
	Phan_Loai nvarchar(50)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)
GO
CREATE TABLE Rom(
	ID_Rom int IDENTITY(1,1) PRIMARY KEY , 
	Rom nvarchar(30)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)
GO
CREATE TABLE MauSac(
	ID_Mau_Sac int IDENTITY(1,1) PRIMARY KEY , 
	Mau_Sac nvarchar(50)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)
GO
CREATE TABLE Ram(
	ID_Ram int IDENTITY(1,1) PRIMARY KEY , 
	Ram nvarchar(30)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)
GO
CREATE TABLE KichThuoc(
	ID_Kich_Thuoc int IDENTITY(1,1) PRIMARY KEY , 
	Kich_Thuoc nvarchar(50)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)
GO
CREATE TABLE DungLuongPin(
	ID_Dung_Luong_Pin int IDENTITY(1,1) PRIMARY KEY , 
	Dung_Luong_Pin nvarchar(50)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)
GO
CREATE TABLE CPU(
	ID_CPU int IDENTITY(1,1) PRIMARY KEY , 
	CPU nvarchar(50)  not null ,
	Trang_Thai int default 0 -- 0 còn , 0 đã xóa
)

GO
CREATE TABLE Imei(
	ID_Imei int IDENTITY(1,1) PRIMARY KEY , 
	Ma_Imei varchar(20) not null UNIQUE,
	Trang_Thai int DEFAULT 0, -- 0 chưa kết nối sp , 1 còn hàng , 2 đã bán 
	ID_San_Pham int
)
GO
CREATE TABLE ImeiDaBan(
	ID_Imei_Da_Ban int IDENTITY(1,1) PRIMARY KEY , 
	Ma_Imei_Da_Ban varchar(20) not null ,
	Trang_Thai int DEFAULT 0, -- 0 đã trong hóa đơn chi tiết--  nếu hoàn  hàng xóa hẳn dữ liệu của bảng imei cũ
	ID_HDCT int 
)
GO
CREATE TABLE SanPham(
	ID_San_Pham int IDENTITY(1,1) PRIMARY KEY,
	Ma_San_Pham as ('SP' + RIGHT('000' + CAST(id_San_Pham as varchar(4)), 4)) persisted,
	Ten_San_Pham nvarchar(50), 
	Mo_Ta nvarchar(max),
	Ngay_Tao DATETIME DEFAULT GETDATE(),
	So_Luong int ,
	Gia_Nhap float, 
	Gia_Ban float, 
	Hinh_Anh nvarchar(max), 
	ID_Rom int foreign key (ID_Rom) references Rom(ID_Rom), 
	ID_Mau_Sac int foreign key (ID_Mau_Sac) references MauSac(ID_Mau_Sac), 
	ID_Ram int foreign key (ID_Ram) references Ram(ID_Ram), 
	ID_Kich_Thuoc int foreign key (ID_Kich_Thuoc) references KichThuoc(ID_Kich_Thuoc), 
	ID_Dung_Luong_Pin int foreign key (ID_Dung_Luong_Pin) references DungLuongPin(ID_Dung_Luong_Pin), 
	ID_CPU int foreign key (ID_CPU) references CPU(ID_CPU), 
	ID_Xuat_Xu int foreign key (ID_Xuat_Xu) references XuatXu(ID_Xuat_Xu), 
	ID_Phan_Loai int foreign key (ID_Phan_Loai) references PhanLoai(ID_Phan_Loai),
	Trang_Thai int DEFAULT 0 -- 0 còn hàng  , 1 đã xóa ,2 đã bán
)
GO
CREATE TABLE HoaDon(
	ID_Hoa_Don int IDENTITY(1,1) PRIMARY KEY, 
	Ma_Hoa_Don as ('HD' + RIGHT('000' + CAST(ID_Hoa_Don as varchar(4)), 4)) persisted,
	Ngay_Tao DATETIME DEFAULT GETDATE(),
	Ngay_Thanh_Toan Date,
	Tong_Gia float,
	Hinh_Thuc_Thanh_Toan nvarchar(50),
	Ten_Nguoi_Nhan nvarchar(50), 
	SĐT varchar(30), 
	Dia_Chi nvarchar(50), 
	Trang_Thai int DEFAULT 0, -- 0 chưa thanh toán , 1 đã thanh toán
	ID_Khach_Hang int ,
	ID_Giam_Gia int , 
	ID_Nhan_Vien int 
)
GO
CREATE TABLE HoaDonChiTiet(
	ID_HDCT int IDENTITY(1,1) PRIMARY KEY , 
	So_Luong int , -- lấy số lượng khi chọn 
	Gia float, -- lấy giá bán của sp chọn 
	Trang_Thai int DEFAULT 0 ,-- ko dùng đến tất cả để mặc định 
	ID_Hoa_Don int , 
	ID_San_Pham int 
)
GO
--giảm giá- nhân viên 
ALTER TABLE GiamGia
ADD CONSTRAINT FK_GiamGia_NhanVien
FOREIGN KEY (ID_Nhan_Vien) REFERENCES NhanVien(ID_Nhan_Vien);
--hóa đơn - khách hàng 
ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_KhachHang
FOREIGN KEY (ID_Khach_Hang) REFERENCES KhachHang(ID_Khach_Hang);
--hóa đơn - nhân viên
ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_NhanVien
FOREIGN KEY (ID_Nhan_Vien) REFERENCES NhanVien(ID_Nhan_Vien);
-- hóa đơn -giảm giá
ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_GiamGia
FOREIGN KEY (ID_Giam_Gia) REFERENCES GiamGia(ID_Giam_Gia);
-- hdct -hd
ALTER TABLE HoaDonChiTiet
ADD CONSTRAINT FK_HDCT_HD
FOREIGN KEY (ID_Hoa_Don) REFERENCES HoaDon(ID_Hoa_Don);
-- imei -- sp 
ALTER TABLE Imei
ADD CONSTRAINT FK_IMEI_SP
FOREIGN KEY (ID_San_Pham) REFERENCES SanPham(ID_San_Pham);
-- imei đã bán -- hdct 
ALTER TABLE ImeiDaBan
ADD CONSTRAINT FK_IMEIDABAN_HDCT
FOREIGN KEY (ID_HDCT) REFERENCES HoaDonChiTiet(ID_HDCT);
-- hdct -- sp
ALTER TABLE HoaDonChiTiet
ADD CONSTRAINT FK_HDCT_SP
FOREIGN KEY (ID_San_Pham) REFERENCES SanPham(ID_San_Pham);
GO
-- thêm kích thước 
insert into KichThuoc (Kich_Thuoc)values ('4.7 inch')
insert into KichThuoc (Kich_Thuoc)values ('5.5 inch')
insert into KichThuoc (Kich_Thuoc)values ('5.8 inch')
insert into KichThuoc (Kich_Thuoc)values ('6.5 inch')
insert into KichThuoc (Kich_Thuoc)values ('6.7 inch')
-- thêm ram 
insert into Ram (Ram) values ('2 GB')
insert into Ram (Ram) values ('3 GB')
insert into Ram (Ram) values ('4 GB')
insert into Ram (Ram) values ('6 GB')
insert into Ram (Ram) values ('8 GB')
-- thêm rom 
insert into Rom (Rom) values ('64 GB')
insert into Rom (Rom) values ('126 GB')
insert into Rom (Rom) values ('256 GB')
insert into Rom (Rom) values ('512 GB')
insert into Rom (Rom) values ('1 T')
-- thêm dung lượng pin
insert into DungLuongPin (Dung_Luong_Pin) values ('2.675mAh')
insert into DungLuongPin (Dung_Luong_Pin) values ('2.716 mAh')
insert into DungLuongPin (Dung_Luong_Pin) values ('3.174 mAh')
insert into DungLuongPin (Dung_Luong_Pin) values ('4.352 mAh')
insert into DungLuongPin (Dung_Luong_Pin) values ('4.422 mAh')
-- thêm phân loại 
insert into PhanLoai (Phan_Loai) values (N'Mới')
insert into PhanLoai (Phan_Loai) values (N'Cũ 99%')
insert into PhanLoai (Phan_Loai) values (N'Cũ 95%')
insert into PhanLoai (Phan_Loai) values (N'Cũ 90%')
-- xuất xứ
insert into XuatXu (Xuat_Xu) values (N'Hàn Quốc')
insert into XuatXu (Xuat_Xu) values (N'Đài loan')
insert into XuatXu (Xuat_Xu) values (N'Mỹ')
insert into XuatXu (Xuat_Xu) values (N'Anh')
insert into XuatXu (Xuat_Xu) values (N'Brazil')
-- thêm cpu 
insert into CPU (CPU) values ('Apple A11 Bionic')
insert into CPU (CPU) values ('Apple A12 Bionic')
insert into CPU (CPU) values ('Apple A13 Bionic')
insert into CPU (CPU) values ('Apple A14 Bionic')
insert into CPU (CPU) values ('Apple A15 Bionic')
-- thêm màu sắc
insert into MauSac (Mau_Sac) values (N'Hồng')
insert into MauSac (Mau_Sac) values (N'Xanh Dương')
insert into MauSac (Mau_Sac) values (N'Tím')
insert into MauSac (Mau_Sac) values (N'Đen')
insert into MauSac (Mau_Sac) values (N'Xám Bạc')

-- thêm nhân viên
GO
INSERT INTO NhanVien (Ma_NV, Tai_Khoan, Mat_Khau, Ho_Ten, Gioi_Tinh, Ngay_Sinh, Dia_Chi, SDT, Hinh_Anh, Email, Trang_Thai, Ngay_Bat_Dau ,Chuc_Vu )
VALUES
('NV001', 'taikhoan1', 'Matkhau1@', N'Nguyễn Văn A', 0, '1990-01-01', N'123 Đường A', '0123456789', '', 'taikhoan1@gmail.com', 0, '2024-01-01',N'Nhân Viên'),
('NV002', 'taikhoan2', 'Matkhau2@', N'Trần Thị B', 1, '1991-02-02', N'456 Đường B', '0987654321', '', 'taikhoan2@gmail.com', 0,'2024-01-01',N'Nhân Viên'),
('NV003', 'taikhoan3', 'Matkhau3@', N'Lê Văn C', 0, '1992-03-03', N'789 Đường C', '0912345678', '', 'taikhoan3@gmail.com', 0, '2024-01-01',N'Nhân Viên'),
('NV004', 'taikhoan4', 'Matkhau4@', N'Phạm Thị D', 1, '1993-04-04', N'101 Đường D', '0945678901', '', 'taikhoan4@gmail.com', 0, '2024-01-01',N'Nhân Viên'),
('NV005', 'taikhoan5', 'Matkhau5@', N'Hoàng Văn E', 0, '1994-05-05', N'202 Đường E', '0934567890', '', 'taikhoan5@gmail.com', 0,'2024-01-01',N'Nhân Viên');
GO
-- Khach Hang
INSERT INTO KhachHang (Ma_Khach_Hang, Ten, SĐT, Email, Gioi_Tinh, Dia_Chi)
VALUES
('KH001', N'Khách hàng A', '0123456789', 'khachhang1@gmail.com', 0, N'Địa chỉ 1'),
('KH002', N'Khách hàng B', '0987654321', 'khachhang2@gmail.com', 1, N'Địa chỉ 2'),
('KH003', N'Khách hàng C', '0912345678', 'khachhang3@gmail.com', 0, N'Địa chỉ 3'),
('KH004', N'Khách hàng D', '0945678901', 'khachhang4@gmail.com', 1, N'Địa chỉ 4'),
('KH005', N'Khách hàng E', '0934567890', 'khachhang5@gmail.com', 0, N'Địa chỉ 5');
GO
INSERT INTO GiamGia (Ma_Giam_Gia, Ten_Chuong_Trinh, Mo_Ta, Ngay_Bat_Dau, Ngay_Ket_Thuc, So_luong, Kieu_Giam, Muc_Giam_Gia,Muc_Giam_Gia_Toi_Da, Gia_tri_DH_Toi_Thieu, ID_Nhan_Vien)
VALUES
('GG001', N'Chương trình giảm giá A', N'Mô tả A', '2023-01-01', '2023-12-31', 10, 0, 10,200000,100000, 1),--% -- giảm 200K
('GG002', N'Chương trình giảm giá B', N'Mô tả B', '2023-02-01', '2023-11-30', 2, 1, 200000,NULL,200000, 2),-- tiền
('GG003', N'Chương trình giảm giá C', N'Mô tả C', '2023-03-01', '2023-10-31', 3, 0, 15,200000,150000, 3),--%
('GG004', N'Chương trình giảm giá D', N'Mô tả D', '2023-04-01', '2023-09-30', 6, 1, 250000,NULL,250000, 2),-- tiền
('GG005', N'Chương trình giảm giá E', N'Mô tả E', '2023-05-01', '2023-08-31', 9, 0, 30,200000,300000, 1);--%
GO
INSERT INTO SanPham (Ten_San_Pham, Mo_Ta, So_Luong, Gia_Nhap, Gia_Ban, Hinh_Anh, ID_Rom, ID_Mau_Sac, ID_Ram, ID_Kich_Thuoc, ID_Dung_Luong_Pin, ID_CPU, ID_Xuat_Xu, ID_Phan_Loai, Trang_Thai)
VALUES
(N'iPhone 12', N'Sản phẩm mới nhất từ Apple',3, 7000000, 8000000,NULL, 3, 5, 4, 4, 4, 4, 3, 1, 0),-- tổng 4 sp bán 2 còn 2
(N'iPhone 11', N'Mẫu iPhone giá rẻ', 4, 6000000, 7000000,NULL, 2, 4, 3, 3, 3, 3, 2, 2, 0),
(N'iPhone XS', N'Mẫu iPhone cao cấp', 5, 8000000, 9000000,NULL, 3, 2, 4, 3, 3, 2, 1, 3, 0), -- tổng 4 bán 1 còn 3
(N'iPhone XR', N'Mẫu iPhone nhiều màu sắc', 3, 6500000, 7500000,NULL, 1, 3, 2, 2, 2, 1, 5, 2, 0), -- tổng 3 bán 2 còn 1
(N'iPhone 8', N'Mẫu iPhone kích thước nhỏ', 7, 5000000, 6000000,NULL, 1, 1, 1, 1, 1, 5, 4, 4, 0); -- tổng 4 chx bán 
--
go
insert into imei (Ma_Imei,Trang_Thai,ID_San_Pham) values
('000000000000000',2,1),-- Đã bán
('000000000000001',2,1),-- Đã bán
('000000000000002',1,1),
('000000000000003',1,1),
('000000000000017',1,1), -- ms 
('000000000000018',2,1),--ms imei db--
('000000000000019',2,1),--ms imei db--
('000000000000020',2,1),--ms imei db--
('000000000000004',1,2),
('000000000000005',1,2),
('000000000000021',2,2),-- ms imei db--
('000000000000022',2,2),--ms imei db --
('000000000000023',2,2),--ms imei db
('000000000000024',2,2),--ms imei db
('000000000000025',1,2),
('000000000000026',1,2),
('000000000000006',1,3),
('000000000000007',2,3),-- Đã bán--
('000000000000008',1,3),
('000000000000009',1,3),
('000000000000027',2,3),--ms imei db--
('000000000000028',2,3),--ms imei db--
('000000000000029',1,3),
('000000000000030',1,3),
('000000000000010',2,4),-- Đã bán
('000000000000011',2,4),-- đã bán
('000000000000012',1,4),
('000000000000031',1,4),
('000000000000032',2,4),--ms imei db--
('000000000000033',2,4),--ms imei db--
('000000000000034',1,4),
('000000000000013',1,5),
('000000000000014',1,5),
('000000000000015',1,5),
('000000000000016',1,5),
('000000000000035',2,5),--ms imei db
('000000000000036',1,5),--ms imei db
('000000000000037',1,5),
('000000000000038',1,5);


GO
--Hoa Don
INSERT INTO HoaDon (Ngay_Thanh_Toan, Tong_Gia, Hinh_Thuc_Thanh_Toan, Ten_Nguoi_Nhan, SĐT, Dia_Chi, Trang_Thai, ID_Khach_Hang, ID_Giam_Gia, ID_Nhan_Vien)
VALUES 
('2024-06-15',16800000, N'Tiền mặt', N'Nguyễn Văn A', '0123456789', N'123 Đường A', 1,1,1, 1),-- 1 đã thanh toán 2sp của sp 1 -- giảm 200K
('2021-02-20',16500000, N'Tiền mặt', N'Khách Lẻ', '0987654321', N'456 Đường B', 1,NULL,2, 2), -- đã tt 2 sp của (1 sp 3 và 1 sp 4) -- giảm 200K
('2020-03-25',7500000, N'Tiền Mặt', N'Lê Văn C', '0912345678', N'789 Đường C', 1, 3,NULL, 1),  -- đã tt
(NULL,23000000,NULL,NULL,NULL,NULL, 0,NULL,NULL, 2),	-- 0 chưa thanh toán -- khách lẻ 
(NULL,17000000,NULL,NULL,NULL,NULL, 0,4,NULL,1), -- 0 chưa thanh toán -- khách hàng 
--
('2024-03-30',16000000, N'Tiền Mặt', N'Khách Lẻ', '0912345678',NULL, 1, NULL,NULL, 1),
('2023-04-25',7000000, N'Tiền Mặt', N'Khách Lẻ', '0912345611', NULL, 1, NULL,NULL, 1),
('2024-03-25',7000000, N'Chuyển Khoản', N'Khách Lẻ', '0912345622', NULL, 1,NULL,NULL, 1),
('2023-01-21',9000000, N'Tiền Mặt', N'Nguyễn Văn A', '0123456789', N'Địa chỉ 1', 1, 3,NULL, 1),-- khach 1 
('2021-02-20',6000000, N'Tiền Mặt', N'Lê Văn F', '0912345644', N'Địa chỉ 2', 1, 3,NULL, 2), --- khach 2
('2024-06-20',6000000, N'Cả Hai', N'Lê Văn G', '0912345655', N'Địa chỉ 3', 1, 3,NULL, 3); -- khach 3
GO
-- hóa đơn chi tiết 
insert into HoaDonChiTiet (So_Luong,Gia,ID_Hoa_Don,ID_San_Pham) VALUES
(2,8000000,1,1), -- hoa don chi tiet khach hang 1 
(1,9000000,2,3), -- hoa don thứ 2 của khách lẻ sp 3
(1,7500000,2,4), -- hoa don thứ 2 của khách lẻ sp 4
(1,7500000,3,4), -- hoa don thứ 3 của khách hang 3 sp 4
--
(2,8000000,4,1),
(1,7000000,4,2),-- hóa đơn chờ thứ 1 -4
(1,8000000,5,1),-- hóa đơn chờ thứ 2 -5
(1,9000000,5,3),
(1,7000000,6,2), -- hóa đơn thứ 6
(2,9000000,6,4),
(1,7000000,7,2),
(1,7000000,8,2),
(1,9000000,9,3),
(1,6000000,10,5),
(1,6000000,11,5);
go
-- imei đã bán
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('00000000000000',0,1) -- hdct 1 đã tt  idsp 1 hd 1 -- đã tt
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('00000000000001',0,1) -- hdct 1	đã tt idsp 1 hd 1 
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('00000000000007',0,2) -- hdct 2	đã tt idsp 3 hd 2
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000010',0,3)-- hdct 3	đã tt id sp 4 hd 2
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000011',0,4)-- hdct 4	đã tt id sp 4 hd 3
--
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000018',0,5) -- hd 4
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000019',0,5) -- hd 4
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000020',0,7) -- hd 5
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000021',0,6)  -- hd4
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000022',0,9)  -- hd 6
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000023',0,11) -- hd 7
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000024',0,12) -- hd 8
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000027',0,8) -- hd 5
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000028',0,13)  -- hd 9
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000032',0,10) -- hd6
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000033',0,10) --hd6
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000035',0,14) --hd 10
insert into ImeiDaBan (Ma_Imei_Da_Ban,Trang_Thai,ID_HDCT) values ('000000000000036',0,15); -- hd 11





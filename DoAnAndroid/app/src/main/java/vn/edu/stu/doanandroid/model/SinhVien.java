package vn.edu.stu.doanandroid.model;


import java.sql.Blob;
import java.sql.Date;

public class SinhVien  {
    private String MaSV;
    private String TenSV;
    private String MaLop;
    private byte[] HinhAnh;
    private String DiaChi;
    private Date NgaySinh;

    public SinhVien() {
    }

    public SinhVien(String maSV, String tenSV, String maLop, byte[] hinhAnh, String diaChi, Date ngaySinh) {
        MaSV = maSV;
        TenSV = tenSV;
        MaLop = maLop;
        HinhAnh = hinhAnh;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getTenSV() {
        return TenSV;
    }

    public void setTenSV(String tenSV) {
        TenSV = tenSV;
    }

    public String getMaLop() {
        return MaLop;
    }

    public void setMaLop(String maLop) {
        MaLop = maLop;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        NgaySinh = ngaySinh;
    }
}

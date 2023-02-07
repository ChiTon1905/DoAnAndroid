package vn.edu.stu.doanandroid.model;


import java.io.Serializable;

public class Lop implements Serializable {
    private String Malop;
    private String TenLop;

    public String getMalop() {
        return Malop;
    }

    public void setMalop(String malop) {
        Malop = malop;
    }

    public String getTenLop() {
        return TenLop;
    }

    public void setTenLop(String tenLop) {
        TenLop = tenLop;
    }

    public Lop() {
    }

    public Lop(String malop, String tenLop) {
        Malop = malop;
        //TenLop = tenLop;
    }


}

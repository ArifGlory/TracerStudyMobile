package myproject.tracerstudy.Kelas;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Alumni implements Serializable {
    public String idAlumni;
    public String nama;
    public String tahun_lulus;
    public String idJurusan;
    public String nis;
    public String nisn;
    public String phone;
    public String email;
    public String alamat;
    public String pekerjaan;
    public String foto;

    public Alumni(String idAlumni, String nama, String tahun_lulus, String idJurusan, String nis, String nisn, String phone, String email, String alamat, String pekerjaan, String foto) {
        this.idAlumni = idAlumni;
        this.nama = nama;
        this.tahun_lulus = tahun_lulus;
        this.idJurusan = idJurusan;
        this.nis = nis;
        this.nisn = nisn;
        this.phone = phone;
        this.email = email;
        this.alamat = alamat;
        this.pekerjaan = pekerjaan;
        this.foto = foto;
    }

    public Alumni(){}

    public String getIdAlumni() {
        return idAlumni;
    }

    public void setIdAlumni(String idAlumni) {
        this.idAlumni = idAlumni;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahun_lulus() {
        return tahun_lulus;
    }

    public void setTahun_lulus(String tahun_lulus) {
        this.tahun_lulus = tahun_lulus;
    }

    public String getIdJurusan() {
        return idJurusan;
    }

    public void setIdJurusan(String idJurusan) {
        this.idJurusan = idJurusan;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

package myproject.tracerstudy.Kelas;

import java.io.Serializable;

public class UserModel implements Serializable {
    public String idAlumni;
    public String password;
    public String nis;
    public String nisn;
    public String namaAlumni;
    public String noHape;
    public String email;
    public String alamat;
    public String tahunLulus;
    public String pekerjaan;
    public String foto;
    public String idJurusan;
    public String publish;

    public UserModel(){}

    public UserModel(String idAlumni, String password, String nis, String nisn, String namaAlumni, String noHape, String email, String alamat, String tahunLulus, String pekerjaan, String foto, String idJurusan, String publish) {
        this.idAlumni = idAlumni;
        this.password = password;
        this.nis = nis;
        this.nisn = nisn;
        this.namaAlumni = namaAlumni;
        this.noHape = noHape;
        this.email = email;
        this.alamat = alamat;
        this.tahunLulus = tahunLulus;
        this.pekerjaan = pekerjaan;
        this.foto = foto;
        this.idJurusan = idJurusan;
        this.publish = publish;
    }

    public String getIdAlumni() {
        return idAlumni;
    }

    public void setIdAlumni(String idAlumni) {
        this.idAlumni = idAlumni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getNamaAlumni() {
        return namaAlumni;
    }

    public void setNamaAlumni(String namaAlumni) {
        this.namaAlumni = namaAlumni;
    }

    public String getNoHape() {
        return noHape;
    }

    public void setNoHape(String noHape) {
        this.noHape = noHape;
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

    public String getTahunLulus() {
        return tahunLulus;
    }

    public void setTahunLulus(String tahunLulus) {
        this.tahunLulus = tahunLulus;
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

    public String getIdJurusan() {
        return idJurusan;
    }

    public void setIdJurusan(String idJurusan) {
        this.idJurusan = idJurusan;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
}

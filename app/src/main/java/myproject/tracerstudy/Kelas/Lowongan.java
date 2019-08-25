package myproject.tracerstudy.Kelas;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Lowongan implements Serializable {

    public String idLowongan;
    public String judul;
    public String deskripsi;
    public String foto;

    public Lowongan(){}

    public Lowongan(String idLowongan, String judul, String deskripsi, String foto) {
        this.idLowongan = idLowongan;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.foto = foto;
    }

    public String getIdLowongan() {
        return idLowongan;
    }

    public void setIdLowongan(String idLowongan) {
        this.idLowongan = idLowongan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

package myproject.tracerstudy.Kelas;

import java.io.Serializable;

public class Pertanyaan implements Serializable {

    public String idPertanyaan;
    public String pertanyaan;
    public String idKuesioner;

    public Pertanyaan(){}

    public Pertanyaan(String idPertanyaan, String pertanyaan, String idKuesioner) {
        this.idPertanyaan = idPertanyaan;
        this.pertanyaan = pertanyaan;
        this.idKuesioner = idKuesioner;
    }

    public String getIdPertanyaan() {
        return idPertanyaan;
    }

    public void setIdPertanyaan(String idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getIdKuesioner() {
        return idKuesioner;
    }

    public void setIdKuesioner(String idKuesioner) {
        this.idKuesioner = idKuesioner;
    }
}

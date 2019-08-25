package myproject.tracerstudy.Kelas;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Kuesioner implements Serializable {

    public String idKuesioner;
    public String namaKuesioner;
    public String created;
    public String idAdmin;

    public Kuesioner(){}

    public Kuesioner(String idKuesioner, String namaKuesioner, String created, String idAdmin) {
        this.idKuesioner = idKuesioner;
        this.namaKuesioner = namaKuesioner;
        this.created = created;
        this.idAdmin = idAdmin;
    }

    public String getIdKuesioner() {
        return idKuesioner;
    }

    public void setIdKuesioner(String idKuesioner) {
        this.idKuesioner = idKuesioner;
    }

    public String getNamaKuesioner() {
        return namaKuesioner;
    }

    public void setNamaKuesioner(String namaKuesioner) {
        this.namaKuesioner = namaKuesioner;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }
}

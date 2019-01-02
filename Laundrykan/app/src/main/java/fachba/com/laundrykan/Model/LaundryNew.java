package fachba.com.laundrykan.Model;

import com.orm.SugarRecord;

public class LaundryNew {

    private String nama,jamoperasi,longi,lati;

    public LaundryNew(String nama, String jamoperasi, String longi, String lati) {
        this.nama = nama;
        this.jamoperasi = jamoperasi;
        this.longi = longi;
        this.lati = lati;
    }

    public LaundryNew() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJamoperasi() {
        return jamoperasi;
    }

    public void setJamoperasi(String jamoperasi) {
        this.jamoperasi = jamoperasi;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }
}

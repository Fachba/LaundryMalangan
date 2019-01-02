package fachba.com.laundrykan.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;

public class LaundryIntent implements Parcelable {
    private String nama,jamoperasi,longi,lati;

    public LaundryIntent() {
    }

    public LaundryIntent(String nama, String jamoperasi, String longi, String lati) {
        this.nama = nama;
        this.jamoperasi = jamoperasi;
        this.longi = longi;
        this.lati = lati;
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

    public static Creator<LaundryIntent> getCREATOR() {
        return CREATOR;
    }

    protected LaundryIntent(Parcel in) {
    }

    public static final Creator<LaundryIntent> CREATOR = new Creator<LaundryIntent>() {
        @Override
        public LaundryIntent createFromParcel(Parcel in) {
            return new LaundryIntent(in);
        }

        @Override
        public LaundryIntent[] newArray(int size) {
            return new LaundryIntent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

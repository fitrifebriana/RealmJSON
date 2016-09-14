package id.or.codelabs.realmjson.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by FitriFebriana on 8/31/2016.
 */
public class Book extends RealmObject {

    @PrimaryKey
    private String kdBuku;
    private String judulBuku;
    private String pengarang;

    public Book() {

    }

    public String getKdBuku() {
        return kdBuku;
    }

    public void setKdBuku(String kdBuku) {
        this.kdBuku = kdBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }
}

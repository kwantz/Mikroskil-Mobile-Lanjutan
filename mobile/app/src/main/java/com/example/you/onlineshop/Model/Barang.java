package com.example.you.onlineshop.Model;

public class Barang {

    private Integer id;
    private String lokasiFoto;
    private String nama;
    private String keterangan;
    private Integer stock;
    private Integer harga;
    private Integer diskon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLokasiFoto() {
        return lokasiFoto;
    }

    public void setLokasiFoto(String lokasiFoto) {
        this.lokasiFoto = lokasiFoto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getDiskon() {
        return diskon;
    }

    public void setDiskon(Integer diskon) {
        this.diskon = diskon;
    }
}

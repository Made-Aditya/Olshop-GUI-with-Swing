// Classes.proses.ProductDataFactory.java
package Classes.proses;

import Classes.kelas.listProduk;
import Classes.kelas.produk;

public class ProductDataFactory {
    /** Membuat data produk untuk kategori Elektronik. */
    public listProduk createElektronikData() { // Ganti ke public
        listProduk Elektronik = new listProduk(5, "Elektronik");
        Elektronik.tambahProduk(new produk("Laptop", 101, 15000000));
        Elektronik.tambahProduk(new produk("Smartphone", 102, 8000000));
        Elektronik.tambahProduk(new produk("Tablet", 103, 5000000));
        Elektronik.tambahProduk(new produk("Smartwatch", 104, 2000000));
        Elektronik.tambahProduk(new produk("Headphones", 1000005, 1000000));
        return Elektronik;
    }

    /** Membuat data produk untuk kategori Buku. */
    public listProduk createBukuData() { // Ganti ke public
        listProduk Buku = new listProduk(5, "Buku");
        Buku.tambahProduk(new produk("Novel abc", 201, 100000));
        Buku.tambahProduk(new produk("Komik abc", 202, 50000));
        Buku.tambahProduk(new produk("Ensiklopedia abc", 203, 200000));
        Buku.tambahProduk(new produk("Biografi abc", 204, 150000));
        Buku.tambahProduk(new produk("Majalah abc", 205, 75000));
        return Buku;
    }
}
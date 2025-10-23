package Classes.proses;

import Classes.kelas.listProduk;
import Classes.kelas.AbstractProduk;
import Classes.kelas.ElektronikProduk; // Import Subclass Elektronik
import Classes.kelas.BukuProduk;       // Import Subclass Buku

public class ProductDataFactory {
    /** Membuat data produk untuk kategori Elektronik. */
    public listProduk createElektronikData() {
        listProduk Elektronik = new listProduk(5, "Elektronik");

        // Ganti new AbstractProduk dengan new ElektronikProduk dan tambahkan properti unik (Garansi, Daya)
        Elektronik.tambahProduk(new ElektronikProduk("Laptop", 101, 15000000, 24, 65));
        Elektronik.tambahProduk(new ElektronikProduk("Smartphone", 102, 8000000, 12, 10));
        Elektronik.tambahProduk(new ElektronikProduk("Tablet", 103, 5000000, 12, 15));
        Elektronik.tambahProduk(new ElektronikProduk("Smartwatch", 104, 2000000, 6, 5));
        Elektronik.tambahProduk(new ElektronikProduk("Headphones", 1000005, 1000000, 3, 2));

        return Elektronik;
    }

    /** Membuat data produk untuk kategori Buku. */
    public listProduk createBukuData() {
        listProduk Buku = new listProduk(5, "Buku");

        // Ganti new AbstractProduk dengan new BukuProduk dan tambahkan properti unik (Penulis, ISBN)
        Buku.tambahProduk(new BukuProduk("Novel abc", 201, 100000, "Budi Santoso", "978-602-001"));
        Buku.tambahProduk(new BukuProduk("Komik abc", 202, 50000, "Cinta Risa", "978-602-002"));
        Buku.tambahProduk(new BukuProduk("Ensiklopedia abc", 203, 200000, "Tim Edukasi", "978-602-003"));
        Buku.tambahProduk(new BukuProduk("Biografi abc", 204, 150000, "Ahmad Fajar", "978-602-004"));
        Buku.tambahProduk(new BukuProduk("Majalah abc", 205, 75000, "Redaksi Media", "978-602-005"));

        return Buku;
    }
}
// Classes.kelas.BukuProduk.java
package Classes.kelas;

/**
 * Merepresentasikan produk kategori Buku.
 * Mewarisi properti dasar dari AbstractProduk.
 */
public class BukuProduk extends AbstractProduk {
    // Properti unik untuk Buku
    private final String penulis;
    private final String isbn;

    /**
     * Konstruktor untuk produk buku.
     * @param nama_produk Nama buku.
     * @param id_produk ID unik.
     * @param harga_produk Harga.
     * @param penulis Nama penulis buku.
     * @param isbn Nomor ISBN.
     */
    public BukuProduk(String nama_produk, int id_produk, long harga_produk,
                      String penulis, String isbn) {
        // Panggil konstruktor AbstractProduk (Master Class)
        super(nama_produk, id_produk, harga_produk);
        this.penulis = penulis;
        this.isbn = isbn;
    }

    // Getter untuk properti unik
    public String getPenulis() {
        return penulis;
    }

    public String getIsbn() {
        return isbn;
    }

    /** Implementasi wajib dari metode abstrak AbstractProduk. */
    @Override
    public String getDeskripsiSingkat() {
        return "Penulis: " + penulis + ", ISBN: " + isbn;
    }
}
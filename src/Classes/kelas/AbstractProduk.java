package Classes.kelas;

/**
 * Kelas abstrak ini berfungsi sebagai master class untuk semua jenis produk.
 * Kelas lain (seperti ElektronikProduk, BukuProduk) HARUS mewarisi (extend) dari kelas ini.
 */
public abstract class AbstractProduk {
    // --- Data (Variabel) yang disimpan oleh SEMUA produk (protected untuk akses subclass) ---

    // Properti dasar diubah menjadi 'protected' agar dapat diakses langsung oleh subclass.
    protected final String nama_produk;
    protected final int id_produk;
    protected final long harga_produk;

    // --- Konstruktor (Hanya untuk dipanggil oleh subclass) ---

    /**
     * Konstruktor ini digunakan oleh subclass untuk menginisialisasi properti dasar produk.
     * @param nama_produk Nama dari barang.
     * @param id_produk ID unik dari barang.
     * @param harga_produk Harga jual barang.
     */
    public AbstractProduk(String nama_produk, int id_produk, long harga_produk){
        this.nama_produk = nama_produk;
        this.id_produk = id_produk;
        this.harga_produk = harga_produk;
    }

    // --- Getter (Tetap public) ---

    /** Mengembalikan nama produk. */
    public String getNama_produk(){
        return nama_produk;
    }

    /** Mengembalikan ID produk. */
    public int getId_produk(){
        return id_produk;
    }

    /** Mengembalikan harga produk. */
    public long getHarga_produk(){
        return harga_produk;
    }

    // --- Metode Abstrak (Wajib diimplementasikan oleh setiap Subclass) ---

    /**
     * Memberikan deskripsi spesifik tentang produk (misalnya: penulis buku, garansi elektronik).
     * @return String yang menjelaskan properti unik produk.
     */
    public abstract String getDeskripsiSingkat();
}
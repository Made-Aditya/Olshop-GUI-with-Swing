package Classes.kelas;

// Kelas 'produk' ini adalah cetak biru (blueprint) untuk MEREKAM data satu jenis barang.
// Setiap kali kita mau bikin barang baru, kita pakai kelas ini.
public class produk{
    // --- Data (Variabel) yang disimpan oleh SETIAP produk ---

    // Nama produk (misalnya: Laptop, Novel abc). Pakai 'final' karena nama tidak akan berubah setelah dibuat.
    private final String nama_produk;

    // ID unik untuk produk. Pakai 'final' karena ID juga tidak boleh berubah.
    private final int id_produk;

    // Harga produk. Kita pakai 'long' (angka yang sangat besar) untuk jaga-jaga
    // kalau harganya melebihi batas 'int' (walaupun harganya masih int).
    private final long harga_produk;

    // --- Konstruktor (Cara membuat objek produk baru) ---

    /**
     * Konstruktor ini dipanggil setiap kali kita membuat objek 'produk' baru.
     * Tugasnya: Menerima data nama, ID, dan harga, lalu menyimpannya.
     * @param nama_produk Nama dari barang (String).
     * @param id_produk ID unik dari barang (int).
     * @param harga_produk Harga jual barang (int, tapi disimpan sebagai long).
     */
    public produk(String nama_produk, int id_produk, long harga_produk){
        this.nama_produk = nama_produk;
        this.id_produk = id_produk;
        // Kita simpan nilai harga ke variabel internal
        this.harga_produk = harga_produk;
    }

    // --- Getter (Cara mengambil data dari objek) ---

    /**
     * Mengembalikan nama produk.
     * @return Nama produk (String).
     */
    public String getNama_produk(){
        return nama_produk;
    }

    /**
     * Mengembalikan ID produk.
     * @return ID produk (int).
     */
    public int getId_produk(){
        return id_produk;
    }

    /**
     * Mengembalikan harga produk.
     * Kita sediakan sebagai 'long' untuk memastikan angka besar aman.
     * @return Harga produk (long).
     */
    public long getHarga_produk(){
        return harga_produk;
    }
}

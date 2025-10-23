// Classes.kelas.ElektronikProduk.java
package Classes.kelas;

/**
 * Merepresentasikan produk kategori Elektronik.
 * Mewarisi properti dasar dari AbstractProduk.
 */
public class ElektronikProduk extends AbstractProduk {
    // Properti unik untuk Elektronik
    private final int garansi_bulan; // Misalnya, garansi dalam bulan
    private final int daya_watt;     // Misalnya, konsumsi daya

    /**
     * Konstruktor untuk produk elektronik.
     * @param nama_produk Nama barang.
     * @param id_produk ID unik.
     * @param harga_produk Harga.
     * @param garansi_bulan Durasi garansi.
     * @param daya_watt Konsumsi daya.
     */
    public ElektronikProduk(String nama_produk, int id_produk, long harga_produk,
                            int garansi_bulan, int daya_watt) {
        // Panggil konstruktor AbstractProduk (Master Class)
        super(nama_produk, id_produk, harga_produk);
        this.garansi_bulan = garansi_bulan;
        this.daya_watt = daya_watt;
    }

    // Getter untuk properti unik
    public int getGaransi_bulan() {
        return garansi_bulan;
    }

    public int getDaya_watt() {
        return daya_watt;
    }

    /** Implementasi wajib dari metode abstrak AbstractProduk. */
    @Override
    public String getDeskripsiSingkat() {
        return "Garansi: " + garansi_bulan + " bulan, Daya: " + daya_watt + "W";
    }
}
package Classes.kelas;

import java.util.ArrayList;
import java.util.List;

// Kelas 'keranjang' ini adalah inti dari sistem belanja kita.
// Tugasnya: menyimpan daftar produk yang mau dibeli, melacak total harga, dan menyimpan saldo uang pengguna.
public class keranjang {
    // List yang digunakan untuk menyimpan objek 'produk' yang dimasukkan ke keranjang.
    // Kita pakai ArrayList (bukan array biasa) karena ukurannya bisa bertambah/berkurang dengan mudah.
    private List<produk> produk_keranjang;
    // Total harga dari semua produk yang ada di dalam keranjang.
    private int totalHarga;
    // Saldo uang yang dimiliki pengguna.
    private int saldo;

    /**
     * Konstruktor untuk membuat objek keranjang baru.
     * @param saldo Saldo awal uang yang dimiliki pengguna.
     */
    public keranjang(int saldo){
        // Inisialisasi keranjang sebagai ArrayList kosong
        produk_keranjang = new ArrayList<>();
        totalHarga = 0; // Total harga awal pasti nol
        this.saldo = saldo; // Simpan saldo awal
    }

    /**
     * Menambahkan objek produk ke keranjang.
     * Ini adalah metode utama untuk memasukkan barang.
     * @param p Objek produk yang mau ditambahkan.
     * @return true jika berhasil ditambahkan, false jika keranjang penuh (kapasitas maks 10).
     */
    public boolean tambahproduk_keranjang(produk p){
        // Kita batasi maksimum 10 produk di keranjang (pakai produk_keranjang.size() untuk tahu jumlahnya)
        if (produk_keranjang.size() < 10) {
            produk_keranjang.add(p); // Tambahkan produk ke List

            // PENTING: Update total harga!
            totalHarga += p.getHarga_produk();
            return true;
        } else {
            System.out.println("Keranjang penuh");
            return false;
        }
    }

    /**
     * Metode Overload: Menambahkan produk ke keranjang hanya dengan tahu ID-nya dan kategori tempatnya berada.
     * Metode ini akan memanggil metode tambahproduk_keranjang(produk p) di atas.
     * @param id_produk ID produk yang mau ditambahkan.
     * @param kategori Objek listProduk tempat produk itu berada.
     * @return true jika berhasil, false jika produk tidak ditemukan atau keranjang penuh.
     */
    public boolean tambahproduk_keranjang(int id_produk, listProduk kategori){
        // Cari objek produk berdasarkan ID di kategori
        produk p = kategori.getProdukById(id_produk);
        if(p != null){
            // Kalau ketemu, panggil lagi metode tambahproduk_keranjang(produk p)
            return tambahproduk_keranjang(p);
        } else {
            System.out.println("Produk dengan ID " + id_produk + " tidak ditemukan di kategori " + kategori.getNamaKategori());
            return false;
        }
    }

    /**
     * Menghapus produk dari keranjang berdasarkan ID produknya.
     * @param id ID produk yang mau dihapus.
     * @return true jika berhasil dihapus, false jika keranjang kosong atau produk tidak ditemukan.
     */
    public boolean hapusproduk_keranjang(int id){
        if(produk_keranjang.isEmpty()){
            System.out.println("Keranjang kosong");
            return false;
        }
        else{
            // Loop melalui setiap produk di keranjang
            for(int i = 0; i < produk_keranjang.size(); i++){
                produk temp = produk_keranjang.get(i); // Ambil produk

                // Cek apakah ID produknya cocok
                if(temp.getId_produk() == id){
                    // PENTING: Kurangi total harga sebelum dihapus!
                    totalHarga -= temp.getHarga_produk();

                    produk_keranjang.remove(i); // Hapus produk dari List
                    return true;
                }
            }
            return false; // Produk tidak ditemukan setelah dicek semua
        }
    }

    /**
     * Metode ini TINGGALAN (belum dipakai di Main.java) untuk logika membeli.
     * Di Main.java, logika beli dilakukan dengan menghapus produk dan mengurangi saldo secara terpisah.
     * @param pil_produk Array yang berisi pilihan produk (saat ini tidak digunakan).
     * @return true jika syarat pembelian terpenuhi (keranjang ada isi & saldo cukup).
     */
    public boolean beliproduk_keranjang(int pil_produk[]){
        if(produk_keranjang.isEmpty() || saldo < totalHarga){
            System.out.println("Keranjang kosong atau Saldo tidak cukup");
            return false;
        }
        // Di aplikasi GUI (Main.java), logika pengurangan saldo dan penghapusan produk dilakukan di Main.java
        return true;
    }

    /**
     * Mengembalikan semua produk di keranjang dalam bentuk Array (bukan List).
     * Ini digunakan oleh KeranjangTabelModel untuk membaca datanya.
     * @return Array berisi semua objek produk di keranjang.
     */
    public produk[] getProduk_keranjang(){
        // Konversi ArrayList menjadi Array biasa (wajib dilakukan untuk kompatibilitas)
        return produk_keranjang.toArray(new produk[produk_keranjang.size()]);
    }

    // --- Getter & Setter ---

    /**
     * Mendapatkan saldo uang pengguna saat ini.
     * @return Nilai saldo (int).
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * Mengatur atau mengubah nilai saldo.
     * Ini dipanggil dari Main.java setelah pengguna selesai membeli.
     * @param saldo Nilai saldo baru.
     */
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    /**
     * Mendapatkan total harga belanjaan di keranjang.
     * @return Nilai total harga (int).
     */
    public int getTotalHarga(){
        return totalHarga;
    }

    /**
     * Mendapatkan jumlah produk yang ada di keranjang.
     * @return Jumlah item (int).
     */
    public int getJumlahProduk_keranjang(){
        return produk_keranjang.size();
    }

    /**
     * Mengubah isi keranjang menjadi teks yang mudah dibaca (untuk debugging/cetak).
     * @return String deskripsi lengkap keranjang.
     */
    @Override
    public String toString(){
        String str = "Isi Keranjang Anda:\n";
        str += "Saldo Anda: " + saldo + "\n";
        int index = 1;
        for(produk temp : produk_keranjang){
            str += index + ". " + temp.getNama_produk() + " - ID: " + temp.getId_produk() + " - Harga: " + temp.getHarga_produk() + "\n";
            index++;
        }
        str += "Total Harga: " + totalHarga + "\n";
        return str;
    }
}

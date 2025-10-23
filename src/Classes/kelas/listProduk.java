package Classes.kelas;

// Kelas ini bertindak sebagai 'Gudang' atau daftar untuk menyimpan banyak objek 'produk'
// dalam sebuah array, dan juga menyimpan nama kategori (misalnya: "Elektronik").
public class listProduk {
    // Array tempat semua objek produk akan disimpan. Ingat, array punya kapasitas tetap!
    private AbstractProduk AbstractProduk[];
    // Nama dari kategori produk ini (misalnya: "Elektronik", "Buku")
    private String namaKategori;
    // Counter untuk melacak berapa banyak produk yang SUDAH terisi di dalam array.
    // Ini PENTING, karena 'produk.length' adalah kapasitas, sedangkan 'jumlahProduk' adalah isinya.
    private int jumlahProduk;

    // --- Konstruktor Standar ---
    public listProduk(){
        // Konstruktor default: kapasitas array produk kita atur 10.
        AbstractProduk = new AbstractProduk[10];
        jumlahProduk = 0; // Awalnya, belum ada produk
    }

    // --- Konstruktor Khusus ---
    /**
     * Konstruktor ini memungkinkan kita menentukan kapasitas array dan nama kategori.
     * @param kapasitas Ukuran maksimum array produk.
     * @param namaKategori Nama kategori ini.
     */
    public listProduk(int kapasitas, String namaKategori){
        AbstractProduk = new AbstractProduk[kapasitas];
        jumlahProduk = 0;
        this.namaKategori = namaKategori;
    }

    // --- Metode Inti (Manajemen Produk) ---

    /**
     * Menambahkan produk baru ke posisi berikutnya yang kosong di array.
     * @param p Objek produk yang akan ditambahkan.
     * @return true jika berhasil ditambahkan, false jika array sudah penuh (kapasitas terlampaui).
     */
    public boolean tambahProduk(AbstractProduk p){
        // Cek: Apakah jumlah produk saat ini (jumlahProduk) masih kurang dari kapasitas array (produk.length)?
        if(jumlahProduk < AbstractProduk.length){
            AbstractProduk[jumlahProduk] = p; // Tambahkan produk di posisi saat ini
            jumlahProduk++; // Naikkan counternya (jumlah produk sekarang bertambah satu)
            return true;
        } else {
            // Array sudah penuh!
            return false;
        }
    }

    /**
     * Menghapus produk berdasarkan ID-nya dan menggeser elemen setelahnya ke depan.
     * Ini penting karena kita tidak mau ada slot kosong di tengah array setelah produk dihapus.
     * @param id ID produk yang akan dihapus.
     * @return true jika berhasil dihapus, false jika ID produk tidak ditemukan.
     */
    public boolean hapusProduk(int id){
        // Loop melalui produk yang sudah terisi (sampai jumlahProduk saja)
        for(int i = 0; i < jumlahProduk; i++){
            AbstractProduk temp = AbstractProduk[i];
            // Cek apakah ID produk saat ini sama dengan ID yang mau dihapus
            if(temp.getId_produk() == id){

                // --- Logika Geser Array (Shifting) ---
                // Loop untuk menggeser semua elemen di belakang ke kiri satu posisi
                for(int j = i; j < jumlahProduk - 1; j++){
                    AbstractProduk[j] = AbstractProduk[j + 1];
                }

                // Set posisi terakhir yang tadinya terisi menjadi null
                AbstractProduk[jumlahProduk - 1] = null;
                jumlahProduk--; // Kurangi hitungan produk yang terisi
                return true;
            }
        }
        return false; // Produk dengan ID tersebut tidak ditemukan
    }

    /**
     * Mencari produk yang namanya mengandung kata kunci tertentu (tidak peduli huruf besar/kecil).
     * @param nama Kata kunci pencarian.
     * @return Array berisi hasil produk yang cocok.
     */
    public AbstractProduk[] searchProduk(String nama){
        // Kita buat array hasil sementara dengan ukuran maksimum (seukuran array produk)
        AbstractProduk[] hasil = new AbstractProduk[AbstractProduk.length];
        int index = 0; // Counter untuk array 'hasil'

        for(int i = 0; i < jumlahProduk; i++){
            AbstractProduk temp = AbstractProduk[i];
            // Konversi nama produk dan kata kunci ke huruf kecil (toLowerCase) agar pencarian case-insensitive
            if(temp.getNama_produk().toLowerCase().contains(nama.toLowerCase())){
                hasil[index] = temp;
                index++;
            }
        }

        // --- Mencetak Hasil ke Console (Untuk debugging) ---
        System.out.println("Hasil pencarian untuk '"+ nama +"' :");
        for(int i = 0; i < index; i++){
            AbstractProduk temp = hasil[i];
            System.out.println("- " + temp.getNama_produk() + " (ID: " + temp.getId_produk() + ", Harga: " + temp.getHarga_produk() + ")");
        }

        return hasil;
    }

    /**
     * Mendapatkan objek produk berdasarkan ID uniknya.
     * @param id_produk ID produk yang dicari.
     * @return Objek produk jika ditemukan, atau null jika tidak ada.
     */
    public AbstractProduk getProdukById(int id_produk){
        for(int i = 0; i < jumlahProduk; i++){
            AbstractProduk temp = AbstractProduk[i];
            if(temp.getId_produk() == id_produk){
                return temp;
            }
        }
        return null;
    }

    // --- Getter (Untuk Mengambil Informasi Kelas) ---

    /**
     * Mendapatkan nama kategori.
     * @return Nama kategori (String).
     */
    public String getNamaKategori() {
        return namaKategori;
    }

    /**
     * Mendapatkan jumlah produk yang saat ini tersimpan (bukan kapasitas maksimum array).
     * @return Jumlah produk (int).
     */
    public int getJumlahProduk() {
        return jumlahProduk;
    }

    /**
     * Mendapatkan objek produk pada indeks (posisi) tertentu.
     * Metode ini sangat penting karena dipanggil oleh ProdukTabelModel untuk mengisi JTable.
     * @param index Posisi produk yang dicari di array.
     * @return Objek produk, atau null jika indeks tidak valid.
     */
    public AbstractProduk getProduk(int index){
        // Pemeriksaan batas dasar agar tidak error (ArrayIndexOutOfBoundsException)
        if (index >= 0 && index < jumlahProduk) {
            return AbstractProduk[index];
        }
        return null;
    }

    /**
     * Override dari method bawaan Java. Digunakan untuk mencetak seluruh isi gudang (list produk)
     * ke dalam format String yang mudah dibaca.
     * @return String daftar semua produk di kategori ini.
     */
    @Override
    public String toString(){
        String str = "Daftar Produk Kategori "+ namaKategori + " :\n";
        for(int i = 0; i < jumlahProduk; i++){
            AbstractProduk temp = AbstractProduk[i];
            str += (i+1) + ". " + temp.getNama_produk() + " - ID: " + temp.getId_produk() + " - Harga: " + temp.getHarga_produk() + "\n";
        }
        return str;
    }
}

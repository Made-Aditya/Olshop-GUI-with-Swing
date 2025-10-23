package Classes.elements;

import Classes.kelas.keranjang;
import Classes.kelas.AbstractProduk;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

// Kelas ini adalah Model data untuk JTable di bagian Keranjang.
// Berbeda dari ProdukTabelModel, kelas ini punya kolom tambahan: "Pilih" (Checkbox) dan "Aksi" (Tombol).
public class KeranjangTabelModel extends AbstractTableModel {

    // Objek keranjang yang berisi data produk dan saldo.
    private final keranjang keranjangData;

    // Nama-nama kolom. Perhatikan ada 5 kolom, termasuk "Pilih" dan "Aksi".
    private final String[] columnNames = {"Pilih", "ID", "Nama Produk", "Harga", "Aksi"};

    // List terpisah untuk menyimpan status centang (True/False) untuk setiap baris produk.
    private final List<Boolean> checkedStatus;

    /**
     * Konstruktor untuk model tabel keranjang.
     * @param keranjangData Objek keranjang yang datanya akan ditampilkan.
     */
    public KeranjangTabelModel(keranjang keranjangData) {
        this.keranjangData = keranjangData;
        // Inisialisasi status centang (default: false untuk semua)
        this.checkedStatus = new ArrayList<>();
        // Kita buat status centang awal sebanyak jumlah produk yang ada di keranjang.
        for (int i = 0; i < keranjangData.getJumlahProduk_keranjang(); i++) {
            checkedStatus.add(false);
        }
    }

    /**
     * Metode untuk memperbarui model setelah produk ditambahkan atau dihapus dari keranjang.
     * Ini PENTING untuk menjaga agar ukuran 'checkedStatus' sinkron dengan jumlah produk sebenarnya.
     */
    public void updateModel() {
        // --- Sinkronisasi Ukuran ---
        int currentSize = keranjangData.getJumlahProduk_keranjang();

        // Jika ada produk baru yang ditambahkan, kita tambahkan status centang (default false)
        while (checkedStatus.size() < currentSize) {
            checkedStatus.add(false);
        }

        // Jika produk dihapus, kita hapus status centang terakhir
        while (checkedStatus.size() > currentSize) {
            checkedStatus.remove(checkedStatus.size() - 1);
        }

        // Memberi tahu JTable bahwa data seluruh tabel sudah berubah
        fireTableDataChanged();
    }

    // --- Metode Wajib AbstractTableModel ---

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        // Jumlah baris adalah jumlah produk di keranjang.
        return keranjangData != null ? keranjangData.getJumlahProduk_keranjang() : 0;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Mengembalikan data untuk sel tertentu.
     */
    @Override
    public Object getValueAt(int row, int col) {
        // Mengakses produk dari keranjang berdasarkan indeks baris
        AbstractProduk p = keranjangData.getProduk_keranjang()[row];

        if (p == null) return null;

        switch (col) {
            case 0:
                // Kolom 0: Kotak centang. Mengambil status dari List checkedStatus.
                return checkedStatus.get(row);
            case 1:
                return p.getId_produk();
            case 2:
                return p.getNama_produk();
            case 3:
                return p.getHarga_produk();
            case 4:
                // Kolom 4: Teks untuk tombol "Aksi" (nantinya akan dirender sebagai tombol).
                return "Hapus";
            default:
                return null;
        }
    }

    /**
     * Memberi tahu JTable Tipe Data untuk setiap kolom.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class; // PENTING: Untuk menampilkan Checkbox
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return Long.class;
            case 4:
                // Tombol dirender dari teks String
                return String.class;
            default:
                return Object.class;
        }
    }

    /**
     * Memberi tahu JTable apakah sel pada baris dan kolom tertentu bisa diedit.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        // Hanya kolom "Pilih" (0) dan kolom "Aksi" (4) yang bisa diedit/diinteraksi.
        return col == 0 || col == 4;
    }

    /**
     * Dipanggil ketika pengguna mengubah nilai sel (misalnya, mencentang Checkbox).
     */
    @Override
    public void setValueAt(Object aValue, int row, int col) {
        if (col == 0) {
            // Jika kolom 0 yang diedit (Checkbox)
            checkedStatus.set(row, (Boolean) aValue); // Perbarui status centang di List terpisah
            // Memberi tahu JTable bahwa sel tersebut sudah diperbarui
            fireTableCellUpdated(row, col);
        }
        // Catatan: Kolom 4 (Aksi/Tombol) tidak memerlukan setValueAt karena aksinya ditangani oleh ButtonEditor.
    }

    // --- Metode Kustom ---

    /**
     * Metode bantu untuk mendapatkan daftar ID produk yang status centangnya TRUE.
     * Metode ini dipanggil oleh Main.java saat tombol "Beli" atau "Hapus" ditekan.
     * @return List ID produk (Integer) yang dicentang.
     */
    public List<Integer> getCheckedProductIds() {
        List<Integer> selectedIds = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            // Cek apakah status centang baris ke-i adalah true
            if (checkedStatus.get(i)) {
                // Ambil ID produk dari baris yang dicentang (Kolom 1)
                selectedIds.add((Integer) getValueAt(i, 1));
            }
        }
        return selectedIds;
    }
}

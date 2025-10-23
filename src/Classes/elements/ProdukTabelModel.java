package Classes.elements;

import Classes.kelas.listProduk;
import Classes.kelas.AbstractProduk;

import javax.swing.table.AbstractTableModel;

// Kelas ini adalah "Jembatan" atau "Penerjemah" antara data kita (listProduk)
// dan komponen tabel di antarmuka grafis (JTable).
// Kita harus extend (mewarisi) AbstractTableModel agar Java tahu ini adalah model tabel.
public class ProdukTabelModel extends AbstractTableModel {

    // Variabel yang menyimpan data produk yang akan ditampilkan.
    private final listProduk dataProduk;

    // Array String yang berisi nama-nama kolom tabel (Header).
    private final String[] columnNames = {"ID", "Nama Produk", "Harga", "Detail Spesifik"};

    /**
     * Konstruktor untuk model tabel.
     * @param dataProduk Objek listProduk yang berisi semua data yang akan ditampilkan.
     */
    public ProdukTabelModel(listProduk dataProduk) {
        this.dataProduk = dataProduk;
    }

    // --- Metode Wajib AbstractTableModel ---

    /**
     * Metode wajib #1: Memberi tahu JTable ada berapa banyak kolom.
     * @return Jumlah kolom (berdasarkan array columnNames).
     */
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }

    /**
     * Metode wajib #2: Memberi tahu JTable ada berapa banyak baris.
     * @return Jumlah produk yang ada di listProduk.
     */
    @Override
    public int getRowCount(){
        // Kita panggil method getJumlahProduk() dari objek dataProduk
        return dataProduk != null ? dataProduk.getJumlahProduk() : 0;
    }

    /**
     * Metode wajib #3: Memberi tahu JTable apa nama untuk setiap kolom (Header).
     * @param col Indeks kolom (0, 1, 2, dst).
     * @return Nama kolom yang sesuai.
     */
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }

    /**
     * Metode wajib #4: Ini metode paling penting!
     * Memberi tahu JTable data apa yang harus ditampilkan di SEL tertentu (baris dan kolom).
     * @param row Indeks baris yang diminta.
     * @param col Indeks kolom yang diminta.
     * @return Data (Object) untuk sel tersebut.
     */
    @Override
    public Object getValueAt(int row, int col){
        // Langkah 1: Ambil objek produk di baris (row) yang diminta.
        AbstractProduk p = dataProduk.getProduk(row);

        if (p == null) return null;

        // Langkah 2: Tentukan data mana yang harus dikembalikan berdasarkan kolom (col).
        switch(col){
            case 0:
                return p.getId_produk();
            case 1:
                return p.getNama_produk();
            case 2:
                return p.getHarga_produk();
            case 3:
                // BARIS BARU: Kolom 3 (Detail Spesifik)
                // Panggil method abstrak yang sudah diimplementasikan oleh subclass
                return p.getDeskripsiSingkat();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex) {
            case 0:
                return Integer.class; // ID
            case 1:
                return String.class;  // Nama Produk
            case 2:
                return Long.class;    // Harga
            case 3:
                // BARIS BARU: Detail Spesifik adalah String
                return String.class;  // Detail Spesifik adalah String
            default:
                return Object.class;
        }
    }
}

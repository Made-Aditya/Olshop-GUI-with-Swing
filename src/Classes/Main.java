package Classes;

// Import baru untuk kelas yang dipindahkan
import Classes.elements.ProdukTabelModel;
import Classes.kelas.listProduk;
import Classes.kelas.keranjang;
import Classes.elements.KeranjangTabelModel;
import Classes.proses.CartActionController;
import Classes.proses.TableConfigurator;
import Classes.proses.ProductDataFactory; // Import ProductDataFactory

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {
    // --- VARIABEL UNTUK KOMPONEN TAMPILAN (GUI) ---
    private JLabel jsWelcome;
    private JTextField textField1;
    private JButton submitButton;
    private JComboBox<String> categorySelector;
    private JPanel MainPanel;
    private JTable listProdukTable;
    private JTable tableKeranjang;
    private JButton buttonAddProduct;
    private JButton buttonBuyChecked;
    private JButton buttonDeleteChecked;
    private JLabel labelTotalHarga;
    private JLabel labelSaldo;

    // --- VARIABEL UNTUK DATA APLIKASI ---
    private final Map<String, listProduk> categories = new HashMap<>();
    private final keranjang userKeranjang;
    private final KeranjangTabelModel keranjangTableModel;

    // --- INSTANCE CONTROLLER BARU ---
    private final CartActionController cartActionController;
    private final ProductDataFactory dataFactory = new ProductDataFactory();
    private final TableConfigurator tableConfigurator = new TableConfigurator();

    public Main() {
        // 1. Inisialisasi Data Keranjang dan Model
        userKeranjang = new keranjang(50000000);
        keranjangTableModel = new KeranjangTabelModel(userKeranjang);

        // 2. Inisialisasi Controller Aksi dan berikan Dependency yang dibutuhkan
        cartActionController = new CartActionController(this, userKeranjang, keranjangTableModel);

        // Pasang model ke tabel keranjang (komponen GUI sudah diinisialisasi oleh Designer)
        if(tableKeranjang != null){
            tableKeranjang.setModel(keranjangTableModel);
        }

        // 3. Setup Tampilan Jendela Utama (JFrame)
        setContentPane(MainPanel);
        setTitle("Aplikasi Manajemen Online Shop Sederhana");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);

        // 4. Setup Data Produk
        // Panggil dari DataFactory
        listProduk Elektronik = dataFactory.createElektronikData();
        listProduk Buku = dataFactory.createBukuData();

        categories.put(Elektronik.getNamaKategori(), Elektronik);
        categories.put(Buku.getNamaKategori(), Buku);

        categorySelector.addItem(Elektronik.getNamaKategori());
        categorySelector.addItem(Buku.getNamaKategori());

        // 5. Memasang Event Listener
        categorySelector.addActionListener(e -> {
            String selectedCategory = (String) categorySelector.getSelectedItem();
            if (selectedCategory != null) {
                switchCategory(categories.get(selectedCategory));
            }
        });

        // Hubungkan Tombol ke Controller Aksi
        buttonAddProduct.addActionListener(e -> cartActionController.addProductToKeranjang(listProdukTable));
        buttonBuyChecked.addActionListener(e -> cartActionController.buyCheckedProducts());
        buttonDeleteChecked.addActionListener(e -> cartActionController.deleteCheckedProducts());

        // 6. Tampilan Awal
        switchCategory(Elektronik);
        // Panggil dari TableConfigurator
        tableConfigurator.setupKeranjangTableRenderers(tableKeranjang, cartActionController.createDeleteActionListener());
        tableConfigurator.setupKeranjangTableColumnWidths(tableKeranjang);
        updateKeranjangView();

        setVisible(true);
    }

    // --- METHOD PUBLIC/GETTER YANG DIBUTUHKAN OLEH CONTROLLER LAIN ---

    /** Mengembalikan referensi ke listProdukTable untuk diakses CartActionController. */
    public JTable getListProdukTable() {
        return listProdukTable;
    }

    /** Mengembalikan referensi ke tableKeranjang untuk diakses TableConfigurator. */
    public JTable getTableKeranjang() {
        return tableKeranjang;
    }

    // --- METODE UTILITY LEVEL TINGGI (TETAP DI MAIN) ---

    /**
     * Fungsi ajaib untuk REFRESH SEMUA TAMPILAN yang berhubungan dengan keranjang.
     */
    public void updateKeranjangView() {
        keranjangTableModel.updateModel();

        if(labelTotalHarga != null){
            labelTotalHarga.setText("Total Harga: " + userKeranjang.getTotalHarga());
        }
        if(labelSaldo != null){
            labelSaldo.setText("Saldo: " + userKeranjang.getSaldo());
        }
    }

    /**
     * Mengganti isi listProdukTable berdasarkan kategori yang dipilih dari dropdown.
     */
    public void switchCategory(listProduk selectedData) {
        ProdukTabelModel newModel = new ProdukTabelModel(selectedData);
        listProdukTable.setModel(newModel);

        jsWelcome.setText("Daftar Produk Kategori: " + selectedData.getNamaKategori().toUpperCase());

        // Panggil dari TableConfigurator
        tableConfigurator.setupTableColumnWidths(listProdukTable);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Biarkan default
        }
        SwingUtilities.invokeLater(Main::new);
    }
}
// Classes.proses.CartActionController.java
package Classes.proses;

import Classes.Main;
import Classes.elements.ButtonEditor;
import Classes.kelas.keranjang;
import Classes.elements.KeranjangTabelModel;
import Classes.kelas.produk;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;


public class CartActionController {
    // Referensi Dependency Injection (DI)
    private final Main mainFrame;
    private final keranjang userKeranjang;
    private final KeranjangTabelModel keranjangTableModel;

    public CartActionController(Main mainFrame, keranjang keranjang, KeranjangTabelModel model) {
        this.mainFrame = mainFrame;
        this.userKeranjang = keranjang;
        this.keranjangTableModel = model;
    }

    /**
     * Menangani klik tombol "Tambah ke Keranjang".
     */
    public void addProductToKeranjang(JTable listProdukTable) {
        int selectedRow = listProdukTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Pilih baris produk...", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = listProdukTable.convertRowIndexToModel(selectedRow);

        Integer id_produk = (Integer) listProdukTable.getModel().getValueAt(modelRow, 0);
        String nama_produk = (String) listProdukTable.getModel().getValueAt(modelRow, 1);
        Long harga_produk = (Long) listProdukTable.getModel().getValueAt(modelRow, 2);

        produk p = new produk(nama_produk, id_produk, harga_produk.intValue());

        if (userKeranjang.tambahproduk_keranjang(p)) {
            JOptionPane.showMessageDialog(mainFrame, nama_produk + " berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.updateKeranjangView(); // Panggil melalui referensi Main
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Keranjang penuh atau gagal.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menangani klik tombol "Beli Item yang Dipilih".
     */
    public void buyCheckedProducts() {
        List<Integer> checkedIds = keranjangTableModel.getCheckedProductIds();
        if (checkedIds.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Tidak ada item yang dipilih untuk dibeli.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Langkah 1: Hitung Total Harga yang DICENTANG ---
        long totalCheckedPrice = 0;
        for (int id : checkedIds) {
            for (int i = 0; i < userKeranjang.getJumlahProduk_keranjang(); i++) {
                produk p = userKeranjang.getProduk_keranjang()[i];
                if (p != null && p.getId_produk() == id) {
                    totalCheckedPrice += p.getHarga_produk();
                    break;
                }
            }
        }

        if (userKeranjang.getSaldo() < totalCheckedPrice) {
            JOptionPane.showMessageDialog(mainFrame, "Saldo tidak cukup (Total: " + totalCheckedPrice + ").", "Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Langkah 2 & 3: Kurangi Saldo dan Hapus Produk ---
        int newSaldo = (int) (userKeranjang.getSaldo() - totalCheckedPrice);
        int purchasedCount = 0;

        for (int id : checkedIds) {
            if (userKeranjang.hapusproduk_keranjang(id)) {
                purchasedCount++;
            }
        }

        // --- Langkah 4: Update Saldo Akhir dan Tampilan ---
        if (purchasedCount > 0) {
            userKeranjang.setSaldo(newSaldo);

            JOptionPane.showMessageDialog(mainFrame,
                    purchasedCount + " item berhasil dibeli. Saldo Anda sekarang: " + newSaldo,
                    "Sukses Pembelian",
                    JOptionPane.INFORMATION_MESSAGE);

            mainFrame.updateKeranjangView();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Gagal memproses pembelian.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menangani klik tombol "Hapus Item yang Dipilih".
     */
    public void deleteCheckedProducts() {
        List<Integer> checkedIds = keranjangTableModel.getCheckedProductIds();

        if (checkedIds.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Tidak ada item yang dipilih untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int deletedCount = 0;
        for (int id : checkedIds) {
            if (userKeranjang.hapusproduk_keranjang(id)) {
                deletedCount++;
            }
        }

        if (deletedCount > 0) {
            JOptionPane.showMessageDialog(mainFrame, deletedCount + " item berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.updateKeranjangView();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Gagal menghapus item.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Membuat ActionListener khusus untuk tombol "Hapus" yang ada di dalam baris tabel keranjang.
     */
    public ActionListener createDeleteActionListener() {
        return e -> {
            // Kita harus mengambil referensi tabel dari MainFrame
            JTable tableKeranjang = mainFrame.getTableKeranjang();

            ButtonEditor editor = (ButtonEditor) tableKeranjang.getCellEditor();
            int modelRow = editor.row;

            tableKeranjang.getCellEditor().stopCellEditing();

            Integer id_produk = (Integer) keranjangTableModel.getValueAt(modelRow, 1);
            String nama_produk = (String) keranjangTableModel.getValueAt(modelRow, 2);

            if (userKeranjang.hapusproduk_keranjang(id_produk)) {
                JOptionPane.showMessageDialog(mainFrame, nama_produk + " berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.updateKeranjangView();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal menghapus " + nama_produk + ".", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
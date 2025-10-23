package Classes.elements;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// Kelas ini adalah "Pelukis Tombol" (Renderer).
// Tugas utamanya hanya SATU: memastikan teks "Hapus" di dalam KeranjangTabelModel
// terlihat seperti tombol sungguhan di JTable.
public class ButtonRenderer extends JButton implements TableCellRenderer {

    /**
     * Konstruktor ButtonRenderer.
     * Kita jadikan objek ini (yang merupakan JButton) tidak transparan.
     */
    public ButtonRenderer() {
        setOpaque(true);
    }

    /**
     * Metode Wajib: Dipanggil oleh JTable setiap kali sel kolom "Aksi" perlu digambar.
     * Metode ini mengambil tombol ini sendiri (this) dan mengatur tampilannya
     * berdasarkan data sel (value) dan status tabel (isSelected, hasFocus).
     * * @param table JTable tempat tombol ini digambar.
     * @param value Nilai data dari sel (di sini harusnya String "Hapus").
     * @param isSelected Status apakah baris ini sedang dipilih.
     * @param hasFocus Status apakah sel ini sedang memiliki fokus.
     * @param row Indeks baris saat ini.
     * @param column Indeks kolom saat ini.
     * @return Komponen (tombol ini sendiri) dengan pengaturan tampilan yang baru.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // --- 1. Pengaturan Warna Berdasarkan Status Pilihan (Selected) ---
        if (isSelected) {
            // Kalau barisnya dipilih, pakai warna pilihan (misal: biru)
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            // Kalau tidak dipilih, kembalikan ke warna normal tabel dan warna latar tombol default Java.
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        // --- 2. Pengaturan Teks ---
        // Atur teks tombol (harus "Hapus" dari model, atau String kosong jika null)
        setText((value == null) ? "" : value.toString());

        // --- 3. Mengembalikan Tombol ---
        // Kembalikan objek JButton ini, yang sekarang sudah di-set warnanya dan teksnya.
        return this;
    }
}

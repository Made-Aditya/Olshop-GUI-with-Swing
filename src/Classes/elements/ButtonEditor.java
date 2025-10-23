package Classes.elements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Kelas ini adalah "Pengelola Aksi Tombol" (Editor).
// Tugas utamanya adalah menggantikan tampilan Renderer dengan JButton sungguhan
// TEPAT saat sel tersebut dipilih/diklik, agar aksi klik bisa diproses.
// Kita mewarisi DefaultCellEditor yang sudah bawaan dari Java Swing.
public class ButtonEditor extends DefaultCellEditor {

    // Tombol yang akan benar-benar menangani klik.
    protected JButton button;
    // Label teks pada tombol (misalnya, "Hapus").
    private String label;
    // Bendera (flag) untuk melacak apakah tombol sedang ditekan.
    private boolean isPushed;

    // Variabel ini kita butuhkan untuk mengetahui di baris mana tombol ini diklik.
    private JTable table;
    public int row;
    public int column;

    /**
     * Konstruktor ButtonEditor.
     * @param checkBox Komponen default yang harus dilewatkan ke superclass (tidak digunakan, tapi wajib ada).
     * @param clickAction ActionListener yang berisi logika yang akan dieksekusi saat tombol diklik (misalnya, logika hapus di Main.java).
     */
    public ButtonEditor(JCheckBox checkBox, ActionListener clickAction) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);

        // --- Bagian KRITIS ---
        // Kita tambahkan ActionListener yang datang dari Main.java ke tombol ini.
        // Ketika tombol di editor diklik, logika hapus di Main akan terpanggil.
        button.addActionListener(clickAction);
    }

    /**
     * Dipanggil oleh JTable ketika sel mulai diedit (yaitu, diklik).
     * Saat ini terjadi, JTable mengganti tampilan Renderer dengan Editor (yaitu tombol sungguhan).
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        // Simpan referensi baris dan kolom yang sedang diedit/diklik. PENTING untuk logika aksi di Main.
        this.table = table;
        this.row = row;
        this.column = column;

        // Atur warna tombol editor agar sesuai dengan status pilihan tabel.
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }

        // Atur teks tombol (misalnya "Hapus")
        label = (value == null) ? "" : value.toString();
        button.setText(label);

        // Set bendera isPushed menjadi true
        isPushed = true;

        // Kembalikan tombol ini sebagai komponen Editor yang siap diklik.
        return button;
    }

    /**
     * Dipanggil oleh JTable ketika proses editing selesai (setelah tombol diklik).
     */
    @Override
    public Object getCellEditorValue() {
        // Jika tombol sempat ditekan sebelum editing berhenti:
        if (isPushed) {
            // Catatan: Logika Hapus sudah dieksekusi oleh ActionListener yang kita pasang di konstruktor.
            // Di sini, kita hanya perlu mengembalikan nilai sel yang sudah "diedit" (label tombol itu sendiri).
        }
        // Set bendera kembali ke false
        isPushed = false;
        return label;
    }

    /**
     * Dipanggil untuk menghentikan proses editing sel.
     */
    @Override
    public boolean stopCellEditing() {
        // Pastikan bendera isPushed direset.
        isPushed = false;
        // Panggil superclass untuk menyelesaikan proses editing.
        return super.stopCellEditing();
    }

    /**
     * Metode standar untuk memberitahu listener bahwa editing sudah selesai.
     */
    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

// Classes.proses.TableConfigurator.java
package Classes.proses;

import Classes.elements.ButtonEditor;
import Classes.elements.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener; // Wajib import ActionListener

public class TableConfigurator {
    /**
     * Mengkonfigurasi ButtonRenderer dan ButtonEditor untuk kolom "Aksi" di tabel Keranjang.
     * @param tableKeranjang Tabel keranjang yang akan dikonfigurasi.
     * @param deleteActionListener ActionListener yang akan dipasang pada tombol Hapus.
     */
    public void setupKeranjangTableRenderers(JTable tableKeranjang, ActionListener deleteActionListener) {
        // Pastikan kolom Aksi (index 4) ada
        if (tableKeranjang.getColumnCount() <= 4) {
            return;
        }

        TableColumn actionColumn = tableKeranjang.getColumnModel().getColumn(4);

        actionColumn.setCellRenderer(new ButtonRenderer());
        // Gunakan ActionListener yang DILEMPAR dari Controller
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox(), deleteActionListener));
    }

    /**
     * Mengatur lebar kolom agar tabel Keranjang terlihat rapi.
     */
    public void setupKeranjangTableColumnWidths(JTable table) {
        if (table.getColumnCount() == 0 || table.getColumnCount() <= 4){
            return; // Pencegahan error array index
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // Kolom Pilih (0) - Checkbox
        TableColumn selectColumn = table.getColumnModel().getColumn(0);
        selectColumn.setPreferredWidth(50);
        selectColumn.setMinWidth(50);
        selectColumn.setMaxWidth(50);

        // ... (Sisa logika pengaturan kolom)
        // ... (Logika pengaturan kolom lain dihilangkan untuk keringkasan, diasumsikan sama)

        // Kolom ID (1)
        TableColumn idColumn = table.getColumnModel().getColumn(1);
        idColumn.setPreferredWidth(50);
        idColumn.setMinWidth(50);
        idColumn.setMaxWidth(60);

        // Kolom Harga (3)
        TableColumn priceColumn = table.getColumnModel().getColumn(3);
        priceColumn.setPreferredWidth(100);
        priceColumn.setMinWidth(100);
        priceColumn.setMaxWidth(120);

        // Kolom Aksi (4) - Tombol Hapus
        TableColumn actionColumn = table.getColumnModel().getColumn(4);
        actionColumn.setPreferredWidth(80);
        actionColumn.setMinWidth(80);
        actionColumn.setMaxWidth(80);
    }

    /**
     * Metode untuk mengatur lebar kolom di listProdukTable.
     */
    public void setupTableColumnWidths(JTable table) {
        if (table.getColumnCount() <= 3){
            return;
        }
        // ... (Semua logika setupTableColumnWidths dipindahkan di sini)
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        final int idColumnIndex = 0;
        TableColumn idColumn = table.getColumnModel().getColumn(idColumnIndex);
        int max_width = 0;

        // ... (Logika pengukuran dan penetapan lebar kolom, dipindahkan sepenuhnya)
        TableCellRenderer headerRenderer = idColumn.getHeaderRenderer();
        if (headerRenderer == null) {
            headerRenderer = table.getTableHeader().getDefaultRenderer();
        }
        Component headerComp = headerRenderer.getTableCellRendererComponent(
                table, idColumn.getHeaderValue(), false, false, 0, idColumnIndex);
        max_width = headerComp.getPreferredSize().width;

        for (int i = 0; i < table.getRowCount(); i++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(i, idColumnIndex);
            Component cellComp = table.prepareRenderer(cellRenderer, i, idColumnIndex);
            int width = cellComp.getPreferredSize().width + table.getIntercellSpacing().width;
            max_width = Math.max(max_width, width);
        }

        final int PADDING = 10;
        int finalWidth = max_width + PADDING;

        idColumn.setPreferredWidth(finalWidth);
        idColumn.setMinWidth(finalWidth);
        idColumn.setMaxWidth(finalWidth + 20);

        TableColumn nameColumn = table.getColumnModel().getColumn(1);
        nameColumn.setPreferredWidth(300);

        TableColumn priceColumn = table.getColumnModel().getColumn(2);
        priceColumn.setPreferredWidth(100);

        TableColumn detailColumn = table.getColumnModel().getColumn(3);
        detailColumn.setPreferredWidth(250);
    }
}
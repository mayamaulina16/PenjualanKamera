package frame;

import helpers.Koneksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class PembelianViewFrame extends JFrame{
    private JPanel mainPanel;
    private JTextField cariTextField;
    private JButton cariButton;
    private JTable viewTable;
    private JButton tambahButton;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton cetakButton;
    private JButton tutupButton;

    public PembelianViewFrame() {
        tambahButton.addActionListener(e -> {
            PembelianInputFrame inputFrame = new PembelianInputFrame();
            inputFrame.setVisible(true);
        });
        ubahButton.addActionListener(e -> {
            int barisTerpilih = viewTable.getSelectedRow();
            if (barisTerpilih < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Pilih data terlebih dulu",
                        "Validasi Pilih Data",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            TableModel tm = viewTable.getModel();
            int id_barang = Integer.parseInt(tm.getValueAt(barisTerpilih, 0).toString());
            PembelianInputFrame inputFrame = new PembelianInputFrame();
            inputFrame.setId_barang(id_barang);
            inputFrame.isiKomponen();
            inputFrame.setVisible(true);
        });
        batalButton.addActionListener(e -> {
            isiTable();
        });
        hapusButton.addActionListener(e -> {
            int barisTerpilih = viewTable.getSelectedRow();
            if (barisTerpilih < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Pilih data terlebih dulu",
                        "Validasi Pilih Data",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int pilihan = JOptionPane.showConfirmDialog(
                    null,
                    "Yakin ingin dihapus?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION);

            if (pilihan == 0) { //pilihan YES
                TableModel tm = viewTable.getModel();
                int id_barang = Integer.parseInt(tm.getValueAt(barisTerpilih, 0).toString());

                Connection c = Koneksi.getConnection();
                String deleteSQL = "DELETE FROM pembelian WHERE id_barang = ?";

                try {
                    PreparedStatement ps = c.prepareStatement(deleteSQL);
                    ps.setInt(1, id_barang);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cariButton.addActionListener(e -> {
            if (cariTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Isi kata kunci pencarian",
                        "Validasi Kata Kunci Kosong",
                        JOptionPane.WARNING_MESSAGE);
                cariTextField.requestFocus();
                return;
            }
            String keyword = "%" + cariTextField.getText() + "%";
            String searchSQL = "SELECT * FROM pembelian WHERE id_barang like ?";
            Connection c = Koneksi.getConnection();
            try {
                PreparedStatement ps = c.prepareStatement(searchSQL);
                ps.setString(1, keyword);
                ResultSet rs = ps.executeQuery();

                String[] header = {"Id_Barang", "Nama Pembeli", "Alamat", "No. Telepon"};
                DefaultTableModel dtm = new DefaultTableModel(header, 0);
                viewTable.getColumnModel().getColumn(0).setWidth(100);
                viewTable.getColumnModel().getColumn(0).setMaxWidth(100);
                viewTable.getColumnModel().getColumn(0).setMinWidth(100);
                viewTable.getColumnModel().getColumn(0).setPreferredWidth(100);

                Object[] row = new Object[2];
                while (rs.next()) {
                    row[0] = rs.getInt("id_barang");
                    row[1] = rs.getString("nama_pembeli");
                    row[2] = rs.getString("alamat");
                    row[3] = rs.getString("no_telp");
                    dtm.addRow(row);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        tutupButton.addActionListener(e -> {
            dispose();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                isiTable();
            }
        });
        isiTable();
        init();
    }

    public void init() {
        setContentPane(mainPanel);
        setTitle("Data Pembelian");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiTable() {
        String selectSQL = "SELECT * FROM pembelian";
        Connection c = Koneksi.getConnection();

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);

            String[] header = {"Id Barang", "Nama Pembeli", "Alamat", "No. Telepon"};
            DefaultTableModel dtm = new DefaultTableModel(header, 0);
            viewTable.setModel(dtm);

            viewTable.getColumnModel().getColumn(0).setWidth(100);
            viewTable.getColumnModel().getColumn(0).setMaxWidth(100);
            viewTable.getColumnModel().getColumn(0).setMinWidth(100);
            viewTable.getColumnModel().getColumn(0).setPreferredWidth(100);

            Object[] row = new Object[2];
            while (rs.next()) {
                row[0] = rs.getInt("id_barang");
                row[1] = rs.getString("nama_pembeli");
                row[2] = rs.getString("alamat");
                row[3] = rs.getString("no_telp");
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


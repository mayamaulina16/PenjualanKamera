package frame;

import helpers.Koneksi;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarangInputFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField idTextField;
    private JTextField merkTextField;
    private JTextField tipeTextField;
    private JTextField beratTextField;
    private JTextField hargaTextField;
    private JTextField stokTextField;
    private JButton simpanButton;
    private JButton batalButton;

    int id_barang;

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public BarangInputFrame() {
        simpanButton.addActionListener(e -> {
            String merk = merkTextField.getText();
            if (merk.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Isi Merk Barang",
                        "Validasi Data Kosong",
                        JOptionPane.WARNING_MESSAGE);
                merkTextField.requestFocus();
                return;
            }
            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if (id_barang == 0) {
                    String cekSQL = "SELECT * FROM barang WHERE merk = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, merk);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        String insertSQL = "INSERT INTO barang SET merk = ?";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, merk);
                        ps.executeUpdate();
                        dispose();
                    }

                } else {
                    String cekSQL = "SELECT * FROM barang WHERE merk = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, merk);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        String updateSQL = "UPDATE barang SET merk = ? WHERE id_barang ?";
                        ps = c.prepareStatement(updateSQL);
                        ps.setString(1, merk);
                        ps.setInt(2, id_barang);
                        ps.executeUpdate();
                        dispose();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        batalButton.addActionListener(e -> {
            dispose();
        });
        init();
    }

    public void init() {
        setContentPane(mainPanel);
        setTitle("Input Barang");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiKomponen() {
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM barang WHERE id_barang = ?";
        PreparedStatement ps;

        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id_barang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id_barang")));
                merkTextField.setText(rs.getString("merk"));
                tipeTextField.setText(rs.getString("tipe"));
                beratTextField.setText(rs.getString("berat"));
                hargaTextField.setText(String.valueOf(rs.getInt("harga")));
                stokTextField.setText(String.valueOf(rs.getInt("stok")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

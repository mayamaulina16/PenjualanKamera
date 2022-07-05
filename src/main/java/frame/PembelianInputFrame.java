package frame;

import helpers.ComboBoxItem;
import helpers.Koneksi;

import javax.swing.*;
import java.sql.*;

public class PembelianInputFrame extends JFrame{
    private JPanel mainPanel;
    private JTextField idTextField;
    private JTextField pembeliTextField;
    private JTextField alamatTextField;
    private JTextField telpTextField;
    private JButton simpanButton;
    private JButton batalButton;
    private JComboBox merkComboBox;

    private int id_barang;

    public void setId_barang(int id_barang) { this.id_barang = id_barang; }

    public PembelianInputFrame() {
        simpanButton.addActionListener(e -> {
            String pembeli = pembeliTextField.getText();
            if(pembeli.equals("")){
                JOptionPane.showMessageDialog(null,
                        "Isi Nama Pembeli",
                        "Validasi Data Kosong",
                        JOptionPane.WARNING_MESSAGE);
                pembeliTextField.requestFocus();
                return;
            }

            ComboBoxItem item = (ComboBoxItem) merkComboBox.getSelectedItem();
            int merk = item.getValue();
            if (merk == 0){
                JOptionPane.showMessageDialog(null,
                        "Pilih Merk",
                        "Validasi Data Kosong",
                        JOptionPane.WARNING_MESSAGE);
                merkComboBox.requestFocus();
                return;
            }

            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if (id_barang == 0) {
                    String cekSQL = "SELECT * FROM pembelian WHERE nama_pembeli = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, pembeli);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        String insertSQL = "INSERT INTO pembelian SET nama_pembeli = ?, merk = ?";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, pembeli);
                        ps.setString(2, String.valueOf(merk));
                        ps.executeUpdate();
                        dispose();
                    }

                } else {
                    String cekSQL = "SELECT * FROM pembelian WHERE nama_pembeli = ? AND id_barang != ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, pembeli);
                    ps.setInt(2, id_barang);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    }else {
                    String updateSQL = "UPDATE pembelian SET nama_pembeli = ?, merk = ? WHERE id_barang ?";
                    ps = c.prepareStatement(updateSQL);
                    ps.setString(1, pembeli);
                    ps.setInt(2, merk);
                    ps.setInt(3, id_barang);
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
        kustomisasiKomponen();
        init();
    }

    public void init() {
        setContentPane(mainPanel);
        setTitle("Input Pembelian");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiKomponen(){
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM pembelian WHERE id_barang = ?";
        PreparedStatement ps;

        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id_barang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                idTextField.setText(String.valueOf(rs.getInt("id_barang")));
                pembeliTextField.setText(rs.getString("nama_pembeli"));
                alamatTextField.setText(rs.getString("alamat"));
                telpTextField.setText(rs.getString("no_telp"));
                String merk = rs.getString("merk");
                for (int i = 0; i < merkComboBox.getItemCount(); i++){
                    merkComboBox.setSelectedIndex(i);
                    ComboBoxItem item = (ComboBoxItem) merkComboBox.getSelectedItem();
                    if(merk == item.getText()){
                        break;
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void kustomisasiKomponen(){
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM barang ORDER BY merk";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            merkComboBox.addItem(new ComboBoxItem(0, "Pilih Merk"));
            while (rs.next()){
                merkComboBox.addItem(new ComboBoxItem(
                        rs.getInt("id"),
                        rs.getString("merk")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
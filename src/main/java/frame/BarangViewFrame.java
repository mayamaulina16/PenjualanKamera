package frame;

import javax.swing.*;

public class BarangViewFrame extends JFrame{
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
    private JPanel buttonPanel;
    private JScrollPane viewScrollPane;

    public BarangViewFrame(){
        init();
    }

    public void init() {
        setContentPane(mainPanel);
        pack();
        setTitle("Input Barang");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

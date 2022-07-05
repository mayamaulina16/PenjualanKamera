import frame.BarangViewFrame;
import frame.PembelianViewFrame;
import helpers.Koneksi;

public class Main {
    public static void main(String[] args) {
        Koneksi.getConnection();
        BarangViewFrame viewFrame = new BarangViewFrame();
        PembelianViewFrame viewFrame = new PembelianViewFrame();
        viewFrame.setVisible(true);
    }
}


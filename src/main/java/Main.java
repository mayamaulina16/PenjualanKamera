import frame.BarangViewFrame;
import helpers.Koneksi;

public class Main {
    public static void main(String[] args) {
        Koneksi.getConnection();
        BarangViewFrame viewFrame = new BarangViewFrame();
        viewFrame.setVisible(true);
    }
}

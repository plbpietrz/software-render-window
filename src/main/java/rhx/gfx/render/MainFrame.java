package rhx.gfx.render;

import javax.swing.*;
import java.io.IOException;

public class MainFrame {

    public static void main(String[] args) throws IOException {
        JFrame frame = buildFrame();

        frame.add(new DrawFramePanel(100, 100));
    }


    private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
        return frame;
    }


}
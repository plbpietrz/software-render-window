package rhx.gfx.render;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main frame class.
 */
public class MainFrame {

    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;
    public static final String SOFTWARE_RENDER_WINDOW = "SRW";

    public static void main(String[] args) throws IOException {
        final int width;
        final int height;
        if (args.length == 2) {
            width = Integer.parseInt(args[WIDTH]);
            height = Integer.parseInt(args[HEIGHT]);
        } else {
            width = 640;
            height = 480;
        }

        JFrame frame = buildFrame(width, height);
        frame.add(new DrawFramePanel(frame));
    }


    private static JFrame buildFrame(final int width, final int height) {
        JFrame frame = new JFrame();
        frame.setTitle(SOFTWARE_RENDER_WINDOW);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        frame.setVisible(true);
        return frame;
    }


}
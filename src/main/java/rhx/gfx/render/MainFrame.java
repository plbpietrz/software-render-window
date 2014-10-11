package rhx.gfx.render;

import rhx.gfx.render.tunnel.WaterRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main frame class.
 */
public class MainFrame {

    public static final int WIDTH_PARAM = 0;
    public static final int HEIGHT_PARAM = 1;
    public static final String SOFTWARE_RENDER_WINDOW = "SRW";

    public static void main(String[] args) throws IOException {
        final int width;
        final int height;
        if (args.length == 2) {
            width = Integer.parseInt(args[WIDTH_PARAM]);
            height = Integer.parseInt(args[HEIGHT_PARAM]);
        } else {
            width = 640;
            height = 480;
        }

        JFrame frame = buildFrame(width, height);
        DrawFramePanel panel = new DrawFramePanel(frame);
        frame.add(panel);
        new Thread(
                new RenderLoop()
                        .setDrawableSurface(panel)
                        .setRenderer(new WaterRenderer("tunnelarboreatex.png"))
                        .setDisplay(frame)
                        .setMaxFPS(10)
        ).start();
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
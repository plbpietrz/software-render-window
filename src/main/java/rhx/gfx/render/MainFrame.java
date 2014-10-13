package rhx.gfx.render;

import rhx.gfx.render.renderer.WaterDropMouseListener;
import rhx.gfx.render.renderer.WaterRenderer;

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

        final JFrame frame = buildFrame(width, height);
        DrawFramePanel panel = new DrawFramePanel(frame);
        frame.add(panel);
        final WaterRenderer renderer = new WaterRenderer("akira.jpg");
        panel.addMouseListener(new WaterDropMouseListener(renderer));
        resizeWindowToFitContent(frame);
        final RenderLoop renderLoop = new RenderLoop();
        new Thread(
            new RenderLoop()
                .setDrawableSurface(panel)
                .setRenderer(renderer)
                .setDisplay(frame)
                .setMaxFPS(60)
        ).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        frame.setTitle(SOFTWARE_RENDER_WINDOW + " FPS:" + renderLoop.getFrameCountAndReset());
                        Thread.sleep(TimeUnit.SECONDS.toMillis(1l));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void resizeWindowToFitContent(JFrame frame) {
        Dimension frameSize = frame.getSize();
        Dimension contentPaneSize = frame.getContentPane().getSize();
        frame.setSize(new Dimension(2 * frameSize.width - contentPaneSize.width, 2 * frameSize.height - contentPaneSize.height));
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
package rhx.gfx.render;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Simple instance of {@link javax.swing.JPanel} used to draw the image using a underlying {@link java.awt.image.BufferedImage}
 * that can be retrieved from the panel itself.
 * Created by pietrzyk on 7/15/2014.
 */
public class DrawFramePanel extends JPanel implements Drawable {

    public static final int RED         = 0xff0000;
    public static final int GREEN       = 0x00ff00;
    public static final int BLUE        = 0x0000ff;
    public static final int NO_ALPHA    = 0x000000;

    private final BufferedImage activeFrame;


    public DrawFramePanel(final Component parent) {
        this(parent.getWidth(), parent.getHeight());
    }

    public DrawFramePanel(final int width, final int height) {
        final int[] dstBuffer = new int[width * height];
        WritableRaster raster = Raster.createPackedRaster(
                new DataBufferInt(dstBuffer, width * height),   // data buffer
                width, height,                                  // dimensions
                width,                                          // scanline stride (image width times values per pixel)
                new int[]{RED, GREEN, BLUE}, null);             // band mask

        activeFrame = new BufferedImage(
                new DirectColorModel(32,
                        RED,        // Red
                        GREEN,      // Green
                        BLUE,       // Blue
                        NO_ALPHA    // Alpha
                 ),raster,false,null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(activeFrame, 0, 0, null);
    }

    @Override
    public Raster getDrawableRaster() {
        return activeFrame.getRaster();
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(activeFrame.getWidth(), activeFrame.getHeight());
    }
}

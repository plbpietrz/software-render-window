package rhx.gfx.render;

import rhx.gfx.render.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Simple instance of {@link javax.swing.JPanel} used to draw the image using a underlying {@link java.awt.image.BufferedImage}
 * that can be retrieved from the panel itself.
 * Created by pietrzyk on 7/15/2014.
 */
public class DrawFramePanel extends JPanel implements Drawable {

    private final BufferedImage activeFrame;

    public DrawFramePanel(final Component parent) {
        this(parent.getWidth(), parent.getHeight());
    }

    public DrawFramePanel(final int width, final int height) {
        activeFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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

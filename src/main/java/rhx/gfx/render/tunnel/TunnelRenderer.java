package rhx.gfx.render.tunnel;

import rhx.gfx.render.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.PixelGrabber;
import java.io.IOException;

/**
 * Created by rhinox on 2014-10-05.
 */
public class TunnelRenderer implements Renderer {

    private int scrWidth;
    private int scrHeight;

    private int distances[][];
    private int angles[][];
    private int texture[];
    private Component loopback;

    public TunnelRenderer(Component loopback) throws IOException {
        this.loopback = loopback;

        BufferedImage textureImage = ImageIO.read(getClass().getClassLoader().getResource("tunnelarboreatex.png"));

        scrWidth = textureImage.getWidth();
        scrHeight = textureImage.getHeight();
        texture = new int[scrWidth * scrHeight];

        try {
            new PixelGrabber(textureImage, 0, 0, scrWidth, scrHeight, texture, 0, scrWidth).grabPixels();
        } catch (InterruptedException e) {
            throw new IOException("Error reading texture file!", e);
        }

        distances = new int[scrWidth][scrHeight];
        angles = new int[scrWidth][scrHeight];

        for (int x = 0; x < scrWidth; x++) {
            for (int y = 0; y < scrHeight; y++) {
                distances[x][y] = (int) ((30.0 * scrHeight / Math.sqrt((x - scrWidth / 2.0) * (x - scrWidth / 2.0) + (y - scrHeight / 2.0) * (y - scrHeight / 2.0))) % scrHeight);
                angles[x][y] = (int) (0.5 * scrWidth * Math.atan2(y - scrHeight / 2.0, x - scrWidth / 2.0) / Math.PI);
            }
        }
    }

    static int shiftX = 0;
    static int shiftY = 0;

    static double movement = 0.1;
    static double animation = 0;

    @Override
    public Renderer drawOn(Drawable drawable) {
        DataBufferInt dataBuffer = (DataBufferInt) drawable.getDrawableRaster().getDataBuffer();
        int rasterWidth = drawable.getDimension().width;
        int[] offScreenRaster = dataBuffer.getData();
        animation += 3;
        movement  += 1;

        shiftX = (int) (scrWidth + animation);
        shiftY = (int) (scrHeight + movement);

        for (int y = 0, cursor = 0; y < scrHeight; y++) {
            for (int x = 0; x < scrWidth; x++, cursor++) {
                offScreenRaster[x + y * rasterWidth] = texture[(distances[x][y] + shiftX) % scrWidth + (((angles[x][y] + shiftY) % scrHeight) * scrWidth)];
            }
        }
        loopback.repaint();
        return this;
    }
}

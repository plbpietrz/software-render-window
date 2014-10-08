package rhx.gfx.render.tunnel;

import rhx.gfx.render.Drawable;
import rhx.gfx.render.IntDim;

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
    private final int texHeight;
    private final int texWidth;

    private int scrWidth;
    private int scrHeight;

    private int distances[][];
    private int angles[][];
    private int texture[];
    private Component loopback;


    public TunnelRenderer(Component loopback) throws IOException {
        this.loopback = loopback;

        BufferedImage textureImage = ImageIO.read(getClass().getClassLoader().getResource("tunnelarboreatex.png"));

        texWidth = textureImage.getWidth();
        texHeight = textureImage.getHeight();
        texture = new int[texHeight * texWidth];

        try {
            new PixelGrabber(textureImage, 0, 0, texWidth, texHeight, texture, 0, texWidth).grabPixels();
        } catch (InterruptedException e) {
            throw new IOException("Error reading texture file!", e);
        }
    }

    private int shiftX, shiftY, shiftLookX, shiftLookY;

    static double movement = 0.1;
    static double animation = 0;

    @Override
    public Renderer init(Drawable drawable) {
        IntDim dimension = drawable.getDimension();
        scrWidth  = dimension.width;
        scrHeight = dimension.height;

        distances = new int[scrWidth * 2][scrHeight * 2];
        angles    = new int[scrWidth * 2][scrHeight * 2];

        for (int x = 0; x < scrWidth * 2; x++) {
            for (int y = 0; y < scrHeight * 2; y++) {
                distances[x][y] = (int) ((30.0 * texHeight / Math.sqrt((x - scrWidth/2) * (x - scrWidth/2) + (y - scrHeight/2) * (y - scrHeight/2))) % texHeight);
                   angles[x][y] = (int) (0.5 * texWidth * Math.atan2(y - scrHeight/2, x - scrWidth/2) / Math.PI);
            }
        }
        return this;
    }

    @Override
    public Renderer drawOn(Drawable drawable) {
        IntDim dimension = drawable.getDimension();
        DataBufferInt dataBuffer = (DataBufferInt) drawable.getDrawableRaster().getDataBuffer();
        int rasterWidth = drawable.getDimension().width;
        int[] offScreenRaster = dataBuffer.getData();
        animation += 3;
        movement  += 1;

        shiftX = (int) (texWidth * 1.0 * animation);
        shiftY = (int) (texHeight * 0.25 * animation);

        shiftLookX = scrWidth / 2 + (int)(scrWidth / 2 * Math.sin(animation));
        shiftLookY = scrHeight / 2 + (int)(scrHeight / 2 * Math.sin(animation * 2.0));

        for (int y = 0, cursor = 0; y < scrHeight; y++) {
            for (int x = 0; x < scrWidth; x++, cursor++) {
                offScreenRaster[x + y * rasterWidth] =
                        texture[(distances[x + shiftLookX][y + shiftLookY] + shiftX) % texWidth +
                                 (((angles[x + shiftLookX][y + shiftLookY] + shiftY) % texHeight) * scrWidth)];
            }
        }
        loopback.repaint();
        return this;
    }
}

package rhx.gfx.render.tunnel;

import rhx.gfx.render.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.PixelGrabber;
import java.io.IOException;

/**
 * Water effect renderer.
 * Created by rhinox on 2014-10-10.
 */
public class WaterRenderer implements Renderer {
    private final int texHeight;
    private final int texWidth;

    private int scrWidth;
    private int scrHeight;

    private int texture[];

    public WaterRenderer() throws IOException {
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

    @Override
    public Renderer init(Drawable drawable) {
        Dimension dimension = drawable.getDimension();
        scrWidth = dimension.width;
        scrHeight = dimension.height;
        return this;
    }

    @Override
    public Renderer drawOn(Drawable drawable) {
        DataBufferInt dataBuffer = (DataBufferInt) drawable.getDrawableRaster().getDataBuffer();
        int[] offScreenRaster = dataBuffer.getData();
        for (int y = 0; y < scrHeight; y++) {
            for (int x = 0; x < scrWidth; x++) {
                    offScreenRaster[x + y * scrWidth] = texture[x * texWidth / scrWidth + (y * texHeight / scrHeight) * texWidth];
            }
        }
        return this;
    }
}

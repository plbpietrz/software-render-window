package rhx.gfx.render.tunnel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;

/**
 * Image file based {@link rhx.gfx.render.tunnel.Renderer}. Reads image file that has to be available on the classpath.
 * Created by rhinox on 2014-10-11.
 */
public abstract class ImageRenderer implements Renderer {

    protected final int texWidth;
    protected final int texHeight;
    protected final int[] texture;

    public ImageRenderer(String imageFileName) throws IOException {
        BufferedImage textureImage = ImageIO.read(getClass().getClassLoader().getResource(imageFileName));

        texWidth = textureImage.getWidth();
        texHeight = textureImage.getHeight();
        texture = new int[texHeight * texWidth];

        try {
            new PixelGrabber(textureImage, 0, 0, texWidth, texHeight, texture, 0, texWidth).grabPixels();
        } catch (InterruptedException e) {
            throw new IOException("Error reading texture file!", e);
        }
    }
}

package rhx.gfx.render.tunnel;

import rhx.gfx.render.Drawable;

import java.awt.*;
import java.awt.image.DataBufferInt;
import java.io.IOException;

/**
 * Water effect renderer.
 * Created by rhinox on 2014-10-10.
 */
public class WaterRenderer extends ImageRenderer {

    private int scrWidth;
    private int scrHeight;

    public WaterRenderer(String imageFileName) throws IOException{
        super(imageFileName);
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

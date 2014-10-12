package rhx.gfx.render.renderer;

import rhx.gfx.render.Drawable;

import java.awt.*;
import java.awt.image.DataBufferInt;
import java.io.IOException;

/**
 * Water effect renderer.
 * Created by rhinox on 2014-10-10.
 */
public class WaterRenderer extends ImageRenderer {

    private static final double WATER_RINDEX = 2.0d;
    private static final int DAMP = 3;

    private int scrWidth;
    private int scrHeight;

    private int[] waveMap;
    private int[] outBuffer;
    private int[] waveMapLast;

    public WaterRenderer(String imageFileName) throws IOException{
        super(imageFileName);
        waveMap = new int[texture.length];
        waveMapLast = new int[texture.length];
        outBuffer = new int[texture.length];
    }

    @Override
    public Renderer init(Drawable drawable) {
        Dimension dimension = drawable.getDimension();
        scrWidth = dimension.width;
        scrHeight = dimension.height;

        waveMap[127 + 126 * texWidth] = 400;
        return this;
    }

    @Override
    public Renderer drawOn(Drawable drawable) {
        updateWaveMap();
        updateOutputBuffer();
        updateScreen(drawable);
        return this;
    }

    private void updateWaveMap() {
        int n;
        for (int y = 1; y < texHeight - 1; ++y) {
            for (int x = 1; x < texWidth - 1; ++x) {
                n = (
                        waveMap[x + 1 + y * texWidth] +
                        waveMap[x - 1 + y * texWidth] +
                        waveMap[x + (y + 1) * texWidth] +
                        waveMap[x + (y - 1) * texWidth]
                ) / 2 - waveMapLast[x + y * texWidth];
                n = n - n / DAMP;
                waveMapLast[x + y * texWidth] = n;
            }
        }
        int [] waveMapTmp = waveMap;
        waveMap = waveMapLast;
        waveMapLast = waveMapTmp;
    }

    private void updateOutputBuffer() {
        int xDiff, yDiff, xDisplace, yDisplace, pixel;
        double xAngle, xRefraction, yAngle, yRefraction;
        for (int y = 1; y < texHeight - 1; ++y) {
            for (int x = 1; x < texWidth - 1; ++x) {
                xDiff = waveMap[x+1 + y * texWidth] - waveMap[x + y * texWidth];
                yDiff = waveMap[x + (y + 1) * texWidth] - waveMap[x + y * texWidth];

                xAngle = Math.atan(xDiff);
                xRefraction = Math.asin(Math.sin(xAngle) / WATER_RINDEX);
                xDisplace = (int) (Math.tan(xRefraction) * xDiff);

                yAngle = Math.atan(yDiff);
                yRefraction = Math.asin(Math.sin(yAngle) / WATER_RINDEX);
                yDisplace = (int) (Math.tan(yRefraction) * yDiff);

                if (xDiff < 0)
                    if (yDiff < 0) pixel = texture[x - xDisplace + (y - yDisplace) * texWidth];
                    else pixel = texture[x - xDisplace + (y + yDisplace) * texWidth];
                else
                    if (yDiff < 0) pixel = texture[x + xDisplace + (y - yDisplace) * texWidth];
                    else pixel = texture[x + xDisplace + (y + yDisplace) * texWidth];
                outBuffer[x + y * texWidth] = pixel;
            }
        }
    }

    private void updateScreen(Drawable drawable) {
        DataBufferInt dataBuffer = (DataBufferInt) drawable.getDrawableRaster().getDataBuffer();
        int[] offScreenRaster = dataBuffer.getData();

        for (int y = 0; y < scrHeight; ++y) {
            for (int x = 0; x < scrWidth; ++x) {
                offScreenRaster[x + y * scrWidth] = outBuffer[x * texWidth / scrWidth + (y * texHeight / scrHeight) * texWidth];
            }
        }
    }
}

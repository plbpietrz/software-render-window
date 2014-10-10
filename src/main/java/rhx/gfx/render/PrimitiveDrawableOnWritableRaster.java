package rhx.gfx.render;

import java.awt.*;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;

/**
 * This implementation of {@link rhx.gfx.render.PrimitiveDrawable} is based on and underlying int {@link java.awt.image.Raster}.
 * Created by rhinox on 2014-07-15.
 */
public class PrimitiveDrawableOnWritableRaster implements PrimitiveDrawable {


    private final int width;
    private final int height;
    private final int[] rasterDataBuffer;

    public PrimitiveDrawableOnWritableRaster(Raster writableRaster) {
        DataBuffer dataBuffer = writableRaster.getDataBuffer();
        if (DataBuffer.TYPE_INT == dataBuffer.getDataType()) {
            width = writableRaster.getWidth();
            height = writableRaster.getHeight();
            rasterDataBuffer = ((DataBufferInt)dataBuffer).getData();
        } else {
            throw new IllegalArgumentException(
                    "Provided raster of type: " + writableRaster.getClass().getCanonicalName() + " is not supported.");
        }
    }

    private void drawPixel(final int x, final int y, Color color) {
        rasterDataBuffer[x + y * width] = color.getRGB();
    }

    @Override
    public PrimitiveDrawable drawLine(int sx, int sy, int ex, int ey, Color color) {
        int dx, dy, dp, dd,  d0, ix, iy, di, i;

        if (sx > ex) {
            dx = sx;
            sx = ex;
            ex = dx;

            dy = sy;
            sy = ey;
            ey = dy;
        }

        if (sy < ey) {
            if ( (ex - sx) > (ey - sy)) {
                dx = ex - sx;
                dy = ey - sy;
                ix = sx;
                iy = sy;
                dp = 2 * dy;
                dd = 2 * (dy - dx);
                d0 = 2 * dy - dx;
                di = d0;

                drawPixel(sx, sy, color);

                for (i = 0; i < dx; ++i) {
                    ix += 1;
                    if (di >= 0) {
                        di += dd;
                        iy += 1;
                    } else  {
                        di += dp;
                    }
                    drawPixel(ix, iy, color);
                }
            } else {
                dx = ex - sx;
                dy = ey - sy;
                ix = sx;
                iy = sy;
                dp = 2 * dx;
                dd = 2 * (dx - dy);
                d0 = 2 * dx - dy;
                di = d0;

                drawPixel(sx, sy, color);
                for (i = 0; i < dy; ++i) {
                    iy += 1;
                    if (di >= 0) {
                        di += dd;
                        ix += 1;
                    } else {
                        di += dp;
                    }
                    drawPixel(ix, iy, color);
                }
            }
        } else {
            if ( (ex - sx) > (sy - ey)) {
                dx = ex - sx;
                dy = sy - ey;
                ix = sx;
                iy = sy;
                dp = - 2 * dy;
                dd = - 2 * (dy - dx);
                d0 = - 2 * dy + dx;
                di = d0;

                drawPixel(sx, sy, color);
                for (i = 0; i < dx; ++i) {
                    ix += 1;
                    if (di <= 0) {
                        di += dd;
                        iy -= 1;
                    } else {
                        di += dp;
                    }
                    drawPixel(ix, iy, color);
                }
            } else {
                dx = ex - sx;
                dy = sy - ey;
                ix = sx;
                iy = sy;
                dp = - 2 * dx;
                dd = - 2 * (dx - dy);
                d0 = - 2 * dx + dy;
                di = d0;

                drawPixel(sx, sy, color);
                for (i = 0; i < dy; ++i) {
                    iy -= 1;
                    if (di <= 0) {
                        di += dd;
                        ix += 1;
                    } else {
                        di += dp;
                    }
                    drawPixel(ix, iy, color);
                }
            }
        }
        return this;
    }

    @Override
    public PrimitiveDrawable drawCircle(int cx, int cy, int r) {
        return drawCircle(cx, cy, r, 1, 1);
    }

    @Override
    public PrimitiveDrawable drawCircle(int cx, int cy, int r, int p, int q) {
        throw new UnsupportedOperationException("TO BE IMPLEMENTED!!");
    }
}

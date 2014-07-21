package rhx.gfx.render;

import java.awt.*;

/**
 * Interface representing object able to do primitive drawing operations. Return values on methods used for fluent
 * API implementation.
 * Created by pietrzyk on 7/15/2014.
 */
public interface PrimitiveDrawable {

    /**
     * Basic line drawing method. It draws a straight line from start to end and with designated color.
     * @param sx start x
     * @param sy start y
     * @param ex end x
     * @param ey end y
     * @param color line {@link java.awt.Color}
     * @return implementation dependent, could be new or the same instance of {@link rhx.gfx.render.PrimitiveDrawable}
     */
    PrimitiveDrawable drawLine(final int sx, final int sy, final int ex, final int ey, Color color);

    /**
     * Basic circle drawing method with pixel aspect ration equal to 1
     * @param cx circle center x component
     * @param cy circle center y component
     * @param r circle radius
     * @return implementation dependent, could be new or the same instance of {@link rhx.gfx.render.PrimitiveDrawable}
     */
    PrimitiveDrawable drawCircle(final int cx, final int cy, final int r);

    /**
     * Basic circle drawing method with pixel aspect ration equal to 1
     * @param cx circle center x component
     * @param cy circle center y component
     * @param r circle radius
     * @param p pixel center x aspect part
     * @param q pixel center y aspect part
     * @return implementation dependent, could be new or the same instance of {@link rhx.gfx.render.PrimitiveDrawable}
     */
    PrimitiveDrawable drawCircle(final int cx, final int cy, final int r, final int p, final int q);

}

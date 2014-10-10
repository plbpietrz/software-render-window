package rhx.gfx.render.tunnel;

import rhx.gfx.render.Drawable;

/**
 * Renderer is a component that does a draw operation on a given {@link rhx.gfx.render.Drawable} surface.
 * Created by rhinox on 2014-08-11.
 */
public interface Renderer {

    /**
     * Draw operation.
     * @param drawable {@link rhx.gfx.render.Drawable} surface on which we will draw.
     * @return this renderer
     */
    Renderer drawOn(Drawable drawable);

}

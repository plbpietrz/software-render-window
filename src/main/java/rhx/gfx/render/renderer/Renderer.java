package rhx.gfx.render.renderer;

import rhx.gfx.render.Drawable;

/**
 * Renderer.
 * Created by rhinox on 2014-08-11.
 */
public interface Renderer {

    Renderer init(Drawable drawable);

    /**
     * Draw operation.
     * @param drawable {@link rhx.gfx.render.Drawable} surface on which we will draw.
     * @return this renderer
     */
    Renderer drawOn(Drawable drawable);

}

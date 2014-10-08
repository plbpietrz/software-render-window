package rhx.gfx.render.tunnel;

import rhx.gfx.render.Drawable;

/**
 * Renderer.
 * Created by rhinox on 2014-08-11.
 */
public interface Renderer {

    Renderer init(Drawable drawable);

    Renderer drawOn(Drawable drawable);

}

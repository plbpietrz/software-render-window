package rhx.gfx.render;

/**
 * Basic rendering object. Contains logic required to render on {@link rhx.gfx.render.Drawable} surface.
 * Created by rhinox on 2014-08-11.
 */
public interface Render {

    /**
     * Set the current active drawing surface based on {@link rhx.gfx.render.Drawable}.
     * @param surface
     * @return by fluent conventions it should be the same {@link rhx.gfx.render.Render}
     */
    Render setDrawableSurface(Drawable surface);

    /**
     * Render the active frame.
     * @return by fluent conventions it should be the same {@link rhx.gfx.render.Render}
     */
    Render render();
}

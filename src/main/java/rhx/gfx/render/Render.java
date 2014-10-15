package rhx.gfx.render;

import rhx.gfx.render.renderer.Renderer;

import java.awt.*;

/**
 * Basic rendering object. Contains logic required to render on {@link rhx.gfx.render.Drawable} surface.
 * Created by rhinox on 2014-08-11.
 */
public interface Render {

    /**
     * Set the current active drawing surface based on {@link rhx.gfx.render.Drawable}.
     *
     * @param surface {@link rhx.gfx.render.Drawable} surface on which the image will be drawn.
     * @return by fluent conventions it should be the same {@link rhx.gfx.render.Render}
     */
    Render setDrawableSurface(Drawable surface);

    /**
     * Set the active {@link rhx.gfx.render.renderer.Renderer} object that is suposed to draw on {@link rhx.gfx.render.Drawable} surface
     *
     * @param renderer {@link Renderer} instance
     * @return by fluent conventions it should be the same {@link rhx.gfx.render.Render}
     */
    Render setRenderer(Renderer renderer);

    /**
     * Renders the active frame.
     *
     * @return by fluent conventions it should be the same {@link rhx.gfx.render.Render}
     */
    Render render();

    /**
     * The actual {@link java.awt.Component} that will be responsible for displaying drawn image.
     * @param display {@link java.awt.Component} responsible for displaying the content
     * @return by fluent conventions it should be the same {@link rhx.gfx.render.Render}
     */
    Render setDisplay(Component display);
}

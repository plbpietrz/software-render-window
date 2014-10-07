package rhx.gfx.render;

import rhx.gfx.render.tunnel.Renderer;

import java.util.concurrent.TimeUnit;

/**
 * Created by rhinox on 2014-08-15.
 */
public class RenderLoop implements Render, Runnable {

    private static final int NOT_SET = -1;
    public static final int ONE_SECOND = 1000;

    private int maxFps = NOT_SET;
    private boolean renderFlag = true;
    private Renderer renderer;
    private Drawable surface;

    @Override
    public void run() {
        if (maxFps == NOT_SET) {
            while (renderFlag) {
                render();
            }
        } else {
            try {
                long frameBudget = ONE_SECOND / maxFps, instant;
                while (renderFlag) {
                    instant = System.currentTimeMillis();
                    render();
                    instant = System.currentTimeMillis() - instant;
                    Thread.sleep(TimeUnit.MILLISECONDS.toMillis(frameBudget - instant));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RenderLoop render() {
        renderer.drawOn(surface);
        return this;
    }

    @Override
    public RenderLoop setDrawableSurface(Drawable surface) {
        this.surface = surface;
        return this;
    }

    @Override
    public RenderLoop setRenderer(Renderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public RenderLoop stop() {
        this.renderFlag = false;
        return this;
    }

    public RenderLoop setMaxFPS(int fps) {
        this.maxFps = fps;
        return this;
    }
}

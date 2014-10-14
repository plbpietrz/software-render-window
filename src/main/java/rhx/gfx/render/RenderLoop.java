package rhx.gfx.render;

import rhx.gfx.render.tunnel.Renderer;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Main render loop. Can be run in a separate thread.
 * Created by rhinox on 2014-08-15.
 */
public class RenderLoop implements Render, Runnable {

    private static final int NOT_SET = -1;
    public static final int ONE_SECOND = 1000;

    private int maxFps = NOT_SET, frameCount;
    private boolean renderFlag = true;

    private Renderer renderer;
    private Drawable surface;
    private Component display;

    @Override
    public void run() {
        renderer.init(surface);
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
        frameCount += 1;
        display.repaint();
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

    @Override
    public RenderLoop setDisplay(Component display) {
        this.display = display;
        return this;
    }

    /**
     * Stop render loop from drawing image.
     * @return this object
     */
    public RenderLoop stop() {
        this.renderFlag = false;
        return this;
    }

    /**
     * Set the target maximum frames per second.
     * @param fps frames per second
     * @return this object
     */
    public RenderLoop setMaxFPS(int fps) {
        this.maxFps = fps;
        return this;
    }

    /**
     * Return the number of frames generated from the beginning of the render loop start or the last execution of
     * this method.
     * @return number of frames from last execution of this method
     */
    public int getFrameCountAndReset() {
        int tmp = frameCount;
        frameCount = 0;
        return tmp;
    }
}

package rhx.gfx.render.renderer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by rhinox on 2014-10-13.
 */
public class WaterDropMouseListener implements MouseListener {

    private WaterRenderer renderer;

    public WaterDropMouseListener(WaterRenderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        renderer.poke(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

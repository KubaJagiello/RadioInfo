package View;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * This class is used to override some methods in JScrollBar
 * to chose own colors.
 */
public class MyScrollBarUI extends BasicScrollBarUI{

    /**
     * @param c JComponent
     * @return MyScrollBarUI instance
     */
    public static ComponentUI createUI(JComponent c) {
        return new MyScrollBarUI();
    }

    /**
     * Custom colors on increase button in JScrollBar
     * @param i Int
     * @return JButton
     */
    @Override
    protected JButton createIncreaseButton(int i) {
        return new BasicArrowButton(i, new Color(135, 189, 216), new Color(135, 189, 216), new Color(255, 255, 255), new Color(135, 189, 216));
    }

    /**
     * Custom colors on decrease button in JScrollBar
     * @param i Int
     * @return JButton
     */
    @Override
    protected JButton createDecreaseButton(int i) {
        return new BasicArrowButton(i, new Color(135, 189, 216), new Color(135, 189, 216), new Color(255, 255, 255), new Color(135, 189, 216));

    }

    /**
     * Custom colors on background in JScrollBar
     * @param g grahics
     * @param c JComponent
     * @param trackBounds Rectangle
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, trackBounds.width, trackBounds.height);
    }

    /**
     * Custom colors on JScrollBar
     * @param g graphics
     * @param c JComponent
     * @param thumbBounds Rectangle
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(new Color(135, 189, 216));
        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }
}

package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * DefaultTableCellRenderer that makes it possible to draw Image
 * inside cell.
 */
public class ImageRenderer extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage((Image) value,
                                        0,
                                        0,
                                        getWidth(),
                                        getHeight(),
                                        this);
            }
        };
    }
}

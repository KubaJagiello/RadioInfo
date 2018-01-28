package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Shows title and description for program.
 */
class ProgramDescription extends Content {

    ProgramDescription(){
        initTable();
    }

    /**
     * Creates JTable and DefaultTableModel with custom settings.
     * Has 2 columns for title and description of program.
     */
    private void initTable(){
        tableModel = createDefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setFillsViewportHeight(true);
        jTable.setDragEnabled(false);
        jTable.setRowHeight(218);
        tableModel.addColumn("Title");
        jTable.setTableHeader(null);
        jTable.setGridColor(Color.WHITE);
        setLayout(new BorderLayout());
        setCellRendererForTime();
        add(new JScrollPane(jTable));
    }

    /**
     * @return new DefaultTableModel that treats every cell as String.class
     * on default. Also prevents cells from beeing editable.
     */
    private DefaultTableModel createDefaultTableModel(){
        return new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
    }

    /**
     * Renders cell in JTable to have color and bigger font.
     */
    private void setCellRendererForTime(){
            jTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    return new JPanel(){

                        @Override
                        protected void paintComponent(Graphics g) {
                            g.setColor(row  % 2 > 0 ? new Color(207, 224, 232) :
                                    new Color(183, 215, 232));
                            g.fillRect(0,0, getWidth(), getHeight());
                            g.setColor(Color.BLACK);
                            Graphics2D g2d = (Graphics2D) g;
                            if(value.toString().length() > 0) {
                                if(row % 2 > 0) {
                                    drawStringMultiLine(g2d, value.toString(), 225, 10, 35, 25);
                                } else {
                                    drawStringMultiLine(g2d, value.toString(), 80, 150, 110, 30);
                                }
                            }
                        }
                    };
                }
            });

    }

    /**
     * Makes it possible to write multiple strings in custom rendered table
     * @param g Graphics2D
     * @param text text to be rendered
     * @param lineWidth width of cell
     * @param x string will be drawn from this x cord
     * @param y string will be drawn from this y cord
     * @param fontSize size of String
     */
    private static void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y, int fontSize) {
        FontMetrics m = g.getFontMetrics();
        TextLayout textLayout;
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Font font = new Font("Serif", Font.BOLD, fontSize);
        if(m.stringWidth(text) < lineWidth) {
            textLayout = new TextLayout(text, font, frc);
            textLayout.draw(g, x ,y);
        } else {
            String[] words = text.split(" ");
            String currentLine = words[0];
            for(int i = 1; i < words.length; i++) {
                if(m.stringWidth(currentLine+words[i]) < lineWidth) {
                    currentLine += " "+words[i];
                } else {
                    textLayout = new TextLayout(currentLine, font, frc);
                    textLayout.draw(g, x ,y);
                    y += m.getHeight()+10;
                    currentLine = words[i];
                }
            }
            if(currentLine.trim().length() > 0) {
                textLayout = new TextLayout(currentLine, font, frc);
                textLayout.draw(g, x ,y);
            }
        }
    }
}

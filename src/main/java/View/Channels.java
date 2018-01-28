package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

/**
 * Shows images and name of channels.
 */
class Channels extends Content {

    Channels(){
        initTable();
        setRenderForText();
    }

    /**
     * Creates JTable and DefaultTableModel with custom settings.
     * It has two columns with Image and Channel.
     */
    private void initTable(){
        tableModel = createDefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setFillsViewportHeight(true);
        jTable.setDragEnabled(false);
        jTable.setTableHeader(null);
        tableModel.addColumn("Image");
        tableModel.addColumn("Channel");
        jTable.setRowHeight(180);
        jTable.getColumnModel().getColumn(0).setMaxWidth(180);
        jTable.getColumnModel().getColumn(0).setMinWidth(180);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        setLayout(new BorderLayout());
        jTable.setGridColor(Color.WHITE);
        jTable.setDefaultRenderer(Image.class, new ImageRenderer());
        jTable.setFont(new Font("Serif", Font.BOLD, 30));
        add(new JScrollPane(jTable));
    }

    /**
     * Renders String of channel name to appear bigger in table.
     */
    private void setRenderForText(){
        jTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                return new JPanel(){
                    @Override
                    protected void paintComponent(Graphics g) {
                        g.setColor(row % 2 > 0 ? new Color(255, 255, 255) :
                                new Color(255, 255, 255));
                        g.fillRect(0,0, getWidth(), getHeight());
                        g.setColor(Color.BLACK);
                        Font font = new Font("Serif", Font.BOLD, 30);
                        FontRenderContext frc = new FontRenderContext(null, true, true);

                        TextLayout layout = new TextLayout(value.toString(), font, frc);
                        Graphics2D g2d = (Graphics2D) g;
                        layout.draw(g2d, 10, 100);
                    }
                };
            }
        });
    }

    /**
     * @return new DefaultTableModel to treat column 1 as Image
     * and the other one as String.
     */
    private DefaultTableModel createDefaultTableModel(){
        return new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch(columnIndex){
                    case 0:
                        return Image.class;
                    default:
                        return String.class;
                }

            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}

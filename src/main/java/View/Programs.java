package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 * This class shows programs in table.
 */
class Programs extends Content {

    Programs(){
        initTable();
        setCellRendererForTime();
    }

    /**
     * Creates new jTable and DefaultTableModel. TableModel has 3 columns for program
     * name, start time and end time.
     */
    private void initTable(){
        tableModel = createDefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setFillsViewportHeight(true);
        jTable.setDragEnabled(false);
        jTable.setTableHeader(null);
        tableModel.addColumn("Program");
        tableModel.addColumn("Starts");
        tableModel.addColumn("Ends");
        jTable.setRowHeight(70);

        jTable.getColumnModel().getColumn(1).setMaxWidth(75);
        jTable.getColumnModel().getColumn(2).setMaxWidth(75);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(1).setMinWidth(75);
        jTable.getColumnModel().getColumn(2).setMinWidth(75);
        setLayout(new BorderLayout());
        jTable.setGridColor(Color.WHITE);
        add(new JScrollPane(jTable));
    }

    /**
     * Overrides DefaultTableCellRenderer so that programs that have already
     * passed will have their time colored in gray.
     */
    private void setCellRendererForTime(){
        for(int i = 0; i < 3; i++) {
            jTable.getColumnModel().
                    getColumn(i).
                    setCellRenderer( new DefaultTableCellRenderer() {
                private LocalDateTime timeNow = LocalDateTime.now();



                @Override
                public Component getTableCellRendererComponent(JTable table,
                                                               Object value,
                                                               boolean isSelected,
                                                               boolean hasFocus,
                                                               int row,
                                                               int column) {

                    return new JPanel(){
                        @Override
                        protected void paintComponent(Graphics g) {

                            if(value != null && column > 0  && timeNow.isAfter((LocalDateTime)value)){
                                g.setColor(new Color(135, 189, 216));
                            } else {
                                g.setColor( row % 2 > 0 ? new Color(255, 255, 255) :
                                        new Color(218, 235, 232));
                            }

                            g.fillRect(0,0, getWidth(), getHeight());
                            g.setColor(Color.BLACK);
                            Font font = new Font("Serif", Font.BOLD, 20);
                            FontRenderContext frc = new FontRenderContext(null, true, true);

                            Graphics2D g2d = (Graphics2D) g;
                            if(value!=null) {
                                if (column > 0) {
                                    String tmp = ((LocalDateTime) value).format(
                                            DateTimeFormatter.ofPattern("HH:mm"));
                                    TextLayout layout = new TextLayout(tmp, font, frc);
                                    layout.draw(g2d, 5, 40);
                                } else {
                                    if(value.toString().length() > 0) {
                                        TextLayout layout = new TextLayout(value.toString(), font, frc);
                                        layout.draw(g2d, 5, 40);
                                    }
                                }
                            }
                        }
                    };
                }
            });
        }
    }


    /**
     * @return new DefaultTableModel that will treat every cell as
     * String on default and sets cell to no editable to prevent errors.
     */
    private DefaultTableModel createDefaultTableModel(){
        return new DefaultTableModel(){

            @Override
            public void addColumn(Object o, Vector vector) {
                super.addColumn(o, vector);
            }

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
}

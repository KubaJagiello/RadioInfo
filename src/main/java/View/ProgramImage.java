package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * This Class is only for showing Image for Program.
 */
class ProgramImage extends Content {

    ProgramImage(){
        initTable();
    }

    /**
     * Creates JTable and DefaultTableModel. The only column is
     * Image for program.
     */
    private void initTable(){
        tableModel = createDefaultTableModel();
        jTable = new JTable(tableModel);
        jTable.setFillsViewportHeight(true);
        jTable.setDragEnabled(false);
        jTable.setTableHeader(null);
        jTable.setRowHeight(435);
        tableModel.addColumn("Image");
        setLayout(new BorderLayout());
        add(new JScrollPane(jTable));
        jTable.setDefaultRenderer(Image.class, new ImageRenderer());
    }

    /**
     * @param objects Arrays of objects that will be inserted into table
     */
    @Override
    void addContent(Object[] objects) {
        super.addContent(objects);
    }

    /**
     * @return new DefaultTableModel that treats cells as Image.Class
     * on default.
     */
    private DefaultTableModel createDefaultTableModel(){
        return new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return Image.class;
            }
        };
    }
}

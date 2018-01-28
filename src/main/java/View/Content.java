package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Abstract class that specifies these three methods that every table needs
 * to have.
 */
abstract class Content extends JPanel{

    JTable jTable;
    DefaultTableModel tableModel;

    Content(){

    }

    /**
     * @param objects Arrays of objects that will be inserted into table
     */
    void addContent(Object[] objects){
        tableModel.addRow(objects);
    }

    /**
     * Clears table
     */
    void clearContent(){
        tableModel.setRowCount(0);
    }

    /**
     * Sets your actionlistener for given column.
     * @param action Your implementation that will be executed
     * @param column Column in table
     */
    void setActionListener(ListenerForAction action, int column){
        jTable.getSelectionModel().addListSelectionListener( e -> {
            if(!e.getValueIsAdjusting() && jTable.getSelectedRow() > -1){
                action.action(jTable.getValueAt(jTable.getSelectedRow(), column).toString());
                jTable.clearSelection();
            }
        });
    }
}

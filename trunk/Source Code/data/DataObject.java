package data;

import java.sql.SQLException;
import javax.sql.RowSet;
import javax.swing.table.AbstractTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uSeR
 */
public class DataObject extends AbstractTableModel {
    private RowSet rs;

    public DataObject(RowSet rs){
        this.rs = rs;
    }

    public int getRowCount() {
        int count = 0;
        try {
            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int getColumnCount() {
        try{
            return rs.getMetaData().getColumnCount();
        }catch(SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            rs.absolute(rowIndex);
            return rs.getObject(columnIndex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

    }

    @Override
    public String getColumnName(int columnIndex){
        try{
            return rs.getMetaData().getColumnName(columnIndex);
        }catch(SQLException ex){
            ex.printStackTrace();
            return "Error";
        }
    }

}

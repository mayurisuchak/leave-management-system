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
            if(rs.first())
            {
                count = 1;
                while (rs.next()) {
                    count++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            count = 0;
        }finally{
            return count;
        }
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
        Object value = null;
        try {
            rs.absolute(rowIndex);
            value = rs.getObject(columnIndex+1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            value = -1;
        } finally{
            return value;
        }

    }

    @Override
    public String getColumnName(int columnIndex){
        String colName;
        colName = "Error";
        try{
            colName = rs.getMetaData().getColumnName(columnIndex+1);
        }catch(SQLException ex){
            ex.printStackTrace();
            colName = "Error";
        }finally{
            return colName;
        }
    }

}

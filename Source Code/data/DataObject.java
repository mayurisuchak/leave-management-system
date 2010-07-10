package data;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
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
    private CachedRowSet rs;

    public DataObject(RowSet rs){
        try {
            this.rs = ((CachedRowSet) rs).createCopy();
        } catch (SQLException ex) {
            Logger.getLogger(DataObject.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            //sSystem.out.println(count);
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
            rs.absolute(rowIndex+1);
            value = rs.getObject(columnIndex+1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            value = -1;
        } finally{
           // System.out.println(value);
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
            //System.out.println(colName);
            return colName;
        }
    }

}

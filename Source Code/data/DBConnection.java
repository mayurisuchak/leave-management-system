/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractList;
import java.sql.Date;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import snaq.db.ConnectionPool;

/**
 *
 * @author uSeR
 */
public class DBConnection {
    private ConnectionPool cp;
    private CachedRowSet crs;

    public DBConnection(Properties props) {
        try {
            Class.forName(props.getProperty("driverUrl"));
            String name = props.getProperty("name");
            int maxPool = Integer.parseInt(props.getProperty("maxPool"));
            int maxSize= Integer.parseInt(props.getProperty("maxSize"));
            int idleTimeout = Integer.parseInt(props.getProperty("idleTimeout"));
            String url = props.getProperty("url");
            cp = new ConnectionPool(name, maxPool, maxSize, idleTimeout, url , props);
            cp.init();
            crs = new CachedRowSetImpl();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public RowSet query(String sqlString){
        try {
            Connection conn = cp.getConnection();
            Statement stm = conn.createStatement();
            crs.populate(stm.executeQuery(sqlString));
            stm.close();
            conn.close();
            return crs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int query(String prepareSQL, AbstractList<Object> params){
        try {
            Connection conn = cp.getConnection();
            PreparedStatement preStm = conn.prepareStatement(prepareSQL);
            int paramsCount = params.size();
            for(int i = 0; i < paramsCount; i++)
            {
                String type = params.get(i).getClass().toString();
                if(type.indexOf("String") > -1)
                    preStm.setString(i+1, (String)params.get(i));
                else if(type.indexOf("Double") > -1)
                    preStm.setFloat(i+1, (Float)params.get(i));
                else if(type.indexOf("Integer") > -1)
                    preStm.setInt(i+1, (Integer)params.get(i));
                else if(type.indexOf("Date") > -1)
                {
                    java.util.Date date = (java.util.Date)params.get(i);
                    preStm.setDate(i+1, new Date(date.getTime()));
                }
                else if(type.indexOf("Boolean") > -1)
                    preStm.setBoolean(i+1, (Boolean)params.get(i));

            }
            int affectedRows = preStm.executeUpdate();
            conn.close();
            preStm.close();
            return affectedRows;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public RowSet getRowSet(){
        return crs;
    }

    public Connection getConnection(){
        try {
            return cp.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void close(){
        try {
            crs.close();
            cp.release();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

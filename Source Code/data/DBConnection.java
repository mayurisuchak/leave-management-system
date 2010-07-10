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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;

/**
 *
 * @author uSeR
 */
public class DBConnection{
    private ConnectionPool cp;
    private CachedRowSet crs;

    public DBConnection(Properties props) {
        try {
            ConnectionPoolManager.registerGlobalShutdownHook();
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
            Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet r = stm.executeQuery(sqlString);
            r.first();
            crs.populate(r,1);
            crs.first();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            return crs;
        }
    }

    public RowSet complexQuery(String prepareSQL, AbstractList<Object> params){
        try {
            Connection conn = cp.getConnection();
            PreparedStatement preStm = conn.prepareStatement(prepareSQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
            crs.populate(preStm.executeQuery(),1);
            preStm.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            return crs;
        }
    }

    public int queryUpdate(String prepareSQL, AbstractList<Object> params){
        int affectedRows = 0;
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
            affectedRows = preStm.executeUpdate();
            conn.close();
            preStm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            affectedRows = -1;
        } finally{
            return affectedRows;
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

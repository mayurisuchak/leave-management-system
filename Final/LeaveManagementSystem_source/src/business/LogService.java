/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;
import data.DataObject;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;

/**
 *
 * @author uSeR
 */
class LogService {
    public static final String LOG_ACTION_LEAVE_APPLICATION = "apply leave";
    public static final String LOG_ACTION_CANCELATION_REQUEST = "request cancellation";
    public static final String LOG_ACTION_WITHDRAWAL = "withdraw leave";
    public static final String LOG_ACTION_LEAVE_APPROVAL = "approve leave";
    public static final String LOG_ACTION_LEAVE_REJECTION = "reject leave";
    public static final String LOG_ACTION_CANCEL_APPROVAL = "approve cancellation";
    public static final String LOG_ACTION_CANCEL_REJECTION = "reject cancellation";
    public static final String LOG_ACTION_PASSWORD_CHANGE = "change password";

    private DataAccess da;
    public LogService() {
        da = DataAccess.getInstance();
    }

    /**
     * view all log details of userid
     */
    public DataObject viewLogDetailAll(int userID){
        return new DataObject( da.viewLogDetail(userID));
    }

    /**
     * view log detail from time to time of userid
     */
    public DataObject viewLogDetail(int userID, Date dateStart, Date dateEnd){
        return new DataObject( da.viewLogDetail(userID,dateStart,dateEnd));
    }

    /**
     * create log, if ok return true. Remark: action is one of constant String LOG_ACTION_XXX
     * @param action: constant LOG_ACTION_*
     */
    public boolean createLog(int userID, String action){
        if(da.createLog(userID, action)>0)
            return true;
        else
            return false;
    }

    /**
     * create log, if ok return true. <P>Remark: 1. Action is one of constant String LOG_ACTION_XXX. <p>2. Log with actions relative to a leave.
     * @param action: constant LOG_ACTION_*
     */
    public boolean createLog(int userID, String action, int leaveID){
        if(da.createLog(userID, action, leaveID)>0)
            return true;
        else
            return false;
    }

    /**
     * get username by userid
     */
    public String getUsername(int userID){
        String username = "null";
        try {            
            RowSet rs = da.viewUsername(userID);
            if (rs.first()) {
                username = rs.getString(1);
            } else {
                username = "null";
            }
        } catch (SQLException ex) {
            username = "null";
        }  finally{
            return username;
        }

    }
}

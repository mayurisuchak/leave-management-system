/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;
import data.DataObject;
import java.util.Date;

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

    // view log detail all
    public DataObject viewLogDetailAll(int userID){
        return new DataObject( da.viewLogDetail(userID));
    }

    // view log detail from time to time
    public DataObject viewLogDetail(int userID, Date dateStart, Date dateEnd){
        return new DataObject( da.viewLogDetail(userID,dateStart,dateEnd));
    }

    // create log
    public boolean createLog(int userID, String action){
        if(da.createLog(userID, action)>0)
            return true;
        else
            return false;
    }
    public boolean createLog(int userID, String action, int leaveID){
        if(da.createLog(userID, action, leaveID)>0)
            return true;
        else
            return false;
    }

}

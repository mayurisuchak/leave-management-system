/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;
import data.DataObject;
import java.util.Calendar;
import javax.sql.RowSet;

/**
 *
 * @author uSeR
 */
class RequestService {
    private DataAccess da;

    public RequestService() {
        da = DataAccess.getInstance();
    }

    /**
     * check whether userid is a superior, return true if right
     */
    public boolean checkSuperior(int userID){
        return da.checkSuperior(userID);
    }

    /**
     * view leaves submitted to superior
     */
    public DataObject viewSubmittedLeave(int superiorID){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        RowSet rs = da.viewSubmittedLeave(superiorID, currentYear);
        return new DataObject(rs);
    }

    /**
     * approve(reject) leave(request) from userID <P> allowance = true to approve , allowance = false to reject
     */
    public boolean updateLeaveStatus(int leaveID, Boolean allowance ){
        if(da.updateLeaveState(leaveID, allowance)>0)
            return true;
        else
            return false;
    }

    /**
     * view list of subordinate
     */
    public RowSet viewSubordinateList(int superiorID){
        return da.viewSubordinateList(superiorID);
    }
}

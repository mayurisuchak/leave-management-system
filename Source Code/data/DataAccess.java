/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.sql.RowSet;

/**
 *
 * @author uSeR
 */
public class DataAccess {
    public static final String LOG_ACTION_LEAVE_APPLICATION = "apply leave";
    public static final String LOG_ACTION_CANCELATION_REQUEST = "request cancellation";
    public static final String LOG_ACTION_WITHDRAWAL = "withdraw leave";
    public static final String LOG_ACTION_LEAVE_APPROVAL = "approve leave";
    public static final String LOG_ACTION_LEAVE_REJECTION = "reject leave";
    public static final String LOG_ACTION_CANCEL_APPROVAL = "approve cancellation";
    public static final String LOG_ACTION_CANCEL_REJECTION = "reject cancellation";
    public static final String LOG_ACTION_PASSWORD_CHANGE = "change password";

    private DBConnection db;

    public DataAccess(String propertiesFile) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(propertiesFile));
            db = new DBConnection(prop);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public DataAccess(Properties properties) {
        db = new DBConnection(properties);
    }

    public DataAccess(){
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("properties.prop"));
            db = new DBConnection(prop);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // view leave detail according to userID and year
    public RowSet viewLeaveDetail(int userID, int year){
        return db.query("EXEC sp_LeaveDetail " + userID + ", " + year);
    }

    // view leave submitted to superiorID
    public RowSet viewSubmittedLeave(int superiorID){
        return db.query("EXEC sp_SubmitedLeaves " + superiorID);
    }

    // view report of subordinate in year
    public RowSet viewSubordinateReport(int superiorID, int year){
        return db.query("EXEC sp_SubordinateDetail " + superiorID + "," + year);
    }

    // view list of subordinate which superiorID is in charge of
    public RowSet viewSubordinateList(int superiorID){
        return db.query("EXEC sp_Subordinate " + superiorID);
    }

    // view personal detail in year
    public RowSet viewPersonalDetail(int userID, int year){
        return db.query("EXEC sp_PersonalDetail " + userID + "," + year);
    }

    // view log detail of an employee with id: userID
    public RowSet viewLogDetail(int userID){
        return db.query("EXEC sp_SubordinateDetail " + userID);
    }

    // update password of userID with new password: pass
    public int updatePassword(int userID, String pass){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);
        paramList.add(pass);
        return db.query("EXEC sp_ChangePassword ?, ?", paramList);
    }

    // create new log with leaveID
    public int createLog(int userID, String action, int leaveID){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);      // UserID field
        paramList.add(new Date());  // Time field
        paramList.add(action);      // Action field
        paramList.add(leaveID);     // LeaveID field
        return db.query("EXEC sp_CreateLog ?,?,?,?", paramList);
    }

    // create new log with leavID = null
    public int createLog(int userID, String action){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);      // UserID field
        paramList.add(new Date());  // Time field
        paramList.add(action);      // Action field
        return db.query("EXEC sp_CreateLog ?,?,?", paramList);
    }

    // create new leave
    public int createNewLeave(int userID, Date dateStart, Date dateEnd, String reason, String communication, String subject){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);          // UserID field
        paramList.add(dateStart);       // DateStart field
        paramList.add(dateEnd);         // DateEnd field
        paramList.add(reason);          // Reason field
        paramList.add(communication);   // Communication field
        paramList.add(new Date());      // Date field
        paramList.add(subject);         // Subject field
        return db.query("EXEC sp_ApplyLeave ?,?,?,?,?,?,?", paramList);
    }

    // withdraw a not-approved leave or request cancellation of a approved leave
    public int removeLeave(int leaveID){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(leaveID);
        return db.query("EXEC sp_CancelLeave ?",paramList);
    }

    // approve/reject leave/request (allowance = true: approve, allowance = false: rejected, )
    public int updateLeaveState(int leaveID, Boolean allowance){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(leaveID);
        paramList.add(allowance);
        return db.query("EXEC sp_ManageRequest ?,?",paramList);
    }

    // check userid is whether a superior or not
    public boolean checkSuperior(int userID){
        try {
            RowSet rs = db.query("EXEC sp_CheckSuperior " + userID);
            rs.next();
            if (rs.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void close(){
        db.close();
    }
}

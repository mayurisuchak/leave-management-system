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
    
    private static DataAccess dataAccess = new DataAccess();

    private DBConnection db;

//    private DataAccess(String propertiesFile) {
//        try {
//            Properties prop = new Properties();
//            prop.load(new FileInputStream(propertiesFile));
//            db = new DBConnection(prop);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private DataAccess(Properties properties) {
//        db = new DBConnection(properties);
//    }

    private DataAccess(){
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("properties.prop"));
            db = new DBConnection(prop);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static DataAccess getInstance(){
        return dataAccess;
    }

    // view leave detail according to userID and year
    public RowSet viewLeaveDetail(int userID, int year){
        return db.query("EXEC sp_LeaveDetail " + userID + ", " + year);
    }

    // view leave submitted to superiorID
    public RowSet viewSubmittedLeave(int superiorID, int year){
        return db.query("EXEC sp_SubmitedLeaves " + superiorID + "," + year);
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

     // view detail of a leave
    public RowSet viewDetailOfLeave(int leaveID){
        return db.query("EXEC sp_DetailOfLeave " + leaveID);
    }

    // view log detail of an employee with id: userID
    public RowSet viewLogDetail(int userID){
        return db.query("EXEC sp_LogDetail " + userID);
    }

    // view log detail of an employee form date to date
    public RowSet viewLogDetail(int userID, Date dateStart, Date dateEnd ){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);
        paramList.add(dateStart);
        paramList.add(dateEnd);
        return db.complexQuery("EXEC sp_LogDetailDuration ?,?,? ", paramList );
    }

    // update password of userID with new password: pass, if ok return 1
    public int updatePassword(int userID, String oldpass, String newpass){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);
        paramList.add(newpass);
        paramList.add(oldpass);
        return db.queryUpdate("EXEC sp_ChangePassword ?, ?, ?", paramList);
    }

    // create new log with leaveID, if ok return 1
    public int createLog(int userID, String action, int leaveID){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);      // UserID field
        paramList.add(new Date());  // Time field
        paramList.add(action);      // Action field
        paramList.add(leaveID);     // LeaveID field
        return db.queryUpdate("EXEC sp_CreateLog ?,?,?,?", paramList);
    }

    // create new log with leavID = null, if ok return 1
    public int createLog(int userID, String action){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);      // UserID field
        paramList.add(new Date());  // Time field
        paramList.add(action);      // Action field
        return db.queryUpdate("EXEC sp_CreateLog ?,?,?", paramList);
    }

    // create new leave, if ok return 1
    public int createNewLeave(int userID, Date dateStart, Date dateEnd, String reason, String communication, String subject){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(userID);          // UserID field
        paramList.add(dateStart);       // DateStart field
        paramList.add(dateEnd);         // DateEnd field
        paramList.add(reason);          // Reason field
        paramList.add(communication);   // Communication field
        paramList.add(new Date());      // Date field
        paramList.add(subject);         // Subject field
        return db.queryUpdate("EXEC sp_ApplyLeave ?,?,?,?,?,?,?", paramList);
    }

    // withdraw a not-approved leave or request cancellation of a approved leave, if ok return 1
    public int removeLeave(int leaveID){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(leaveID);
        return db.queryUpdate("EXEC sp_CancelLeave ?",paramList);
    }

    // approve/reject leave/request (allowance = true: approve, allowance = false: rejected ), if ok return 1
    public int updateLeaveState(int leaveID, Boolean allowance){
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(leaveID);
        paramList.add(allowance);
        return db.queryUpdate("EXEC sp_ManageRequest ?,?",paramList);
    }

    // check userid is whether a superior or not, if ok return 1
    public boolean checkSuperior(int userID){
        try {
            RowSet rs = db.query("EXEC sp_CheckSuperior " + userID);
            rs.first();
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

     // check login authentication if ok return 1
    public int checkLogin(String username, String password){
        try {
            RowSet rs = db.query("EXEC sp_CheckLogin '" + username + "','" + password + "'");
            if(rs.first())
                return rs.getInt(1);
            else
                return -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    // get join year of userid, if ok return join year
    public int viewJoinYear(int userID){
        try {
            RowSet rs = db.query("EXEC sp_JoinYear " + userID);
            if (rs.first()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    // get newly leaveID
    public RowSet getNewlyLeave(int userID){
        RowSet rs = db.query("EXEC sp_GetNewlyLeave " + userID);
        return rs;
    }

    // get leave status
    private RowSet getLeaveStatus(int leaveID){
        RowSet rs = db.query("EXEC sp_GetLeaveStatus " + leaveID);
        return rs;
    }

    // check if leave is approve or not: approved -> -1 || not approved -> 1
    public int checkApprove(int leaveID){
        try {
            RowSet rs = getLeaveStatus(leaveID);
            if (rs.first()) {
                String status = rs.getString(1);
                if (status.compareTo("Approved") == 0) {
                    return -1;
                } else if (status.compareTo("Not Approved") == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

     // check if satus is 'Not Approved' or 'Canceling': Not Approved -> 1 || Canceling -> -1
    public int checkRequest(int leaveID){
        try {
            RowSet rs = getLeaveStatus(leaveID);
            if (rs.first()) {
                String status = rs.getString(1);
                if (status.compareTo("Not Approved") == 0) {
                    return 1;
                } else if (status.compareTo("Canceling") == 0) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }

    }


    public void close(){
        db.close();
    }
}

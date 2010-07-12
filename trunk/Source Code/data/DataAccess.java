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
    public RowSet viewSubmittedLeaves(int superiorID, int year){
        return db.query("EXEC sp_SubmittedLeaves " + superiorID + "," + year);
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
        boolean check = false;
        try {
            RowSet rs = db.query("EXEC sp_CheckSuperior " + userID);
            rs.first();
            if (rs.getInt(1) > 0) {
                check = true;
            } else {
                check = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            check = false;
        }finally{
            return check;
        }
    }

     // check login authentication if ok return 1
    public int checkLogin(String username, String password){
        int tmp = -1;
        try {
            RowSet rs = db.query("EXEC sp_CheckLogin '" + username + "','" + password + "'");
            if(rs.first())
                tmp = rs.getInt(1);
            else
                tmp = -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            tmp = -1;
        } finally{
            return tmp;
        }

    }

    // get join year of userid, if ok return join year
    public int viewJoinYear(int userID){
        int year = -1;
        try {
            RowSet rs = db.query("EXEC sp_JoinYear " + userID);
            if (rs.first()) {
                year = rs.getInt(1);
            } else {
                year = -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            year = -1;
        } finally{
            return year;
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

    // check leave whether withdrawed or canceling: withdraw -> -1 || canceling -> 1
    public int checkRemoveLeave(int leaveID){
        int check = 0;
        try {
            RowSet rs = getLeaveStatus(leaveID);
            if (rs.first()) {
                String status = rs.getString(1);
                if (status.compareTo("Withdrawed") == 0) {
                    check = -1;
                } else if (status.compareTo("Canceling") == 0) {
                    check = 1;
                } else {
                    check = 0;
                }
            } else {
                check = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            check = 0;
        }finally{
            return check;
        }

    }

     // check if status and return: <P> -1 : Approved <P> -2 : Rejected <P> 1 : Canceled <P> 2: Cancel-Rejected
    public int checkUpdateLeaveStatus(int leaveID){
        int check = 0;
        try {
            RowSet rs = getLeaveStatus(leaveID);
            if (rs.first()) {
                String status = rs.getString(1);
                if (status.compareTo("Approved") == 0) {
                    check = -1;
                } else if (status.compareTo("Rejected") == 0) {
                    check = -2;
                } else if (status.compareTo("Canceled") == 0) {
                    check = 1;
                }else if (status.compareTo("Cancel-Rejected") == 0) {
                    check = 2;
                }else {
                    check = 0;
                }
            } else {
                check = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            check = 0;
        } finally{
            return check;
        }

    }


    public void close(){
        db.close();
    }
}

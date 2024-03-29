/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;
import data.DataObject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.sql.RowSet;

/**
 *
 * @author uSeR
 */
class LeaveService {
    DataAccess da;
    public LeaveService() {
        da = DataAccess.getInstance();
    }

    public boolean changePassword(int userID, String oldPassword, String newPassword){

        if(da.updatePassword(userID, md5String(oldPassword), md5String(newPassword)) > 0)
            return true;
        else
            return false;
    }

 /**
 * md5 input string and return crypted string
 */
    private String md5String(String plaintext){
        try {
            // md5 password
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(plaintext.getBytes());
            byte[] digest = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
            while(hashtext.length() < 32 ){
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * return userid equivalent to username and password
     */
    public int loginProcess(String username, String password){
        password = md5String(password);
        return da.checkLogin(username, password);

    }

    /**
     * apply new leave, if ok, return true
     */
    public boolean applyLeave(int userID, Date dateStart,Date dateEnd,String reason, String communication, String subject ){
        if(da.createNewLeave(userID,dateStart,dateEnd,reason,communication,subject)>0)
            return true;
        else
            return false;
    }

    /**
     *
     * cancel/withdraw leave, if ok, return true
     *
     */
    public boolean removeLeave(int leaveID){
        if(da.removeLeave(leaveID)>0)
            return true;
        else
            return false;
    }

    /**
     * get join year of userID, return join year
     */
    public int viewJoinYear(int userID){
        return da.viewJoinYear(userID);
    }

    /**
     * view detail of leave: 0->[Subject], 1->[Reason], 2->DateStart, 3->DateEnd, 4->Communication, 5->[Status]
     */
    public DataObject viewDetailOfLeave(int leaveID){
        return new DataObject(da.viewDetailOfLeave(leaveID));
        
    }

    /**
     * get newly leave, if ok return leaveID
     */
    public int getNewlyLeave(int userID){
        int tmp = -1;
        try {
            RowSet rs = da.getNewlyLeave(userID);
            if (rs.first()) {
                tmp = rs.getInt(1);
            }else{
                tmp = -1;
            }

        } catch (SQLException ex) {
            tmp = -1;
        } finally {
            return tmp;
        }

    }

    /*
     * check leave whether withdrawed or canceling: withdraw -> -1 || canceling -> 1
     */
    public int checkRemoveLeave(int leaveID){
        return da.checkRemoveLeave(leaveID);
    }


    /**
     * view leave history
     */
    public DataObject viewLeaves(int userID, int year){
        return new DataObject(da.viewLeaveDetail(userID, year));
    }


    /**
     * view personal detail : [Total leave Days], [Remaining leave days]
     */
    public Vector<String> viewPersonalDetail(int userID, int year){
        Vector<String> list = new Vector<String>();
        try {
            RowSet rs = da.viewPersonalDetail(userID, year);
            rs.first();
            list.add(rs.getString(3));
            list.add(rs.getString(4));
        } catch (SQLException ex) {
            list.add("error");
            list.add("error");
        }finally{
            return list;
        }
    }
    
    /**
     * view subordinate detail : [Name], [Code],[Total leave Days], [Remaining leave days]
     */
    public Vector<String> viewSubordinateDetail(int userID, int year){
        Vector<String> list = new Vector<String>();
        try {
            RowSet rs = da.viewPersonalDetail(userID, year);
            rs.first();
            list.add(rs.getString(1));
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
        } catch (SQLException ex) {
            list.add("error");
            list.add("error");
        }finally{
            return list;
        }
    }

}

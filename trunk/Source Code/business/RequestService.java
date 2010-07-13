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
import java.util.Calendar;
import javax.sql.RowSet;
import java.util.Date;

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
        RowSet rs = da.viewSubmittedLeaves(superiorID, currentYear);
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

    /**
     * check if status and return: <P> -1 : Approved <P> -2 : Rejected <P> 1 : Canceled <P> 2: Cancel-Rejected
     */
    public int checkUpdateLeaveStatus(int leaveID){
        return da.checkUpdateLeaveStatus(leaveID);
    }

    /**
     * add new user and return: 1 if ok
     */
    public int createUser(String username, String password, String fullname, int joinYear, int superiorID, int position){
        return da.createUser( username, md5String(password), fullname, joinYear, superiorID, position);
    }

    /**
     * add new holiday and return: 1 if ok
     */
    public int createNewHoliday(Date date, String name){
        return da.createNewHoliday( date, name);
    }

    /**
     * delete existing holiday and return: 1 if ok
     */
    public int removeHoliday(Date date){
        return da.removeHoliday(date);
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
}
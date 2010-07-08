/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author uSeR
 */
public class LeaveService {
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

    // encrypt plain string to md5 string
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

    // return userid equivalent to username and password
    public int loginProcess(String username, String password){
        password = md5String(password);
        return da.checkLogin(username, password);

    }

    // apply new leave
    public boolean applyLeave(int userID, Date dateStart,Date dateEnd,String reason, String communication, String subject ){
        if(da.createNewLeave(userID,dateStart,dateEnd,reason,communication,subject)>0)
            return true;
        else
            return false;
    }

    // cancel/withdraw leave
    public boolean removeLeave(int leaveID){
        if(da.removeLeave(leaveID)>0)
            return true;
        else
            return false;
    }

    // get join year of userID
    public int viewJoinYear(int userID){
        return da.viewJoinYear(userID);
    }
}

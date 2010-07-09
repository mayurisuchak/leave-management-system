/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 *
 * @author uSeR
 */
public class BusinessProcessing {
    private static BusinessProcessing businessProcessing = new BusinessProcessing();
    private int userID;
    private int username;
    private boolean superior;
    private LeaveService leaveService;
    private LogService logService;
    private ReportService reportService;
    private RequestService requestService;
    //private GUIManager guiMan;

    private BusinessProcessing(){
        leaveService = new LeaveService();
        logService = new LogService();
        reportService = new ReportService();
        requestService = new RequestService();
        //guiMan = GUIManager.getInstance();
    }

    public static BusinessProcessing getInstance(){
        return businessProcessing;
    }

    // login processing
    public void loginProcess(String username, String password){
        userID = leaveService.loginProcess(username, password);
        if (getUserID() > -1) {
            System.out.println("ok");
            if(requestService.checkSuperior(userID)){
                superior = true;
            }else{
                superior = false;
            }
            //guiMan.showLeaveManage();

        }else{
            System.out.println("fail");
            // guiMa.showMessage("Cannot login");
        }
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return the username
     */
    public int getUsername() {
        return username;
    }

    /**
     * @return the superior
     */
    public boolean isSuperior() {
        return superior;
    }

    /*********************************************************************************************
     *
     *  Tasks involve Leave Service
     *
     *********************************************************************************************/
    // change password
    public void changePassword(String newPassword, String reNewPassword, String oldPassword){
        if(newPassword.compareTo(reNewPassword) == 0){
            if(leaveService.changePassword(userID, oldPassword, newPassword)){
                logService.createLog(userID, LogService.LOG_ACTION_PASSWORD_CHANGE);
                //guiMan.showMessage("change successfull");
                //guiMan.showLeaveManage();
            }else{
                //guiMan.showMessage("old password doesn't match");
            }

        }
        else{
            // guiMan.showMessage("2 new password don't match");
        }
    }

    // apply new leave
    public void applyLeave(int userID, String dateStartStr, String dateEndStr, String reason, String communication, String subject ){
        try {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
            Date dateStart = df.parse(dateStartStr);
            Date dateEnd = df.parse(dateEndStr);
            if (leaveService.applyLeave(userID, dateStart, dateEnd, reason, communication, subject)) {
                logService.createLog(userID, LogService.LOG_ACTION_LEAVE_APPLICATION, leaveService.getNewlyLeave(userID));
                //guiMan.showMessage("Apply new leave successfully");
                //guiMan.showLeaveManage();
            } else {
                //guiMan.showMessage("Failed to apply new leave!");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    // withdraw/request cancel a leave
    public void removeLeave(int leaveID){
        if(leaveService.removeLeave(leaveID)){
            int check = leaveService.checkApprove(leaveID);
            if(check==1)
                logService.createLog(userID, LogService.LOG_ACTION_WITHDRAWAL,leaveID);
            if(check==-1)
                logService.createLog(userID, LogService.LOG_ACTION_CANCELATION_REQUEST,leaveID);
            //refresh table
        }else{
            //guiMan.showMessage("Failed to withdraw/canceled leave.");
        }
    }

    // get content in year combo box
    public String [] getYearList(int userID){
        ArrayList<String> list = new ArrayList<String>();
        int joinYear = leaveService.viewJoinYear(userID);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for(int i = joinYear; i <= currentYear; i++)
            list.add(Integer.toString(i));
        return (String[]) list.toArray();
    }

    // view leave history
    public DataObject viewLeaves(int year){
        return leaveService.viewLeaves(userID, year);
    }

    /*********************************************************************************************
     *
     *  Tasks involve Request Service
     *
     *********************************************************************************************/

    // view submitted leaves
    public DataObject viewSubmittedLeaves(){
        return requestService.viewSubmittedLeave(userID);
    }

    // approve/reject leave/request
    public void updateLeaveStatus(int leaveID, boolean allowance){
        if(requestService.updateLeaveStatus(leaveID, allowance))
        {
            int check = leaveService.checkApprove(leaveID);
            if(check==1)
                if(allowance)
                    logService.createLog(userID, LogService.LOG_ACTION_LEAVE_APPROVAL,leaveID);
                else
                    logService.createLog(userID, LogService.LOG_ACTION_LEAVE_REJECTION,leaveID);
            if(check==-1)
                 if(allowance)
                    logService.createLog(userID, LogService.LOG_ACTION_CANCEL_APPROVAL,leaveID);
                else
                    logService.createLog(userID, LogService.LOG_ACTION_CANCEL_REJECTION,leaveID);
            //guiMan.showMessage("Update successfull!");
            //guiMan.refreshTable();
        }else{
            //guiMan.showMessage("Failed to aprrove(reject) selected leave(request)!");
        }
    }

    // view list of subordinate
    public DataObject viewSubordinateList(){
        return new DataObject(requestService.viewSubordinateList(userID));
    }

     /*********************************************************************************************
     *
     *  Tasks involve Report Service
     *
     *********************************************************************************************/
     // view list
     public DataObject viewReport(int year){
         return reportService.viewReport(userID, year);
     }

     /*********************************************************************************************
     *
     *  Tasks involve Log Service
     *
     *********************************************************************************************/
     // view log of userID
     public DataObject viewLogDetailAll(int subordinateID){
         return logService.viewLogDetailAll(subordinateID);
     }

     // view log of userID form time to time
     public DataObject viewLogDetail(int subordinateID, Date dateStart, Date dateEnd){
         return logService.viewLogDetail(subordinateID,dateStart,dateEnd);
     }
    
}
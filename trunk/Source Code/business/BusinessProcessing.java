/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import GUI.GUIManager;
import data.DataObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;


/**
 *
 * @author uSeR
 */
public class BusinessProcessing {
    private static BusinessProcessing businessProcessing = new BusinessProcessing();
    private int userID;
    private String username;
    private boolean superior;
    private LeaveService leaveService;
    private LogService logService;
    private ReportService reportService;
    private RequestService requestService;
    private DataObject dataObject;
    private Thread thread;
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
    public void loginProcess(final String username,final String password){
        this.username = username;
        thread = new Thread(){
            @Override
            public void run (){

                userID = leaveService.loginProcess(username, password);
                if (getUserID() > -1) {
                    System.out.println("ok");
                    if(requestService.checkSuperior(userID)){
                        superior = true;
                        GUIManager.showScreenX(GUIManager.Screen.MainAprroverScreen, business.BusinessProcessing.getInstance().viewLeaves(1999));
                    }else{
                        superior = false;
                         GUIManager.showScreenX(GUIManager.Screen.MainScreen, business.BusinessProcessing.getInstance().viewLeaves(1999));
                    }
                    //guiMan.showLeaveManage();

                }else{
                    System.out.println("fail");
                    // guiMa.showMessage("Cannot login");
                    GUIManager.showMessageX("Wrong");
                }
            }
        } ;
        thread.start();
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
    public String getUsername() {
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
    /**
     * change password
     */
    public void changePassword(final String newPassword, final String reNewPassword, final String oldPassword){
        thread = new Thread(){
            @Override
            public void run(){
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
        };
        thread.start();
    }

    /**
     * apply new leave. Remark: date string must be in default locale of java virtual machine and in SHORT mode
     */
    public void applyLeave(final int userID, final String dateStartStr, final String dateEndStr, final String reason, final String communication, final String subject ){
       thread = new Thread(){
            @Override
           public void run(){
                try {
                    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
                    Date dateStart = df.parse(dateStartStr);
                    Date dateEnd = df.parse(dateEndStr);
                    if(dateStart.after(dateEnd))
                    {
                        //guiMan.showMessage("Date start cannot be later than date end !");
                        return;
                    }
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
       };
       thread.start();
    }

    /**
     *
     * withdraw/request cancel a leave
     */
    public void removeLeave(final int leaveID){
        thread = new Thread(){
            @Override
            public void run(){
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
        };
        thread.start();
    }

    /**
     *
     * get content in year combo box, return a vector of string
     */
    public Vector<String> getYearList(final int userID){
        try {
            final Vector<String> list = new Vector<String>();
            thread = new Thread() {

                @Override
                public void run() {
                    int joinYear = leaveService.viewJoinYear(userID);
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    for (int i = joinYear; i <= currentYear; i++) {
                        list.add(Integer.toString(i));
                    }
                }
            };
            thread.start();
            // pause main thread execution
            while (thread.isAlive())
                thread.join(1000);
            return list;
        } catch (InterruptedException ex) {
            return null;
        }
    }

    // get personal detail
    /**
     *
     * get personal information in a specific year: [Total leave Days], [Remaining leave days]
     *
     */
    public Vector<String> viewPersonalDetail(final int year){
        final Vector<String> list = new Vector<String>();
        try {
            thread = new Thread() {

                @Override
                public void run() {
                    list.addAll(leaveService.viewPersonalDetail(userID, year));

                }
            };
            thread.start();
            // pause main thread execution
            while (thread.isAlive())
                thread.join(1000);
            return list;
        } catch (InterruptedException ex) {
            return null;
        }

    }

    /**
     *
     * view leave history
     *
     */
    public DataObject viewLeaves(final int year){
        thread = new Thread(){
            @Override
            public void run(){
                dataObject = leaveService.viewLeaves(userID, year);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive())
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
        }
        return dataObject;
    }

    /*********************************************************************************************
     *
     *  Tasks involve Request Service
     *
     *********************************************************************************************/

    /**
     *
     * view submitted leaves
     */
    public DataObject viewSubmittedLeaves(){
        thread = new Thread(){
            @Override
            public void run(){
                dataObject = requestService.viewSubmittedLeave(userID);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive())
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        return dataObject;
    }

    /**
     *
     * approve(reject) leave(request) from userID <P> allowance = true to approve , allowance = false to reject
     *
     */
    public void updateLeaveStatus(final int leaveID, final boolean allowance){
        thread = new Thread(){
            @Override
            public void run(){
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
        };
        thread.start();
    }

    /**
     *
     * view list of subordinate
     */
    public DataObject viewSubordinateList(){
        thread = new Thread(){
            @Override
            public void run(){
               dataObject = new DataObject(requestService.viewSubordinateList(userID)); 
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive())
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        return dataObject;
    }

     /*********************************************************************************************
     *
     *  Tasks involve Report Service
     *
     *********************************************************************************************/
    /**
     *
     *  view report about subordinate according to year
     *
     */
     public DataObject viewReport(final int year){
         thread = new Thread(){
            @Override
             public void run(){
                dataObject = reportService.viewReport(userID, year);
             }
         };
         thread.start();
        // pause main thread execution
        while (thread.isAlive())
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        return dataObject;
     }

     /*********************************************************************************************
     *
     *  Tasks involve Log Service
     *
     *********************************************************************************************/

     /**
      *
      *  view log of userID
      *
      */
     public DataObject viewLogDetailAll(final int subordinateID){
         thread = new Thread(){
            @Override
            public void run(){
                dataObject = logService.viewLogDetailAll(subordinateID);
            }
         };
         thread.start();
        // pause main thread execution
        while (thread.isAlive())
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        return dataObject;
     }

     /**
      *
      * view log of userID form time to time
      */
     public DataObject viewLogDetail(final int subordinateID, final Date dateStart, final Date dateEnd){
         thread = new Thread(){
            @Override
            public void run(){
                dataObject = logService.viewLogDetail(subordinateID,dateStart,dateEnd);
            }
         };
         thread.start();
        // pause main thread execution
        while (thread.isAlive())
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        return dataObject;
     }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.GUIManager;
import data.DataObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private BusinessProcessing() {
        leaveService = new LeaveService();
        logService = new LogService();
        reportService = new ReportService();
        requestService = new RequestService();
        //guiMan = GUIManager.getInstance();
    }

    public static BusinessProcessing getInstance() {
        return businessProcessing;
    }

    // login processing
    public void loginProcess(final String username, final String password) {
        this.username = username;
        thread = new Thread() {

            @Override
            public void run() {

                userID = leaveService.loginProcess(username, password);
                if (getUserID() > -1) {
                    System.out.println("ok");
                    GregorianCalendar cal = new GregorianCalendar(); //Create calendar
                    int realYear = cal.get(GregorianCalendar.YEAR);
                    if (requestService.checkSuperior(userID)) {
                        superior = true;
                        GUIManager.showScreenX(GUIManager.Screen.MainAprroverScreen, viewLeaves(realYear));
                    } else {
                        superior = false;
                        GUIManager.showScreenX(GUIManager.Screen.MainScreen, viewLeaves(realYear));
                    }
                    //guiMan.showLeaveManage();

                } else {
                    System.out.println("fail");
                    // guiMa.showMessage("Cannot login");
                    GUIManager.showMessageX("Wrong");
                }
            }
        };
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
    public void changePassword(final String newPassword, final String reNewPassword, final String oldPassword) {
        thread = new Thread() {

            @Override
            public void run() {
                if (newPassword.compareTo(reNewPassword) == 0) {
                    if (leaveService.changePassword(userID, oldPassword, newPassword)) {
                        logService.createLog(userID, LogService.LOG_ACTION_PASSWORD_CHANGE);
                        //guiMan.showMessage("change successfull");
                        // GUIManager.showScreenX(GUIManager.Screen.LogScreen, dataObject);
                        GregorianCalendar cal = new GregorianCalendar(); //Create calendar

                        int realYear = cal.get(GregorianCalendar.YEAR);
                        if (isSuperior()) {

                            GUIManager.showScreenX(GUIManager.Screen.MainAprroverScreen, viewLeaves(realYear));
                        } else {
                            GUIManager.showScreenX(GUIManager.Screen.MainScreen, viewLeaves(realYear));
                        }
                    } else {
                        GUIManager.showMessageX("old password doesn't match");
                    }

                } else {
                    // guiMan.showMessage("2 new password don't match");
                }
            }
        };
        thread.start();
    }

    /**
     * apply new leave. Remark: date string must be in default locale of java virtual machine and in SHORT mode
     */
    public void applyLeave(final int userID, final String dateStartStr, final String dateEndStr, final String reason, final String communication, final String subject) {
        thread = new Thread() {

            @Override
            public void run() {
                try {
                    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
                    Date dateStart = df.parse(dateStartStr);
                    Date dateEnd = df.parse(dateEndStr);
                    Date currDate = new Date();
                    if (dateStart.after(dateEnd)) {
                        GUIManager.showMessageX("Date start cannot be later than date end !");
                        return;
                    }

                    if (currDate.after(dateStart)) {
                        GUIManager.showMessageX("Date start cannot be earlier than current date !");
                        return;
                    }
                    if (leaveService.applyLeave(userID, dateStart, dateEnd, reason, communication, subject)) {
                        logService.createLog(userID, LogService.LOG_ACTION_LEAVE_APPLICATION, leaveService.getNewlyLeave(userID));
                        // GUIManager.showMessageX("Apply new leave successfully");
                        GregorianCalendar cal = new GregorianCalendar(); //Create calendar

                        int realYear = cal.get(GregorianCalendar.YEAR);
                        if (isSuperior()) {

                            GUIManager.showScreenX(GUIManager.Screen.MainAprroverScreen, viewLeaves(realYear));
                        } else {
                            GUIManager.showScreenX(GUIManager.Screen.MainScreen, viewLeaves(realYear));
                        }

                    } else {
                        GUIManager.showMessageX("Failed to apply new leave!");
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
    public void removeLeave(final int leaveID) {
        thread = new Thread() {

            @Override
            public void run() {
                if (leaveService.removeLeave(leaveID)) {
                    int check = leaveService.checkRemoveLeave(leaveID);
                    if (check == -1) {
                        logService.createLog(userID, LogService.LOG_ACTION_WITHDRAWAL, leaveID);
                    }
                    if (check == 1) {
                        logService.createLog(userID, LogService.LOG_ACTION_CANCELATION_REQUEST, leaveID);
                    }
                    //refresh table
                    GregorianCalendar cal = new GregorianCalendar(); //Create calendar

                    int realYear = cal.get(GregorianCalendar.YEAR);
                    GUIManager.table.clearSelection();
                    GUIManager.table.setModel(viewLeaves(realYear));
                } else {
                    GUIManager.showMessageX("Failed to withdraw/canceled leave.");
                }
            }
        };
        thread.start();
    }

    /**
     *
     * get content in year combo box, return a vector of string
     */
    public Vector<String> getYearList(final int userID) {
        try {
            final Vector<String> list = new Vector<String>();
            thread = new Thread() {

                @Override
                public void run() {
                    int joinYear = leaveService.viewJoinYear(userID);
                    if (joinYear > 0) {
                        Calendar calendar = Calendar.getInstance();
                        int currentYear = calendar.get(Calendar.YEAR);
                        for (int i = joinYear; i <= currentYear; i++) {
                            list.add(Integer.toString(i));
                        }
                    } else {
                        list.add("error");
                    }
                }
            };
            thread.start();
            // pause main thread execution
            while (thread.isAlive()) {
                thread.join(1000);
            }
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
    public Vector<String> viewPersonalDetail(final int year) {
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
            while (thread.isAlive()) {
                thread.join(1000);
            }
            return list;
        } catch (InterruptedException ex) {
            return list;
        }

    }

    /**
     *
     * get personal information in a specific year: [Name], [Code], [Total leave Days], [Remaining leave days]
     *
     */
    public Vector<String> viewSubordinateDetail(final int userID,final int year) {
        final Vector<String> list = new Vector<String>();
        try {
            thread = new Thread() {

                @Override
                public void run() {
                    list.addAll(leaveService.viewSubordinateDetail(userID, year));

                }
            };
            thread.start();
            // pause main thread execution
            while (thread.isAlive()) {
                thread.join(1000);
            }
            return list;
        } catch (InterruptedException ex) {
            return list;
        }

    }

    /**
     *
     * view leave history
     *
     */
    public DataObject viewLeaves(final int year) {
        thread = new Thread() {

            @Override
            public void run() {
                dataObject = leaveService.viewLeaves(userID, year);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return dataObject;
    }

    /**
     * view detail of leave: 0->[Subject], 1->[Reason], 2->DateStart, 3->DateEnd, 4->Communication, 5->[Status]
     */
    public DataObject viewDetailOfLeave(final int leaveID) {
         thread = new Thread() {

            @Override
            public void run() {
                dataObject = leaveService.viewDetailOfLeave(leaveID);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
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
    public DataObject viewSubmittedLeaves() {
        thread = new Thread() {

            @Override
            public void run() {
                dataObject = requestService.viewSubmittedLeave(userID);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return dataObject;
    }

    /**
     *
     * approve(reject) leave(request) from userID <P> allowance = true to approve , allowance = false to reject
     *
     */
    public void updateLeaveStatus(final int leaveID, final boolean allowance) {
        thread = new Thread() {

            @Override
            public void run() {
                if (requestService.updateLeaveStatus(leaveID, allowance)) {
                    int check = requestService.checkUpdateLeaveStatus(leaveID);
                    if (check == -1) {
                        logService.createLog(userID, LogService.LOG_ACTION_LEAVE_APPROVAL, leaveID);
                    } else if (check == -2) {
                        logService.createLog(userID, LogService.LOG_ACTION_LEAVE_REJECTION, leaveID);
                    } else if (check == 1) {
                        logService.createLog(userID, LogService.LOG_ACTION_CANCEL_APPROVAL, leaveID);
                    } else if (check == 2) {
                        logService.createLog(userID, LogService.LOG_ACTION_CANCEL_REJECTION, leaveID);
                    }
                    GUIManager.showMessageX("Update successfull!");
                    
                } else {
                   GUIManager.showMessageX("Failed to aprrove(reject) selected leave(request)!");
                }
            }
        };
        thread.start();
    }

    /**
     *
     * view list of subordinate
     */
    public DataObject viewSubordinateList() {
        thread = new Thread() {

            @Override
            public void run() {
                dataObject = new DataObject(requestService.viewSubordinateList(userID));
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
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
    public DataObject viewReport(final int year) {
        thread = new Thread() {

            @Override
            public void run() {
                dataObject = reportService.viewReport(userID, year);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
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
    public DataObject viewLogDetailAll(final int subordinateID) {
        thread = new Thread() {

            @Override
            public void run() {
                dataObject = logService.viewLogDetailAll(subordinateID);
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return dataObject;
    }

    /**
     *
     * view log of userID form time to time
     */
    public DataObject viewLogDetail(final int subordinateID, final String dateStartStr, final String dateEndStr) {

        thread = new Thread() {

            @Override
            public void run() {
                try {
                    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
                    Date dateStart = df.parse(dateStartStr);
                    Date dateEnd = df.parse(dateEndStr);   
                    dataObject = logService.viewLogDetail(subordinateID, dateStart, dateEnd);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return dataObject;
    }

    /**
     * add new user and return: 1 if ok
     */
    public void createUser(final String username, final String password, final String fullname, final int joinYear, final int superiorID, final int position){
        thread = new Thread() {

            @Override
            public void run() {

               if( requestService.createUser( username, password, fullname, joinYear, superiorID, position)>0){
                    //Create ok
                }else{
                    //Create fail

                }
            }
        };
        thread.start();

    }

    /**
     * add new holiday and return: 1 if ok
     */
    public void createNewHoliday(final Date date, final String name){
        thread = new Thread() {

            @Override
            public void run() {

                if(requestService.createNewHoliday(date, name)>0){
                    //create ok
                }else{
                    // create fail
                }
            }
        };
        thread.start();

    }

    /**
     * delete existing holiday and return: 1 if ok
     */
    public void removeHoliday(final Date date){

         thread = new Thread() {

            @Override
            public void run() {

                if(requestService.removeHoliday(date)>0){
                    //delete ok
                }else{
                    //create fail
                }
            }
        };
        thread.start();
    }

    /**
     *
     * return list of superior: vector which one elem is: array of object: [1] -> id, [2]-> Fullname + Position
     */
    public Vector<Object[]> getSuperiorList() {
        final Vector<Object[]> list = new Vector<Object[]>();
        try {

            thread = new Thread() {

                @Override
                public void run() {
                    list.addAll( requestService.getSuperiorList());
                }
            };
            thread.start();
            // pause main thread execution
            while (thread.isAlive()) {
                thread.join(1000);
            }
            return list;
        } catch (InterruptedException ex) {
            return list;
        }
    }

    /**
     *
     * return list of superior: vector which one elem is: array of object: [1] -> positionid, [2]-> position name
     */
    public Vector<Object[]> getPostionList() {
        final Vector<Object[]> list = new Vector<Object[]>();
        try {

            thread = new Thread() {

                @Override
                public void run() {
                    list.addAll( requestService.getPositionList());
                }
            };
            thread.start();
            // pause main thread execution
            while (thread.isAlive()) {
                thread.join(1000);
            }
            return list;
        } catch (InterruptedException ex) {
            return list;
        }
    }

     /**
     *
     * view log of userID form time to time
     */
    public String getUsername(final int userID) {
        final StringBuilder name = new StringBuilder("");
        thread = new Thread() {

            @Override
            public void run() {
                name.append(logService.getUsername(userID));
            }
        };
        thread.start();
        // pause main thread execution
        while (thread.isAlive()) {
            try {
                thread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return name.toString();
    }
}

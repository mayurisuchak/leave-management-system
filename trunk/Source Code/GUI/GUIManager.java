/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import data.DataObject;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author KOKICHI
 */
public class GUIManager {

    public static enum Screen {

        LoginScreen, MainScreen, AddNewScreen,ChangePassScreen,
        LogScreen, ListEmployeeScreen, MainAprroverScreen,
        ManagerLeaveScreen, ReportScreen, ViewLeaveScreen
    };
    public static JFrame currentScreen = null;
    public static String strDate = null;
    public static JTable table = null;
   

    public static void showScreenX(Screen type,DataObject data) {
        switch (type) {
            case LoginScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new LoginScreen();
                break;
            case MainScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new MainScreen(data);
                break;
            case AddNewScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new AddNewScreen();
                break;
            case LogScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new LogScreen();
                break;
            case ListEmployeeScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new ListEmployeeScreen();
                break;
            case MainAprroverScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new MainAprroverScreen(data);
                break;
            case ManagerLeaveScreen:

                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new ManagerLeaveScreen();
                break;
            case ReportScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new ReportScreen();
                break;
            case ViewLeaveScreen:
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                currentScreen = new ViewLeaveScreen();
                break;
            case ChangePassScreen:
                if(currentScreen != null){
                    currentScreen.dispose();
                }
                currentScreen = new ChangePassScreen();
                break;
            default:
                currentScreen = null;
                break;
        }
    }

    public static JFrame getCurrentScreen() {
        return currentScreen;
    }

    public static JTable getCurrentTable(){
        return table;
    }

    public static void showMessageX(String message) {
        JOptionPane.showMessageDialog(currentScreen, message);
    }

    

    public static void main(String[] avg) {
        showScreenX(Screen.LoginScreen,null);
       // showCalendar();
        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.BusinessProcessing;
import data.DataObject;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 *
 * @author KOKICHI
 */
public class MainAprroverScreen extends JFrame {

    private static int MY_HEIGHT = 412;
    private static int MY_WIDTH = 620;
    private JLabel topPanel;
    private JLabel botPanel;
    private JPanel panel;
    private JLabel lbCalendar;
    private JLabel lbChangePass;
    private JLabel lbHelp;
    private JLabel lbLogin;
    private JLabel lbUsername;
    private JLabel lbSignout;
    private JLabel lbTotalDay;
    private JLabel lbRemainDay;
    private JLabel lbYear;
    private JTable table;
    private JLabel lbApplyLeave;
    private JLabel lbWithdraw;
    private JLabel lbRqCancel;
    private JLabel lbManagerLeave;
    private Font mFont;
    private Color mColor;
    private JLabel lbSpectator;
    //  private JLabel lbTable;
    private JLabel lbViewReport;
    private JComboBox cbYear;
    private JLabel lbSmallSpector;
    private JScrollPane spTable;
    private JLabel lbRegister;
    //  private JLabel lpanel;
    DataObject data;
    private String strTotalDay;
    private String strRemainDay;

    public MainAprroverScreen(DataObject data) {
        // System.out.print("dadwa");
        this.data = data;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final ImageIcon imgBG = new ImageIcon("resource/bg2.jpg");
        init();

        panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                //  Dispaly image at at full size
                g.drawImage(imgBG.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        getContentPane().add(panel);


        setBounds(300, 200, MY_WIDTH, MY_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Leave Management System");

        //set model

    }

    private void init() {
        setResizable(false);
        mFont = new Font("Tahoma", Font.BOLD, 10);
        mColor = new Color(255, 255, 255);
        String strUrl = "resource/buttons/";

        ImageIcon imgTop = new ImageIcon(strUrl + "top.png");
        ImageIcon imgBot = new ImageIcon(strUrl + "bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl + "line.png");
        ImageIcon imgLpanel = new ImageIcon(strUrl + "panelLeft.png");
        ImageIcon imgBtApply = new ImageIcon(strUrl + "btn_applyLeave.png");
        ImageIcon imgBtCancel = new ImageIcon(strUrl + "btn_requestCancel.png");
        ImageIcon imgTabel = new ImageIcon(strUrl + "mainTable.png");
        ImageIcon imgManagerLeave = new ImageIcon(strUrl + "bnt_managerLeave.png");

        BusinessProcessing bp = business.BusinessProcessing.getInstance();
        lbCalendar = new JLabel("Calendar");
        lbChangePass = new JLabel("Change Password");
        lbHelp = new JLabel("Help");
        lbLogin = new JLabel("Login as ");
        lbSignout = new JLabel("Sign Out");
        lbTotalDay = new JLabel("Total Leave Day ");
        lbRemainDay = new JLabel("Remain Leave Day ");
        lbYear = new JLabel("Year ");
        lbViewReport = new JLabel("View Report");
        lbUsername = new JLabel(bp.getUsername());
        lbUsername.setFont(new Font("tahoma", Font.ITALIC | Font.BOLD, 11));
        lbUsername.setForeground(new Color(0, 0, 255));

        cbYear = new JComboBox(bp.getYearList(bp.getUserID()));
        cbYear.setSelectedIndex(cbYear.getItemCount() - 1);

        strTotalDay = lbTotalDay.getText();
        strRemainDay = lbRemainDay.getText();

        table = new JTable(data);
        spTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbApplyLeave = new JLabel(imgBtApply);
        lbRqCancel = new JLabel(imgBtCancel);
        // lbTable = new JLabel(imgTabel);
        lbSmallSpector = new JLabel(imgSpectator);
        lbManagerLeave = new JLabel(imgManagerLeave);
        // lpanel = new JLabel(imgLpanel);

        // lpanel.setBounds(5, 80, imgLpanel.getIconWidth(), imgLpanel.getIconHeight()-50);
        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 360, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbCalendar.setBounds(5, 50, 50, 14);
        lbChangePass.setBounds(lbCalendar.getX() + 60, lbCalendar.getY(), 100, 14);
        lbHelp.setBounds(lbChangePass.getX() + 110, lbCalendar.getY(), 50, 14);

        lbLogin.setBounds(3 * MY_WIDTH / 4, lbCalendar.getY(), 100, 14);
        lbUsername.setBounds(lbLogin.getX() + 50, lbCalendar.getY(), 100, 14);
        lbSignout.setBounds(MY_WIDTH - 50, lbCalendar.getY(), 100, 14);
        lbViewReport.setBounds(10, 105, 100, 14);
        lbSmallSpector.setBounds(6, lbViewReport.getY() + 20, 155, imgSpectator.getIconHeight());
        lbTotalDay.setBounds(lbViewReport.getX(), lbSmallSpector.getY() + 5, 150, 14);
        lbRemainDay.setBounds(lbTotalDay.getX(), lbTotalDay.getY() + 20, 150, 14);
        lbYear.setBounds(lbTotalDay.getX(), lbRemainDay.getY() + 20, 100, 14);
        cbYear.setBounds(lbYear.getX() + 40, lbYear.getY(), 50, 20);
        spTable.setBounds(lbHelp.getX(), 85, 430, 200);
        // lbTable.setBounds(table.getX(), table.getY(), table.getWidth(), table.getHeight());
        lbApplyLeave.setBounds(spTable.getX(), spTable.getY() + spTable.getHeight() + 10, imgBtApply.getIconWidth(), imgBtCancel.getIconHeight());
        lbRqCancel.setBounds(lbApplyLeave.getX() + 90, lbApplyLeave.getY() - 30, imgBtCancel.getIconWidth(), imgBtCancel.getIconWidth());
        lbManagerLeave.setBounds(MY_WIDTH - imgManagerLeave.getIconWidth() - 10, lbApplyLeave.getY(), imgManagerLeave.getIconWidth(), imgManagerLeave.getIconHeight());
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        lbHelp.setCursor(cursor);
        lbCalendar.setCursor(cursor);
        lbChangePass.setCursor(cursor);
        lbSignout.setCursor(cursor);
        lbApplyLeave.setCursor(cursor);
        lbRqCancel.setCursor(cursor);
        lbViewReport.setCursor(cursor);
        lbManagerLeave.setCursor(cursor);

        //top and bot panel
        add(topPanel);
        add(botPanel);
        //table and table background
        add(spTable);
        // add(lbTable);
        //add(lpanel);

        //add top function
        add(lbSpectator);
        add(lbCalendar);
        add(lbHelp);
        add(lbLogin);
        add(lbSignout);
        add(lbChangePass);
        add(lbUsername);

        // add left panel
        add(lbRemainDay);
        add(lbTotalDay);
        add(cbYear);
        add(lbSmallSpector);
        add(lbViewReport);

        add(lbYear);

        //add buttons
        add(lbApplyLeave);
        add(lbRqCancel);
        add(lbManagerLeave);
        // add(lbWithdraw);

        setFontAndColor(mFont, mColor);

        //init data
        initData();

        // add action

        cbYear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                BusinessProcessing bp = business.BusinessProcessing.getInstance();
                Vector vt = bp.viewPersonalDetail(Integer.parseInt(cbYear.getSelectedItem().toString()));
                lbTotalDay.setText(strTotalDay + vt.get(0));
                lbRemainDay.setText(strRemainDay + vt.get(1));
                data = bp.viewLeaves(Integer.parseInt(cbYear.getSelectedItem().toString()));
                table.setModel(data);

            }
        });

        lbCalendar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                CalendarProgram cp = CalendarProgram.getInstance();
            }
        });

        lbChangePass.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                GUIManager.showScreenX(GUIManager.Screen.ChangePassScreen, null);
            }
        });

        lbApplyLeave.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                GUIManager.showScreenX(GUIManager.Screen.AddNewScreen, null);
            }
        });

        lbSignout.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                GUIManager.showScreenX(GUIManager.Screen.LoginScreen, null);
            }
        });

        lbRqCancel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                BusinessProcessing bp = business.BusinessProcessing.getInstance();
                //table
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int t = Integer.parseInt(String.valueOf(table.getValueAt(row, 0)));
                bp.removeLeave(t);

            }
        });

        lbManagerLeave.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                BusinessProcessing bp = business.BusinessProcessing.getInstance();
                GUIManager.showScreenX(GUIManager.Screen.ManagerLeaveScreen, bp.viewSubmittedLeaves());
            }
        });

        lbViewReport.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                BusinessProcessing bp = business.BusinessProcessing.getInstance();
                GUIManager.showScreenX(GUIManager.Screen.ReportScreen, bp.viewReport(Integer.parseInt(cbYear.getSelectedItem().toString())));
            }
        });

        lbHelp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!java.awt.Desktop.isDesktopSupported()) {

                    System.err.println("Desktop is not supported (fatal)");
                    System.exit(1);
                }
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

                if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {

                    System.err.println("Desktop doesn't support the browse action (fatal)");
                    System.exit(1);
                }
                try {
                    java.net.URI uri = new java.net.URI("help.html");
                    desktop.browse(uri);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    private void setFontAndColor(Font f, Color c) {

        lbViewReport.setFont(f);
        lbViewReport.setForeground(c);
        lbCalendar.setFont(f);
        lbCalendar.setForeground(c);

        lbChangePass.setFont(f);
        lbChangePass.setForeground(c);

        lbHelp.setFont(f);
        lbHelp.setForeground(c);

        lbLogin.setFont(f);
        lbLogin.setForeground(c);

        lbSignout.setFont(f);
        lbSignout.setForeground(c);

        lbTotalDay.setFont(f);
        lbTotalDay.setForeground(c);

        lbRemainDay.setFont(f);
        lbRemainDay.setForeground(c);

        lbYear.setFont(f);
        lbYear.setForeground(c);
    }

    private void initData() {
        BusinessProcessing bp = business.BusinessProcessing.getInstance();
        Vector vt = bp.viewPersonalDetail(Integer.parseInt(cbYear.getSelectedItem().toString()));
        lbTotalDay.setText(strTotalDay + vt.get(0));
        lbRemainDay.setText(strRemainDay + vt.get(1));
        GUIManager.table = table;

        if (bp.getUserID() == 1) {

            lbRegister = new JLabel("Register");
            lbRegister.setBounds(lbHelp.getX() + 50, lbCalendar.getY(), 50, 14);

            lbRegister.setFont(mFont);
            lbRegister.setForeground(mColor);
            lbRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(lbRegister);


            //add action
            lbRegister.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    GUIManager.showScreenX(GUIManager.Screen.RegisterScreen, null);
                }
            });
        }

    }

    public static void main(String[] avg) {
        new MainAprroverScreen(null);
    }
}

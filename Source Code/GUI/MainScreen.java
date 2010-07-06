/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 *
 * @author KOKICHI
 */
public class MainScreen extends JFrame {

    private static int MY_HEIGHT = 385;
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
    private Font mFont;
    private Color mColor;
    private JLabel lbSpectator;
    //  private JLabel lpanel;

    public MainScreen() {
        System.out.print("dadwa");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final ImageIcon imgBG = new ImageIcon("src/resource/bg.jpg");
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
    }

    private void init() {
        setResizable(false);
        mFont = new Font("Tahoma", Font.BOLD, 10);
        mColor = new Color(255, 255, 255);
        String strUrl = "src/resource/buttons/";

        ImageIcon imgTop = new ImageIcon(strUrl + "top.png");
        ImageIcon imgBot = new ImageIcon(strUrl + "bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl + "line.png");
        ImageIcon imgLpanel = new ImageIcon(strUrl + "panelLeft.png");
        ImageIcon imgBtApply = new ImageIcon(strUrl + "btn_applyLeave.png");
        ImageIcon imgBtCancel = new ImageIcon(strUrl + "btn_requestCan.png");

        lbCalendar = new JLabel("Calendar");
        lbChangePass = new JLabel("Change Password");
        lbHelp = new JLabel("Help");
        lbLogin = new JLabel("Login as ");
        lbSignout = new JLabel("Sign Out");
        lbTotalDay = new JLabel("Total Leave Day ");
        lbRemainDay = new JLabel("Remain Leave Day ");
        lbYear = new JLabel("Year ");
        lbUsername = new JLabel("hung");
        lbUsername.setFont(new Font("tahoma",Font.ITALIC|Font.BOLD,11));
        lbUsername.setForeground(new Color(0,0,255));
        table = new JTable();

        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbApplyLeave = new JLabel(imgBtApply);
        lbRqCancel = new JLabel(imgBtCancel);
        // lpanel = new JLabel(imgLpanel);

        // lpanel.setBounds(5, 80, imgLpanel.getIconWidth(), imgLpanel.getIconHeight()-50);
        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 335, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbCalendar.setBounds(5, 50, 100, 14);
        lbChangePass.setBounds(lbCalendar.getX() + 60, lbCalendar.getY(), 100, 14);
        lbHelp.setBounds(lbChangePass.getX() + 110, lbCalendar.getY(), 50, 14);
        lbLogin.setBounds(3 * MY_WIDTH / 4, lbCalendar.getY(), 100, 14);
        lbUsername.setBounds(lbLogin.getX()+50, lbCalendar.getY(), 100 , 14);
        lbSignout.setBounds(MY_WIDTH-50, lbCalendar.getY(), 100, 14);
        lbTotalDay.setBounds(10, 100, 100, 14);
        lbRemainDay.setBounds(lbTotalDay.getX(), lbTotalDay.getY() + 20, 100, 14);
        lbYear.setBounds(lbTotalDay.getX(), lbRemainDay.getY() + 20, 100, 14);
        table.setBounds(MY_WIDTH / 4 + 50, 80, 400, 200);
        lbApplyLeave.setBounds(table.getX(), table.getY()+ table.getHeight() + 10, imgBtApply.getIconWidth(), imgBtCancel.getIconHeight());
        lbRqCancel.setBounds(lbApplyLeave.getX() + 90, lbApplyLeave.getY()-30, imgBtCancel.getIconWidth(), imgBtCancel.getIconWidth());

        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        lbHelp.setCursor(cursor);
        lbCalendar.setCursor(cursor);
        lbChangePass.setCursor(cursor);
        lbSignout.setCursor(cursor);

        add(topPanel);
        add(botPanel);
        add(table);
        //add(lpanel);
        add(lbSpectator);
        add(lbCalendar);
        add(lbApplyLeave);
        add(lbChangePass);
        add(lbHelp);
        add(lbLogin);
        add(lbRemainDay);
        add(lbRqCancel);
        add(lbSignout);
        add(lbSpectator);
        add(lbTotalDay);
        add(lbUsername);
        // add(lbWithdraw);
        add(lbYear);
        setFontAndColor(mFont, mColor);

    }

    private void setFontAndColor(Font f, Color c) {
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

    public static void main(String[] avg) {
        //  new LoginScreen();
        new MainScreen();
    }
}

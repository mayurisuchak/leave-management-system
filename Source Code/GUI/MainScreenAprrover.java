/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author KOKICHI
 */
public class MainScreenAprrover extends JFrame {

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
    private JLabel lbManagerLeave;
    private Font mFont;
    private Color mColor;
    private JLabel lbSpectator;
    private JLabel lbTable;
    private JLabel lbViewReport;
    private JComboBox cbYear;
    private JLabel lbSmallSpector;
    //  private JLabel lpanel;

    public MainScreenAprrover() {
        // System.out.print("dadwa");
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
        ImageIcon imgTabel = new ImageIcon(strUrl + "mainTable.png");
        ImageIcon imgManagerLeave = new ImageIcon(strUrl+"bnt_managerLeave.png");

       
        lbCalendar = new JLabel("Calendar");
        lbChangePass = new JLabel("Change Password");
        lbHelp = new JLabel("Help");
        lbLogin = new JLabel("Login as ");
        lbSignout = new JLabel("Sign Out");
        lbTotalDay = new JLabel("Total Leave Day ");
        lbRemainDay = new JLabel("Remain Leave Day ");
        lbYear = new JLabel("Year ");
        lbViewReport = new JLabel("View Report");
        lbUsername = new JLabel("hung");
        lbUsername.setFont(new Font("tahoma", Font.ITALIC | Font.BOLD, 11));
        lbUsername.setForeground(new Color(0, 0, 255));
        cbYear = new JComboBox();
        table = new JTable() {

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // We want renderer component to be transparent so background image is visible
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        table.setOpaque(false);

        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbApplyLeave = new JLabel(imgBtApply);
        lbRqCancel = new JLabel(imgBtCancel);
        lbTable = new JLabel(imgTabel);
        lbSmallSpector = new JLabel(imgSpectator);
        lbManagerLeave = new JLabel(imgManagerLeave);
        // lpanel = new JLabel(imgLpanel);

        // lpanel.setBounds(5, 80, imgLpanel.getIconWidth(), imgLpanel.getIconHeight()-50);
        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 335, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbCalendar.setBounds(5, 50, 100, 14);
        lbChangePass.setBounds(lbCalendar.getX() + 60, lbCalendar.getY(), 100, 14);
        lbHelp.setBounds(lbChangePass.getX() + 110, lbCalendar.getY(), 50, 14);
        lbLogin.setBounds(3 * MY_WIDTH / 4, lbCalendar.getY(), 100, 14);
        lbUsername.setBounds(lbLogin.getX() + 50, lbCalendar.getY(), 100, 14);
        lbSignout.setBounds(MY_WIDTH - 50, lbCalendar.getY(), 100, 14);
        lbViewReport.setBounds(10, 100, 100, 14);
        lbSmallSpector.setBounds(5,lbViewReport.getY()+20 ,100 ,imgSpectator.getIconHeight() );
        lbTotalDay.setBounds(lbViewReport.getX(), lbSmallSpector.getY()+5, 100, 14);
        lbRemainDay.setBounds(lbTotalDay.getX(), lbTotalDay.getY() + 20, 100, 14);
        lbYear.setBounds(lbTotalDay.getX(), lbRemainDay.getY() + 20, 100, 14);
        cbYear.setBounds(lbYear.getX() + 40, lbYear.getY(), 50, 20);
        table.setBounds(MY_WIDTH / 4 + 50, 80, 400, 200);
        lbTable.setBounds(table.getX(), table.getY(), table.getWidth(), table.getHeight());
        lbApplyLeave.setBounds(table.getX(), table.getY() + table.getHeight() + 10, imgBtApply.getIconWidth(), imgBtCancel.getIconHeight());
        lbRqCancel.setBounds(lbApplyLeave.getX() + 90, lbApplyLeave.getY() - 30, imgBtCancel.getIconWidth(), imgBtCancel.getIconWidth());
        lbManagerLeave.setBounds(MY_WIDTH-imgManagerLeave.getIconWidth()-10, lbApplyLeave.getY(), imgManagerLeave.getIconWidth() , imgManagerLeave.getIconHeight());
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
        add(table);
        add(lbTable);
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

    public static void main(String[] avg) {
        //  new LoginScreen();
        new MainScreenAprrover();
    }
}

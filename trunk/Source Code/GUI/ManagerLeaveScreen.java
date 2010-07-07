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

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.xml.bind.JAXB;

/**
 *
 * @author KOKICHI
 */
public class ManagerLeaveScreen extends JFrame {

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
    private JTable table;
    private JLabel lbApprove;
    private JLabel lbReject;
    private JLabel lbBack;
    private Font mFont;
    private Color mColor;
    private JLabel lbSpectator;
    private JLabel lbTable;
    private JLabel lbViewReport;
    private JLabel lbSmallSpector;
    private JLabel lbList;
    private JLabel lbFullName;
    private JLabel lbCode;
    //  private JLabel lpanel;

    public ManagerLeaveScreen() {
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
        ImageIcon imgBtApprove = new ImageIcon(strUrl + "btn_approve.png");
        ImageIcon imgBtReject = new ImageIcon(strUrl + "btn_reject.png");
        ImageIcon imgTabel = new ImageIcon(strUrl + "mainTable.png");
        ImageIcon imagBack = new ImageIcon(strUrl+"btn_back2Detail.png");


        lbCalendar = new JLabel("Calendar");
        lbChangePass = new JLabel("Change Password");
        lbHelp = new JLabel("Help");
        lbLogin = new JLabel("Login as ");
        lbSignout = new JLabel("Sign Out");
        lbTotalDay = new JLabel("Total Leave Day ");
        lbRemainDay = new JLabel("Remain Leave Day ");
        lbFullName = new JLabel("Name ");
        lbCode = new JLabel("Code ");

        lbList = new JLabel("View List Employee");
        lbViewReport = new JLabel("View Report");
        lbUsername = new JLabel("hung");
        lbUsername.setFont(new Font("tahoma", Font.ITALIC | Font.BOLD, 11));
        lbUsername.setForeground(new Color(0, 0, 255));

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
        lbApprove = new JLabel(imgBtApprove);
        lbReject = new JLabel(imgBtReject);
        lbTable = new JLabel(imgTabel);
        lbSmallSpector = new JLabel(imgSpectator);
        lbBack = new JLabel(imagBack);
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
        lbList.setBounds(lbViewReport.getX(),lbViewReport.getY()+20 ,100 ,14 );
        lbSmallSpector.setBounds(5,lbList.getY()+20 ,100 ,imgSpectator.getIconHeight() );
        lbFullName.setBounds(lbViewReport.getX(), lbSmallSpector.getY()+5, 100, 14);
        lbCode.setBounds(lbViewReport.getX(), lbFullName.getY()+20, 100, 14);
        lbTotalDay.setBounds(lbViewReport.getX(), lbCode.getY()+20, 100, 14);
        lbRemainDay.setBounds(lbViewReport.getX(), lbTotalDay.getY() + 20, 100, 14);

        table.setBounds(MY_WIDTH / 4 + 50, 80, 400, 200);
        lbTable.setBounds(table.getX(), table.getY(), table.getWidth(), table.getHeight());
        lbApprove.setBounds(table.getX(), table.getY() + table.getHeight() + 10, imgBtApprove.getIconWidth(), imgBtApprove.getIconHeight());
        lbReject.setBounds(lbApprove.getX() + 90, lbApprove.getY() - 25, imgBtReject.getIconWidth(), imgBtReject.getIconWidth());
        lbBack.setBounds(MY_WIDTH-imagBack.getIconWidth()-10, lbApprove.getY(), imagBack.getIconWidth() , imagBack.getIconHeight());
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        lbHelp.setCursor(cursor);
        lbCalendar.setCursor(cursor);
        lbChangePass.setCursor(cursor);
        lbSignout.setCursor(cursor);
        lbApprove.setCursor(cursor);
        lbReject.setCursor(cursor);
        lbViewReport.setCursor(cursor);
        lbBack.setCursor(cursor);
        lbList.setCursor(cursor);

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
        add(lbList);
        add(lbFullName);
        add(lbCode);
        add(lbRemainDay);
        add(lbTotalDay);

        add(lbSmallSpector);
        add(lbViewReport);



        //add buttons
        add(lbApprove);
        add(lbReject);
        add(lbBack);
        // add(lbWithdraw);

        setFontAndColor(mFont, mColor);

    }

    private void setFontAndColor(Font f, Color c) {

        lbList.setFont(f);
        lbList.setForeground(c);
        lbFullName.setFont(f);
        lbFullName.setForeground(c);
        lbCode.setFont(f);
        lbCode.setForeground(c);
   
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


    }

    public static void main(String[] avg) {
        //  new LoginScreen();
        new ManagerLeaveScreen();
    }
}

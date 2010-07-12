/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.BusinessProcessing;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author KOKICHI
 */
public class ChangePassScreen extends JFrame {

    private static int MY_HEIGHT = 412;
    private static int MY_WIDTH = 620;
    private JLabel topPanel;
    private JLabel botPanel;
    private JLabel lbOldPass;
    private JLabel lbPassword;
    private JLabel lbConfirmPass;
    private JLabel lbSend;
    private JLabel lbSpectator;
    private JTextField tfOldPass;
    private JTextField tfPassword;
    private JTextField tfConfirmPass;
    private JPanel panel;
    private JLabel lbBack;

    public ChangePassScreen() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
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
        Font f = new Font("Tahoma", Font.BOLD, 13);
        Color c = new Color(255, 255, 255);
        String strUrl = "src/resource/buttons/";
        ImageIcon imgTop = new ImageIcon(strUrl + "top.png");
        ImageIcon imgBot = new ImageIcon(strUrl + "bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl + "line.png");
        ImageIcon imgLogin = new ImageIcon(strUrl + "btn_send.png");


        JLabel temp = new JLabel("Change Password");
        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbSend = new JLabel(imgLogin);
        lbOldPass = new JLabel("Old Password ");
        lbPassword = new JLabel("New Password ");
        lbConfirmPass = new JLabel("Confirm Password");
        lbBack = new JLabel("Back");

        tfOldPass = new JTextField();
        tfPassword = new JPasswordField();
        tfConfirmPass = new JPasswordField();

        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 360, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbOldPass.setBounds(MY_WIDTH / 4, MY_HEIGHT / 3, 100, 13);
        //System.out.print(lbOldPass.getY());
        tfOldPass.setBounds(lbOldPass.getX() + 150, lbOldPass.getY(), 150, 20);
        lbPassword.setBounds(lbOldPass.getX(), lbOldPass.getY() + 30, 100, 13);
        tfPassword.setBounds(lbOldPass.getX() + 150, lbOldPass.getY() + 30, 150, 20);
        lbConfirmPass.setBounds(lbOldPass.getX(), lbPassword.getY() + 30, 150, 20);
        tfConfirmPass.setBounds(lbOldPass.getX() + 150, lbConfirmPass.getY(), 150, 20);
        temp.setBounds(5, 50, 150, 13);
        lbBack.setBounds(MY_WIDTH - 50, 50, 50, 20);
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbSend.setBounds(lbConfirmPass.getX() + 50, lbConfirmPass.getY() + 20, imgLogin.getIconWidth(), imgLogin.getIconHeight());
        Cursor cu = new Cursor(Cursor.HAND_CURSOR);
        lbSend.setCursor(cu);
        lbBack.setCursor(cu);

        lbOldPass.setFont(f);
        lbOldPass.setForeground(c);
        temp.setFont(f);
        temp.setForeground(c);
        lbPassword.setFont(f);
        lbPassword.setForeground(c);
        tfPassword.setFont(f);
        //tfPassword.setForeground(c);
        tfOldPass.setFont(f);
        tfConfirmPass.setFont(f);

        lbBack.setFont(f);
        lbBack.setForeground(c);


        lbConfirmPass.setFont(f);
        lbConfirmPass.setForeground(c);
        //tfOldPass.setForeground(c);

        add(topPanel);
        add(botPanel);
        add(lbOldPass);
        add(lbSpectator);
        add(temp);
        add(lbPassword);
        add(tfPassword);
        add(tfOldPass);
        add(lbSend);
        add(lbConfirmPass);
        add(tfConfirmPass);
        add(lbBack);

        // add action
        lbSend.addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                //System.out.println("dcm");
                String strPass = tfPassword.getText();
                String strOldPass = tfOldPass.getText();
                String strConfirmPass = tfConfirmPass.getText();
                if (strPass.equals("") || strOldPass.equals("") || strConfirmPass.equals("")) {
                    GUIManager.showMessageX("Please fill all infomation");
                    return;
                }
                business.BusinessProcessing.getInstance().changePassword(strPass, strConfirmPass, strOldPass);


            }
        });

        lbBack.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                GregorianCalendar cal = new GregorianCalendar(); //Create calendar
                int realYear = cal.get(GregorianCalendar.YEAR);
                BusinessProcessing bp = business.BusinessProcessing.getInstance();


                if (bp.isSuperior()) {

                    GUIManager.showScreenX(GUIManager.Screen.MainAprroverScreen,bp.viewLeaves(realYear));
                } else {
                    GUIManager.showScreenX(GUIManager.Screen.MainScreen,bp.viewLeaves(realYear));
                }
            }
        });
    }

    public static void main(String[] avg) {
        new ChangePassScreen();
        // new MainScreen();
    }
}

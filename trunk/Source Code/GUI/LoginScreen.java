/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
public class LoginScreen extends JFrame {

    private static int MY_HEIGHT = 385;
    private static int MY_WIDTH = 620;
    private JLabel topPanel;
    private JLabel botPanel;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbLogin;
    private JLabel lbSpectator;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JPanel panel;

    public LoginScreen() {
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
        ImageIcon imgTop = new ImageIcon(strUrl+"top.png");
        ImageIcon imgBot = new ImageIcon(strUrl+"bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl+"line.png");
        ImageIcon imgLogin = new ImageIcon(strUrl+"btn_login_11.png");


        JLabel temp = new JLabel("Login");
        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbLogin = new JLabel(imgLogin);
        lbUsername = new JLabel("Username ");
        lbPassword = new JLabel("Password ");

        tfUsername = new JTextField();
        tfPassword = new JPasswordField();

        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 335, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbUsername.setBounds(MY_WIDTH / 4, MY_HEIGHT / 3, 100, 13);
        System.out.print(lbUsername.getY());
        tfUsername.setBounds(lbUsername.getX() + 100, lbUsername.getY(), 150,20);
        lbPassword.setBounds(lbUsername.getX(), lbUsername.getY() + 30, 100, 13);
        tfPassword.setBounds(lbUsername.getX() + 100, lbUsername.getY() + 30, 150, 20);
        temp.setBounds(5, 50, 100, 13);
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbLogin.setBounds(lbPassword.getX()+ 50, lbPassword.getY()+20, imgLogin.getIconWidth(), imgLogin.getIconHeight());

        lbUsername.setFont(f);
        lbUsername.setForeground(c);
        temp.setFont(f);
        temp.setForeground(c);
        lbPassword.setFont(f);
        lbPassword.setForeground(c);
        tfPassword.setFont(f);
        //tfPassword.setForeground(c);
        tfUsername.setFont(f);
       //tfUsername.setForeground(c);

        add(topPanel);
        add(botPanel);
        add(lbUsername);
        add(lbSpectator);
        add(temp);
        add(lbPassword);
        add(tfPassword);
        add(tfUsername);
        add(lbLogin);
    }

    public static void main(String[] avg) {
        new LoginScreen();
      // new MainScreen();
    }
}

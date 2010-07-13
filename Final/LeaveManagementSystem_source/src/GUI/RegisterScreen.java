/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.BusinessProcessing;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 *
 * @author KOKICHI
 */
public class RegisterScreen extends JFrame {

    private static int MY_HEIGHT = 412;
    private static int MY_WIDTH = 620;
    private JLabel topPanel;
    private JLabel botPanel;
    private JPanel panel;
    private JLabel lbSpectator;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbFullname;
    private JLabel lbSuperior;
    private JLabel lbPosition;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfFullname;
    private JComboBox cbSuperiors;
    private JComboBox cbPositions;
    private JLabel lbSend;
    private JLabel lbClear;
    private JLabel lbBack;
    private Vector<Object[]> vtSuperiors;
    private Vector<Object[]> vtPositions;

    public RegisterScreen() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        final ImageIcon imgBG = new ImageIcon("resource/bg.jpg");
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
    }

    private void init() {
        setResizable(false);
        Font f = new Font("Tahoma", Font.BOLD, 11);
        Color c = new Color(255, 255, 255);
        String strUrl = "resource/buttons/";
        ImageIcon imgTop = new ImageIcon(strUrl + "top.png");
        ImageIcon imgBot = new ImageIcon(strUrl + "bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl + "line.png");
        ImageIcon imgSend = new ImageIcon(strUrl + "btn_send.png");
        ImageIcon imgClear = new ImageIcon(strUrl + "btn_clear.png");



        //System.out.print(lbUsername.getY());


        JLabel temp = new JLabel("Register ");
        temp.setFont(f);
        temp.setForeground(c);
        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbSend = new JLabel(imgSend);
        lbClear = new JLabel(imgClear);



        lbUsername = new JLabel("Username ");
        lbPassword = new JLabel("Password ");
        lbFullname = new JLabel("Fullname ");
        lbSuperior = new JLabel("Superior ");
        lbPosition = new JLabel("Position ");
        lbBack = new JLabel("Back");

        //lbAddress.setText();
        BusinessProcessing bp = BusinessProcessing.getInstance();

        tfUsername = new JTextField();

        tfPassword = new JTextField();

        tfFullname = new JTextField();

        //init data fo combo
        Vector itemsS = new Vector();
        Vector itemsP = new Vector();
        vtSuperiors = bp.getSuperiorList();
        vtPositions = bp.getPostionList();


        for (int i = 0; i < vtSuperiors.size(); i++) {
            Object[] o = (Object[]) vtSuperiors.get(i);
            itemsS.addElement(o[1]);
        }


        for (int i = 0; i < vtPositions.size(); i++) {
            Object[] o = (Object[]) vtPositions.get(i);
            itemsP.addElement(o[1]);
        }
        cbSuperiors = new JComboBox(itemsS);

        cbPositions = new JComboBox(itemsP);



        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 360, imgBot.getIconWidth(), imgBot.getIconHeight());
        temp.setBounds(5, 50, 100, 13);
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbUsername.setBounds(MY_WIDTH / 5, lbSpectator.getY() + 25, 150, 14);
        lbPassword.setBounds(lbUsername.getX(), lbUsername.getY() + 30, 150, 14);
        lbFullname.setBounds(lbPassword.getX(), lbPassword.getY() + 30, 150, 14);
        lbSuperior.setBounds(lbFullname.getX(), lbFullname.getY() + 30, 150, 14);
        lbPosition.setBounds(lbSuperior.getX(), lbSuperior.getY() + 30, 150, 28);
        lbBack.setBounds(MY_WIDTH - 50, 50, 50, 20);


        tfUsername.setBounds(lbUsername.getX() + 110, lbUsername.getY(), 150, 20);
        tfPassword.setBounds(lbPassword.getX() + 110, lbPassword.getY(), 150, 20);
        tfFullname.setBounds(lbFullname.getX() + 110, lbFullname.getY(), 150, 20);
        cbSuperiors.setBounds(lbSuperior.getX() + 110, lbSuperior.getY(), 200, 20);
        cbPositions.setBounds(lbPosition.getX() + 110, lbPosition.getY(), 100, 20);

        lbSend.setBounds(MY_WIDTH / 4, MY_HEIGHT / 2 + 100, 150, 28);
        lbClear.setBounds(lbSend.getX() + 100, lbSend.getY(), 150, 28);

        Cursor cu = new Cursor(Cursor.HAND_CURSOR);
        lbSend.setCursor(cu);
        lbClear.setCursor(cu);
        lbBack.setCursor(cu);


        add(lbBack);

        add(topPanel);
        add(botPanel);
        add(lbSpectator);
        add(temp);

        add(lbUsername);
        add(lbPassword);
        add(lbFullname);
        add(lbSuperior);
        add(lbPosition);
        add(lbSend);
        add(lbClear);


        add(tfFullname);
        add(tfUsername);

        add(tfPassword);
        add(cbSuperiors);
        add(cbPositions);


        //add action
        lbBack.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                GregorianCalendar cal = new GregorianCalendar(); //Create calendar
                int realYear = cal.get(GregorianCalendar.YEAR);
                BusinessProcessing bp = business.BusinessProcessing.getInstance();
                if (bp.isSuperior()) {

                    GUIManager.showScreenX(GUIManager.Screen.MainAprroverScreen, bp.viewLeaves(realYear));
                } else {
                    GUIManager.showScreenX(GUIManager.Screen.MainScreen, bp.viewLeaves(realYear));
                }
            }
        });

        lbSend.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (tfUsername.getText().equals("") || tfPassword.getText().equals("") || tfFullname.getText().equals("")) {
                    GUIManager.showMessageX("Please fill all infomation");
                } else {
                    BusinessProcessing bp = BusinessProcessing.getInstance();
                    int superiorId = -1;
                    int positionId = -1;
                    for (int i = 0; i < vtSuperiors.size(); i++) {
                        Object[] o = (Object[]) vtSuperiors.get(i);
                        if (o[1].toString().equals(cbSuperiors.getSelectedItem().toString())) {
                            superiorId = Integer.parseInt(o[0].toString());
                            break;
                        }

                    }


                    for (int i = 0; i < vtPositions.size(); i++) {
                        Object[] o = (Object[]) vtPositions.get(i);
                        if (o[1].toString().equals(cbPositions.getSelectedItem().toString())) {
                            positionId = Integer.parseInt(o[0].toString());
                            break;
                        }

                    }
                    GregorianCalendar cal = new GregorianCalendar(); //Create calendar

                    int realYear = cal.get(GregorianCalendar.YEAR);
                    System.out.print(tfUsername.getText()+"-"+ tfPassword.getText()+"-"+ tfFullname.getText()+"-"+ realYear+"-"+ superiorId+"-"+ positionId);
                    bp.createUser(tfUsername.getText(), tfPassword.getText(), tfFullname.getText(), realYear, superiorId, positionId);
                    GUIManager.showMessageX("Add new user success");
                    clearAll();
                }
            }
        });

        lbClear.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
               clearAll();
            }

        });


        setFontAndColor(f, c);
    }

    private void setFontAndColor(Font f, Color c) {



        lbBack.setFont(f);
        lbBack.setForeground(c);
        tfFullname.setFont(f);
        tfUsername.setFont(f);

        tfPassword.setFont(f);

        lbUsername.setFont(f);
        lbUsername.setForeground(c);
        lbPassword.setFont(f);
        lbPassword.setForeground(c);
        lbFullname.setFont(f);
        lbFullname.setForeground(c);
        lbSuperior.setFont(f);
        lbSuperior.setForeground(c);
        lbPosition.setFont(f);
        lbPosition.setForeground(c);
    }

    private void clearAll(){
        tfFullname.setText("");
        tfPassword.setText("");
        tfUsername.setText("");
    }
    public static void main(String[] avg) {
        new RegisterScreen();
    }
}

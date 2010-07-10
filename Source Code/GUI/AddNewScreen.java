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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author KOKICHI
 */
public class AddNewScreen extends JFrame {

    private static int MY_HEIGHT = 385;
    private static int MY_WIDTH = 620;
    private JLabel topPanel;
    private JLabel botPanel;
    private JPanel panel;
    private JLabel lbSpectator;
    private JLabel lbSubject;
    private JLabel lbReason;
    private JLabel lbFromDate;
    private JLabel lbToDate;
    private JLabel lbAddress;
    private JLabel lbSendBt;
    private JLabel lbClearBt;
    private JTextField tfSubject;
    private JTextArea taReason;
    private JTextField tfFromDate;
    private JTextField tfToDate;
    private JTextArea taAddress;

    public AddNewScreen() {
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
        Font f = new Font("Tahoma", Font.BOLD, 11);
        Color c = new Color(255, 255, 255);
        String strUrl = "src/resource/buttons/";
        ImageIcon imgTop = new ImageIcon(strUrl + "top.png");
        ImageIcon imgBot = new ImageIcon(strUrl + "bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl + "line.png");
        ImageIcon imgSendBt = new ImageIcon(strUrl + "btn_send.png");
        ImageIcon imgClearBt = new ImageIcon(strUrl + "btn_clear.png");



        //System.out.print(lbUsername.getY());


        JLabel temp = new JLabel("Add new leave ");
        temp.setFont(f);
        temp.setForeground(c);
        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        lbClearBt = new JLabel(imgClearBt);
        lbSendBt = new JLabel(imgSendBt);


        lbSubject = new JLabel("Subject");
        lbReason = new JLabel("Reason ");
        lbFromDate = new JLabel("From Date ");
        lbToDate = new JLabel("To Date ");
        lbAddress = new JLabel("<html>Address for <br> communication</html> ");
        //lbAddress.setText();

        tfSubject = new JTextField();
        taReason = new JTextArea(30, 50);
        tfFromDate = new JTextField();
        tfToDate = new JTextField();
        taAddress = new JTextArea(30, 50);

        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 335, imgBot.getIconWidth(), imgBot.getIconHeight());
        temp.setBounds(5, 50, 100, 13);
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbSubject.setBounds(5, lbSpectator.getY() + 25, 150, 14);
        lbReason.setBounds(lbSubject.getX(), lbSubject.getY() + 30, 150, 14);
        lbFromDate.setBounds(MY_WIDTH / 2, lbSubject.getY(), 150, 14);
        lbToDate.setBounds(lbFromDate.getX(), lbFromDate.getY() + 30, 150, 14);
        lbAddress.setBounds(lbToDate.getX(), lbToDate.getY() + 30, 150, 28);

        tfSubject.setBounds(lbSubject.getX() + 110, lbSubject.getY(), 150, 20);
        taReason.setBounds(lbReason.getX() + 110, lbReason.getY(), 150, 80);
        tfFromDate.setBounds(lbFromDate.getX() + 110, lbFromDate.getY(), 150, 20);
        tfToDate.setBounds(lbToDate.getX() + 110, lbToDate.getY(), 150, 20);
        taAddress.setBounds(lbAddress.getX() + 110, lbAddress.getY(), 150, 80);
        taAddress.setLineWrap(true);
        taReason.setLineWrap(true);

        lbSendBt.setBounds(taAddress.getX() - 20, taAddress.getY() + 100, imgSendBt.getIconWidth(), imgSendBt.getIconHeight());
        lbClearBt.setBounds(lbSendBt.getX() + imgSendBt.getIconHeight() + 30, lbSendBt.getY(), imgClearBt.getIconWidth(), imgClearBt.getIconHeight());
        Cursor cu = new Cursor(Cursor.HAND_CURSOR);
        lbSendBt.setCursor(cu);
        lbClearBt.setCursor(cu);

        tfFromDate.setEditable(false);
        tfToDate.setEditable(false);

        add(topPanel);
        add(botPanel);
        add(lbSpectator);
        add(temp);

        add(lbSubject);
        add(lbReason);
        add(lbFromDate);
        add(lbToDate);
        add(lbAddress);

        add(tfFromDate);
        add(tfSubject);
        add(tfToDate);
        add(taAddress);
        add(taReason);
        add(lbSendBt);
        add(lbClearBt);

        setFontAndColor(f, c);

        //add action

        tfFromDate.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                CalendarProgram cp = new CalendarProgram();
                cp.frmMain.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        tfFromDate.setText(GUIManager.strDate);

                    }
                });
            }
        });
        tfToDate.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                CalendarProgram cp = new CalendarProgram();
                cp.frmMain.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        tfToDate.setText(GUIManager.strDate);

                    }
                });
            }
        });

        lbClearBt.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                tfFromDate.setText("");
                tfToDate.setText("");
                taAddress.setText("");
                taReason.setText("");
                tfSubject.setText("");

            }
        });

        lbSendBt.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
               String strSub = tfSubject.getText();
               String strFromdate = tfFromDate.getText();
               String strToDate = tfToDate.getText();
               String strReason = taReason.getText();
               String strAdd = taAddress.getText();
               if(strSub.equals("") || strFromdate.equals("") || strToDate.equals("") || strReason.equals("") || strAdd.equals("")){
                    GUIManager.showMessageX("Please fill all infomation");
                    return;
               }
               
               BusinessProcessing bp = business.BusinessProcessing.getInstance();
               bp.applyLeave(bp.getUserID(), tfFromDate.getText(), tfToDate.getText(),  taReason.getText(), taAddress.getText(), tfSubject.getText());
            }
        });

    }

    private void setFontAndColor(Font f, Color c) {

        tfFromDate.setFont(f);
        tfSubject.setFont(f);
        tfToDate.setFont(f);
        taAddress.setFont(f);
        taReason.setFont(f);

        lbSubject.setFont(f);
        lbSubject.setForeground(c);
        lbReason.setFont(f);
        lbReason.setForeground(c);
        lbFromDate.setFont(f);
        lbFromDate.setForeground(c);
        lbToDate.setFont(f);
        lbToDate.setForeground(c);
        lbAddress.setFont(f);
        lbAddress.setForeground(c);
    }

    public static void main(String[] avg) {
        new AddNewScreen();
    }
}

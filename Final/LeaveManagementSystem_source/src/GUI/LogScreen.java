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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author KOKICHI
 */
public class LogScreen extends JFrame {

    private static int MY_HEIGHT = 412;
    private static int MY_WIDTH = 620;
    private JLabel topPanel;
    private JLabel botPanel;
    private JPanel panel;
    private Font mFont;
    private Color mColor;
    private JTable table;
    // private JLabel lbTable;
    private JLabel lbSpectator;
    private JLabel lbUsername;
    private JLabel lbFromDate;
    private JLabel lbToDate;
    private JTextField tfFromDate;
    private JTextField tfToDate;
    private JScrollPane spTable;
    private DataObject data;
    private int myId;
    private JLabel lbSearch;

    public LogScreen(DataObject data,int userId) {
        this.data = data;
        myId = userId;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Leave Management System");
    }

    private void init() {
        setResizable(false);
        mFont = new Font("Tahoma", Font.BOLD, 10);
        mColor = new Color(255, 255, 255);
        String strUrl = "resource/buttons/";

        ImageIcon imgTop = new ImageIcon(strUrl + "top.png");
        ImageIcon imgBot = new ImageIcon(strUrl + "bottom.png");
        ImageIcon imgSpectator = new ImageIcon(strUrl + "line.png");
        ImageIcon imgTabel = new ImageIcon(strUrl + "mainTable.png");

        table = new JTable(data);
        spTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        //lbTable = new JLabel(imgTabel);

        lbSearch = new JLabel("Search ");
        lbFromDate = new JLabel("From Date ");
        lbToDate = new JLabel("To Date ");
        JLabel temp = new JLabel("Log");
        temp.setBounds(5, 50, 100, 14);
        JLabel temp2 = new JLabel("View log of ");
        lbUsername = new JLabel();
        BusinessProcessing bp = BusinessProcessing.getInstance();
        lbUsername.setText(bp.getUsername(myId));

        tfFromDate = new JTextField("");
        tfToDate = new JTextField("");

        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 360, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        temp2.setBounds(10, lbSpectator.getY() + 10, 100, 14);
        lbUsername.setBounds(temp2.getX() + 60, temp2.getY(), 100, 14);
        lbFromDate.setBounds(MY_WIDTH / 3, lbUsername.getY(), 100, 14);
        tfFromDate.setBounds(lbFromDate.getX() + 60, lbUsername.getY(), 100, 20);
        lbToDate.setBounds(tfFromDate.getX() + 120, lbUsername.getY(), 100, 14);
        tfToDate.setBounds(lbToDate.getX() + 50, lbUsername.getY(), 100, 20);
        lbSearch.setBounds(tfToDate.getX()+120, lbUsername.getY(), 100, 20);
        spTable.setBounds(10, 110, 590, 210);
        ///  lbTable.setBounds(table.getX(), table.getY(), 590,210);

        temp.setFont(mFont);
        temp.setForeground(mColor);
        temp2.setFont(mFont);
        temp2.setForeground(mColor);
        lbUsername.setFont(new Font("tahoma", Font.BOLD | Font.ITALIC, 11));
        lbUsername.setForeground(new Color(0, 0, 255));
        table.setFont(mFont);
        lbFromDate.setFont(mFont);
        lbFromDate.setForeground(mColor);
        lbToDate.setFont(mFont);
        lbToDate.setForeground(mColor);
        tfFromDate.setFont(mFont);
        tfToDate.setFont(mFont);

        lbSearch.setFont(mFont);
        lbSearch.setForeground(mColor);
        lbSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tfFromDate.setEditable(false);
        tfToDate.setEditable(false);

        add(temp);
        add(topPanel);
        add(botPanel);
        add(lbSpectator);
        add(spTable);
        add(temp2);
        add(lbUsername);
        add(lbFromDate);
        add(tfFromDate);
        add(tfToDate);
        add(lbToDate);
        add(lbSearch);
        //add(lbTable);

        //add action
         tfFromDate.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                business.BusinessProcessing bp = BusinessProcessing.getInstance();
                CalendarProgram cp =  CalendarProgram.getInstance();
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
                CalendarProgram cp = CalendarProgram.getInstance();
                cp.frmMain.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        tfToDate.setText(GUIManager.strDate);

                    }
                });
            }
        });

        lbSearch.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if(tfToDate.getText().equals("") || tfFromDate.getText().equals("")) return;

                BusinessProcessing bp = BusinessProcessing.getInstance();
                table.clearSelection();
                table.setModel(bp.viewLogDetail(myId, tfFromDate.getText(), tfToDate.getText()));
            }

        });

    }

    public static void main(String[] avg) {
        new LogScreen(null,0);
    }
}

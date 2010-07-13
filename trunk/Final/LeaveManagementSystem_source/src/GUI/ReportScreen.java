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
import java.util.GregorianCalendar;
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
public class ReportScreen extends JFrame {

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
    private JComboBox cbYear;
    private JLabel lbYear;
    private JScrollPane spTable;
    private DataObject data;
    private JLabel lbBack;

    public ReportScreen(DataObject data) {
        this.data = data;
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        table = new JTable(data) ;
        spTable = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        //lbTable = new JLabel(imgTabel);


        JLabel temp = new JLabel("Report");
        lbBack = new JLabel("Back");
        lbYear = new JLabel("Year");

        BusinessProcessing bp = business.BusinessProcessing.getInstance();
        cbYear = new JComboBox(bp.getYearList(bp.getUserID()));
        cbYear.setSelectedIndex(cbYear.getItemCount()-1);


        temp.setBounds(5, 50, 100, 14);
        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 360, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbYear.setBounds(5, lbSpectator.getY() + 10, 100, 14);
        cbYear.setBounds(lbYear.getX() + 50, lbYear.getY(), 80, 20);
        lbBack.setBounds(MY_WIDTH - 50, 50, 50, 20);

        spTable.setBounds(10, 110, 590, 210);
        ///  lbTable.setBounds(table.getX(), table.getY(), 590,210);

        temp.setFont(mFont);
        temp.setForeground(mColor);
        lbYear.setFont(mFont);
        lbYear.setForeground(mColor);
        cbYear.setFont(mFont);
        lbBack.setFont(mFont);
        lbBack.setForeground(mColor);


        Cursor cu =  new Cursor(Cursor.HAND_CURSOR);
        lbBack.setCursor(cu);


        table.setFont(mFont);



        add(temp);
        add(topPanel);
        add(botPanel);
        add(lbSpectator);
        add(spTable);
        add(lbYear);
        add(cbYear);
        add(lbBack);

        // add action

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

        cbYear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               BusinessProcessing bp = business.BusinessProcessing.getInstance();
              // System.out.println(cbYear.getSelectedItem());

               data = bp.viewReport(Integer.parseInt(cbYear.getSelectedItem().toString()));
             //  System.out.println(data.toString());
               table.setModel(data);

            }
        });


    }

    private void initData(){
        
    }

    public static void main(String[] avg) {
        new ReportScreen(null);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author KOKICHI
 */
public class LogScreen extends JFrame {

    private static int MY_HEIGHT = 385;
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

    public LogScreen() {
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
        ImageIcon imgTabel = new ImageIcon(strUrl + "mainTable.png");
         spTable = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
        //lbTable = new JLabel(imgTabel);

        lbFromDate = new JLabel("From Date ");
        lbToDate = new JLabel("To Date ");
        JLabel temp = new JLabel("Log");
        temp.setBounds(5, 50, 100, 14);
        JLabel temp2 = new JLabel("View log of ");
        lbUsername = new JLabel("hung");

        tfFromDate = new JTextField();
        tfToDate = new JTextField();

        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 335, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        temp2.setBounds(10, lbSpectator.getY() + 10, 100, 14);
        lbUsername.setBounds(temp2.getX() + 60, temp2.getY(), 100, 14);
        lbFromDate.setBounds(MY_WIDTH/3, lbUsername.getY(), 100, 14);
        tfFromDate.setBounds(lbFromDate.getX()+60, lbUsername.getY(), 100,20);
        lbToDate.setBounds(tfFromDate.getX()+120, lbUsername.getY(), 100, 14);
        tfToDate.setBounds(lbToDate.getX()+50, lbUsername.getY(), 100, 20);
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
        //add(lbTable);

    }

    public static void main(String[] avg) {
        new LogScreen();
    }
}

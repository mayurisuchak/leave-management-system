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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author KOKICHI
 */
public class ReportScreen extends JFrame {

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
    private JComboBox cbYear;
    private JLabel lbYear;
    private JScrollPane spTable;

    public ReportScreen() {
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
        // table.setOpaque(false);

        topPanel = new JLabel(imgTop);
        botPanel = new JLabel(imgBot);
        lbSpectator = new JLabel(imgSpectator);
        //lbTable = new JLabel(imgTabel);


        JLabel temp = new JLabel("Report");

        lbYear = new JLabel("Year");
        cbYear = new JComboBox();


        temp.setBounds(5, 50, 100, 14);
        topPanel.setBounds(0, 0, imgTop.getIconWidth(), imgTop.getIconHeight());
        botPanel.setBounds(0, 335, imgBot.getIconWidth(), imgBot.getIconHeight());
        lbSpectator.setBounds(0, 75, imgSpectator.getIconWidth(), imgSpectator.getIconHeight());
        lbYear.setBounds(5, lbSpectator.getY() + 10, 100, 14);
        cbYear.setBounds(lbYear.getX() + 50, lbYear.getY(), 50, 20);

        spTable.setBounds(10, 110, 590, 210);
        ///  lbTable.setBounds(table.getX(), table.getY(), 590,210);

        temp.setFont(mFont);
        temp.setForeground(mColor);
        lbYear.setFont(mFont);
        lbYear.setForeground(mColor);
        cbYear.setFont(mFont);

        table.setFont(mFont);



        add(temp);
        add(topPanel);
        add(botPanel);
        add(lbSpectator);
        add(spTable);
        add(lbYear);
        add(cbYear);



    }

    public static void main(String[] avg) {
        new ReportScreen();
    }
}

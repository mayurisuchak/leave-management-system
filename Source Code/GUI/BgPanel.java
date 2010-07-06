/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author KOKICHI
 */
public class BgPanel extends JPanel {

    private JLabel lbTop;
    private JLabel lbBot;
    private ImageIcon imgBg;

    public void BgPanel() {
        imgBg = new ImageIcon("src/resource/bg.jpg");
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //  Dispaly image at at full size
        g.drawImage(imgBg.getImage(), 0, 0, null);
        super.paintComponent(g);
    }
}

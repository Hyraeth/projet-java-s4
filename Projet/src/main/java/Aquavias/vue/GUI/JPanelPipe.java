package Aquavias.vue.GUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JPanelPipe extends JPanel {
    private BufferedImage img;

    public JPanelPipe(String name) {
        URL resource = getClass().getResource(name);
        try {
            img = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - img.getWidth(null)) / 2;
        int y = (this.getHeight() - img.getHeight(null)) / 2;
        g2d.drawImage(img, x, y, null);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(10, 10);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
        return new Dimension(s, s);
    }
}
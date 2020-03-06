package Aquavias.vue.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import Aquavias.model.Pipe;

public class JPanelPipe extends JPanel {
    private BufferedImage img;
    private Pipe p;

    public JPanelPipe(Pipe p) {
        this.p = p;
        try {
            img = ImageIO.read(new File("assets\\img\\"+p.getFilename()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void maj() {
        
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
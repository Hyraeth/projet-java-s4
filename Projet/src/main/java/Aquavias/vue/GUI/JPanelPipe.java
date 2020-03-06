package Aquavias.vue.GUI;

import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import Aquavias.controller.ControllerIG;

public class JPanelPipe extends JPanel {

    private BufferedImage[][] img = new BufferedImage[2][24];
    private int index;
    private boolean remplit;
    private int rota;
    private ControllerIG c;

    public JPanelPipe(int i, int r, boolean remp, ControllerIG co, int x, int y) {
        this.index = i;
        this.rota = r;
        this.remplit = remp;
        this.c = co;
        loadImg();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                c.rotate(x, y);
                rota = (rota+1)%4;
                repaint();
            }
        });
    }

    public void loadImg() {
        for (int i = 0; i < 24; i++) {
            try {
                int type = i/4;
                int rot = i%4;
                img[0][i] = ImageIO.read(new File("assets\\img\\"+type+rot+false+".png"));
                img[1][i] = ImageIO.read(new File("assets\\img\\"+type+rot+true+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void remplir(boolean rempli) {
        remplit = rempli;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = (remplit)?1:0;
        int j = index+rota;
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - img[i][j].getWidth(null)) / 2;
        int y = (this.getHeight() - img[i][j].getHeight(null)) / 2;
        g2d.drawImage(img[i][j], x, y, null);
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
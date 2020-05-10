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
import Aquavias.model.Pipe;

public class JPanelPipe extends JPanel {

    private static BufferedImage[][] img = new BufferedImage[2][24];
    private int index;
    private boolean remplit;
    private Pipe p;
    private ControllerIG con;

    public JPanelPipe(int i, Pipe p, boolean remp, ControllerIG co, int x, int y, boolean moveable) {
        this.index = i;
        this.p = p;
        this.remplit = remp;
        this.con = co;
        if(moveable) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    con.rotate(x, y);
                    repaint();
                }
            });
        }
    }

    public static void loadImg() {
        for (int i = 0; i < 24; i++) {
            try {
                int type = i/4;
                int rot = i%4;
                img[0][i] = ImageIO.read(new File("assets/img/"+type+rot+false+".png"));
                img[1][i] = ImageIO.read(new File("assets/img/"+type+rot+true+".png"));
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
        int j = index+p.getRotation();
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - img[i][j].getWidth(null)) / 2;
        int y = (this.getHeight() - img[i][j].getHeight(null)) / 2;
        Dimension d = super.getPreferredSize();
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } 
        int w = (int) d.getWidth();
            int h = (int) d.getHeight();
            int s = (w/this.con.getLongueur() < h/this.con.getLargeur() ? w/this.con.getLongueur() : h/this.con.getLargeur());
        g2d.drawImage(img[i][j], 0, 0, s, s, null);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(25, 25);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w/this.con.getLongueur() < h/this.con.getLargeur() ? w/this.con.getLongueur() : h/this.con.getLargeur());
        return new Dimension(s, s);
    }
}
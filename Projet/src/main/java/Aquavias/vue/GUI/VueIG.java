package Aquavias.vue.GUI;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.event.*;

import org.json.JSONException;
import org.w3c.dom.events.MouseEvent;

import Aquavias.controller.ControllerIG;
import Aquavias.model.Niveau;

/**
 * Interface graphique d'un niveau
 */
public class VueIG {
    /**
     * Controller
     */
    private ControllerIG controller;
    private Niveau model;
    /**
     * Conteneur
     */
    private JFrame jframe;

    /**
     * barre d'action
     */
    private JPanel actionBar;
    private JLabel resources;

    private JPanel zonePlateau;
    private JPanelPipe[][] Pipes;

    /**
     * Constructeur de l'interface graphihque du plateau
     * @param c controlleur
     * @param m Niveau
     */
    public VueIG(ControllerIG c, Niveau m) {
        GridBagConstraints gbc = new GridBagConstraints();
        controller = c;
        model = m;
        m.remplir();

        jframe = new JFrame();
        jframe.setLayout(new GridBagLayout());

        JPanelPipe.loadImg();
        zonePlateau = new JPanel();
        zonePlateau();
        zonePlateau.setPreferredSize(new Dimension(m.getLongueur()*100,m.getLargeur()*100));
        initPlateau(m,c);

        zonePlateau();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        gbc.gridy = 0;
        jframe.add(actionBar,gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.9;
        gbc.gridy = 1;
        
        jframe.add(zonePlateau,gbc);
        jframe.pack();
        jframe.setTitle("Aquavias");
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Ajout des boutons dans le barre d'action
     */
    private void zonePlateau() {
      actionBar = new JPanel();

      JButton boutonQuitter = new JButton("Quitter");
      boutonQuitter.addActionListener((e)-> {
          controller.quit();
          jframe.dispose();
      });
      actionBar.add(boutonQuitter);

      JButton debugbutton = new JButton("Debug");
      debugbutton.addActionListener((e)-> {
          controller.debug();
      });
      actionBar.add(debugbutton);

      JButton undoButton = new JButton("Undo");
      undoButton.addActionListener((e)-> {
        controller.undo();
        update();
      });
      actionBar.add(undoButton);
      resources = new JLabel(model.getresources()+"");
      actionBar.add(resources);
    }

    /**
     * Charge un niveau dans l'interface graphique
     * @param model niveau
     * @param controller controller
     */
    public void initPlateau(Niveau model, ControllerIG controller) {
        zonePlateau.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Pipes = new JPanelPipe[model.getLargeur()][model.getLongueur()];
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                Pipes[i][j] = new JPanelPipe(model.getPipe(i, j).getIndexGui(), model.getPipe(i, j), model.getPipe(i, j).isRempli(), controller, i, j, model.getPipe(i,j).isMoveable());
                Pipes[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                if(model.getPipe(i,j).isMoveable()) Pipes[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                zonePlateau.add(Pipes[i][j], gbc);
            }
        }
    }

    /**
     * Màj l'interface graphique pour bien afficher le niveau
     */
    public void update() {
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                Pipes[i][j].remplir(model.getPipe(i, j).isRempli());
                Pipes[i][j].repaint();
            }
        }
        resources.setText(model.getresources()+"");
    }

    /**
     * Ferme l'interface graphique
     */
    public void close() {
        jframe.dispose();
    }

    /**
     * Affiche un message indiquant que le joueur à gagner. Demande s'il veut continuer
     */
    public void displayWinScreen() {
        String options[] = {"Oui","Non"};
        int result = JOptionPane.showOptionDialog(jframe, "Bravo vous avez gagné.\nFaire le prochain niveau?", "Félicitation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(result == JOptionPane.YES_OPTION) {
            int nblvl;
            try {
                File f = new File("assets/lvls/niveau.json");
                if(model.getLvlType().equals("niveaux_off")) {
                    nblvl = Niveau.getNumberLvl(f, model.getLvlType());
                    if(nblvl == model.getLvlNumber()+1) {
                        JOptionPane.showMessageDialog(jframe, "Plus de niveaux.", "Erreur", JOptionPane.WARNING_MESSAGE);
                        controller.setEnabledFenetre(true);
                        this.close();
                    }
                    else {
                        this.close();
                        controller.lancerNiv(model.getLvlNumber()+1);
                    }
                } else {
                    this.close();
                    switch (model.getLvlType()) {
                        case "niveauAleaF":
                            controller.niveauAleaF();
                            break;
                        case "niveauAleaN":
                            controller.niveauAleaN();
                        break;
                        case "niveauAleaD":
                            controller.niveauAleaD();
                        break;
                        default:
                            break;
                    }
                }
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(jframe, "Plus de niveaux.", "Erreur", JOptionPane.WARNING_MESSAGE);
                controller.setEnabledFenetre(true);
                this.close();
            } catch (JSONException e) {
                JOptionPane.showMessageDialog(jframe, "Plus de niveaux.", "Erreur", JOptionPane.WARNING_MESSAGE);
                controller.setEnabledFenetre(true);
                this.close();
            }
        } else {
            controller.setEnabledFenetre(true);
            this.close();
        }
    }

    /**
     * Affiche un message indiquant que le joueur a perdu
     */
    public void displayLoseScreen() {
        String options[] = {"Oui","Non"};
        int result = JOptionPane.showOptionDialog(jframe, "Pas terrible, voulez-vous recommender ?", "Boo", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(result == JOptionPane.YES_OPTION) {
            controller.lancerNiv(model.getLvlNumber());
            this.close();
        } else {
            controller.setEnabledFenetre(true);
            this.close();
        }
    }
}

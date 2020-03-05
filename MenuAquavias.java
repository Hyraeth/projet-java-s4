import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.*;

public class MenuAquavias extends JFrame implements ActionListener {

  private JButton boutonJouer = new JButton("Jouer");
  private JButton boutonOption = new JButton("Options");
  private JButton boutonQuitter = new JButton("Quitter");
  private JPanel contenu = (JPanel) getContentPane();

  public MenuAquavias() {

    super("Aquavias");
    this.setSize(1200,700);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    this.setJMenuBar(createMenuBarre());

    contenu.setLayout(new FlowLayout());

    boutonJouer.addActionListener( (event) -> jouer());
    boutonOption.addActionListener( (event) -> option());
    boutonQuitter.addActionListener((e)-> {
			this.dispose();
		});

    contenu.add(boutonJouer);
    contenu.add(boutonOption);
    contenu.add(boutonQuitter);
  }


  private JMenuBar createMenuBarre() {       // construction barre de menu
    JMenuBar menuBarre = new JMenuBar();

    JButton aide = new JButton("Aide");      // regles
    menuBarre.add(aide);
    JButton quitter = new JButton("Quitter");
    menuBarre.add(quitter);
    quitter.addActionListener((e)-> {
			this.dispose();
		});
    return menuBarre;
  }


  private void jouer() {
    JPanel panel = (JPanel) this.getContentPane();
    panel.removeAll();
    panel.revalidate();
    panel.repaint();

    contenu.add(new Label("Choisissez un niveau"), BorderLayout.PAGE_START);

    for (int i=1; i<10; i++) {
      contenu.add("Center",new JButton("niveau " + i));
    }

    this.setVisible(true);
  }


  private void option() {
    JPanel panel = (JPanel) this.getContentPane();
    panel.removeAll();
    panel.revalidate();
    panel.repaint();

    this.setVisible(true);
  }


  public void actionPerformed(ActionEvent e) {}

  public static void main(String[] args) throws Exception {
    MenuAquavias fenetre = new MenuAquavias();
    fenetre.setVisible(true);
  }

}

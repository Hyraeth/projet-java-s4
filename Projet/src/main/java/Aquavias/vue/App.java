package Aquavias.vue;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import Aquavias.controller.ControllerIG;
import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EventQueue.invokeLater(() -> {
            File f = new File("assets\\lvls\\niveau.json");
            Niveau m = new Niveau();
            try {
                m.load(f, "niveaux_off", 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerIG c = new ControllerIG(m);
            VueIG gui = new VueIG(c, m);
            c.setVue(gui);
        });
    }
}

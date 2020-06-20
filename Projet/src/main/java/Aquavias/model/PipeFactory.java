package Aquavias.model;
/**
 * Classe factory pour fabriquer des tuyaux.
 */
public class PipeFactory {
    /**
     * Crée un tuyau d'un certain type PipeType et peut être tourner ou pas selon la valeur de moveable
     * @param PipeType un int qui représente le tuyau à fabriquer
     * @param moveable un boolean qui indique si ce tuyau peut être tourner
     * @return un Tuyau.
     */
    public Pipe getPipe(int PipeType, boolean moveable) {
        switch (PipeType) {
            case 0:
                return new PipeDepart();
            case 1:
                return new PipeArrivee();
            case 2:
                return new PipeL(moveable);
            case 3:
                return new PipeI(moveable);
            case 4:
                return new PipeT(moveable);
            case 5:
                return new PipeX();
            default:
                System.out.println("I'm so lost");
                return null;
        }
    }
}
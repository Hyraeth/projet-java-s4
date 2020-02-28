package Aquavias.model;

public class PipeFactory {
    public Pipe getPipe(String PipeType, boolean moveable) {
        switch (PipeType) {
            case "depart":
                return new PipeDepart();
            case "arrivee":
                return new PipeArrivee();
            case "pipeL":
                return new PipeL(moveable);
            case "pipeI":
                return new PipeI(moveable);
            case "pipeT":
                return new PipeT(moveable);
            case "pipeX":
                return new PipeX();
            default:
                return null;
        }
    }
}
public class PipeFactory {
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
                return null;
        }
    }
}
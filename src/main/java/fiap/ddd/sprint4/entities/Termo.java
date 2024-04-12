package fiap.ddd.sprint4.entities;

public class Termo extends  _BaseEntity{

    private boolean AceitarTermo;

    public  Termo(){}

    public Termo(int id, boolean aceitarTermo) {
        super(id);
        AceitarTermo = aceitarTermo;
    }

    public boolean isAceitarTermo() {
        return AceitarTermo;
    }

    public void setAceitarTermo(boolean aceitarTermo) {
        AceitarTermo = aceitarTermo;
    }

    @Override
    public String toString() {
        return "Termo{" +
                "AceitarTermo=" + AceitarTermo +
                "} " + super.toString();
    }
}

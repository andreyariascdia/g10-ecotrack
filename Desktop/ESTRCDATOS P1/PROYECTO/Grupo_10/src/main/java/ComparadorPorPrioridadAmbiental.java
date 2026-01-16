import java.util.Comparator;
import java.io.Serializable;

/**
 * Comparador por prioridad ambiental (de mayor a menor)
 */
public class ComparadorPorPrioridadAmbiental implements Comparator<Residuo>, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Override
    public int compare(Residuo r1, Residuo r2) {
        return Integer.compare(r2.getPrioridadAmbiental(), r1.getPrioridadAmbiental());
    }
}
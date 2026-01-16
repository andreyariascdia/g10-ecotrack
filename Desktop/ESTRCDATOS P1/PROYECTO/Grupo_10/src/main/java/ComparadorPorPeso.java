import java.util.Comparator;
import java.io.Serializable;

/**
 * Comparador por peso (de mayor a menor)
 */
public class ComparadorPorPeso implements Comparator<Residuo>, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Override
    public int compare(Residuo r1, Residuo r2) {
        return Double.compare(r2.getPeso(), r1.getPeso());
    }
}
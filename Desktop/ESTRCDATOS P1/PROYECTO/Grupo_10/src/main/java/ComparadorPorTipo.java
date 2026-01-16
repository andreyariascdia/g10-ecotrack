import java.util.Comparator;
import java.io.Serializable;

/**
 * Comparador por tipo (orden alfabético)
 */
public class ComparadorPorTipo implements Comparator<Residuo>, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Override
    public int compare(Residuo r1, Residuo r2) {
        return r1.getTipo().compareToIgnoreCase(r2.getTipo());
    }
}
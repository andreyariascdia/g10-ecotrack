import java.io.Serializable;

/**
 * Clase que representa una zona urbana en el sistema EcoTrack
 * Implementa la función de utilidad: U = recolectado - pendiente
 */
public class Zona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private double residuosRecolectados; // cantidad ya procesada
    private double residuosPendientes; // cantidad sin recoger
    
    public Zona(String nombre) {
        this.nombre = nombre;
        this.residuosRecolectados = 0.0;
        this.residuosPendientes = 0.0;
    }
    
    public Zona(String nombre, double residuosRecolectados, double residuosPendientes) {
        this.nombre = nombre;
        this.residuosRecolectados = residuosRecolectados;
        this.residuosPendientes = residuosPendientes;
    }
    
    /**
     * Calcula la función de utilidad de la zona
     * U = recolectado - pendiente
     * Utilidad positiva = zona bien gestionada
     * Utilidad negativa = foco crítico que requiere acción inmediata
     */
    public double calcularUtilidad() {
        return residuosRecolectados - residuosPendientes;
    }
    
    /**
     * Agrega residuos pendientes a la zona
     */
    public void agregarResiduosPendientes(double cantidad) {
        this.residuosPendientes += cantidad;
    }
    
    /**
     * Marca residuos como recolectados
     */
    public void recolectarResiduos(double cantidad) {
        if (cantidad <= residuosPendientes) {
            this.residuosPendientes -= cantidad;
            this.residuosRecolectados += cantidad;
        }
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public double getResiduosRecolectados() {
        return residuosRecolectados;
    }
    
    public double getResiduosPendientes() {
        return residuosPendientes;
    }
    
    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setResiduosRecolectados(double residuosRecolectados) {
        this.residuosRecolectados = residuosRecolectados;
    }
    
    public void setResiduosPendientes(double residuosPendientes) {
        this.residuosPendientes = residuosPendientes;
    }
    
    @Override
    public String toString() {
        return "Zona{" +
                "nombre='" + nombre + '\'' +
                ", recolectados=" + residuosRecolectados +
                ", pendientes=" + residuosPendientes +
                ", utilidad=" + calcularUtilidad() +
                '}';
    }
}
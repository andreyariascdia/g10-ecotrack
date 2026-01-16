import java.io.Serializable;

/**
 * Clase que representa un vehículo recolector en el sistema EcoTrack
 * Se usará en la cola de prioridad para despacho
 */
public class Vehiculo implements Serializable, Comparable<Vehiculo> {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String zonaAsignada;
    private double capacidadMaxima; // en kilogramos
    private double cargaActual; // en kilogramos
    private int prioridad; // calculada según volumen o impacto ambiental
    
    public Vehiculo(String id, double capacidadMaxima) {
        this.id = id;
        this.capacidadMaxima = capacidadMaxima;
        this.cargaActual = 0.0;
        this.zonaAsignada = null;
        this.prioridad = 0;
    }
    
    public Vehiculo(String id, String zonaAsignada, double capacidadMaxima, int prioridad) {
        this.id = id;
        this.zonaAsignada = zonaAsignada;
        this.capacidadMaxima = capacidadMaxima;
        this.cargaActual = 0.0;
        this.prioridad = prioridad;
    }
    
    /**
     * Verifica si el vehículo puede cargar cierta cantidad de residuos
     */
    public boolean puedeCargar(double peso) {
        return (cargaActual + peso) <= capacidadMaxima;
    }
    
    /**
     * Carga residuos al vehículo
     */
    public boolean cargarResiduos(double peso) {
        if (puedeCargar(peso)) {
            cargaActual += peso;
            return true;
        }
        return false;
    }
    
    /**
     * Vacía el vehículo
     */
    public void vaciar() {
        this.cargaActual = 0.0;
    }
    
    /**
     * Calcula el porcentaje de capacidad utilizada
     */
    public double porcentajeCapacidad() {
        return (cargaActual / capacidadMaxima) * 100;
    }
    
    @Override
    public int compareTo(Vehiculo otro) {
        // Mayor prioridad primero (orden descendente)
        return Integer.compare(otro.prioridad, this.prioridad);
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getZonaAsignada() {
        return zonaAsignada;
    }
    
    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }
    
    public double getCargaActual() {
        return cargaActual;
    }
    
    public int getPrioridad() {
        return prioridad;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setZonaAsignada(String zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }
    
    public void setCapacidadMaxima(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
    
    public void setCargaActual(double cargaActual) {
        this.cargaActual = cargaActual;
    }
    
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
    
    @Override
    public String toString() {
        return "Vehiculo{" +
                "id='" + id + '\'' +
                ", zona='" + zonaAsignada + '\'' +
                ", capacidad=" + capacidadMaxima +
                ", carga=" + cargaActual +
                ", prioridad=" + prioridad +
                '}';
    }
}
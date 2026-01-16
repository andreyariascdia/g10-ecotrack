import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

/**
 * Sistema principal de gestión de residuos urbanos EcoTrack
 */
public class EcoTrackSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Lista circular de residuos (implementación propia - OBLIGATORIO)
    private ListaCircularDoble<Residuo> residuos;
    
    // Pila para centro de reciclaje - LIFO (OBLIGATORIO)
    private Pila<Residuo> centroReciclaje;
    
    // Cola de prioridad para vehículos (OBLIGATORIO)
    private ColaPrioridad<Vehiculo> vehiculos;
    
    // Mapa para estadísticas y zonas (OBLIGATORIO)
    private Map<String, Zona> zonas;
    private Map<String, Double> estadisticasPorTipo;
    
    // Comparadores
    private Comparator<Residuo> comparadorPeso;
    private Comparator<Residuo> comparadorTipo;
    private Comparator<Residuo> comparadorPrioridad;
    
    public EcoTrackSystem() {
        this.residuos = new ListaCircularDoble<>();
        this.centroReciclaje = new Pila<>();
        this.vehiculos = new ColaPrioridad<>();
        this.zonas = new HashMap<>();
        this.estadisticasPorTipo = new HashMap<>();
        
        // Inicializar comparadores
        this.comparadorPeso = new ComparadorPorPeso();
        this.comparadorTipo = new ComparadorPorTipo();
        this.comparadorPrioridad = new ComparadorPorPrioridadAmbiental();
    }
    
    // ========== GESTIÓN DE RESIDUOS ==========
    
    /**
     * Registra un nuevo residuo en el sistema
     */
    public void registrarResiduo(String id, String nombre, String tipo, double peso,
                                 LocalDate fecha, String zona, int prioridad) {
        Residuo residuo = new Residuo(id, nombre, tipo, peso, fecha, zona, prioridad);
        residuos.agregar(residuo);
        
        // Actualizar estadísticas
        actualizarEstadisticas(tipo, peso);
        
        // Actualizar zona
        agregarResiduoAZona(zona, peso);
    }
    
    /**
     * Agrega un residuo ya creado
     */
    public void agregarResiduo(Residuo residuo) {
        residuos.agregar(residuo);
        actualizarEstadisticas(residuo.getTipo(), residuo.getPeso());
        agregarResiduoAZona(residuo.getZona(), residuo.getPeso());
        
        verificarAlertas(residuo.getZona());
    }
    
    /**
     * Verifica si una zona requiere atención urgente basada en su utilidad
     */
    private void verificarAlertas(String nombreZona) {
        if (zonas.containsKey(nombreZona)) {
            Zona zona = zonas.get(nombreZona);
            double utilidad = zona.calcularUtilidad();
            
            // Si la utilidad es muy negativa
            if (utilidad < -50) {
                System.out.println("⚠️ ALERTA: Zona " + nombreZona + 
                    " requiere atención urgente! Utilidad: " + utilidad);
            }
        }
    }
    
    /**
     * Elimina un residuo del sistema
     */
    public boolean eliminarResiduo(Residuo residuo) {
        return residuos.eliminar(residuo);
    }
    
    /**
     * Obtiene todos los residuos
     */
    public ListaCircularDoble<Residuo> getResiduos() {
        return residuos;
    }
    
    // ========== ORDENAMIENTO ==========
    
    /**
     * Ordena los residuos por peso
     */
    public void ordenarPorPeso() {
        residuos.ordenar(comparadorPeso);
    }
    
    /**
     * Ordena los residuos por tipo
     */
    public void ordenarPorTipo() {
        residuos.ordenar(comparadorTipo);
    }
    
    /**
     * Ordena los residuos por prioridad ambiental
     */
    public void ordenarPorPrioridad() {
        residuos.ordenar(comparadorPrioridad);
    }
    
    // ========== CENTRO DE RECICLAJE (PILA) ==========
    
    /**
     * Envía un residuo al centro de reciclaje
     */
    public void enviarACentroReciclaje(Residuo residuo) {
        centroReciclaje.apilar(residuo);
    }
    
    /**
     * Procesa el último residuo del centro de reciclaje
     */
    public Residuo procesarDeReciclaje() {
        if (!centroReciclaje.estaVacia()) {
            return centroReciclaje.desapilar();
        }
        return null;
    }
    
    /**
     * Ver el próximo residuo a procesar sin eliminarlo
     */
    public Residuo verProximoEnReciclaje() {
        if (!centroReciclaje.estaVacia()) {
            return centroReciclaje.verTope();
        }
        return null;
    }
    
    public Pila<Residuo> getCentroReciclaje() {
        return centroReciclaje;
    }
    
    // ========== GESTIÓN DE VEHÍCULOS (COLA DE PRIORIDAD) ==========
    
    /**
     * Agrega un vehículo a la cola de despacho
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.encolar(vehiculo);
    }
    
    /**
     * Despacha el vehículo de mayor prioridad
     */
    public Vehiculo despacharVehiculo() {
        if (!vehiculos.estaVacia()) {
            return vehiculos.desencolar();
        }
        return null;
    }
    
    /**
     * Ver el próximo vehículo a despachar
     */
    public Vehiculo verProximoVehiculo() {
        if (!vehiculos.estaVacia()) {
            return vehiculos.verPrimero();
        }
        return null;
    }
    
    public ColaPrioridad<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    // ========== GESTIÓN DE ZONAS ==========
    
    /**
     * Agrega o actualiza una zona
     */
    public void agregarZona(String nombre) {
        if (!zonas.containsKey(nombre)) {
            zonas.put(nombre, new Zona(nombre));
        }
    }
    
    /**
     * Agrega residuos pendientes a una zona
     */
    private void agregarResiduoAZona(String nombreZona, double peso) {
        agregarZona(nombreZona);
        Zona zona = zonas.get(nombreZona);
        zona.agregarResiduosPendientes(peso);
    }
    
    /**
     * Recolecta residuos de una zona
     */
    public void recolectarDeZona(String nombreZona, double cantidad) {
        if (zonas.containsKey(nombreZona)) {
            zonas.get(nombreZona).recolectarResiduos(cantidad);
        }
    }
    
    /**
     * Obtiene todas las zonas
     */
    public Map<String, Zona> getZonas() {
        return zonas;
    }
    
    /**
     * Obtiene la zona con menor utilidad (mayor necesidad de atención)
     */
    public Zona getZonaPrioritaria() {
        if (zonas.isEmpty()) {
            return null;
        }
        
        Zona zonaPrioritaria = null;
        double menorUtilidad = Double.MAX_VALUE;
        
        for (Zona zona : zonas.values()) {
            double utilidad = zona.calcularUtilidad();
            if (utilidad < menorUtilidad) {
                menorUtilidad = utilidad;
                zonaPrioritaria = zona;
            }
        }
        
        return zonaPrioritaria;
    }
    
    // ========== ESTADÍSTICAS ==========
    
    /**
     * Actualiza las estadísticas por tipo de residuo
     */
    private void actualizarEstadisticas(String tipo, double peso) {
        estadisticasPorTipo.put(tipo, 
            estadisticasPorTipo.getOrDefault(tipo, 0.0) + peso);
    }
    
    /**
     * Obtiene las estadísticas por tipo
     */
    public Map<String, Double> getEstadisticasPorTipo() {
        return estadisticasPorTipo;
    }
    
    /**
     * Obtiene el peso total de residuos
     */
    public double getPesoTotal() {
        double total = 0.0;
        for (Double peso : estadisticasPorTipo.values()) {
            total += peso;
        }
        return total;
    }
    
    // ========== PERSISTENCIA DE DATOS ==========
    
    /**
     * Guarda el estado del sistema en un archivo
     */
    public void guardarDatos(String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(archivo))) {
            oos.writeObject(this);
        }
    }
    
    /**
     * Carga el estado del sistema desde un archivo
     */
    public static EcoTrackSystem cargarDatos(String archivo) 
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(archivo))) {
            return (EcoTrackSystem) ois.readObject();
        }
    }
    
    /**
     * Limpia todo el sistema
     */
    public void limpiarSistema() {
        residuos.limpiar();
        centroReciclaje.limpiar();
        vehiculos.limpiar();
        zonas.clear();
        estadisticasPorTipo.clear();
    }
}
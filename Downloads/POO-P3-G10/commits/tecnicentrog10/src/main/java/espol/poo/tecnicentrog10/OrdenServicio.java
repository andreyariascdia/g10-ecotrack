package espol.poo.proyectotecnicentrogrupo10.modelo;

import java.time.LocalDate;
import java.util.ArrayList;


public class OrdenServicio {
    private Cliente cliente;
    private Tecnico tecnico;
    private LocalDate fecha;
    private TipoVehiculo tipoVehiculo;
    private String placa;
    private ArrayList<DetalleOrdenServicio> detalles;

    public OrdenServicio(Cliente cliente, Tecnico tecnico, LocalDate fecha, TipoVehiculo tipoVehiculo, String placa) {
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.fecha = fecha;
        this.tipoVehiculo = tipoVehiculo;
        this.placa = placa;
        this.detalles = new ArrayList<>();
    }
    
    // Métodos
    public void agregarDetalle(DetalleOrdenServicio detalle) {
        this.detalles.add(detalle);
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleOrdenServicio detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        return total;
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipo) {
        this.tipoVehiculo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public ArrayList<DetalleOrdenServicio> getDetalles() {
        return detalles;
    }
}
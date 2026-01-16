import java.io.Serializable;

/**
 * Nodo para la lista enlazada circular doblemente enlazada
 */
public class Nodo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private T dato;
    private Nodo<T> siguiente;
    private Nodo<T> anterior;
    
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
    
    // Getters y Setters
    public T getDato() {
        return dato;
    }
    
    public void setDato(T dato) {
        this.dato = dato;
    }
    
    public Nodo<T> getSiguiente() {
        return siguiente;
    }
    
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
    
    public Nodo<T> getAnterior() {
        return anterior;
    }
    
    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }
}
import java.io.Serializable;
import java.util.EmptyStackException;

/**
 * Pila (Stack) LIFO para el centro de reciclaje
 * Los residuos en espera de procesamiento se manejan en orden LIFO
 */
public class Pila<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private NodoPila<T> tope;
    private int tamanio;
    
    /**
     * Clase interna para los nodos de la pila
     */
    private static class NodoPila<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        T dato;
        NodoPila<T> siguiente;
        
        NodoPila(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
    
    public Pila() {
        this.tope = null;
        this.tamanio = 0;
    }
    
    /**
     * Verifica si la pila está vacía
     */
    public boolean estaVacia() {
        return tope == null;
    }
    
    /**
     * Retorna el tamaño de la pila
     */
    public int getTamanio() {
        return tamanio;
    }
    
    /**
     * Agrega un elemento al tope de la pila (push)
     */
    public void apilar(T dato) {
        NodoPila<T> nuevoNodo = new NodoPila<>(dato);
        nuevoNodo.siguiente = tope;
        tope = nuevoNodo;
        tamanio++;
    }
    
    /**
     * Elimina y retorna el elemento del tope de la pila (pop)
     */
    public T desapilar() {
        if (estaVacia()) {
            throw new EmptyStackException();
        }
        
        T dato = tope.dato;
        tope = tope.siguiente;
        tamanio--;
        
        return dato;
    }
    
    /**
     * Retorna el elemento del tope sin eliminarlo (peek)
     */
    public T verTope() {
        if (estaVacia()) {
            throw new EmptyStackException();
        }
        
        return tope.dato;
    }
    
    /**
     * Limpia la pila
     */
    public void limpiar() {
        tope = null;
        tamanio = 0;
    }
    
    /**
     * Busca un elemento en la pila
     */
    public boolean contiene(T dato) {
        NodoPila<T> actual = tope;
        
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "Pila vacía";
        }
        
        StringBuilder sb = new StringBuilder("Tope -> ");
        NodoPila<T> actual = tope;
        
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(" -> ");
            }
            actual = actual.siguiente;
        }
        
        return sb.toString();
    }
}
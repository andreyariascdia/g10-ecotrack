import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterador personalizado para la lista circular
 * Permite iterar hacia adelante y hacia atrás
 */
public class IteradorCircular<T> implements Iterator<T> {
    private Nodo<T> actual;
    private Nodo<T> inicio;
    private boolean adelante; // true = adelante, false = atrás
    private boolean primeraPasada;
    private int elementosRecorridos;
    private int tamanioLista;
    
    /**
     * Constructor del iterador
     * @param lista La lista a iterar
     * @param adelante true para iterar hacia adelante, false hacia atrás
     */
    public IteradorCircular(ListaCircularDoble<T> lista, boolean adelante) {
        this.inicio = lista.getCabeza();
        this.actual = lista.getCabeza();
        this.adelante = adelante;
        this.primeraPasada = true;
        this.elementosRecorridos = 0;
        this.tamanioLista = lista.getTamanio();
    }
    
    @Override
    public boolean hasNext() {
        // Si la lista está vacía
        if (actual == null) {
            return false;
        }
        
        // Si no hemos recorrido todos los elementos
        return elementosRecorridos < tamanioLista;
    }
    
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos");
        }
        
        T dato = actual.getDato();
        
        // Mover al siguiente nodo según la dirección
        if (adelante) {
            actual = actual.getSiguiente();
        } else {
            actual = actual.getAnterior();
        }
        
        elementosRecorridos++;
        primeraPasada = false;
        
        return dato;
    }
    
    /**
     * Reinicia el iterador
     */
    public void reiniciar() {
        actual = inicio;
        primeraPasada = true;
        elementosRecorridos = 0;
    }
    
    /**
     * Retorna el elemento actual sin avanzar
     */
    public T actual() {
        if (actual == null) {
            throw new NoSuchElementException("Lista vacía");
        }
        return actual.getDato();
    }
}
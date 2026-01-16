import java.io.Serializable;
import java.util.Comparator;

/**
 * Lista enlazada circular doblemente enlazada
 * Permite navegación hacia adelante y hacia atrás
 */
public class ListaCircularDoble<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Nodo<T> cabeza;
    private int tamanio;
    
    public ListaCircularDoble() {
        this.cabeza = null;
        this.tamanio = 0;
    }
    
    /**
     * Verifica si la lista está vacía
     */
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    /**
     * Retorna el tamaño de la lista
     */
    public int getTamanio() {
        return tamanio;
    }
    
    /**
     * Agrega un elemento al final de la lista
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        if (estaVacia()) {
            // Si la lista está vacía, el nodo apunta a sí mismo
            cabeza = nuevoNodo;
            nuevoNodo.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(nuevoNodo);
        } else {
            // Obtener el último nodo (anterior a la cabeza)
            Nodo<T> ultimo = cabeza.getAnterior();
            
            // Insertar el nuevo nodo entre el último y la cabeza
            ultimo.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(ultimo);
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
        }
        
        tamanio++;
    }
    
    /**
     * Agrega un elemento al inicio de la lista
     */
    public void agregarAlInicio(T dato) {
        agregar(dato);
        // Mover la cabeza al nuevo nodo
        cabeza = cabeza.getAnterior();
    }
    
    /**
     * Elimina un elemento de la lista
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        
        Nodo<T> actual = cabeza;
        
        do {
            if (actual.getDato().equals(dato)) {
                // Si solo hay un nodo
                if (tamanio == 1) {
                    cabeza = null;
                } else {
                    // Conectar el anterior con el siguiente
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                    
                    // Si se elimina la cabeza, mover la cabeza
                    if (actual == cabeza) {
                        cabeza = actual.getSiguiente();
                    }
                }
                
                tamanio--;
                return true;
            }
            
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        
        return false;
    }
    
    /**
     * Busca un elemento en la lista
     */
    public boolean contiene(T dato) {
        if (estaVacia()) {
            return false;
        }
        
        Nodo<T> actual = cabeza;
        
        do {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        
        return false;
    }
    
    /**
     * Obtiene un elemento por índice
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        
        Nodo<T> actual = cabeza;
        
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        
        return actual.getDato();
    }
    
    /**
     * Limpia la lista
     */
    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }
    
    /**
     * Ordena la lista usando un comparador
     */
    public void ordenar(Comparator<T> comparador) {
        if (estaVacia() || tamanio == 1) {
            return;
        }
        
        boolean intercambio;
        
        do {
            intercambio = false;
            Nodo<T> actual = cabeza;
            
            for (int i = 0; i < tamanio - 1; i++) {
                Nodo<T> siguiente = actual.getSiguiente();
                
                if (comparador.compare(actual.getDato(), siguiente.getDato()) > 0) {
                    // Intercambiar datos
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambio = true;
                }
                
                actual = actual.getSiguiente();
            }
        } while (intercambio);
    }
    
    /**
     * Retorna un iterador para recorrer hacia adelante
     */
    public IteradorCircular<T> iterador() {
        return new IteradorCircular<>(this, true);
    }
    
    /**
     * Retorna un iterador para recorrer hacia atrás
     */
    public IteradorCircular<T> iteradorReverso() {
        return new IteradorCircular<>(this, false);
    }
    
    /**
     * Getter de la cabeza (para el iterador)
     */
    protected Nodo<T> getCabeza() {
        return cabeza;
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;
        
        do {
            sb.append(actual.getDato());
            actual = actual.getSiguiente();
            
            if (actual != cabeza) {
                sb.append(", ");
            }
        } while (actual != cabeza);
        
        sb.append("]");
        return sb.toString();
    }
}
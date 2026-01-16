import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Cola de Prioridad para gestionar los vehículos recolectores
 * La prioridad depende del volumen de residuos o impacto ambiental
 * Implementación usando un heap mínimo
 */
public class ColaPrioridad<T extends Comparable<T>> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<T> heap;
    
    public ColaPrioridad() {
        this.heap = new ArrayList<>();
    }
    
    /**
     * Verifica si la cola está vacía
     */
    public boolean estaVacia() {
        return heap.isEmpty();
    }
    
    /**
     * Retorna el tamaño de la cola
     */
    public int getTamanio() {
        return heap.size();
    }
    
    /**
     * Agrega un elemento a la cola con su prioridad
     */
    public void encolar(T elemento) {
        heap.add(elemento);
        heapifyUp(heap.size() - 1);
    }
    
    /**
     * Elimina y retorna el elemento de mayor prioridad
     */
    public T desencolar() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        
        T elemento = heap.get(0);
        
        // Mover el último elemento al inicio
        T ultimo = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, ultimo);
            heapifyDown(0);
        }
        
        return elemento;
    }
    
    /**
     * Retorna el elemento de mayor prioridad sin eliminarlo
     */
    public T verPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        
        return heap.get(0);
    }
    
    /**
     * Limpia la cola
     */
    public void limpiar() {
        heap.clear();
    }
    
    /**
     * Reorganiza el heap hacia arriba (para inserción)
     */
    private void heapifyUp(int indice) {
        while (indice > 0) {
            int padre = (indice - 1) / 2;
            
            if (heap.get(indice).compareTo(heap.get(padre)) >= 0) {
                break;
            }
            
            // Intercambiar
            T temp = heap.get(indice);
            heap.set(indice, heap.get(padre));
            heap.set(padre, temp);
            
            indice = padre;
        }
    }
    
    /**
     * Reorganiza el heap hacia abajo (para eliminación)
     */
    private void heapifyDown(int indice) {
        int tamanio = heap.size();
        
        while (true) {
            int menor = indice;
            int izquierdo = 2 * indice + 1;
            int derecho = 2 * indice + 2;
            
            if (izquierdo < tamanio && heap.get(izquierdo).compareTo(heap.get(menor)) < 0) {
                menor = izquierdo;
            }
            
            if (derecho < tamanio && heap.get(derecho).compareTo(heap.get(menor)) < 0) {
                menor = derecho;
            }
            
            if (menor == indice) {
                break;
            }
            
            // Intercambiar
            T temp = heap.get(indice);
            heap.set(indice, heap.get(menor));
            heap.set(menor, temp);
            
            indice = menor;
        }
    }
    
    /**
     * Retorna todos los elementos (para visualización)
     */
    public ArrayList<T> obtenerTodos() {
        return new ArrayList<>(heap);
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "Cola vacía";
        }
        
        return "Cola de Prioridad: " + heap.toString();
    }
}
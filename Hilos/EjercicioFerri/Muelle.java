package net.ddns.pablotic;

/**
 *
 * @author PON_TU_NOMBRE_COMPLETO_AQUÍ
 */
import java.util.ArrayList;
import java.util.List;

// recurso compartido (monitor): la carretera deja vehículos y el ferry los embarca
public class Muelle {

    private final String nombre;
    private final List<Integer> buffer = new ArrayList<>(); // vehículos esperando, capacidad infinita

    public Muelle(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // devuelves cuántos vehículos hay esperando
    public synchronized int enEspera() {
        return buffer.size();
    }

    // la carretera deja un vehículo y avisas a quien esté esperando
    // decide si este método tiene que ser synchronized
    public void agregarVehiculo(int v) {
        // ======================= COMPLETAR =======================
        // añades el vehículo al buffer
        // despiertas a los hilos que esten esperando en este monitor (notifyAll)
        // =========================================================
        throw new UnsupportedOperationException("agregarVehiculo: sin implementar");
    }

    // el ferry pide un vehículo que quepa en su hueco; si no hay ninguno, esperas
    // decide si este método tiene que ser synchronized
    public int embarcar(int hueco) throws InterruptedException {
        // ======================= COMPLETAR =======================
        // usas buscarQueQuepa(hueco) para saber si hay algo que quepa (-1 si no)
        // si no hay nada que quepa, esperas con wait()
        // piensa si la condición va en un if o en un while
        // cuando haya uno, lo quitas del buffer con buffer.remove(Integer.valueOf(v))
        // devuelves el vehículo embarcado
        // =========================================================
        throw new UnsupportedOperationException("embarcar: sin implementar");
    }

    // buscas el vehículo más grande que quepa en el hueco; -1 si no hay ninguno
    // ojo: este método no es synchronized; piensa desde dónde lo llamas
    private int buscarQueQuepa(int hueco) {
        for (int tipo = 5; tipo >= 1; tipo--) {
            if (tipo <= hueco && buffer.contains(tipo)) {
                return tipo;
            }
        }
        return -1;
    }
}

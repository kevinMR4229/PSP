package EjercicioFerri.Actividad1;

/**
 *
 * @author KEVIN_MENDEZ_RODRIGUEZ
 */
import java.util.ArrayList;
import java.util.List;

// consumidor: carga en el muelle de origen, cruza, descarga al otro lado y vuelve
public class Ferry extends Thread {

    private final Muelle origen;
    private final String destino;
    private final int capacidad;
    private final int viajes;
    private final int tiempoTrayecto;
    private final List<Integer> cargados = new ArrayList<>();
    private int espacioLibre;
    private final String colorTexto;
    private final String colorNavegar;
    private final String colorConsola = "\u001B[0m";

    public Ferry(String nombre, Muelle origen, String destino, int capacidad, int viajes, int tiempoTrayecto,
            String colorTexto, String colorNavegar) {
        super(nombre); // le das nombre al hilo, así lo reconoces en las trazas
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.viajes = viajes;
        this.tiempoTrayecto = tiempoTrayecto;
        this.espacioLibre = capacidad;
        this.colorTexto = colorTexto;
        this.colorNavegar = colorNavegar;
    }

    @Override
    public void run() {
        try {
            for (int viaje = 1; viaje <= viajes; viaje++) {
                log("viaje " + viaje + "/" + viajes + ": atracado en " + origen.getNombre()
                        + ", hay " + origen.enEspera() + " vehiculos esperando");
                cargar();
                navegar(origen.getNombre(), destino);
                descargar();
                navegar(destino, origen.getNombre());
            }
            log("fin de servicio, has hecho " + viajes + " viajes");
        } catch (InterruptedException e) {
            log("interrumpido, dejas el servicio");
        }
    }

    // cargas hasta llenarte; si no cabe nada, el muelle te deja dormido hasta que llegue algo
    private void cargar() throws InterruptedException {
        while (espacioLibre > 0) {
            int v = origen.embarcar(espacioLibre); // aqui puedes quedarte bloqueado
            cargados.add(v);
            espacioLibre -= v;
            log("carga " + Carretera.nombreVehiculo(v) + " -> huecos libres: " + espacioLibre + "/" + capacidad);
        }
        log("carga completa, zarpas");
    }

    // simulas la travesía con una espera
    private void navegar(String desde, String hasta) throws InterruptedException {
        log(colorNavegar + "~~~ navegando de " + desde + " a " + hasta + " ~~~" + colorConsola);
        Thread.sleep(tiempoTrayecto * 1000L);
    }

    // vacías el ferry en destino: los vehículos salen de la simulación
    private void descargar() {
        while (!cargados.isEmpty()) {
            int v = cargados.remove(0);
            espacioLibre += v;
            log("descarga " + Carretera.nombreVehiculo(v) + " en " + destino);
        }
    }

    private void log(String msg) {
        System.out.println(colorTexto + "[" + getName() + "] " + msg + colorConsola);
    }
}

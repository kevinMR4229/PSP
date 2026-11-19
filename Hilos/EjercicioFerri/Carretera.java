package net.ddns.pablotic;

/**
 *
 * @author PON_TU_NOMBRE_COMPLETO_AQUÍ
 */
import java.util.Random;

// productor: va dejando vehículos en el muelle cada cierto tiempo al azar
public class Carretera extends Thread {

    private final Muelle muelle;
    private final int minTiempo;
    private final int maxTiempo;
    private final Random rand = new Random();

    public Carretera(String nombre, Muelle muelle, int minTiempo, int maxTiempo) {
        super(nombre); // le das nombre al hilo, así lo reconoces en las trazas
        this.muelle = muelle;
        this.minTiempo = minTiempo;
        this.maxTiempo = maxTiempo;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // esperas un tiempo aleatorio (en segundos) entre vehículo y vehículo
                int espera = rand.nextInt(maxTiempo - minTiempo + 1) + minTiempo;
                Thread.sleep(espera * 1000L);

                // generas un vehículo al azar: su número es lo que ocupa en el ferry
                int v = rand.nextInt(5) + 1;

                // avisas por pantalla antes de dejarlo, asi la traza sale en orden
                System.out.println("[" + getName() + "] llega " + nombreVehiculo(v)
                        + " (" + v + (v == 1 ? " plaza" : " plazas") + ") al muelle " + muelle.getNombre());

                // lo dejas en el muelle (recurso compartido con el ferry)
                muelle.agregarVehiculo(v);

            } catch (InterruptedException e) {
                break; // si te interrumpen, sales del bucle y el hilo termina
            }
        }
    }

    // traduces el tamaño al nombre del vehiculo (lo usa tambien el ferry)
    public static String nombreVehiculo(int v) {
        switch (v) {
            case 5: return "CAMION";
            case 4: return "MINIBUS";
            case 3: return "FURGONETA";
            case 2: return "COCHE";
            case 1: return "MOTO";
            default: return "DESCONOCIDO";
        }
    }
}

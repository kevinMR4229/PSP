package EjercicioFerri.Actividad1;

/**
 *
 * @author KEVIN_MENDEZ_RODRIGUEZ
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        String verde = "\u001B[92m"; //Color Carretera Norte
        String amarillo = "\u001B[93m"; //Color Ferry
        String cian = "\u001B[96m"; //Ferry navegando

        // ======================= COMPLETAR =======================
        // creas el muelle: new Muelle("Norte")
        Muelle muelleNorte = new Muelle("Norte");
        // creas el productor: new Carretera("CARRETERA", muelle, 1, 3)
        Carretera carreteraNorte = new Carretera("CARRETERA_NORTE", muelleNorte, 1, 3, verde);
        // creas el consumidor: new Ferry("FERRY", muelle, "Sur", 20, 3, 3)
        Ferry ferry = new Ferry("FERRY", muelleNorte, "Sur", 20, 3, 3, amarillo, cian);
        // haces la carretera daemon (setDaemon) para que el programa acabe cuando acabe el ferry
        carreteraNorte.setDaemon(true);
        // lanzas los dos hilos (start)
        carreteraNorte.start();
        ferry.start();
        // esperas a que termine el ferry (join) y muestras un mensaje final
        ferry.join();
        System.out.println("Terminado");
        // =========================================================
    }
}

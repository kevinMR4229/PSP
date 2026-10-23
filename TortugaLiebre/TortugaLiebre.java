/* Ejemplo 1: creación y vida de un hilo.
   La tortuga y la liebre corren cada una en su hilo. El main hace de juez
   y va mirando en que estado está cada una hasta que terminan las dos. */

// primera forma de crear un hilo: implementar Runnable (aquí solo se dice qué hay que hacer)
class Tortuga implements Runnable {

    public void run() {
        for (int paso = 1; paso <= 10; paso++) {
            System.out.println("Tortuga: paso " + paso);
            try {
                Thread.sleep(200);   // va despacio pero no se para
            } catch (InterruptedException e) {
            }
        }
        System.out.println(">>> La TORTUGA llega a la meta");
    }
}

/* segunda forma: heredar de Thread.
   Aquí la propia clase ya es un hilo, no hace falta pasarle ningún Runnable */
class Liebre extends Thread {

    public void run() {
        for (int paso = 1; paso <= 10; paso++) {
            System.out.println("Liebre:  paso " + paso);
            try {
                if (paso == 5) {
                    // a mitad de carrera se confía y se echa una siesta de entre 500 y 2000 ms
                    int siesta = 500 + (int) (Math.random() * 1500);
                    System.out.println("Liebre:  se echa una siesta de " + siesta + " ms...");
                    Thread.sleep(siesta);
                } else {
                   Thread.sleep(50);   // el resto de pasos son rapidos
                }
            } catch (InterruptedException e) {
            }
        }
        System.out.println(">>> La LIEBRE llega a la meta");
    }
}

class Ejemplo1_CarreraTortugas {

    public static void main(String[] args) throws InterruptedException {

        // aquí se crean los hilos, pero todavía no corren
        Thread tortuga = new Thread(new Tortuga(), "Tortuga");
        Liebre liebre = new Liebre();
        liebre.setName("Liebre");

        System.out.println("Antes de start():   tortuga=" + tortuga.getState() + "   liebre=" + liebre.getState());

        System.out.println("¡Preparados, listos, ya!");
        tortuga.start();   // start() es lo que lanza el hilo de verdad
        liebre.start();    // si llamamos a run() directamente no se crea ningún hilo

        // mientras siga viva alguna, el juez mira cómo van cada medio segundo
        while (tortuga.isAlive() || liebre.isAlive()) {
            Thread.sleep(500);
            System.out.println("   [juez] tortuga=" + tortuga.getState() + "   liebre=" + liebre.getState());
        }

        // aquí esperamos a que terminen los dos (ya han acabado, así que no espera nada)
        tortuga.join();
        liebre.join();

        System.out.println("Al final:           tortuga=" + tortuga.getState() + "   liebre=" + liebre.getState());
        System.out.println("Carrera terminada");
    }
}
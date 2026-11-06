package LosTresFrancotiradores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Francotirador implements Runnable {

    private volatile boolean verde = false;

    @Override
    public void run() {

        try {
            while (true) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000 + 1));
                verde = true;

                Thread.sleep(ThreadLocalRandom.current().nextInt(600, 1100 + 1));
                verde = false;
            }

        } catch (InterruptedException e) {
            if (verde) {
                System.out.println("OBJETIVO ELIMINADO");
            } else {
                System.out.println("FALLO!");
            }
        }

    }

    public boolean isVerde() {
        return this.verde;
    }

}

class Padre {

    private static final int CANT_FRAN = 3;

    public static void main(String[] args) throws InterruptedException {
        boolean ejecutado = false;

        boolean listos = false;
        List<Francotirador> francotiradores = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        List<Boolean> estadoAnterior = new ArrayList<>();
        List<Boolean> nuevoEstado = new ArrayList<>();

        // aquí se crean los hilos, pero todavía no corren
        for (int i = 0; i < CANT_FRAN; i++) {
            Francotirador francotirador = new Francotirador();

            francotiradores.add(francotirador);
            hilos.add(new Thread(francotirador, "f" + i + 1));
        }

        // Arranco los hilos
        System.out.println("Comiencen a apuntar");
        for (int i = 0; i < hilos.size(); i++) {
            hilos.get(i).start();
        }

        // Guardo el primer estado de los francotiradores
        for (int i = 0; i < hilos.size(); i++) {
            estadoAnterior.add(francotiradores.get(i).isVerde());
        }

        // Muestro el primer estado de los francotiradores
        for (int i = 0; i < hilos.size(); i++) {
            System.out.print(francotiradores.get(i).isVerde() ? "VERDE|" : "ROJO|");
        }
        System.out.println();

        while (!listos) {
            // Espero 50 milisegundo siempre antes de comprobar
            Thread.sleep(50);

            // Guardo el nuevo estado de los francotiradores luego de esperar
            nuevoEstado.clear();
            for (int i = 0; i < hilos.size(); i++) {
                nuevoEstado.add(francotiradores.get(i).isVerde());
            }

            // Compruebo si algun francotirador cambio su estado. Si es el caso guardo el
            // nuevo estado en el anterior y los muestro por consola

            if (!estadoAnterior.equals(nuevoEstado)) {
                estadoAnterior.clear();
                estadoAnterior.addAll(nuevoEstado);

                for (Boolean verde : nuevoEstado) {
                    System.out.print(verde ? "VERDE|" : "ROJO|");
                }
                System.out.println();
            }

            // Compruebo si todos estan listos

            listos = francotiradores.stream()
                    .allMatch(f -> f.isVerde());
        }

        Thread.sleep(100);
        System.out.println("FUEGOOO!!!!");

        // Los francotiradores confirman si acertaron o fallaron

        for (int i = 0; i < hilos.size(); i++) {
            hilos.get(i).interrupt();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println();

        // Compruebo que los tres hayan acertado
        ejecutado = francotiradores.stream()
                .allMatch(f -> f.isVerde());

        if (ejecutado) {
            System.out.println("MISIÓN CUMPLIDA!");
        } else {
            System.out.println("FRACASO ABSOLUTO");
        }

    }

}

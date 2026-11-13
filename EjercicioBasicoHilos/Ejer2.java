package EjercicioBasicoHilos;

import java.util.ArrayList;
import java.util.List;

class Hijo2 implements Runnable {

    private volatile boolean rapido;

    public Hijo2(Boolean rapido) {
        this.rapido = rapido;
    }

    @Override
    public void run() {

        if (rapido) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }

        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {

            }

        }

    }

}

public class Ejer2 {

    public static void main(String[] args) throws InterruptedException {
        List<Hijo2> hijos = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        

        // Creo los hijos
        for (int i = 1; i <= 2; i++) {
            Hijo2 hijo = new Hijo2(i == 1 ? true : false);

            hijos.add(hijo);
            hilos.add(new Thread(hijos.get(i-1), "Pepe" + i, i == 1 ? null : hilos.get(i-1)));
        }


        System.out.println("Estado antes de iniciarse de " + hilos.get(0) + ":" + hilos.get(0).isAlive());
        System.out.println("Estado antes de iniciarse de " + hilos.get(1) + ":" + hilos.get(1).isAlive());

        for (int i = 0; i < hilos.size(); i++) {
            hilos.get(i).start();
        }

        System.out.println("Estado antes de iniciarse de " + hilos.get(0) + ":" + hilos.get(0).isAlive());
        System.out.println("Estado antes de iniciarse de " + hilos.get(1) + ":" + hilos.get(1).isAlive());

        Thread.sleep(3000);

        System.out.println("Estado antes de iniciarse de " + hilos.get(0) + ":" + hilos.get(0).isAlive());
        System.out.println("Estado antes de iniciarse de " + hilos.get(1) + ":" + hilos.get(1).isAlive());

    }

}

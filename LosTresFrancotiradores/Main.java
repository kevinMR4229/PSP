package LosTresFrancotiradores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Francotirador implements Runnable {

    volatile boolean verde = false;
    boolean eliminado = false;

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
                System.out.println("acertado");
            } else {
                System.out.println("fallado");
            }
        }

    }

}

class Padre {

    public static void main(String[] args) throws InterruptedException {
        boolean ejecutado = false;

        List<Thread> lista = new ArrayList<>();


        // aquí se crean los hilos, pero todavía no corren
        lista.add(new Thread(new Francotirador(), "f1"));
        lista.add(new Thread(new Francotirador(), "f2"));
        lista.add(new Thread(new Francotirador(), "f3"));

        //Arranco los hilos
        System.out.println("Comiencen a apuntar");
        f1.start();
        f2.start();
        f3.start();

        while (!ejecutado) {
            Thread.sleep(50);
            System.out.println(f1.);

            
        }

        
    }

    public String color(Thread thread){
        return thread.
    }
}

package EjercicioBasicoHilos;

import java.util.ArrayList;
import java.util.List;

class Hijo implements Runnable {

    private volatile boolean yes;

    public Hijo(Boolean yes) {
        this.yes = yes;
    }

    @Override
    public void run() {

        if (yes) {
            //for (int i = 0; i < 1000; i++) {
            //    System.out.print("A");
            //    
            //}

            while (true) {
                System.out.print("A");
            }

        } else {
            //for (int i = 0; i < 1000; i++) {
            //    System.out.print("B");
            //    
            //}

            while (true) {
                System.out.print("-");
            }

        }

    }

}

public class Ejer1 {

    public static void main(String[] args) {

        List<Hijo> hijos = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        List<Integer> prioridades = List.of(Thread.MIN_PRIORITY, Thread.MAX_PRIORITY);

        // Creo los hijos
        for (int i = 1; i <= 2; i++) {
            Hijo hijo = new Hijo(i==1?true:false);

            hijos.add(hijo);
            hilos.add(new Thread(hijo, "Pepe" + i));
            hilos.get(i-1).setPriority(prioridades.get(i-1));
        }
        
        for (int i = 0; i < hilos.size(); i++) {
            hilos.get(i).start();
        }

    }

}

package Procesos.Ejercicio1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Cuantos notepads quieres");

            int cantidad = sc.nextInt();
            sc.nextLine();

            String ruta = "notepad";

            ProcessBuilder builder = new ProcessBuilder(ruta);

            Map<Long, Process> procesos = new HashMap<>();
            List<Long> ids = new ArrayList<>();

            for(int i=1; i<=cantidad; i++){
                Process proceso = builder.start();
                long id = proceso.pid();

                procesos.put(id, proceso);
                ids.add(id);

                System.out.println(String.format("Proceso con id: %s iniciado", id));
            }

            while (!procesos.isEmpty()) {
                for(int i=0; i<procesos.size(); i++){

                    if (!procesos.get(ids.get(i)).isAlive()) {
                        
                        System.out.println(String.format("Proceso con id: %s finalizado", ids.get(i)));

                        procesos.remove(ids.get(i));
                        ids.remove(i);
                        i--;
                    }
                    
                }

                
            }
        }
        
    }

}
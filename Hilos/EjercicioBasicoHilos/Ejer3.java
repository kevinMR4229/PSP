package Hilos.EjercicioBasicoHilos;

import java.util.ArrayList;
import java.util.List;

class Hilo extends Thread {
    private String vocal;
    private int contadorLocal = 0; // Cada hilo cuenta las suyas
    private String texto;

    public Hilo(String texto, String vocal) {
        this.texto = texto;
        this.vocal = vocal;
    }

    @Override
    public void run() {
        texto = texto.trim();

        for (int i = 0; i < texto.length(); i++) {
            // equalsIgnoreCase detecta tanto 'O' como 'o'
            if (String.valueOf(texto.charAt(i)).equalsIgnoreCase(vocal)) {
                contadorLocal++;
            }
        }  
    }

    // Método para recuperar el resultado de este hilo
    public int getContadorLocal() {
        return contadorLocal;
    }
}    

public class Ejer3 {    
    public static void main(String[] args) throws InterruptedException {
        int totalVocales = 0;
        List<String> vocales = List.of("a", "e", "i", "o", "u");
        String texto = "Otorrinolaringologia";
        List<Hilo> hilos = new ArrayList<>(); // Tipado a Hilo para poder llamar a getContadorLocal()

        // 1. Crear e iniciar todos los hilos a la vez
        for (int i = 0; i < 5; i++) {
            Hilo hilo = new Hilo(texto, vocales.get(i));
            hilos.add(hilo);
            hilo.start();
        }

        // 2. Esperar a que todos terminen y sumar sus resultados
        for (Hilo hilo : hilos) {
            hilo.join();
            totalVocales += hilo.getContadorLocal();
        }

        System.out.println(String.format("Hay %d vocales", totalVocales)); // Salida: Hay 10 vocales
    }
}

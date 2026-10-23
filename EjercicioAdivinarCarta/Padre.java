package EjercicioAdivinarCarta;

import java.io.IOException;
import java.util.List;

public class Padre {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> cartas = List.of("As Oro", "Caballo Basto", "3 Espada");

        // Creo el hijo pensador
        String classpathPensador = System.getProperty("java.class.path");
        ProcessBuilder constructorPensador = new ProcessBuilder("java", "-cp", classpathPensador,
                "EjercicioAdivinarCarta.Hijo","pensador");

        // Creo el hijo adivinador
        String classpathAdivinador = System.getProperty("java.class.path");
        ProcessBuilder constructorAdivinador = new ProcessBuilder("java", "-cp", classpathAdivinador,
                "EjercicioAdivinarCarta.Hijo","adivinador");


        //Comparten consola
        constructorPensador.inheritIO();
        //Comparten consola
        constructorAdivinador.inheritIO();
        


        // Inico el proceso
        Process proceso1 = constructorPensador.start();
        int codigoSalida1 = proceso1.waitFor();

        // Inico el proceso
        Process proceso2 = constructorAdivinador.start();
        int codigoSalida2 = proceso2.waitFor();
    }
}

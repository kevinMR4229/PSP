package EjercicioBuscaPalabra;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);

        //Pregunto el nombre del fichero
        System.out.println("Dame el nombre del fichero");
        String nomFichero = sc.nextLine();

        //Pregunto las palabras
        System.out.println("Dame las palabras");
        String palabras = sc.nextLine();


        //Creo el proceso
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder constructor = new ProcessBuilder("java", "-cp", classpath, "EjercicioMuevo.Buscapalabra", nomFichero, palabras);

        //Comparten consola
        constructor.inheritIO();

        //Inico el proceso
        Process proceso = constructor.start();
        int codigoSalida = proceso.waitFor();

    }

}

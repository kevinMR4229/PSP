package EjercicioBuscaPalabra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainPro {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        int salida = 1;
        String palabra;

        List<String> palabras = new ArrayList<>();

        //Pregunto el nombre del fichero
        System.out.println("Dame el nombre del fichero");
        String nomFichero = sc.nextLine();

        //Pregunto las palabras
        while (salida == 1) {
            System.out.println("Dame la palabra");
            palabra = sc.nextLine();
            palabras.add(palabra);

            System.out.println("Para agregar otra 1 \n Para terminar 2");

            salida = sc.nextInt();
            sc.nextLine();

        }
        


        //Creo el proceso
        String classpath = System.getProperty("java.class.path");
        List<ProcessBuilder> constructores = new ArrayList<>();

        for (int i =0; i < palabras.size(); i++){
            constructores.add(new ProcessBuilder("java", "-cp", classpath, "EjercicioMuevo.BuscaPalabra", nomFichero, palabras.get(i)));

            //Comparten consola
            constructores.get(i).inheritIO();

            //Inico el proceso
            Process proceso = constructores.get(i).start();
            //int codigoSalida = proceso.waitFor();
        }
        

        

        

    }
}

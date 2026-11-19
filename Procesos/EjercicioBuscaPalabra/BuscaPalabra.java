package EjercicioBuscaPalabra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BuscaPalabra {

    public static void main(String[] args) throws IOException {
        String nomFichero = args[0]; 
        String palabra = args[1];

        String ruta = "EjercicioMuevo/Ficheros/" + nomFichero + ".txt";

        Stream<String> todasLineas = Files.lines(Paths.get(ruta));

        var coincLineas = todasLineas.map(l -> l.toLowerCase())
        .filter(l -> l.contains(palabra))
        .toList();


        for (int i =0; i < coincLineas.size(); i++){
            System.out.println(coincLineas.get(i));
        }

        System.out.println("La palabra " + palabra + " se repite " + coincLineas.size());
    }
}

package EjercicioAdivinarCarta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class Hijo {
    public static void main(String[] args) throws IOException, InterruptedException {
        String comportamiento = args[0];

        List<String> cartas = List.of(args[1], args[2], args[3]);

        boolean acertado = false;

        Random random = new Random();

        String cartaElegida;






        // Comportamiento pensador
        if (comportamiento.equals("pensador")) {

            //Genero un numero random para elegir la carta
            int indice = random.nextInt(cartas.size());

            //guardo la carta
            cartaElegida = cartas.get(indice);

            Thread.sleep(1000);

            //aviso que ya ejigio la carta
            System.out.println("LISTO");

            BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));


            //Escucha al padre y le dice si acerto o no 
            while (!acertado) {
                
                String respuestaPadre = reader.readLine();

                if (respuestaPadre == null) {
                    break;
                }

                if (respuestaPadre.equals(cartaElegida)) {
                    System.out.println("Acertaste");
                    acertado = true;
                } else {
                    System.out.println("Fallaste");
                }

            }

        }






        // Comportamiento adivinador
        if (comportamiento == "adivinador") {

            //Genero un numero random para elegir la carta
            int indice = random.nextInt(cartas.size());

            //guardo la carta
            cartaElegida = cartas.get(indice);

            Thread.sleep(1000);

            //Manda la carta que eligio
            System.out.println(cartaElegida);

            BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));


            //Escucha al padre a ver si acerto
            while (!acertado) {
                
                String respuestaPadre = reader.readLine();

                if (respuestaPadre == null) {
                    break;
                }

                if (respuestaPadre.equals(cartaElegida)) {
                    System.out.println("Acertaste");
                    acertado = true;
                } else {
                    System.out.println("Fallaste");
                }

            }
        }
    }
}


package EjercicioAdivinarCarta;

import java.io.*;
import java.util.*;

public class Hijo {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        String comportamiento = args[0];

        // Recibo las cartas que ha enviado el padre.
        List<String> cartas = new ArrayList<>(
                Arrays.asList(args).subList(1, args.length)
        );

        Random random = new Random();

        // Streams para comunicarse con el padre.
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        PrintWriter writer = new PrintWriter(System.out, true);

        // COMPORTAMIENTO PENSADOR
        if (comportamiento.equals("pensador")) {

            // Escoge una carta aleatoria.
            int indice = random.nextInt(cartas.size());
            String cartaElegida = cartas.get(indice);

            // Avisa al padre sin revelar la carta.
            writer.println("LISTO");

            boolean acertado = false;

            while (!acertado) {

                // Recibe una carta del padre.
                String respuestaPadre = reader.readLine();

                if (respuestaPadre == null) {
                    break;
                }

                // Retraso de 1 a 2 segundos.
                Thread.sleep(random.nextInt(1000, 2001));

                // Comprueba la carta y responde al padre.
                if (respuestaPadre.equals(cartaElegida)) {
                    writer.println("SI");
                    acertado = true;
                } else {
                    writer.println("NO");
                }
            }
        }

        // COMPORTAMIENTO ADIVINADOR
        else if (comportamiento.equals("adivinador")) {

            boolean acertado = false;

            while (!acertado && !cartas.isEmpty()) {

                // Escoge aleatoriamente una carta disponible.
                int indice = random.nextInt(cartas.size());
                String cartaElegida = cartas.get(indice);

                // Descarta la carta para no repetirla.
                cartas.remove(indice);

                // Envía su intento al padre.
                writer.println(cartaElegida);

                // Espera la respuesta del padre.
                String respuestaPadre = reader.readLine();

                if (respuestaPadre == null) {
                    break;
                }

                if (respuestaPadre.equals("SI")) {
                    acertado = true;
                } else if (respuestaPadre.equals("FIN")) {
                    // Otro adivinador ha ganado.
                    break;
                } else if (respuestaPadre.equals("NO")) {
                    // Espera aleatoria antes del siguiente intento.
                    Thread.sleep(random.nextInt(500, 1501));
                }
            }
        }
    }
}


package EjercicioAdivinarCarta;

import java.io.*;
import java.util.*;

public class Padre {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        List<String> cartas = List.of(
                "As Oro",
                "Caballo Basto",
                "3 Espada"
        );

        List<Process> adivinadores = new ArrayList<>();
        List<BufferedReader> readersAdiv = new ArrayList<>();
        List<PrintWriter> writersAdiv = new ArrayList<>();

        // Cartas ya comprobadas por el pensador.
        Set<String> cartasProbadas = new HashSet<>();

        String classpath = System.getProperty("java.class.path");

        System.out.println("Cuantos adivinadores quieres lanzar?");
        int cantidad = sc.nextInt();

        if (cantidad < 1) {
            System.out.println("Debe existir al menos un adivinador.");
            sc.close();
            return;
        }

        // Construyo los argumentos comunes para los hijos.
        List<String> comandoBase = new ArrayList<>();
        comandoBase.add("java");
        comandoBase.add("-cp");
        comandoBase.add(classpath);
        comandoBase.add("EjercicioAdivinarCarta.Hijo");

        // CREACIÓN DEL PENSADOR
        List<String> comandoPensador = new ArrayList<>(comandoBase);
        comandoPensador.add("pensador");
        comandoPensador.addAll(cartas);

        ProcessBuilder constructorPensador =
                new ProcessBuilder(comandoPensador);

        // Los errores se muestran en la consola del padre.
        constructorPensador.redirectError(
                ProcessBuilder.Redirect.INHERIT
        );

        Process pensador = constructorPensador.start();

        // Streams del pensador.
        BufferedReader readerPensador = new BufferedReader(
                new InputStreamReader(pensador.getInputStream())
        );

        PrintWriter writerPensador = new PrintWriter(
                pensador.getOutputStream(), true
        );

        // CREACIÓN DE LOS ADIVINADORES
        List<String> comandoAdivinador = new ArrayList<>(comandoBase);
        comandoAdivinador.add("adivinador");
        comandoAdivinador.addAll(cartas);

        ProcessBuilder constructorAdivinador =
                new ProcessBuilder(comandoAdivinador);

        constructorAdivinador.redirectError(
                ProcessBuilder.Redirect.INHERIT
        );

        for (int i = 0; i < cantidad; i++) {

            // Inicio un proceso independiente por adivinador.
            Process proceso = constructorAdivinador.start();
            adivinadores.add(proceso);

            // Stream para recibir sus intentos.
            readersAdiv.add(new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            ));

            // Stream para enviarle SI, NO o FIN.
            writersAdiv.add(new PrintWriter(
                    proceso.getOutputStream(), true
            ));

            System.out.println(
                    "Adivinador " + (i + 1) +
                    " iniciado. PID: " + proceso.pid()
            );
        }

        // Espero a que el pensador haya seleccionado la carta.
        String estadoPensador = readerPensador.readLine();

        if (!"LISTO".equals(estadoPensador)) {
            System.out.println("El pensador no pudo iniciarse.");
            return;
        }

        System.out.println("Pensador: LISTO");
        System.out.println("Comienza el juego.");

        int intentos = 0;
        boolean acertado = false;
        String cartaFinal = "";
        int ganador = -1;

        // COMUNICACIÓN ENTRE LOS PROCESOS
        while (!acertado) {

            boolean mensajeRecibido = false;

            for (int i = 0; i < adivinadores.size(); i++) {

                BufferedReader reader = readersAdiv.get(i);

                // Evito bloquear al padre esperando a un hijo
                // que todavía no ha enviado ninguna carta.
                if (!reader.ready()) {
                    continue;
                }

                String cartaElegida = reader.readLine();

                if (cartaElegida == null) {
                    continue;
                }

                mensajeRecibido = true;
                intentos++;

                System.out.println(
                        "Adivinador " + (i + 1) +
                        " intenta: " + cartaElegida
                );

                String respuesta;

                // Si otro adivinador ya probó esta carta,
                // sabemos que era incorrecta y evitamos
                // preguntar otra vez al pensador.
                if (cartasProbadas.contains(cartaElegida)) {
                    respuesta = "NO";
                    System.out.println(
                            "Padre: carta ya descartada."
                    );
                } else {

                    cartasProbadas.add(cartaElegida);

                    // Envío la carta al pensador.
                    writerPensador.println(cartaElegida);

                    // Espero la respuesta del pensador.
                    respuesta = readerPensador.readLine();

                    if (respuesta == null) {
                        throw new IOException(
                                "El pensador terminó inesperadamente."
                        );
                    }
                }

                System.out.println("Pensador/Padre: " + respuesta);

                // Comunico la respuesta al adivinador.
                writersAdiv.get(i).println(respuesta);

                if (respuesta.equals("SI")) {

                    acertado = true;
                    ganador = i;
                    cartaFinal = cartaElegida;

                    break;
                }
            }

            // Si todavía no hay respuestas disponibles,
            // dejo de consultar continuamente los streams.
            if (!acertado && !mensajeRecibido) {
                Thread.sleep(50);
            }
        }

        // AVISO A LOS DEMÁS ADIVINADORES
        for (int i = 0; i < adivinadores.size(); i++) {

            if (i != ganador) {
                writersAdiv.get(i).println("FIN");
            }
        }

        System.out.println();
        System.out.println("========== RESULTADO ==========");
        System.out.println("Carta correcta: " + cartaFinal);
        System.out.println("Ganador: Adivinador " + (ganador + 1));
        System.out.println("Numero de intentos: " + intentos);
        System.out.println("================================");

        // Espero la finalización de todos los procesos hijos.
        pensador.waitFor();

        for (Process proceso : adivinadores) {
            proceso.waitFor();
        }

        readerPensador.close();
        writerPensador.close();

        for (BufferedReader reader : readersAdiv) {
            reader.close();
        }

        for (PrintWriter writer : writersAdiv) {
            writer.close();
        }

        sc.close();

        System.out.println("Todos los procesos han terminado.");
    }
}

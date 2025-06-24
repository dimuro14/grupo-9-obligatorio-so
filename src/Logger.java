import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class Logger {
    private static final String ARCHIVO = "log_simulacion.txt";

    public static synchronized void limpiarArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            bw.write("==== INICIO DE SIMULACION ====\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void log(String mensaje) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String tiempo = LocalTime.now().toString();
            bw.write("[" + tiempo + "] " + mensaje + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

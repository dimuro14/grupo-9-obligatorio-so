public class Estadisticas {
    private static int totalPacientes = 0;
    /** Llamar cuando un paciente empieza a ser atendido */
    public synchronized static void sumarPaciente() {
        totalPacientes++;
    }

    /** Al final de toda la simulación */
    public synchronized static void report() {
        double promPacientes    = totalPacientes > 0 
                           ? (double) totalPacientes    / 12 
                           : 0;

        Logger.log("=== ESTADISTICAS DE LA SIMULACIÓN ===");
        Logger.log("Total pacientes atendidos: " + totalPacientes);
        Logger.log(String.format("Pacientes atendidos por hora promedio:", promPacientes));
        Logger.log("===========================");
    }
}

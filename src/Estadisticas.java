public class Estadisticas {
    private static int totalPacientes = 0;
    private static long totalTiempoEspera = 0;
    private static long totalTiempoAtencion = 0;

    public synchronized static void sumarPaciente() { totalPacientes++; }
    public synchronized static void sumarTiempoEspera(int t) { totalTiempoEspera += t; }
    public synchronized static void sumarTiempoAtencion(long t) { totalTiempoAtencion += t; }

    public synchronized static void report() {
        double promPacientesHora = totalPacientes > 0 ? (double) totalPacientes/12 : 0;
        double promEspera = totalPacientes > 0 ? (double)totalTiempoEspera/totalPacientes : 0;
        double promAtencion = totalPacientes > 0 ? (double)totalTiempoAtencion/totalPacientes : 0;

        Logger.log("=== ESTADÍSTICAS DE LA SIMULACIÓN ===");
        Logger.log("Total pacientes atendidos: " + totalPacientes);
        Logger.log(String.format("Pacientes/hora promedio: %.2f", promPacientesHora));
        Logger.log(String.format("Espera promedio (h): %.2f", promEspera));
        Logger.log(String.format("Atención promedio (ms): %.2f", promAtencion));
        Logger.log("====================================");
    }
}

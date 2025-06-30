public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        
        //Inicio el logger para llevar registro de la simulacion
        Logger.limpiarArchivo();  // Se borra el contenido anterior al iniciar
        Logger.log("Simulación iniciada");
        
        //Variables de estadisticas
        int promedioEspera = 0;
        int tiempoCPU = 0;
        int totalAtendidos = 0;

        // Elegí qué simulacro ejecutar
        int nroInicialPacientes;
        int pacientesPorHora;
        boolean salaReservadaEmergencia;
        int tiempoConsulta = 200;
        boolean odontologo = false;

        //Simulacro 0 - Test
        nroInicialPacientes = 1;         // Emergencia, Control, Análisis, Análisis
        pacientesPorHora = 1;
        salaReservadaEmergencia = true;

        // ---------------------------
        //Simulacro 1 – Día normal
        //nroInicialPacientes = 4;         // Emergencia, Control, Análisis, Análisis
        //pacientesPorHora = 2;
        //salaReservadaEmergencia = true;

        // ---------------------------
        // Simulacro 2 – Sin sala de emergencia
        // nroInicialPacientes = 5;
        // pacientesPorHora = 1;
        // salaReservadaEmergencia = false;

        // ---------------------------
        //Simulacro 3 – Carga crítica
        // nroInicialPacientes = 10;
        // pacientesPorHora = 5;
        // salaReservadaEmergencia = true;
        
        CentroMedico.start(nroInicialPacientes, pacientesPorHora, tiempoConsulta, salaReservadaEmergencia, odontologo);

        //Estadisticas finales para el logger
        Logger.log("==== ESTADÍSTICAS ====");
        Logger.log("Tiempo promedio de espera: " + promedioEspera + " minutos");
        Logger.log("Uso total de CPU (tiempo ocupado): " + tiempoCPU + " minutos");
        Logger.log("Cantidad de pacientes atendidos: " + totalAtendidos);
    
    }

}

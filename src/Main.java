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
        
        int nroInicialPacientes = 1;
        int pacientesPorHora = 1;
        int tiempoConsulta = 0;
        boolean salaReservadaEmergencia = false;
        
        CentroMedico.start(nroInicialPacientes, pacientesPorHora);

        //Estadisticas finales para el logger
        Logger.log("==== ESTADÍSTICAS ====");
        Logger.log("Tiempo promedio de espera: " + promedioEspera + " minutos");
        Logger.log("Uso total de CPU (tiempo ocupado): " + tiempoCPU + " minutos");
        Logger.log("Cantidad de pacientes atendidos: " + totalAtendidos);
    
    }

}

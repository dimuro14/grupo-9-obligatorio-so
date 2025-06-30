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
    
    }

}

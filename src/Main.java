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

        int nroInicialPacientes;
        int pacientesPorHora;
        int tiempoConsulta;
        int numMedicos;
        int numEnfermeros;
        boolean salaReservadaEmergencia;
        boolean odontologo;

        // Simulacro 1 – Día normal
        nroInicialPacientes = 4;
        pacientesPorHora = 2;
        tiempoConsulta = 200;
        numMedicos = 2;
        numEnfermeros = 1;
        salaReservadaEmergencia = true;
        odontologo = false;

        // Simulacro 2 – Sin sala de emergencia
        //nroInicialPacientes = 5;
        //pacientesPorHora = 1;
        //tiempoConsulta = 200;
        //numMedicos = 2;
        //numEnfermeros = 1;
        //salaReservadaEmergencia = false;
        //odontologo = false;

        // Simulacro 3 – Carga crítica
        // nroInicialPacientes = 10;
        // pacientesPorHora = 5;
        // tiempoConsulta = 200;
        // numMedicos = 2;
        // numEnfermeros = 1;
        // salaReservadaEmergencia = true;
        // odontologo = false;
        
        CentroMedico.start(nroInicialPacientes, pacientesPorHora, tiempoConsulta, numMedicos, numEnfermeros, salaReservadaEmergencia, odontologo);
        
    }

}

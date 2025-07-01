import java.util.concurrent.Semaphore;

public class CentroMedico {
	
	//Añadir constructor con parámetros?

	@SuppressWarnings("unused")
    public static void start(int nroInicialPacientes, int pacientesPorHora, int tiempoConsulta, boolean salaReservadaEmergencia, boolean odontologo) throws Exception {

		Semaphore apertura = new Semaphore(0);

		Semaphore medicos = new Semaphore(2);
		Semaphore enfermeros = new Semaphore(1);

		// Arrancamos monitor en un hilo aparte (ej. cada 1 s)
        MonitorRecursos monitor = new MonitorRecursos(1000);
        Thread hiloMon = new Thread(monitor, "ResourceMonitor");
        hiloMon.start();

		//Inicializar MLQ
		MLQ mlq = new MLQ();

		//De 8:00 a 20:00
		Horario horario = new Horario(mlq, nroInicialPacientes, pacientesPorHora, odontologo, apertura, monitor);
		horario.start();

		apertura.acquire();

		ConsultorioMedico consultorio1 = new ConsultorioMedico("Consultorio Médico 1", horario, mlq, medicos, enfermeros);
		consultorio1.start();

		//ConsultorioMedico consultorio2 = new ConsultorioMedico("Consultorio Médico 2", horario, mlq, medicos, enfermeros, odontologo);
		//consultorio2.start();

		SalaEnfermeria salaEnfermeria = new SalaEnfermeria("Sala de Enfermería 1", horario, mlq, enfermeros);
		salaEnfermeria.start();
		
		if (salaReservadaEmergencia == true) {
			
			//SalaEmergencia salaEmergencia = new SalaEmergencia("Sala de Emergencia", horario, mlq, medicos, enfermeros);
			//salaEmergencia.start();
		
		}

		if (odontologo == true) {
			
			Semaphore odontologos = new Semaphore(1);
			SalaOdontologia salaOdontologia = new SalaOdontologia("Sala de Odontología 1", horario, odontologos);
			salaOdontologia.start();
		
		}

    }
    
}
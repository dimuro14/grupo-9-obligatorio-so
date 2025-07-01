import java.util.concurrent.Semaphore;

public class CentroMedico {
	
	//Añadir constructor con parámetros?

	@SuppressWarnings("unused")
    public static void start(int nroInicialPacientes, int pacientesPorHora, int tiempoConsulta, int numMedicos, int numEnfermeros, boolean salaReservadaEmergencia, boolean odontologo) throws Exception {

		Semaphore apertura = new Semaphore(0);

		Semaphore medicos = new Semaphore(numMedicos);
		Semaphore enfermeros = new Semaphore(numEnfermeros);

		//Inicializar MLQ
		MLQ mlq = new MLQ();

		//De 8:00 a 20:00
		Horario horario = new Horario(mlq, nroInicialPacientes, pacientesPorHora, odontologo, apertura);
		horario.start();

		apertura.acquire();

		ConsultorioMedico consultorio1 = new ConsultorioMedico("Consultorio Médico 1", horario, mlq, medicos, enfermeros, tiempoConsulta);
		consultorio1.start();

		ConsultorioMedico consultorio2 = new ConsultorioMedico("Consultorio Médico 2", horario, mlq, medicos, enfermeros, tiempoConsulta);
		consultorio2.start();

		SalaEnfermeria salaEnfermeria = new SalaEnfermeria("Sala de Enfermería 1", horario, mlq, enfermeros, tiempoConsulta);
		salaEnfermeria.start();
		
		if (salaReservadaEmergencia == true) {
			
			SalaEmergencia salaEmergencia = new SalaEmergencia("Sala de Emergencia", horario, mlq, medicos, enfermeros, tiempoConsulta);
			salaEmergencia.start();
		
		}

		if (odontologo == true) {
			
			Semaphore odontologos = new Semaphore(1);
			SalaOdontologia salaOdontologia = new SalaOdontologia("Sala de Odontología", horario, mlq, odontologos, tiempoConsulta);
			salaOdontologia.start();
		
		}

    }
    
}
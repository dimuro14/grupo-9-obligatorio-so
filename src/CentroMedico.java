public class CentroMedico {

	public static class ConsultorioMedico extends Thread {
		
		public ConsultorioMedico(String str) {
	        
		    super(str);
		    
		}

	    @Override
	    public void run() {

			//Añadir while true?
	 
			//Tomar un elemento de la cola de pacientes (MLQ)
			//Si no hay pacientes, esperar

			//Si el enfermero está ocupado, esperarlo

			//Si hay un paciente en espera en uno de los dos consultorios médicos, atenderlo

	    }
	    
	}

	public static class SalaEnfermeria extends Thread {
		
		public SalaEnfermeria(String str) {
	        
		    super(str);
		    
		}

	    @Override
	    public void run() {

			//Añadir while true?
	 
			//Tomar un elemento de la cola de pacientes (MLQ)
			//Si no hay pacientes, esperar

			//Si el enfermero está ocupado, esperarlo

			//Si hay un paciente en espera en uno de los dos consultorios médicos, atenderlo
			
	    }
	    
	}
	
	@SuppressWarnings("unused")
    public static void start(int nroInicialPacientes, int pacientesPorHora) throws Exception {

		//Inicializar MLQ
		MLQ mlq = new MLQ();

		//De 8:00 a 20:00
		Horario horario = new Horario(mlq, nroInicialPacientes, pacientesPorHora);
		horario.start();

		//Tener en cuenta como semáforos:
		//1 sala de enfermería
		//2 consultorios médicos

		//Los semáforos serían los consultorios o los médicos/enfermeros?
		
    }
    
}
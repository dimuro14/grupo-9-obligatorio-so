public class CentroMedico {

	semaforoEnfermería = new Semaphore(1);
	semaforoConsultorio1 = new Semaphore(1);
	semaforoConsultorio2 = new Semaphore(1);
	
	public static class Medico extends Thread {
		
		public Medico(String str) {
	        
		    super(str);
		    
		}

	    @Override
	    public void run() {

			//Añadir while true?
	        
			//Si el enfermero está ocupado, esperarlo

			//Si hay un paciente en espera en uno de los dos consultorios médicos, atenderlo
			
	    }
	    
	}

	public static class Enfermero extends Thread {
		
		public Enfermero(String str) {
	        
		    super(str);
		    
		}

	    @Override
	    public void run() {

			//Añadir while true?
	        
			//Si el médico está atendiendo un paciente, lo asiste (espera al enfermero)

			//Si hay un paciente en espera en la sala de enfermería, atenderlo

	    }
	    
	}

	
	@SuppressWarnings("unused")
    public static void start(int nroInicialPacientes) {

		//Inicializar MLQ
		//MLQ mlq = new MLQ();
		
		//Añadir pacientes iniciales a las colas (con un método que los genere aleatoriamente?)
		//for (int i = 0; i < nroInicialPacientes; i++) {
		//  //Crear Paciente  
		//	Paciente.agregarACola();
		//}

		//De 8:00 a 20:00
		Horario horario = new Horario();
		horario.start();

		//Tener en cuenta como semáforos:
		//1 sala de enfermería
		//2 consultorios médicos
		
		//Iniciar hilos de médicos y enfermeros

		//Hacer que los médicos y enfermeros hagan pasar a los pacientes? O crear un hilo asistente que lo haga?

    }
    
}

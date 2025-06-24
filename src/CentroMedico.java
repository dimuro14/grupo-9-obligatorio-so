public class CentroMedico {
	
	//Añadir constructor con parámetros?

	@SuppressWarnings("unused")
    public static void start(int nroInicialPacientes, int pacientesPorHora) throws Exception {

		//Tener en cuenta:
		//1 sala de enfermería
		//2 consultorios médicos

		//Crear semáforos para pacientes, médicos y enfermeros?
		Semaphore medicos = new Semaphore(2);
		Semaphore enfermeros = new Semaphore(1);

		//Inicializar MLQ
		MLQ mlq = new MLQ();

		//De 8:00 a 20:00
		Horario horario = new Horario(mlq, nroInicialPacientes, pacientesPorHora);
		horario.start();

		ConsultorioMedico consultorio1 = new ConsultorioMedico("Consultorio 1", horario, mlq, medicos, enfermeros);
		//ConsultorioMedico consultorio2 = new ConsultorioMedico(2, mlq, medicos, enfermeros);
		consultorio1.start();
		//consultorio2.start();

    }
    
}
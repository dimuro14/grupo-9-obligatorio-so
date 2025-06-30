
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Horario extends Thread {
		
    AtomicInteger hora = null;
    MLQ mlq = null;
    int nroInicialPacientes = 0;
    int pacientesPorHora = 0;
    
    HashMap<Paciente, Integer> tiempoDeEspera = new HashMap<>();

    public Horario(MLQ mlq, int nroInicialPacientes, int pacientesPorHora) {
        
        this.hora = new AtomicInteger(8);
        this.mlq = mlq;
        this.nroInicialPacientes = nroInicialPacientes;
        this.pacientesPorHora = pacientesPorHora;

    }

    public int getHora() {
        
        return hora.get();
    
    }

    public int getHoraCierre() {
        
        return 20;

    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {

        System.out.println("Centro Médico - Abierto");

        //Añadir pacientes iniciales a las colas (con un método que los genere aleatoriamente?)
		for (int i = 0; i < nroInicialPacientes; i++) {
		  	
			//Crear Paciente  
			Paciente nuevoPaciente = Paciente.crearPacienteAleatorio();
			mlq.agregarACola(nuevoPaciente);
            //Log nuevo paciente añadido
            Logger.log("Nuevo paciente ingresado: Hora:" + hora.get() + ":00 ; Tipo de consulta:" + nuevoPaciente.tipoConsulta);

		}
        
        System.out.println("Hora: " + hora.get() + ":00");

        for (int i = 8; i < 20; i++) {
            
            try {
                
                Thread.sleep(1000);
            
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            
            }
            
            hora.set(hora.get() + 1);
            System.out.println("Hora: " + hora.get() + ":00");
            
            for (Paciente paciente : tiempoDeEspera.keySet()) {
                
                int tiempoActual = tiempoDeEspera.get(paciente);
                tiempoDeEspera.put(paciente, tiempoActual + 1);
                
            }
            
            //Añadir pacientes por hora, mediante un método de CentroMedico.
            for (int j = 0; j < pacientesPorHora; j++) {
                
                //Crear Paciente  
                Paciente nuevoPaciente = Paciente.crearPacienteAleatorio();
                mlq.agregarACola(nuevoPaciente);
                tiempoDeEspera.put(nuevoPaciente, 0);
                //System.out.println("Nuevo paciente: " + nuevoPaciente.getNombre() + " añadido a la cola.");
                
            }
            
        }

        System.out.println("Centro Médico - Cerrado");
        
    }
	    
}

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Horario extends Thread {
		
    AtomicInteger hora = null;
    Semaphore apertura = null;
    MLQ mlq = null;
    int nroInicialPacientes = 0;
    int pacientesPorHora = 0;
    boolean odontologo = false;

    public Horario(MLQ mlq, int nroInicialPacientes, int pacientesPorHora, boolean odontologo, Semaphore apertura) {
        
        this.hora = new AtomicInteger(8);
        this.mlq = mlq;
        this.nroInicialPacientes = nroInicialPacientes;
        this.pacientesPorHora = pacientesPorHora;
        this.odontologo = odontologo;
        this.apertura = apertura;

    }

    public int getHora() {
        
        return hora.get();
    
    }

    public int getHoraCierre() {
        
        return 20;

    }

    private void agregarPacientesIniciales() {

        System.out.println("Agregando pacientes iniciales:");
        
        for (int i = 0; i < nroInicialPacientes; i++) {
            
            //Crear Paciente  
            Paciente nuevoPaciente = Paciente.crearPacienteAleatorio();

            if (odontologo == false) {

                //Si no hay odontologo, solo se pueden agregar pacientes de tipo Consulta General
                if (nuevoPaciente.tipoConsulta.equals("CarneDeSalud") && nuevoPaciente.informeOdontologo == false) {
                    
                    System.out.println("Nuevo paciente " + nuevoPaciente.nombre + " (" + nuevoPaciente.tipoConsulta + ") rechazado: No tiene informe odontólogico y no hay odontólogo.");
                    Logger.log("Nuevo paciente " + nuevoPaciente.nombre + " (" + nuevoPaciente.tipoConsulta + ") rechazado: Hora: " + hora.get() + ":00; No tiene informe odontólogico y no hay odontólogo.");
                    
                    continue;
                
                }

            }

            mlq.agregarACola(nuevoPaciente);
            
            //Log nuevo paciente añadido
            Logger.log("Nuevo paciente " + nuevoPaciente.nombre + " (" + nuevoPaciente.tipoConsulta + ") ingresado: Hora: " + hora.get() + ":00");
        
        }
    
    }

    private void agregarPacientesPorHora() {

        //Añadir pacientes por hora, mediante un método de CentroMedico.
        for (int j = 0; j < pacientesPorHora; j++) {
            
            Paciente nuevoPaciente = Paciente.crearPacienteAleatorio();
            mlq.agregarACola(nuevoPaciente);
            
            System.out.println("Nuevo paciente " + nuevoPaciente.nombre + " (" + nuevoPaciente.tipoConsulta + ") ingresado: Hora: " + hora.get() + ":00");
            Logger.log("Nuevo paciente " + nuevoPaciente.nombre + " (" + nuevoPaciente.tipoConsulta + ") ingresado: Hora: " + hora.get() + ":00");
        
        
        } 
        
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {

        boolean abierto = false;

        agregarPacientesIniciales();
        
        System.out.println("Centro Médico - Abierto");
        Logger.log("Centro Médico - Abierto");

        for (int i = 8; i < 20; i++) {
            
            System.out.println("Hora: " + hora.get() + ":00");
            Logger.log("Hora: " + hora.get() + ":00");

            if (abierto == false) {
                
                abierto = true;

                apertura.release(); // Liberar el semáforo para indicar que el centro médico está abierto

            }

            try {
                
                Thread.sleep(1000);
            
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            
            }
            
            hora.set(hora.get() + 1);

            agregarPacientesPorHora();

            if (mlq.colaBaja.size() > 10) {
                
                Paciente paciente = mlq.colaBaja.poll();
                mlq.agregarAColaMedia(paciente);
            
            }
            
        }

        System.out.println("Centro Médico - Cerrado");
        Logger.log("Centro Médico - Cerrado");
        
        Estadisticas.report();
    
    }
	    
}
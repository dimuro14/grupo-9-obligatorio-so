import java.util.concurrent.Semaphore;

public class SalaOdontologia extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore odontologos = null;
    int tiempoConsulta = 0;

    public SalaOdontologia(String nombre, Horario horario, MLQ mlq, Semaphore odontologos, int tiempoConsulta) {
        
        super(nombre);
        this.horario = horario;
        this.mlq = mlq;
        this.odontologos = odontologos;
        this.tiempoConsulta = tiempoConsulta;
    
    }
    
    /* Agrega paciente a la cola odontológica
    public synchronized void agregarPaciente(Paciente paciente){
        
        colaOdontologica.add(paciente);
        notify();
    
    }

    // Espera bloqueado hasta que haya pacientes disponsibles
    private synchronized Paciente esperarPaciente() throws InterruptedException {
        
        while (colaOdontologica.isEmpty()){

            wait(); 

        }
        
        return colaOdontologica.poll();

    }*/
    
    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre() || !mlq.getColasVacias()) {
            
            try {
                
                Paciente paciente = mlq.tomarPacienteOdontologia();
        
                if (paciente == null) {
                    
                    continue; // No hay pacientes, esperar
                
                }

                System.out.println("Sala de Odontología: Esperando odontólogo");
                Logger.log("Sala de Odontología: Esperando odontólogo");
                
                odontologos.acquire();

                System.out.println("Sala de Odontología: Atendiendo a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log("Sala de Odontología: Atendiendo a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

                Thread.sleep(tiempoConsulta);

                System.out.println("Sala de Odontología: Terminó de atender a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log("Sala de Odontología: Terminó de atender a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

                System.out.println("Sala de Odontología: Carné de salud entregado a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log("Sala de Odontología: Carné de salud entregado a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

                odontologos.release();

            } catch (InterruptedException e) {
                
                System.out.println("Sala de Odontología: Atención interrumpida.");
                break;
            
            }
        
        }
    
    }
    
}
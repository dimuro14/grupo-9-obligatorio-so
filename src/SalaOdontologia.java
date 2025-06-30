import java.util.concurrent.Semaphore;

public class SalaOdontologia extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore odontologos = null;

    public SalaOdontologia(String str, Horario horario, MLQ mlq, Semaphore odontologos) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.odontologos = odontologos;
        
    }

    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre()) {
            
            try {
                
                // Tomar paciente (esperar si no hay)
                // Agregar semáforos de paciente

                // El paciente debe ser tomado exclusivamente si requiere ser atendido por un enfermero
                Paciente paciente = mlq.tomarPaciente();

                if (paciente == null) {
                
                    continue; // No hay pacientes, esperar

                }

                System.out.println(getName() + ": Paciente " + paciente.nombre + " en espera.");
                System.out.println(getName() + ": Esperando odontólogo.");
                odontologos.acquire();

                System.out.println(getName() + ": Atendiendo a " + paciente.nombre + ".");
                
                // Simular atención médica
                Thread.sleep(200);
                
                odontologos.release();

                System.out.println(getName() + ": Terminó de atender a " + paciente.nombre);

            } catch (InterruptedException e) {
                
                System.out.println(getName() + ": Atención interrumpida.");
                break;
            
            }
        
        }
    
    }
    
}
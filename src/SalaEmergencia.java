import java.util.concurrent.Semaphore;

public class SalaEmergencia extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore medicos = null;
    Semaphore enfermeros = null;

    public SalaEmergencia(String str, Horario horario, MLQ mlq, Semaphore medicos, Semaphore enfermeros) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.medicos = medicos;
        this.enfermeros = enfermeros;
        
    }

    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre()) {
            
            try {
                
                // Tomar paciente (esperar si no hay)
                // Agregar semáforos de paciente

                // El paciente debe ser tomado exclusivamente si es una emergencia
                Paciente paciente = mlq.tomarPaciente("SalaEmergencia");

                if (paciente == null) {
                
                    continue; // No hay pacientes, esperar

                }

                // Interrumpir médicos y enfermeros si es necesario?

                System.out.println(getName() + ": Paciente " + paciente.nombre + " en espera.");
                Logger.log(getName() + ": Paciente " + paciente.nombre + " en espera.");
                System.out.println(getName() + ": Esperando médico.");
                Logger.log(getName() + ": Esperando médico.");
                medicos.acquire();
                System.out.println(getName() + ": Esperando asistente.");
                Logger.log(getName() + ": Esperando asistente.");
                enfermeros.acquire();
                System.out.println(getName() + ": Atendiendo a " + paciente.nombre + ".");
                Logger.log(getName() + ": Atendiendo a " + paciente.nombre + ".");
                // Simular atención médica
                Thread.sleep(200);
                
                medicos.release();
                enfermeros.release();
                
                System.out.println(getName() + ": Terminó de atender a " + paciente.nombre);
                Logger.log(getName() + ": Terminó de atender a " + paciente.nombre);

            } catch (InterruptedException e) {
                
                System.out.println(getName() + ": Atención interrumpida.");
                Logger.log(getName() + ": Atención interrumpida.");
                break;
            
            }
        
        }
    
    }
    
}
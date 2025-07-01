import java.util.concurrent.Semaphore;

public class SalaEmergencia extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore medicos = null;
    Semaphore enfermeros = null;
    int tiempoConsulta = 0;

    public SalaEmergencia(String str, Horario horario, MLQ mlq, Semaphore medicos, Semaphore enfermeros, int tiempoConsulta) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.medicos = medicos;
        this.enfermeros = enfermeros;
        this.tiempoConsulta = tiempoConsulta;
        
    }

    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre() || !mlq.getColasVacias()) {

            try {
                
                if (horario.getHora() >= horario.getHoraCierre() && !mlq.getColasVacias()) {
                
                    //System.out.println("" + getName() + ": Hora de cierre alcanzada, pero hay pacientes en espera.");
                
                }

                // Tomar paciente (esperar si no hay)
                // Agregar semáforos de paciente
                Paciente paciente = mlq.tomarPaciente("SalaEmergencia");

                if (paciente == null) {
                    
                    continue; // No hay pacientes, esperar

                }

                System.out.println(getName() + ": Paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ") en espera.");
                Logger.log(getName() + ": Paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ") en espera.");
                
                System.out.println(getName() + ": Esperando médico.");
                Logger.log(getName() + ": Esperando médico.");
                medicos.acquire();
                
                System.out.println(getName() + ": Esperando asistente.");
                Logger.log(getName() + ": Esperando asistente.");
                enfermeros.acquire();

                System.out.println(getName() + ": Atendiendo a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log(getName() + ": Atendiendo a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

                // Simular atención médica
                Thread.sleep(tiempoConsulta);
                
                medicos.release();
                enfermeros.release();
                
                System.out.println(getName() + ": Terminó de atender a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log(getName() + ": Terminó de atender a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

            } catch (InterruptedException e) {
                
                System.out.println(getName() + ": Atención interrumpida.");
                Logger.log(getName() + ": Atención interrumpida.");
                
                break;
            
            }
        
        }
    
    }
    
}
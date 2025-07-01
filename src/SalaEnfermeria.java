import java.util.concurrent.Semaphore;
public class SalaEnfermeria extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore enfermeros = null;
    int tiempoConsulta = 0;

    public SalaEnfermeria(String str, Horario horario, MLQ mlq, Semaphore enfermeros, int tiempoConsulta) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.enfermeros = enfermeros;
        this.tiempoConsulta = tiempoConsulta;
        
    }

    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre() || !mlq.getColasVacias()) {
            
            try {
                
                Paciente paciente = mlq.tomarPaciente("SalaEnfermeria");

                if (paciente == null) {
                
                    continue; // No hay pacientes, esperar

                }

                System.out.println(getName() + ": Paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log(getName() + ": Paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

                System.out.println(getName() + ": Esperando enfermero.");
                Logger.log(getName() + ": Esperando enfermero.");

                enfermeros.acquire();

                System.out.println(getName() + ": Atendiendo a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log(getName() + ": Atendiendo a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                
                // Simular atención médica
                Thread.sleep(tiempoConsulta);
                
                enfermeros.release();

                System.out.println(getName() + ": Terminó de atender a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                Logger.log(getName() + ": Terminó de atender a " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

                if ((paciente.tipoConsulta).equalsIgnoreCase("CarneDeSalud")){
                    
                    System.out.println(getName() + ": Se transfirió al paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ") a EntrevistaMedica.");
                    Logger.log(getName() + ": Se transfirió al paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ") a EntrevistaMedica.");
                    
                    paciente.SetTipoConsulta("EntrevistaMedica");
                    mlq.agregarACola(paciente);
                    
                }

            } catch (InterruptedException e) {
                
                System.out.println(getName() + ": Atención interrumpida.");
                Logger.log(getName() + ": Atención interrumpida.");
                
                break;
            
            }
        
        }
    
    }
    
}
import java.util.concurrent.Semaphore;
public class SalaEnfermeria extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore enfermeros = null;

    public SalaEnfermeria(String str, Horario horario, MLQ mlq, Semaphore enfermeros) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.enfermeros = enfermeros;
        
    }

    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre()) {
            
            try {
                
                // Tomar paciente (esperar si no hay)
                // Agregar semáforos de paciente

                // El paciente debe ser tomado exclusivamente si requiere ser atendido por un enfermero
                Paciente paciente = mlq.tomarPaciente("SalaEnfermeria");

                if (paciente == null) {
                
                    continue; // No hay pacientes, esperar

                }

                System.out.println(getName() + ": Paciente " + paciente.nombre + " en espera.");
                System.out.println(getName() + ": Esperando enfermero.");
                enfermeros.acquire();

                System.out.println(getName() + ": Atendiendo a " + paciente.nombre + ".");
                
                // Simular atención médica
                Thread.sleep(200);
                
                enfermeros.release();

                System.out.println(getName() + ": Terminó de atender a " + paciente.nombre);
                
                if ((paciente.tipoConsulta).equalsIgnoreCase("CarneDeSalud")){
                    
                    paciente.SetTipoConsulta("EntrevistaMedica");
                    mlq.agregarACola(paciente);

                    System.out.println(getName() + ": Se transfirio el paciente a Entrevista Medica " + paciente.nombre);
                
                }

            } catch (InterruptedException e) {
                
                System.out.println(getName() + ": Atención interrumpida.");
                break;
            
            }
        
        }
    
    }
    
}
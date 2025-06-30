import java.util.concurrent.Semaphore;

public class ConsultorioMedico extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore medicos = null;
    Semaphore enfermeros = null;

    public ConsultorioMedico(String str, Horario horario, MLQ mlq, Semaphore medicos, Semaphore enfermeros) {
        
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
                Paciente paciente = mlq.tomarPaciente("ConsultorioMedico");

                if (paciente == null) {
                
                    continue; // No hay pacientes, esperar

                }

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

                if ((paciente.tipoConsulta).equalsIgnoreCase("EntrevistaMedica")){
                    if (!paciente.informeOdontologo){
                        paciente.SetTipoConsulta("ConsultaOdontologica");
                        mlq.agregarACola(paciente);
                        System.out.println(getName() + ": Se transfirio el paciente a Consulta de Odontologia " + paciente.nombre);
                        Logger.log(getName() + ": Se transfirio el paciente a Consulta de Odontologia " + paciente.nombre);
                    }
                    


                }

            } catch (InterruptedException e) {
                
                System.out.println(getName() + ": Atención interrumpida.");
                Logger.log(getName() + ": Atención interrumpida.");
                break;
            
            }
        
        }
    
    }
    
}
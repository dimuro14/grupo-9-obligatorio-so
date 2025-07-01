import java.util.concurrent.Semaphore;

public class ConsultorioMedico extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore medicos = null;
    Semaphore enfermeros = null;
    int tiempoConsulta = 0;

    public ConsultorioMedico(String str, Horario horario, MLQ mlq, Semaphore medicos, Semaphore enfermeros, int tiempoConsulta) {
        
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
                
                // Tomar paciente (esperar si no hay)
                // Agregar semáforos de paciente
                Paciente paciente = mlq.tomarPaciente("ConsultorioMedico");

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

                if ((paciente.tipoConsulta).equalsIgnoreCase("EntrevistaMedica")){
                    
                    if (!paciente.informeOdontologo){
                        
                        System.out.println(getName() + ": Se transfirió al paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ") a la sala de odontología.");
                        Logger.log(getName() + ": Se transfirió al paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ") a la sala de odontología.");
                        
                        paciente.SetTipoConsulta("ConsultaOdontologia");
                        mlq.agregarAColaOdontologia(paciente);    
                        
                    } else {
                        
                        System.out.println(getName() + ": Se entregó el carne de salud al paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ").");
                        Logger.log(getName() + ": Se entregó el carne de salud al paciente " + paciente.nombre + " (" + paciente.tipoConsulta + ").");

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
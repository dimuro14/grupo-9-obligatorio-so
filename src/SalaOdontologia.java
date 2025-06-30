import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class SalaOdontologia extends Thread {
    private final Queue<Paciente> colaOdontologica = new LinkedList<>();
    
    Horario horario = null;
    //MLQ mlq = null;
    Semaphore odontologos = null;

    /*public SalaOdontologia(String str, Horario horario, MLQ mlq, Semaphore odontologos) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.odontologos = odontologos;
        
    }*/
   public SalaOdontologia(String nombre, Horario horario, Semaphore odontologos) {
        
        super(nombre);
        this.horario = horario;
        this.odontologos = odontologos;
    
    }
    
    // Agrega paciente a la cola odontológica
    public synchronized void agregarPaciente(Paciente paciente){
        colaOdontologica.add(paciente);
        notify();
    }

    //espera bloqueado hasta que haya pacientes disponsibles
    private synchronized Paciente esperarPaciente() throws InterruptedException {
        while (colaOdontologica.isEmpty()){
            wait(); //espera active por llegada de pacientes  
        }
        return colaOdontologica.poll();

    }
    
    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre()) {
            
            try {
                
                // Tomar paciente (esperar si no hay)
                // Agregar semáforos de paciente
                //espera paciente
                Paciente paciente = esperarPaciente();
                // El paciente debe ser tomado exclusivamente si requiere ser atendido por un enfermero
                //Paciente paciente = mlq.tomarPaciente("SalaOdontologia");
                
                //if (paciente == null) {
                
                  //  continue; // No hay pacientes, esperar
                System.out.println("Esperando odontólogo");
                odontologos.acquire();

                System.out.println("Odontólogo está atendiendo a " + paciente.nombre);
                Thread.sleep(200);
                System.out.println("Odontólogo termino de atender a " + paciente.nombre);
                System.out.println("Se le entrega el carne de salud a " + paciente.nombre);

                odontologos.release();


                } catch(InterruptedException e) {
                    System.out.println(" se interrumpio la atención odontológica ");
                    break;
                }

                /*System.out.println(getName() + ": Paciente " + paciente.nombre + " en espera.");
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
            
            }*/
        
        }
    
    }
    
}
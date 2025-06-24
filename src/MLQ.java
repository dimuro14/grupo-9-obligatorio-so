import java.util.concurrent.ConcurrentLinkedQueue;

public class MLQ {
    
    //Atención de emergencias
    ConcurrentLinkedQueue<Paciente> colaAlta = null;
    //Atención de consultas generales
    ConcurrentLinkedQueue<Paciente> colaMedia = null;
    //Atención de curaciones y análisis clínicos
    ConcurrentLinkedQueue<Paciente> colaBaja = null;

    public MLQ() {

        this.colaAlta = new ConcurrentLinkedQueue<>();
        this.colaMedia = new ConcurrentLinkedQueue<>();
        this.colaBaja = new ConcurrentLinkedQueue<>();
    
    }

    public void agregarACola(Paciente paciente) {

        //Añadir a su respectiva cola
        
        //Tener en cuenta:

        //Cola Alta
        //tipoConsulta = "Emergencia"
        //tipoConsulta = "Urgencia"

        //Cola Media y Baja
        //tipoConsulta = "CarneDeSalud"
        //y cualquier otro tipo de consulta

        if (paciente.tipoConsulta == "Emergencia" || paciente.tipoConsulta == "Urgencia") {
            
            colaAlta.add(paciente);
        
        } else if (paciente.tipoConsulta == "CarneDeSalud" || paciente.tipoConsulta == "ConsultaGeneral") {
            
            colaMedia.add(paciente);
        
        } else {
            
            colaBaja.add(paciente);
        
        }
    
    }

    public Paciente tomarPaciente() {
        
        //Tomar un paciente de la cola de mayor prioridad que tenga pacientes
        //Si no hay pacientes en ninguna cola, retornar null

        if (!colaAlta.isEmpty()) {
            
            return colaAlta.poll();
        
        } else if (!colaMedia.isEmpty()) {
            
            return colaMedia.poll();
        
        } else if (!colaBaja.isEmpty()) {
            
            return colaBaja.poll();
        
        } else {
            
            return null;

        }
    
    }

    public void start() throws InterruptedException {

        //Añadir procesos a las colas antes de utilizar el método start.
        
        Thread alta = new Thread(new PlanificadorFCFS(this.colaAlta));
        Thread media = new Thread(new PlanificadorFCFS(this.colaMedia));
        Thread baja = new Thread(new PlanificadorFCFS(this.colaBaja));

        //Thread envejecedor = new Thread(new Envejecedor(this.colaBaja, this.colaMedia, 3000));

        alta.start();
        alta.join();

        //envejecedor.start();

        media.start();
        baja.start();

        media.join();
        baja.join();
        //envejecedor.join();
    
    }

}
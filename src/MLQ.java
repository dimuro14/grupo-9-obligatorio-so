import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MLQ {
    
    //Atención de emergencias
    ConcurrentLinkedQueue<Paciente> colaAlta = null;
    //Atención de consultas generales
    ConcurrentLinkedQueue<Paciente> colaMedia = null;
    //Atención de curaciones y análisis clínicos
    ConcurrentLinkedQueue<Paciente> colaBaja = null;

    private static Map<String, String> consultaASala = new HashMap<>();

    public MLQ() {

        this.colaAlta = new ConcurrentLinkedQueue<>();
        this.colaMedia = new ConcurrentLinkedQueue<>();
        this.colaBaja = new ConcurrentLinkedQueue<>();

        consultaASala.put("Emergencia", "SalaEmergencia");
        consultaASala.put("Urgencia", "SalaEmergencia");
        consultaASala.put("CarneDeSalud", "SalaEnfermeria");
        consultaASala.put("EntrevistaMedica", "ConsultorioMedico");
        consultaASala.put("ConsultaGeneral", "ConsultorioMedico");
        consultaASala.put("InformeOdontologia", "SalaOdontologia");
    
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

        if (paciente.tipoConsulta == "Emergencia") {
            
            if (colaAlta.size() < 3) {
                
                colaAlta.add(paciente);

            }
        
        } else if (paciente.tipoConsulta == "Urgencia") {
            
            if (colaMedia.size() < 10) {
                
                colaMedia.add(paciente);

            } else {
                
                System.out.println("Paciente con urgencia no puede ser atendido.");
            
            }
            
        } else if (paciente.tipoConsulta == "CarneDeSalud" || paciente.tipoConsulta == "EntrevistaMedica" || paciente.tipoConsulta == "ConsultaGeneral" || paciente.tipoConsulta == "InformeOdontologia") {
            
            colaBaja.add(paciente);
        
        } else {
            
            colaMedia.add(paciente);

        }
    
    }

    private String obtenerSala(String tipoConsulta) {

        //Retorna la sala a la que corresponde el tipo de consulta
        //Si no existe, retorna "SalaEnfermeria"

        return consultaASala.getOrDefault(tipoConsulta, "SalaEnfermeria");
    
    }

    public Paciente tomarPaciente(String sala) {
        
        //Tomar un paciente de la cola de mayor prioridad que tenga pacientes
        //Si no hay pacientes en ninguna cola, retornar null
        
        if (!colaAlta.isEmpty() && obtenerSala(colaAlta.peek().tipoConsulta) == sala) {
            
            return colaAlta.poll();
        
        } else if (!colaMedia.isEmpty() && obtenerSala(colaMedia.peek().tipoConsulta) == sala) {
            
            return colaMedia.poll();
        
        } else if (!colaBaja.isEmpty() && obtenerSala(colaBaja.peek().tipoConsulta) == sala) {
            
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
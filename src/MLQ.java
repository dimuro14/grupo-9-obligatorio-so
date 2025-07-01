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

    private static final Map<String, String> consultaASala = new HashMap<>();

    public MLQ() {

        this.colaAlta = new ConcurrentLinkedQueue<>();
        this.colaMedia = new ConcurrentLinkedQueue<>();
        this.colaBaja = new ConcurrentLinkedQueue<>();

        consultaASala.put("Emergencia", "ConsultorioMedico");
        consultaASala.put("Urgencia", "ConsultorioMedico");
        consultaASala.put("CarneDeSalud", "SalaEnfermeria");
        consultaASala.put("EntrevistaMedica", "ConsultorioMedico");
        consultaASala.put("ConsultaGeneral", "ConsultorioMedico");
    
    }

    public void agregarACola(Paciente paciente) {
        
        if (paciente.tipoConsulta.equals("Emergencia")) {
            
            if (colaAlta.size() < 3) {
                
                System.out.println("Paciente agregado a cola alta: " + paciente.nombre + " (" + paciente.tipoConsulta + ")");
                colaAlta.add(paciente);

            }
        
        } else if (paciente.tipoConsulta.equals("Urgencia")) {
            
            if (colaMedia.size() < 10) {
                
                System.out.println("Paciente agregado a cola media: " + paciente.nombre + " (" + paciente.tipoConsulta + ")");
                colaMedia.add(paciente);

            } else {
                
                System.out.println("Paciente con urgencia no puede ser atendido.");
            
            }
            
        } else if (paciente.tipoConsulta.equals("CarneDeSalud") || paciente.tipoConsulta.equals("EntrevistaMedica") || paciente.tipoConsulta.equals("ConsultaGeneral") || paciente.tipoConsulta.equals("InformeOdontologia")) {
            
            System.out.println("Paciente agregado a cola baja: " + paciente.nombre + " (" + paciente.tipoConsulta + ")");
            colaBaja.add(paciente);
        
        } else {
            
            System.out.println("Paciente agregado a cola media: " + paciente.nombre + " (" + paciente.tipoConsulta + ")");
            colaMedia.add(paciente);

        }
    
    }

    public void agregarAColaMedia(Paciente paciente) {
        
        colaMedia.add(paciente);
    
    }

    private String obtenerSala(String tipoConsulta) {

        //Retorna la sala a la que corresponde el tipo de consulta
        //Si no existe, retorna "SalaEnfermeria"

        return consultaASala.getOrDefault(tipoConsulta, "SalaEnfermeria");
    
    }

    public synchronized Paciente tomarPaciente(String sala) {
        
        //Tomar un paciente de la cola de mayor prioridad que tenga pacientes
        //Si no hay pacientes en ninguna cola, retornar null
        
        if (!colaAlta.isEmpty() && (sala.equals(obtenerSala(colaAlta.peek().tipoConsulta)) || sala.equals("SalaEmergencia"))) {
            
            //System.out.println("Tomando paciente de cola alta: " + colaAlta.peek().nombre);
            Paciente paciente = colaAlta.poll();

            return paciente;
        
        } else if (!colaMedia.isEmpty() && sala.equals(obtenerSala(colaMedia.peek().tipoConsulta))) {
            
            //System.out.println("Tomando paciente de cola media: " + colaMedia.peek().nombre);
            Paciente paciente = colaMedia.poll();

            return paciente;

        } else if (!colaBaja.isEmpty() && sala.equals(obtenerSala(colaBaja.peek().tipoConsulta))) {
            
            //System.out.println("Tomando paciente de cola baja: " + colaBaja.peek().nombre);

            Paciente paciente = colaBaja.poll();

            return paciente;
        
        } else {
            
            //System.out.println("No hay pacientes en la cola de " + sala);
            
            return null;

        }
    
    }

    ConcurrentLinkedQueue<Paciente> colaOdontologia = new ConcurrentLinkedQueue<>();

    public void agregarAColaOdontologia(Paciente paciente) {
        
        colaOdontologia.add(paciente);
    
    }

    public synchronized Paciente tomarPacienteOdontologia() {

        if (!colaOdontologia.isEmpty()) {
            
            Paciente paciente = colaOdontologia.poll();
            return paciente;
        
        } else {
            
            return null;

        }
    
    }

    public boolean getColasVacias() {
        
        return colaAlta.isEmpty() && colaMedia.isEmpty() && colaBaja.isEmpty() && colaOdontologia.isEmpty();
    
    }

}
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MLQ {
    
    public ConcurrentLinkedQueue<Proceso> colaAlta = null;
    public ConcurrentLinkedQueue<Proceso> colaMedia = null;
    public ConcurrentLinkedQueue<Proceso> colaBaja = null;

    public Queue<Proceso> getColaAlta() { return colaAlta; }
    public Queue<Proceso> getColaMedia() { return colaMedia; }
    public Queue<Proceso> getColaBaja() { return colaBaja;}

    
    public MLQ() {

        this.colaAlta = new ConcurrentLinkedQueue<>();
        this.colaMedia = new ConcurrentLinkedQueue<>();
        this.colaBaja = new ConcurrentLinkedQueue<>();
    
    }

    
    //Cambiar Proceso por Consulta?
    public void agregarACola(String cola, Proceso proceso, int prioridad) {

        switch (cola) {
            
            case "ALTA":
                colaAlta.add(proceso, prioridad);
                break;
            
            case "MEDIA":
                colaMedia.add(proceso, prioridad);
                break;
            
            case "BAJA":
                colaBaja.add(proceso, prioridad);
                break;
            
            default:
                System.out.println("Prioridad no válida: " + prioridad);
        
        }
    
    }

    public void start() {

        //Añadir procesos a las colas antes de utilizar el método start.
        
        Thread alta = new Thread(new PlanificadorFCFS(this.colaAlta));
        Thread media = new Thread(new PlanificadorRR(this.colaMedia, 1000));
        Thread baja = new Thread(new PlanificadorFCFS(this.colaBaja));

        Thread envejecedor = new Thread(new Envejecedor(this.colaBaja, this.colaMedia, 3000));

        alta.start();
        alta.join();

        envejecedor.start();

        media.start();
        baja.start();

        media.join();
        baja.join();
        envejecedor.join();
    
    }

}
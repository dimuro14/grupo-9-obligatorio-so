import java.util.Queue;

public class PlanificadorFCFS implements Runnable {
    
    private Queue<Paciente> cola;

    public PlanificadorFCFS(Queue<Paciente> cola) {
        
        this.cola = cola;
    
    }

    @Override
    public void run() {
        
        while (!cola.isEmpty()) {
            
            Paciente p = cola.poll();
            
            if (p != null) {

                //p.run();
                //System.out.println("Terminado: " + p.getNombre());
            
            }
        
        }

    }

}
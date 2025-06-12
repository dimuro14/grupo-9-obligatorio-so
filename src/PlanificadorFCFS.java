import java.util.Queue;

public class PlanificadorFCFS implements Runnable {
    private Queue<Proceso> cola;

    public PlanificadorFCFS(Queue<Proceso> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        while (!cola.isEmpty()) {
            Proceso p = cola.poll();
            if (p != null) {
                p.run();
                System.out.println("Terminado: " + p.getNombre());
            }
        }
    }
}
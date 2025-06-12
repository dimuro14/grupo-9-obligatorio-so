import java.util.Queue;

public class PlanificadorRR implements Runnable {
    private Queue<Proceso> cola;
    private int quantum;

    public PlanificadorRR(Queue<Proceso> cola, int quantum) {
        this.cola = cola;
        this.quantum = quantum;
    }

    @Override
    public void run() {
        while (!cola.isEmpty()) {
            Proceso p = cola.poll();
            if (p != null) {
                p.ejecutar(quantum);
                if (!p.estaTerminado()) {
                    cola.add(p); // vuelve al final si no terminó
                } else {
                    System.out.println("Terminado: " + p.getNombre());
                }
            }
        }
    }
}
import java.util.Queue;

public class Envejecedor implements Runnable {
    private Queue<Proceso> colaBaja;
    private Queue<Proceso> colaMedia;
    private long umbralMs;

    public Envejecedor(Queue<Proceso> colaBaja, Queue<Proceso> colaMedia, long umbralMs) {
        this.colaBaja = colaBaja;
        this.colaMedia = colaMedia;
        this.umbralMs = umbralMs;
    }

    @Override
    public void run() {
        while (!colaBaja.isEmpty()) {
            Proceso p = colaBaja.peek();
            if (p != null && (System.currentTimeMillis() - p.getTiempoLlegada()) > umbralMs) {
                colaBaja.poll();
                p.actualizarTiempoLlegada();
                colaMedia.add(p);
                System.out.println("⏫ Envejecimiento: " + p.getNombre() + " subido a cola media");
            }
            try {
                Thread.sleep(500); // chequea cada 0.5 seg
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
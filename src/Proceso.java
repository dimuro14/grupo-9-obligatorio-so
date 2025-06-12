public class Proceso implements Runnable {
    
    private final String nombre;
    private int duracionRestante;
    private long tiempoLlegada;

    public Proceso(String nombre, int duracion) {
        
        this.nombre = nombre;
        this.duracionRestante = duracion;
        this.tiempoLlegada = System.currentTimeMillis();
    
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void ejecutar(int quantum) {
        
        int tiempo = Math.min(quantum, duracionRestante);
        
        System.out.println("→ Ejecutando: " + nombre + " por " + tiempo + " ms");
        
        try {
            
            Thread.sleep(tiempo);
        
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        
        }
        
        duracionRestante -= tiempo;
    
    }

    @Override
    public void run() {

        ejecutar(duracionRestante);
    
    }

    public boolean estaTerminado() {
        
        return duracionRestante <= 0;
    
    }

    public String getNombre() { 
        
        return nombre; 
    
    }
    
    public int getDuracionRestante() { 
        
        return duracionRestante; 
    
    }
    
    public long getTiempoLlegada() { 
        
        return tiempoLlegada; 
    
    }
    
    public void actualizarTiempoLlegada() { 
        
        this.tiempoLlegada = System.currentTimeMillis(); 
    
    }

}
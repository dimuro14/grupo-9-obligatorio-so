
import java.util.concurrent.atomic.AtomicInteger;

public class Horario extends Thread {
		
    AtomicInteger hora = null;
    
    public Horario() {
        
        this.hora = new AtomicInteger(8);
        
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {

        System.out.println("Centro Médico - Abierto");
        
        System.out.println("Hora: " + hora.get() + ":00");

        for (int i = 8; i < 20; i++) {
            
            try {
                
                Thread.sleep(100);
            
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            
            }
            
            hora.set(hora.get() + 1);
            System.out.println("Hora: " + hora.get() + ":00");
            //Añadir pacientes por hora, mediante un método de CentroMedico.
                
        }

        System.out.println("Centro Médico - Cerrado");
        
    }
	    
}
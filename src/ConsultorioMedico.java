public static class ConsultorioMedico extends Thread {
    
    Horario horario = null;
    MLQ mlq = null;
    Semaphore medicos = null;
    Semaphore enfermeros = null;

    public ConsultorioMedico(String str, Horario horario, MLQ mlq, Semaphore medicos, Semaphore enfermeros) {
        
        super(str);
        this.horario = horario;
        this.mlq = mlq;
        this.medicos = medicos;
        this.enfermeros = enfermeros;
        
    }

    @Override
    public void run() {

        while (horario.getHora() < horario.getHoraCierre()) {
            
            try {
                
                // Tomar paciente (esperar si no hay)
                Paciente paciente = mlq.tomarPaciente();
                System.out.println(getName() + " encontró paciente: " + paciente.nombre);

                // Esperar médico disponible
                medicos.acquire();
                System.out.println(getName() + " atendiendo a " + paciente.nombre);

                if (enfermeros.availablePermits() > 0) {
                    
                    enfermeros.acquire();
                    System.out.println(getName() + " tiene enfermero para " + paciente.nombre);
                
                }

                // Simular atención médica
                Thread.sleep(1000);
                medicos.release();
                System.out.println(getName() + " terminó con " + paciente.nombre);

            } catch (InterruptedException e) {
                
                System.out.println(getName() + " interrumpido.");
                break;
            
            }
        
        }
    
    }
    
}
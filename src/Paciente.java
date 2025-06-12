public class Paciente {

    String tipoConsulta = null;

    public Paciente(String tipoConsulta) {

        this.tipoConsulta = tipoConsulta;

    }

    public void agregarACola(MLQ mlq) {

        //Añadir a su respectiva cola y establecer su prioridad
        int duracion = (int)(Math.random() * 4000) + 1000; // 
        Proceso p = new Proceso(this.tipoConsulta, duracion);
        
        //Tener en cuenta:

        //Cola Alta
        //tipoConsulta = "Emergencia"
        //tipoConsulta = "Urgencia"

        //Cola Media y Baja
        //tipoConsulta = "CarneDeSalud"
        //y cualquier otro tipo de consulta
        switch (tipoConsulta.toLowerCase()) {
            case "emergencia", "urgencia" -> mlq.getColaAlta().add(p);
            case "carnedesalud", "consulta" -> mlq.getColaMedia().add(p);
            case "analisis" -> mlq.getColaBaja().add(p);
            default -> mlq.getColaBaja().add(p);

        }
         
        System.out.println("Paciente agregado: " + tipoConsulta + " con duración " + duracion + " ms");
        
    }

}
public class Paciente {

    String nombre = null;
    String tipoConsulta = null;
    boolean informeOdontologo = false;

    public Paciente(String nombre, String tipoConsulta, boolean informeOdontologo) {

        this.nombre = nombre;
        this.tipoConsulta = tipoConsulta;
        this.informeOdontologo = informeOdontologo;

    }

    public static Paciente crearPacienteAleatorio() {

        String[] nombres = {"Juan", "María", "Pedro", "Ana", "Luis", "Laura", "Carlos", "Marta", "Javier", "Sofía"};
        String nombre = nombres[(int) (Math.random() * nombres.length)];
        
        String[] tiposConsulta = {"General", "Especialista", "Urgencia", "Emergencia", "CarneDeSalud", "ConsultaGeneral"};
        String tipoConsulta = tiposConsulta[(int) (Math.random() * tiposConsulta.length)];
        
        boolean informeOdontologo = Math.random() < 0.5; // 50% de probabilidad

        Paciente nuevoPaciente = new Paciente(nombre, tipoConsulta, informeOdontologo);

        return nuevoPaciente;

    }

}
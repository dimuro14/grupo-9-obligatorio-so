public class Paciente {

    String tipoConsulta = null;
    boolean informeOdontologo = false;

    public Paciente(String tipoConsulta, boolean informeOdontologo) {

        this.tipoConsulta = tipoConsulta;
        this.informeOdontologo = informeOdontologo;

    }

    public static Paciente crearPacienteAleatorio() {

        String[] tiposConsulta = {"General", "Especialista", "Urgencia", "Emergencia", "CarneDeSalud", "ConsultaGeneral"};
        String tipoConsulta = tiposConsulta[(int) (Math.random() * tiposConsulta.length)];
        boolean informeOdontologo = Math.random() < 0.5; // 50% de probabilidad

        return new Paciente(tipoConsulta, informeOdontologo);

    }

}
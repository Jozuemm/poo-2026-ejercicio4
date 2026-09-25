package model;

//Herencia de Maquina
public class MaquinaAlgodon extends Maquina{

    private static int siguienteNumero = 1;// esto es para generar el codigo
    private static final int LIMITE_POTENCIA = 1000;
    private static final double RECARGO_POTENCIA = 60.0;

    private final int potenciaWatts;

    public MaquinaAlgodon(String marca, String modelo, double tarifaDiaria, int potenciaWatts) {
        super(generarCodigo(), marca, modelo, tarifaDiaria);
        if (potenciaWatts <= 0) {//enviamos datos a Maquina con super y luego verificamos la potencia
            throw new IllegalArgumentException("La potencia debe ser mayor a cero");
        }
        this.potenciaWatts = potenciaWatts;
        
    }
    //Aca se hace el codigo para cada uno "AG001" y asi se va
    private static String generarCodigo() {
        String codigo = String.format("AG%03d", siguienteNumero);
        siguienteNumero++;// el %03d es para que el numero siempre tenga 3 digitos 
        return codigo;
    }

    public int getPotenciaWatts() {
        return potenciaWatts;
    }

    @Override 
    public double calcularCosto(int dias) {
        validarDias(dias);

        //Calcular total y si la potencia es mas de 1000 se cobra tarifa extra
        double total = getTarifaDiaria() * dias;

        if (potenciaWatts > LIMITE_POTENCIA) {
            total += RECARGO_POTENCIA;
        }
        return total;
    }

    @Override 
    public String getCategoria() {
        return "Algodon de azucar";
    }

    @Override 
    public String getDetalles() {
        return "Potencia: " + potenciaWatts + "W";
    }
}

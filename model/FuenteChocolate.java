package model;

public class FuenteChocolate extends Maquina {

    private static int siguienteNumero = 1;

    private static final double RECARGO_POR_KG = 20.0;
    private final double capacidadKg;

    public FuenteChocolate(String marca, String modelo, double tarifaDiaria, double capacidadKg) {
        super(generarCodigo(), marca, modelo, tarifaDiaria);
        if (capacidadKg <= 0){
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero");
        }
        this.capacidadKg = capacidadKg;

    }

    private static String generarCodigo() {
        String codigo = String.format("CH%03d", siguienteNumero);
        siguienteNumero++;
        return codigo;
    }

    public double getCapacidadKg() {
        return capacidadKg;
    }

    @Override 
    public double calcularCosto(int dias) {
        validarDias(dias);

        double costoBase = getTarifaDiaria() * dias;
        double recargo = RECARGO_POR_KG * dias * capacidadKg;
        return costoBase + recargo;
    }

    @Override 
    public String getCategoria() {
        return "Fuente de chocolate";
    }

    @Override
    public String getDetalles() {
        return "Capacidad: " + capacidadKg + " kg";
    }
}
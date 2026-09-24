package model;

public class MaquinaPalomitas extends Maquina{

    private static int siguienteNumero = 1;

    private final int porcionesPorHora;
    private final boolean carritoIntegrado;

    public MaquinaPalomitas(String marca, String modelo, double tarifaDiaria, int porcionesPorHora, boolean carritoIntegrado){
        super(generarCodigo(), marca, modelo, tarifaDiaria);
        if (porcionesPorHora <= 0){
            throw new IllegalArgumentException("Las porciones por hora deben ser mayores a cero");
           
        }
        this.porcionesPorHora = porcionesPorHora;
        this.carritoIntegrado = carritoIntegrado; 
    }

    private static String generarCodigo() {
        String codigo = String.format("PP%03d", siguienteNumero);
        siguienteNumero++;
        return codigo;
    }

    public int getPorcionesPorhora() {
        return porcionesPorHora;
    }

    public boolean tieneCarritoIntegrado() {
        return carritoIntegrado;
    }

    @Override 
    public double calcularCosto(int dias) {
        validarDias(dias);

        double costoBase = getTarifaDiaria() * dias;
        double recargo = 0;

        if(carritoIntegrado) {
            recargo = 40 * dias;
        }
        return costoBase + recargo;
    }

    @Override
    public String getCategoria() {
        return "Palomitas";
    }

    @Override 
    public String getDetalles() {
        String teieneCarrito;

        if (carritoIntegrado) {
            teieneCarrito = "Si";
        } else {
            teieneCarrito = "No";
        }
        return "Porciones por hora: " + porcionesPorHora + " ¿Carrito integrado?: " + teieneCarrito;
        
    }
}

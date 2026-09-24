package model;

//Maquina es abstracta pq no queremos objetos de maquina si no de sus subclases
public abstract class Maquina {

    private final String codigo;//el final permite que el valor no se pueda cambiar despues
    private final String marca;
    private final String modelo;
    private final double tarifaDiaria;

    private boolean disponible;

    // Aqui verificamos que los datos si sean validos
    protected Maquina(String codigo, String marca, String modelo, double tarifaDiaria) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio");

        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacia");

        }
          if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El modelo no puede estar vacío."
            );
        }

        if (tarifaDiaria <= 0) {
            throw new IllegalArgumentException(
                "La tarifa diaria debe ser mayor que cero."
            );
        }
        this.codigo = codigo.trim();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true;

    }

    //Poner los getters para poder acceder a los datos sin cambiarlos

    public String getCodigo() {
        return codigo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    // Vertificar si una maquina esta disponible para alquilarla
    public boolean alquilar() {
        if (!disponible) {
            return false;
        }
        disponible = false;
        return true;
    }

    public boolean devolver() {
        if (disponible){
            return false;
        }
        disponible = true;
        return true;
    }

    protected void validarDias(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException( " El numero de dias debe ser mayor a 0");
        }

    }

    public abstract double calcularCosto(int dias);

    public abstract String getCategoria();

    public abstract String getDetalles();
}

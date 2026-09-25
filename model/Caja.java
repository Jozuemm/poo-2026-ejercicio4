package model;

public class Caja {

    private double ingresos;

    public Caja() {
        this.ingresos = 0.0;
    }

    public void registrarIngreso(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");

        }
        ingresos += monto;
    }

    public double getIngresos() {
        return ingresos;
    }

    public double getIngesos() {
        return getIngresos();
    }
    
}

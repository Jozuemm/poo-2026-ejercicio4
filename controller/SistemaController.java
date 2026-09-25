package controller;

import java.util.Collection;
import model.Caja;
import model.FuenteChocolate;
import model.Inventario;
import model.Maquina;
import model.MaquinaAlgodon;
import model.MaquinaPalomitas;

public class SistemaController {

    private final Inventario inventario;
    private final Caja caja;

    public SistemaController(Inventario inventario, Caja caja) {
        if (inventario == null) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        if (caja == null) {
            throw new IllegalArgumentException("La caja no puede ser nula.");
        }
        this.inventario = inventario;
        this.caja = caja;
    }

    public String registrarAlgodon(String marca, String modelo, double tarifa, int potencia) {
        MaquinaAlgodon maquina = new MaquinaAlgodon(marca, modelo, tarifa, potencia);
        boolean agregada = inventario.agregar(maquina);
        if (!agregada) {
            throw new IllegalStateException("No se pudo registrar la máquina. Código ya existente.");
        }
        return maquina.getCodigo();
    }

    public String registrarChocolate(String marca, String modelo, double tarifa, double capacidad) {
        FuenteChocolate maquina = new FuenteChocolate(marca, modelo, tarifa, capacidad);
        boolean agregada = inventario.agregar(maquina);
        if (!agregada) {
            throw new IllegalStateException("No se pudo registrar la fuente de chocolate. Código ya existente.");
        }
        return maquina.getCodigo();
    }

    public String registrarPalomitas(String marca, String modelo, double tarifa, int porciones, boolean carrito) {
        MaquinaPalomitas maquina = new MaquinaPalomitas(marca, modelo, tarifa, porciones, carrito);
        boolean agregada = inventario.agregar(maquina);
        if (!agregada) {
            throw new IllegalStateException("No se pudo registrar la máquina de palomitas. Código ya existente.");
        }
        return maquina.getCodigo();
    }

    public Maquina buscarMaquina(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la máquina no puede estar vacío.");
        }
        Maquina maquina = inventario.buscar(codigo);
        if (maquina == null) {
            throw new IllegalArgumentException("No se encontró ninguna máquina con el código: " + codigo.trim().toUpperCase());
        }
        return maquina;
    }

    public Collection<Maquina> consultarInventario() {
        return inventario.obtenerTodas();
    }

    public double cotizar(String codigo, int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("La cantidad de días debe ser mayor a cero.");
        }
        Maquina maquina = buscarMaquina(codigo);
        return maquina.calcularCosto(dias);
    }

    public double confirmarAlquiler(String codigo, int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("La cantidad de días debe ser mayor a cero.");
        }
        Maquina maquina = buscarMaquina(codigo);
        if (!maquina.estaDisponible()) {
            throw new IllegalStateException("La máquina " + maquina.getCodigo() + " ya está alquilada (no está disponible).");
        }
        double costo = maquina.calcularCosto(dias);
        boolean exito = maquina.alquilar();
        if (!exito) {
            throw new IllegalStateException("No se pudo realizar el alquiler de la máquina " + codigo + ".");
        }
        caja.registrarIngreso(costo);
        return costo;
    }

    public void registrarDevolucion(String codigo) {
        Maquina maquina = buscarMaquina(codigo);
        if (maquina.estaDisponible()) {
            throw new IllegalStateException("La máquina " + maquina.getCodigo() + " ya se encuentra disponible (no está alquilada).");
        }
        boolean exito = maquina.devolver();
        if (!exito) {
            throw new IllegalStateException("No se pudo registrar la devolución de la máquina " + codigo + ".");
        }
    }

    public double consultarIngresos() {
        return caja.getIngresos();
    }

    public String generarReporte() {
        String reporte = "\nREPORTE GENERAL\n";
        reporte += "Total de maquinas registradas: " + inventario.contarTotal() + "\n\n";
        reporte += "Desglose por categoria:\n";

        String[] categorias = {"Palomitas", "Algodon de azucar", "Fuente de chocolate"};
        for (String cat : categorias) {
            int disp = inventario.contarDisponibles(cat);
            int alq = inventario.contarAlquiladas(cat);
            int total = disp + alq;
            reporte += "- " + cat + " -> Total: " + total + " (Disponibles: " + disp + ", Alquiladas: " + alq + ")\n";
        }

        reporte += "\nDinero en caja: Q" + String.format("%.2f", caja.getIngresos());
        return reporte;
    }
}


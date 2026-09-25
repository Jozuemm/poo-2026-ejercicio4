package app;

import controller.SistemaController;
import model.Caja;
import model.Inventario;
import view.ConsolaView;

public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Caja caja = new Caja();
        SistemaController controller = new SistemaController(inventario, caja);

        // Inicialización de máquinas de prueba según los requisitos de demostración:
        // Al menos dos de cada categoría con distintas condiciones de cobro.
        
        // Categoria 1: Palomitas
        controller.registrarPalomitas("Ninja", "123", 100.00, 30, true);
        controller.registrarPalomitas("Ninja", "124", 75.00, 15, false);

        // Categoria 2: Algodon de azucar
        controller.registrarAlgodon("Ninja", "125", 80.00, 1200);
        controller.registrarAlgodon("Ninja", "126", 60.00, 800);

        // Categoria 3: Fuente de chocolate
        controller.registrarChocolate("Ninja", "127", 150.00, 2.5);
        controller.registrarChocolate("Ninja", "128", 90.00, 1.5);

        ConsolaView view = new ConsolaView(controller);
        view.iniciar();
    }
}


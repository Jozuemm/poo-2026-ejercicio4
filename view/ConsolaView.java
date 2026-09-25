package view;

import controller.SistemaController;
import java.util.Collection;
import java.util.Scanner;
import model.Maquina;

public class ConsolaView {

    private final Scanner scanner;
    private final SistemaController controller;

    public ConsolaView(SistemaController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("El controlador no puede ser nulo.");
        }
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        System.out.println("Sistema Dulce Estacion");

        while (!salir) {
            try {
                mostrarMenu();
                int opcion = leerEntero("Seleccione una opcion: ");
                System.out.println();

                switch (opcion) {
                    case 1:
                        registrarMaquina();
                        break;
                    case 2:
                        mostrarInventario();
                        break;
                    case 3:
                        cotizar();
                        break;
                    case 4:
                        alquilar();
                        break;
                    case 5:
                        devolver();
                        break;
                    case 6:
                        mostrarReporte();
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Registrar maquina");
        System.out.println("2. Consultar inventario");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Alquilar maquina");
        System.out.println("5. Devolver maquina");
        System.out.println("6. Reporte general");
        System.out.println("7. Salir");
    }

    private void registrarMaquina() {
        System.out.println("\nRegistro de maquina:");
        System.out.println("1. Maquina de palomitas");
        System.out.println("2. Maquina de algodon de azucar");
        System.out.println("3. Fuente de chocolate");
        System.out.println("4. Cancelar");
        int tipo = leerEntero("Opcion: ");

        if (tipo == 4) {
            System.out.println("Registro cancelado.");
            return;
        }
        if (tipo < 1 || tipo > 3) {
            System.out.println("Opcion no valida.");
            return;
        }

        try {
            System.out.print("Marca: ");
            String marca = scanner.nextLine().trim();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine().trim();

            double tarifa = leerDecimal("Tarifa diaria (Q): ");

            String codigoGenerado = "";

            switch (tipo) {
                case 1:
                    int porciones = leerEntero("Porciones por hora: ");
                    System.out.print("Tiene carrito integrado? (s/n): ");
                    String respCarrito = scanner.nextLine().trim().toLowerCase();
                    boolean carrito = respCarrito.equals("s") || respCarrito.equals("si");
                    codigoGenerado = controller.registrarPalomitas(marca, modelo, tarifa, porciones, carrito);
                    break;
                case 2:
                    int potencia = leerEntero("Potencia en watts: ");
                    codigoGenerado = controller.registrarAlgodon(marca, modelo, tarifa, potencia);
                    break;
                case 3:
                    double capacidad = leerDecimal("Capacidad maxima en kg: ");
                    codigoGenerado = controller.registrarChocolate(marca, modelo, tarifa, capacidad);
                    break;
            }

            System.out.println("Maquina registrada con codigo: " + codigoGenerado);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarInventario() {
        System.out.println("\nInventario:");
        Collection<Maquina> maquinas = controller.consultarInventario();
        if (maquinas.isEmpty()) {
            System.out.println("No hay maquinas en el inventario.");
            return;
        }

        for (Maquina m : maquinas) {
            String estado = m.estaDisponible() ? "Disponible" : "Alquilada";
            System.out.println(m.getCodigo() + " - " + m.getCategoria() + " " + m.getMarca() + " " + m.getModelo()
                    + " | Tarifa: Q" + String.format("%.2f", m.getTarifaDiaria()) + " | Estado: " + estado + " | " + m.getDetalles());
        }
    }

    private void cotizar() {
        System.out.println("\nCotizar alquiler:");
        System.out.print("Codigo de la maquina: ");
        String codigo = scanner.nextLine().trim();
        int dias = leerEntero("Cantidad de dias: ");

        try {
            Maquina m = controller.buscarMaquina(codigo);
            double costoTotal = controller.cotizar(codigo, dias);

            System.out.println("\nCotizacion:");
            System.out.println("Codigo: " + m.getCodigo());
            System.out.println("Maquina: " + m.getCategoria() + " " + m.getMarca() + " " + m.getModelo());
            System.out.println("Detalles: " + m.getDetalles());
            System.out.printf("Tarifa diaria: Q%.2f%n", m.getTarifaDiaria());
            System.out.println("Dias: " + dias);
            System.out.println("Estado: " + (m.estaDisponible() ? "Disponible" : "Alquilada (no disponible por el momento)"));
            System.out.printf("Total: Q%.2f%n", costoTotal);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void alquilar() {
        System.out.println("\nAlquilar maquina:");
        System.out.print("Codigo de la maquina: ");
        String codigo = scanner.nextLine().trim();
        int dias = leerEntero("Cantidad de dias: ");

        try {
            Maquina m = controller.buscarMaquina(codigo);
            if (!m.estaDisponible()) {
                System.out.println("Error: La maquina " + m.getCodigo() + " ya se encuentra alquilada.");
                return;
            }

            double total = controller.cotizar(codigo, dias);

            System.out.println("\nResumen del alquiler:");
            System.out.println("Maquina: " + m.getCodigo() + " - " + m.getMarca() + " " + m.getModelo());
            System.out.printf("Total a cobrar: Q%.2f%n", total);

            System.out.print("Confirma el alquiler? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                double cobrado = controller.confirmarAlquiler(codigo, dias);
                System.out.printf("Alquiler confirmado. Total cobrado: Q%.2f%n", cobrado);
            } else {
                System.out.println("Alquiler cancelado. No se cobro nada.");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void devolver() {
        System.out.println("\nDevolver maquina:");
        System.out.print("Codigo de la maquina: ");
        String codigo = scanner.nextLine().trim();

        try {
            controller.registrarDevolucion(codigo);
            System.out.println("Devolucion realizada. La maquina " + codigo.toUpperCase() + " ahora esta disponible.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarReporte() {
        System.out.println(controller.generarReporte());
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero decimal.");
            }
        }
    }
}

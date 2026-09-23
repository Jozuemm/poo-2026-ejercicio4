# EJERCICIO 4
 
## INTEGRANTES

- Josué Morales - 26588
- Daniel Villatoro - 26241

## ANALISIS
### Organización general del sistema

Organizartemos el sistema utilizando el Modelo vista controlador. El modelo representará las máquinas y conservará laa información del inventario e ingresos. La vista permitira que el usuario interactue con el programa mediante un menu de consola. El controlador recibirá las solicitudes de la vista y coordinara las operaciones realizadas por lso objetos del modelo. 

### Caracteristicas comunes de las maquinas

Todas las máquinas tendran un codigo de inventario, marca, modelo, tarifa diarioa y estado de disponibilidad. Estos se ubicaran en la clase Maquina para evitar la repeticion en cada categoria. 

La clase también declarará el método CalcularCosto(int dias). Cada subclase de Maquina implementara el metodo de acuerdo a su propio regla de cobro. Permitiendo asi que el controlador calcule el aqlquiler mediante polimorfismo. 

### Generación de codigos

Usaremos un sistema de codigos dependiendo el tipo de maquina
- Algodon de azucar - AG001, AG002 y asi sucesivamente
- Fuente de Chocolate - CH001, CH002 ....
- Maquina de poporopos - PP001, PP002 ....

El constructor de cada subclase generará el codigo utilizando su prefijo y contador. Utilizaremos 

#### String.format("AG%03d", siguienteNumero);

Para que asi 1 sea 001, 12 012 y 123 siga siendo 123
Y asi evitar coicidencias entre categorias

### Responsabilidad del inventario

Inventario almacenará las máquinas utilizando

#### HashMap<String, Maquina>

La llave sera el codigo y el valor el objeto completo como 
AG001 seria un objeto MaquinaAlgodon
Lo cual nos va a permitir buscar directamente una maquina por su codigo. También Inventario comprobará que el codigo no se encuentre ya registrado para protegernos de duplicados

### Responsabilidad de la caja

La clase Caja va a conservar unicamente el dinero recibido mediante alquileres confirmados. O sea que no se registrará dinero cuando:
- Se realice una cotización
- El cliente cancele la confirmación
- Se intente alquilar una máquina ocupada
- Se devuelva una máquina
- Ocurra cualquier operación inválida
El controlador solo llamará a registrarIngreso() despues de comprobar que el alquiler fue aceptado y que la maquina cambio correctamente a ocupada

### Flujo de un alquiler

1. La vista solicita el código y los días
2. El controlador busca la máquina en el inventario
3. Verifica que exista y esté disponible
4. Solicita a la máquina que calcule el costo
5. La vista muestra el total y pregunta si se confirma
6. Si el usuario acepta, el controlador marca la máquina como ocupada
7. El controlador registra el total en la caja
8. Si el usuario cancela, no se modifica ningún objeto

Esta será la base ordenada para implementar primero la version de consola. Despues asi podremos agregar VistaSwing para aprender a ya hacer front jeje
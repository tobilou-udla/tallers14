package AplicacionClub;

import java.util.Scanner;
import java.util.ArrayList;
import club.*;
import club.Socio.Tipo;
import club.Excepciones.*;

/**
 * Clase principal del sistema de administración del Club.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        Club club = new Club();

        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Ingrese una opción: ");

            switch(opcion) {
                case 1:
                    afiliarSocio(sc, club);
                    break;
                case 2:
                    registrarAutorizado(sc, club);
                    break;
                case 3:
                    pagarFactura(sc, club);
                    break;
                case 4:
                    registrarConsumo(sc, club);
                    break;
                case 5:
                    aumentarFondos(sc, club);
                    break;
                case 6:
                    calcularTotalConsumos(sc, club);
                    break;
                case 7:
                    verificarEliminacionSocio(sc, club);
                    break;
                case 8:
                    System.out.println("\n¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while(opcion != 8);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n========== SISTEMA DE ADMINISTRACIÓN DEL CLUB ==========");
        System.out.println("1. Afiliar un socio al club");
        System.out.println("2. Registrar persona autorizada");
        System.out.println("3. Pagar una factura");
        System.out.println("4. Registrar un consumo");
        System.out.println("5. Aumentar fondos");
        System.out.println("6. Calcular total de consumos");
        System.out.println("7. Verificar si se puede eliminar socio");
        System.out.println("8. Salir");
        System.out.println("========================================================");
    }

    private static void afiliarSocio(Scanner sc, Club club) {
        try {
            System.out.println("\n--- AFILIAR NUEVO SOCIO ---");

            System.out.print("Ingrese cédula: ");
            String cedula = sc.nextLine().trim();
            if(cedula.isEmpty()) {
                System.out.println("Error: La cédula no puede estar vacía.");
                return;
            }

            System.out.print("Ingrese nombre completo: ");
            String nombre = sc.nextLine().trim();
            if(nombre.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío.");
                return;
            }

            int tipoNum = leerEntero(sc, "Tipo de suscripción (1=Regular, 2=VIP): ");
            if(tipoNum != 1 && tipoNum != 2) {
                System.out.println("Error: Tipo inválido. Debe ser 1 o 2.");
                return;
            }

            Tipo tipo = (tipoNum == 2) ? Tipo.VIP : Tipo.REGULAR;
            club.afiliarSocio(cedula, nombre, tipo);

            System.out.println("Socio afiliado exitosamente.");
            System.out.println("Tipo: " + tipo);
            System.out.println("Fondos iniciales: $" +
                    (tipo == Tipo.VIP ? Socio.FONDOS_INICIALES_VIP : Socio.FONDOS_INICIALES_REGULARES));

        } catch(SocioYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(LimiteVIPException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarAutorizado(Scanner sc, Club club) {
        try {
            System.out.println("\n--- REGISTRAR PERSONA AUTORIZADA ---");

            System.out.print("Ingrese cédula del socio: ");
            String cedula = sc.nextLine().trim();
            if(cedula.isEmpty()) {
                System.out.println("Error: La cédula no puede estar vacía.");
                return;
            }

            System.out.print("Ingrese nombre de la persona autorizada: ");
            String nombreAutorizado = sc.nextLine().trim();
            if(nombreAutorizado.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío.");
                return;
            }

            club.agregarAutorizadoSocio(cedula, nombreAutorizado);
            System.out.println("Persona autorizada registrada exitosamente.");

        } catch(SocioNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(AutorizadoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(FondosInsuficientesException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(AutorizadoYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void pagarFactura(Scanner sc, Club club) {
        try {
            System.out.println("\n--- PAGAR FACTURA ---");

            System.out.print("Ingrese cédula del socio: ");
            String cedula = sc.nextLine().trim();
            if(cedula.isEmpty()) {
                System.out.println("Error: La cédula no puede estar vacía.");
                return;
            }

            ArrayList<Factura> facturas = club.darFacturasSocio(cedula);

            if(facturas.isEmpty()) {
                System.out.println("El socio no tiene facturas pendientes.");
                return;
            }

            System.out.println("\nFacturas pendientes:");
            for(int i = 0; i < facturas.size(); i++) {
                System.out.println("[" + i + "] " + facturas.get(i).toString());
            }

            int indice = leerEntero(sc, "Ingrese número de la factura a pagar: ");

            if(indice < 0 || indice >= facturas.size()) {
                System.out.println("Error: Número de factura inválido.");
                return;
            }

            club.pagarFacturaSocio(cedula, indice);
            System.out.println("Factura pagada exitosamente.");

        } catch(SocioNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(FondosInsuficientesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarConsumo(Scanner sc, Club club) {
        try {
            System.out.println("\n--- REGISTRAR CONSUMO ---");

            System.out.print("Ingrese cédula del socio: ");
            String cedula = sc.nextLine().trim();
            if(cedula.isEmpty()) {
                System.out.println("Error: La cédula no puede estar vacía.");
                return;
            }

            System.out.print("Ingrese nombre de quien consume: ");
            String nombreCliente = sc.nextLine().trim();
            if(nombreCliente.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío.");
                return;
            }

            System.out.print("Ingrese concepto del consumo: ");
            String concepto = sc.nextLine().trim();
            if(concepto.isEmpty()) {
                System.out.println("Error: El concepto no puede estar vacío.");
                return;
            }

            double valor = leerDouble(sc, "Ingrese valor del consumo: $");
            if(valor <= 0) {
                System.out.println("Error: El valor debe ser mayor a 0.");
                return;
            }

            club.registrarConsumo(cedula, nombreCliente, concepto, valor);
            System.out.println("Consumo registrado exitosamente.");
            System.out.println("Se generó una factura por $" + valor);

        } catch(SocioNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(FondosInsuficientesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void aumentarFondos(Scanner sc, Club club) {
        try {
            System.out.println("\n--- AUMENTAR FONDOS ---");

            System.out.print("Ingrese cédula del socio: ");
            String cedula = sc.nextLine().trim();
            if(cedula.isEmpty()) {
                System.out.println("Error: La cédula no puede estar vacía.");
                return;
            }

            Socio socio = club.buscarSocio(cedula);
            if(socio == null) {
                System.out.println("Error: No existe un socio con la cédula: " + cedula);
                return;
            }

            System.out.println("Fondos actuales: $" + socio.darFondos());
            System.out.println("Límite máximo: $" +
                    (socio.darTipo() == Tipo.VIP ? Socio.MONTO_MAXIMO_VIP : Socio.MONTO_MAXIMO_REGULARES));

            double monto = leerDouble(sc, "Ingrese monto a aumentar: $");
            if(monto <= 0) {
                System.out.println("Error: El monto debe ser mayor a 0.");
                return;
            }

            club.aumentarFondosSocio(cedula, monto);
            System.out.println("Fondos aumentados exitosamente.");
            System.out.println("Nuevos fondos: $" + socio.darFondos());

        } catch(SocioNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(LimiteFondosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void calcularTotalConsumos(Scanner sc, Club club) {
        try {
            System.out.println("\n--- CALCULAR TOTAL DE CONSUMOS ---");

            System.out.print("Ingrese cédula del socio: ");
            String cedula = sc.nextLine().trim();
            if(cedula.isEmpty()) {
                System.out.println("Error: La cédula no puede estar vacía.");
                return;
            }

            double total = club.calcularTotalConsumos(cedula);

            System.out.println("\n========================================");
            if(total == 0) {
                System.out.println("El socio no tiene consumos registrados.");
                System.out.println("Total de consumos: $0.00");
            } else {
                System.out.println("Total de consumos: $" + total);
            }
            System.out.println("========================================");

        } catch(SocioNoExisteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verificarEliminacionSocio(Scanner sc, Club club) {
        System.out.println("\n--- VERIFICAR SI SE PUEDE ELIMINAR SOCIO ---");

        System.out.print("Ingrese cédula del socio: ");
        String cedula = sc.nextLine().trim();
        if(cedula.isEmpty()) {
            System.out.println("Error: La cédula no puede estar vacía.");
            return;
        }

        String resultado = club.sePuedeEliminarSocio(cedula);
        System.out.println("\n" + resultado);
    }

    private static int leerEntero(Scanner sc, String mensaje) {
        int numero = 0;
        boolean valido = false;

        while(!valido) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine().trim();
                numero = Integer.parseInt(linea);
                valido = true;
            } catch(NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }

        return numero;
    }

    private static double leerDouble(Scanner sc, String mensaje) {
        double numero = 0;
        boolean valido = false;

        while(!valido) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine().trim();
                numero = Double.parseDouble(linea);
                valido = true;
            } catch(NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }

        return numero;
    }
}
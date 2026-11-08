package co.uniquindio.edu;

mport java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Clase principal: menu interactivo para registrar informacion, gestionar pedidos
 * y demostrar las principales operaciones del restaurante Sabor & Tradicion.
 */
public class Aplicacion {

     private static final Scanner scanner = new Scanner(System.in);
     private static final Restaurante restaurante =
             new Restaurante("Sabor & Tradición", "900.123.456-7", "Armenia, Quindío");

     public static void main(String[] args) {
          int opcion = -1;
          try {
               do {
                    mostrarMenu();
                    opcion = leerEntero("Seleccione una opción: ");
                    ejecutarOpcion(opcion);
               } while (opcion != 0);
          } catch (NoSuchElementException e) {
               System.out.println("\nEntrada finalizada. Cerrando la aplicación.");
          }
     }

     // ------------------------------------------------------------------ MENU
     private static void mostrarMenu() {
          System.out.println("\n==================================================");
          System.out.println("   " + restaurante.getNombreComercial().toUpperCase());
          System.out.println("   NIT: " + restaurante.getNit() + " | " + restaurante.getDireccion());
          System.out.println("==================================================");
          System.out.println(" 1.  Registrar productos en el menú");
          System.out.println(" 2.  Registrar clientes");
          System.out.println(" 3.  Calcular precio de venta de un producto");
          System.out.println(" 4.  Verificar condiciones de calidad de los productos");
          System.out.println(" 5.  Consultar productos disponibles en el menú");
          System.out.println(" 6.  Registrar pedido (sin confirmar)");
          System.out.println(" 7.  Gestionar servicio de domicilio");
          System.out.println(" 8.  Confirmar pedido");
          System.out.println(" 9.  Calcular valor total de un pedido");
          System.out.println("10.  Consultar pedidos de un cliente");
          System.out.println("11.  Actualizar y consultar estado de pedidos");
          System.out.println("12.  Generar reportes de ventas");
          System.out.println("13.  Pruebas unitarias (información)");
          System.out.println("14.  Cargar datos de ejemplo");
          System.out.println(" 0.  Salir");
          System.out.println("--------------------------------------------------");
     }

     private static void ejecutarOpcion(int opcion) {
          try {
               switch (opcion) {
                    case 1:  registrarProducto(); break;
                    case 2:  registrarCliente(); break;
                    case 3:  calcularPrecioVenta(); break;
                    case 4:  verificarCalidad(); break;
                    case 5:  consultarMenu(); break;
                    case 6:  registrarPedido(); break;
                    case 7:  solicitarDomicilio(); break;
                    case 8:  confirmarPedido(); break;
                    case 9:  consultarTotal(); break;
                    case 10: consultarPedidosCliente(); break;
                    case 11: gestionarEstado(); break;
                    case 12: System.out.println("\n" + restaurante.generarReporteVentas()); break;
                    case 13: mostrarPruebasUnitarias(); break;
                    case 14: cargarDatosEjemplo(); break;
                    case 0:  System.out.println("¡Hasta pronto!"); break;
                    default: System.out.println("Opción no válida.");
               }
          } catch (IllegalStateException | IllegalArgumentException e) {
               System.out.println("Error: " + e.getMessage());
          }
     }

     // ------------------------------------------------------------- PRODUCTOS
     private static void registrarProducto() {
          System.out.println("\n--- Registrar producto ---");
          System.out.println("1. Plato fuerte  2. Bebida  3. Postre");
          int categoria = leerEntero("Categoría: ");
          if (categoria < 1 || categoria > 3) {
               System.out.println("Categoría no válida.");
               return;
          }
          String codigo = leerTexto("Código único: ");
          String nombre = leerTexto("Nombre: ");
          String descripcion = leerTexto("Descripción: ");
          double precioBase = leerDouble("Precio base: ");
          int tiempo = leerEntero("Tiempo estimado de preparación (min): ");

          Producto producto;
          if (categoria == 1) {
               double peso = leerDouble("Peso (g): ");
               System.out.println("Tipo de preparación: 1. Asado  2. Frito  3. Horneado  4. Plancha");
               int t = leerEntero("Tipo: ");
               TipoPreparacion[] tipos = TipoPreparacion.values();
               if (t < 1 || t > tipos.length) {
                    System.out.println("Tipo de preparación no válido.");
                    return;
               }
               boolean coccion = leerSiNo("¿Superó el control de cocción? (s/n): ");
               producto = new PlatoFuerte(codigo, nombre, descripcion, precioBase, tiempo, peso, tipos[t - 1], coccion);
          } else if (categoria == 2) {
               double volumen = leerDouble("Volumen (ml): ");
               double temperatura = leerDouble("Temperatura de servicio (°C): ");
               boolean fria = leerSiNo("¿Es fría? (s/n): ");
               boolean especial = leerSiNo("¿Requiere preparación especial? (s/n): ");
               producto = new Bebida(codigo, nombre, descripcion, precioBase, tiempo, volumen, temperatura, fria, especial);
          } else {
               double peso = leerDouble("Peso (g): ");
               double temperatura = leerDouble("Temperatura de almacenamiento (°C): ");
               boolean refrigeracion = leerSiNo("¿Requiere refrigeración? (s/n): ");
               producto = new Postre(codigo, nombre, descripcion, precioBase, tiempo, peso, temperatura, refrigeracion);
          }

          if (restaurante.registrarProducto(producto)) {
               System.out.println("Producto registrado. Precio de venta: " + moneda(producto.calcularPrecioVenta()));
               if (!producto.cumpleCondicionesCalidad()) {
                    System.out.println("Atención: NO cumple las condiciones de calidad; no podrá incluirse en pedidos confirmados.");
               }
          } else {
               System.out.println("No se registró: ya existe un producto con ese código.");
          }
     }

     private static void consultarMenu() {
          System.out.println("\n--- Menú del restaurante ---");
          List<Producto> menu = restaurante.consultarMenu();
          if (menu.isEmpty()) {
               System.out.println("El menú está vacío.");
               return;
          }
          for (Producto p : menu) {
               System.out.printf("[%s] %-12s %-22s base %-9s venta %-9s %d min | %s%n",
                       p.getCodigo(), p.getClass().getSimpleName(), p.getNombre(),
                       moneda(p.getPrecioBase()), moneda(p.calcularPrecioVenta()),
                       p.getTiempoPreparacion(), p.getDescripcion());
          }
     }

     private static void calcularPrecioVenta() {
          System.out.println("\n--- Calcular precio de venta ---");
          Producto p = restaurante.buscarProducto(leerTexto("Código del producto: "));
          if (p == null) {
               System.out.println("El producto no pertenece al menú.");
               return;
          }
          System.out.println(p.getNombre() + " (" + p.getClass().getSimpleName() + ")");
          System.out.println("Precio base:            " + moneda(p.getPrecioBase()));
          if (p instanceof PlatoFuerte) {
               System.out.println("Incremento plato fuerte: 20 %");
          } else if (p instanceof Bebida) {
               System.out.println("Incremento bebida:       10 %");
               if (((Bebida) p).getRequierePreparacionEspecial()) {
                    System.out.println("Recargo preparación especial: " + moneda(3000) + "");
               }
          } else if (p instanceof Postre) {
               System.out.println("Incremento postre:       15 %");
               if (((Postre) p).getRequiereRefrigeracion()) {
                    System.out.println("Incremento por refrigeración: 5 % del precio base");
               }
          }
          System.out.println("PRECIO DE VENTA:        " + moneda(p.calcularPrecioVenta()));
     }

     private static void verificarCalidad() {
          System.out.println("\n--- Verificación de condiciones de calidad ---");
          List<Producto> menu = restaurante.consultarMenu();
          if (menu.isEmpty()) {
               System.out.println("El menú está vacío.");
               return;
          }
          int rechazados = 0;
          for (Producto p : menu) {
               boolean ok = p.cumpleCondicionesCalidad();
               if (!ok) {
                    rechazados++;
               }
               System.out.printf("[%s] %-22s %s%n", p.getCodigo(), p.getNombre(), ok ? "CUMPLE" : "RECHAZADO");
          }
          System.out.println("Aprobados: " + (menu.size() - rechazados) + " | Rechazados: " + rechazados);
     }

     // --------------------------------------------------------------- CLIENTES
     private static void registrarCliente() {
          System.out.println("\n--- Registrar cliente ---");
          String documento = leerTexto("Número de documento: ");
          String nombre = leerTexto("Nombre completo: ");
          String telefono = leerTexto("Teléfono: ");
          String correo = leerTexto("Correo electrónico: ");
          String direccion = leerTexto("Dirección de residencia: ");
          Cliente cliente = new Cliente(documento, nombre, telefono, correo, direccion);
          System.out.println(restaurante.registrarCliente(cliente)
                  ? "Cliente registrado correctamente."
                  : "No se registró: ya existe un cliente con ese documento.");
     }

     // ---------------------------------------------------------------- PEDIDOS
     private static void registrarPedido() {
          System.out.println("\n--- Registrar pedido ---");
          Cliente cliente = restaurante.buscarCliente(leerTexto("Documento del cliente: "));
          if (cliente == null) {
               System.out.println("El cliente no está registrado.");
               return;
          }
          if (restaurante.consultarMenu().isEmpty()) {
               System.out.println("El menú está vacío. Registre productos primero.");
               return;
          }
          System.out.println("Modalidad: 1. Consumo en restaurante  2. Domicilio");
          int m = leerEntero("Modalidad: ");
          if (m != 1 && m != 2) {
               System.out.println("Modalidad no válida.");
               return;
          }
          Pedido pedido = restaurante.registrarPedido(cliente,
                  m == 1 ? ModalidadEntrega.RESTAURANTE : ModalidadEntrega.DOMICILIO);

          boolean seguir = true;
          while (seguir) {
               consultarMenu();
               Producto producto = restaurante.buscarProducto(leerTexto("Código del producto: "));
               if (producto == null) {
                    System.out.println("El producto no pertenece al menú.");
               } else {
                    int cantidad = leerEntero("Cantidad: ");
                    if (cantidad <= 0) {
                         System.out.println("La cantidad debe ser mayor que cero.");
                    } else {
                         pedido.agregarProducto(producto, cantidad);
                         System.out.println("Producto agregado.");
                    }
               }
               seguir = leerSiNo("¿Agregar otro producto? (s/n): ");
          }
          System.out.println("Pedido " + pedido.getCodigo() + " registrado en estado PENDIENTE. "
                  + "Valor productos: " + moneda(pedido.calcularValorProductos()));
          if (pedido.getModalidad() == ModalidadEntrega.DOMICILIO) {
               System.out.println("Recuerde solicitar el domicilio (opción 7) antes de confirmar.");
          }
     }

     private static void solicitarDomicilio() {
          System.out.println("\n--- Solicitar domicilio ---");
          Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
          if (pedido == null) {
               System.out.println("Pedido no encontrado.");
               return;
          }
          if (pedido.getModalidad() != ModalidadEntrega.DOMICILIO) {
               System.out.println("Este pedido no es a domicilio.");
               return;
          }
          if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
               System.out.println("Solo se puede solicitar domicilio a pedidos pendientes.");
               return;
          }
          System.out.println("Empresa: 1. RápidoExpress (hasta 12 km)  2. EntregaSegura (hasta 20 km)");
          int e = leerEntero("Empresa: ");
          if (e != 1 && e != 2) {
               System.out.println("Empresa no válida.");
               return;
          }
          double km = leerDouble("Distancia del recorrido (km): ");
          EmpresaDomicilio empresa = (e == 1) ? new RapidoExpress() : new EntregaSegura();

          if (restaurante.solicitarDomicilio(pedido, empresa, km)) {
               System.out.println(empresa.getNombre() + " aceptó el servicio. Costo del domicilio: "
                       + moneda(pedido.getDomicilio().getCosto()));
          } else {
               System.out.println(empresa.getNombre() + " NO aceptó el servicio (distancia no permitida). "
                       + "Pruebe con otra empresa o distancia.");
          }
     }

     private static void confirmarPedido() {
          System.out.println("\n--- Confirmar pedido ---");
          Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
          if (pedido == null) {
               System.out.println("Pedido no encontrado.");
               return;
          }
          if (restaurante.confirmarPedido(pedido)) {
               System.out.println("Pedido confirmado. Total (con domicilio si aplica): " + moneda(pedido.calcularTotal()));
          } else {
               System.out.println("No se pudo confirmar. Verifique: cliente registrado, productos del menú, "
                       + "cantidades mayores que cero, calidad de los productos, domicilio aceptado "
                       + "y que el pedido esté pendiente.");
          }
     }

     private static void consultarTotal() {
          System.out.println("\n--- Total del pedido ---");
          Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
          if (pedido == null) {
               System.out.println("Pedido no encontrado.");
               return;
          }
          System.out.println("Productos: " + moneda(pedido.calcularValorProductos()));
          if (pedido.getDomicilio() != null) {
               System.out.println("Domicilio: " + moneda(pedido.getDomicilio().getCosto()));
          }
          System.out.println("TOTAL: " + moneda(pedido.calcularTotal()));
     }

     private static void consultarPedidosCliente() {
          System.out.println("\n--- Pedidos de un cliente ---");
          String documento = leerTexto("Documento del cliente: ");
          Cliente cliente = restaurante.buscarCliente(documento);
          if (cliente == null) {
               System.out.println("El cliente no está registrado.");
               return;
          }
          List<Pedido> lista = restaurante.consultarPedidosCliente(documento);
          if (lista.isEmpty()) {
               System.out.println(cliente.getNombreCompleto() + " no tiene pedidos.");
          }
          for (Pedido p : lista) {
               System.out.println("\nPedido " + p.getCodigo() + " | " + p.getFecha() + " | "
                       + p.getModalidad() + " | Estado: " + p.getEstado());
               for (DetallePedido d : p.getDetalles()) {
                    System.out.printf("   %-22s x%-3d %s%n", d.getProducto().getNombre(),
                            d.getCantidad(), moneda(d.calcularSubtotal()));
               }
               System.out.println("   Total: " + moneda(p.calcularTotal()));
          }
     }

     private static void gestionarEstado() {
          System.out.println("\n--- Estado de pedidos ---");
          System.out.println("1. Consultar estado de un pedido");
          System.out.println("2. Actualizar estado de un pedido");
          System.out.println("3. Consultar pedidos por estado");
          int op = leerEntero("Opción: ");
          switch (op) {
               case 1:  consultarEstado(); break;
               case 2:  actualizarEstado(); break;
               case 3:  consultarPorEstado(); break;
               default: System.out.println("Opción no válida.");
          }
     }

     private static void consultarEstado() {
          Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
          System.out.println(pedido == null ? "Pedido no encontrado."
                  : "Pedido " + pedido.getCodigo() + ": " + pedido.getEstado());
     }

     private static void consultarPorEstado() {
          System.out.println("Estado: 1. Pendiente  2. Confirmado  3. Entregado  4. Cancelado");
          int n = leerEntero("Estado: ");
          EstadoPedido[] estados = EstadoPedido.values();
          if (n < 1 || n > estados.length) {
               System.out.println("Estado no válido.");
               return;
          }
          boolean hay = false;
          for (Pedido p : restaurante.getPedidos()) {
               if (p.getEstado() == estados[n - 1]) {
                    System.out.println(p);
                    hay = true;
               }
          }
          if (!hay) {
               System.out.println("No hay pedidos en estado " + estados[n - 1] + ".");
          }
     }

     private static void actualizarEstado() {
          Pedido pedido = restaurante.buscarPedido(leerTexto("Código del pedido: "));
          if (pedido == null) {
               System.out.println("Pedido no encontrado.");
               return;
          }
          System.out.println("Estado actual: " + pedido.getEstado());
          System.out.println("Nuevo estado: 1. Confirmado  2. Entregado  3. Cancelado");
          int n = leerEntero("Nuevo estado: ");
          EstadoPedido nuevo;
          switch (n) {
               case 1:  nuevo = EstadoPedido.CONFIRMADO; break;
               case 2:  nuevo = EstadoPedido.ENTREGADO; break;
               case 3:  nuevo = EstadoPedido.CANCELADO; break;
               default:
                    System.out.println("Estado no válido.");
                    return;
          }
          System.out.println(restaurante.actualizarEstado(pedido, nuevo)
                  ? "Estado actualizado a " + pedido.getEstado() + "."
                  : "Cambio de estado no permitido.");
     }

     // ------------------------------------------------------ PRUEBAS UNITARIAS
     private static void mostrarPruebasUnitarias() {
          System.out.println("\n--- Pruebas unitarias (JUnit 5) ---");
          System.out.println("Hay 5 pruebas por cada clase concreta del modelo con comportamiento verificable (50 en total):");
          System.out.println("  PlatoFuerteTest, BebidaTest, PostreTest, RapidoExpressTest, EntregaSeguraTest,");
          System.out.println("  DomicilioTest, DetallePedidoTest, ClienteTest, PedidoTest y RestauranteTest.");
          System.out.println("Se encuentran en la carpeta 'test/modelo'.");
          System.out.println("Para ejecutarlas en IntelliJ: clic derecho sobre la carpeta 'test' -> Run 'All Tests'.");
     }

     // ----------------------------------------------------------- DATOS DEMO
     private static void cargarDatosEjemplo() {
          restaurante.registrarProducto(new PlatoFuerte("PF01", "Bandeja paisa", "Plato típico", 25000, 40, 600, TipoPreparacion.FRITO, true));
          restaurante.registrarProducto(new PlatoFuerte("PF02", "Churrasco", "Carne a la plancha", 30000, 25, 450, TipoPreparacion.PLANCHA, true));
          restaurante.registrarProducto(new PlatoFuerte("PF03", "Sancocho", "Cocción no aprobada", 22000, 50, 700, TipoPreparacion.HORNEADO, false));
          restaurante.registrarProducto(new Bebida("BE01", "Limonada de coco", "Preparación especial", 8000, 10, 400, 5, true, true));
          restaurante.registrarProducto(new Bebida("BE02", "Café", "Café caliente", 4000, 5, 250, 70, false, false));
          restaurante.registrarProducto(new Postre("PO01", "Tres leches", "Requiere nevera", 9000, 5, 200, 4, true));
          restaurante.registrarProducto(new Postre("PO02", "Flan", "Sin refrigeración", 7000, 5, 150, 20, false));
          restaurante.registrarCliente(new Cliente("1001", "Ana Gómez", "3001112233", "ana@correo.com", "Calle 10 #5-20"));
          restaurante.registrarCliente(new Cliente("1002", "Luis Pérez", "3104445566", "luis@correo.com", "Carrera 15 #8-30"));
          System.out.println("Datos de ejemplo cargados (7 productos y 2 clientes). El producto PF03 no cumple calidad.");
     }

     // ------------------------------------------------------ LECTURA SEGURA
     private static String leerTexto(String mensaje) {
          System.out.print(mensaje);
          return scanner.nextLine().trim();
     }

     private static int leerEntero(String mensaje) {
          while (true) {
               try {
                    return Integer.parseInt(leerTexto(mensaje));
               } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido.");
               }
          }
     }

     private static double leerDouble(String mensaje) {
          while (true) {
               try {
                    return Double.parseDouble(leerTexto(mensaje).replace(",", "."));
               } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido.");
               }
          }
     }

     private static boolean leerSiNo(String mensaje) {
          while (true) {
               String r = leerTexto(mensaje).toLowerCase();
               if (r.equals("s") || r.equals("si") || r.equals("sí")) return true;
               if (r.equals("n") || r.equals("no")) return false;
               System.out.println("Responda s o n.");
          }
     }

     private static String moneda(double valor) {
          return "$" + String.format("%,.0f", valor);
     }
}
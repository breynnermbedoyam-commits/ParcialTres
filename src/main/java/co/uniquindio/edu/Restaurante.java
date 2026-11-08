package co.uniquindio.edu;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Restaurante {

    private String nombreComercial;
    private String nit;
    private String direccion;
    private List<Producto> menu;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;

    public Restaurante(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.menu = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getMenu() {
        return menu;
    }

    public void setMenu(List<Producto> menu) {
        this.menu = menu;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    private int contadorPedidos = 0;

    // ---------------- Busquedas ----------------
    public Producto buscarProducto(String codigo) {
        for (Producto p : menu) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String documento) {
        for (Cliente c : clientes) {
            if (c.getDocumento().equalsIgnoreCase(documento)) {
                return c;
            }
        }
        return null;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido p : pedidos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    // ---------------- Registros ----------------
    /** Registra un producto en el menu evitando codigos duplicados. */
    public boolean registrarProducto(Producto producto) {
        if (producto == null || buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        menu.add(producto);
        return true;
    }

    /** Registra un cliente evitando documentos repetidos. */
    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getDocumento()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    /** Crea un pedido pendiente con codigo unico (PED-001, PED-002, ...). */
    public Pedido registrarPedido(Cliente cliente, ModalidadEntrega modalidad) {
        contadorPedidos++;
        String codigo = String.format("PED-%03d", contadorPedidos);
        Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, modalidad);
        pedidos.add(pedido);
        return pedido;
    }

    // ---------------- Domicilio ----------------
    /** Solicita el servicio a la empresa elegida. Devuelve true si fue aceptado. */
    public boolean solicitarDomicilio(Pedido pedido, EmpresaDomicilio empresa, double km) {
        if (pedido == null || pedido.getModalidad() != ModalidadEntrega.DOMICILIO) {
            return false;
        }
        Domicilio domicilio = new Domicilio(empresa, km);
        boolean aceptado = domicilio.solicitarServicio();
        pedido.setDomicilio(domicilio);
        return aceptado;
    }

    // ---------------- Pedidos ----------------
    /** Verifica cliente registrado, productos del menu y condiciones; luego confirma. */
    public boolean confirmarPedido(Pedido pedido) {
        if (pedido == null || pedido.getCliente() == null
                || buscarCliente(pedido.getCliente().getDocumento()) == null) {
            return false;
        }
        for (DetallePedido d : pedido.getDetalles()) {
            if (d.getProducto() == null || buscarProducto(d.getProducto().getCodigo()) == null) {
                return false;
            }
        }
        if (!pedido.confirmar()) {
            return false;
        }
        pedido.getCliente().agregarPedido(pedido);
        if (!pedidos.contains(pedido)) {
            pedidos.add(pedido);
        }
        return true;
    }

    /** Cambia el estado respetando las reglas: PENDIENTE -> CONFIRMADO -> ENTREGADO, o CANCELADO. */
    public boolean actualizarEstado(Pedido pedido, EstadoPedido nuevoEstado) {
        if (pedido == null || nuevoEstado == null) {
            return false;
        }
        switch (nuevoEstado) {
            case CONFIRMADO:
                return confirmarPedido(pedido);
            case ENTREGADO:
                if (pedido.getEstado() == EstadoPedido.CONFIRMADO) {
                    pedido.marcarEntregado();
                    return true;
                }
                return false;
            case CANCELADO:
                if (pedido.getEstado() == EstadoPedido.PENDIENTE
                        || pedido.getEstado() == EstadoPedido.CONFIRMADO) {
                    pedido.cancelar();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    // ---------------- Consultas ----------------
    public List<Producto> consultarMenu() {
        return new ArrayList<>(menu);
    }

    /** Productos del menu que NO cumplen las condiciones de calidad. */
    public List<Producto> consultarProductosRechazados() {
        List<Producto> rechazados = new ArrayList<>();
        for (Producto p : menu) {
            if (!p.cumpleCondicionesCalidad()) {
                rechazados.add(p);
            }
        }
        return rechazados;
    }

    public List<Pedido> consultarPedidosCliente(String documento) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getCliente() != null && p.getCliente().getDocumento().equalsIgnoreCase(documento)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // ---------------- Reportes ----------------
    /**
     * Cuenta solo pedidos CONFIRMADOS o ENTREGADOS (los cancelados y pendientes no son ingreso).
     * Los valores cobrados por domicilios se separan de los ingresos del restaurante.
     */
    public ReporteVentas generarReporteVentas() {
        double ingresos = 0;
        double domicilios = 0;
        Map<String, Integer> unidades = new LinkedHashMap<>();
        Map<String, Producto> productos = new LinkedHashMap<>();
        Map<String, Double> porCategoria = new LinkedHashMap<>();

        for (Pedido p : pedidos) {
            if (p.getEstado() != EstadoPedido.CONFIRMADO && p.getEstado() != EstadoPedido.ENTREGADO) {
                continue;
            }
            ingresos += p.calcularValorProductos();
            if (p.getModalidad() == ModalidadEntrega.DOMICILIO && p.getDomicilio() != null) {
                domicilios += p.getDomicilio().getCosto();
            }
            for (DetallePedido d : p.getDetalles()) {
                Producto prod = d.getProducto();
                unidades.merge(prod.getCodigo(), d.getCantidad(), Integer::sum);
                productos.putIfAbsent(prod.getCodigo(), prod);
                porCategoria.merge(prod.getClass().getSimpleName(), d.calcularSubtotal(), Double::sum);
            }
        }

        Producto masVendido = null;
        int maxUnidades = 0;
        for (Map.Entry<String, Integer> e : unidades.entrySet()) {
            if (e.getValue() > maxUnidades) {
                maxUnidades = e.getValue();
                masVendido = productos.get(e.getKey());
            }
        }

        String mejorCategoria = null;
        double maxIngreso = -1;
        for (Map.Entry<String, Double> e : porCategoria.entrySet()) {
            if (e.getValue() > maxIngreso) {
                maxIngreso = e.getValue();
                mejorCategoria = e.getKey();
            }
        }
        return new ReporteVentas(ingresos, domicilios, masVendido, maxUnidades, mejorCategoria, porCategoria);
    }
}



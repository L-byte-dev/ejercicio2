package ejercicio2.servicio.pedido;

import ejercicio2.domain.Cliente;
import ejercicio2.domain.Carrito;
import ejercicio2.domain.Pedido;
import ejercicio2.domain.Producto;
import ejercicio2.enums.EstadoPedido;
import ejercicio2.entrada.InputConsoleService;

import java.util.HashMap;

public class PedidoServicioImpl implements PedidoServicio {

    public String MENSAJE_SIN_PEDIDOS_TEMPLATE = "No hay pedidos";
    private Cliente cliente;

    public PedidoServicioImpl(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void actualizarPedidoAPagado(Carrito carrito) {
        carrito.getPedido().setEstado(EstadoPedido.PAGADO);
    }

    @Override
    public Pedido crearPedido(Carrito carrito, Long idPedido) {

        Pedido pedido = new Pedido();

        if (carrito.getCliente().getPedidos().isEmpty()){
            pedido.setId(1L);
        }else {
            pedido.setId((long) carrito.getCliente().getPedidos().size() + 1);
        }

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setCliente(carrito.getCliente());
        pedido.setCarrito(carrito);
        pedido.getCliente().getPedidos().add(pedido);

        return pedido;
    }

    @Override
    public void mostrarPedidos(Cliente cliente, EstadoPedido estadoPedido) {
        if (cliente.getPedidos().isEmpty()) {
            System.out.println(MENSAJE_SIN_PEDIDOS_TEMPLATE);
            return;
        }

        for (Pedido pedido : cliente.getPedidos()) {
            if (estadoPedido == null || pedido.getEstado() == estadoPedido) {
                System.out.println("Pedido ID: " + pedido.getId());
                System.out.println("Estado: " + pedido.getEstado());
                System.out.println("Productos en el pedido:");
                Carrito carrito = pedido.getCarrito();
                HashMap<Producto, Integer> productosEnCarrito = carrito.getProducts();

                for (Producto producto : productosEnCarrito.keySet()) {
                    int cantidad = productosEnCarrito.get(producto);
                    System.out.println("- " + producto.getNombre() + " (Cantidad: " + cantidad + ")");
                }
                System.out.println("-------------------------------------");
            }
        }
    }

    @Override
    public void elegirEstadoPedido() {
        System.out.println("1. Ver todos los pedidos");
        System.out.println("2. Ver pedidos pendientes");
        System.out.println("3. Ver pedidos pagados");
        System.out.println("4. Ver pedidos enviados");
        System.out.println("5. Ver pedidos entregados");
        System.out.println("Ingrese una opción:");
        int option = InputConsoleService.getScanner().nextInt();

        switch (option) {
            case 1:
                mostrarPedidos(cliente, null);
                break;
            case 2:
                mostrarPedidos(cliente, EstadoPedido.PENDIENTE);
                break;
            case 3:
                mostrarPedidos(cliente, EstadoPedido.PAGADO);
                break;
            case 4:
                mostrarPedidos(cliente, EstadoPedido.ENVIADO);
                break;
            case 5:
                mostrarPedidos(cliente, EstadoPedido.ENTREGADO);
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}
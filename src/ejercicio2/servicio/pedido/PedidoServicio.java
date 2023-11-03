package ejercicio2.servicio.pedido;

import ejercicio2.domain.Carrito;
import ejercicio2.domain.Cliente;
import ejercicio2.domain.Pedido;
import ejercicio2.enums.EstadoPedido;

public interface PedidoServicio {
    void actualizarPedidoAPagado(Carrito carrito);

    Pedido crearPedido(Carrito carrito, Long idPedido);

    void mostrarPedidos(Cliente cliente, EstadoPedido estadoPedido);

    void elegirEstadoPedido();
}
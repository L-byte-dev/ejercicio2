package ejercicio2.servicio.pedido;

import ejercicio2.domain.Carrito;
import ejercicio2.domain.Pedido;

public interface PedidoServicio {
    void actualizarPedidoAPagado(Carrito carrito);

    Pedido crearPedido(Carrito carrito, Long idPedido);
}
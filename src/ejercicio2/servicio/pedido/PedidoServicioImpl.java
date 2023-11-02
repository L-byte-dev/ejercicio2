package ejercicio2.servicio.pedido;

import ejercicio2.Main;
import ejercicio2.domain.Carrito;
import ejercicio2.domain.Pedido;
import ejercicio2.enums.EstadoPedido;

import java.util.Objects;

public class PedidoServicioImpl implements PedidoServicio {

    @Override
    public void actualizarPedidoAPagado(Carrito carrito) {
        carrito.getPedido().setEstado(EstadoPedido.PAGADO);

        Carrito newCart = new Carrito(carrito.getId() + 1, Main.getCarritoEnCurso().getCliente());
        Main.getCarritoEnCurso().getCliente().setCarrito(newCart);
        Main.setCarritoEnCurso(newCart);
    }

    @Override
    public Pedido crearPedido(Carrito carrito, Long idPedido) {

        Pedido pedido = new Pedido();

        if (Objects.isNull(idPedido)){
            pedido.setId(1L);
        }else {
            pedido.setId(idPedido + 1);
        }

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setCliente(carrito.getCliente());
        pedido.setCarrito(carrito);

        return pedido;
    }
}
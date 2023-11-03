package ejercicio2.servicio.carrito;

import ejercicio2.basededatos.BdProductos;
import ejercicio2.domain.Carrito;
import ejercicio2.domain.Pedido;
import ejercicio2.domain.Producto;
import ejercicio2.servicio.pedido.PedidoServicio;
import ejercicio2.servicio.stock.StockServicio;

import java.util.Objects;

public class CarritoServicioImpl implements CarritoServicio {

    public static final String MENSAJE_CANTIDAD_MINIMA_TEMPLATE =  "Debe ingresar una cantidad mayor que 0";

    public static final int CANTIDAD_MINIMA = 1;

    public static final String MENSAJE_ALERTA_CANTIDAD_DISPONIBLE_TEMPLATE = "Solo quedan %d productos en stock";

    private StockServicio stockServicio;

    private PedidoServicio pedidoServicio;

    private Carrito carritoEnCurso;

    public CarritoServicioImpl(Carrito carritoEnCurso, StockServicio stockServicio, PedidoServicio pedidoServicio) {
        this.carritoEnCurso = carritoEnCurso;
        this.stockServicio = stockServicio;
        this.pedidoServicio = pedidoServicio;
    }

    @Override
    public void addProduct(Producto prod, int qty) {
        if(qty < CANTIDAD_MINIMA){
            System.out.println(MENSAJE_CANTIDAD_MINIMA_TEMPLATE);
        } else {
            if(prod.getStock() < qty){
                System.out.println(String.format(MENSAJE_ALERTA_CANTIDAD_DISPONIBLE_TEMPLATE,prod.getStock()));
            } else {
                if (carritoEnCurso.getProducts().containsKey(prod)) {
                    int nuevaCantidad = carritoEnCurso.getProducts().get(prod) + qty;
                    if (nuevaCantidad > BdProductos.getProductById(prod.getId()).getStock()) {
                        System.out.println(String.format(MENSAJE_ALERTA_CANTIDAD_DISPONIBLE_TEMPLATE, prod.getStock()));
                    } else {
                        stockServicio.modificarCantidad(prod.getId(), qty);
                        carritoEnCurso.getProducts().put(prod, nuevaCantidad);
                    }
            }
                if (Objects.isNull(carritoEnCurso.getPedido())){
                    Pedido pedido = pedidoServicio.crearPedido(carritoEnCurso, null);
                    carritoEnCurso.setPedido(pedido);
                }

                stockServicio.modificarCantidad(prod.getId(),qty);

                Integer nuevaCantidad = qty + (Objects.isNull(carritoEnCurso.getProducts().get(prod)) ? 0 : carritoEnCurso.getProducts().get(prod));
                if (nuevaCantidad.equals(0)){
                    carritoEnCurso.getProducts().remove(prod);
                } else {
                    carritoEnCurso.getProducts().put(prod, nuevaCantidad);
                }
            }
        }
    }

    @Override
    public boolean cerrarCarrito() {
        if (this.carritoEnCurso.getProducts().isEmpty()){
            return false;
        }else {
            pedidoServicio.actualizarPedidoAPagado(carritoEnCurso);
            carritoEnCurso.getCliente().setCarrito();
            this.carritoEnCurso = this.carritoEnCurso.getCliente().getCarrito();
            return true;
        }
    }
}

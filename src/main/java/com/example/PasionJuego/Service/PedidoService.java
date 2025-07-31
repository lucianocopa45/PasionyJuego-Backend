package com.example.PasionJuego.Service;

import com.example.PasionJuego.DtoRequest.LineaPedidoDtoRequest;
import com.example.PasionJuego.DtoRequest.PedidoDtoRequest;
import com.example.PasionJuego.DtoResponse.PedidoDtoResponse;
import com.example.PasionJuego.DtoResponse.PedidoResumenDtoResponse;
import com.example.PasionJuego.DtoResponse.ProductoResumenDto;
import com.example.PasionJuego.Entity.LineaPedido;
import com.example.PasionJuego.Entity.Pedido;
import com.example.PasionJuego.Entity.Producto;
import com.example.PasionJuego.Entity.Usuario;
import com.example.PasionJuego.Excepciones.ProductoNotFoundException;
import com.example.PasionJuego.Excepciones.UsuarioNotFoundException;
import com.example.PasionJuego.Repository.PedidoRepository;
import com.example.PasionJuego.Repository.ProductoRepository;
import com.example.PasionJuego.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public List<PedidoDtoResponse> getByIdPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
                ()-> new UsuarioNotFoundException("Pedido no encontrado con ID: " + idPedido));

        if (pedido.getUsuario() == null) {
            throw new RuntimeException("El pedido no tiene un usuario asignado");
        }

        List<PedidoDtoResponse> response = new ArrayList<>();

        for (LineaPedido linea: pedido.getLineas()){
            PedidoDtoResponse dto = new PedidoDtoResponse();

            dto.setIdPedidos(pedido.getId_pedidos());
            dto.setIdUsuario(pedido.getUsuario().getId_usuario());
            dto.setCreatedAt(pedido.getCreatedAt());
            dto.setUpdateAt(pedido.getUpdatedAt());
            dto.setEstado(pedido.getEstado());
            dto.setMetodoPago(pedido.getMetodoPago());
            dto.setTotal(pedido.getTotal());

            response.add(dto);
        }
        return response;
    }
    public PedidoDtoResponse crearPedido(PedidoDtoRequest dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(
                () -> new UsuarioNotFoundException("El usuario con el ID: " + dto.getIdUsuario() + " no se ha encontrado"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .lineas(new ArrayList<>())
                .estado(dto.getEstado())
                .metodoPago(dto.getMetodoPago())
                .build();

        double totalPrecio = 0.0;

        for (LineaPedidoDtoRequest lineaDto : dto.getLineas()) {
            Producto producto = productoRepository.findById(lineaDto.getProductoId()).orElseThrow(
                    () -> new ProductoNotFoundException("Producto con el ID: " + lineaDto.getProductoId() + " no encontrado"));

            if (lineaDto.getCantidad() > producto.getStock()) {
                throw new RuntimeException("No hay suficiente stock para producto:" + producto.getDescripcion());
            }
            double subTotal = producto.getPrecio() * lineaDto.getCantidad();
            totalPrecio += subTotal;

            LineaPedido linea = LineaPedido.builder()
                    .producto(producto)
                    .cantidad(lineaDto.getCantidad())
                    .pedido(pedido)
                    .build();

            pedido.getLineas().add(linea);
            producto.setStock(producto.getStock() - lineaDto.getCantidad());
        }
        pedido.setTotal(totalPrecio);
        pedidoRepository.save(pedido);

        PedidoDtoResponse response = new PedidoDtoResponse();
        response.setIdPedidos(pedido.getId_pedidos());
        response.setIdUsuario(usuario.getId_usuario());
        response.setCreatedAt(pedido.getCreatedAt());
        response.setUpdateAt(pedido.getUpdatedAt());
        response.setEstado(pedido.getEstado());
        response.setMetodoPago(pedido.getMetodoPago());
        response.setTotal(totalPrecio);

        return response;
    }
    public List<PedidoResumenDtoResponse> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll(); // Traés todos los pedidos de la BD
        return pedidos.stream()
                .map(pedido -> {
                    PedidoResumenDtoResponse dto = new PedidoResumenDtoResponse();
                    dto.setIdPedidos(pedido.getId_pedidos());
                    dto.setIdUsuario(pedido.getUsuario().getId_usuario());
                    dto.setNombreUsuario(pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
                    dto.setFechaCreacion(pedido.getCreatedAt());
                    dto.setEstado(pedido.getEstado());
                    dto.setTotal(pedido.getTotal());

                    List<ProductoResumenDto> productos = pedido.getLineas().stream()
                            .map(linea -> {
                                ProductoResumenDto prodDto = new ProductoResumenDto();
                                prodDto.setNombreProducto(linea.getProducto().getDescripcion());
                                prodDto.setCantidad(linea.getCantidad());
                                return prodDto;
                            })
                            .collect(Collectors.toList());

                    dto.setProductos(productos);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public Pedido deleteProducto(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
                ()-> new UsuarioNotFoundException("Pedido no encontrado con ID: " + idPedido));
        pedidoRepository.delete(pedido);
        return pedido;
    }

    }

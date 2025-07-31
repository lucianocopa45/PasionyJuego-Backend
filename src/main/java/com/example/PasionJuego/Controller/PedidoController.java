package com.example.PasionJuego.Controller;

import com.example.PasionJuego.DtoRequest.PedidoDtoRequest;
import com.example.PasionJuego.DtoResponse.PedidoDtoResponse;
import com.example.PasionJuego.DtoResponse.PedidoResumenDtoResponse;
import com.example.PasionJuego.Entity.Pedido;
import com.example.PasionJuego.Repository.PedidoRepository;
import com.example.PasionJuego.Service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos")
@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    PedidoRepository pedidoRepository;

    @Operation(summary = "Obtener pedido por ID", description = "Devuelve el pedido con el ID especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping(path = "/{idPedido}")
    public ResponseEntity<List<PedidoDtoResponse>> getByIdPedido(@PathVariable Long idPedido){
        return ResponseEntity.ok(pedidoService.getByIdPedido(idPedido));
    }
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados")
    @GetMapping(path = "getAllPedidos")
    public ResponseEntity<List<PedidoResumenDtoResponse>> getAllPedidos(){
        List<PedidoResumenDtoResponse> response = pedidoService.getAllPedidos();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con los productos indicados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(path = "/crearPedidoLista")
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoDtoRequest dto){
        System.out.println("DTO recibido: " + dto);
        System.out.println("ID usuario recibido: " + dto.getIdUsuario());
        if (dto.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID usuario es null");
        }
        PedidoDtoResponse response = pedidoService.crearPedido(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar productos del pedido", description = "Elimina todos los productos asociados a un pedido dado")
    @DeleteMapping("deleteProducto/{idPedido}")
    public Pedido deleteProducto(@PathVariable Long idPedido){
        return pedidoService.deleteProducto(idPedido);
    }
}

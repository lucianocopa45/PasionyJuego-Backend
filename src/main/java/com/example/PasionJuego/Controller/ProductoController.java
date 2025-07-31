package com.example.PasionJuego.Controller;

import com.example.PasionJuego.DtoRequest.ProductoDtoRequest;
import com.example.PasionJuego.DtoResponse.ProductoDtoResponse;
import com.example.PasionJuego.Service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Productos", description = "Operaciones relacionadas con la gestión de Productos")
@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponse(responseCode = "200", description = "Producto creado correctamente")
    @PostMapping
    public ResponseEntity<ProductoDtoResponse> crearProducto(@Valid @RequestBody ProductoDtoRequest dto) {
        System.out.println(dto); // para ver si llegan los datos
        ProductoDtoResponse response = productoService.createProducto(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener todos los productos", description = "Lista todos los productos disponibles")
    @GetMapping(path = "/productosAll")
    public ResponseEntity<List<ProductoDtoResponse>> getAllProductos() {
        List<ProductoDtoResponse> response = productoService.getAllProductos();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    @PutMapping (path = "/{idProducto}")
    public ResponseEntity<ProductoDtoResponse> updateProducto(@PathVariable Long idProducto, @Valid @RequestBody ProductoDtoRequest dto){
        //System.out.println("ID de categoría recibido: " + dto.getCategoriaId());
        System.out.println("DTO recibido: " + dto);
        ProductoDtoResponse response = productoService.updateProducto(idProducto, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/deleteProducto/{idProducto}")
    @Operation(summary = "Eliminar producto", description = "Eliminar un producto")
    public ResponseEntity<String> deleteProducto(@PathVariable Long idProducto){
        ProductoDtoResponse response = productoService.deleteProducto(idProducto);
        return ResponseEntity.ok("Se elimino producto con el ID: "+ idProducto);
    }
}

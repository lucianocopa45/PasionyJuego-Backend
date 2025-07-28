package com.example.PasionJuego.Repository;

import com.example.PasionJuego.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

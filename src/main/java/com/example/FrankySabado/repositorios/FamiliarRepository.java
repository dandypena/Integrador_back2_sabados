package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Integer> {

    // Buscar familiar por ID de usuario
    Optional<Familiar> findByUsuarioId(Integer usuarioId);

    // Verificar si un familiar está vinculado a un estudiante específico
    boolean existsByUsuarioIdAndEstudianteVinculadoId(Integer usuarioId, Integer estudianteId);
}


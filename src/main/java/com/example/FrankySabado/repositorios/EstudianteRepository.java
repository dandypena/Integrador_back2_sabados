package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    // Trae los estudiantes del grupo indicado, ordenados de mayor a menor promedio
    @Query("SELECT e FROM Estudiante e WHERE e.grupo.id = :grupoId ORDER BY e.promedio DESC")
    List<Estudiante> findRankingByGrupo(Long grupoId);
    
    // Buscar estudiante por usuario
    Optional<Estudiante> findByUsuario(Usuario usuario);
}


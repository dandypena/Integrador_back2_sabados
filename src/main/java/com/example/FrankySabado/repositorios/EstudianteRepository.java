package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    // Trae los estudiantes del grupo indicado, ordenados de mayor a menor promedio
    @Query("SELECT e FROM Estudiante e WHERE e.grupo.id = :grupoId ORDER BY e.promedio DESC")
    List<Estudiante> findRankingByGrupo(Long grupoId);
}


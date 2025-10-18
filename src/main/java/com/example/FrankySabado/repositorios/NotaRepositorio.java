package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.FrankySabado.ayudas.TipoEvaluacion;

import java.util.List;

@Repository
public interface NotaRepositorio extends JpaRepository<Nota, Integer> {

    // 🔹 HU08 - Listar notas por estudiante
    List<Nota> findByEstudianteId(Integer estudianteId);

    // 🔹 Para HU11 – Notas de bajo rendimiento
    @Query("""
        SELECT n.estudiante.usuario.nombre, n.tipo, AVG(n.valor)
        FROM Nota n
        GROUP BY n.estudiante.usuario.nombre, n.tipo
        HAVING AVG(n.valor) < 3.0
    """)
    List<Object[]> findRendimientoBajo();

    // 🔹 Para HU12 – Consolidar notas por tipo
    @Query("""
        SELECT n.tipo, AVG(n.valor)
        FROM Nota n
        WHERE n.estudiante.id = :idEstudiante
        GROUP BY n.tipo
    """)
    List<Object[]> obtenerConsolidadoPorTipo(Integer idEstudiante);
    long countByEstudianteIdAndTipo(Integer idEstudiante, TipoEvaluacion tipo);

    // 🔹 HU10 - Obtener notas por materia y grupo
    @Query("""
        SELECT n
        FROM Nota n
        JOIN n.estudiante e
        JOIN e.grupo g
        WHERE g.materiaId = :materiaId
        AND g.id = :grupoId
        ORDER BY e.usuario.nombre ASC
    """)
    List<Nota> findByMateriaIdAndGrupoId(Long materiaId, Long grupoId);

    // 🔹 HU18 - Obtener notas ordenadas por fecha para análisis de evolución
    @Query("""
        SELECT n
        FROM Nota n
        WHERE n.estudiante.id = :estudianteId
        ORDER BY n.fecha ASC
    """)
    List<Nota> findByEstudianteIdOrderByFechaAsc(Integer estudianteId);
}

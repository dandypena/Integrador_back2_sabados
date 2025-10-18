package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.FrankySabado.ayudas.TipoEvaluacion;

import java.util.List;

@Repository
public interface NotaRepositorio extends JpaRepository<Nota, Integer> {



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
}



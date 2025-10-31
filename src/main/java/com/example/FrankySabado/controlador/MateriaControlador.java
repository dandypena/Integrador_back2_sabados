package com.example.FrankySabado.controlador;

import com.example.FrankySabado.modelos.Materia;
import com.example.FrankySabado.repositorios.MateriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
@CrossOrigin(origins = "*")
public class MateriaControlador {

    private final MateriaRepository materiaRepository;

    public MateriaControlador(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    /**
     * GET /materias - Listar todas las materias
     */
    @GetMapping
    public ResponseEntity<List<Materia>> listarMaterias() {
        try {
            List<Materia> materias = materiaRepository.findAll();
            return ResponseEntity.ok(materias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * GET /materias/{id} - Obtener una materia por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerMateria(@PathVariable Integer id) {
        try {
            return materiaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

package com.example.FrankySabado.servicios;

import com.example.FrankySabado.modelos.Grupo;
import com.example.FrankySabado.modelos.Materia;
import com.example.FrankySabado.repositorios.GrupoRepository;
import com.example.FrankySabado.repositorios.MateriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para inicializar las materias y grupos en la base de datos
 */
@Service
public class MateriaInicializadorServicio {

    private final MateriaRepository materiaRepository;
    private final GrupoRepository grupoRepository;

    public MateriaInicializadorServicio(MateriaRepository materiaRepository, GrupoRepository grupoRepository) {
        this.materiaRepository = materiaRepository;
        this.grupoRepository = grupoRepository;
    }

    /**
     * Inicializa grupos y materias predefinidas al iniciar la aplicación
     * Solo se ejecuta si no existen en la base de datos
     */
    @PostConstruct
    @Transactional
    public void inicializarMaterias() {
        // Inicializar grupos primero
        long gruposCount = grupoRepository.count();
        if (gruposCount == 0) {
            System.out.println("🔄 Inicializando grupos predefinidos...");
            crearGrupo("Grupo A", "Grupo de estudio A - Horario mañana");
            crearGrupo("Grupo B", "Grupo de estudio B - Horario tarde");
            crearGrupo("Grupo C", "Grupo de estudio C - Horario noche");
            crearGrupo("Grupo D", "Grupo de estudio D - Fines de semana");
            crearGrupo("Grupo E", "Grupo de estudio E - Virtual");
            System.out.println("✅ 5 grupos creados exitosamente");
        }
        
        // Inicializar materias
        long materiasCount = materiaRepository.count();
        if (materiasCount == 0) {
            System.out.println("🔄 Inicializando materias predefinidas...");
            
            // Crear las 7 materias
            crearMateria("Backend 1", "BACK001", 4, "Desarrollo backend con Spring Boot - Nivel básico");
            crearMateria("Backend 2", "BACK002", 4, "Desarrollo backend avanzado con microservicios");
            crearMateria("Frontend 1", "FRONT001", 4, "Desarrollo frontend con React - Nivel básico");
            crearMateria("Frontend 2", "FRONT002", 4, "Desarrollo frontend avanzado con React y Redux");
            crearMateria("Bases de Datos", "BD001", 4, "Diseño y gestión de bases de datos relacionales");
            crearMateria("Metodologías Ágiles", "AGIL001", 3, "Scrum, Kanban y metodologías ágiles de desarrollo");
            crearMateria("Python", "PY001", 4, "Programación en Python para análisis de datos");
            
            System.out.println("✅ 7 materias creadas exitosamente en la base de datos");
        } else {
            System.out.println("✓ Datos ya inicializados (" + gruposCount + " grupos, " + materiasCount + " materias)");
        }
    }

    /**
     * Crear un grupo y guardarlo en la base de datos
     */
    private void crearGrupo(String nombre, String descripcion) {
        Grupo grupo = new Grupo();
        grupo.setNombre(nombre);
        grupo.setDescripcion(descripcion);
        
        grupoRepository.save(grupo);
        System.out.println("  → " + nombre + " creado");
    }
    
    /**
     * Crear una materia y guardarla en la base de datos
     */
    private void crearMateria(String nombre, String codigo, Integer creditos, String descripcion) {
        Materia materia = new Materia();
        materia.setNombre(nombre);
        materia.setCodigo(codigo);
        materia.setCreditos(creditos);
        materia.setDescripcion(descripcion);
        
        materiaRepository.save(materia);
        System.out.println("  → " + nombre + " (" + codigo + ") creada");
    }
}

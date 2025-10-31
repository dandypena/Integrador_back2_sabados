package com.example.FrankySabado.servicios;

import com.example.FrankySabado.ayudas.Estados;
import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.CrearEstudianteDTO;
import com.example.FrankySabado.dtos.CrearDocenteDTO;
import com.example.FrankySabado.dtos.LoginDTO;
import com.example.FrankySabado.modelos.Docente;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Grupo;
import com.example.FrankySabado.modelos.Materia;
import com.example.FrankySabado.modelos.Usuario;
import com.example.FrankySabado.repositorios.DocenteRepository;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import com.example.FrankySabado.repositorios.MateriaRepository;
import com.example.FrankySabado.repositorios.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;
    private final DocenteRepository docenteRepository;
    private final MateriaRepository materiaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioServicio(UsuarioRepository usuarioRepository, EstudianteRepository estudianteRepository, DocenteRepository docenteRepository, MateriaRepository materiaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
        this.docenteRepository = docenteRepository;
        this.materiaRepository = materiaRepository;
    }

    /**
     * Autenticar usuario con correo y contraseña
     * @param loginDTO Datos de login
     * @return Usuario autenticado
     * @throws RuntimeException si las credenciales son inválidas
     */
    public Usuario autenticar(LoginDTO loginDTO) {
        // Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        
        // Verificar contraseña
        if (!usuario.getContraseña().equals(loginDTO.getContraseña())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        // Verificar que el usuario esté activo
        if (usuario.getEstado() != Estados.Activo) {
            throw new RuntimeException("Usuario inactivo. Contacte al administrador.");
        }
        
        return usuario;
    }

    /**
     * Buscar usuario por correo
     * @param correo Correo del usuario
     * @return Usuario encontrado
     */
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public Estudiante crearEstudiante(CrearEstudianteDTO dto) {
        // Validar que el correo no exista
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContraseña(dto.getContraseña());
        usuario.setRol(Roles.Estudiante);
        usuario.setEstado(Estados.Activo);

        usuario = usuarioRepository.save(usuario);

        // Crear Estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setUsuario(usuario);
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setPromedio(0.0); // Promedio inicial en 0

        // Si se proporciona grupoId, asignarlo
        if (dto.getGrupoId() != null) {
            Grupo grupo = entityManager.getReference(Grupo.class, dto.getGrupoId());
            estudiante.setGrupo(grupo);
        }

        return estudianteRepository.save(estudiante);
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public Docente crearDocente(CrearDocenteDTO dto) {
        // Validar que el correo no exista
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContraseña(dto.getContraseña());
        usuario.setRol(Roles.Docente);
        usuario.setEstado(Estados.Activo);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Crear Docente
        Docente docente = new Docente();
        docente.setUsuario(usuarioGuardado);
        docente.setEspecialidad(dto.getEspecialidad());
        docente.setNivelAcademico(dto.getNivelAcademico());
        docente.setDepartamento(dto.getDepartamento());

        // Asignar materia principal si se proporciona
        if (dto.getMateriaId() != null) {
            Materia materia = materiaRepository.findById(dto.getMateriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada con ID: " + dto.getMateriaId()));
            docente.setMateriaPrincipal(materia);
        }

        return docenteRepository.save(docente);
    }
}

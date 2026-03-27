# 🎓 Sistema de Asignación de Materias para Docentes

## 📋 Resumen de Implementación

Se ha implementado un sistema donde **cada docente tiene asignada una materia específica** al momento del registro. Esta materia se usa **automáticamente** al crear notas, eliminando la necesidad de seleccionarla manualmente cada vez.

---

## ✅ Cambios Implementados

### 🔧 **Backend (Spring Boot)**

#### 1. **Modelo `Docente.java`**
```java
// Nueva relación ManyToOne con Materia
@ManyToOne
@JoinColumn(name = "fk_materia_principal", referencedColumnName = "id")
private Materia materiaPrincipal;
```

#### 2. **DTO `CrearDocenteDTO.java`**
```java
// Nuevo campo para recibir la materia en el registro
private Long materiaId;
```

#### 3. **DTO `LoginResponseDTO.java`**
```java
// Nuevos campos para devolver información de la materia al hacer login
private Long materiaId;
private String materiaNombre;
```

#### 4. **Servicio `UsuarioServicio.java`**
```java
// Asignar materia principal al crear docente
if (dto.getMateriaId() != null) {
    Materia materia = materiaRepository.findById(dto.getMateriaId())
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
    docente.setMateriaPrincipal(materia);
}
```

#### 5. **Controlador `AuthControlador.java`**
```java
// Incluir información de materia en respuesta de login
if (usuario.getRol() == Roles.Docente) {
    Docente docente = docenteRepository.findByUsuario(usuario).orElse(null);
    if (docente != null && docente.getMateriaPrincipal() != null) {
        response.setMateriaId(docente.getMateriaPrincipal().getId());
        response.setMateriaNombre(docente.getMateriaPrincipal().getNombre());
    }
}
```

---

### 🎨 **Frontend (React)**

#### 1. **Registro de Docentes (`RegistroUsuarios.jsx`)**
```jsx
// Nuevo campo en formulario de registro
<select {...register('materiaId', {
  required: 'Debes seleccionar la materia que impartirás'
})}>
  <option value="">Seleccionar materia</option>
  {materias.map(materia => (
    <option key={materia.id} value={materia.id}>
      {materia.nombre} ({materia.codigo})
    </option>
  ))}
</select>
```

#### 2. **Autenticación (`authServiceBackend.js`)**
```javascript
// Guardar información de materia del docente en la sesión
const user = {
  // ... otros campos
  materiaId: userData.materiaId || null,
  materiaNombre: userData.materiaNombre || null
};
```

#### 3. **Gestión de Notas (`NotasBackend.jsx`)**
```jsx
// Usar automáticamente la materia del docente
const materiaNombre = usuario.materiaNombre || data.nombreMateria?.trim() || 'Sin materia';

// Mostrar materia asignada en lugar de campo de entrada
{usuario.materiaNombre ? (
  <div className="alert alert-info">
    <strong>{usuario.materiaNombre}</strong>
    <p>Esta es tu materia asignada. Las notas se registrarán automáticamente con esta materia.</p>
  </div>
) : (
  // Campo manual solo si no tiene materia asignada
  <input type="text" {...register('nombreMateria')} />
)}
```

---

## 🗄️ **Migración de Base de Datos**

```sql
-- Agregar columna para materia principal
ALTER TABLE docente 
ADD COLUMN fk_materia_principal BIGINT NULL;

-- Agregar foreign key
ALTER TABLE docente
ADD CONSTRAINT fk_docente_materia_principal
FOREIGN KEY (fk_materia_principal) REFERENCES materias(id)
ON DELETE SET NULL;
```

**Archivo:** `migracion_materia_docente.sql`

---

## 🚀 **Flujo de Uso**

### **Para Nuevos Docentes:**

1. **Registro:**
   - El docente completa el formulario de registro
   - **Selecciona la materia que impartirá** del dropdown (obligatorio)
   - El sistema asocia esa materia a su perfil

2. **Login:**
   - El docente inicia sesión
   - El backend devuelve su información **incluyendo su materia asignada**
   - El frontend guarda esta información en la sesión

3. **Crear Notas:**
   - Al abrir el formulario de "Nueva Nota", ve su materia asignada en un **banner informativo**
   - **No necesita escribir ni seleccionar la materia**
   - La nota se registra automáticamente con su materia asignada

### **Para Docentes Existentes (sin materia):**

- Si un docente no tiene materia asignada, el formulario mostrará el **campo manual** para escribir la materia
- Se recomienda actualizar su perfil para asignarle una materia

---

## ✨ **Beneficios**

### **1. Eficiencia:**
- ⚡ **Menos clicks:** No hay que seleccionar la materia en cada nota
- 🎯 **Más rápido:** El proceso de registro de notas es instantáneo
- 🔄 **Automatización:** La materia se asigna automáticamente

### **2. Consistencia:**
- ✅ **Sin errores:** Todas las notas del docente tienen la misma materia
- 📊 **Datos limpios:** No hay variaciones de nombres (backend 1, Backend 1, BACKEND 1)
- 🎓 **Profesional:** Sistema más robusto y confiable

### **3. Validación:**
- 🛡️ **Control:** Solo se pueden asignar materias existentes en el sistema
- 🔒 **Seguridad:** La relación materia-docente está en la base de datos
- 📋 **Auditoría:** Se puede rastrear qué docente imparte cada materia

---

## 📊 **Datos de Prueba**

### **Materias Disponibles (7):**
1. Backend 1 (BACK001)
2. Backend 2 (BACK002)
3. Frontend 1 (FRONT001)
4. Frontend 2 (FRONT002)
5. Bases de Datos (BD001)
6. Metodologías Ágiles (AGIL001)
7. Python (PY001)

### **Usuario de Prueba:**
```javascript
// Crear un docente de prueba con materia asignada
{
  nombre: "Profesor Backend",
  correo: "profesor.backend@test.com",
  contraseña: "123456",
  especialidad: "DESARROLLO_BACKEND",
  nivelAcademico: "MAGISTER",
  departamento: "Antioquia",
  materiaId: 1 // Backend 1
}
```

---

## 🔧 **Instrucciones de Despliegue**

### **1. Ejecutar Migración SQL:**
```bash
mysql -u root -p bd_sabado < migracion_materia_docente.sql
```

### **2. Compilar Backend:**
```bash
cd c:\Desarrollo\Integrador_back2_sabados
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

### **3. Iniciar Frontend:**
```bash
cd c:\Desarrollo\learning\Dashboard-Integrador
npm run dev
```

### **4. Probar el Sistema:**
1. Ir a `http://localhost:5173/registro`
2. Registrar un nuevo docente seleccionando una materia
3. Iniciar sesión con ese docente
4. Ir a "Ver Notas" → "Nueva Nota"
5. Verificar que aparece la materia asignada automáticamente

---

## 📝 **Notas Técnicas**

### **Relaciones de Base de Datos:**
- `docente.fk_materia_principal` → `materias.id` (ManyToOne)
- `ON DELETE SET NULL`: Si se elimina una materia, el docente queda sin materia asignada (puede asignarle otra)

### **Validaciones:**
- El campo `materiaId` es **obligatorio** en el registro de docente
- Si la materia no existe, lanza `IllegalArgumentException`
- Las materias deben estar previamente cargadas en `datos_iniciales.sql`

### **Compatibilidad:**
- ✅ Docentes nuevos: Tendrán materia asignada obligatoriamente
- ✅ Docentes existentes: Pueden seguir usando el campo manual
- ✅ Notas antiguas: Siguen funcionando con `nombreMateria` de texto

---

## 🎯 **Próximos Pasos (Opcional)**

1. **Panel de Administración:**
   - Permitir que admin cambie la materia asignada a un docente

2. **Múltiples Materias:**
   - Permitir que un docente imparta varias materias
   - Selector en formulario de notas si tiene más de una

3. **Reportes:**
   - Listado de docentes por materia
   - Estadísticas de notas por materia

4. **Migración de Docentes Existentes:**
   - Script para asignar materias a docentes ya registrados

---

## 📞 **Soporte**

Si encuentras algún problema:
1. Verifica que la migración SQL se ejecutó correctamente
2. Asegúrate de que el backend esté corriendo
3. Revisa la consola del navegador para errores
4. Verifica que las 7 materias estén en la base de datos

---

**Implementado con ❤️ por el equipo de desarrollo**

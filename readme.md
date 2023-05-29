# Práctica 8/9 - Ubrileaks
## Integrantes del grupo
### - Adrián Merino Gamaza --> Arquitecto de Software - QA
### - Daniel Alfonso Rodríguez Santos --> Analista - QA

## Información del proyecto
- Aplicación de registro y gestión de intervenciones de alumnos en ambiente académico
- Java (IntelliJ IDEA) + BD MariaDB (MySQL Workbench)
- Creación/Borrado BBDD/Tablas
- Funciones de información sobre intervenciones
- Función selección alumno
- Función muestra información alumno
- Función reseteo intervenciones
- Funciones de alta/baja/modificación alumnos
- Importación/Exportación XML/CSV

## Desarrollo del proyecto
- Funciones de creación/borrado BBDD - Completado
- Funcionalidades de muestra de información - Completado
- Funcionalidades de gestión de información - Completado
- Funciones de importación/exportación de datos - Completado
- Interfaz implementada con AWT - Completado
- Uso de PostgreSQL (opcional) - Pendiente implementación
- Presentación del proyecto - Completado

## Uso de la aplicación
Menú Principal:
- Crear/Borrar Base de Datos
  - Crear BD --> Introducir nombre BD
  - Crear Tabla --> Introducir nombre BD y nombre tabla
  - Borrar BD --> Introducir nombre BD
  - Borrar Tabla --> Introducir nombre BD y nombre tabla
  
- Operaciones Base de Datos
  - Alumno con más participaciones --> Introducir nombre BD y nombre tabla
  - Alumno con menos participaciones --> Introducir nombre BD y nombre tabla
  - Alumnos por debajo de la media --> Introducir nombre BD y nombre tabla
  - Alumnos por cantidad de intervenciones --> Introducir nombre BD, nombre tabla y número de intervenciones
  - Último alumno en participar --> Introducir nombre BD y nombre tabla
  - Seleccionar alumno aleatorio --> Introducir nombre BD y nombre tabla
  - Mostrar información de un alumno -->  Introducir nombre BD, nombre tabla y nombre del alumno
  - Resetear intervenciones --> Introducir nombre BD y nombre tabla
  - Dar de alta --> Introducir nombre BD, nombre tabla y nombre del alumno
  - Dar de baja --> Introducir nombre BD, nombre tabla y nombre del alumno
  - Modificar alumno --> Introducir nombre BD, nombre tabla, nombre del alumno, número de intervenciones, última intervención y nuevo nombre
  
- Importar/Exportar Datos
  - Importar XML --> Introducir nombre BD, nombre tabla y ruta del archivo XML a importar
  - Exportar XML --> Introducir nombre BD, nombre tabla y ruta del archivo XML a exportar
  - Importar CSV --> Introducir nombre BD, nombre tabla y ruta del archivo CSV a importar
  - Exportar CSV --> Introducir nombre BD, nombre tabla y ruta del archivo CSV a exportar

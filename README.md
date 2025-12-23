# Sistema de Gestión de Aerolínea ✈️

## Descripción
Proyecto académico desarrollado en Java como parte de la Licenciatura en Sistemas.
El sistema modela la gestión integral de una aerolínea, permitiendo registrar clientes,
aeropuertos y vuelos, así como la venta, cancelación y reprogramación de pasajes.

El diseño está orientado a objetos, utilizando interfaces, herencia y estructuras de datos
eficientes para resolver problemas reales del dominio.

---

## Tecnologías utilizadas
- Lenguaje: **Java**
- Paradigma: **Programación Orientada a Objetos**
- Colecciones: `List`, `Map`
- Entorno de desarrollo: Eclipse / Visual Studio Code

---

## Conceptos aplicados

### Programación Orientada a Objetos (POO)
- Definición de contratos mediante **interfaces**
- Uso de **clases, herencia y encapsulamiento**
- Separación de responsabilidades
- Polimorfismo en la gestión de distintos tipos de vuelos

### Algoritmos y Estructuras de Datos
- Uso de estructuras de datos eficientes (`Map`, `List`)
- Análisis de complejidad temporal mediante **notación Big-O**
- Operaciones críticas resueltas en **O(1)**, como:
  - Cancelación de pasajes
  - Cálculo del total recaudado por destino

---

## Funcionalidades principales
- Registro de clientes y aeropuertos
- Alta de vuelos:
  - Públicos nacionales
  - Públicos internacionales (con o sin escalas)
  - Vuelos privados
- Venta y cancelación de pasajes
- Consulta de asientos disponibles
- Reprogramación automática de pasajes ante cancelación de vuelos
- Consulta de vuelos similares por fecha y destino
- Detalle completo de un vuelo
- Cálculo del total recaudado por destino en tiempo constante

---

## Diseño del sistema
La interfaz `IAerolinea` define las operaciones principales del sistema, actuando como
contrato para las distintas implementaciones. Esto permite un diseño desacoplado,
extensible y fácil de mantener.

El sistema contempla validaciones de negocio y el uso de excepciones ante datos inválidos.

---

## Contexto académico
Proyecto desarrollado como trabajo práctico para la carrera **Licenciatura en Sistemas**,
con foco en:
- Modelado de sistemas complejos
- Diseño orientado a objetos
- Eficiencia algorítmica
- Buenas prácticas de programación

---

## Cómo ejecutar
1. Clonar el repositorio
2. Importar el proyecto en Eclipse o abrirlo en Visual Studio Code
3. Ejecutar la clase principal (`Main` o clase de prueba correspondiente)

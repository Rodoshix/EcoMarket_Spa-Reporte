# EcoMarket SPA - Microservicio Reporte-Service

Este microservicio se encarga de generar reportes relacionados con las ventas registradas en EcoMarket SPA. Incluye integración con `venta-service` y persistencia de logs para auditoría.

---

## Tecnologías

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Lombok
- MySQL
- RestTemplate

---

## Funcionalidades

- Generar reporte general de ventas por rango de fechas
- Generar reporte de ventas por cliente (filtrable por fecha)
- Generar reporte de ventas por producto (filtrable por fecha)
- Calcular totales de ventas, productos vendidos y recaudación
- Registrar logs de reportes generados en base de datos

---

## Configuración del entorno

### Base de datos

- Motor: MySQL (MariaDB compatible)
- Nombre: `reporte_db`
- Usuario: `root`
- Contraseña: *(vacía por defecto en XAMPP)*

### Archivo `application.properties`

```properties
spring.application.name=reporte-service

spring.datasource.url=jdbc:mysql://localhost:3306/reporte_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8086
```

---


## Microservicios integrados

- **venta-service** (`localhost:8084`): fuente de datos de ventas

---

## Endpoints disponibles

Importa la colección incluida:  
**`EcoMarket - ReporteService.postman_collection.json`**

| Método | Ruta                                                                             | Descripción                                                                      |
|--------|----------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| GET    | `/api/reportes/ventas?desde=YYYY-MM-DD&hasta=YYYY-MM-DD`                         | Reporte de ventas por rango de fechas.                                           |
| GET    | `/api/reportes/ventas/usuario/{email}?desde=YYYY-MM-DD&hasta=YYYY-MM-DD`         | Calcula las ventas hechas por un cliente específico dentro del rango.            |
| GET    | `/api/reportes/ventas/producto/{idProducto}?desde=YYYY-MM-DD&hasta=YYYY-MM-DD`   | Calcula cuántas veces se vendió un producto, cuántas unidades y cuánto ingresó.  |

---

## 📝 Persistencia de logs

Cada vez que se genera un reporte, se guarda un log automático en la base de datos:

| Campo             | Descripción                      |
|-------------------|----------------------------------|
| tipoReporte       | VENTAS, USUARIO, PRODUCTO        |
| parametros        | Rango de fechas u otros datos    |
| fechaGeneracion   | Cuándo fue solicitado el reporte |

---

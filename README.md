# BFF - Cuidado Seguro

## Descripción

El Backend For Frontend (BFF) de **Cuidado Seguro** actúa como una capa intermedia entre el frontend y los microservicios del sistema.

Su principal función es centralizar las solicitudes realizadas por los clientes, aplicar reglas de seguridad, validar autenticación mediante JWT y enrutar correctamente las peticiones hacia los distintos microservicios de la arquitectura.

El punto de entrada publico del sistema es **AWS API Gateway** (servicio administrado). El BFF es un servicio interno: solo recibe trafico desde AWS API Gateway y es el unico componente que conoce las URLs de los microservicios.

```text
Frontend React
      |
      v  HTTPS
AWS API Gateway
      |
      v  HTTP/HTTPS (VPC Link -> ALB interno)
     BFF
      |
      +---> MS Pacientes
      +---> MS Datos Medicos
      +---> MS Auth
```

El API Gateway propio (`api.gateway.cuidadoseguro`) fue retirado del flujo: el BFF ya no lo utiliza.

---

# Tecnologías Utilizadas

## Lenguaje y Framework

* **Java 17**
* **Spring Boot 3.2.5**
* **Spring Security**
* **Spring WebFlux**

## Seguridad

* **JWT (JSON Web Token)**
* **Spring Security**
* **JJWT 0.12.5**

## Documentación

* **Swagger OpenAPI**
* **Springdoc OpenAPI WebFlux**

## DevOps y Arquitectura

* **Docker**
* **Arquitectura de Microservicios**
* **Backend For Frontend (BFF)**
* **AWS API Gateway** (entrada publica, servicio administrado)

---

# Funcionalidad del BFF

El BFF cumple las siguientes funciones dentro del sistema:

* Centralizar solicitudes del frontend
* Validar autenticación JWT
* Gestionar seguridad de rutas
* Redirigir tráfico hacia microservicios
* Reducir complejidad en el frontend
* Actuar como punto único de acceso

---

# Arquitectura del Proyecto

El proyecto se organiza mediante una arquitectura basada en componentes y configuración centralizada.

## Componentes principales

### Security

Encargado de validar tokens JWT y proteger endpoints.

### Service

Gestiona el enrutamiento de solicitudes hacia los microservicios internos.

### Filters

Permite interceptar solicitudes HTTP antes de llegar al backend.

### Config

Contiene configuraciones globales del sistema.

---

# Estructura del Proyecto

```bash
com.cuidadoseguro.bff
│
├── config
│
├── security
│
├── filter
│
├── controller
│
└── BffCuidadoseguroApplication.java
```

---

# Dependencias Principales

| Dependencia          | Descripción                    |
| -------------------- | ------------------------------ |
| Spring WebFlux       | Enrutamiento reactivo          |
| Spring Security      | Seguridad y autenticación      |
| JWT                  | Validación de tokens           |
| Spring WebFlux       | Programación reactiva          |
| Swagger OpenAPI      | Documentación API              |
| Lombok               | Reducción de código repetitivo |

---

# Configuración del Proyecto

```properties
spring.application.name=bff-cuidadoseguro

server.port=8090

# URLs internas de los microservicios
auth.url=${AUTH_SERVICE_URL:http://localhost:8081}
pacientes.url=${PACIENTES_SERVICE_URL:http://localhost:8082}
datosmedicos.url=${DATOS_MEDICOS_SERVICE_URL:http://localhost:8083}

# Origenes permitidos para llamadas directas al BFF (solo desarrollo local)
cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:5173}

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs

# Aplicación reactiva
spring.main.web-application-type=reactive
```

---

# Explicación de Configuración

| Configuración                    | Descripción                |
| -------------------------------- | -------------------------- |
| spring.application.name          | Nombre del BFF             |
| server.port                      | Puerto del BFF             |
| auth.url                         | URL interna del MS Auth    |
| pacientes.url                    | URL interna de MS Pacientes|
| datosmedicos.url                 | URL interna de MS Datos Med|
| springdoc.swagger-ui.path        | Ruta Swagger UI            |
| spring.main.web-application-type | Configura WebFlux reactivo |

---

# Instalación del Proyecto

## Clonar repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

## Ingresar al proyecto

```bash
cd bff-cuidadoseguro
```

## Compilar proyecto

```bash
mvn clean install
```

---

# Ejecución del Proyecto

## Ejecutar localmente

```bash
mvn spring-boot:run
```

---

# Configuración Docker

```yaml
services:
  bff-cuidadoseguro:
    build: .
    container_name: bff-cuidadoseguro
    ports:
      - "8090:8090"
```

---

# Seguridad JWT

El sistema implementa autenticación basada en JSON Web Tokens (JWT).

## Funcionalidades implementadas

* Validación de tokens
* Protección de rutas
* Seguridad de endpoints
* Interceptación de solicitudes
* Integración con autenticación centralizada

---

# Swagger - Documentación API

## Acceso Swagger UI

```bash
http://localhost:8090/swagger-ui.html
```

## API Docs

```bash
http://localhost:8090/api-docs
```

---

# Comunicación con Microservicios

El BFF se integra con los distintos microservicios del sistema Cuidado Seguro.

## Servicios Integrados

* Microservicio de Autenticación
* Microservicio de Pacientes
* Microservicio de Datos Médicos

---

# Arquitectura Implementada

## Backend For Frontend (BFF)

Permite adaptar respuestas específicas para el frontend.

## AWS API Gateway

Unico punto de entrada publico: HTTPS, CORS, throttling y observabilidad perimetral. Integra unicamente con el BFF, nunca con los microservicios.

## Programación Reactiva

Implementada mediante Spring WebFlux.

## Seguridad JWT

Protege endpoints y controla autenticación.

---

# Requisitos Previos

Antes de ejecutar el proyecto se requiere:

* Java 17
* Maven
* Docker Desktop (opcional)
* Puerto 8090 disponible

---

# Puertos Utilizados

| Puerto | Descripción              |
| ------ | ------------------------ |
| 8090   | Puerto principal del BFF |
| 8081   | MS Auth                  |
| 8082   | MS Pacientes             |
| 8083   | MS Datos Médicos         |

---

# Testing y Validación

Las pruebas pueden realizarse mediante:

* Swagger UI
* Postman
* Frontend React

---

# Autor

Proyecto desarrollado para la asignatura de Fullstack III.

Desarrollado por: Carlos Bernal.

---

# Conclusión

El BFF de Cuidado Seguro implementa una arquitectura moderna basada en Spring WebFlux, detras de AWS API Gateway.

El sistema permite:

* Centralizar solicitudes
* Gestionar seguridad JWT
* Enrutar tráfico entre microservicios
* Mejorar la comunicación frontend-backend
* Escalar el sistema de forma modular

Todo esto permite construir una solución escalable, segura y preparada para arquitecturas distribuidas modernas.

## Despliegue
Imagen disponible en Docker Hub

# Ecommerce API

API para gestionar productos en un sistema de ecommerce. Permite realizar operaciones CRUD sobre productos, consultar datos desde la API de la NASA (Astronomy Picture of the Day) y está documentada con Swagger.

## Características

- CRUD de productos en una base de datos MongoDB.
- Integración con la API de la NASA para obtener imágenes astronómicas.
- Documentación interactiva con Swagger/OpenAPI.

---

## Requisitos Previos

Antes de ejecutar este proyecto, asegúrate de contar con:

- **Java 21** o superior.
- **MongoDB** (instalado localmente o como contenedor Docker).
- **Gradle** para la compilación del proyecto.
- **Docker** (opcional para despliegue en contenedores).

---

### Despliegue con Docker Compose

El proyecto incluye un archivo `docker-compose.yml` para facilitar la ejecución de la aplicación junto con MongoDB en contenedores.

### 1. Docker Hub
La aplicacion se subio a un repositorio publico, a continuacion se brindan los comando para poder compilar nuevamente la imagen y poder actualizar la app si fuera necesario

```bash
docker build -t ecommerce-api .
docker tag api-ecommerce:latest walterbamac/api-ecommerce:latest
docker push walterbamac/api-ecommerce:latest
```
#### **2. Despliegue**

Ejecuta el siguiente comando para iniciar los contenedores de la aplicación y MongoDB:

```bash
docker compose up -d
```

#### **3. Detener Servicios**
Ejecuta el siguiente comando para detener los contenedores de la aplicación y MongoDB:

```bash
docker compose down  --rmi all
```

---

## Documentación
Accede a la documentación interactiva en: http://localhost:8080/swagger-ui.html

# Sistema de Gestión de Biblioteca

Este es un sistema de gestión de bibliotecas que permite a los usuarios administrar libros, préstamos y usuarios dependiendo del rol asignado. El sistema está diseñado para bibliotecas pequeñas o medianas que necesitan una forma eficiente de gestionar su inventario de libros y realizar un seguimiento de los préstamos a los usuarios.
## Diagrama de clases
![Untitled Diagram](https://github.com/sebasrb08/Library-management/assets/118924181/03072bff-3d49-483a-9357-2ab9e2fc4ef4)

## Funcionalidades Principales

El sistema ofrece las siguientes funcionalidades principales:

1.  **Gestión de Libros**:
    
    -   Agregar, editar y eliminar libros.
    -   Ver detalles de libros, incluyendo título, autor, género, fecha de publicación y cantidad disponible.
2.  **Gestión de Usuarios**:
    
    -   Registrar, iniciar sesión y  eliminar usuarios de la biblioteca.
    -   Ver detalles de usuarios, incluyendo nombre de usuario, roles y estado de la cuenta.
3.  **Gestión de Préstamos**:
   
    -   Registrar nuevos préstamos de libros a usuarios.
    -   Ver detalles de préstamos, incluyendo información del libro, del usuario y fechas de préstamo y devolución.
    -   Cancelar préstamos y devolucion de libros a la biblioteca.
4.  **Actualización Automática de Estado**:
    
    -   El sistema actualiza automáticamente el estado de los préstamos según las fechas de devolución.


## Tecnologías Utilizadas

El sistema de gestión de bibliotecas está desarrollado utilizando las siguientes tecnologías:

-   **Java Spring Boot**: Utilizado para el desarrollo del backend del sistema.
-   **Spring Security**: Para la autenticación y autorización de usuarios en la aplicación.
-   **JSON Web Tokens (JWT)**: Utilizado como mecanismo de autenticación para generar tokens seguros y realizar la gestión de sesiones de usuario.
-   **Spring Data JPA**: Para el acceso a la base de datos y la gestión de entidades.
-   **Mapper**: Utilizado para mapear objetos DTO (Data Transfer Objects) a entidades y viceversa.
-   **JUnit 5 y Mockito**: Para escribir pruebas unitarias y realizar mocks de dependencias en las pruebas.
-   **Validation**: Para la validación de datos de entrada y asegurar la integridad de los datos en el sistema.
-   **Swagger**: Para la documentación y exploración interactiva de la API REST.
-   **Base de Datos Relacional (Ej. MySQL, PostgreSQL)**: Utilizada para almacenar la información de libros, usuarios y préstamos.

## Requisitos del Sistema

-   **Java 17**: Versión de Java necesaria para ejecutar la aplicación.
-   **MySQL**: Base de datos relacional utilizada para almacenar la información de libros, préstamos y usuarios.
-   Maven para la gestión de dependencias.

## Configuración

1.  Clonar el repositorio del proyecto desde GitHub.
2.  Configurar la base de datos MySQL y actualizar las propiedades de conexión en el archivo `application.properties`.
3.  Ejecutar el proyecto utilizando Maven o importarlo en un IDE compatible con Spring Boot.

## Calidad del Código y Pruebas Unitarias
 El proyecto se desarrolla siguiendo las mejores prácticas de codificación y se enfoca en mantener la calidad del código en todo momento. Algunos aspectos destacados incluyen:
  - Pruebas unitarias exhaustivas realizadas con JUnit y Mockito para garantizar la fiabilidad y estabilidad del sistema.
  - Adhesión a las convenciones de codificación y buenas prácticas de la industria para mantener un código limpio y legible.

## Uso


El sistema de gestión de bibliotecas puede ser accedido mediante la API REST y utilizando Swagger para una interfaz interactiva de exploración y prueba de la API. Los usuarios pueden interactuar con el sistema realizando las siguientes acciones:

### API REST

El sistema proporciona una API REST que permite a los usuarios interactuar con el sistema utilizando herramientas de cliente HTTP estándar. Las siguientes operaciones están disponibles a través de la API:
### Roles de Usuario

El sistema de gestión de bibliotecas cuenta con tres roles de usuario diferentes, cada uno con sus propias capacidades y permisos:

1.  **ADMIN**: Este rol está destinado para administradores del sistema, quienes tienen acceso completo y pueden realizar cualquier acción en la plataforma.
    
2.  **LIBRARIAN**: Los usuarios con el rol de LIBRARIAN tienen privilegios para gestionar el inventario de libros y préstamos.
    
3.  **USER**: Los usuarios con el rol USER pueden acceder a  ver los libros, realizar solicitudes de préstamo.
    

### Acceso a los Endpoints de la API

Los diferentes roles tienen acceso a diferentes partes de la API del sistema. A continuación se detalla el acceso a los endpoints según el rol del usuario:

-   **ADMIN**:
    
    -   Acceso completo a todos los endpoints de la API.
    -   Puede agregar, editar y eliminar libros, usuarios y préstamos.
    -   Puede ver detalles de libros, usuarios y préstamos.
-   **LIBRARIAN**:
    
    -   Acceso a endpoints relacionados con la gestión de libros y préstamos.
    -   Puede agregar, editar y eliminar libros.
    -   Puede registrar préstamos y procesar devoluciones.
    -   Puede ver detalles de libros y préstamos.
-   **USER**:
    
    -   Acceso limitado a endpoints para visualizar información y realizar solicitudes de préstamo.
    -   Puede ver detalles de libros y préstamos.
### Endpoints privados
-   **Gestión de Libros**:
    
    -   `POST /book/create`: Agregar un nuevo libro .
    -   `PUT /book/update/{id}`: Actualizar los detalles de un libro existente.
    -   `DELETE /book/delete/{id}`: Eliminar un libro.
    -   `GET /book/get/{id}`: Obtener detalles de un libro específico.
    -   `GET /book/get`: Obtener una lista de todos los libros .
    -    `GET /book/get/available`: Obtener una lista de todos los libros disponibles .
-   **Gestión de Usuarios**:
    
    -   `DELETE /user/delete/{id}`: Eliminar un usuario de la biblioteca.
    -   `GET /user/get/{id}`: Obtener detalles de un usuario específico.
    -   `GET /user/get`: Obtener una lista de todos los usuarios en la biblioteca.
-   **Gestión de Préstamos**:
    
    -   `POST /loan/request`: Registrar un nuevo préstamo de libro a un usuario.
    -   `PUT /loan/return/{id}`: Finalizar un préstamo y devolver un libro a la biblioteca.
    -  `DELETE/loan/cancellation/{id}`: Cancelar un préstamo específico.
    -   `GET /loan/approval/{id}`: Obtener detalles de un préstamo específico.
    -   `GET /loan/approval`: Obtener una lista de todos los préstamos registrados en el sistema.

### Endpoints publicos
-   **Iniciar Sesión**:
    
    -   `POST /auth/log-in`: Permite a los usuarios iniciar sesión proporcionando sus credenciales. Se espera recibir un token JWT válido como respuesta, el cual se utilizará para realizar solicitudes a los endpoints protegidos.
-   **Registrar Usuario**:
    
    -   `POST /auth/sign-in`: Permite a los usuarios registrarse en el sistema proporcionando sus detalles de usuario. Una vez registrado exitosamente, el usuario podrá iniciar sesión utilizando las credenciales proporcionadas.




### Swagger

Swagger proporciona una interfaz interactiva para explorar y probar la API del sistema de gestión de bibliotecas. Los usuarios pueden acceder a Swagger a través de la siguiente URL:

`http://localhost:8080/swagger-ui.html` 

Desde la interfaz de Swagger, los usuarios pueden ver la documentación de la API, probar diferentes endpoints y enviar solicitudes para interactuar con el sistema de forma segura y conveniente.

Para interactuar con la API utilizando Swagger, simplemente navega a la URL proporcionada y sigue las instrucciones en la interfaz.

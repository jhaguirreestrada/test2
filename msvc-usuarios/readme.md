## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [APIs Rest](#Apis REST)
### General Info
***
Proyecto de exposición y lectura de servicios REST como proyecto examen para IONIX
## Technologies
***
A list of technologies used within the project:
* [Java](https://example.com): Version 1.8
* [Base de Datos MySQL](https://example.com): Credenciales en archivo properties
* [Port](https://example.com): 8001
## Apis REST
***
* Crear Usuarios:  POST ==> localhost:8001/crear-usuario
```
Requiere autorizacion credenciales en arhivo properties
Request:
{
    "id": 5,
    "nombre": "Nombre Usuario 3",
    "userName": "usuario3",
    "email": "usuario555@correo.com",
    "phone": "666661"
}
```
* Listar Usuarios: GET ==> localhost:8001/listar-usuarios
* Listar Usuario por email: GET ==> localhost:8001/listar_usuario-por-email/{email}
* Eliminar usuario por Id: DELETE ==> localhost:8001/eliminar-usuario-por-id/{id}
```
Requiere autorizacion credenciales en arhivo properties
```

* Consultar API externa: POST ==> localhost:8001/consultar-api-externa

![img.png](img.png)

### Requisitos

- Java 11
- Spring Boot 2.6.3
- Maven
- Mysql

### Ambiente Local

Se debe ejecutar los siguientes comandos en una terminal

- mvn clean install

- El servicio será levantado en http://localhost:8080.

### Herramientas

Intellij idea

##   

**Params:**

| Parametro      | Requerido | Descripcion                                                     | Ejemplo |
|----------------|-----------|-----------------------------------------------------------------|---------|
| iteraciones    | ✓         | numero de iteraciones Q                                         | N/A     |
| idPilaDataBase | ✓         | un número del 1 al 5 que representara el id de la pila de datos |     N/A |

**Body:**

```json
{
  "responseCode": 200,
  "message": "Respuesta correcta, observa el log para ver los resultados =)",
  "respuesta": "6,4,2,3,5,7"
}
```

**GET**
``{url}/bar/tender?iteraciones=3&idPilaDataBase=6
``


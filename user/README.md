# Oracle User
# Requerimientos

- Java 11
- Docker
- Maven

# Configuración
Una vez descargado eh instalado los requerimeintos antes 
mencionados es necesario descargar la imagen de MySQL en docker, 
para esto se debe ejecutar el siguiente comando:

`docker pull mysql/mysql-server:8.0`

Dentro del archivo `application.yml` el cual se encuentra en la 
siguiente ruta del proyecto `/user/src/main/resources/application.yml`
se encuentran las configuraciones de conexión para la BD de MySQL, 
dentro de las cuales se realizarán cambios dependiendo el tipo
de ejecución se deberan cambiar los valores,
se muestran a continuación:

- `spring.datasource.username=<USERNAME>`
- `spring.datasource.password=<PASSWORD>` 
- `spring.datasource.url=jdbc:mysql://<HOST>:<PORT>/<SCHEMA>`

Cada que se levanta el microservicio este creará el schema de la BD,
el cual en caso de existir primero ejecutará el borrado del schema
y posteriormente su creación, si se desea cambiar esta configuración
se debe cambiar dentro del archivo `application.yml`
- `spring.jpa.hibernate.ddl-auto=create-drop`

# Ejecutar proyecto mediante comandos o terminal

Una ves descargada la imagen docker de la BD MySQL se debe 
ejecutar el siguiente comando:
- `docker run --name mysql8 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=testdb -e MYSQL_USER=admin -e MYSQL_PASSWORD=root -d mysql/mysql-server:8.0`

Donde:
* MYSQL_DATABASE=testdb : <SCHEMA>
* MYSQL_USER=admin : <USERNAME>
* MYSQL_PASSWORD=root : <PASSWORD>
* localhost : <HOST>
* 3306 : <PORT>

Se deberá abrir una terminal o línea de comandos 
y ubicarse en dentro de la carpeta raiz del proyecto `/user` 
ahi ejecutar el siguiente comando
- `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080`

O bien ejecutar el shell 
- `init_service.sh`

# Ejecutar usando docker-compose.yml

Dentro de la carpeta del proyecto `/user` (la raíz del proyecto) se encuentra
el archivo `docker-compose.yml`, dentro de este archivo 
se describen los siguientes servicios:

1. `mysqldb` \
Dentro de este service se agregan las configuraciones de la BD MySQL
las cuales deberán coincidir con las configuraciones del archivo
`application.yml` descrito en configuraciones donde el `<HOST> = mysqldb`
   1. environment: \
      `MYSQL_ROOT_PASSWORD: root` \
      `MYSQL_DATABASE: testdb` \
      `MYSQL_USER: <USERNAME>` \
      `MYSQL_PASSWORD: <PASSWORD>` 
   2. ports: \
      `- <PORT>:<PORT>`
2. `API` \
Este servicio es el microservicio de usuarios el cual levantara 
ejecutara el jar del servicio compilado, en sus configuraciones se puede definir
el puerto donde se desea que se despliegue el aplicativo
   1. ports: \
      `- "8080:8080"`

Se deberá abrir una terminal o línea de comandos y ubicarse 
en dentro de la carpeta raiz del proyecto `/user` ahi se debe compilar 
el proyecto usando el siguiente comando:
- `mvn clean install -DskipTest`

Despues de compilar de manera exitosa se deberá ejecutar el siguiente comando
para levantar los servicios mediante el siguiente comando
- `docker-compose up -d`

Si se desea ver los logs de los servicios cuando levanta ejecutar el siguiente comando
- `docker-compose up --build`

Si se desea detener el servicio
- `docker-compose stop`

Si se desea detener el servicio y borrar las imagenes 
- `docker-compose down`

# Test del microservicio usando swagger

Una vez levantados los servicios ingresar a la 
siguiente URL desde cualquier navegador
- `http://localhost:8080/swagger-ui.html`

Dentro del swagger se pueden acceder a los siguientes endpoints
* GET http://localhost:8080/oracle-employees/v1/users
* POST http://localhost:8080/oracle-employees/v1/users
Ejemplo \
  `{
  "address": {
  "city": "mexico",
  "country": "toluca",
  "county": "toluca",
  "phoneDetails": {
  "countryCode": "52",
  "phone": "7223964287"
  },
  "postalCode": "50170",
  "state": "MX",
  "street": "lago omega"
  },
  "firstName": "Emmmanuel",
  "lastName": "De la Isla",
  "role": "ARCHITECT"
  }`
* GET http://localhost:8080/oracle-employees/v1/users/{user-id}
* PUT http://localhost:8080/oracle-employees/v1/users/{user-id}
* DELETE http://localhost:8080/oracle-employees/v1/users/{user-id}
* PATCH http://localhost:8080/oracle-employees/v1/users/{user-id} \
Ejemplos: \
  `[
  {
  "op":"replace",
  "path": "/address/phoneDetails/phone",
  "value": "72239876543"
  }
  ]` \
  `[
  {
  "op":"replace",
  "path": "firstName",
  "value": "Emmanuel"
  }
  ]`
* GET http://localhost:8080/oracle-employees/v1/users/role/{role-id}


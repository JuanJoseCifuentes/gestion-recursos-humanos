## Información del equipo

Integrantes:
- Tomas Candelo Montoya
- Juan Jose Cifuentes Cuellar
- Gabriel Jimenez Mosquera

## Funcionalidad del proyecto

El proyecto esta destinado a manejar y llevar control del área de gestión humana de una empresa, teniendo en cuenta a los empleados, sus respectivos perfiles, sus roles y los certificados que obtengan por medio de capacitaciones en su empresa. El proyecto tiene la posibilidad de asignarle un rol único o repetido a un empleado, de asignarle su correspondiente perfil, en el cual se mencionan sus experiencias, capacidades entre otras, el proyecto tienen la capacidad de eliminar roles, empleados, sus perfiles correspondientes y los certificados que han obtenido.

## Como ejecutarlo

Para ejecutar el proyecto se necesita usar el JDK 19 como SDK en las propiedades del proyecto para que funcione perfectamente, luego después de ejecutar el proyecto en el IDE de su preferencia, debera ir a la dirección http//localhost:8080 y según los objetos y direcciones mencionadas en el Swagger podrá observar o realizar modificaciones a los datos de los recursos humanos de la empresa. 

Utilice el comando ``docker-compose up --build`` para levantar el proyecto desde Docker.

Si desea levantar el proyecto desde IntelliJ, utilice primero el comando 
```
docker run --name rh_db -d -p 8091:3306 -e MYSQL_DATABASE=gestion_rh_rest -e MYSQL_USER=manager_rh_1 -e MYSQL_PASSWORD=123456789 -e MYSQL_ROOT_PASSWORD=1234 mysql
```
y a continuación ejecute la aplicación desde el perfil predeterminado.


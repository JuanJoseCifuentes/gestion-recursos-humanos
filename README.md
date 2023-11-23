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
docker run --name rh_db -d -p 8091:3306 -e MYSQL_DATABASE=gestion_rh_rest -e MYSQL_USER=manager_rh_1 -e MYSQL_PASSWORD=123456789 -e MYSQL_ROOT_PASSWORD=1234 mysql
```
y a continuación ejecute la aplicación desde el perfil predeterminado.
_____________
## Requisitos no funcionales

- **Portabilidad:** Gracias a la ventaja de utilizar Docker, nuestro aplicativo es fácilmente desplegable en cualquier dispositivo que tenga Docker instalado, si bien esto implicaría que la base de datos tendría que iniciarse manualmente por medios separados.
- **Usabilidad:** Debido a la nomenclatura consistente a lo largo de los diferentes endpoints y a la simpleza de las peticiones rest, que en el más complicado de los casos tan solo pide del usuario un JSON (nomenclatura estándar para el ambiente de tecnología) y una autenticación básica, nuestra API es fácil de usar y presentará una experiencia simple y satisfactoria a las personas que trabajen con ella.
- **Seguridad:** Si bien la encriptación no es la más sofisticada de todas, nuestra API ofrece seguridad ante peticiones no autorizadas que pudieran llegar por otros medios ajenos al manager para quien la aplicación está diseñada.
- **Mantenibilidad:** Gracias al buen manejo de organización en carpetas y a la documentación adecuada, sería fácil tanto para nosotros como para posibles nuevos desarrolladores seguir implementando nuevas funcionalidades en el proyecto y corregir los posibles errores que se presenten eventualmente.
- **Escalabilidad:** Si bien el proyecto actual no lo tiene implementado, se podría, utilizando un aplicativo intermedio sencillo de Load Balancer como Eureka, podríamos tener múltiples instancias del proyecto levantadas en diferentes servidores para asegurar que el proyecto pudiera lidiar con cargas de trabajo especialmente pesadas. 
_____________
## Estilos y patrones arquitectónicos

### Estilo arquitectónico - Monolítico

El presente proyecto se trata claramente de una aplicación con estilo **monolítico**. Esto se hace obvio al considerar la funcionalidad y construcción del proyecto, que en todo momento demuestra que ha sido pensado como una única unidad cohesiva de código, y funciona como tal al momento de desplegarse. Para respaldar esta afirmación, repasaremos algunas de las características de este estilo:

- Al compilarse, se empaqueta como una sola pieza, facilitando su despliegue y observabilidad. En nuestro caso, este producto final es el único archivo JAR que se genera y que contiene todo el proyecto.

![Resultado del build, una unica pieza](/images/project_build.png)

- Contiene toda la funcionalidad requerida sin necesidad de acceder o desplegar otras aplicaciones. Nuestro proyecto maneja todas sus funcionalidades sin tener que acceder o realizar peticiones a cualquier otro aplicativo, como se puede ver en el siguiente ejemplo:

![Todas la lógica ocurre dentro del propio proyecto](/images/logica_autocontenida.png)

- Realiza todas sus operaciones por si mismo de punta a punta. Desde la primera interacción con el usuario, hasta el retorno de una respuesta, la información viaja únicamente a través de los procesos de nuestra aplicación.

![La información fluye solo dentro del proyecto](/images/flujo_proyecto.png)

En resumen, nuestro proyecto tiene un estilo monolítico, que no nos representa ningún riesgo ni contratiempo debido a su tamaño y alcance tan pequeño.

### Patrón arquitectónico - MVC

El patrón que más resalta en nuestro proyecto es el patrón arquitectónico de **MVC (Model - View - Controller)**. Esto es claro principalmente en la separación clara que existe en nuestro proyecto de los controladores, la lógica subyacente, y la representación de la información, que se da de la siguiente manera:

- **Controlador:** Es nuestro conjunto de endpoints encontrados en el paquete *controlador*. Son quienes reciben las peticiones http de los usuarios y se encargan de invocar funciones en nuestro modelo como respuesta. A su vez, es quien se encarga de retornar la información necesaria para que el componente de vista pueda relatarla al usuario.

- **Modelo:** En nuestro caso, el modelo se compone tanto de los repositorios encontrados en el paquete *db*, como de las clases encontradas en el paquete *logica*. Utilizando estas clases, es capaz de representar la información de la base de datos con la que opera el sistema, y también gestionar los accesos y consultas a la base de datos donde se encuentran.

- **Vista:** Para finalizar, la vista de nuestro proyecto sería el frontend correspondiente que visualiza para el usuario la sección de modelo de una forma adecuada para la interacción.

En definitiva, nuestro proyecto ha utilizado este patrón para organizar la comunicación de la información a lo largo de sus diferentes niveles, además de facilitar su desarrollo y mantenimiento mediante la separación de conceptos.

_______________
## Responsabilidad ética

Esta aplicación, tiene como funcionalidad el manejo del registro de empleados de una empresa, como responsabilidades, se debería tener suma precaución con el uso de la aplicación, ya que un dato mal ingresado en el empleado, en un perfil o en un certificado puede generar ruido y mala información dentro de empresa. Se recomienda que aquellos datos que se ingresen sean totalmente verídicos, y previamente verificados.

De igual manera, esta aplicación debería ser manejada únicamente por el personal de recursos humanos, ya que, al contar con la posibilidad de eliminar empleados se pueden presentar situaciones donde alguien, con una intención deshonesta elimine a algún empleado que no haya sido formalmente retirado de la empresa, lo cual puede generar inconvenientes dentro de misma empresa por distintos factores internos. También, al tener datos sensibles como lo son la dirección o el teléfono personal, para evitar posibles intentos de fraude telefónico, o extorsión como acoso físico, se sigue, y se vuelve a recomendar que la aplicación sea utilizada solo por personal autorizado para realizar el monitoreo y control del área de recursos humanos.


# TP INTEGRADOR BASE DE DATOS/ PROGRAMACIÓN AVANZADA I

**UTN Phones** es una compañia de telefonia celular con base en Mar del Plata, y quiere empezar a operar con nuevos sistemas.

Con este fin se nos explica el siguiente escenario :

- En nuestra compañía, un cliente es un individuo que posee 1 o mas lineas de telefono. Cada uno de estas líneas puede realizar llamadas a cualquier linea de telefono de nuestra propia compañía. No hay forma de realizar una llamada a otra compañía.
- Los precios de cada llamada se va a calcular dependiendo la localidad de origen y la de destino. Los numeros seran guardados siempre con el prefijo (Ej : 4552394 de Mar del Plata será guardado como 2234552394) y se debe contar con la tarifa correspondiente a las llamadas de la misma ciudad. **Por ejemplo :** Tarifa desde MDQ a MDQ es de $10 por minuto , MDQ a MIR $5 el minuto. Idealmente al momento de inserción de la llamada se debe calcular la localidad de origen y la de destino y aplicar la lógica para calcular su precio. Cada localidad tiene un prefijo que la identifica y que nos permitirá saber el precio por minuto de lallamada.
- Hay un proceso automático que todos los 1º de cada mes, corre automáticamente y verifica las llamadas no facturadas y genera una nueva factura con un vencimiento a 15 días. Debemos guardar solo el estado de si la factura esta paga ono.
- Se hace una factura por cada línea registrada en elsistema.
- Cada llamada debe pertenecer a solo unafactura.

La información de los empleados y clientes debe ser la siguiente :

- Nombre
- Apellido
- DNI
- Ciudad
- Provincia

Cada línea tiene los siguientes datos :

- Numero de linea (numero detelefono).
- Tipo de usuario (móvil oresidencial).

Las llamadas incluyen :

- Numero de telefonoorigen
- Número de teléfonodestino
- Localidad deorigen
- Localidad dedestino
- Precio porminuto
- Duración (ensegundos)
- Preciototal
- Factura donde está incluida esta llamada. Las tarifas incluyen:

- Ciudad deorigen
- Ciudad dedestino
- Precio porminuto

(\*) La ciudad de origen y de destino serán iguales para llamadas locales

Las facturas deben tener :

- Línea
- Cliente
- Cantidad dellamadas
- Preciocosto
- Preciototal
- Fecha
- Fecha devencimiento

# PROGRAMACIÓN AVANZADA I

**UTN Phones** ha contratado los servicios de dos empresas expertas en la realización de plataformas web. Con el fin de distribuir la carga de trabajo entre las dos empresas, una de ellas se encargará del desarrollo Web y Aplicación Celular, mientras a nuestra organización se encargará del desarrollo de una API REST para el mantenimiento de las líneas (Llámese desde ahora **Backoffice** , para permitir a los empleados poder manejar los datos de clientes, facturación, suspensión de líneas ,etc) y también para dar soporte al **portal de usuarios web y Aplicación Android**. Se debe generar también la infraestructura de base de datos necesaria para guardar la información. **La base de datos ** será escogida por el alumno según considere.

- El portal de usuarios y aplicacion Android deberá permitir:
  1. Login declientes
  2. Consulta de llamadas del usuario logueado por rango defechas.
  3. Consulta de facturas del usuario logueado por rango defechas.
  4. Consulta de destinos más llamados por elusuario.

- Desde el sistema de **Backoffice** , se debe permitir:

1. Login deempleados.
2. Manejo declientes.
3. Alta , baja y suspensión delíneas.
4. Consulta detarifas.
5. Consulta de llamadas porusuario.
6. Consulta de facturación . La facturación se hará directamente porun

proceso interno en la basedatos.

- Se debe permitir también el agregado de llamadas, con un login especial, ya que este método de nuestra API será llamado nada más que por el área de infraestructura cada vez que se produzca una llamada. El área de infraestructura sólo enviará la siguiente información de llamadas:
  - Número deorigen
  - Número dedestino
  - Duración de lallamada

La tarifa y las localidades de destino deberán calcularse al momento de guardar la llamada y no será recibido por la API REST.

En caso de **no realizar el TP de BASE DE DATOS II** , se debe calcular esta última información desde la API REST.

Para la completitud del TP se considera :

- Seguir los fundamentos de APIREST.
- División en capas mostradas en clase (Controller, Service,Persistence/Dao)
- Unit tests con cobertura de al menos 70% delcódigo.

El no cumplimiento de alguno de los requisitos significa la desaprobación del TP con la consiguiente desaprobación de la materia.

**Fechas de entrega :** 23/06 - 25/06 - 30/06 - 02/07

# BASE DE DATOS II

Se debe generar un **diseño de base de datos relacional** que permita dar soporte al escenario planteado anteriormente, y a su vez todo el trabajo que se requiera para cumplimentar lo siguiente :

1. Generar las estructuras necesarias para dar soporte a 4 sistemas diferentes:
  1. BACKOFFICE, que permitirá el manejo de clientes, líneas ytarifas.
  2. CLIENTES, que permitirá consultas de llamadas yfacturación.
  3. INFRAESTRUCTURA , que será el sistema que enviará la información de llamadas a la base dedatos.
  4. FACTURACIÓN , proceso automático defacturación.
2. La facturación se realizará por un proceso automático en la base de datos. Se debe programar este proceso para el primer día de cada mes y debe generar una factura por línea y debe tomar en cuenta **todas las llamadas no facturadas para cada una de las líneas, sin tener en cuenta su fecha.** La fecha de vencimiento de esta factura será estipulada a 15días.
3. Generar las estructuras necesarias para el cálculo de precio de llamadas y las localidades ( Ciudad de origen y ciudad de destino) al momento de inserción de llamadas, ya que solo se reciben los números de origen y destino , la fecha y la duración de lallamada.
4. Generar las estructuras necesarias para dar soporte a las consultas de llamadas por fecha y por usuario , debido a que tenemos restricción de que estas no pueden demorar más de dos segundos y tenemos previsto que tendremos 500.000.000 de llamadas en el sistema en el mediano plazo. Este reporteincluirá

:

1. Número deorigen
2. Ciudad deorigen
3. Número dedestino
4. Ciudad dedestino
5. Preciototal
6. Duración
7. Fecha y hora dellamada.

1. Como PLAN B , generar una estructura de base de datos NoSQL de su preferencia para dar soporte al problemaplanteado.

**Fechas de entrega :** 23/06 - 25/06 - 30/06 - 02/07

# Web Service Sopa de letras
Se crea RESTful web service with Spring Boot.

## Se crea un web service para publicar la `Sopa de letras`

 HTTP POST requests para crear la sopa de letras: `http://<server>/alphabetSoup`
  
	Recibe como parámetros: 
		 w - Ancho de la sopa de letras, valor opcional por defecto debe ser 15

		 h - Largo de la sopa de letras, valor opcional pode defecto debe ser 15

	   ltr - Habilitar o deshabilitar palabras que van de izquierda a derecha, valor opcional por defecto debe ser true

	   rtl - Habilitar o deshabilitar palabras que van de derecha a izquierda, valor opcional por defecto debe ser false

	   ttb - Habilitar o deshabilitar palabras que van desde arriba hacia abajo, valor opcional por defecto debe ser true

	   btt - Habilitar o deshabilitar palabras que van desde abajo hacia arriba, valor opcional por defecto debe ser false

		 d - Habilitar o deshabilitar palabras diagonales, valor por opcional por defecto debe ser false
		 
	Devuelve un `JSON`:
	  
	   En caso satisfactorio:`{“id”:”d041eaf2-0ac2-4376-812b-3e08be0bfd65”}`
	   En caso de error:`{“message”:”Mensaje de error”}`

  HTTP GET requests para visualizar la lista de palabras que se encuentran la sopa de letras: `http://<server>/alphabetSoup/list/{id}`
    
    Devuelve un `JSON` con la lista de palabras que se encuentran en la sopa de letras:
	  
	  `{"words": [
		"love                (9,11)(9,14)",
		"acoplar             (11,5)(11,11)",
		"inspiracion         (33,4)(43,4)",
		"matematica          (11,13)(20,13)",
		"lily                (17,1)(20,1)",
		"enhance             (35,5)(41,5)",
		"codigo              (33,6)(33,11)",
		"fuente              (27,9)(27,14)"
     ]`
	 
  HTTP GET requests para visualizar la sopa de letras: `http://<server>/alphabetSoup/view/{id}`
    
	Devuelve en texto plano indicando las palabras ya encontradas
	
  HTTP PUT requests para indicar que se ha encontrado una palabra y modificar el estado de la sopa de letras: `http://<server>/alphabetSoup/{id}?sc=6&sr=7&ec=6&er=11`
	
	Recibe como parámetros: 
	
	  sr - Fila donde comienza la palabra encontrada

	  sc - Columna donde comienza la palabra encontrada

      er - Fila donde termina la palabra encontrada

      ec - Columna donde termina la palabra encontrada
	  
	  Estos parámetros son obligatorios
	  
	Devuelve un `JSON` con un mensaje indicando si la palabra es correcta o no y actualiza el estado de la sopa de letras

## Qué se necesita
+  Un IDE para Java.
+ `JDK 1.8` o superior.
+ `Gradle 2.3+` o `Maven 3.0+`
+ `STD plug-in` o `Postman`

En el ficher pom.xml se encuentran las dependencias necesarias para ejecutar este proyecto.
# Proyecto de Automatización QA - Selenium

## Descripción

Este proyecto automatiza pruebas funcionales de un flujo de login y compra en una aplicación web usando **Java**, **Selenium WebDriver** y **JUnit 5**.

La aplicación bajo prueba es:
https://www.demoblaze.com/index.html

---

## Tecnologías utilizadas

* Java 21
* Selenium WebDriver
* JUnit 5
* Maven
* IntelliJ IDEA

---

## Estructura del proyecto

```
src
 └─ test
     └─ java
         └─ com.example.demo
             └─ LoginTest.java
```

---

## Casos de prueba implementados

### 1. Login Fallido

* Se ingresan credenciales incorrectas
* Se valida que aparezca un mensaje de error (alert)

### 2. Login Exitoso

* Se ingresan credenciales válidas
* Se valida que el usuario haya iniciado sesión correctamente

### 3. Compra Completa

* Login exitoso
* Selección de producto
* Agregar al carrito
* Llenado de formulario de compra
* Validación de mensaje de confirmación

---

## Configuración del proyecto

### 1. Clonar repositorio

```
git clone https://github.com/carlosxd753/examen-final-calidad-software.git
```

### 2. Instalar dependencias

```
mvn clean install
```

---

## Ejecución de pruebas

Desde IntelliJ:

* Click derecho sobre la clase `LoginTest`
* Run

O desde consola:

```
mvn test
```

---

## Notas importantes

* Se utiliza **ChromeDriver** para la ejecución
* Asegurarse de tener Google Chrome instalado
* Selenium Manager gestiona automáticamente el driver

---

## Autor

Carlos Basurto

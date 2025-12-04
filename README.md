
# 🦈 Wa-Tor: Simulación de Ecosistema Marino (Java Threads)

## 📜 Descripción General

Este proyecto es una implementación en **Java** del autómata celular **Wa-Tor** (Water Torus), un modelo de simulación de ecosistema marino. El objetivo principal es aplicar los conceptos de **concurrencia y multihilo** en Java, donde cada criatura (pez o tiburón) es tratada como un hilo (Thread) independiente que interactúa con un entorno compartido.

## 🎯 El Ejercicio: El Planeta Wa-Tor

La simulación se desarrolla en un mundo acuático, toroidal y reticulado de dimensión **$20 \times 20$** (los bordes se conectan).

### 🐟 Población Inicial

* **Peces:** 100 individuos (50 machos y 50 hembras).
* **Tiburones:** 10 individuos (5 machos y 5 hembras).

### 🚶 Reglas de Movimiento e Interacción

Cada habitante se mueve de una posición hacia otra adyacente (Norte, Sur, Este u Oeste). Las interacciones entre habitantes se rigen por las siguientes reglas:

1.  **Misma Especie y Mismo Sexo:** Ambos continúan su camino sin interactuar.
2.  **Especies Diferentes (Depredación):** El **tiburón siempre aniquila al pez** y continúa su camino.
3.  **Misma Especie y Distinto Sexo (Reproducción):** Se **reproducen**, generando un nuevo individuo de sexo aleatorio (macho o hembra con igual probabilidad), y ambos padres continúan su camino.

## 💻 Conceptos de Concurrencia Implementados

* **Hilos (Threads):** Cada pez y tiburón se implementa como un hilo (`Thread` o `Runnable`), permitiendo que todos los habitantes se muevan e interactúen simultáneamente.
* **Sincronización:** Es crucial implementar mecanismos de sincronización (como `synchronized` o `ReentrantLock`) para gestionar el acceso seguro a la cuadrícula compartida (`WorldGrid`) y evitar condiciones de carrera (race conditions) durante el movimiento y la interacción.

## 🛠️ Tecnologías Utilizadas

| Tecnología | Descripción |
| :--- | :--- |
| **Java** | Lenguaje de programación principal (se recomienda JDK 17+). |
| **`java.lang.Thread`** | Clase fundamental para la implementación de la concurrencia. |
| **Estructuras de Datos** | Uso de matrices (arrays 2D) o listas para modelar el mundo $20 \times 20$. |

## 📁 Estructura de Clases

El proyecto sigue un diseño orientado a objetos para encapsular la lógica de cada componente:

* `WaTor.java`: Clase principal que inicializa el mundo, crea la población inicial y lanza los hilos.
* `WorldGrid.java`: Contiene la lógica del tablero, maneja el movimiento toroidal y gestiona la sincronización.
* `Creature.java`: Clase abstracta o interfaz para definir el comportamiento base de un habitante.
* `Fish.java`: Implementa la lógica específica de movimiento, supervivencia y reproducción del pez.
* `Shark.java`: Implementa la lógica específica de movimiento, depredación y reproducción del tiburón.
* `Sex.java`: Enumeración para representar el sexo (`MACHO`, `HEMBRA`).

## ⚙️ Instalación y Ejecución

1.  **Clona el repositorio:**

    ```bash
    git clone https://docs.github.com/es/repositories/creating-and-managing-repositories/quickstart-for-repositories
    cd [nombre del repositorio]
    ```

2.  **Compila el código:**

    ```bash
    javac [ruta de tu clase principal].java
    ```

3.  **Ejecuta la simulación:**

    ```bash
    java [ruta de tu clase principal]
    ```

## 👤 Autor

* **Jostin Soza**
* GitHub: [SozaJostin-Sc](https://github.com/SozaJostin-Sc)

-----
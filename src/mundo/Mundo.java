package mundo;

import constantes.Colores;
import habitantes.*;
import mundo.util.Generador;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Mundo {
    /**
     * Constantes que definen las dimensiones del mundo
     *
     */
    private final int ANCHO = 20;
    private final int ALTO = 20;
    /// La decision de usar colas enlazadas se resume en su capacidad de manejar múltiples threads (los habitantes) accediendo al mapa de forma segura y eficiente.
    private ConcurrentLinkedQueue<Habitantes>[][] mapa = new ConcurrentLinkedQueue[ANCHO][ALTO];
    private boolean ejecutando = true;


    /**
     * Constructor
     *
     */
    public Mundo() {
        for (int i = 0; i < ANCHO; i++) {
            for (int j = 0; j < ALTO; j++) {
                mapa[i][j] = new ConcurrentLinkedQueue<>();
            }
        }
    }


    /**
     * Metodo que inicializa el mundo
     *
     */
    public void run() {
        /// Se utiliza una función lambda para definir el comportamiento del nuevo hilo de manera concisa y en línea.
        Thread hiloVisualizacion = new Thread(() -> {
            while (ejecutando) {
                limpiarPantalla();
                draw();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        hiloVisualizacion.start();
    }


    /**
     * Metodo para detener toda la ejecucion del mapa
     *
     */
    public void stop() {
        ejecutando = false;

        for (int i = 0; i < ANCHO; i++) {
            for (int j = 0; j < ALTO; j++) {
                for (Habitantes habitante : mapa[i][j]) {
                    if (habitante.isAlive()) {
                        habitante.interrupt();
                    }
                }
            }
        }

        // Esperar a que los threads terminen naturalmente
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

    }

    /**
     * Metodo para dibujar el mapa
     *
     */
    public void draw() {
        System.out.println("==================Wa-Tor=================");
        System.out.println("+----------------------------------------+");
        for (int i = 0; i < ALTO; i++) {
            System.out.print("|");
            for (int j = 0; j < ANCHO; j++) {
                System.out.print(obtenerSimboloColor(mapa[j][i]));
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("+----------------------------------------+");
        mostrarEstadisticasRapidas();
        System.out.println("==============LEYENDA====================");
        System.out.println(Colores.PEZ + "P = Pez " + Colores.RESET + "|" +
                Colores.TIBURON + "T = Tiburon " + Colores.RESET + "|" +
                Colores.AMBOS + "# = ambos" + Colores.RESET + "|" +
                Colores.AGUA + ". = mar " + Colores.RESET);
    }

    /**
     * Metodo sincronizado para agregar un habitante al mapa
     *
     */
    public synchronized void add(Habitantes habitante) {
        int x = (habitante.getX() + ANCHO) % ANCHO;
        int y = (habitante.getY() + ALTO) % ALTO;

        habitante.setY(y);
        habitante.setX(x);
        mapa[x][y].add(habitante);

    }

    /**
     * Metodo privado para obtener los simbolos del mapa
     *
     */
    private String obtenerSimboloColor(ConcurrentLinkedQueue<Habitantes> celda) {
        if (celda.isEmpty()) return Colores.AGUA + ". " + Colores.RESET;

        boolean esPez = false;
        boolean esTiburon = false;

        for (Habitantes h : celda) {
            if (h instanceof Pez) {
                esPez = true;
            } else if (h instanceof Tiburon) {
                esTiburon = true;
            }
        }

        if (esPez && esTiburon) return Colores.AMBOS + "#" + Colores.RESET + " ";
        if (esPez) return Colores.PEZ + "P" + Colores.RESET + " ";
        return Colores.TIBURON + "T" + Colores.RESET + " ";
    }

    /**
     * Metodo para eliminar un habitante del mapa.
     * @param habitante instancia de habitante
     * */
    public void eliminarHabitante(Habitantes habitante) {
        int x = habitante.getX();
        int y = habitante.getY();

        if (x >= 0 && x < ANCHO && y >= 0 && y < ALTO) {
            mapa[x][y].remove(habitante);
        }
    }

    /**
     * Metodo para limpiar la pantalla
     *
     */
    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Metodo para mover un habitante
     * @param h instancia de habitante
     * @param newX coordenada x.
     * @param newY coordenada y
     */
    public synchronized void mover(Habitantes h, int newX, int newY) {
        // Verificación temprana
        if (!h.isVivo()) return;

        int oldX = h.getX();
        int oldY = h.getY();

        // Normalizar nuevas coordenadas
        newX = (newX + ANCHO) % ANCHO;
        newY = (newY + ALTO) % ALTO;

        // Si no hay movimiento, salir
        if (oldX == newX && oldY == newY) return;

        // Interactuar ANTES de mover
        h.interactuar(newX, newY);

        // Verificar nuevamente después de interactuar
        if (!h.isVivo()) return;

        // Realizar el movimiento
        mapa[oldX][oldY].remove(h);
        h.setX(newX);
        h.setY(newY);
        mapa[newX][newY].add(h);
    }

    /**
     * Verificar si hay una instancia de una clase específica (Tiburon o Pez)
     * en la casilla dada
     *
     * @param x    coordenada X
     * @param y    coordenada Y
     * @param tipo clase hija a habitante para buscar
     * @return true si se encuentra al menos una instancia del tipo especificado.
     *
     */
    public boolean hayInstanciaDeClase(int x, int y, Class<? extends Habitantes> tipo) {
        x = (x + ANCHO) % ANCHO;
        y = (y + ALTO) % ALTO;

        ConcurrentLinkedQueue<Habitantes> celda = mapa[x][y];

        for (Habitantes h : celda) {
            if (tipo.isInstance(h) && h.isVivo()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Metodo para obtener la instancia de una clase
     * @param x    coordenada X
     * @param y    coordenada Y
     * @param tipo clase hija a habitante para buscar
     * @return Habitante, instancia de la clase habitante.
     * */
    public Habitantes obtenerHabitante(int x, int y, Class<? extends  Habitantes> tipo){
        x = (x + ANCHO) % ANCHO;
        y = (y + ALTO) % ALTO;

        ConcurrentLinkedQueue<Habitantes> celda = mapa[x][y];

        for (Habitantes h : celda) {
            if (tipo.isInstance(h) && h.isVivo()) {
                return h;
            }
        }

        return null;
    }


    /**
     * Estadísticas finales simplificadas
     */
    public void mostrarEstadisticasFinales() {
        int peces = 0;
        int tiburones = 0;

        for (int i = 0; i < ANCHO; i++) {
            for (int j = 0; j < ALTO; j++) {
                for (Habitantes habitante : mapa[i][j]) {
                    if (habitante.isVivo()) {
                        if (habitante instanceof Pez) {
                            peces++;
                        } else if (habitante instanceof Tiburon) {
                            tiburones++;
                        }
                    }
                }
            }
        }

        int totalVivos = peces + tiburones;

        System.out.println("\n" + "=".repeat(40));
        System.out.println("     ESTADÍSTICAS FINALES");
        System.out.println("=".repeat(40));
        System.out.println("Total habitantes vivos: " + totalVivos);
        System.out.println("Peces: " + peces);
        System.out.println("Tiburones: " + tiburones);
        System.out.println("=".repeat(40));
    }


    /**
     * Método rápido para estadísticas durante la simulación
     */
    public void mostrarEstadisticasRapidas() {
        int peces = 0, tiburones = 0;

        for (int i = 0; i < ANCHO; i++) {
            for (int j = 0; j < ALTO; j++) {
                for (Habitantes habitante : mapa[i][j]) {
                    if (habitante.isVivo()) {
                        if (habitante instanceof Pez) peces++;
                        else if (habitante instanceof Tiburon) tiburones++;
                    }
                }
            }
        }

        System.out.printf(Colores.PEZ + "Peces: %d " + Colores.RESET + "| " +
                Colores.TIBURON + "Tiburones: %d " + Colores.RESET + "| " +
                "Total: %d\n", peces, tiburones, peces + tiburones);
    }


    /**
     * GETTERS Y SETTERS
     *
     */
    public ConcurrentLinkedQueue<Habitantes>[][] getMapa() {
        return mapa;
    }

    public void setMapa(ConcurrentLinkedQueue<Habitantes>[][] mapa) {
        this.mapa = mapa;
    }

    public int getANCHO() {
        return ANCHO;
    }

    public int getALTO() {
        return ALTO;
    }

}


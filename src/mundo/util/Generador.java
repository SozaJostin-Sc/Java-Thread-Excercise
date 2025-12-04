package mundo.util;

import constantes.Movimiento;
import constantes.Sexo;
import habitantes.*;
import mundo.Mundo;

import java.util.Random;

public class Generador {
    /**
     * Constantes
     *
     */
    private final int MAX_Y = 20, MAX_X = 20;
    private static final Random rd = new Random();
    private static Mundo mundo = null;
    private static final int MAX_PECES_MACHOS = 50;
    private static final int MAX_PECES_HEMBRA = 50;
    private static final int MAX_TIBURONES_MACHOS = 5;
    private static final int MAX_TIBURONES_HEMBRA = 5;
    private static final int MAX_ANIOS_ANTIGUEDAD = 20;

    /**
     * Constructor
     *
     */
    public Generador(Mundo mundo) {
        Generador.mundo = mundo;
        generarTiburones();
        generarPeces();
    }

    /**
     * Metodo privado para la generacion de peces
     *
     */
    private void generarPeces() {
        /// variables locales
        int x, y, mov, antiguedad;
        Movimiento[] movimientos;
        Pez pez;
        for (int i = 0; i < MAX_PECES_MACHOS; i++) {
            x = rd.nextInt(MAX_X);
            y = rd.nextInt(MAX_Y);
            movimientos = Movimiento.values();
            mov = rd.nextInt(movimientos.length);
            antiguedad = rd.nextInt(MAX_ANIOS_ANTIGUEDAD);
            pez = new Pez(x, y, Sexo.MACHO, true, movimientos[mov], antiguedad, mundo);
            pez.iniciar();
        }

        for (int i = 0; i < MAX_PECES_HEMBRA; i++) {
            x = rd.nextInt(MAX_X);
            y = rd.nextInt(MAX_Y);
            antiguedad = rd.nextInt(MAX_ANIOS_ANTIGUEDAD);
            movimientos = Movimiento.values();
            mov = rd.nextInt(movimientos.length);
            pez = new Pez(x, y, Sexo.HEMBRA, true, movimientos[mov], antiguedad, mundo);
            pez.iniciar();
        }
    }

    /**
     * Metodo privado para la generacion de tiburones
     *
     */
    private void generarTiburones() {
        /// variables locales
        int x, y, mov, antiguedad;
        Movimiento[] movimientos;
        Tiburon tiburon;
        for (int i = 0; i < MAX_TIBURONES_MACHOS; i++) {
            x = rd.nextInt(MAX_X);
            y = rd.nextInt(MAX_Y);
            antiguedad = rd.nextInt(MAX_ANIOS_ANTIGUEDAD);
            movimientos = Movimiento.values();
            mov = rd.nextInt(movimientos.length);
            tiburon = new Tiburon(x, y, Sexo.MACHO, true, movimientos[mov], antiguedad, mundo);
            tiburon.iniciar();
        }

        for (int i = 0; i < MAX_TIBURONES_HEMBRA; i++) {
            x = rd.nextInt(MAX_X);
            y = rd.nextInt(MAX_Y);
            movimientos = Movimiento.values();
            mov = rd.nextInt(movimientos.length);
            antiguedad = rd.nextInt(MAX_ANIOS_ANTIGUEDAD);
            tiburon = new Tiburon(x, y, Sexo.HEMBRA, true, movimientos[mov], antiguedad, mundo);
            tiburon.iniciar();
        }
    }

    /**
     * Metodo publico estatico para la generacion de un solo pez en determinadas coordenadas.
     *
     */
    public static void generarPez(int x, int y) {
        Movimiento[] movimientos = Movimiento.values();
        int mov = rd.nextInt(movimientos.length);
        int antiguedad = rd.nextInt(MAX_ANIOS_ANTIGUEDAD);

        Pez pez = new Pez(x, y, Sexo.MACHO, true, movimientos[mov], antiguedad, mundo);
        pez.iniciar();
    }

    /**
     * Metodo publico estatico para la generacion de un solo tiburon en determinadas coordenadas.
     *
     */
    public static void generarTiburon(int x, int y) {
        Movimiento[] movimientos = Movimiento.values();
        int mov = rd.nextInt(movimientos.length);
        int antiguedad = rd.nextInt(MAX_ANIOS_ANTIGUEDAD);

        Tiburon tiburon = new Tiburon(x, y, Sexo.MACHO, true, movimientos[mov], antiguedad, mundo);
        tiburon.iniciar();
    }

}
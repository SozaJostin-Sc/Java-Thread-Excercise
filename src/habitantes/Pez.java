package habitantes;

import constantes.Movimiento;
import constantes.Sexo;
import mundo.util.Generador;
import mundo.Mundo;

public class Pez extends Habitantes {

    /**
     * Constructor de la clase hija Pez.
     */
    public Pez(int x, int y, Sexo sexo, boolean vivo, Movimiento movimiento, int antiguedad, Mundo mundo) {
        super(x, y, sexo, vivo, movimiento, antiguedad, mundo);
    }

    /**
     * Implementacion del metodo mover
     *
     */
    @Override
    public void mover() {
        if (!isVivo()) return;

        //Calcular nuevas coordenadas SIN modificar las actuales
        int newX = getX();
        int newY = getY();

        switch (movimiento) {
            case ARRIBA -> newY = getY() - 1;
            case ABAJO -> newY = getY() + 1;
            case DERECHA -> newX = getX() + 1;
            case IZQUIERDA -> newX = getX() - 1;
        }

        //Normalizar ANTES de cualquier modificación
        int normalizedX = (newX + getMundo().getANCHO()) % getMundo().getANCHO();
        int normalizedY = (newY + getMundo().getALTO()) % getMundo().getALTO();

        //Solo el método mover del mundo actualiza las coordenadas
        mundo.mover(this, normalizedX, normalizedY);
    }


    /**
     * Implementacion del metodo interactuar
     * Se maneja la logica de relacion entre habitantes
     */
    @Override
    public void interactuar(int x, int y) {
        /*
         * Bloque de código sincronizado
         * Con esto garantizamos que esta parte del código sea usada por un hilo a la vez.
         * Garantizando la atomicidad de este bloque.
         */
        synchronized (getMundo()) {
            if (!isVivo()) return;

            /// Interacción si hay un tiburon
            if (getMundo().hayInstanciaDeClase(x, y, Tiburon.class)) {
                this.morir();
                return;
            }

            /// LOGICA SI HAY OTRO PEZ
            Habitantes otroPez = getMundo().obtenerHabitante(x, y, Pez.class);
            if (otroPez != null && otroPez.isVivo()) {
                if (otroPez != this) {
                    if (otroPez.getSexo() != this.getSexo()) {
                        reproducirse(x, y);
                    } else {
                        if (this.getAntiguedad() > otroPez.getAntiguedad()) {
                            otroPez.morir();
                        } else if (this.getAntiguedad() < otroPez.getAntiguedad()) {
                            this.morir();
                        }
                    }
                }
            }
        }
    }

    /**
     * Implementacion del metodo reproducirse.
     *
     */
    @Override
    public void reproducirse(int x, int y) {
        Generador.generarPez(x, y);
    }
}

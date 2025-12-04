package habitantes;

import constantes.Movimiento;
import constantes.Sexo;
import mundo.util.Generador;
import mundo.Mundo;

public class Tiburon extends Habitantes {

    /**
     * Constructor de la clase hija Tiburon.
     */
    public Tiburon(int x, int y, Sexo sexo, boolean vivo, Movimiento movimiento, int antiguedad, Mundo mundo) {
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
        /// Metodo sincronizado para garantizar la atomicidad.
        synchronized (getMundo()) {
            if (!isVivo()) return;

            Habitantes otroPez = getMundo().obtenerHabitante(x, y, Pez.class);
            Habitantes otroTiburon = getMundo().obtenerHabitante(x, y, Tiburon.class);

            //Si hay un pez
            if (getMundo().hayInstanciaDeClase(x, y, Pez.class)) {
                otroPez.morir();
                return;
            }

            /// Sí se encuentra con otro tiburon.
            if (otroTiburon != null && otroTiburon.isVivo()) {
                if (otroTiburon != this) {
                    if (otroTiburon.getSexo() != this.getSexo()) {
                        reproducirse(x, y);
                    } else {
                        if (this.getAntiguedad() > otroTiburon.getAntiguedad()) {
                            otroTiburon.morir();
                        } else if (this.getAntiguedad() < otroTiburon.getAntiguedad()) {
                            this.morir();
                        }
                    }
                }
            }
        }
    }

    /**
     * Implementacion de logica de reproduccion.
     *
     */
    @Override
    public void reproducirse(int x, int y) {
        Generador.generarTiburon(x, y);
    }
}

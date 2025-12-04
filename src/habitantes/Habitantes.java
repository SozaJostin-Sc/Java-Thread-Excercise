package habitantes;

import constantes.Movimiento;
import constantes.Sexo;
import mundo.Mundo;

public abstract class Habitantes extends Thread{
    /**
     * Atributos principales de un habitante en Wa-tor
     */
    protected int x,y; //Hacen referencia a las coordenadas en un plano cartesiano
    protected Sexo sexo;
    protected final Mundo mundo;
    protected boolean vivo;
    protected Movimiento movimiento;
    protected int antiguedad;

    /**
     * Constructor principal de la clase abstracta Habitante
     * */
    public Habitantes(int x, int y, Sexo sexo, boolean vivo, Movimiento movimiento, int antiguedad, Mundo mundo) {
        this.x = x;
        this.y = y;
        this.sexo = sexo;
        this.vivo = vivo;
        this.movimiento = movimiento;
        this.antiguedad = antiguedad;
        this.mundo = mundo;
        this.mundo.add(this); /// Con la referencia directa a mundo, cada vez que haya una nueva instancia, automaticamente se agrega a mundo.
    }

    /**
     * Método para iniciar el habitante explícitamente
     */
    public void iniciar() {
        if (this.vivo && !this.isAlive()) {
            this.start();
        }
    }

    /**
     * Metodos abstractos
     * */
    public abstract void interactuar(int x, int y);
    public abstract void mover();
    public abstract void reproducirse(int x, int y);

    /**
     * Metodo que inicializa la instancia de Habitante
     * */
    @Override
    public void run() {
        while (isVivo() && !Thread.currentThread().isInterrupted()) {
            try {
                mover();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Metodo que cambia el estado de un habitante a muerto.
     * */
    public void morir() {
        if (this.vivo) {
            setVivo(false);
            mundo.eliminarHabitante(this);
            this.interrupt(); // Asegurar que el thread termine
        }
    }


    /**
     * GETTERS Y SETTERS
     * */
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Sexo getSexo() {
        return sexo;
    }
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    public Mundo getMundo() {
        return mundo;
    }
    public boolean isVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    public Movimiento getMovimiento() {
        return movimiento;
    }
    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }
    public int getAntiguedad() {
        return antiguedad;
    }
    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }
}

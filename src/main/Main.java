package main;

import mundo.util.Generador;
import mundo.Mundo;

public class Main {
    public static void main(String[] args) {
        Mundo mundo = new Mundo();
        new Generador(mundo);
        mundo.run();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mundo.stop();
        mundo.mostrarEstadisticasFinales();
    }
}

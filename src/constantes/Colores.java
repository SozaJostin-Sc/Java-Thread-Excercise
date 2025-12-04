package constantes;

/*
* En Java,
* los colores ANSI se utilizan con códigos de escape que se insertan
* en la salida de texto para modificar su color en la consola.
* */

public enum Colores {
    PEZ  ("\033[0;32m"),
    TIBURON ("\u001B[31m"),
    AGUA("\u001B[34m"),
    AMBOS("\u001B[33m"),
    RESET("\u001B[0m");

    private final String code;

    Colores(String code){
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

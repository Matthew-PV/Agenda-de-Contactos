package dominio;
import java.lang.StringBuilder;

public class Contacto {
    private String nombre, numeroDeTelefono;
    public Contacto() {}
    public Contacto(String nombre, String numeroDeTelefono) {
        this.nombre = nombre;
        this.numeroDeTelefono = numeroDeTelefono;
    }


    //Getters y setters
    public String getNombre() {return nombre;}
    public Contacto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public String getNumeroDeTelefono() {return numeroDeTelefono;}
    public Contacto setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
        return this;
    }


    //Métodos de Contacto
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(": ").append(numeroDeTelefono);
        return sb.toString();
    }
    public boolean equals(Object object) {
        Contacto contacto = (Contacto) object;
        if (object == null) {return false;}
        else if (contacto.equals(object)) {return true;}
        else {return false;}
    }
}
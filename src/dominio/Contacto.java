package dominio;
import java.io.Serializable;
import java.lang.StringBuilder;

public class Contacto implements Serializable {
    private String nombre, apellido, numeroDeTelefono;


    //Constructores
    public Contacto() {
        nombre = "";
        apellido = "";
        numeroDeTelefono = "";
    }
    public Contacto(String nombre) {
        this.nombre = nombre;
        apellido = "";
        numeroDeTelefono = "";
    }
    public Contacto(String nombre, String numeroDeTelefono) {
        this.nombre = nombre;
        apellido = "";
        this.numeroDeTelefono = numeroDeTelefono;
    }
    public Contacto(String nombre, String apellido, String numeroDeTelefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDeTelefono = numeroDeTelefono;
    }


    //Getters y setters
    public String getNombre() {return nombre;}
    public Contacto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public String getApellido() {return apellido;}
    public Contacto setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }
    public Contacto quitarApellido() {
        apellido = "";
        return this;
    }
    public String getNumeroDeTelefono() {return numeroDeTelefono;}
    public Contacto setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
        return this;
    }
    public Contacto quitarTelefono() {
        numeroDeTelefono = "";
        return this;
    }


    //Métodos de Contacto
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        Contacto contacto = (Contacto) object;
        return nombre.equals(contacto.nombre) && apellido.equals(contacto.apellido);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (apellido == "") {
            sb.append(nombre).append(": ").append(numeroDeTelefono);
        }
        else {
            sb.append(nombre).append(' ').append(apellido).append(": ").append(numeroDeTelefono);
        }
        return sb.toString();
    }
    public int hashCode() {
        return (nombre.hashCode()-1)*33+apellido.hashCode();
    }
}
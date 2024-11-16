package dominio;

public class ContactNotFound extends Exception {
    private final Contacto contacto;

    public ContactNotFound(Contacto contacto) {this.contacto = contacto;}

    public Contacto getContacto() {return contacto;}
    public String nombreContacto() {return contacto.getNombre();}
    public String apellidoContacto() {return contacto.getApellido();}
}
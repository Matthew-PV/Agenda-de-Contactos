package dominio;

public class ContactDuplicated extends Exception{
    private final Contacto contacto;

    public ContactDuplicated(Contacto contacto) {this.contacto = contacto;}

    public Contacto getContacto() {return contacto;}
    public String nombreContacto() {return contacto.getNombre();}
    public String apellidoContacto() {return contacto.getApellido();}
}
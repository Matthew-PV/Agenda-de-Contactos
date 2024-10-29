package dominio;
import java.util.ArrayList;

public class Libreta {
    private ArrayList<Contacto> Contactos;
    public Libreta() {}
    public Libreta(ArrayList<Contacto> Contactos){
        this.Contactos = Contactos;
    }


    //Trabajo con Contacto
    public ArrayList<Contacto> getContactos() {return Contactos;}
    public Libreta setContactos(ArrayList<Contacto> Contactos) {
        this.Contactos = Contactos;
        return this;
    }
    public Contacto getContacto(int index) {return Contactos.get(index);}
    public Libreta add(Contacto contacto) {
        Contactos.add(contacto);
        return this;
    }
    public int size() {return Contactos.size();}


    //Métodos de Libreta
    public String toString() {
        try {
            StringBuilder sb = new StringBuilder("Contactos\n");
            int index = 1;
            for (Contacto contacto : Contactos) {
                sb.append('\t').append(index).append(". ").append(contacto);
                index++;
            }
            return sb.toString();
        } catch (NullPointerException e) {
            return "No hay contactos en la libreta.";
        }
    }
}
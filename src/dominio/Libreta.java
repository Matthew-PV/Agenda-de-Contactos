package dominio;
import interfaz.Interfaz;

import java.io.*;
import java.util.ArrayList;

public class Libreta implements Serializable {
    private static final ArrayList<Libreta> Libretas = new ArrayList<>();
    private String nombre;
    private ArrayList<Contacto> Contactos;


    //Constructores
    public Libreta() {
        nombre = "";
        Contactos = new ArrayList<>();
        Libretas.add(this);
    }
    public Libreta(String nombre) {
        this.nombre = nombre;
        Contactos = new ArrayList<>();
        Libretas.add(this);
    }


    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public Libreta setNombre(String nombre) {
        File file = new File(this.nombre+".ser");
        if (file.exists()) file.renameTo(new File(nombre + ".ser"));
        this.nombre = nombre;
        return this;
    }
    public ArrayList<Libreta> getLibretas() {return Libretas;}
    public static Libreta getLibreta(String nombre) {
        for (Libreta libreta : Libretas) {
            if (libreta.nombre.equals(nombre)) {
                return libreta;
            }
        }
        return null;
    }
    public static boolean exists(String nombre) {return getLibreta(nombre) != null;}


    //Trabajo con Contacto
    public ArrayList<Contacto> getContactos() {
        return Contactos;
    }

    public Libreta setContactos(ArrayList<Contacto> Contactos) {
        this.Contactos = Contactos;
        return this;
    }
    public Contacto getContacto(int index) {
        return Contactos.get(index);
    }
    public Contacto getContacto(String nombre) {
        Contacto contacto = new Contacto(nombre);
        if (Contactos.contains(contacto)) return Contactos.get(Contactos.indexOf(contacto));
        else return new Contacto();
    }
    public Contacto getContacto(String nombre, String apellido) {
        Contacto contacto = new Contacto(nombre, apellido);
        if (Contactos.contains(contacto)) return Contactos.get(Contactos.indexOf(contacto));
        else return new Contacto();
    }
    public Libreta add(Contacto contacto) throws ContactDuplicated {
        if (Contactos.contains(contacto)) throw new ContactDuplicated(contacto);
            else Contactos.add(contacto);
        return this;
    }
    public int size() {
        return Contactos.size();
    }
    public Contacto buscar(Contacto contacto) {
        int index = Contactos.indexOf(contacto);
        if (index == -1) {
            return null;
        } else {
            return Contactos.get(index);
        }
    }
    /*  Versión alternativa del método remove
    public boolean remove(Contacto contacto) {
        int index = Contactos.indexOf(contacto);
        if (index != -1) {
            Contactos.remove(index);
            return true;
        }
        return false;
    }
    */
    public boolean borrarContacto(Contacto contacto) throws ContactNotFound{
            if (Contactos.contains(contacto)) {
                contacto = Contactos.get(Contactos.indexOf(contacto));
                if (Interfaz.confirmacion("¿Estás seguro de que quieres borrar a "+contacto+"?")) {
                    Contactos.remove(contacto);
                    return true;
                }
            } else throw new ContactNotFound(contacto);
        return false;
    }
    public boolean modificarContacto(Contacto contacto,String[] modificacion)
            throws ArrayIndexOutOfBoundsException, ContactNotFound {

        boolean res = true;
        String instruccion = modificacion[0]; String valor = modificacion[1];
        if (Contactos.contains(contacto)) {
            contacto = Contactos.get(Contactos.indexOf(contacto));

            if (Interfaz.confirmacion("¿Estás seguro de que quieres modificar a "+contacto+"?")) {

                if (instruccion.equalsIgnoreCase("nombre"))
                    contacto.setNombre(valor);

                else if (instruccion.equalsIgnoreCase("apellido"))
                    contacto.setApellido(valor);

                else if (instruccion.equalsIgnoreCase("numeroDeTelefono") ||
                instruccion.equalsIgnoreCase("telefono") ||
                instruccion.equalsIgnoreCase("numero"))
                contacto.setNumeroDeTelefono(valor);

                else if (instruccion.equalsIgnoreCase("quitar")) { //Atributos que se pueden quitar
                    if (valor.equalsIgnoreCase("apellido"))
                        contacto.quitarApellido();
                    else if (valor.equalsIgnoreCase("numeroDeTelefono") ||
                            valor.equalsIgnoreCase("telefono") ||
                            valor.equalsIgnoreCase("numero"))
                        contacto.quitarTelefono();
                    else {
                        System.out.println("Atributo incorrecto.");
                        res = false;
                    }
                }

                else {
                    System.out.println("Atributo incorrecto.");
                    res = false;
                }

            }
        } else throw new ContactNotFound(contacto);
        return res;
    }


    //Métodos de Libreta
    public static Libreta leer(String nombre) {
        try {
            ObjectInput fi = new ObjectInputStream(new FileInputStream(nombre+".ser"));
            Libreta libreta = (Libreta) fi.readObject();
            fi.close();
            return libreta;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error de lectura.");
            return new Libreta();
        }
    }
    public void grabar() {
        try {
            File file = new File(nombre+".ser");
            if (file.exists()) {
                ObjectOutputStream fo = new ObjectOutputStream(new FileOutputStream(nombre + ".ser"));
                if (Interfaz.confirmacion("Ya existe una libreta llamada " +
                        nombre + ". ¿Quieres sobreescribirla?")) {
                    file.delete();
                    fo.writeObject(this);
                    fo.close();
                }
            }
            else {
                ObjectOutputStream fo = new ObjectOutputStream(new FileOutputStream(nombre + ".ser"));
                fo.writeObject(this);
                fo.close();
            }
        } catch (IOException e) {
            System.out.println("Error de escritura.");
        }
    }
    public boolean borrar() {
        if (Interfaz.confirmacion("¿Estás seguro de que quieres borrar "+nombre+"?")) {
            File file = new File(nombre + ".ser");
            file.delete();
            return true;
        }
        return false;
    }

    public boolean equals(Object object) {
        if (object == null) {return false;}
        if (this.getClass() != object.getClass()) {return false;}
        Libreta libreta = (Libreta) object;
        return nombre.equals(libreta.nombre);
    }
    public String toString() {
        if (!Contactos.isEmpty()) {
            try {
                StringBuilder sb = new StringBuilder("Contactos ").append(nombre);
                int index = 1;
                for (Contacto contacto : Contactos) {
                    sb.append("\n\t").append(index).append(". ")
                            .append(contacto);
                    index++;
                }
                return sb.toString();
            } catch (NullPointerException e) {
                return "No hay contactos en la libreta.";
            }
        } else {
            return "No hay contactos en la libreta.";
        }
    }
    public int hashCode() {
        return (nombre.hashCode()-1)*33;
    }
}
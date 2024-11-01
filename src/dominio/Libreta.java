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
    public Libreta add(Contacto contacto) {
        Contactos.add(contacto);
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
            ObjectOutputStream fo = new ObjectOutputStream(new FileOutputStream(nombre + ".ser"));
            if (file.exists()) {
                if (Interfaz.confirmacion("Ya existe una libreta llamada " +
                        nombre + ". ¿Quieres sobreescribirla?")) {
                    file.delete();
                    fo.writeObject(this);
                    fo.close();
                }
            }
            else {
                fo.writeObject(this);
                fo.close();
            }
        } catch (IOException e) {
            System.out.println("Error de escritura.");
        }
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
}
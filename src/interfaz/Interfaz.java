package interfaz;
import dominio.*;
import java.io.File;
import java.util.Scanner;

public class Interfaz {
    private Libreta libreta;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CIAN = "\u001B[36m";
    public static final String ANSI_AZUL = "\u001B[34m";
    public static final String ANSI_AMARILLO = "\u001B[33m";
    public static final String ANSI_MORADO = "\u001B[35m";
    private static final Scanner teclado = new Scanner(System.in);
    public Interfaz() {}


    //Métodos de Interfaz
    public void ejecutar() {
        System.out.println("¡Bienvenido/a! ¿En qué libreta quieres trabajar?");
        String nombreLibreta = teclado.nextLine();

        File file = new File(nombreLibreta+".ser");
        if (file.exists()) libreta = Libreta.leer(nombreLibreta);
        else libreta = new Libreta(nombreLibreta);
        System.out.println();

        String peticion;
        do {
            peticion = leerPeticion();
        } while (procesandoPeticion(peticion));
    }
    public String listaOpciones() {
        StringBuilder sb = new StringBuilder(ANSI_CIAN+"Lista de opciones de "
                +ANSI_AMARILLO+libreta.getNombre()+ANSI_RESET+"\n");
        sb.append("\t1. "+ANSI_CIAN+"añadir"+ANSI_AZUL+" <nombre> <apellido(opcional)> <teléfono>"+ANSI_RESET+": " +
                        "Añade un contacto a la libreta.\n")
                .append("\t2. "+ANSI_CIAN+"modificar"+ANSI_AZUL+" <nombre> <apellido(opcional)> <(atributo a modificar)/quitar> <valor/(atributo a quitar)>"+ANSI_RESET+":\n" +
                        "\t\tCambia un valor para un determinado contacto. Se pueden quitar el apellido y el teléfono.\n")
                .append("\t3. "+ANSI_CIAN+"borrar"+ANSI_AZUL+" <nombre> <apellido(opcinal)>"+ANSI_RESET+": Borra un contacto de la libreta.\n")
                .append("\t4. "+ANSI_CIAN+"lista"+ANSI_RESET+": Muestra la lista de contactos.\n")
                .append("\t5. "+ANSI_MORADO+"cambiarNombre"+ANSI_AZUL+" <nuevo nombre>"+ANSI_RESET+": Cambia el nombre de la libreta.\n")
                .append("\t6. "+ANSI_MORADO+"grabar"+ANSI_RESET+": Guarda la libreta de contactos.\n")
                .append("\t7. "+ANSI_MORADO+"borrarLibreta"+ANSI_RESET+": Borra la libreta (Este cambio no puede deshacerse).\n")
                .append("\t8. "+ANSI_MORADO+"cambiarLibreta"+ANSI_AZUL+" <nombre de la libreta>"+ANSI_RESET+": Cambia la libreta sobre la que se trabaja.\n")
                .append("\t9. "+ANSI_AMARILLO+"salir"+ANSI_RESET+": Sale del programa.\n")
                .append("Por favor, introduzca las instrucciones sin espacios adicionales.\n");
        return sb.toString();
    }
    public String leerPeticion() {
        System.out.print(listaOpciones()+"Introduce tu petición: ");
        String entrada = teclado.nextLine();
        return entrada;
    }
    public boolean procesandoPeticion(String entrada) {
        String[] peticion = entrada.split("\\s+");
        if (peticion[0].equalsIgnoreCase("añadir")) return add(peticion);
        else if (peticion[0].equalsIgnoreCase("modificar") ||
                peticion[0].equalsIgnoreCase("cambiar")) return modificar(peticion);
        else if (peticion[0].equalsIgnoreCase("borrar")) return borrar(peticion);
        else if (peticion[0].equalsIgnoreCase("lista" )) return lista();
        else if (peticion[0].equalsIgnoreCase("cambiarNombre")) return cambiarNombre(peticion);
        else if (peticion[0].equalsIgnoreCase("grabar")) return grabar();
        else if (peticion[0].equalsIgnoreCase("borrarLibreta")) return borrarLibreta();
        else if (peticion[0].equalsIgnoreCase("cambiarLibreta")) return cambiarLibreta(peticion);
        else if (peticion[0].equalsIgnoreCase("salir")) {
            if (confirmacion("¿Quieres guardar los cambios?")) {
                libreta.grabar();
            }
            System.out.println("Saliendo del programa.");
            return false;
        }
        else {
            System.out.println("Petición errónea.");
            System.out.println();
            return true;
        }
    }


    //Peticiones
    private boolean add(String[] peticion) {
        try {
            if (peticion.length <= 3) { //Contacto sin apellido
                libreta.add(new Contacto(peticion[1], peticion[2]));
            }
            else { //Contacto con apellido
                libreta.add(new Contacto(peticion[1],peticion[2],peticion[3]));
            }
        } catch(ArrayIndexOutOfBoundsException e) {
                /* Dará este error cuando el usuario haya introducido menos o más datos de los necesarios,
                pues "peticion[]" será más o menos corta que lo que la creación del contacto requiere. */
            System.out.println("Por favor, introduce la información con los espacios indicados.");
        }
        catch (ContactDuplicated e) {
            System.out.println(e.nombreContacto()+" "+e.apellidoContacto()+" ya existe.");
        }
        System.out.println();
        return true;
    }
    public boolean modificar(String[] peticion) {
        try {
            if (peticion.length <= 4) //Contacto sin apellido
                 libreta.modificarContacto(new Contacto(peticion[1]),
                         new String[]{peticion[2], peticion[3]});
            else //Contacto con apellido
                libreta.modificarContacto(new Contacto(peticion[1],peticion[2], ""),
                        new String[]{peticion[3], peticion[4]});
        } catch(ArrayIndexOutOfBoundsException e) {
                /* Dará este error cuando el usuario haya introducido menos o más datos de los necesarios,
                pues "peticion[]" será más o menos corta que lo que la creación del contacto requiere. */
            System.out.println("Por favor, introduce la información con los espacios indicados.");
        }
        catch(ContactNotFound e) {
            System.out.println(e.nombreContacto()+" "+e.apellidoContacto()+" no encontrado.");
        }
        System.out.println();
        return true;
    }
    private boolean borrar(String[] peticion) {
        try {
            if (peticion.length <= 2) { //Contacto sin apellido
                libreta.borrarContacto(new Contacto(peticion[1]));
            }
            else { //Contacto con apellido
                libreta.borrarContacto(new Contacto(peticion[1],peticion[2],""));
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
                /* Dará este error cuando el usuario haya introducido menos o más datos de los necesarios,
                pues "peticion[]" será más o menos corta que lo que la creación del contacto requiere. */
            System.out.println("Por favor, introduce la información con los espacios indicados.");
        }
        catch(ContactNotFound e) {
            System.out.println(e.nombreContacto()+" "+e.apellidoContacto()+" no encontrado.");
        }
        System.out.println();
        return true;
    }
    private boolean lista() {
        System.out.println(libreta+"\n");
        return true;
    }
    private boolean cambiarNombre(String[] peticion) {
        if (confirmacion("¿Quieres cambiar el nombre de "+libreta.getNombre()+
                " a "+peticion[1]+"?")) {
            libreta.setNombre(peticion[1]);
        }
        System.out.println();
        return true;
    }
    private boolean grabar() {
        libreta.grabar();
        System.out.println();
        return true;
    }
    private boolean borrarLibreta() {
        if (libreta.borrar()) {
            System.out.println("¿En qué libreta quieres trabajar?");
            String nombreLibreta = teclado.nextLine();

            File file = new File(nombreLibreta+".ser");
            if (file.exists()) libreta = Libreta.leer(nombreLibreta);
            else libreta = new Libreta(nombreLibreta);
        }
        System.out.println();
        return true;
    }
    private boolean cambiarLibreta (String[] peticion) {
        try {
            if (confirmacion("¿Quieres guardar los cambios?")) {
                libreta.grabar();
            }

            File file = new File(peticion[1] + ".ser");
            if (file.exists()) {
                libreta = Libreta.leer(peticion[1]);
            } else {
                libreta = new Libreta(peticion[1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Por favor, introduce la información correctamente.");
        }
        System.out.println();
        return true;
    }


    //Métodos de utilidad
    public static boolean confirmacion(String pregunta) {
        boolean resultado = true; String siNo;
        do {
            System.out.println(pregunta+" (si/no): ");
            siNo = teclado.nextLine();

            if (siNo.equalsIgnoreCase("no") || siNo.equalsIgnoreCase("n"))
                resultado = false;
            else if(!(siNo.equalsIgnoreCase("si") || siNo.equalsIgnoreCase("s")))
                System.out.println("Respuesta incorrecta, inténtalo de nuevo.");

        } while(!(siNo.equalsIgnoreCase("si") || siNo.equalsIgnoreCase("s") ||
                siNo.equalsIgnoreCase("no") || siNo.equalsIgnoreCase("n")));
        return resultado;
    }
}
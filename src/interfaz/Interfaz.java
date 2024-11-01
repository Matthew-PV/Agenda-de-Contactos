package interfaz;
import dominio.*;

import java.io.File;
import java.util.Scanner;

public class Interfaz {
    private Libreta libreta;
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
        StringBuilder sb = new StringBuilder("Lista de opciones de "+libreta.getNombre()+"\n");
        sb.append("\t1. añadir <nombre> <apellido(opcional)> <teléfono>: Añade un contacto a la libreta.\n")
                .append("\t2. cambiarNombre <nuevo nombre>: Cambia el nombre de la libreta.\n")
                .append("\t3. lista: Muestra la lista de contactos.\n")
                .append("\t4. grabar: Guarda la libreta de contactos.\n")
                .append("\t5. cambiarLibreta <nombre de la libreta>: Cambia la libreta sobre la que se trabaja.\n")
                .append("\t6. salir: Sale del programa.\n")
                .append("\tPor favor, introduzca las instrucciones sin espacios adicionales.\n");
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
        else if (peticion[0].equalsIgnoreCase("cambiarNombre")) return cambiarNombre(peticion);
        else if (peticion[0].equalsIgnoreCase("lista")) return lista();
        else if (peticion[0].equalsIgnoreCase("grabar")) return grabar();
        else if (peticion[0].equalsIgnoreCase("cambiarLibreta")) return cambiarLibreta(peticion);
        else if (peticion[0].equalsIgnoreCase("salir")) {
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
                System.out.println();
                return true;
            }
            else { //Contacto con apellido
                libreta.add(new Contacto(peticion[1],peticion[2],peticion[3]));
                System.out.println();
                return true;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
                /* Dará este error cuando el usuario haya introducido menos o más datos de los necesarios,
                pues "peticion[]" será más o menos corta que lo que la creación del contacto requiere. */
            System.out.println("Por favor, introduce la información con los espacios indicados.");
            System.out.println();
            return true;
        }
    }
    private boolean cambiarNombre(String[] peticion) {
        if (confirmacion("¿Quieres cambiar el nombre de "+libreta.getNombre()+
                " a "+peticion[1]+"?")) {
            libreta.setNombre(peticion[1]);
        }
        System.out.println();
        return true;
    }
    private boolean lista() {
        System.out.println(libreta+"\n");
        return true;
    }
    private boolean grabar() {
        libreta.grabar();
        System.out.println();
        return true;
    }
    private boolean cambiarLibreta (String[] peticion) {
        if (confirmacion("¿Quieres guardar los cambios?")) {
            libreta.grabar();
            libreta = Libreta.leer(peticion[1]);
        }
        else {
            libreta = Libreta.leer(peticion[1]);
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
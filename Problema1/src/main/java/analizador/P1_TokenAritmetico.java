
package analizador;

/**
 *
 * @author celimar rojas
 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P1_TokenAritmetico {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- ANALIZADOR LÉXICO ----");
        System.out.print("Ingresar la expresión aritmética por favor: ");
        String entrada = scanner.nextLine();
        
        System.out.println("\n---- RESULTADO DEL ANÁLISIS ----");
        analizarExpresion(entrada);
        System.out.println(); // Salto de línea final
    }

    public static void analizarExpresion(String entrada) {
        // 1. Definición de Patrones (Tokens) según las reglas
        String regexNumero = "[0-9]+(\\.[0-9]+)?";        // Entero o real con punto
        String regexOperador = "[\\+\\-\\*/]";            // Operadores básicos
        String regexParenIzq = "\\(";
        String regexParenDer = "\\)";
        String regexOperando = "[a-zA-Z_][a-zA-Z0-9_]*"; // Letra/guion, no inicia con número

        // Combinamos los patrones en grupos con nombre para identificarlos en el bucle
        String regexCompleta = String.format("(?<NUMERO>%s)|(?<OPERADOR>%s)|(?<PIZQ>%s)|(?<PDER>%s)|(?<OPERANDO>%s)",
                regexNumero, regexOperador, regexParenIzq, regexParenDer, regexOperando);

        Pattern pattern = Pattern.compile(regexCompleta);
        Matcher matcher = pattern.matcher(entrada);

        int index = 0;
        int contadorParentesis = 0;
        boolean errorBalanceo = false;

        while (index < entrada.length()) {
            // Ignorar los espacios en blanco entre componentes
            if (Character.isWhitespace(entrada.charAt(index))) {
                index++;
                continue;
            }

            // Intentamos encontrar el siguiente token válido en la posición actual
            if (matcher.find(index) && matcher.start() == index) {
                if (matcher.group("NUMERO") != null) {
                    System.out.print("NUMERO " + matcher.group("NUMERO") + " ");
                } else if (matcher.group("OPERADOR") != null) {
                    System.out.print("OPERADOR " + matcher.group("OPERADOR") + " ");
                } else if (matcher.group("PIZQ") != null) {
                    System.out.print("PAREN_IZQ ( ");
                    contadorParentesis++;
                } else if (matcher.group("PDER") != null) {
                    System.out.print("PAREN_DER ) ");
                    contadorParentesis--;
                    if (contadorParentesis < 0) errorBalanceo = true;
                } else if (matcher.group("OPERANDO") != null) {
                    System.out.print("OPERANDO " + matcher.group("OPERANDO") + " ");
                }
                index = matcher.end(); // Movemos el índice al final del token encontrado
            } else {
                // Si el carácter no coincide con ninguna regla, es un ERROR
                System.out.print("ERROR " + entrada.charAt(index) + " ");
                index++;
            }
        }

        // Validación de paréntesis balanceados al final del análisis
        if (!errorBalanceo && contadorParentesis == 0) {
            System.out.println("PARÉNTESIS BALANCEADOS.");
        } else {
            System.out.println("PARÉNTESIS NO BALANCEADOS.");
        }
    }
}
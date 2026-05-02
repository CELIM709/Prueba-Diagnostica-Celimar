
package matematicas;

/**
 *
 * @author celimar rojas
 */
import java.util.Scanner;

public class P3_ConjeturaDeCollatz {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- VISUALIZADOR DE LA CONJETURA DE COLLATZ ---");
        
        // 1. Pedir los límites del intervalo [p, q]
        System.out.print("Ingrese el limite inferior (p): ");
        int p = scanner.nextInt();
        
        System.out.print("Ingrese el limite superior (q): ");
        int q = scanner.nextInt();
        
        // 2. Validación de la regla: q >= 100 * p
        if (q < 100 * p) {
            System.out.println("ERROR: No se cumple la regla (q >= 100p).");
            System.out.println("Para p=" + p + ", q debe ser al menos " + (100 * p));
            return; 
        }
        
        System.out.println("\nIniciando la demostracion en el intervalo [" + p + ", " + q + "]...");
        
        // 3. Evaluar cada número n en el intervalo
        for (int n = p; n <= q; n++) {
            demostrarCollatz(n);
        }
        
        System.out.println("\nDemostrado para todos los numeros en el intervalo.");
    }

    public static void demostrarCollatz(int nOriginal) {
        // Usamos long para evitar desbordamiento en números grandes
        long n = nOriginal; 
        System.out.print("n=" + nOriginal + ": " + n);
        
        while (n > 1) {
            if (n % 2 == 0) {
                n = n / 2; // Si es par
            } else {
                n = (3 * n) + 1; // Si es impar
            }
            System.out.print(" -> " + n);
        }
        System.out.println(""); 
    }
}
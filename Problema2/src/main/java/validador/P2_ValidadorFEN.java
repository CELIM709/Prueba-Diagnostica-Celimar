
package validador;

/**
 *
 * @author celimar rojas
 */
public class P2_ValidadorFEN { 

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("---- VALIDADOR DE POSICIONES FEN (AJEDREZ) ----");
        System.out.print("Introduzca la cadena FEN por favor: ");
        String fen = scanner.nextLine();

        String resultado = validarFEN(fen);
        
        System.out.println("\n--- RESULTADO ---");
        System.out.println(resultado);
    }

    public static String validarFEN(String cadena) {
        if (cadena == null || cadena.trim().isEmpty()) {
            return "INVALIDO: La entrada se encuentra vacia.";
        }

        // 1. Dividir en los 6 campos obligatorios
        String[] partes = cadena.trim().split("\\s+");
        if (partes.length != 6) {
            return "INVALIDO: Debe tener 6 campos (se encontraron " + partes.length + ").";
        }

        String colocacion = partes[0];
        String turno = partes[1];
        String enroque = partes[2];
        String enPassant = partes[3];
        String medioMovimiento = partes[4];
        String numeroMovimiento = partes[5];

        // 2. Validar Colocación de Piezas (8 filas)
        String[] filas = colocacion.split("/");
        if (filas.length != 8) {
            return "INVALIDO: El campo de piezas debe tener 8 filas.";
        }

        for (int i = 0; i < filas.length; i++) {
            String fila = filas[i];
            int casillasEnFila = 0;

            for (char c : fila.toCharArray()) {
                if (Character.isDigit(c)) {
                    casillasEnFila += Character.getNumericValue(c);
                } else if ("pnbrqkPNBRQK".indexOf(c) != -1) {
                    casillasEnFila += 1;
                } else {
                    return "INVALIDO: Caracter extraño '" + c + "' en la fila " + (i + 1);
                }
            }

            if (casillasEnFila != 8) {
                return "INVALIDO: La fila " + (i + 1) + " suma " + casillasEnFila + " casillas (deben ser 8).";
            }
        }

        // 3. Validar Turno
        if (!turno.matches("[wb]")) {
            return "INVÁLIDO: El turno debe ser 'w' o 'b'.";
        }

        // 4. Validar Enroque
        if (!enroque.equals("-")) {
            if (!enroque.matches("[KQkq]+")) {
                return "INVÁLIDO: El enroque contiene caracteres no permitidos.";
            }
            java.util.Set<Character> vistos = new java.util.HashSet<>();
            for (char c : enroque.toCharArray()) {
                if (!vistos.add(c)) {
                    return "INVALIDO: El campo de enroque contiene letras duplicadas.";
                }
            }
        }

        // 5. Validar En Passant
        if (!enPassant.equals("-")) {
            if (!enPassant.matches("[a-h][36]")) {
                return "INVALIDO: Casilla en-passant invalida (ej: e3, h6).";
            }
        }

        // 6. Validar Números (Medio y Full movimiento)
        try {
            if (Integer.parseInt(medioMovimiento) < 0) return "INVALIDO: Medio movimiento negativo.";
            if (Integer.parseInt(numeroMovimiento) < 1) return "INVALIDO: Número de movimiento < 1.";
        } catch (NumberFormatException e) {
            return "INVALIDO: Los campos de movimiento deben ser números enteros.";
        }

        return "VALIDO: La cadena cumple con la notacion FEN.";
    }
}
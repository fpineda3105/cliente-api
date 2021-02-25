package pe.com.fpineda.challenge.cliente.util;

import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * @author fpineda
 */
public class FechaUtils {

    private static final SecureRandom rand = new SecureRandom();
    private static final int MIN_AGE = 70;
    private static final int MAX_AGE = 80;

    private FechaUtils() {}

    public static LocalDate generarPosibleFechaMuerte(int edadActual) {
        var edadMuerte = (int) rand.ints(MIN_AGE, (MAX_AGE + 1)).limit(10).average().getAsDouble();
        var anosRestantes = edadMuerte - edadActual;
        if (anosRestantes <= 0) {
            anosRestantes = MAX_AGE + 1;
        }

        var fechaActual  = LocalDate.now();

        var anoMuerte = fechaActual.plusYears(anosRestantes).getYear();
        var mesMuerte = (int) rand.ints(1,13).limit(10).average().getAsDouble();
        var diaMuerte = (int) rand.ints(1,30).limit(10).average().getAsDouble();

        return LocalDate.of(anoMuerte,mesMuerte,diaMuerte);

    }
}

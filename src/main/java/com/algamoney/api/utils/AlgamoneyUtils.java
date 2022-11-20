package com.algamoney.api.utils;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AlgamoneyUtils {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(1979, 6, 9);

        long data = convertLocalDateToTimestamp(localDate);

        LocalDate date  =  convertTimestampToLocalDate(data);
    }

    public static String formatLocalDateTime(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:SS");
        return date != null ? date.format(formatter) : null;
    }

    public static String formatLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        return date != null ? date.format(formatter) : null;
    }

    public static long convertLocalDateToTimestamp(LocalDate localDate) {
       return localDate != null ? localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
         .toEpochMilli() : null;
    }

    public static LocalDate convertTimestampToLocalDate(Long dateInMillis) {
        return dateInMillis != null ? new Timestamp(dateInMillis).toLocalDateTime().toLocalDate() : null;
    }

    public static String limit(String str, int limit) {
        return limit(str, limit, true);
    }

    public static String limit(String str, int limit, boolean traduzirAcentos) {
        if (traduzirAcentos) {
            str = traduzirAcentos(str);
        }
        if (str != null && str.length() > limit) {
            return str.substring(0, limit);
        }
        return str;
    }

    public static String traduzirAcentos(String texto) {
        if (texto == null) {
            return null;
        } else {
            texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
            return texto.replaceAll("\\p{M}", "");
        }
    }
}

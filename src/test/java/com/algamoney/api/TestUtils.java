package com.algamoney.api;

import com.algamoney.api.database.entity.enumeration.CategoriaTelefone;
import com.algamoney.api.utils.AlgamoneyUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class TestUtils {

    public static void main(String[] args) {
        //converterLocalDateToMilisegundos();

        //enumToStrin();

        decryptText();


    }

    public static void decryptText() {
        String text = "$2a$10$T8Fpc6FuRen2uLodDyfDOu/P1OWJddQMU8TXYXF6REd8gpu17Y.KK\n";
        String decryptedText = encryptDecryptText(text);
        System.out.println(decryptedText);
    }

    public static String encryptDecryptText(String text) {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        char[] encryptedDecryptedText = text.toUpperCase().toCharArray();
        for(int index = 0; index < encryptedDecryptedText.length; index++) {
            int letterIndex  = ALPHABET.indexOf(encryptedDecryptedText[index]);
            int cipherIndex = ((ALPHABET.length() - 1) * letterIndex + (ALPHABET.length() - 1)) % ALPHABET.length();
            encryptedDecryptedText[index] = ALPHABET.charAt(cipherIndex);
        }
        return String.valueOf(encryptedDecryptedText);
    }

    private static void converterLocalDateToMilisegundos() {
        LocalDate localDateInicial = LocalDate.parse("1979-06-09");
        long timeInMillisFinal = AlgamoneyUtils.convertLocalDateToTimestamp(localDateInicial);
        System.out.println(timeInMillisFinal);

    }

    private static void enumToStrin() {
        List<CategoriaTelefone> list = Arrays.asList(CategoriaTelefone.values());
        String categoria = CategoriaTelefone.RESIDENCIAL.name();
        CategoriaTelefone categoriaTelefone = CategoriaTelefone.valueOf(CategoriaTelefone.RESIDENCIAL.name());

        System.out.println(categoria);

        Integer idCategoria = CategoriaTelefone.RESIDENCIAL.getCategoria();
        System.out.println(idCategoria);
    }

    private static void teste() {
        LocalDate hoje = LocalDate.now();
        LocalDate amanha = LocalDate.now().plusDays(1);
        //LocalDate data  = Objects.re(hoje, "amanha");
        /*Objects.requireNonNullElse(this.formalizacao.getDataExpiracao(),
                formalizacao.getDataEnvio().toLocalDate().plusDays(LIMITE_DIAS_EXPIRACAO));*/
    }


}

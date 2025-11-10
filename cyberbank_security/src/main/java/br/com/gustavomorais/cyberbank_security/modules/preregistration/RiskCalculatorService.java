package br.com.gustavomorais.cyberbank_security.modules.preregistration;

import java.time.LocalDate;
import java.time.Period;

public class RiskCalculatorService {

    public static int calcularRiskIndex(String dataNascimento, String uf, String cidade) {
        int idadeRisco = getRiscoPorIdade(dataNascimento);
        int ufRisco = getRiscoPorUF(uf);
        int cidadeRisco = getRiscoPorCidade(cidade);
        return (int) ((idadeRisco * 0.5) + (ufRisco * 0.25) + (cidadeRisco * 0.25));
    }

    private static int getRiscoPorIdade(String dataNascimento) {
        int idade = calcularIdade(dataNascimento);
        if (idade >= 18 && idade <= 21)
            return 600;
        if (idade >= 22 && idade <= 26)
            return 500;
        if (idade >= 27 && idade <= 35)
            return 400;
        if (idade >= 36 && idade <= 49)
            return 300;
        if (idade >= 50 && idade <= 59)
            return 200;
        return 150;
    }

    private static int getRiscoPorUF(String uf) {
        return switch (uf.toUpperCase()) {
            case "SP" -> 900;
            case "RJ" -> 900;
            case "CE" -> 840;
            case "PE" -> 820;
            case "AL" -> 810;
            case "BA" -> 800;
            case "DF" -> 790;
            case "AM" -> 780;
            case "MG" -> 770;
            case "RS" -> 760;
            case "PR" -> 750;
            case "PA" -> 740;
            case "SC" -> 730;
            case "PB" -> 720;
            case "RN" -> 710;
            case "SE" -> 700;
            case "GO" -> 690;
            case "ES" -> 680;
            case "MA" -> 670;
            case "PI" -> 660;
            case "MT" -> 650;
            case "MS" -> 640;
            case "RO" -> 630;
            case "AP" -> 620;
            case "AC" -> 610;
            case "RR" -> 600;
            case "TO" -> 590;

            default -> 590;
        };
    }

    private static int getRiscoPorCidade(String cidade) {
        return switch (cidade.toLowerCase()) {
            case "são paulo" ->	900;
            case "rio dejaneiro" -> 900;
            case "fortaleza" ->	840;
            case "campinas" ->	820;
            case "maceió" ->	810;
            case "recife" ->	800;
            case "salvador" ->	790;
            case "belo Horizonte" -> 780;
           case "curitiba" ->	770;
            case "brasília" ->	760;
            case "manaus" ->	750;
           case  "belém" ->	740;
            case "goiânia" ->	730;
           case  "joão pessoa" ->	720;
           case  "natal" -> 	710;
           case  "porto Alegre" ->	700;
           case  "vitória" ->	690;
           case  "feira de Santana" ->	680;
            case "são Luís" ->	670;
           case "uberlândia" ->	660;

            default -> 400;
        };
    }

    private static int calcularIdade(String dataNascimento) {
        LocalDate nascimento = LocalDate.parse(dataNascimento);
        return Period.between(nascimento, LocalDate.now()).getYears();
    }
}

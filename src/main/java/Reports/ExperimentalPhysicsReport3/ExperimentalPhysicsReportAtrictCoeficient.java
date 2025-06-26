package Reports.ExperimentalPhysicsReport3;
import Incertezas.IncertezaTipoA;
import Incertezas.IncertezaTipoB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentalPhysicsReportAtrictCoeficient {
    public static void executar(String caminhoArquivo) {

        List<Double> coluna1 = new ArrayList<>();
        List<Double> coluna2 = new ArrayList<>();
        List<Double> coluna3 = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(";");

                if (valores.length > 2) {
                    try {
                        double valorColuna1 = Double.parseDouble(valores[0].replace(",", ".").trim());
                        double valorColuna2 = Double.parseDouble(valores[1].replace(",", ".").trim());
                        double valorColuna3 = Double.parseDouble(valores[2].replace(",", ".").trim());


                        coluna1.add(valorColuna1);
                        coluna2.add(valorColuna2);
                        coluna3.add(valorColuna3);
                    } catch (NumberFormatException e) {
                        // Ignorar linhas com erro de conversão
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro de leitura: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        double[] L2discoR = coluna1.stream().mapToDouble(Double::doubleValue).toArray();
        double[] L2CilindroR = coluna2.stream().mapToDouble(Double::doubleValue).toArray();
        double[] L2disco2r = coluna3.stream().mapToDouble(Double::doubleValue).toArray();

        double L1 = 30; // em cm
        double resolucaoL1 = 0.5; //em cm
        double resolucaoL2 = 0.8; //em cm
        double incertezaL1 = IncertezaTipoB.calcularIncertezaPadrao(resolucaoL1);

        double medioL2DiscoR = IncertezaTipoA.calcularMedia(L2discoR);
        double medioL2CilindroR = IncertezaTipoA.calcularMedia(L2CilindroR);
        double medioL2disco2R = IncertezaTipoA.calcularMedia(L2disco2r);

        double miEstaticoDiscoR = medioL2DiscoR/L1;
        double miEstaticoCilindroR = medioL2CilindroR/L1;
        double miEstaticoDisco2R = medioL2disco2R/L1;

        //Calculando incertezas

        double incertezaL2DiscoR = IncertezaTipoA.calcularIncertezaPadrao(L2disco2r);
        double incertezaL2CilindroR = IncertezaTipoA.calcularIncertezaPadrao(L2CilindroR);
        double incertezaL2disco2R = IncertezaTipoA.calcularIncertezaPadrao(L2disco2r);


        double incertezamiEstaticoDiscoR = Math.sqrt(Math.pow((medioL2DiscoR/Math.pow(L1,2))*incertezaL1,2)+Math.pow(incertezaL2DiscoR/L1,2));
        double incertezamiEstaticoDisco2R = Math.sqrt(Math.pow((medioL2disco2R/Math.pow(L1,2))*incertezaL1,2)+Math.pow(incertezaL2disco2R/L1,2));
        double incertezamiEstaticoCilindroR = Math.sqrt(Math.pow((medioL2CilindroR/Math.pow(L1,2))*incertezaL1,2)+Math.pow(incertezaL2CilindroR/L1,2));

        System.out.printf("Seu valor de mi estático para o disco menor é: %.2f +/- %.2f \n", miEstaticoDiscoR, incertezamiEstaticoDiscoR);
        System.out.println();
        System.out.printf("Seu valor de mi estático para o disco maior é: %.2f +/- %.2f \n", miEstaticoDisco2R, incertezamiEstaticoDisco2R);
        System.out.println();
        System.out.printf("Seu valor de mi estático para o cilindro com mesmo raio do disco menor é: %.2f +/- %.2f \n", miEstaticoCilindroR, incertezamiEstaticoCilindroR);
        System.out.println();


        double CompatibilidadeDRD2R = Math.abs(miEstaticoDiscoR - miEstaticoDisco2R)/Math.sqrt(Math.pow(incertezamiEstaticoDiscoR,2)+Math.pow(incertezamiEstaticoDisco2R,2));
        double CompatibilidadeDRCR = Math.abs(miEstaticoDiscoR - miEstaticoCilindroR)/Math.sqrt(Math.pow(incertezamiEstaticoDiscoR,2)+Math.pow(incertezamiEstaticoCilindroR,2));
        double CompatibilidadeCRD2R = Math.abs(miEstaticoDisco2R - miEstaticoCilindroR)/Math.sqrt(Math.pow(incertezamiEstaticoCilindroR,2)+Math.pow(incertezamiEstaticoDisco2R,2));

        if (CompatibilidadeDRD2R < 2.5 && CompatibilidadeCRD2R < 2.5 && CompatibilidadeDRCR < 2.5){
            System.out.println("Os valores são compátiveis e seu Valor K é: ");
            System.out.println("CompatibilidadeDRD2R: " + CompatibilidadeDRD2R);
            System.out.println("CompatibilidadeCRD2R: "+ CompatibilidadeCRD2R);
            System.out.println("CompatibilidadeDRCR: "+ CompatibilidadeDRCR);
        }
        else {
            System.out.println("Os Valores não são compátiveis e seu valor K é");
            System.out.println("CompatibilidadeDRD2R: " + CompatibilidadeDRD2R);
            System.out.println("CompatibilidadeCRD2R: "+ CompatibilidadeCRD2R);
            System.out.println("CompatibilidadeDRCR: "+ CompatibilidadeDRCR);
        }

    }
}

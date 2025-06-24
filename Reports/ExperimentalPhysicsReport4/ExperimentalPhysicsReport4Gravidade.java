package ExperimentalPhysicsReport4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Incertezas.IncertezaTipoA;
import Incertezas.IncertezaTipoB;

public class ExperimentalPhysicsReport4Gravidade {
    public static void main(String[] args) {
        String caminhoArquivo = "CSV/Csv Experimento 4 (substitua pelo seu)";

        List<Double> coluna1 = new ArrayList<>();
        List<Double> coluna2 = new ArrayList<>();
        List<Double> coluna3 = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(";");

                if (partes.length >= 2) {
                    double Pendulo1 = Double.parseDouble(partes[0].replace(",", ".").trim());
                    double Pendulo2 = Double.parseDouble(partes[1].replace(",", ".").trim());
                    double Pendulo3 = Double.parseDouble(partes[2].replace(",", ".").trim());

                    coluna1.add(Pendulo1);
                    coluna2.add(Pendulo2);
                    coluna3.add(Pendulo3);
                }
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro de leitura: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        System.out.println();

        // Converter para arrays

        double[] tempoPenduloAltura1 = coluna1.stream().mapToDouble(Double :: doubleValue ).toArray();
        double[] tempoPenduloAltura2 = coluna2.stream().mapToDouble(Double :: doubleValue ).toArray();
        double[] tempoPenduloAltura3 = coluna2.stream().mapToDouble(Double :: doubleValue ).toArray();

        for (int i = 0; i < tempoPenduloAltura1.length; i++) {
            tempoPenduloAltura1[i] = tempoPenduloAltura1[i]/10;
            tempoPenduloAltura2[i] = tempoPenduloAltura2[i]/10;
            tempoPenduloAltura3[i] = tempoPenduloAltura3[i]/10;

        }

        /**
         *
         * Valores importantes.
         *
         */

        double Altura1   = 1.11; // em metros
        double Altura2 = 1.29; // em metros
        double Altura3 = 1.49; // em metros
        double resolucaoAltura = 0.004; // em metros
        double resolucaoTempo = 0.01  ; // em segundos


        double mediaTempoAltura1 = IncertezaTipoA.calcularMedia(tempoPenduloAltura1);
        double mediaTempoAltura2 = IncertezaTipoA.calcularMedia(tempoPenduloAltura2);
        double mediaTempoAltura3 = IncertezaTipoA.calcularMedia(tempoPenduloAltura3);



        double gravidadeAltura1 = 4*Math.pow(Math.PI,2)*(Altura1/Math.pow(mediaTempoAltura1,2));
        double gravidadeAltura2 = 4*Math.pow(Math.PI,2)*(Altura2/Math.pow(mediaTempoAltura2,2));
        double gravidadeAltura3 = 4*Math.pow(Math.PI,2)*(Altura3/Math.pow(mediaTempoAltura3,2));

        /**
         *
         * Calculando incertezas
         */

        double incertezaTempo1 = IncertezaTipoA.calcularIncertezaPadrao(tempoPenduloAltura1);
        double incertezaTempo2 = IncertezaTipoA.calcularIncertezaPadrao(tempoPenduloAltura2);
        double incertezaTempo3 = IncertezaTipoA.calcularIncertezaPadrao(tempoPenduloAltura3);

        double incertezaAltura = IncertezaTipoB.calcularIncertezaPadrao(resolucaoAltura);

        System.out.printf("Seu valor medio para Tempo 1 é: %.2f +/- %.3f \n", mediaTempoAltura1,incertezaTempo1);
        System.out.printf("Seu valor medio para Tempo 2 é: %.2f +/- %.3f \n", mediaTempoAltura2,incertezaTempo2);
        System.out.printf("Seu valor medio para Tempo 3 é: %.2f +/- %.3f \n", mediaTempoAltura3,incertezaTempo3);

        double incertezaGravidade1 = gravidadeAltura1*Math.sqrt(Math.pow((incertezaAltura/Altura1),2)+2*Math.pow((incertezaTempo1/mediaTempoAltura1),2));
        double incertezaGravidade2 = gravidadeAltura2*Math.sqrt(Math.pow((incertezaAltura/Altura2),2)+2*Math.pow((incertezaTempo2/mediaTempoAltura2),2));
        double incertezaGravidade3 = gravidadeAltura3*Math.sqrt(Math.pow((incertezaAltura/Altura3),2)+2*Math.pow((incertezaTempo3/mediaTempoAltura3),2));

        System.out.println();

        System.out.printf("Seu valor média para a grávidade Para a Altura 1 é: (%.2f +/- %.2f)m/s² \n",gravidadeAltura1,incertezaGravidade1);
        System.out.printf("Seu valor média para a grávidade Para a Altura 2 é: (%.2f +/- %.2f)m/s² \n",gravidadeAltura2,incertezaGravidade2);
        System.out.printf("Seu valor média para a grávidade Para a Altura 3 é: (%.2f +/- %.2f)m/s² \n",gravidadeAltura3,incertezaGravidade3);

    }
}

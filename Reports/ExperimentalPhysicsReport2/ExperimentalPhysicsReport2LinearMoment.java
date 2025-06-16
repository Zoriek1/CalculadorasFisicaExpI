package ExperimentalPhysicsReport2;
import Incertezas.IncertezaTipoA;
import Incertezas.IncertezaTipoB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentalPhysicsReport2LinearMoment {
    public static void main(String[] args) {

        String caminhoArquivo = "CSV/Csv Experimento 2 (substitua pelo seu)";
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

        double[] DeltaX1 = coluna1.stream().mapToDouble(Double::doubleValue).toArray();
        double[] DeltaLinhaX1 = coluna2.stream().mapToDouble(Double::doubleValue).toArray();
        double[] DeltaLinhaX2 = coluna3.stream().mapToDouble(Double::doubleValue).toArray();

        double massa1 = 66.1;
        double massa2 = 16.5;

        double resolucaoBalanca = 0.1; // em gramas
        double resolucaoRegua = 0.1; // em cm

        double incertezaBalanca = IncertezaTipoB.calcularIncertezaPadrao(resolucaoBalanca);

        double DeltaX1Medio = IncertezaTipoA.calcularMedia(DeltaX1);
        double IncertezaDeltaX1 = IncertezaTipoA.calcularIncertezaPadrao(DeltaX1);

        // Printa Delta X Experimental.

        System.out.printf("O valor de Delta X experimental é: %.2f +/- %.2f \n", DeltaX1Medio, IncertezaDeltaX1);


        // DeltaLinhaX e DeltaLinhaX2

        Double DeltaLinhaX1Medio = IncertezaTipoA.calcularMedia(DeltaLinhaX1);
        Double DeltaLinhaX1incerteza = IncertezaTipoA.calcularIncertezaPadrao(DeltaLinhaX1);
        Double DeltaLinhaX2Medio = IncertezaTipoA.calcularMedia(DeltaLinhaX2);
        Double DeltaLinhaX2incerteza = IncertezaTipoA.calcularIncertezaPadrao(DeltaLinhaX2);

        System.out.println();
        System.out.printf("O valor de Delta X1 linha é: %.2f +/- %.2f \n", DeltaLinhaX1Medio, DeltaLinhaX1incerteza);
        System.out.println();
        System.out.printf("O valor de Delta X1 linha é: %.2f +/- %.2f \n", DeltaLinhaX2Medio, DeltaLinhaX2incerteza);


        // Calculando DeltaX teorico

        double DeltaXTeorico = DeltaLinhaX1Medio + (massa2 / massa1)*DeltaLinhaX2Medio;

        double IncertezaDeltaXTeorico = Math.sqrt(Math.pow(DeltaLinhaX1incerteza, 2)+Math.pow((massa2/massa1)*DeltaLinhaX2incerteza, 2)+Math.pow((massa2/Math.pow(massa1,2))*incertezaBalanca, 2)+Math.pow((DeltaLinhaX2Medio/massa1)*incertezaBalanca, 2));
        System.out.println();
        System.out.printf("O valor de Delta X1 linha é: %.2f +/- %.2f \n", DeltaXTeorico, IncertezaDeltaXTeorico);


        double CompatibilidadeDX1DXT1 = Math.abs(DeltaX1Medio - DeltaXTeorico)/Math.sqrt(Math.pow(IncertezaDeltaX1,2)+Math.pow(IncertezaDeltaXTeorico,2));

        System.out.println();
        if (CompatibilidadeDX1DXT1 < 2.5){
            System.out.println("Os valor é compátivel e seu Valor K é: ");
            System.out.println("Compatibilidade: " + CompatibilidadeDX1DXT1);

        }
        else {
            System.out.println("Os valor não é compátivel e seu Valor K é: ");
            System.out.println("Compatibilidade: " + CompatibilidadeDX1DXT1);
        }

    }
}

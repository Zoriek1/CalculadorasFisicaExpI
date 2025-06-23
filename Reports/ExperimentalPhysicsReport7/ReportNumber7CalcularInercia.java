package ExperimentalPhysicsReport7;
import Incertezas.IncertezaTipoA;
import Incertezas.IncertezaTipoB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportNumber7CalcularInercia {

    public static void main(String[] args) {
        String caminhoArquivo = "CSV/Csv Experimento 7 (substitua pelo seu)";

        List<Double> coluna1 = new ArrayList<>();
        List<Double> coluna2 = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(";");

                if (partes.length >= 4) {
                    double Base = Double.parseDouble(partes[0].replace(",", ".").trim());
                    double BaseComDisco = Double.parseDouble(partes[2].replace(",", ".").trim());

                    coluna1.add(Base);
                    coluna2.add(BaseComDisco);
                }
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro de leitura: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        System.out.println();

        // Converter para arrays

        double ValoresBaseArray[] = coluna1.stream().mapToDouble(Double :: doubleValue ).toArray();
        double ValoresAnelArray[] = coluna2.stream().mapToDouble(Double :: doubleValue ).toArray();


        // Valores Importantes!

        double diametroInternoAnel = 0.11; // em metro
        double diametroExternoAnel = 0.125; // em metro
        double diametroDoDiscoDeFio = 0.03715; // onde se enrola o fio em metro
        double massaSuspensa = 0.060; // em kg
        double massaDoAnel = 0.699; // em kg
        double massaDaBase = 1.865; // em kg
        double gravidade = 9.782028; // m/s²
        double alturaH2 = 0.73; // em m

        double raioExternoAnel = diametroExternoAnel/2;
        double raioInternoAnel = diametroInternoAnel/2;
        double raioDoDiscoDoFio = diametroDoDiscoDeFio/2;



        // Cáculos:

        double mediaEmSegundosBase = IncertezaTipoA.calcularMedia(ValoresBaseArray);
        double mediaEmSegundosAnel = IncertezaTipoA.calcularMedia(ValoresAnelArray);


        double inerciaExperimental = (massaSuspensa*Math.pow(raioDoDiscoDoFio,2)/(2*alturaH2)*(Math.pow(mediaEmSegundosAnel,2)-Math.pow(mediaEmSegundosBase,2)));
        System.out.println(inerciaExperimental);

    }
}
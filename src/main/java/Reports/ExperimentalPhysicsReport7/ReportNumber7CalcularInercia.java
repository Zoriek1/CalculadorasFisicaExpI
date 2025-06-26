package Reports.ExperimentalPhysicsReport7;
import Incertezas.IncertezaTipoA;
import Incertezas.IncertezaTipoB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportNumber7CalcularInercia {

    public static void executar(String caminhoArquivo) {


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

        double[] ValoresBaseArray = coluna1.stream().mapToDouble(Double :: doubleValue ).toArray();
        double[] ValoresAnelArray = coluna2.stream().mapToDouble(Double :: doubleValue ).toArray();


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


        /*
         * Calculando Inercia Experimental e a prevista para o anel.
         */

        double mediaEmSegundosBase = IncertezaTipoA.calcularMedia(ValoresBaseArray);
        double mediaEmSegundosAnel = IncertezaTipoA.calcularMedia(ValoresAnelArray);


        double inerciaExperimental = (massaSuspensa*Math.pow(raioDoDiscoDoFio,2)/(2*alturaH2)*(Math.pow(mediaEmSegundosAnel,2)-Math.pow(mediaEmSegundosBase,2)));
        double inerciaAnel = (massaDoAnel*(Math.pow(raioInternoAnel,2)+Math.pow(raioExternoAnel,2)))/2;


        /**
         * Calculando incerteza de cada variavel medida.
         * 1° Declarando variaveis (resoluções)
         **/

        double resolucaoRegua1Medida = 0.001; // em metro
        double resolucaoPaquimetro = 0.00005; // em metro
        double resolucaoCronometro = 0.01; // em segundo
        double resolucaoTrena = 0.005; // em metro
        double resolucaoBalanca = 0.001; // em kilograma

        /**
         * Calculando incerteza de cada variavel medida.
         * 2° Calculando a incerteza
         **/

        double incertezaRegua1Medida = IncertezaTipoB.calcularIncertezaPadrao(resolucaoRegua1Medida);
        double incertezaPaquimetro = IncertezaTipoB.calcularIncertezaPadrao(resolucaoPaquimetro);
        double incertezaCronometro = IncertezaTipoB.calcularIncertezaPadrao(resolucaoCronometro);
        double incertezaTrena = IncertezaTipoB.calcularIncertezaPadrao(resolucaoTrena);
        double incertezabalanca = IncertezaTipoB.calcularIncertezaPadrao(resolucaoBalanca);
        double incertezaGravidade = 0.000023;


        System.out.printf("A incerteza para valores medidos na régua: %.5f metro \n", incertezaRegua1Medida);
        System.out.printf("A incerteza para valores medidos na paquimêtro: %.6f metro\n", incertezaPaquimetro);
        System.out.printf("A incerteza para valores medidos na Cronômetro: %.4f segundos \n", incertezaCronometro);
        System.out.printf("A incerteza para valores medidos na trêna: %.4f metro \n", incertezaTrena);
        System.out.printf("A incerteza para valores medidos na balança: %.4f metro \n", incertezabalanca);


        double incertezaCombinadaIncerciaTeoricaAnel = inerciaAnel*Math.sqrt((Math.pow((incertezabalanca/2*massaDoAnel),2)+Math.pow((incertezaRegua1Medida/raioExternoAnel),2)+Math.pow((incertezaRegua1Medida/raioInternoAnel),2)));

        System.out.printf("A sua inércia Teórica é: (%.2f +/- %.2f)* 10⁻³m²*Kg \n", (inerciaAnel*1000),(incertezaCombinadaIncerciaTeoricaAnel*1000));

        double incertezaCombinadaExperimental = inerciaExperimental*Math.sqrt(Math.pow(incertezaGravidade/gravidade,2)+Math.pow(incertezabalanca/massaSuspensa,2)+Math.pow(incertezaTrena/alturaH2,2)+4*Math.pow(incertezaPaquimetro/raioDoDiscoDoFio,2)+4*(Math.pow(mediaEmSegundosAnel,2)*Math.pow(incertezaCronometro,2)+Math.pow(mediaEmSegundosBase,2)*Math.pow(incertezaCronometro,2))/Math.pow(Math.pow(mediaEmSegundosAnel,2)-Math.pow(mediaEmSegundosBase,2),2));
        System.out.println();
        System.out.printf("A sua inércia Experimental é: (%.2f +/- %.2f)* 10⁻³m²*Kg \n", (inerciaExperimental*1000),(incertezaCombinadaExperimental*1000));



    }
}
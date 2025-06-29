package Reports.ExperimentalPhysicsReport1;

import Incertezas.IncertezaTipoB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentalPhysicsReport1Density {


    public static void executar(String caminhoArquivo) {
        List<String> nomes = new ArrayList<>();
        List<String> descricoes = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<Double> resolucoes = new ArrayList<>();

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
                    String nome = partes[0].trim();
                    String descricao = partes[3].trim();
                    double valor = Double.parseDouble(partes[1].replace(",", ".").trim());
                    double resolutes = Double.parseDouble(partes[2].replace(",", ".").trim());

                    nomes.add(nome);
                    valores.add(valor);
                    resolucoes.add(resolutes);
                    descricoes.add(descricao);
                }
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro de leitura: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return;
        }

        System.out.println();

        // Converter para arrays
        String[] nomesArray = nomes.toArray(new String[0]);
        String[] DescricoesArray = descricoes.toArray(new String[0]);
        double[] GrandezaArray = valores.stream().mapToDouble(Double::doubleValue).toArray();
        double[] resolucoesArray = resolucoes.stream().mapToDouble(Double::doubleValue).toArray();

        // Calculando p_1
        double VolumeBecker = GrandezaArray[1];
        double massaCilindro = GrandezaArray[0];
        double Densiade1 = massaCilindro / VolumeBecker;

        double resolucaoBecker = resolucoesArray[1];
        double resolucaoMassaCilindro = resolucoesArray[0];

        double incertezaBecker = IncertezaTipoB.calcularIncertezaPadrao(resolucaoBecker);
        double incertezaMassaCilindro = IncertezaTipoB.calcularIncertezaPadrao(resolucaoMassaCilindro);

        double incertezaCombinadaDensidade1 = Densiade1 * Math.sqrt(
                Math.pow(incertezaBecker / VolumeBecker, 2) +
                        Math.pow(incertezaMassaCilindro / massaCilindro, 2)
        );

        System.out.printf("Densidade_1 = %.2f +/- %.2f g/cm^3 \n", Densiade1, incertezaCombinadaDensidade1);

        // p_2
        double AlturaFitaMetricaCilindro = GrandezaArray[4] / 10;
        double CircunerenciaFitaMetricaCilindro = GrandezaArray[8] / 10;
        double densidade2 = (4 * Math.PI) * massaCilindro /
                (Math.pow(CircunerenciaFitaMetricaCilindro, 2) * AlturaFitaMetricaCilindro);

        double resolucaoFitaMetrica = resolucoesArray[7] / 10;
        double incertezaFitaMetrica = IncertezaTipoB.calcularIncertezaPadrao(resolucaoFitaMetrica);

        double incertezaCombinadaDensidade2 = densidade2 * Math.sqrt(
                2 * Math.pow(incertezaFitaMetrica / CircunerenciaFitaMetricaCilindro, 2) +
                        Math.pow(incertezaMassaCilindro / massaCilindro, 2) +
                        Math.pow(incertezaFitaMetrica / AlturaFitaMetricaCilindro, 2)
        );

        System.out.printf("Densidade_2 = %.2f +/- %.2f g/cm^3 \n", densidade2, incertezaCombinadaDensidade2);

        // p_3
        double alturaCilindroPaquimetro = GrandezaArray[2] / 10;
        double diametroCilindroPaquimetro = GrandezaArray[5] / 10;
        double densidade3 = (4 * massaCilindro) / (Math.PI * Math.pow(diametroCilindroPaquimetro, 2) * alturaCilindroPaquimetro);

        double resolucaoPaquimetro = resolucoesArray[5] / 10;
        double incertezaPaquimetro = IncertezaTipoB.calcularIncertezaPadrao(resolucaoPaquimetro);

        double incertezaCombinadaDensidade3 = densidade3 * Math.sqrt(
                2 * Math.pow(incertezaPaquimetro / diametroCilindroPaquimetro, 2) +
                        Math.pow(incertezaMassaCilindro / massaCilindro, 2) +
                        Math.pow(incertezaPaquimetro / alturaCilindroPaquimetro, 2)
        );

        System.out.printf("Densidade_3 = %.2f +/- %.2f g/cm^3 \n", densidade3, incertezaCombinadaDensidade3);

        System.out.println();

        // Teste de compatibilidade
        double CompatibilidadeP1P2 = Math.abs(Densiade1 - densidade2) /
                Math.sqrt(Math.pow(incertezaCombinadaDensidade1, 2) + Math.pow(incertezaCombinadaDensidade2, 2));
        double CompatibilidadeP1P3 = Math.abs(Densiade1 - densidade3) /
                Math.sqrt(Math.pow(incertezaCombinadaDensidade1, 2) + Math.pow(incertezaCombinadaDensidade3, 2));
        double CompatibilidadeP2P3 = Math.abs(densidade2 - densidade3) /
                Math.sqrt(Math.pow(incertezaCombinadaDensidade2, 2) + Math.pow(incertezaCombinadaDensidade3, 2));

        if (CompatibilidadeP1P2 < 2.5 && CompatibilidadeP2P3 < 2.5 && CompatibilidadeP1P3 < 2.5) {
            System.out.println("Os valores são compatíveis e seu Valor K é:");
        } else {
            System.out.println("Os valores não são compatíveis e seu Valor K é:");
        }

        System.out.println("Compatibilidade Volume Becker vs Volume com Fita métrica: " + CompatibilidadeP1P2);
        System.out.println("Compatibilidade Volume Becker vs Volume com Paquímetro: " + CompatibilidadeP1P3);
        System.out.println("Compatibilidade Volume com Fita métrica vs Volume com Paquímetro: " + CompatibilidadeP2P3);

        System.out.println();
        System.out.println("==================== Dados lidos ====================");

        for (int i = 0; i < nomesArray.length; i++) {
            System.out.println("nome[" + i + "]: " + nomesArray[i]
                    + " | valor: " + GrandezaArray[i]
                    + " | resolução: " + resolucoesArray[i]
                    + " | descrição: " + DescricoesArray[i]);
        }

        System.out.println();
    }
}

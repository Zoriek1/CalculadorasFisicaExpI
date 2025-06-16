package ExperimentalPhysicsReport5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportNumber5CalculatorForConstantKCorrigida {
    public static void main(String[] args) {

        String caminhoArquivo = "CSV/Csv Experimento 6 (substitua pelo seu)";
        List<Double> coluna1 = new ArrayList<>();
        List<Double> coluna2 = new ArrayList<>();
        List<Double> coluna3 = new ArrayList<>();
        List<Double> coluna4 = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(";");

                if (valores.length > 3) {
                    try {
                        double valorColuna1 = Double.parseDouble(valores[1].replace(",", ".").trim());
                        double valorColuna2 = Double.parseDouble(valores[2].replace(",", ".").trim());
                        double valorColuna3 = Double.parseDouble(valores[3].replace(",", ".").trim());
                        double valorColuna4 = Double.parseDouble(valores[0].replace(",", ".").trim());

                        coluna1.add(valorColuna1);
                        coluna2.add(valorColuna2);
                        coluna3.add(valorColuna3);
                        coluna4.add(valorColuna4);
                    } catch (NumberFormatException e) {
                        // Ignorar linhas com erro de conversão
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] l1s = coluna1.stream().mapToDouble(Double::doubleValue).toArray();
        double[] l2s = coluna2.stream().mapToDouble(Double::doubleValue).toArray();
        double[] l3s = coluna3.stream().mapToDouble(Double::doubleValue).toArray();
        double[] massas = coluna4.stream().mapToDouble(Double::doubleValue).toArray();

        // Converter cm para m
        for (int i = 0; i < l1s.length; i++) {
            l1s[i] = l1s[i] / 100;
            l2s[i] = l2s[i] / 100;
            l3s[i] = l3s[i] / 100;
        }

        double l1_zero = 0.129;
        double l2_zero = 0.149;
        double l3_zero = 0.282;

        double gravidade = 9.782028;
        double[] peso = new double[massas.length];

        for (int i = 0; i < peso.length; i++) {
            massas[i] = massas[i] / 1000; // Converter g para kg
            peso[i] = massas[i] * gravidade;
        }

        double[] K1 = new double[l1s.length];
        double[] K2 = new double[l1s.length];
        double[] Ks = new double[l1s.length];

        // Preencher com NaN para evitar uso de valores não calculados
        K1[0] = Double.NaN;
        K2[0] = Double.NaN;
        Ks[0] = Double.NaN;

        for (int i = 1; i < l1s.length; i++) {
            K1[i] = peso[i] / (l1s[i] - l1_zero);
            K2[i] = peso[i] / (l2s[i] - l2_zero);
            Ks[i] = peso[i] / (l3s[i] - l3_zero);
        }

        // Incertezas instrumentais
        double resolucaoBalanca = 0.001; // kg
        double resolucaoRegua = 0.003; // metros
        double incertezaRegua = resolucaoRegua / Math.sqrt(3);
        double incertezaBalanca = resolucaoBalanca / Math.sqrt(3);
        double incertezaGravidade = 0.000023;

        // Calcular incertezas individuais para cada medição
        double[] incertezaK1 = new double[K1.length];
        double[] incertezaK2 = new double[K2.length];
        double[] incertezaKs = new double[Ks.length];

        // Preencher com NaN para evitar uso de valores não calculados
        incertezaK1[0] = Double.NaN;
        incertezaK2[0] = Double.NaN;
        incertezaKs[0] = Double.NaN;

        for (int i = 1; i < K1.length; i++) {
            double deltaX1 = l1s[i] - l1_zero;
            double deltaX2 = l2s[i] - l2_zero;
            double deltaX3 = l3s[i] - l3_zero;

            incertezaK1[i] = K1[i] * Math.sqrt(
                    Math.pow(incertezaBalanca / massas[i], 2) +
                            Math.pow(incertezaRegua / deltaX1, 2) +
                            Math.pow(incertezaGravidade / gravidade, 2)
            );

            incertezaK2[i] = K2[i] * Math.sqrt(
                    Math.pow(incertezaBalanca / massas[i], 2) +
                            Math.pow(incertezaRegua / deltaX2, 2) +
                            Math.pow(incertezaGravidade / gravidade, 2)
            );

            incertezaKs[i] = Ks[i] * Math.sqrt(
                    Math.pow(incertezaBalanca / massas[i], 2) +
                            Math.pow(incertezaRegua / deltaX3, 2) +
                            Math.pow(incertezaGravidade / gravidade, 2)
            );
        }

        // Calcular pesos para média ponderada
        double somaPesosK1 = 0;
        double[] pesosK1 = new double[incertezaK1.length];
        double somaPesosK2 = 0;
        double[] pesosK2 = new double[incertezaK1.length];
        double somaPesosKs = 0;
        double[] pesosKs = new double[incertezaK1.length];

        // Ignorar índice 0 (valores NaN)
        for (int i = 1; i < incertezaK1.length; i++) {
            pesosK1[i] = 1.0 / Math.pow(incertezaK1[i], 2);
            somaPesosK1 += pesosK1[i];

            pesosK2[i] = 1.0 / Math.pow(incertezaK2[i], 2);
            somaPesosK2 += pesosK2[i];

            pesosKs[i] = 1.0 / Math.pow(incertezaKs[i], 2);
            somaPesosKs += pesosKs[i];
        }

        // Calcular médias ponderadas
        double K1Ponderada = 0;
        double K2Ponderada = 0;
        double KsPonderada = 0;

        for (int i = 1; i < incertezaK1.length; i++) {
            K1Ponderada += (K1[i] * pesosK1[i]) / somaPesosK1;
            K2Ponderada += (K2[i] * pesosK2[i]) / somaPesosK2;
            KsPonderada += (Ks[i] * pesosKs[i]) / somaPesosKs;
        }

        // Calcular incertezas das médias ponderadas
        double incertezaK1Ponderada = 1.0 / Math.sqrt(somaPesosK1);
        double incertezaK2Ponderada = 1.0 / Math.sqrt(somaPesosK2);
        double incertezaKsPonderada = 1.0 / Math.sqrt(somaPesosKs);

        // CALCULAR KS PREVISTO CORRETAMENTE (CORREÇÃO PRINCIPAL)
        double KsPrevistoCorreto = (K1Ponderada * K2Ponderada) / (K1Ponderada + K2Ponderada);

        // Calcular derivadas para propagação de incerteza
        double derivKs_dK1 = Math.pow(K2Ponderada, 2) / Math.pow(K1Ponderada + K2Ponderada, 2);
        double derivKs_dK2 = Math.pow(K1Ponderada, 2) / Math.pow(K1Ponderada + K2Ponderada, 2);

        // Propagação de incerteza para KsPrevisto
        double incertezaKsPrevisto = Math.sqrt(
                Math.pow(derivKs_dK1 * incertezaK1Ponderada, 2) +
                        Math.pow(derivKs_dK2 * incertezaK2Ponderada, 2)
        );

        // Teste de compatibilidade
        double diferenca = Math.abs(KsPonderada - KsPrevistoCorreto);
        double incertezaCombinada = Math.sqrt(
                Math.pow(incertezaKsPonderada, 2) +
                        Math.pow(incertezaKsPrevisto, 2)
        );
        double testeCompatibilidade = diferenca / incertezaCombinada;

        // Exibir resultados
        System.out.println("\n===== RESULTADOS CORRIGIDOS =====");
        System.out.printf("K1 Ponderado: %.3f ± %.3f N/m%n", K1Ponderada, incertezaK1Ponderada);
        System.out.printf("K2 Ponderado: %.3f ± %.3f N/m%n", K2Ponderada, incertezaK2Ponderada);
        System.out.printf("Ks Experimental: %.3f ± %.3f N/m%n", KsPonderada, incertezaKsPonderada);
        System.out.printf("Ks Previsto: %.3f ± %.3f N/m%n", KsPrevistoCorreto, incertezaKsPrevisto);

        System.out.println("\n===== TESTE DE COMPATIBILIDADE =====");
        System.out.printf("Diferença: %.3f N/m%n", diferenca);
        System.out.printf("Incerteza combinada: %.3f N/m%n", incertezaCombinada);
        System.out.printf("Valor t: %.3f%n", testeCompatibilidade);

        if (testeCompatibilidade < 2.5) {
            System.out.println("Conclusão: Valores COMPATÍVEIS (dentro de 2.5σ)");
        } else {
            System.out.println("Conclusão: Valores INCOMPATÍVEIS (fora de 2.5σ)");
        }

        // Cálculo teórico para verificação
        double ksTeorico = 1.0 / ((1.0/K1Ponderada) + (1.0/K2Ponderada));
        System.out.printf("\nVerificação teórica: 1/(1/K1 + 1/K2) = %.3f N/m%n", ksTeorico);
    }
}
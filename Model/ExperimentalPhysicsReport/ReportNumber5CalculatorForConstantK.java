package ExperimentalPhysicsReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ReportNumber5CalculatorForConstantK {
        public static void main(String[] args) {

            String caminhoArquivo = "C:\\Users\\caioc\\Downloads\\Lab1_Exp_05_Const.Elastica.csv";
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


            for (int i = 0; i < l1s.length; i++) {
                l1s[i] = l1s[i] / 100;
                l2s[i] = l2s[i] / 100;
                l3s[i] = l3s[i] / 100;
            }

            double l1_zero = 0.129;
            double l2_zero = 0.149;
            double l3_zero = 0.282;

            double gravidade = 9.7820280000000000000;

            double[] peso = new double[massas.length];

            for (int i = 0; i < peso.length; i++) {
                massas[i] = massas[i] / 1000;
                peso[i] = massas[i] * gravidade;
            }


            double[] K1 = new double[l1s.length];
            double[] K2 = new double[l1s.length];
            double[] Ks = new double[l1s.length];
            double K1Medio = 0;
            double K2Medio = 0;
            double KsMedio = 0;

            for (int i = 1; i < l1s.length; i++) {
                K1[i] = peso[i] / (l1s[i] - l1_zero);
                K2[i] = peso[i] / (l2s[i] - l2_zero);
                Ks[i] = peso[i] / (l3s[i] - l3_zero);

            }

            for (int i = 0; i < K1.length; i++) {
                K1Medio += K1[i]/ ((K1.length)-1);
                K2Medio += K2[i]/ ((K2.length)-1);
                KsMedio += Ks[i]/ ((Ks.length)-1);
            }
            System.out.println("###### K1, K2 e Ks ######");

            for (int i = 1; i < l1s.length; i++) {
                System.out.println("K1(" + i + "): " + K1[i]);

            }
            System.out.println("K1 Médio: "+ K1Medio);

            System.out.println();
            for (int i = 1; i < l1s.length; i++) {
                System.out.println("K2(" + i + "): " + K2[i]);

            }
            System.out.println("K2 Médio: "+ K2Medio);

            System.out.println();
            for (int i = 1; i < l1s.length; i++) {
                System.out.println("Ks(" + i + "): " + Ks[i]);

            }
            System.out.println("Ks Médio: "+ KsMedio);

            System.out.println();

            double[] KsPrevisto = new double[Ks.length];

            for (int i = 1; i < Ks.length; i++) {
                KsPrevisto[i] = (K1[i]*K2[i])/(K1[i] + K2[i]);}


                for (int i = 1; i < l1s.length; i++) {
                System.out.println("KsExperimental(" + i + "): " + KsPrevisto[i]);
            }

            double KsPrevistoMedio = 0;

            for (int i = 1; i < KsPrevisto.length; i++) {
              KsPrevistoMedio += ((KsPrevisto[i])/((KsPrevisto.length)-1));
            }


            System.out.println("Ks Previsto Médio: " + KsPrevistoMedio);


            //finalizado o calculo de K, vamos para a incerteza!

            double resolucaoBalanca = 0.001; //(kg)
            double resolucaoRegua = 0.003; //(metros)

            double incertezaRegua = resolucaoRegua/Math.sqrt(3);
            double incertezaBalanca = resolucaoBalanca/Math.sqrt(3);
            double incertezaGravidade = 0.000023;

            // Calculando as incertezas de K

            double[] incertezaK1 = new double[K1.length];
            double[] incertezaK2 = new double[K2.length];
            double[] incertezaKs = new double[Ks.length];

            for (int i = 1; i < K1.length; i++) {
                incertezaK1[i] = K1[i]*Math.sqrt((Math.pow(incertezaBalanca / massas[i], 2)) + (Math.pow(incertezaRegua / l1s[i], 2)) + (Math.pow(incertezaGravidade / gravidade, 2)));
                incertezaK2[i] = K2[i]*Math.sqrt((Math.pow(incertezaBalanca / massas[i], 2)) + (Math.pow(incertezaRegua / l2s[i], 2)) + (Math.pow(incertezaGravidade / gravidade, 2)));
                incertezaKs[i] = Ks[i]*Math.sqrt((Math.pow(incertezaBalanca / massas[i], 2)) + (Math.pow(incertezaRegua / l3s[i], 2)) + (Math.pow(incertezaGravidade / gravidade, 2)));

            }

            /* Agora faremos uma média ponderada de cada Kn[i].
            Faremos da seguinte forma, w é o peso de cada que se da por 1/incerteza, faremos uma variavel
            "somaWheigts = 0; e faremos a soma de todos os pesos e um loop for que multiplica cada Kn[i]
             pelo seu peso respectivo.
             */

            double SomaWeigthsK1 = 0;
            double[] weigthsK1 = new double[incertezaK1.length];
            double SomaWeigthsK2 = 0;
            double[] weigthsK2 = new double[incertezaK1.length];
            double SomaWeigthsKs = 0;
            double[] weigthsKs = new double[incertezaK1.length];

            for (int i = 1; i < incertezaK1.length; i++) {
                weigthsK1[i] = Math.pow(1/incertezaK1[i],2);
                SomaWeigthsK1 += weigthsK1[i];
                weigthsK2[i] = Math.pow(1/incertezaK2[i],2);
                SomaWeigthsK2 += weigthsK2[i];
                weigthsKs[i] = Math.pow(1/incertezaKs[i],2);
                SomaWeigthsKs += weigthsKs[i];
            }

            double incertezaTotalK1 = (1/SomaWeigthsK1);
            double incertezaTotalK2 = (1/SomaWeigthsK2);
            double incertezaTotalKs = (1/SomaWeigthsKs);




            //Media ponderada.

            double K1Ponderada = 0;
            double K2Ponderada = 0;
            double KsPonderada = 0;

            for (int i = 1; i < incertezaK1.length; i++) {
                K1Ponderada += (K1[i]*weigthsK1[i])/SomaWeigthsK1;
                K2Ponderada += (K2[i]*weigthsK2[i])/SomaWeigthsK2;
                KsPonderada += (Ks[i]*weigthsKs[i])/SomaWeigthsKs;
            }
            double incertezaTotalKsPrevisto = Math.sqrt(Math.pow(incertezaTotalK1,2)+Math.pow(incertezaTotalK2,2));


            System.out.println();
            System.out.println("- (K1, K2, K3) média ponderada em relação a a cada um");
            System.out.println("K1, média Ponderada: "+K1Ponderada+ " +/- " + incertezaTotalK1);
            System.out.println("K2, média Ponderada: "+K2Ponderada+ " +/- " + incertezaTotalK2);
            System.out.println("Ks Experimental, média Ponderada: "+KsPonderada+ " +/- " + incertezaTotalKs);
            System.out.println("Ks Previsto, média Ponderada: "+KsPrevistoMedio+ " +/- " + incertezaTotalKsPrevisto);

            //Com a média ponderada podemos Fazer o teste de compátibilidade Ks (Previsto x Experimental)

            double testeDeCompatibilidade = Math.abs(KsPonderada - KsPrevistoMedio)/Math.sqrt(Math.pow(incertezaTotalKs,2)+Math.pow(incertezaTotalKsPrevisto,2));
            System.out.println();
            if (testeDeCompatibilidade < 2.5){
                System.out.println("Os valores são compátiveis e seu Valor K é: " + testeDeCompatibilidade);

            }
            else {
                System.out.println("Os Valores não são compátiveis e seu valor K é: "+ testeDeCompatibilidade);
            }
        }
        }


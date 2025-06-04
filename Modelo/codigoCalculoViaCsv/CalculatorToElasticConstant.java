package codigoCalculoViaCsv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CalculatorToElasticConstant {
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
            double[] pesos = coluna4.stream().mapToDouble(Double::doubleValue).toArray();


            for (int i = 0; i < l1s.length; i++) {
                l1s[i] = l1s[i]/100;
                l2s[i] = l2s[i]/100;
                l3s[i] = l3s[i]/100;
            }

            double l1_zero = 0.129;
            double l2_zero = 0.149;
            double l3_zero = 0.282;

            double gravidade = 9.7820280000000000000;

            double[] peso = new double[pesos.length];

            for (int i = 0; i < peso.length; i++) {
                pesos[i] = pesos[i]/1000;
                peso[i] = pesos[i]*gravidade;
                System.out.println(peso[i]);
            }


            double[] K1 = new double[l1s.length];
            double[] K2 = new double[l1s.length];
            double[] K3 = new double[l1s.length];

            for (int i=0;i<l1s.length;i++){
                K1[i] = peso[i]/(l1s[i]-l1_zero);
                K2[i] = peso[i]/(l2s[i]-l2_zero);
                K3[i] = peso[i]/(l3s[i]-l3_zero);
            }
            System.out.println("########### K1, K2 e K3 ###########");

            for (int i = 0; i < l1s.length; i++) {
                System.out.println("K1("+i+"): "+K1[i]);
            }
            System.out.println();
            for (int i = 0; i < l1s.length; i++) {
                System.out.println("K2("+i+"): "+K2[i]);
            }
            System.out.println();
            for (int i = 0; i < l1s.length; i++) {
                System.out.println("K3("+i+"): "+K3[i]);
            }
            System.out.println();

            double[] KsPrevisto = new double[K3.length];
            double[] KsReal = new double[K3.length];

            for (int i = 0; i < K3.length; i++) {
                KsPrevisto[i] = 1/K1[i] + 1/K2[i];
                KsReal[i] = 1/K3[i];
            }
            System.out.println("########### Ks Previsto e k_s Real ###########");
            System.out.println();

            for (int i = 0; i < l1s.length; i++) {
                System.out.println("KsExperimental("+i+"): "+KsPrevisto[i]);
            }
            System.out.println();
            for (int i = 0; i < l1s.length; i++) {
                System.out.println("KsReal("+i+"): "+KsReal[i]);
            }



        }
}

    package ExperimentalPhysicsReport6;

    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    public class ReportNumber6CalculatorForConstantGama {

        public static void main(String[] args) {

            String caminhoArquivo = "/home/caio-henrique/Downloads/Exp06_Conservacao_da_Energia.csv";
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
                e.printStackTrace();
            }

            double[] Deltax1 = coluna1.stream().mapToDouble(Double::doubleValue).toArray();
            double[] Deltax2 = coluna2.stream().mapToDouble(Double::doubleValue).toArray();
            double[] Deltax3 = coluna3.stream().mapToDouble(Double::doubleValue).toArray();


           double h21 = 80.0 ;
           double h22 = 100.0;
           double h23 = 120.0;
           double h1 = 12.90;

            double[] gama1 = new double[Deltax1.length];
            double[] gama2 = new double[Deltax2.length];
            double[] gama3 = new double[Deltax3.length];

            for (int i = 0; i < Deltax1.length; i++) {
                 gama1[i] = Math.pow(Deltax1[i],2)/(h21*h1);
                 gama2[i] = Math.pow(Deltax2[i],2)/(h22*h1);
                 gama3[i] = Math.pow(Deltax3[i],2)/(h23*h1);
            }

            //Delta x é incerteza do tipo a e h(1 e 2) é do tipo b.

            double resolucãoH2 = 0.4; //em cm
            double resolucãoH1 = 0.7; //em cm

            double incertezaH2 = resolucãoH2/Math.sqrt(3);
            double incertezaH1 = resolucãoH1/Math.sqrt(3);

            //media arritimética

            double Delta1Medio = 0;
            double Delta2Medio = 0;
            double Delta3Medio = 0;

            for (int i = 0; i < Deltax1.length; i++) {
                Delta1Medio += (Deltax1[i]/Deltax1.length);
                Delta2Medio += (Deltax2[i]/Deltax2.length);
                Delta3Medio += (Deltax3[i]/Deltax3.length);
            }

            double somaQuadrados1 = 0;
            double somaQuadrados2 = 0;
            double somaQuadrados3 = 0;

            for (int i = 0; i < Deltax1.length; i++) {
                somaQuadrados1 += Math.pow(Deltax1[i] - Delta1Medio, 2);
                somaQuadrados2 += Math.pow(Deltax2[i] - Delta2Medio, 2);
                somaQuadrados3 += Math.pow(Deltax3[i] - Delta3Medio, 2);
            }

            double desvioPadrao1 = Math.sqrt(somaQuadrados1 / (Deltax1.length - 1));
            double desvioPadrao2 = Math.sqrt(somaQuadrados2 / (Deltax2.length - 1));
            double desvioPadrao3 = Math.sqrt(somaQuadrados3 / (Deltax3.length - 1));


            double incertezaDeltaX1 = (desvioPadrao1/Math.sqrt(Deltax1.length));
            double incertezaDeltaX2 = (desvioPadrao2/Math.sqrt(Deltax2.length));
            double incertezaDeltaX3 = (desvioPadrao3/Math.sqrt(Deltax3.length));
//

            // gama médio

            double gamaMedio1 = 0;
            double gamaMedio2 = 0;
            double gamaMedio3 = 0;

            for (int i = 0; i < gama1.length; i++) {
                gamaMedio1 += gama1[i]/gama1.length;
                gamaMedio2 += gama2[i]/gama2.length;
                gamaMedio3 += gama3[i]/gama3.length;
            }



            //incerteza de gama.

            double incertezaGama1 = 0;
            double incertezaGama2 = 0;
            double incertezaGama3 = 0;

            incertezaGama1 = gamaMedio1*Math.sqrt(Math.pow(incertezaH1/h1,2)+2*(Math.pow(incertezaDeltaX1/Delta1Medio,2)) + Math.pow(incertezaH2/h21,2));
            incertezaGama2 = gamaMedio2*Math.sqrt(Math.pow(incertezaH1/h1,2)+2*(Math.pow(incertezaDeltaX2/Delta2Medio,2)) + Math.pow(incertezaH2/h22,2));
            incertezaGama3 = gamaMedio3*Math.sqrt(Math.pow(incertezaH1/h1,2)+2*(Math.pow(incertezaDeltaX3/Delta3Medio,2)) + Math.pow(incertezaH2/h23,2));


            System.out.printf("Gama em h21: (%.4f ± %.4f)cm⁻¹\n", gamaMedio1, incertezaGama1);
            System.out.printf("Gama em h22: (%.4f ± %.4f)cm⁻¹\n", gamaMedio2, incertezaGama2);
            System.out.printf("Gama em h23: (%.4f ± %.4f)cm⁻¹\n", gamaMedio3, incertezaGama3);



    }}
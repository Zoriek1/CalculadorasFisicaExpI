    package ExperimentalPhysicsReport6;
    import ExperimentalPhysicsReport6.LinearRegression.LinearRegression;
    import Incertezas.IncertezaTipoA;
    import Incertezas.IncertezaTipoB;

    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    public class ReportNumber6CalculatorForConstantGamaSimplificado {

        public static void main(String[] args) {

            String caminhoArquivo = "CSV/Csv Experimento 6 (substitua pelo seu)";
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
            double h1 = 12.9;
            double resolucaoH1 = 0.7; //cm
            double resolucaoH2 = 0.4; //cm
            double h21 = 80.0;
            double h22 = 100.0;
            double h23 = 120;

            //Incerteza tipo A Delta x
            double mediaDeltax1 = IncertezaTipoA.calcularMedia(Deltax1);
            double desvioAmostralDeltaX1 = IncertezaTipoA.calcularDesvioPadrao(Deltax1);
            double incertezaDeltax1 = IncertezaTipoA.calcularIncertezaPadrao(Deltax1);
            double mediaDeltax2 = IncertezaTipoA.calcularMedia(Deltax2);
            double desvioAmostralDeltaX2 = IncertezaTipoA.calcularDesvioPadrao(Deltax2);
            double incertezaDeltax2 = IncertezaTipoA.calcularIncertezaPadrao(Deltax2);
            double mediaDeltax3 = IncertezaTipoA.calcularMedia(Deltax3);
            double desvioAmostralDeltaX3 = IncertezaTipoA.calcularDesvioPadrao(Deltax3);
            double incertezaDeltax3 = IncertezaTipoA.calcularIncertezaPadrao(Deltax3);



            //Incerteza tipo B H1,H2
            double incertezaH1 = IncertezaTipoB.calcularIncertezaPadrao(resolucaoH1);
            double incertezaH2 = IncertezaTipoB.calcularIncertezaPadrao(resolucaoH2);

            //gama

            double[] gama1 = new double[Deltax1.length];
            double[] gama2 = new double[Deltax2.length];
            double[] gama3 = new double[Deltax3.length];

            for (int i = 0; i < Deltax1.length; i++) {
                gama1[i] = Math.pow(Deltax1[i],2)/(h21*h1);
                gama2[i] = Math.pow(Deltax2[i],2)/(h22*h1);
                gama3[i] = Math.pow(Deltax3[i],2)/(h23*h1);
            }


            //gama medio
            double gamaMedio1 = 0;
            double gamaMedio2 = 0;
            double gamaMedio3 = 0;

            for (int i = 0; i < gama1.length; i++) {
                gamaMedio1 += gama1[i];
                gamaMedio2 += gama2[i];
                gamaMedio3 += gama3[i];
            }

            gamaMedio1 /= gama1.length;
            gamaMedio2 /= gama2.length;
            gamaMedio3 /= gama3.length;



            double incertezaGama1 = gamaMedio1*Math.sqrt(Math.pow(incertezaH1/h1,2)+2*(Math.pow(incertezaDeltax1/mediaDeltax1,2)) + Math.pow(incertezaH2/h21,2));
            double incertezaGama2 = gamaMedio2*Math.sqrt(Math.pow(incertezaH1/h1,2)+2*(Math.pow(incertezaDeltax2/mediaDeltax2,2)) + Math.pow(incertezaH2/h22,2));
            double incertezaGama3 = gamaMedio3*Math.sqrt(Math.pow(incertezaH1/h1,2)+2*(Math.pow(incertezaDeltax3/mediaDeltax3,2)) + Math.pow(incertezaH2/h23,2));


            System.out.printf("Gama em h21: (%.4f ± %.4f)cm⁻¹\n", gamaMedio1, incertezaGama1);
            System.out.printf("Gama em h22: (%.4f ± %.4f)cm⁻¹\n", gamaMedio2, incertezaGama2);
            System.out.printf("Gama em h23: (%.4f ± %.4f)cm⁻¹\n", gamaMedio3, incertezaGama3);

            double CompatibilidadeG1G2 = Math.abs(gamaMedio1 - gamaMedio2)/Math.sqrt(Math.pow(incertezaGama1,2)+Math.pow(incertezaGama2,2));
            double CompatibilidadeG2G3 = Math.abs(gamaMedio2 - gamaMedio3)/Math.sqrt(Math.pow(incertezaGama2,2)+Math.pow(incertezaGama3,2));
            double CompatibilidadeG3G1 = Math.abs(gamaMedio3 - gamaMedio1)/Math.sqrt(Math.pow(incertezaGama3,2)+Math.pow(incertezaGama1,2));

            if (CompatibilidadeG1G2 < 2.5 && CompatibilidadeG2G3 < 2.5 && CompatibilidadeG3G1 < 2.5){
                System.out.println("Os valores são compátiveis e seu Valor K é: " + CompatibilidadeG1G2+ "  "+ CompatibilidadeG2G3 +" "+ CompatibilidadeG3G1);

            }
            else {
                System.out.println("Os Valores não são compátiveis e seu valor K é: "+ CompatibilidadeG1G2+ "  "+ CompatibilidadeG2G3 +" "+ CompatibilidadeG3G1);
            }



            System.out.println();
            System.out.println("X=Delta x (1) Y=Gama (1)");
            LinearRegression Ln1 = new LinearRegression(Deltax1,gama1);
            Ln1.calcularRegressao();
            Ln1.mostrarResultados();
            System.out.println();
            System.out.println("X=Delta x (2) Y=Gama (2)");
            LinearRegression Ln2 = new LinearRegression(Deltax2,(gama2));
            Ln2.calcularRegressao();
            Ln2.mostrarResultados();
            System.out.println();
            System.out.println("X=Delta x (3) Y=Gama (3)");
            LinearRegression Ln3 = new LinearRegression(Deltax3,gama3);
            Ln3.calcularRegressao();
            Ln3.mostrarResultados();


















    }}
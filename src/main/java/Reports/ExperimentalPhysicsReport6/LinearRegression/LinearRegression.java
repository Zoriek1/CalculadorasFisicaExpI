package ExperimentalPhysicsReport6.LinearRegression;

public class LinearRegression {
    private double[] x;
    private double[] y;
    private double inclinacao;
    private double interceptacao;
    private double rQuadrado;
    private double incertezaInclinacao;
    private double incertezaInterceptacao;

    public LinearRegression(double[] x, double[] y) {
        if (x == null || y == null || x.length != y.length || x.length < 2) {
            throw new IllegalArgumentException("Dados de entrada inválidos");
        }
        this.x = x.clone();
        this.y = y.clone();
    }

    public void calcularRegressao() {
        int n = x.length;

        // Cálculos básicos
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
        }

        double xMedia = sumX / n;
        double yMedia = sumY / n;

        // Cálculo dos coeficientes
        this.inclinacao = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        this.interceptacao = yMedia - this.inclinacao * xMedia;

        // Cálculo do R²
        double ssTot = 0, ssRes = 0;
        for (int i = 0; i < n; i++) {
            double yPredito = predict(x[i]);
            ssTot += Math.pow(y[i] - yMedia, 2);
            ssRes += Math.pow(y[i] - yPredito, 2);
        }
        this.rQuadrado = 1 - (ssRes / ssTot);

        // Cálculo das incertezas
        calcularIncertezas();
    }

    private void calcularIncertezas() {
        int n = x.length;
        double sumResiduosQuad = 0;

        for (int i = 0; i < n; i++) {
            double yPredito = predict(x[i]);
            sumResiduosQuad += Math.pow(y[i] - yPredito, 2);
        }

        double s = Math.sqrt(sumResiduosQuad / (n - 2)); // Desvio padrão dos resíduos
        double sumXQuad = 0, xMedia = 0;

        for (double xi : x) {
            xMedia += xi;
        }
        xMedia /= n;

        for (double xi : x) {
            sumXQuad += Math.pow(xi - xMedia, 2);
        }

        this.incertezaInclinacao = s / Math.sqrt(sumXQuad);
        this.incertezaInterceptacao = s * Math.sqrt(1.0/n + Math.pow(xMedia, 2)/sumXQuad);
    }

    public double predict(double x) {
        return interceptacao + inclinacao * x;
    }

    public void mostrarResultados() {
        System.out.println("=== Resultados da Regressão Linear ===");
        System.out.printf("Equação: y = %.4fx + %.4f%n", inclinacao, interceptacao);
        System.out.printf("Coeficiente de Determinação (R²): %.4f%n", rQuadrado);
        System.out.printf("Incerteza na Inclinação: ±%.4f%n", incertezaInclinacao);
        System.out.printf("Incerteza na Interceptação: ±%.4f%n", incertezaInterceptacao);
    }

    // Getters
    public double getInclinacao() {
        return inclinacao;
    }

    public double getInterceptacao() {
        return interceptacao;
    }

    public double getRQuadrado() {
        return rQuadrado;
    }

    public double getIncertezaInclinacao() {
        return incertezaInclinacao;
    }

    public double getIncertezaInterceptacao() {
        return incertezaInterceptacao;
    }
}
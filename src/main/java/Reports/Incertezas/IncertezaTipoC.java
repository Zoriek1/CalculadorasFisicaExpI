package Incertezas;

public class IncertezaTipoC {
    private final double incertezaTipoA;
    private final double incertezaTipoB1;
    private final double incertezaTipoB2;
    private double incertezaCombinada;
    private double incertezaExpandida;
    private double fatorAbrangencia;

    /**
     * Construtor para incerteza do Tipo C com 3 componentes
     * @param incertezaTipoA Incerteza do Tipo A
     * @param incertezaTipoB1 Primeira incerteza do Tipo B
     * @param incertezaTipoB2 Segunda incerteza do Tipo B
     */
    public IncertezaTipoC(double incertezaTipoA, double incertezaTipoB1, double incertezaTipoB2) {
        if (incertezaTipoA < 0 || incertezaTipoB1 < 0 || incertezaTipoB2 < 0) {
            throw new IllegalArgumentException("Todas as incertezas devem ser valores positivos");
        }

        this.incertezaTipoA = incertezaTipoA;
        this.incertezaTipoB1 = incertezaTipoB1;
        this.incertezaTipoB2 = incertezaTipoB2;
    }

    /**
     * Método estático para calcular a incerteza combinada com 3 componentes
     * @param incertezaA Incerteza do Tipo A
     * @param incertezaB1 Primeira incerteza do Tipo B
     * @param incertezaB2 Segunda incerteza do Tipo B
     * @return Incerteza combinada
     */
    public static double calcularIncertezaCombinada(double incertezaA, double incertezaB1, double incertezaB2) {
        if (incertezaA < 0 || incertezaB1 < 0 || incertezaB2 < 0) {
            throw new IllegalArgumentException("Todas as incertezas devem ser valores positivos");
        }
        return Math.sqrt(2*Math.pow(incertezaA, 2) + Math.pow(incertezaB1, 2) + Math.pow(incertezaB2, 2));
    }

    /**
     * Método estático para calcular a incerteza expandida
     * @param incertezaCombinada Incerteza combinada
     * @param fatorAbragência Fator de abrangência (k)
     * @return Incerteza expandida
     */
    public static double calcularIncertezaExpandida(double incertezaCombinada, double fatorAbragência) {
        if (incertezaCombinada < 0) {
            throw new IllegalArgumentException("A incerteza combinada deve ser positiva");
        }
        return incertezaCombinada * fatorAbragência;
    }

    /**
     * Calcula e armazena a incerteza combinada (método de instância)
     */
    public void calcularIncertezaCombinada() {
        this.incertezaCombinada = calcularIncertezaCombinada(
                this.incertezaTipoA,
                this.incertezaTipoB1,
                this.incertezaTipoB2
        );
    }

    /**
     * Calcula e armazena a incerteza expandida (método de instância)
     * @param fatorAbragência Fator de abrangência (k)
     */
    public void calcularIncertezaExpandida(double fatorAbragência) {
        if (Double.isNaN(incertezaCombinada)) {
            throw new IllegalStateException("A incerteza combinada deve ser calculada primeiro");
        }
        this.fatorAbrangencia = fatorAbragência;
        this.incertezaExpandida = calcularIncertezaExpandida(this.incertezaCombinada, fatorAbragência);
    }

    /**
     * Calcula tudo de uma vez (método de instância)
     * @param fatorAbragência Fator de abrangência (k)
     */
    public void calcularTudo(double fatorAbragência) {
        calcularIncertezaCombinada();
        calcularIncertezaExpandida(fatorAbragência);
    }

    // Getters
    public double getIncertezaTipoA() {
        return incertezaTipoA;
    }

    public double getIncertezaTipoB1() {
        return incertezaTipoB1;
    }

    public double getIncertezaTipoB2() {
        return incertezaTipoB2;
    }

    public double getIncertezaCombinada() {
        return incertezaCombinada;
    }

    public double getIncertezaExpandida() {
        return incertezaExpandida;
    }

    public double getFatorAbragência() {
        return fatorAbrangencia;
    }

    /**
     * Exibe os resultados no console
     */
    public void mostrarResultados() {
        System.out.println("=== Resultados Incerteza Tipo C ===");
        System.out.printf("Incerteza Tipo A: %.4f%n", incertezaTipoA);
        System.out.printf("Incerteza Tipo B1: %.4f%n", incertezaTipoB1);
        System.out.printf("Incerteza Tipo B2: %.4f%n", incertezaTipoB2);

        if (!Double.isNaN(incertezaCombinada)) {
            System.out.printf("Incerteza Combinada: %.4f%n", incertezaCombinada);
        } else {
            System.out.println("Incerteza Combinada: Não calculada");
        }

        if (!Double.isNaN(incertezaExpandida)) {
            System.out.printf("Incerteza Expandida (k=%.1f): %.4f%n", fatorAbrangencia, incertezaExpandida);
        } else {
            System.out.println("Incerteza Expandida: Não calculada");
        }
    }

    /**
     * Retorna uma string formatada com os resultados
     * @return String formatada
     */
    @Override
    public String toString() {
        return String.format(
                "IncertezaTipoC [A: %.4f, B1: %.4f, B2: %.4f, Combinada: %.4f, Expandida(k=%.1f): %.4f]",
                incertezaTipoA, incertezaTipoB1, incertezaTipoB2,
                incertezaCombinada, fatorAbrangencia, incertezaExpandida
        );
    }
}
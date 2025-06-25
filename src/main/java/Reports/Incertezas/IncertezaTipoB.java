package Incertezas;

public class IncertezaTipoB {
    private final double medida;
    private final double resolucao;
    private double incerteza;

    /**
     * Construtor para incerteza do Tipo B (distribuição retangular)
     * @param medida Valor da medição
     * @param resolucao Resolução do instrumento
     */
    public IncertezaTipoB(double medida, double resolucao) {
        if (resolucao <= 0) {
            throw new IllegalArgumentException("A resolução deve ser maior que zero");
        }

        this.medida = medida;
        this.resolucao = resolucao;
        // O cálculo não é mais feito automaticamente
    }

    /**
     * Método estático para calcular a incerteza padrão do Tipo B (distribuição retangular)
     * @param resolucao Resolução do instrumento
     * @return valor da incerteza padrão
     */
    public static double calcularIncertezaPadrao(double resolucao) {
        if (resolucao <= 0) {
            throw new IllegalArgumentException("A resolução deve ser maior que zero");
        }
        return resolucao / Math.sqrt(3);
    }

    /**
     * Calcula e armazena a incerteza padrão (método de instância)
     */
    public void calcularIncerteza() {
        this.incerteza = calcularIncertezaPadrao(this.resolucao);
    }

    /**
     * Exibe a incerteza calculada no console
     */
    public void mostrarIncerteza() {
        if (Double.isNaN(incerteza)) {
            System.out.println("A incerteza ainda não foi calculada. Chame calcularIncerteza() primeiro.");
            return;
        }
        System.out.printf("Incerteza padrão: %.4f%n", incerteza);
    }

    // Getters
    public double getMedida() {
        return medida;
    }

    public double getResolucao() {
        return resolucao;
    }

    public double getIncerteza() {
        return incerteza;
    }

    /**
     * Retorna uma string formatada com os resultados
     * @return String formatada
     */
    @Override
    public String toString() {
        return String.format("IncertezaTipoB [Medida: %.4f, Resolução: %.4f, Incerteza: %.4f]",
                medida, resolucao, incerteza);
    }
}
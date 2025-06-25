package Incertezas;

public class IncertezaTipoA {
    private double[] valores;
    private double media;
    private double desvioPadrao;
    private double incertezaPadrao;

    public IncertezaTipoA(double[] valores) {
        if (valores == null || valores.length == 0) {
            throw new IllegalArgumentException("Array de valores não pode ser nulo ou vazio");
        }
        this.valores = valores.clone();
        // Removemos o cálculo automático
    }

    // Métodos estáticos para cálculo direto
    public static double calcularMedia(double[] valores) {
        if (valores == null || valores.length == 0) {
            throw new IllegalArgumentException("Array de valores não pode ser nulo ou vazio");
        }

        double soma = 0;
        for (double valor : valores) {
            soma += valor;
        }
        return soma / valores.length;
    }

    public static double calcularDesvioPadrao(double[] valores) {
        if (valores == null || valores.length == 0) {
            throw new IllegalArgumentException("Array de valores não pode ser nulo ou vazio");
        }
        if (valores.length == 1) {
            return 0;
        }

        double media = calcularMedia(valores);
        double somaQuadradosDiferencas = 0;
        for (double valor : valores) {
            somaQuadradosDiferencas += Math.pow(valor - media, 2);
        }
        return Math.sqrt(somaQuadradosDiferencas / (valores.length - 1));
    }

    public static double calcularIncertezaPadrao(double[] valores) {
        if (valores == null || valores.length == 0) {
            throw new IllegalArgumentException("Array de valores não pode ser nulo ou vazio");
        }
        return calcularDesvioPadrao(valores) / Math.sqrt(valores.length);
    }

    // Métodos de instância que usam os valores armazenados
    public void calcularTudo() {
        this.media = calcularMedia(this.valores);
        this.desvioPadrao = calcularDesvioPadrao(this.valores);
        this.incertezaPadrao = calcularIncertezaPadrao(this.valores);
    }

    public void calcularMedia() {
        this.media = calcularMedia(this.valores);
    }

    public void calcularDesvioPadrao() {
        this.desvioPadrao = calcularDesvioPadrao(this.valores);
    }

    public void calcularIncertezaPadrao() {
        this.incertezaPadrao = calcularIncertezaPadrao(this.valores);
    }

    // Getters
    public double[] getValores() {
        return valores.clone();
    }

    public double getMedia() {
        return media;
    }

    public double getDesvioPadrao() {
        return desvioPadrao;
    }

    public double getIncertezaPadrao() {
        return incertezaPadrao;
    }

    // Método para exibir resultados
    public void mostrarResultados() {
        System.out.println("Média: " + media);
        System.out.println("Desvio Padrão: " + desvioPadrao);
        System.out.println("Incerteza Padrão: " + incertezaPadrao);
    }
}
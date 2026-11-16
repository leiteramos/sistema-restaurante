package restaurante;

public class Bebida extends ItemCardapio {

    private String tamanho;

    public Bebida(String nome, double preco, String tamanho) {
        super(nome, preco);
        this.tamanho = tamanho;
    }

    @Override
    public String getDescricao() {
        return nome + " - tamanho: " + tamanho;
    }
}

// teste
package restaurante;

public abstract class ItemCardapio {

    protected String nome;
    protected double preco;

    public ItemCardapio(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public abstract String getDescricao();

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}


// teste
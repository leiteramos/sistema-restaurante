package restaurante;

public class Sobremesa extends ItemCardapio {

    private boolean gelada;

    public Sobremesa(String nome, double preco, boolean gelada) {
        super(nome, preco);
        this.gelada = gelada;
    }

    @Override
    public String getDescricao() {
        return nome + (gelada ? " - sobremesa gelada" : " - sobremesa normal");
    }
}
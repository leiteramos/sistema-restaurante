package restaurante;

public class PratoPrincipal extends ItemCardapio {

    private String acompanhamento;

    public PratoPrincipal(String nome, double preco, String acompanhamento) {
        super(nome, preco);
        this.acompanhamento = acompanhamento;
    }

    @Override
    public String getDescricao() {
        return nome + " + acompanhamento: " + acompanhamento;
    }
}
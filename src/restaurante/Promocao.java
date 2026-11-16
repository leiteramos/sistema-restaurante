package restaurante;

public interface Promocao {

    double aplicarDesconto(ItemCardapio item, int quantidade);

    String getNomePromocao();
}
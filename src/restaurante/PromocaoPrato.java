package restaurante;

public class PromocaoPrato implements Promocao {

    @Override
    public double aplicarDesconto(ItemCardapio item, int quantidade) {

        if (item instanceof PratoPrincipal && quantidade >= 2) {
            return item.getPreco() * quantidade * 0.10;
        }

        return 0;
    }

    @Override
    public String getNomePromocao() {
        return "10% de desconto em 2 ou mais pratos";
    }
}
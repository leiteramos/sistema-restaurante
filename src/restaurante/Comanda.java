package restaurante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comanda {

    private int numero;
    private LocalDate data;
    private List<ItemPedido> itens;
    private boolean fechada;

    public Comanda(int numero) {
        this.numero = numero;
        this.data = LocalDate.now();
        this.itens = new ArrayList<>();
        this.fechada = false;
    }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        itens.add(new ItemPedido(item, quantidade));
    }

    public void removerItem(int indice) {
        if (indice >= 0 && indice < itens.size()) {
            itens.remove(indice);
        }
    }

    public double calcularSubtotal() {

        double total = 0;

        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }

        return total;
    }

    public double calcularDesconto(Promocao promocao) {

        double desconto = 0;

        for (ItemPedido item : itens) {

            desconto += promocao.aplicarDesconto(
                    item.getItem(),
                    item.getQuantidade()
            );
        }

        return desconto;
    }

    public double calcularTotal(Promocao promocao) {

        return calcularSubtotal() - calcularDesconto(promocao);
    }

    public double calcularTaxaServico(Promocao promocao) {

        return calcularTotal(promocao) * 0.10;
    }

    public double calcularTotalFinal(Promocao promocao) {

        return calcularTotal(promocao)
                + calcularTaxaServico(promocao);
    }

    public void fechar() {
        fechada = true;
    }

    public boolean isFechada() {
        return fechada;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getData() {
        return data;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
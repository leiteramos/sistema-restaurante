package restaurante;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private DefaultListModel<String> modeloLista;
    private JList<String> listaPedido;

    private Comanda comandaAtual;
    private Historico historico;
    private Promocao promocao;

    private List<ItemCardapio> cardapio;

    private int numeroComanda = 1;

    private DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaPrincipal() {

        historico = new Historico();

        promocao = new PromocaoPrato();

        cardapio = new ArrayList<>();

        cadastrarItensIniciais();

        comandaAtual = new Comanda(numeroComanda);

        setTitle("Sistema de Gerenciamento de Restaurante");

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        montarTela();
    }

    // =====================================================
    // CARDÁPIO INICIAL
    // =====================================================

    private void cadastrarItensIniciais() {

        cardapio.add(
                new PratoPrincipal(
                        "Hambúrguer",
                        25.00,
                        "Batata frita"
                )
        );

        cardapio.add(
                new PratoPrincipal(
                        "Pizza",
                        40.00,
                        "Queijo e orégano"
                )
        );

        cardapio.add(
                new Bebida(
                        "Refrigerante",
                        7.00,
                        "500 ml"
                )
        );

        cardapio.add(
                new Bebida(
                        "Suco",
                        8.00,
                        "500 ml"
                )
        );

        cardapio.add(
                new Sobremesa(
                        "Sorvete",
                        10.00,
                        true
                )
        );
    }

    // =====================================================
    // MONTAR INTERFACE
    // =====================================================

    private void montarTela() {

        JPanel painel = new JPanel(
                new BorderLayout(10, 10)
        );

        JLabel titulo = new JLabel(
                "SISTEMA DE GERENCIAMENTO DE RESTAURANTE",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        painel.add(
                titulo,
                BorderLayout.NORTH
        );

        modeloLista = new DefaultListModel<>();

        listaPedido = new JList<>(
                modeloLista
        );

        painel.add(
                new JScrollPane(listaPedido),
                BorderLayout.CENTER
        );

        JPanel botoes = new JPanel(
                new GridLayout(3, 3, 8, 8)
        );

        JButton btnAdicionar =
                new JButton("Adicionar Item");

        JButton btnRemover =
                new JButton("Remover Item");

        JButton btnTotal =
                new JButton("Ver Total");

        JButton btnFechar =
                new JButton("Fechar Comanda");

        JButton btnHistorico =
                new JButton("Histórico");

        JButton btnNova =
                new JButton("Nova Comanda");

        JButton btnCadastrar =
                new JButton("Cadastrar Cardápio");

        JButton btnGerenciar =
                new JButton("Gerenciar Cardápio");

        JButton btnData =
                new JButton("Histórico por Data");

        botoes.add(btnAdicionar);
        botoes.add(btnRemover);
        botoes.add(btnTotal);

        botoes.add(btnFechar);
        botoes.add(btnHistorico);
        botoes.add(btnNova);

        botoes.add(btnCadastrar);
        botoes.add(btnGerenciar);
        botoes.add(btnData);

        painel.add(
                botoes,
                BorderLayout.SOUTH
        );

        btnAdicionar.addActionListener(
                e -> adicionarItem()
        );

        btnRemover.addActionListener(
                e -> removerItem()
        );

        btnTotal.addActionListener(
                e -> mostrarTotal()
        );

        btnFechar.addActionListener(
                e -> fecharComanda()
        );

        btnHistorico.addActionListener(
                e -> mostrarHistorico()
        );

        btnNova.addActionListener(
                e -> novaComanda()
        );

        btnCadastrar.addActionListener(
                e -> cadastrarItemCardapio()
        );

        btnGerenciar.addActionListener(
                e -> gerenciarCardapio()
        );

        btnData.addActionListener(
                e -> consultarHistoricoPorData()
        );

        add(painel);
    }

    // =====================================================
    // ADICIONAR ITEM AO PEDIDO
    // =====================================================

    private void adicionarItem() {

        if (cardapio.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "O cardápio está vazio."
            );

            return;
        }

        String[] opcoes =
                new String[cardapio.size()];

        for (int i = 0; i < cardapio.size(); i++) {

            opcoes[i] =
                    cardapio.get(i).toString();
        }

        String escolha =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Escolha um item:",
                        "Cardápio",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

        if (escolha == null) {
            return;
        }

        int indice = -1;

        for (int i = 0; i < opcoes.length; i++) {

            if (opcoes[i].equals(escolha)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return;
        }

        int quantidade;

        try {

            quantidade = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            this,
                            "Digite a quantidade:"
                    )
            );

            if (quantidade <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "A quantidade deve ser maior que zero."
                );

                return;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantidade inválida."
            );

            return;
        }

        ItemCardapio item =
                cardapio.get(indice);

        comandaAtual.adicionarItem(
                item,
                quantidade
        );

        atualizarLista();
    }

    // =====================================================
    // REMOVER ITEM DO PEDIDO
    // =====================================================

    private void removerItem() {

        int indice =
                listaPedido.getSelectedIndex();

        if (indice == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um item para remover."
            );

            return;
        }

        comandaAtual.removerItem(indice);

        atualizarLista();
    }

    private void atualizarLista() {

        modeloLista.clear();

        for (ItemPedido item :
                comandaAtual.getItens()) {

            modeloLista.addElement(
                    item.toString()
            );
        }
    }

    // =====================================================
    // TOTAL
    // =====================================================

    private void mostrarTotal() {

        double subtotal =
                comandaAtual.calcularSubtotal();

        double desconto =
                comandaAtual.calcularDesconto(
                        promocao
                );

        double taxa =
                comandaAtual.calcularTaxaServico(
                        promocao
                );

        double total =
                comandaAtual.calcularTotalFinal(
                        promocao
                );

        String mensagem =
                "COMANDA Nº "
                + comandaAtual.getNumero()
                + "\n\n"

                + "Subtotal: R$ "
                + String.format(
                        "%.2f",
                        subtotal
                )

                + "\nDesconto: R$ "
                + String.format(
                        "%.2f",
                        desconto
                )

                + "\nTaxa de serviço (10%): R$ "
                + String.format(
                        "%.2f",
                        taxa
                )

                + "\n\nTOTAL: R$ "
                + String.format(
                        "%.2f",
                        total
                );

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Total da Comanda",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // FECHAR COMANDA
    // =====================================================

    private void fecharComanda() {

        if (comandaAtual.getItens().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "A comanda está vazia."
            );

            return;
        }

        double total =
                comandaAtual.calcularTotalFinal(
                        promocao
                );

        comandaAtual.fechar();

        historico.adicionar(
                comandaAtual
        );

        StringBuilder recibo =
                new StringBuilder();

        recibo.append(
                "========== RECIBO ==========\n"
        );

        recibo.append(
                "Comanda: "
                + comandaAtual.getNumero()
                + "\n"
        );

        recibo.append(
                "Data: "
                + comandaAtual.getData()
                        .format(formatoData)
                + "\n\n"
        );

        for (ItemPedido item :
                comandaAtual.getItens()) {

            recibo.append(
                    item.toString()
                    + "\n"
            );
        }

        recibo.append(
                "\n-----------------------------\n"
        );

        recibo.append(
                "Desconto: R$ "
                + String.format(
                        "%.2f",
                        comandaAtual.calcularDesconto(
                                promocao
                        )
                )
                + "\n"
        );

        recibo.append(
                "Taxa de serviço: R$ "
                + String.format(
                        "%.2f",
                        comandaAtual.calcularTaxaServico(
                                promocao
                        )
                )
                + "\n"
        );

        recibo.append(
                "TOTAL: R$ "
                + String.format(
                        "%.2f",
                        total
                )
        );

        JOptionPane.showMessageDialog(
                this,
                recibo.toString(),
                "RECIBO",
                JOptionPane.INFORMATION_MESSAGE
        );

        novaComanda();
    }

    // =====================================================
    // NOVA COMANDA
    // =====================================================

    private void novaComanda() {

        numeroComanda++;

        comandaAtual =
                new Comanda(numeroComanda);

        atualizarLista();
    }

    // =====================================================
    // HISTÓRICO COMPLETO
    // =====================================================

    private void mostrarHistorico() {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "HISTÓRICO DE COMANDAS\n\n"
        );

        if (historico.getComandas().isEmpty()) {

            texto.append(
                    "Nenhuma comanda fechada."
            );

        } else {

            for (Comanda c :
                    historico.getComandas()) {

                double total =
                        c.calcularTotalFinal(
                                promocao
                        );

                texto.append(
                        "Comanda nº "
                        + c.getNumero()
                        + " | Data: "
                        + c.getData()
                                .format(formatoData)
                        + " | Total: R$ "
                        + String.format(
                                "%.2f",
                                total
                        )
                        + "\n"
                );
            }
        }

        JOptionPane.showMessageDialog(
                this,
                texto.toString(),
                "Histórico",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // HISTÓRICO POR DATA
    // =====================================================

    private void consultarHistoricoPorData() {

        String entrada =
                JOptionPane.showInputDialog(
                        this,
                        "Digite a data no formato:\n"
                        + "dd/MM/yyyy",
                        "Consulta por Data",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (entrada == null) {
            return;
        }

        LocalDate data;

        try {

            data = LocalDate.parse(
                    entrada,
                    formatoData
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data inválida.\n"
                    + "Use o formato dd/MM/yyyy."
            );

            return;
        }

        List<Comanda> resultado =
                historico.consultarPorData(data);

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "COMANDAS DO DIA "
                + entrada
                + "\n\n"
        );

        if (resultado.isEmpty()) {

            texto.append(
                    "Nenhuma comanda encontrada nessa data."
            );

        } else {

            for (Comanda c : resultado) {

                double total =
                        c.calcularTotalFinal(
                                promocao
                        );

                texto.append(
                        "Comanda nº "
                        + c.getNumero()
                        + " | Total: R$ "
                        + String.format(
                                "%.2f",
                                total
                        )
                        + "\n"
                );
            }
        }

        JOptionPane.showMessageDialog(
                this,
                texto.toString(),
                "Histórico por Data",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // CADASTRAR ITEM NO CARDÁPIO
    // =====================================================

    private void cadastrarItemCardapio() {

        String[] categorias = {
            "Prato Principal",
            "Bebida",
            "Sobremesa"
        };

        String categoria =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Escolha a categoria:",
                        "Cadastrar Item",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        categorias,
                        categorias[0]
                );

        if (categoria == null) {
            return;
        }

        String nome =
                JOptionPane.showInputDialog(
                        this,
                        "Nome do item:"
                );

        if (nome == null || nome.trim().isEmpty()) {
            return;
        }

        double preco;

        try {

            preco = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            this,
                            "Preço:"
                    ).replace(",", ".")
            );

            if (preco <= 0) {
                throw new NumberFormatException();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preço inválido."
            );

            return;
        }

        ItemCardapio novoItem;

        if (categoria.equals("Prato Principal")) {

            String acompanhamento =
                    JOptionPane.showInputDialog(
                            this,
                            "Acompanhamento:"
                    );

            novoItem =
                    new PratoPrincipal(
                            nome,
                            preco,
                            acompanhamento
                    );

        } else if (categoria.equals("Bebida")) {

            String tamanho =
                    JOptionPane.showInputDialog(
                            this,
                            "Tamanho da bebida:"
                    );

            novoItem =
                    new Bebida(
                            nome,
                            preco,
                            tamanho
                    );

        } else {

            int resposta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "A sobremesa é gelada?",
                            "Sobremesa",
                            JOptionPane.YES_NO_OPTION
                    );

            boolean gelada =
                    resposta == JOptionPane.YES_OPTION;

            novoItem =
                    new Sobremesa(
                            nome,
                            preco,
                            gelada
                    );
        }

        cardapio.add(novoItem);

        JOptionPane.showMessageDialog(
                this,
                "Item cadastrado com sucesso!"
        );
    }

    // =====================================================
    // GERENCIAR CARDÁPIO
    // =====================================================

    private void gerenciarCardapio() {

        String[] opcoes = {
            "Listar Cardápio",
            "Editar Item",
            "Remover Item"
        };

        String escolha =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Escolha uma opção:",
                        "Gerenciar Cardápio",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

        if (escolha == null) {
            return;
        }

        if (escolha.equals("Listar Cardápio")) {

            listarCardapio();

        } else if (escolha.equals("Editar Item")) {

            editarItemCardapio();

        } else {

            removerItemCardapio();
        }
    }

    // =====================================================
    // LISTAR CARDÁPIO
    // =====================================================

    private void listarCardapio() {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "========== CARDÁPIO ==========\n\n"
        );

        if (cardapio.isEmpty()) {

            texto.append(
                    "Nenhum item cadastrado."
            );

        } else {

            for (int i = 0; i < cardapio.size(); i++) {

                ItemCardapio item =
                        cardapio.get(i);

                texto.append(
                        (i + 1)
                        + " - "
                        + item.toString()
                        + "\n"
                );

                texto.append(
                        "    "
                        + item.getDescricao()
                        + "\n\n"
                );
            }
        }

        JOptionPane.showMessageDialog(
                this,
                texto.toString(),
                "Cardápio",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // EDITAR CARDÁPIO
    // =====================================================

    private void editarItemCardapio() {

        if (cardapio.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Não há itens cadastrados."
            );

            return;
        }

        String[] opcoes =
                new String[cardapio.size()];

        for (int i = 0; i < cardapio.size(); i++) {

            opcoes[i] =
                    cardapio.get(i).toString();
        }

        String escolha =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Escolha o item para editar:",
                        "Editar Cardápio",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

        if (escolha == null) {
            return;
        }

        int indice = -1;

        for (int i = 0; i < opcoes.length; i++) {

            if (opcoes[i].equals(escolha)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return;
        }

        ItemCardapio antigo =
                cardapio.get(indice);

        String novoNome =
                JOptionPane.showInputDialog(
                        this,
                        "Novo nome:",
                        antigo.getNome()
                );

        if (novoNome == null ||
                novoNome.trim().isEmpty()) {

            return;
        }

        double novoPreco;

        try {

            novoPreco = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            this,
                            "Novo preço:",
                            antigo.getPreco()
                    ).replace(",", ".")
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preço inválido."
            );

            return;
        }

        ItemCardapio novoItem;

        if (antigo instanceof PratoPrincipal) {

            String acompanhamento =
                    JOptionPane.showInputDialog(
                            this,
                            "Novo acompanhamento:"
                    );

            novoItem =
                    new PratoPrincipal(
                            novoNome,
                            novoPreco,
                            acompanhamento
                    );

        } else if (antigo instanceof Bebida) {

            String tamanho =
                    JOptionPane.showInputDialog(
                            this,
                            "Novo tamanho:"
                    );

            novoItem =
                    new Bebida(
                            novoNome,
                            novoPreco,
                            tamanho
                    );

        } else {

            int resposta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "A sobremesa é gelada?",
                            "Sobremesa",
                            JOptionPane.YES_NO_OPTION
                    );

            boolean gelada =
                    resposta == JOptionPane.YES_OPTION;

            novoItem =
                    new Sobremesa(
                            novoNome,
                            novoPreco,
                            gelada
                    );
        }

        cardapio.set(
                indice,
                novoItem
        );

        JOptionPane.showMessageDialog(
                this,
                "Item editado com sucesso!"
        );
    }

    // =====================================================
    // REMOVER ITEM DO CARDÁPIO
    // =====================================================

    private void removerItemCardapio() {

        if (cardapio.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Não há itens cadastrados."
            );

            return;
        }

        String[] opcoes =
                new String[cardapio.size()];

        for (int i = 0; i < cardapio.size(); i++) {

            opcoes[i] =
                    cardapio.get(i).toString();
        }

        String escolha =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Escolha o item para remover:",
                        "Remover do Cardápio",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

        if (escolha == null) {
            return;
        }

        int indice = -1;

        for (int i = 0; i < opcoes.length; i++) {

            if (opcoes[i].equals(escolha)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return;
        }

        int resposta =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente remover este item?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

        if (resposta ==
                JOptionPane.YES_OPTION) {

            cardapio.remove(indice);

            JOptionPane.showMessageDialog(
                    this,
                    "Item removido com sucesso!"
            );
        }
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaPrincipal tela =
                    new TelaPrincipal();

            tela.setVisible(true);
        });
    }
}
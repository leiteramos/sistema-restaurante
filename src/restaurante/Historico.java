package restaurante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Historico {

    private List<Comanda> comandas;

    public Historico() {
        comandas = new ArrayList<>();
    }

    public void adicionar(Comanda comanda) {
        comandas.add(comanda);
    }

    public List<Comanda> consultarPorData(LocalDate data) {

        List<Comanda> resultado = new ArrayList<>();

        for (Comanda c : comandas) {

            if (c.getData().equals(data)) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public List<Comanda> getComandas() {
        return comandas;
    }
}
package ConsultandoTabelaFIPE.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(String nome,
                    String codigo) {

    @Override
    public String toString() {
        return this.nome + " - Codigo: " + this.codigo;
    }
}

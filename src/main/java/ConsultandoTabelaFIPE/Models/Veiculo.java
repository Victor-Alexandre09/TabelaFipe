package ConsultandoTabelaFIPE.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Veiculo {
    @JsonProperty("Modelo") private String modelo ;
    @JsonProperty("AnoModelo") private String ano;
    @JsonProperty("Combustivel") private String combustivel;
    @JsonProperty("Valor") private String valor;

    @Override
    public String toString() {
        return "\n Modelo: " + this.modelo +
               "\n Ano: " + this.ano +
               "\n Combustivel: " + this.combustivel +
               "\n Valor: " + this.valor;
    }
}

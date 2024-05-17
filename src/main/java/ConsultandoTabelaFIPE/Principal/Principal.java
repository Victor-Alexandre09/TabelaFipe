package ConsultandoTabelaFIPE.Principal;
import ConsultandoTabelaFIPE.Models.Modelos;
import ConsultandoTabelaFIPE.Models.Dados;
import ConsultandoTabelaFIPE.Models.Veiculo;
import ConsultandoTabelaFIPE.Service.ConsumoApi;
import ConsultandoTabelaFIPE.Service.CoverteDados;
import ConsultandoTabelaFIPE.Service.IConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    ConsumoApi consumoApi = new ConsumoApi();
    IConverteDados converter = new CoverteDados();
    Scanner scanner = new Scanner(System.in);

    public void menu() throws JsonProcessingException {

        String tipoVeiculo = "";
        String opcao;
        do {
            System.out.println("""
                TABELA FIPE
                Escolha um tipo de veiculo:
                1 - Carros
                2 - Motos
                2 - Caminhoes""");

            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    tipoVeiculo = "carros";
                    break;
                case "2":
                    tipoVeiculo = "motos";
                    break;
                case "3":
                    tipoVeiculo = "caminhoes";
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (tipoVeiculo.isEmpty());

            String endereco = "https://parallelum.com.br/fipe/api/v1/" + tipoVeiculo + "/marcas";
        String json = consumoApi.Obterdados(endereco);
        List<Dados> listaMarcas = converter.obterLista(json, Dados.class);

        String codigoMarca = "";
        do {
            listaMarcas.forEach(System.out::println);
            System.out.println("Digite o codigo da marca do carro: ");
            var marcaCodigo = scanner.nextLine();
            Optional<Dados> marcaEscolhida = listaMarcas.stream()
                    .filter(c -> c.codigo().equalsIgnoreCase(marcaCodigo))
                    .findFirst();
            if (marcaEscolhida.isPresent()) {
                codigoMarca = marcaEscolhida.get().codigo();
            } else {
                System.out.println("Marca não encontrada para o código informado.");
            }
        } while (codigoMarca.isEmpty());

        endereco = "https://parallelum.com.br/fipe/api/v1/" + tipoVeiculo + "/marcas/" + codigoMarca + "/modelos";
        json = consumoApi.Obterdados(endereco);
        Modelos modelos = converter.obterDados(json, Modelos.class);

        modelos.listaModelos().forEach(System.out::println);

        List<Dados> modelosFiltrados;
        List<Dados> modeloEscolhido;
        boolean codigo;
        String codigoModelo = "";
        do {
            System.out.println("\nDigite um trecho do nome de um modelo para filtrar" +
                    " e insira o codigo para visualizar suas informacoes");
            var digito = scanner.nextLine();
            codigo = modelos.listaModelos().stream()
                    .anyMatch(dados -> dados.codigo().equalsIgnoreCase(digito));
            if (codigo) {
                codigoModelo = digito;
                break;
            } else {
                modelosFiltrados = modelos.listaModelos().stream()
                        .filter(veiculos -> veiculos.nome().toUpperCase().contains(digito.toUpperCase()))
                        .collect(Collectors.toList());
                if (!modelosFiltrados.isEmpty()) {
                    modelosFiltrados.forEach(System.out::println);
                } else {
                    System.out.println("Nenhum modelo encontrado com o nome informado.");
                }
            }
        } while (!codigo);

        endereco = "https://parallelum.com.br/fipe/api/v1/" + tipoVeiculo + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos";
        json = consumoApi.Obterdados(endereco);
        modeloEscolhido = converter.obterLista(json, Dados.class);

        System.out.println("Todos os veiculos filtrados com avaliacao por ano:");
        for (Dados item : modeloEscolhido) {
            String codigoAno = item.codigo();
            endereco = "https://parallelum.com.br/fipe/api/v1/" + tipoVeiculo + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/" + codigoAno;
            json = consumoApi.Obterdados(endereco);
            Veiculo veiculo = converter.obterDados(json, Veiculo.class);
            System.out.println(veiculo);
        }
    }
}   

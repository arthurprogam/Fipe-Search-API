package br.com.exercicio.tabelaFipe.principal;

import br.com.exercicio.tabelaFipe.model.Dados;
import br.com.exercicio.tabelaFipe.model.Modelos;
import br.com.exercicio.tabelaFipe.model.Veiculo;
import br.com.exercicio.tabelaFipe.service.ConsumoAPI;
import br.com.exercicio.tabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//Versão em Inglês (PRINCIPAL)
public class Principal {
    Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {

        var menu = """
                *** VEHICLE ***
                Car
                Motorcycle
                Truck
                
                Enter one of the options below:
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("car")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("tru")) {
            endereco = URL_BASE + "caminhoes/marcas";
        } else {
            endereco = URL_BASE + "motos/marcas";
        }

        var json = consumo.obterDados(endereco);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(d -> System.out.printf("%-6s | %s%n", d.codigo(), d.nome()));

        System.out.println("\nEnter the brand code you want to search: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModels for this brand:");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(d -> System.out.printf("%-8s | %s%n", d.codigo(), d.nome()));

        System.out.println("\nWhich vehicle are you looking for?");
        var nomeCarro = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeCarro.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nMatching vehicles:");
        modelosFiltrados.forEach(d -> System.out.printf("%-8s | %s%n", d.codigo(), d.nome()));

        System.out.println("\nEnter the code of the model you are interested in: ");
        var codigoCarro = leitura.nextLine();

        endereco = endereco + "/" + codigoCarro + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (Dados ano : anos) {
            var enderecosAnos = endereco + "/" + ano.codigo();
            json = consumo.obterDados(enderecosAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nFiltered vehicles:");
        veiculos.forEach(System.out::println);
    }
}

//Versão Português
//public class Principal {
//    Scanner leitura = new Scanner(System.in);
//    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
//    private ConsumoAPI consumo  = new ConsumoAPI();
//    private ConverteDados conversor = new ConverteDados();
//    public void exibeMenu() {
//
//        var menu = """
//                *** VEÍCULO ***
//                Carro
//                Moto
//                Caminhão
//
//                Digite uma das opções abaixo:
//                """;
//        System.out.println(menu);
//        var opcao = leitura.nextLine();
//        String endereco;
//        if(opcao.toLowerCase().contains("car")) {
//            endereco = URL_BASE + "carros/marcas";
//        } else if (opcao.toLowerCase().contains("cam")) {
//            endereco = URL_BASE + "caminhoes/marcas";
//        } else { endereco = URL_BASE + "motos/marcas";
//        }
//
//        var json = consumo.obterDados(endereco);
//        System.out.println(json);
//
//        var marcas = conversor.obterLista(json, Dados.class);
//        marcas.stream()
//                .sorted(Comparator.comparing(Dados::codigo))
//                .forEach(System.out::println);
//
//        System.out.println("Informe o código qual deseja consultar: ");
//        var codigoMarca = leitura.nextLine();
//
//        endereco = endereco + "/" + codigoMarca + "/modelos";
//        json = consumo.obterDados(endereco);
//        var modeloLista = conversor.obterDados(json, Modelos.class);
//
//        System.out.println("\nModelos dessa marca:");
//        modeloLista.modelos().stream()
//                .sorted(Comparator.comparing(Dados::codigo))
//                .forEach(System.out::println);
//
//        System.out.println("Qual carro deseja buscar?");
//        var nomeCarro = leitura.nextLine();
//
//        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
//                .filter( m -> m.nome().toLowerCase().contains(nomeCarro.toLowerCase()))
//                .collect(Collectors.toList());
//        System.out.println("\nCarros identificados: ");
//        modelosFiltrados.forEach(System.out::println);
//
//        System.out.println("\nDigite o código do modelo de seu interesse: ");
//        var codigoCarro = leitura.nextLine();
//
//        endereco = endereco + "/" + codigoCarro + "/anos";
//        json = consumo.obterDados(endereco);
//        List<Dados> anos = conversor.obterLista(json, Dados.class);
//
//        List<Veiculo> veiculos = new ArrayList<>();
//
//        for (int i = 0; i < anos.size(); i++) {
//            var enderecosAnos = endereco + "/" + anos.get(i).codigo();
//            json = consumo.obterDados(enderecosAnos);
//            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
//            veiculos.add(veiculo);
//
//        }
//
//        System.out.println("\n Veículos filtrados:");
//        veiculos.forEach(System.out::println);
//
//    }
//}

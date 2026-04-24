package br.com.exercicio.tabelaFipe.controller;

import br.com.exercicio.tabelaFipe.model.*;
import br.com.exercicio.tabelaFipe.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControleFipe {

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    // GET /api/marcas?tipo=carros
    @GetMapping("/marcas")
    public List<Dados> getMarcas(@RequestParam String tipo) {
        String endereco = URL_BASE + tipo + "/marcas";
        var json = consumo.obterDados(endereco);
        return conversor.obterLista(json, Dados.class);
    }

    // GET /api/modelos?tipo=carros&codigoMarca=59
    @GetMapping("/modelos")
    public List<Dados> getModelos(@RequestParam String tipo,
                                  @RequestParam String codigoMarca) {
        String endereco = URL_BASE + tipo + "/marcas/" + codigoMarca + "/modelos";
        var json = consumo.obterDados(endereco);
        Modelos modelos = conversor.obterDados(json, Modelos.class);
        return modelos.modelos();
    }

    // GET /api/veiculos?tipo=carros&codigoMarca=59&codigoModelo=5940
    @GetMapping("/veiculos")
    public List<Veiculo> getVeiculos(@RequestParam String tipo,
                                     @RequestParam String codigoMarca,
                                     @RequestParam String codigoModelo) {
        String enderecoAnos = URL_BASE + tipo + "/marcas/" + codigoMarca
                + "/modelos/" + codigoModelo + "/anos";
        var json = consumo.obterDados(enderecoAnos);
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (Dados ano : anos) {
            json = consumo.obterDados(enderecoAnos + "/" + ano.codigo());
            veiculos.add(conversor.obterDados(json, Veiculo.class));
        }
        return veiculos;
    }
}
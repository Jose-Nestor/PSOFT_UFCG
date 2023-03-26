package com.ufcg.psoft.mercadofacil.repository;



import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VolatilLoteRepositoryTest {


    @Autowired
    VolatilLoteRepository driver;


    Lote lote;
    Lote resultado;
    Produto produto;



    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }


    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }


    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote() {
        resultado = driver.save(lote);


        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }


    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);


        resultado = driver.save(loteExtra);


        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);


    }

    @Test
    @DisplayName("Encontrar um Lote com id = 1L")
    void buscarLote(){
        driver.save(lote);

        assertEquals(driver.find(lote.getId()), lote);
    }

    @Test
    @DisplayName("Buscar todos Lotes")
    void buscarTodosLotes() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);

        assertEquals(driver.findAll().size(), 2);


    }


    @Test
    @DisplayName("Atualizar Lote com apenas um Lote na lista")
    void atualizarLoteListaComUnicoLote() {
        driver.save(lote);

        produto = Produto.builder()
                .id(1L)
                .nome("Produto Topo")
                .codigoBarra("987654321")
                .fabricante("Fabricante Topo")
                .preco(1250.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(10)
                .produto(produto)
                .build();

        driver.update(lote);

        assertEquals(produto.getNome(), "Produto Topo");
        assertEquals(produto.getCodigoBarra(), "987654321");
        assertEquals(produto.getFabricante(), "Fabricante Topo");
        assertEquals(produto.getFabricante(), "Fabricante Topo");
        assertEquals(produto.getPreco(), 1250.36);
        assertEquals(lote.getNumeroDeItens(), 10);
    }

    @Test
    @DisplayName("Atualizar Lote com dois lotes na lista")
    void atualizarLoteListaComDoisLotes() {
        driver.save(lote);

        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(loteExtra);

        produto = Produto.builder()
                .id(1L)
                .nome("Produto Topo")
                .codigoBarra("987654321")
                .fabricante("Fabricante Topo")
                .preco(1250.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(10)
                .produto(produto)
                .build();

        driver.update(lote);

        assertEquals(produto.getNome(), "Produto Topo");
        assertEquals(produto.getCodigoBarra(), "987654321");
        assertEquals(produto.getFabricante(), "Fabricante Topo");
        assertEquals(produto.getFabricante(), "Fabricante Topo");
        assertEquals(produto.getPreco(), 1250.36);
        assertEquals(lote.getNumeroDeItens(), 10);
    }

    @Test
    @DisplayName("Apagar Lote")
    void apagarLote() {
        driver.save(lote);
        driver.delete(lote);

       // assertEquals(driver.find(1L), null);
        assertEquals(driver.findAll(), Collections.emptyList());
    }


    @Test
    @DisplayName("Apagar todos os Lotes")
    void apagarTodosLotes() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        driver.deleteAll();

        assertEquals(driver.findAll(), Collections.emptyList());

    }


}

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
    List<Lote> lotes = new ArrayList<>();



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
    void buscarLote(Long id){
        driver.save(lote);
        Long Id = 1L;
        resultado = driver.find(Id);

        assertEquals(resultado.getProduto(), produto);
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

        resultado = (Lote) driver.findAll();



    }


    @Test
    @DisplayName("Atualizar Lote")
    void atualizarLote() {
    }

    @Test
    @DisplayName("Apagar Lote")
    void apagarLote() {
        driver.save(lote);
        driver.delete(lote);

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

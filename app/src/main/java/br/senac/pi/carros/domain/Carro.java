package br.senac.pi.carros.domain;

/**
 * Created by Aluno on 06/11/2015.
 */
public class Carro {
    private long id;
    private String nome;
    private String marca;

    public Carro(){}

    public Carro(long id, String nome, String marca){
        this.id = id;
        this.nome = nome;
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

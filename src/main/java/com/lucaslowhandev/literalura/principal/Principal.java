package com.lucaslowhandev.literalura.principal;

import com.lucaslowhandev.literalura.dto.ApiResponseDTO;
import com.lucaslowhandev.literalura.dto.LivroDTO;
import com.lucaslowhandev.literalura.model.Autor;
import com.lucaslowhandev.literalura.model.Livro;
import com.lucaslowhandev.literalura.repository.AutorRepository;
import com.lucaslowhandev.literalura.repository.LivroRepository;
import com.lucaslowhandev.literalura.service.ConsumoApi;
import com.lucaslowhandev.literalura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Principal {
    private final String ENDERECO = "https://gutendex.com/books/?search";
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository){
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu(){
        var opcao = -1;
        while(opcao !=0) {
            var menu = """
                    1 - Buscar livro por titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1 :
                    buscarLivroPorTitulo();
                    break;
                case 2 :
                    listarLivrosRegistrados();
                    break;
                case 3 :
                    listarAutoresRegistrados();
                    break;
                case 4 :
                    listarAutoresVivosPorAno();
                    break;
                case 5 :
                    listarLivrosPorIdioma();
                    break;
                case 0 :
                    System.out.println("Saindo...");
                    break;
                default :
                    System.out.println("Opção incorreta.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        Livro livro = getDadosLivro();

        if (livro == null) {
            System.out.println("Não foi possível obter dados deste livro.");
            return;
        }

//        if (livroRegistrado(livro.getTitulo())) {
//            System.out.println("O livro pesquisado já está cadastrado.");
//            return;
//        }

        Autor autor = livro.getAutor();
        Autor autorNoBanco = getAutorNoBanco(autor.getNome());

        if (autorNoBanco == null) {
            autorRepository.save(autor);
        } else {
            livro.setAutor(autorNoBanco);
        }

        livroRepository.save(livro);
        System.out.println("Livro salvo com sucesso:\n" + livro);
    }

    private Livro getDadosLivro() {
        System.out.println("Insira o nome do livro que você deseja procurar: ");
        var nomeLivro = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeLivro.toLowerCase().replace(" ", "%20"));
        ApiResponseDTO apiResponse = conversor.obterDados(json, ApiResponseDTO.class);

        if (apiResponse == null || apiResponse.livroData() == null) {
            System.out.println("Nenhuma resposta da API.");
            return null;
        }

        Optional<LivroDTO> livroBuscado = apiResponse.livroData().stream().findFirst();

        return livroBuscado.map(Livro::new).orElse(null);
    }

    private boolean livroRegistrado(String tituloLivro) {
        return livroRepository.findByTituloIgnoreCase(tituloLivro).isPresent();
    }

    private Autor getAutorNoBanco(String nomeAutor) {
        if (nomeAutor == null || nomeAutor.isBlank()) return null;
        return autorRepository.findByNomeContainingIgnoreCase(nomeAutor).orElse(null);
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();

        if(livros.isEmpty()){
            System.out.println("Nenhum livro foi registrado até o momento.");
        }else{
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if(autores.isEmpty()){
            System.out.println("Nenum autor foi registrado até o momento.");
        }else {
            autores.forEach(a -> {
                Set<Livro> autorLivros = livroRepository.findByAutor(a);
                a.setLivros(autorLivros);
                System.out.println(a);
            });
        }
    }


    private void listarAutoresVivosPorAno() {
        int ano = 0;

        while(ano <=0 ){
            System.out.println("Digite o ano desejado:");
            ano = leitura.nextInt();
            leitura.nextLine();

            if(ano<=0){
                System.out.println("Ano inválido");
            }
        }
        List<Autor> autores = autorRepository.findAutoresPorAno(ano);

        if(autores.isEmpty()){
            System.out.println("Nenhum autor encontrado.");
        }else {
            System.out.println("Autores vivos em um determinado ano:");
            autores.forEach(System.out::println);
            System.out.println("----------- TOTAL DE AUTORES VIVOS ------------");
            System.out.println("Total de autores vivos: " + autores.size());
            System.out.println("-----------------------------------------------");
        }
    }


    private void listarLivrosPorIdioma() {
        System.out.println("""
                Códigos:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        System.out.println("Digite o código do idioma desejado: ");
        String idiomaCodigo = leitura.nextLine();

        List<Livro> livros = livroRepository.findByIdiomaContainingIgnoreCase(idiomaCodigo);

        if(livros.isEmpty()){
            System.out.println("Não foram encontrados livros no idioma mencionado.");
        }else{
            livros.forEach(System.out::println);
            System.out.println("Total de livros encontrados: "+ livros.size());
        }
    }
}

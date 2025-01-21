package br.com.lindomarjr.literalura.menu;

import br.com.lindomarjr.literalura.model.Author;
import br.com.lindomarjr.literalura.model.Book;
import br.com.lindomarjr.literalura.repository.AuthorRepository;
import br.com.lindomarjr.literalura.repository.BookRepository;
import br.com.lindomarjr.literalura.service.api.GutendexAPIService;
import br.com.lindomarjr.literalura.service.api.IBookAPI;
import br.com.lindomarjr.literalura.service.api.dto.BookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Scanner;

@Service
public class Menu {
    private final IBookAPI api = new GutendexAPIService();
    private final Scanner sc = new Scanner(System.in);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    public void run() {
        while (true) {
            show("Bem vindo a LiterAlura");
            show("Escolha a opção desejada");

            show("1 - Buscar por nome do livro");
            show("2 - Buscar por autor");
            show("3 - Buscar todo histórico de livros no banco de dados");
            show("4 - Buscar todo histórico por idioma no banco de dados");
            show("5 - Buscar todo histórico por autor no banco de dados");
            show("6 - Buscar autores vivos no ano digitado");

            show("0 - Sair");

            String option = this.sc.nextLine();

            switch (option) {
                case "1" -> findByBookName();
                case "2" -> findByAuthor();
                case "3" -> findAlreadySearched();
                case "4" -> findByLanguage();
                case "5" -> findByAuthorAlreadySearched();
                case "6" -> findAuthorsByYear();

                case "0" -> System.exit(0);
                default -> show("Opção invalida tente novamente");
            }
        }
    }


    private void findByBookName() {
        HashMap<String, String> params = new HashMap<>();
        show("Digite o titulo do livro!");
        String bookName = sc.nextLine();
        params.put("bookName", bookName);

        BookData apiData = this.api.findSingleBook(params);
        Book resp = new Book().fromBookData(apiData.results());
        if (resp == null) {
            show("Nenhum livro encontrado com esse titulo!");
            return;
        }

        Book bookTitle = bookRepository.findBookByBookTitle(resp.getBookTitle());

        if (bookTitle == null) {
            bookRepository.save(resp);
        }

        show("Titulo: " + resp.getBookTitle() +
                " - Autor: " + resp.getAuthor().get(0).getName() +
                " - Quantidade de downloads: " + resp.getDownloadCount());
    }

    private void findByAuthor() {
        HashMap<String, String> params = new HashMap<>();
        show("Digite o nome do Autor(a)!");
        String author = sc.nextLine();
        params.put("author", author);

        BookData apiData = this.api.findSingleBook(params);
        Book resp = new Book().fromBookData(apiData.results());

        bookRepository.save(resp);
        show("Titulo: " + resp.getBookTitle() +
                " - Autor: " + resp.getAuthor().get(0).getName() +
                " - Quantidade de downloads: " + resp.getDownloadCount());
    }

    private void show(String msg) {
        System.out.println(msg);
    }

    private void findAlreadySearched() {
        boolean found = false;

        for (Book book : bookRepository.findAll()) {
            found = true;
            show("Titulo: " + book.getBookTitle() +
                    " - Autor: " + book.getAuthor().get(0).getName() +
                    " - Quantidade de downloads: " + book.getDownloadCount());
        }

        if (!found) {
            show("Nenhum livro encontrado");
        }
    }

    private void findByLanguage() {
        show("Digite o idioma! (PADRÃO 2 DIGITOS: pt)");
        String language = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Book book : bookRepository.findAll()) {
            if (book.getLanguages().contains(language)) {
                found = true;
                show("Titulo: " + book.getBookTitle() +
                        " - Autor: " + book.getAuthor().get(0).getName() +
                        " - Quantidade de downloads: " + book.getDownloadCount());
            }
        }

        if (!found) {
            show("Nenhum livro encontrado");
        }
    }

    private void findByAuthorAlreadySearched() {
        boolean found = false;

        for (Author author : authorRepository.findAll()) {
            found = true;
            show("Autor: " + author.getName() +
                    " - Nascimento: " + author.getBirthYear() +
                    " - Falecimento: " + author.getDeathYear());
        }
        if (!found) {
            show("Nenhum autor encontrado");
        }
    }

    private void findAuthorsByYear() {
        show("Digite o ano!");
        String year = sc.nextLine();
        int yearInt = 0;
        try {
            yearInt = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            show("Ano invalido");
            return;
        }
        boolean found = false;
        for (Author author : authorRepository.findAll()) {
            if ((yearInt >= author.getBirthYear()) && (yearInt <= author.getDeathYear())) {
                found = true;
                show("Autor: " + author.getName() +
                        " - Nascimento: " + author.getBirthYear() +
                        " - Falecimento: " + author.getDeathYear());
            }
        }
        if (!found) {
            show("Nenhum autor encontrado");
        }
    }
}

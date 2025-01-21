package br.com.lindomarjr.literalura.model;

import br.com.lindomarjr.literalura.service.api.dto.AuthorsData;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_author")
@Table(name = "tb_author")
public class Author implements Serializable {

    @Id
    private String name;
    private int birthYear;
    private int deathYear;

    public Author() {
    }

    public Author(String name, int birthYear, int deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public static List<Author> fromAuthorData(List<AuthorsData> authors) {
        List<Author> list = new ArrayList<>();
        authors.forEach(authorsData -> {
            list.add(new Author(authorsData.name(), authorsData.birthYear(), authorsData.deathYear()));
        });
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }
}

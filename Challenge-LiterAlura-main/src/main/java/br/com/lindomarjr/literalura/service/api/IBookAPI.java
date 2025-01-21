package br.com.lindomarjr.literalura.service.api;

import br.com.lindomarjr.literalura.service.api.dto.BookData;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface IBookAPI {

    public BookData execute(HashMap<String, String> params);

    public BookData findSingleBook(HashMap<String, String> params);
}

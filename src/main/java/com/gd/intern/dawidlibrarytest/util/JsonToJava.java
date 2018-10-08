package com.gd.intern.dawidlibrarytest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.intern.dawidlibrarytest.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonToJava {

    public static List<Book> getListOfBookFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> listBook = objectMapper.readValue(new File(filePath), new TypeReference<List<Book>>() {
        });
        return listBook;
    }
}

package com.example.bookstore.mapper;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.objects.BookEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BookMapper extends AbstractMapper<BookEntity, BookDTO> {
    private final ModelMapper mapper;

    public BookMapper(ModelMapper mapper) {
        super(BookEntity.class, BookDTO.class, mapper);
        this.mapper = mapper;
    }

    @PostConstruct
    public void setMapper() {
        mapper.typeMap(BookEntity.class, BookDTO.class).addMappings(m-> {
            m.map(src -> src.getShelf().getRack().getId(), BookDTO::setRackId);
        });
    }
}

package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Author;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeleteAuthorEventsListener extends AbstractMongoEventListener<Author> {
    
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        Optional.ofNullable(event.getDocument())
                .map(doc -> doc.getObjectId("_id"))
                .ifPresent(id -> bookRepository
                        .deleteAllByAuthorsContains(authorRepository.findById(id.toString()).get()));
    }
}

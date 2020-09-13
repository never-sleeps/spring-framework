package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeleteGenreEventsListener extends AbstractMongoEventListener<Genre> {
    
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        Optional.ofNullable(event.getDocument())
                .map(doc -> doc.getObjectId("_id"))
                .ifPresent(id -> bookRepository
                        .deleteAllByGenresContains(genreRepository.findById(id.toString()).get()));
    }
}

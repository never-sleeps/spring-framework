package ru.otus.spring.events;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Book;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.Optional;

/*
Начальное условие: перед удалением автора удалять книги этого автора, перед удалением книги удалять комментарии к ней.
Если попробовать пройти все уровни за раз (удалить автора, у которого есть книги с комментариями),
то при попытке после такой команды получить список комментариев я получаю список того же размера,
что и до удаления автора, но в дебаге у элементов списка, относящимся к удаленным книгам, будет:
"Method threw 'java.lang.NullPointerException' exception. Cannot evaluate ru.otus.spring.model.Comment.toString()".
Если же удалять книгу, имеющую комментарии, то такой ошибки не происходит, хотя механизм должен быть аналогичным.

Я бы не рекомендовал использовать ни листенеры, ни DBRef :)
Листенеры выглядят удобно, но за ними сложно уследить, и они могут давать неожиданные эффекты.
Их можно смело использовать для валидации, например, приведения типов в объекте,
для каких-то "непрямых" сайдэффектов - например, отправлять объект в elasticSearch.
Но вот каскадинг через них делать не рекомендую, каскадинг лучше делать руками в сервисе.
То же самое с DBRef - лучше использовать его минимально.
Например у вас комментарии в принципе можно хранить прямо внутри книги.
Ну или ссылку на книгу хранить просто как id стрингом.
 */
@Component
@AllArgsConstructor
public class DeleteBooksEventListener extends AbstractMongoEventListener<Book> {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        Optional.ofNullable(event.getDocument())
                .map(doc -> doc.getObjectId("_id"))
                .ifPresent(id -> commentRepository.deleteAllByBook(
                        bookRepository.findById(id.toString()).get()));
    }
}

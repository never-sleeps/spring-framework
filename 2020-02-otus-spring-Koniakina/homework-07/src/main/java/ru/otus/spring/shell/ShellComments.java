package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.model.Comment;
import ru.otus.spring.service.CommentService;

import java.util.Optional;

@ShellComponent("shellComments")
@RequiredArgsConstructor
public class ShellComments {

    private final CommentService commentService;
    private final ShellOptions options;

    @ShellMethod(value = "Count comments", key = {"count comments", "count c"})
    public String countComments() {
        return String.format("Количество комментариев: %d\n", commentService.countComments());
    }

    @ShellMethod(value = "Create comment", key = {"create comment", "cc"})
    public String createComment(@ShellOption int bookId) {
        Comment comment = commentService.createComment(bookId, options.readComment());
        return String.format("%s успешно добавлен\n", comment.toString());
    }

    @ShellMethod(value = "Delete comment", key = {"delete comment", "dc"})
    public String deleteComment(@ShellOption int id) {
        commentService.deleteComment(id);
        return String.format("Комментарий с id '%d' был удален\n", id);
    }

    @ShellMethod(value = "Delete comment", key = {"delete comment by book id", "dc by b id"})
    public String deleteCommentByBook(@ShellOption int idBook) {
        commentService.deleteCommentByBook(idBook);
        return String.format("Комментарии для книги с id '%d' были удалены\n", idBook);
    }

    @ShellMethod(value = "Information about comment by ID", key = {"comment by id", "с by id"})
    public String getCommentById(@ShellOption int id) {
        Optional <Comment> comment = commentService.findCommentById(id);
        return (comment.isPresent()) ? String.format("Комментарий с id '%d': %s\n", id, comment.toString())
                : String.format("В библиотеке нет комментария  с id '%d'\n", id);
    }

    @ShellMethod(value = "Information about comments by author", key = {"comments by book id", "c by b id"})
    public String getCommentsByBook(@ShellOption int bookId) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Comment comment: commentService.findCommentsByBook(bookId)) {
            stringBuilder.append("\n").append(comment.toString());
        }
        return (stringBuilder.toString().isEmpty()) ? String.format("В бибилиотеке нет комментариев к книге с ID '%d'\n", bookId)
                : String.format("Комментарии к книге с ID '%d': %s\n", bookId, stringBuilder.toString());
    }

    @ShellMethod(value = "List of comments", key = {"all comments", "all c"})
    public String getAllComments() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Comment comment: commentService.findAllComments()) {
            stringBuilder.append("\n").append(comment.toString());
        }
        return String.format("Список комментариев: %s\n", stringBuilder.toString());
    }

}

package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;

    @Field
    @NotBlank
    private String text;


    public Comment(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Комментарий {" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

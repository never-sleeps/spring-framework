package ru.otus.spring.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Listeners {

    public static <T> ItemReadListener<T> createItemReadListener(Logger log) {
        return new ItemReadListener<T>() {
            @Override
            public void beforeRead() {
                log.info("Начало чтения");
            }

            @Override
            public void afterRead(T o) {
                log.info("Конец чтения: {}", o);
            }

            @Override
            public void onReadError(Exception e) {
                log.info("Ошибка чтения", e);
            }
        };
    }

    public static <T> ItemWriteListener<T> createItemWriteListener(Logger log) {
        return new ItemWriteListener<T>() {
            @Override
            public void beforeWrite(List<? extends T> list) {
                log.info("Начало записи: {}", list);
            }

            @Override
            public void afterWrite(List<? extends T> list) {
                log.info("Конец записи: {}", list);
            }

            @Override
            public void onWriteError(Exception e, List<? extends T> list) {
                log.info("Ошибка записи: {}", list, e);
            }
        };
    }

    public static <I, O> ItemProcessListener<I, O> createItemProcessListener(Logger log) {
        return new ItemProcessListener<I, O>() {
            @Override
            public void beforeProcess(I o) {
                log.info("Начало обработки: {}", o);
            }

            @Override
            public void afterProcess(I o, O o2) {
                log.info("Конец обработки: {} -> {}", o, o2);
            }

            @Override
            public void onProcessError(I o, Exception e) {
                log.info("Ошбка обработки: {}", o, e);
            }
        };
    }

    public static ChunkListener createChunkListener(Logger log) {
        return new ChunkListener() {
            @Override
            public void beforeChunk(ChunkContext chunkContext) {
                log.info("Начало пачки");
            }

            @Override
            public void afterChunk(ChunkContext chunkContext) {
                log.info("Конец пачки");
            }

            @Override
            public void afterChunkError(ChunkContext chunkContext) {
                log.info("Ошибка пачки");
            }
        };
    }

    public static JobExecutionListener createJobExecutionListener(Logger log) {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("Начало job");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("Конец job");
            }
        };
    }
}

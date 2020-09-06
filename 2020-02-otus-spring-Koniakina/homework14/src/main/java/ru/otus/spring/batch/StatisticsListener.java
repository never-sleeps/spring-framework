package ru.otus.spring.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsListener {

    private final List<String> statistics = new ArrayList<>();

    public void clear() {
        statistics.clear();
    }

    public String getStatisticsAsString() {
        return statistics.isEmpty()
                ? "\nОшибка записи"
                : "\nУспешно записано: " + String.join(", ", statistics);
    }

    public StepExecutionListener getStepExecutionListener(String type) {
        return new RegistrationListener(type);
    }

    public class RegistrationListener implements StepExecutionListener {
        private final String type;
        public RegistrationListener(String type) {
            this.type = type;
        }

        @Override
        public void beforeStep(StepExecution stepExecution) { }
        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            int count = stepExecution.getWriteCount();
            if (count > 0) {
                statistics.add(type + " " + count);
            }
            return null;
        }
    }
}

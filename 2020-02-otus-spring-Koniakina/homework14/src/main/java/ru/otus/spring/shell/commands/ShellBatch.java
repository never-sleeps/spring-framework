package ru.otus.spring.shell.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.batch.StatisticsListener;
import ru.otus.spring.view.Console;

import java.util.Objects;

@ShellComponent("shellBatch")
@RequiredArgsConstructor
public class ShellBatch {

    private final Job migrationJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    private final StatisticsListener statisticsListener;

    private final Console console;

    @ShellMethod(value = "Start of migration Job With Job Launcher from mongo to jpa", key = "sm-jl")
    public String startMigrationJobWithJobLauncher() throws JobExecutionException {
        statisticsListener.clear();
        jobLauncher.run(migrationJob, new JobParameters());
        return statisticsListener.getStatisticsAsString();
    }

    @ShellMethod(value = "Start of migration Job With Job Operator from mongo to jpa", key = "sm-jo")
    public String startMigrationJobWithJobOperator() throws JobExecutionException {
        statisticsListener.clear();
        Long executionId = jobOperator.start("migrationJob", String.valueOf(new JobParameters()));
        console.write(jobOperator.getSummary(executionId));
        return statisticsListener.getStatisticsAsString();
    }

    @ShellMethod(value = "showInfo", key = "info")
    public void showInfo() {
        console.write(jobExplorer.getJobNames().toString());
        console.write(Objects.requireNonNull(jobExplorer.getLastJobInstance("migrationJob")).toString());
    }
}

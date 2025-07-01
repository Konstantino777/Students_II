package com.productstar.students.src.main.java.org.example.students;

import java.util.*;
import java.util.stream.Collectors;

public class ExaminationImpl implements Examination {

    private final Map<String, Map<String, Score>> studentScores = new HashMap<>();
    private final Map<String, Double> averageScoreCache = new HashMap<>();
    private final Deque<Score> excellentScores = new ArrayDeque<>();
    private boolean cacheInvalid = true;

    @Override
    public void addScore(Score score) {
        String studentKey = getStudentKey(score.name(), score.subject());

        // Обновляем основное хранилище
        studentScores.computeIfAbsent(score.name(), k -> new HashMap<>())
                .put(score.subject(), score);

        // Обновляем кеш отличников, если оценка отличная
        if (score.score() == 5) {
            excellentScores.addFirst(score);
            if (excellentScores.size() > 5) {
                excellentScores.removeLast();
            }
        }

        // Помечаем кеш средних оценок как невалидный
        cacheInvalid = true;
    }

    @Override
    public Score getScore(String name, String subject) {
        Map<String, Score> studentSubjects = studentScores.get(name);
        if (studentSubjects == null) {
            return null;
        }
        return studentSubjects.get(subject);
    }

    @Override
    public double getAverageForSubject(String subject) {
        // Если кеш валиден, возвращаем из кеша
        if (!cacheInvalid && averageScoreCache.containsKey(subject)) {
            return averageScoreCache.get(subject);
        }

        // Пересчитываем среднее
        double sum = 0;
        int count = 0;

        for (Map<String, Score> student : studentScores.values()) {
            Score score = student.get(subject);
            if (score != null) {
                sum += score.score();
                count++;
            }
        }

        double average = count == 0 ? 0 : sum / count;

        // Обновляем кеш
        averageScoreCache.put(subject, average);
        return average;
    }

    public Map<String, Double> getAverageScoreCache() {
        return averageScoreCache;
    }

    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        return studentScores.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        return excellentScores.stream()
                .limit(5)
                .map(Score::name)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Score> getAllScores() {
        return studentScores.values().stream()
                .flatMap(m -> m.values().stream())
                .collect(Collectors.toList());
    }

    private String getStudentKey(String name, String subject) {
        return name + ":" + subject;
    }
}

package cw.skypro.cw_javacore.service;

import cw.skypro.cw_javacore.model.Question;

import java.util.Set;

public interface QuestionService {

    String add(String question, String answer);

    String add(Question question);

    String remove(Question question);

    Set<Question> getAll();

    Question getRandomQuestion();

}

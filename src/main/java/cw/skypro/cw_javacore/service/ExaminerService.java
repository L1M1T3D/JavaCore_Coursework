package cw.skypro.cw_javacore.service;

import cw.skypro.cw_javacore.model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);

}

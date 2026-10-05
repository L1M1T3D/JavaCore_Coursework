package cw.skypro.cw_javacore.controller;

import cw.skypro.cw_javacore.model.Question;
import cw.skypro.cw_javacore.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final QuestionService questionService;

    public JavaQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/add")
    public String addQuestion(
            @RequestParam("question") String question,
            @RequestParam("answer") String answer) {

        return questionService.add(question, answer);
    }

    @GetMapping("/remove")
    public String removeQuestion(
            @RequestParam("question") String question,
            @RequestParam("answer") String answer) {

        return questionService.remove(new Question(question, answer));
    }

    @GetMapping
    public Set<Question> getAllQuestions() {
        return questionService.getAll();
    }

}

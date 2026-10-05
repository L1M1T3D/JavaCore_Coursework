package cw.skypro.cw_javacore;

import cw.skypro.cw_javacore.model.Question;
import cw.skypro.cw_javacore.service.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {

    private JavaQuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new JavaQuestionService();
    }

    @Test
    void shouldAddQuestionUsingStrings() {
        questionService.add(
                "Что такое JVM?",
                "Java Virtual Machine"
        );

        Question expected = new Question(
                "Что такое JVM?",
                "Java Virtual Machine"
        );

        assertTrue(questionService.getAll().contains(expected));
    }

    @Test
    void shouldAddQuestionObject() {
        Question question = new Question(
                "Что такое Spring?",
                "Java framework"
        );

        questionService.add(question);

        assertTrue(questionService.getAll().contains(question));
    }

    @Test
    void shouldNotAddDuplicateQuestion() {
        Question question = new Question(
                "Что такое Java?",
                "Язык программирования"
        );

        questionService.add(question);
        questionService.add(question);

        assertEquals(1, questionService.getAll().size());
    }

    @Test
    void shouldReturnEmptySetWhenThereAreNoQuestions() {
        assertNotNull(questionService.getAll());
        assertTrue(questionService.getAll().isEmpty());
    }

    @Test
    void shouldRemoveQuestion() {
        Question question = new Question(
                "Что такое коллекция?",
                "Структура данных"
        );

        questionService.add(question);
        questionService.remove(question);

        assertTrue(questionService.getAll().isEmpty());
    }

    @Test
    void shouldNotChangeCollectionWhenRemovingMissingQuestion() {
        Question question = new Question(
                "Несуществующий вопрос",
                "Ответ"
        );

        questionService.remove(question);

        assertTrue(questionService.getAll().isEmpty());
    }

    @Test
    void shouldReturnRandomQuestion() {
        Question firstQuestion = new Question(
                "Что такое JVM?",
                "Java Virtual Machine"
        );

        Question secondQuestion = new Question(
                "Что такое JDK?",
                "Java Development Kit"
        );

        questionService.add(firstQuestion);
        questionService.add(secondQuestion);

        Question result = questionService.getRandomQuestion();

        assertNotNull(result);
        assertTrue(
                result.equals(firstQuestion)
                        || result.equals(secondQuestion)
        );
    }

    @Test
    void shouldThrowExceptionWhenGettingRandomQuestionFromEmptyCollection() {
        assertThrows(
                IllegalStateException.class,
                () -> questionService.getRandomQuestion()
        );
    }
}

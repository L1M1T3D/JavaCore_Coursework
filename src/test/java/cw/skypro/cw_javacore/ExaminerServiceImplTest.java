package cw.skypro.cw_javacore;

import cw.skypro.cw_javacore.model.Question;
import cw.skypro.cw_javacore.service.ExaminerService;
import cw.skypro.cw_javacore.service.ExaminerServiceImpl;
import cw.skypro.cw_javacore.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    private ExaminerService examinerService;

    private Question firstQuestion;
    private Question secondQuestion;
    private Question thirdQuestion;

    @BeforeEach
    void setUp() {
        examinerService = new ExaminerServiceImpl(questionService);

        firstQuestion = new Question(
                "Что такое JVM?",
                "Java Virtual Machine"
        );

        secondQuestion = new Question(
                "Что такое JDK?",
                "Java Development Kit"
        );

        thirdQuestion = new Question(
                "Что такое JRE?",
                "Java Runtime Environment"
        );
    }

    @Test
    void shouldReturnRequestedNumberOfQuestions() {
        when(questionService.getAll())
                .thenReturn(Set.of(
                        firstQuestion,
                        secondQuestion,
                        thirdQuestion
                ));

        when(questionService.getRandomQuestion())
                .thenReturn(
                        firstQuestion,
                        secondQuestion,
                        thirdQuestion
                );

        Collection<Question> result =
                examinerService.getQuestions(2);

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnUniqueQuestions() {
        when(questionService.getAll())
                .thenReturn(Set.of(
                        firstQuestion,
                        secondQuestion,
                        thirdQuestion
                ));

        when(questionService.getRandomQuestion())
                .thenReturn(
                        firstQuestion,
                        firstQuestion,
                        secondQuestion
                );

        Collection<Question> result =
                examinerService.getQuestions(2);

        assertEquals(2, result.size());
        assertTrue(result.contains(firstQuestion));
        assertTrue(result.contains(secondQuestion));
    }

    @Test
    void shouldReturnEmptySetWhenAmountIsZero() {
        when(questionService.getAll())
                .thenReturn(Set.of(
                        firstQuestion,
                        secondQuestion
                ));

        Collection<Question> result =
                examinerService.getQuestions(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(questionService, never()).getRandomQuestion();
    }

    @Test
    void shouldReturnAllQuestionsWhenAmountEqualsAvailableQuestions() {
        when(questionService.getAll())
                .thenReturn(Set.of(
                        firstQuestion,
                        secondQuestion,
                        thirdQuestion
                ));

        when(questionService.getRandomQuestion())
                .thenReturn(
                        firstQuestion,
                        secondQuestion,
                        thirdQuestion
                );

        Collection<Question> result =
                examinerService.getQuestions(3);

        assertEquals(3, result.size());
    }

    @Test
    void shouldThrowBadRequestWhenAmountIsTooLarge() {
        when(questionService.getAll())
                .thenReturn(Set.of(
                        firstQuestion,
                        secondQuestion
                ));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> examinerService.getQuestions(3)
        );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        verify(questionService, never()).getRandomQuestion();
    }

    @Test
    void shouldThrowBadRequestWhenAmountIsNegative() {
        when(questionService.getAll())
                .thenReturn(Set.of(
                        firstQuestion,
                        secondQuestion
                ));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> examinerService.getQuestions(-1)
        );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );
    }

    @Test
    void shouldReturnEmptyResultWhenThereAreNoAvailableQuestionsAndAmountIsZero() {
        when(questionService.getAll())
                .thenReturn(Set.of());

        Collection<Question> result =
                examinerService.getQuestions(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowBadRequestWhenThereAreNoQuestions() {
        when(questionService.getAll())
                .thenReturn(Set.of());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> examinerService.getQuestions(1)
        );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );
    }
}

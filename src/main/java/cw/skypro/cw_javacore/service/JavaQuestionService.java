package cw.skypro.cw_javacore.service;

import cw.skypro.cw_javacore.model.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public String add(String question, String answer) {
        add(new Question(question, answer));
        return "Вы успешно добавили новый вопрос и ответ!";
    }

    @Override
    public String add(Question question) {
        questions.add(question);
        return "Вы успешно добавили новый вопрос!";
    }

    @Override
    public String remove(Question question) {
        questions.remove(question);
        return "Вы успешно удалили вопрос!";
    }

    @Override
    public Set<Question> getAll() {
        return Set.copyOf(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("Хранилище вопросов пусто.");
        }

        ArrayList<Question> questionList = new ArrayList<>(questions);

        int randomIndex = random.nextInt(questionList.size());

        return questionList.get(randomIndex);
    }
}
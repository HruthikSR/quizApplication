package com.hruthik.QuizzApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hruthik.QuizzApp.Dao.QuestionDao;
import com.hruthik.QuizzApp.Dao.QuizDao;
import com.hruthik.QuizzApp.model.Question;
import com.hruthik.QuizzApp.model.QuestionWrapper;
import com.hruthik.QuizzApp.model.Quiz;
import com.hruthik.QuizzApp.model.Response;

@Service
public class QuizService {
	@Autowired
	QuizDao quizDao;

	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);

		return new ResponseEntity<>("Sucess", HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionsFromDB = quiz.get().getQuestions();
		System.out.print("questionsFromDB :" + questionsFromDB);
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for (Question q : questionsFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
					q.getOption3(), q.getOption4());
			questionsForUser.add(qw);
		}
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
	}

	/*
	 * public ResponseEntity<Integer> calculateResult(int id, List<Response>
	 * responses) { Quiz quiz = quizDao.findById(id).get(); List<Question> questions
	 * = quiz.getQuestions(); int right = 0; for (Response response : responses) {
	 * for (int j = 0; j < questions.size(); j++) { if (response.getId() ==
	 * questions.get(j).getId() &&
	 * response.getResponse().equals(questions.get(j).getRightAnswer())) {
	 * right++;// vice versa the wrong ans is not counting in postman
	 * System.out.println("Condition met"); } } } return new ResponseEntity<>(right,
	 * HttpStatus.OK); }
	 */

	public ResponseEntity<List<Map<String, Integer>>> calculateResult(int id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right = 0;
		 List<Map<String, Integer>> matchedIds = new ArrayList<>();
		for (Response response : responses) {
			for (int j = 0; j < questions.size(); j++) {
				if (response.getId() == questions.get(j).getId()
						&& response.getResponse().equals(questions.get(j).getRightAnswer())) {
					right++;
					System.out.println("Right answer found");
					Map<String, Integer> matched = new HashMap<>();
					matched.put("id",response.getId());
					matchedIds.add(matched);
				}
			}
		}
		System.out.println("No of correct answer:"+right);
		
		return new ResponseEntity<>(matchedIds, HttpStatus.OK);
	}
}

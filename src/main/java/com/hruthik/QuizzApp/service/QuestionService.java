package com.hruthik.QuizzApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hruthik.QuizzApp.Dao.QuestionDao;
import com.hruthik.QuizzApp.model.Question;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;
	public ResponseEntity<List<Question>>getAllQuestions(){
		try {
			return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);	
		}
//		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);	
	}
	
	public ResponseEntity<List<Question>>getQuestionsByCategory(String category){
		try {
		return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_GATEWAY);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		try {
		questionDao.save(question);
		return new ResponseEntity <>("Sucess",HttpStatus.ACCEPTED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	public String deleteQuestion(Question question) {
		questionDao.delete(question);
		System.out.println("Deleted sucessfuly");
		return "sucessfully removed";
		
	}
	
	public void deleteQuestionById(int id) {
		questionDao.deleteById(12);
	}
}

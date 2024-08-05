package com.hruthik.QuizzApp.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hruthik.QuizzApp.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

}

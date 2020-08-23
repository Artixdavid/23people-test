package com.app.test.models.services;

import java.util.List;

import com.app.test.dto.StudentNew;
import com.app.test.models.entity.Student;

public interface IStudentService {

	public List<Student> findall();

	public Student findById(Long id);

	public boolean create(StudentNew studen);
	
	public boolean update(Long id, StudentNew studen);

	public boolean delete(Long id);
}

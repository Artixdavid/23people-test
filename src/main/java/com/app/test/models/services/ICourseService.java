package com.app.test.models.services;

import java.util.List;

import com.app.test.dto.CourseNew;
import com.app.test.models.entity.Course;

public interface ICourseService {

	public List<Course> findAll();

	public Course findById(Long id);

	public boolean create(CourseNew course);

	public boolean update(Long id, CourseNew course);

	public boolean deleteById(Long id);

}

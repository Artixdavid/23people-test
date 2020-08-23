package com.app.test.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.test.models.entity.Course;


public interface ICourseDao extends CrudRepository<Course, Long>{
	
	@Query("SELECT c FROM Course c WHERE c.code = :code ")
	public Course findCourseByCode(@Param("code") String code);
	
	@Query("SELECT c FROM Course c WHERE c.code = :code AND c.courseId <> :id ")
	public Course findCourseByCodeAndId(@Param("code") String code, @Param("id") Long id);
	

}

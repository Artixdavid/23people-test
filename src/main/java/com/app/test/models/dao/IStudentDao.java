package com.app.test.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.test.models.entity.Student;


public interface IStudentDao extends CrudRepository<Student, Long>{
	
	@Query("SELECT s FROM Student s WHERE s.course.id = :id")
	public List<Student> findAllStudentByCourse(@Param("id") Long id);
	
	@Query("SELECT s FROM Student s WHERE s.rut = :rut")
	public Student findStudenByRut(@Param("rut") String rut);
	
	@Query("SELECT s FROM Student s WHERE s.rut = :rut AND s.id <> :id")
	public Student findStudenByRutAndId(@Param("rut") String rut,@Param("id") Long id);

}

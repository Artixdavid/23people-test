package com.app.test.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.test.dto.CourseNew;
import com.app.test.models.dao.ICourseDao;
import com.app.test.models.dao.IStudentDao;
import com.app.test.models.entity.Course;
import com.app.test.models.entity.Student;

@Service
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private ICourseDao courseDao;
	
	@Autowired
	private IStudentDao studentDao;

	@Override
	public List<Course> findAll() {
		return (List<Course>) courseDao.findAll();
	}

	@Override
	public Course findById(Long id) {
		return courseDao.findById(id).orElse(null);
	}

	@Override
	public boolean create(CourseNew newcourse) {
		Course course = courseDao.findCourseByCode(newcourse.getCode());
		if (course != null) {
			return false;
		}
		Course newcourse_ = new Course();
		newcourse_.setCode(newcourse.getCode());
		newcourse_.setCourseName(newcourse.getName());
		courseDao.save(newcourse_);
		return true;
	}

	@Transactional
	@Override
	public boolean update(Long id, CourseNew newCourse) {
		Course course = courseDao.findById(id).orElse(null);
		
		if(course == null) {
			return false;
		}
		
		if(newCourse.getCode() != null && !newCourse.getCode().equals("")) {
			Course courseCode = courseDao.findCourseByCodeAndId(newCourse.getCode(), id);
			
			if(courseCode != null) {
				return false;
			}
		}
		course.setCode(newCourse.getCode());
		course.setCourseName(newCourse.getName());
		courseDao.save(course);
		return true;
		
	}

	@Override
	public boolean deleteById(Long id) {
		
		Course course = courseDao.findById(id).orElse(null);
		if(course == null) {
			return false;
		}
		List<Student> studens = studentDao.findAllStudentByCourse(course.getCourseId());
		
		for(Student stu : studens) {
			stu.setCourse(null);
			studentDao.save(stu);
		}
		
		courseDao.delete(course);
		return true;
	}

}

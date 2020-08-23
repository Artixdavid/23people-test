package com.app.test.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.test.Utils.Utils;
import com.app.test.dto.StudentNew;
import com.app.test.models.dao.ICourseDao;
import com.app.test.models.dao.IStudentDao;
import com.app.test.models.entity.Course;
import com.app.test.models.entity.Student;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private IStudentDao studentDao;

	@Autowired
	private ICourseDao courseDao;

	@Override
	@Transactional(readOnly = true)
	public List<Student> findall() {
		return (List<Student>) studentDao.findAll();
	}

	@Override
	public Student findById(Long id) {
		return studentDao.findById(id).orElse(null);
	}

	@Override
	public boolean create(StudentNew studenNew) {

		String rut = studenNew.getRut();
		rut.replaceAll(".", "");
		rut = Utils.formatearRut(rut);
		Student student = studentDao.findStudenByRut(rut);

		if (student != null) {
			return false;
		} else {
			student = new Student();
		}

		Course course = courseDao.findById(studenNew.getCourseId()).orElse(null);
		if (course == null) {
			return false;
		}

		Date date = Utils.formatearFecha(studenNew.getAge());

		student.setAge(date);
		student.setCourse(course);
		student.setLastName(studenNew.getLastName());
		student.setName(studenNew.getName());
		student.setRut(rut);

		studentDao.save(student);

		return true;

	}

	@Override
	public boolean delete(Long id) {

		Student studen = studentDao.findById(id).orElse(null);

		if (studen == null) {
			return false;
		}

		studentDao.delete(studen);
		return true;
	}

	@Override
	public boolean update(Long id, StudentNew studenNew) {
		
		String rut = studenNew.getRut();
		rut.replaceAll(".", "");
		rut = Utils.formatearRut(rut);
		
		Student student = studentDao.findStudenByRutAndId(rut, id);

		if (student != null) {
			return false;
		} else {
			student = studentDao.findById(id).orElse(null);
			if(student == null) {
				return false;
			}
		}
		
		Course course = courseDao.findById(studenNew.getCourseId()).orElse(null);
		if (course == null) {
			return false;
		}
		
		Date date = Utils.formatearFecha(studenNew.getAge());

		student.setAge(date);
		student.setCourse(course);
		student.setLastName(studenNew.getLastName());
		student.setName(studenNew.getName());
		student.setRut(rut);

		studentDao.save(student);
		
		return true;
	}

}

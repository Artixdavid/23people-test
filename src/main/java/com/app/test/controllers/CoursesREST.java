package com.app.test.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.test.dto.CourseNew;
import com.app.test.dto.Response;
import com.app.test.models.entity.Course;
import com.app.test.models.services.ICourseService;

@RestController
public class CoursesREST {

	@Autowired
	private ICourseService courseService;

	@GetMapping(value="/courses")
	public List<Course> getStudens() {
		return (List<Course>) courseService.findAll();
	}

	@GetMapping(value="/courses/{id}")
	@ResponseBody
	public ResponseEntity<?> getStudenById(@PathVariable(value = "id") Long id) {
		Response response;
		if (id == null) {
			response = new Response("Id can not be null");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		Course course = courseService.findById(id);

		if (course == null) {
			response = new Response("The course does not exits");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(course, HttpStatus.OK);
	}

	@PostMapping(value="/courses/")
	@ResponseBody
	public ResponseEntity<?> createCourse(@RequestBody CourseNew newcourse) {
		Response response;
		if (newcourse == null) {

			response = new Response("Empty body");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (newcourse.getCode() == null || newcourse.getCode().trim().equals("")) {
			response = new Response("Code can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (newcourse.getName() == null || newcourse.getName().trim().equals("")) {
			response = new Response("Name can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		boolean resultado = courseService.create(newcourse);

		if (resultado) {
			response = new Response("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

		response = new Response("The course has already exist!");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value="/courses/{id}")
	@ResponseBody
	public ResponseEntity<?> updateCourse(@RequestBody CourseNew newcourse, @PathVariable(value = "id") Long id) {
		Response response;
		if (newcourse == null) {
			response = new Response("Empty body");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (id == null) {
			response = new Response("Id can not be null");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		boolean updated = courseService.update(id, newcourse);

		if (!updated) {
			response = new Response("The course could not be modified");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		response = new Response("Course modified");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@DeleteMapping(value="/courses/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long id) {

		Response response;
		if (id == null) {
			response = new Response("Id can not be null");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		boolean deleted = courseService.deleteById(id);

		if (!deleted) {
			response = new Response("The course does not exits");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}

		response = new Response("Course deleted");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}

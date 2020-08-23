package com.app.test.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.test.Utils.Utils;
import com.app.test.dto.Response;
import com.app.test.dto.StudentNew;
import com.app.test.models.entity.Student;
import com.app.test.models.services.IStudentService;

@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.DELETE, RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT })
@RestController
public class StudentsREST {

	@Autowired
	private IStudentService studentService;

	@GetMapping("/students")
	public List<Student> getStudens() {
		return (List<Student>) studentService.findall();
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudenById(@PathVariable(value = "id") Long id) {

		Response response;
		if (id == null) {
			response = new Response("Id can not be null");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		Student studen = studentService.findById(id);
		if (studen == null) {
			response = new Response("The Student does not exits");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(studen, HttpStatus.OK);

	}

	@PostMapping("/students/")
	public ResponseEntity<?> createStudent(@RequestBody StudentNew newStuden) {
		Response response;

		if (newStuden == null) {
			response = new Response("Empty body");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (newStuden.getAge() == null || newStuden.getAge().equals("")) {
			response = new Response("Age can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			boolean validaFecha = Utils.validarFecha(newStuden.getAge());
			if (!validaFecha) {
				response = new Response("Invalid age");
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}

		if (newStuden.getCourseId() == null) {
			response = new Response("CourseId can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (newStuden.getRut() == null || newStuden.getRut().trim().equals("")) {
			response = new Response("Rut can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			boolean validaRut = Utils.validarRut(newStuden.getRut());
			if (!validaRut) {
				response = new Response("Invalid rut");
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}

		if (newStuden.getLastName() == null || newStuden.getLastName().trim().equals("")) {
			response = new Response("LastName can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (newStuden.getName() == null || newStuden.getName().trim().equals("")) {
			response = new Response("Name can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		boolean resultado = studentService.create(newStuden);

		if (resultado) {
			response = new Response("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

		response = new Response("The Student has already exist!");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<?> updateStudent(@RequestBody StudentNew studentNew, @PathVariable(value = "id") Long id) {

		Response response;

		if (studentNew == null) {
			response = new Response("Empty body");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (studentNew.getAge() == null || studentNew.getAge().equals("")) {
			response = new Response("Age can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			boolean validaFecha = Utils.validarFecha(studentNew.getAge());
			if (!validaFecha) {
				response = new Response("Invalid age");
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}

		if (studentNew.getCourseId() == null) {
			response = new Response("CourseId can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (studentNew.getRut() == null || studentNew.getRut().trim().equals("")) {
			response = new Response("Rut can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			boolean validaRut = Utils.validarRut(studentNew.getRut());
			if (!validaRut) {
				response = new Response("Invalid rut");
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}

		if (studentNew.getLastName() == null || studentNew.getLastName().trim().equals("")) {
			response = new Response("LastName can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		if (studentNew.getName() == null || studentNew.getName().trim().equals("")) {
			response = new Response("Name can not be empty");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		boolean updated = studentService.update(id, studentNew);

		if (!updated) {
			response = new Response("The Student could not be modified");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		response = new Response("Student modified");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {

		Response response;
		if (id == null) {
			response = new Response("Id can not be null");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		boolean deleted = studentService.delete(id);

		if (!deleted) {
			response = new Response("The Student does not exits");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}

		response = new Response("Student deleted");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}

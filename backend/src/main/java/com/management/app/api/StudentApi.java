package com.management.app.api;

import com.management.app.core.model.entity.Student;
import com.management.app.core.service.IStudentService;
import com.management.app.support.Rest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentApi {

    private final IStudentService service;

    public StudentApi(IStudentService service) {
        this.service = service;
    }

    @GetMapping
    public Rest<?> findPaging(@RequestParam(required = false) Integer pageNumber,
                              @RequestParam(required = false) Integer pageSize,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) Integer teamId) {
        return Rest.ok(service.findPaging(pageNumber, pageSize, teamId, name));
    }

    @GetMapping("/{id}")
    public Rest<Student> findById(@PathVariable Integer id) {
        return Rest.ok(service.findById(id));
    }

    @PostMapping
    public Rest<?> create(@RequestBody Student student) {
        service.create(student);
        return Rest.ok();
    }

    @PutMapping("/{id}")
    public Rest<?> update(@PathVariable Integer id, @RequestBody Student student) {
        service.update(id, student);
         return Rest.ok();
    }

    @DeleteMapping("/{id}")
    public Rest<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return Rest.ok();
    }
}

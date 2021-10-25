package com.management.app.api;

import com.management.app.core.model.entity.Student;
import com.management.app.core.model.entity.Teacher;
import com.management.app.core.service.IStudentService;
import com.management.app.core.service.ITeacherService;
import com.management.app.support.Rest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherApi {

    private final ITeacherService service;

    public TeacherApi(ITeacherService service) {
        this.service = service;
    }

    @GetMapping
    public Rest<?> findPaging(@RequestParam(required = false) Integer pageNumber,
                              @RequestParam(required = false) Integer pageSize,
                              @RequestParam(required = false) String name) {
        return Rest.ok(service.findPaging(pageNumber, pageSize, name));
    }

    @GetMapping("/{id}")
    public Rest<?> findById(@PathVariable Integer id) {
        return Rest.ok(service.findById(id));
    }

    @GetMapping("/all")
    public Rest<?> findAll() {
        return Rest.ok(service.findAll());
    }

    @PostMapping
    public Rest<?> create(@RequestBody Teacher model) {
        service.create(model);
        return Rest.ok();
    }

    @PutMapping("/{id}")
    public Rest<?> update(@PathVariable Integer id, @RequestBody Teacher model) {
        service.update(id, model);
        return Rest.ok();
    }

    @DeleteMapping("/{id}")
    public Rest<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return Rest.ok();
    }
}

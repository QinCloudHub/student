package com.management.app.api;

import com.management.app.core.model.entity.Course;
import com.management.app.core.model.entity.Teacher;
import com.management.app.core.service.ICourseService;
import com.management.app.core.service.ITeacherService;
import com.management.app.support.Rest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseApi {

    private final ICourseService service;

    public CourseApi(ICourseService service) {
        this.service = service;
    }

    @GetMapping("/semesters")
    public Rest<?> findAll() {
        return Rest.ok(service.findAllSemester());
    }

    @GetMapping
    public Rest<?> findPaging(@RequestParam(required = false) Integer pageNumber,
                              @RequestParam(required = false) Integer pageSize,
                              @RequestParam(required = false) String semester,
                              @RequestParam(required = false) Integer teamId,
                              @RequestParam(required = false) Integer teacherId,
                              @RequestParam(required = false) String name) {
        return Rest.ok(service.findPaging(pageNumber, pageSize, semester, teamId, teacherId, name));
    }

    @GetMapping("/{id}")
    public Rest<?> findById(@PathVariable Integer id) {
        return Rest.ok(service.findById(id));
    }

    @PostMapping
    public Rest<?> create(@RequestBody Course model) {
        service.create(model);
        return Rest.ok();
    }

    @PutMapping("/{id}")
    public Rest<?> update(@PathVariable Integer id, @RequestBody Course model) {
        service.update(id, model);
        return Rest.ok();
    }

    @DeleteMapping("/{id}")
    public Rest<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return Rest.ok();
    }
}

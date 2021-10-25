package com.management.app.api;

import com.management.app.core.model.entity.Team;
import com.management.app.core.service.ITeamService;
import com.management.app.support.Rest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamApi {

    private final ITeamService service;


    public TeamApi(ITeamService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Rest<?> findAll() {
        return Rest.ok(service.findAll());
    }

    @GetMapping
    public Rest<?> findPaging(@RequestParam(required = false) Integer pageNumber,
                              @RequestParam(required = false) Integer pageSize,
                              @RequestParam(required = false) String professional) {
        return Rest.ok(service.findPaging(pageNumber, pageSize, professional));
    }

    @GetMapping("/{id}")
    public Rest<?> findById(@PathVariable Integer id) {
        return Rest.ok(service.findById(id));
    }

    @PostMapping
    public Rest<?> create(@RequestBody Team team) {
        service.create(team);
        return Rest.ok();
    }

    @PutMapping("/{id}")
    public Rest<?> update(@PathVariable Integer id, @RequestBody Team team) {
        service.update(id, team);
        return Rest.ok();
    }

    @DeleteMapping("/{id}")
    public Rest<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return Rest.ok();
    }
}

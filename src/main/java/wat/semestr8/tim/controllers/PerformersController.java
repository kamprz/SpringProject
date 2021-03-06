package wat.semestr8.tim.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr8.tim.dtos.PerformersDto;
import wat.semestr8.tim.services.dataservices.PerformersService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PerformersController
{
    private PerformersService service;

    public PerformersController(PerformersService service) {
        this.service = service;
    }

    @GetMapping(value = "/admin/performer")
    public ResponseEntity<List<PerformersDto>> getAllPerformers()
    {
        return ResponseEntity.ok().body(service.getAll());
    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT},value = "/admin/performer")
    public void postPerformers(@RequestBody @Valid PerformersDto dto){
        service.create(dto);
    }

    @DeleteMapping(value = "/admin/performer/{id}")
    public void deletePerformers(@PathVariable int id)
    {
        service.delete(id);
    }

}
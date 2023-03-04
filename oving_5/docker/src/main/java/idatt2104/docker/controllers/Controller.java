package idatt2104.docker.controllers;

import idatt2104.docker.models.Code;
import idatt2104.docker.services.Compiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class Controller {

    @Autowired
    Compiler compiler;

    @PostMapping("/compile")
    @ResponseBody
    public Code compile(@RequestBody Code code) throws IOException {
        return compiler.compile(code);
    }
}

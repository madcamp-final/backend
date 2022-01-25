package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.Idea;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.service.IdeaService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/idea")
public class IdeaController {

    private final IdeaService ideaService;

    @Autowired
    public IdeaController(IdeaService ideaService){
        this.ideaService = ideaService;
    }

    @PostMapping("/create")
    public DefaultResponse createIdeaCategory(@RequestBody Idea idea){
        Idea created = ideaService.create(idea);
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.IDEA_CATEGORY_SUCCESS, created);
    }

    @GetMapping("/load-type")
    public DefaultResponse loadIdeaType(@RequestParam String category){
        return ideaService.loadType(category);
    }

}

package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Idea;
import com.joongoprime.backend.entity.IdeaRepository;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {
    private final IdeaRepository ideaRepository;

    @Autowired
    public IdeaService(IdeaRepository ideaRepository){
        this.ideaRepository = ideaRepository;
    }

    public Idea create(Idea idea){
        return ideaRepository.save(idea);
    }

    public DefaultResponse loadType(String category){
        List<String> ideaType = ideaRepository.getTypeFromCategory(category);
        if(ideaType.size() == 0){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.LOAD_IDEA_TYPE_FAILED);
        }
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.LOAD_IDEA_TYPE_SUCCESS, ideaType);
    }
}

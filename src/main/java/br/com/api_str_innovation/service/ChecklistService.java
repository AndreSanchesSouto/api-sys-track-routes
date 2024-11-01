package br.com.api_str_innovation.service;

import br.com.api_str_innovation.repository.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository repository;
}

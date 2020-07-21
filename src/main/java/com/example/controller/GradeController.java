package com.example.controller;

import com.example.controller.contract.GradeRequestContract;
import com.example.persistence.repository.GradeRepository;
import com.example.persistence.translator.GradeTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class GradeController {
  private final GradeTranslator gradeTranslator = new GradeTranslator();
  private final GradeRepository gradeRepository;

  @Autowired
  public GradeController(GradeRepository gradeRepository) {
    this.gradeRepository = gradeRepository;
  }

  @PostMapping("/{tenant}/api/grade")
  public Mono<ResponseEntity> upsert(@Valid @RequestBody Mono<GradeRequestContract> gradeMono) {
    return gradeMono.map(gradeTranslator::translateTo)
        .flatMap(gradeRepository::upsert)
        .then(Mono.just(ResponseEntity.ok("Saved grade successfully.")));
  }

  @GetMapping("/{tenant}/api/grade")
  public Mono<ResponseEntity> getAllGrades() {
    return gradeRepository.getAll()
        .collectList()
        .map(ResponseEntity::ok);
  }

}

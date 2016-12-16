package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Toys;

import com.mycompany.myapp.repository.ToysRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Toys.
 */
@RestController
@RequestMapping("/api")
public class ToysResource {

    private final Logger log = LoggerFactory.getLogger(ToysResource.class);
        
    @Inject
    private ToysRepository toysRepository;

    /**
     * POST  /toys : Create a new toys.
     *
     * @param toys the toys to create
     * @return the ResponseEntity with status 201 (Created) and with body the new toys, or with status 400 (Bad Request) if the toys has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/toys")
    @Timed
    public ResponseEntity<Toys> createToys(@RequestBody Toys toys) throws URISyntaxException {
        log.debug("REST request to save Toys : {}", toys);
        if (toys.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("toys", "idexists", "A new toys cannot already have an ID")).body(null);
        }
        Toys result = toysRepository.save(toys);
        return ResponseEntity.created(new URI("/api/toys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("toys", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /toys : Updates an existing toys.
     *
     * @param toys the toys to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated toys,
     * or with status 400 (Bad Request) if the toys is not valid,
     * or with status 500 (Internal Server Error) if the toys couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/toys")
    @Timed
    public ResponseEntity<Toys> updateToys(@RequestBody Toys toys) throws URISyntaxException {
        log.debug("REST request to update Toys : {}", toys);
        if (toys.getId() == null) {
            return createToys(toys);
        }
        Toys result = toysRepository.save(toys);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("toys", toys.getId().toString()))
            .body(result);
    }

    /**
     * GET  /toys : get all the toys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of toys in body
     */
    @GetMapping("/toys")
    @Timed
    public List<Toys> getAllToys() {
        log.debug("REST request to get all Toys");
        List<Toys> toys = toysRepository.findAll();
        return toys;
    }

    /**
     * GET  /toys/:id : get the "id" toys.
     *
     * @param id the id of the toys to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the toys, or with status 404 (Not Found)
     */
    @GetMapping("/toys/{id}")
    @Timed
    public ResponseEntity<Toys> getToys(@PathVariable Long id) {
        log.debug("REST request to get Toys : {}", id);
        Toys toys = toysRepository.findOne(id);
        return Optional.ofNullable(toys)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /toys/:id : delete the "id" toys.
     *
     * @param id the id of the toys to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/toys/{id}")
    @Timed
    public ResponseEntity<Void> deleteToys(@PathVariable Long id) {
        log.debug("REST request to delete Toys : {}", id);
        toysRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("toys", id.toString())).build();
    }

}

package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Toys;
import com.mycompany.myapp.repository.ToysRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ToysResource REST controller.
 *
 * @see ToysResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class ToysResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Double DEFAULT_AVG_PRICE = 1D;
    private static final Double UPDATED_AVG_PRICE = 2D;

    @Inject
    private ToysRepository toysRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restToysMockMvc;

    private Toys toys;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ToysResource toysResource = new ToysResource();
        ReflectionTestUtils.setField(toysResource, "toysRepository", toysRepository);
        this.restToysMockMvc = MockMvcBuilders.standaloneSetup(toysResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toys createEntity(EntityManager em) {
        Toys toys = new Toys()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .category(DEFAULT_CATEGORY)
                .avg_price(DEFAULT_AVG_PRICE);
        return toys;
    }

    @Before
    public void initTest() {
        toys = createEntity(em);
    }

    @Test
    @Transactional
    public void createToys() throws Exception {
        int databaseSizeBeforeCreate = toysRepository.findAll().size();

        // Create the Toys

        restToysMockMvc.perform(post("/api/toys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toys)))
                .andExpect(status().isCreated());

        // Validate the Toys in the database
        List<Toys> toys = toysRepository.findAll();
        assertThat(toys).hasSize(databaseSizeBeforeCreate + 1);
        Toys testToys = toys.get(toys.size() - 1);
        assertThat(testToys.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testToys.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testToys.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testToys.getAvg_price()).isEqualTo(DEFAULT_AVG_PRICE);
    }

    @Test
    @Transactional
    public void getAllToys() throws Exception {
        // Initialize the database
        toysRepository.saveAndFlush(toys);

        // Get all the toys
        restToysMockMvc.perform(get("/api/toys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(toys.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].avg_price").value(hasItem(DEFAULT_AVG_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getToys() throws Exception {
        // Initialize the database
        toysRepository.saveAndFlush(toys);

        // Get the toys
        restToysMockMvc.perform(get("/api/toys/{id}", toys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(toys.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.avg_price").value(DEFAULT_AVG_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingToys() throws Exception {
        // Get the toys
        restToysMockMvc.perform(get("/api/toys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateToys() throws Exception {
        // Initialize the database
        toysRepository.saveAndFlush(toys);
        int databaseSizeBeforeUpdate = toysRepository.findAll().size();

        // Update the toys
        Toys updatedToys = toysRepository.findOne(toys.getId());
        updatedToys
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .category(UPDATED_CATEGORY)
                .avg_price(UPDATED_AVG_PRICE);

        restToysMockMvc.perform(put("/api/toys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedToys)))
                .andExpect(status().isOk());

        // Validate the Toys in the database
        List<Toys> toys = toysRepository.findAll();
        assertThat(toys).hasSize(databaseSizeBeforeUpdate);
        Toys testToys = toys.get(toys.size() - 1);
        assertThat(testToys.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testToys.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testToys.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testToys.getAvg_price()).isEqualTo(UPDATED_AVG_PRICE);
    }

    @Test
    @Transactional
    public void deleteToys() throws Exception {
        // Initialize the database
        toysRepository.saveAndFlush(toys);
        int databaseSizeBeforeDelete = toysRepository.findAll().size();

        // Get the toys
        restToysMockMvc.perform(delete("/api/toys/{id}", toys.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Toys> toys = toysRepository.findAll();
        assertThat(toys).hasSize(databaseSizeBeforeDelete - 1);
    }
}

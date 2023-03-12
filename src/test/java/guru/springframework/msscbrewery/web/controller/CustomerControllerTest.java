package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto validCustomerDto;
    private CustomerDto invalidNoNameCustomerDto;

    @Before
    public void setup() {

        validCustomerDto = CustomerDto.builder().id(UUID.randomUUID()).name("Simon").build();
        invalidNoNameCustomerDto = CustomerDto.builder().id(UUID.randomUUID()).build();

    }

    @Test
    public void testPostValidCustomer() throws Exception {

        given(customerService.saveNewCustomer(any())).willReturn(validCustomerDto);
        String validCustomerJSON = objectMapper.writeValueAsString(validCustomerDto);
        mockMvc.perform(post("/api/v1/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validCustomerJSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostInvalidCustomer() throws Exception {
        String invalidCustomerJSON = objectMapper.writeValueAsString(invalidNoNameCustomerDto);
        mockMvc.perform(post("/api/v1/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidCustomerJSON))
                .andExpect(status().isBadRequest());
    }
}

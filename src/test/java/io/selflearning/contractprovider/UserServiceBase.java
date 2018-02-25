package io.selflearning.contractprovider;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContractProviderApplication.class)
public abstract class UserServiceBase {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        User savedUser = new User();
        savedUser.setFirstName("Steevan");
        savedUser.setLastName("Dsouza");
        savedUser.setId(42L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        when(userRepository.findOne(eq(42L))).thenReturn(savedUser);
    }

}

package friends.management.web;

import friends.management.services.FriendsManagementRetrieveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementRetrieveControllerTest {
    @Mock
    private FriendsManagementRetrieveService service;
    @InjectMocks
    private FriendsManagementRetrieveController controller;

    @Test
    public void handle_Failed_return500(){
        when(service.handle()).thenThrow(RuntimeException.class);
        ResponseEntity<Map<String, Object>> actual =
                controller.handle();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

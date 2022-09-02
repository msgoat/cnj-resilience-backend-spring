package group.msg.at.cloud.cloudtrain.adapter.rest.in;

import group.msg.at.cloud.cloudtrain.core.boundary.Welcome;
import group.msg.at.cloud.cloudtrain.core.entity.WelcomeItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/welcome")
@CrossOrigin
public class WelcomeController {

    @Autowired
    Welcome boundary;

    @GetMapping("{userId}")
    public ResponseEntity<WelcomeItems> getWelcomeMessage(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(boundary.collectWelcomeItems(userId));
    }

    @GetMapping("resilient/{userId}")
    public ResponseEntity<WelcomeItems> getWelcomeMessageWithResilience(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(boundary.collectWelcomeItemsWithResilience(userId));
    }
}

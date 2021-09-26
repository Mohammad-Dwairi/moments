package com.mdwairy.momentsapi.friendship;

import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import com.mdwairy.momentsapi.exception.InvalidRequestParamValue;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.INVALID_REQUEST_PARAM_VALUE;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_JSON_KEY;

@RestController
@RequestMapping("{username}/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping
    public List<Friendship> findAllFriends(@PathVariable String username) {
        return friendshipService.findAllFriends(username);
    }

    @GetMapping(params = {"dir"})
    public List<Friendship> findAllPending(@RequestParam String dir, @PathVariable String username) {
        if (dir.equals("outgoing")) {
            return friendshipService.findAllSentAndPendingFriendships(username);
        } else if (dir.equals("incoming")) {
            return friendshipService.findAllReceivedAndPendingFriendships(username);
        }
        throw new InvalidRequestParamValue(INVALID_REQUEST_PARAM_VALUE);
    }

    @PostMapping
    public Friendship createNewFriendship(@RequestBody Map<String, String> body, @PathVariable String username) {
        String key = "friendUsername";
        if (body.containsKey(key)) {
            return friendshipService.createNewFriendship(username, body.get(key));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/{senderUsername}")
    public Friendship alterFriendship(@PathVariable String senderUsername, @PathVariable String username) {
        return friendshipService.acceptFriendship(username, senderUsername);
    }

    @DeleteMapping("/{senderUsername}")
    public void deleteFriendship(@PathVariable String senderUsername, @PathVariable String username) {
        friendshipService.deleteFriendship(username, senderUsername);
    }

}

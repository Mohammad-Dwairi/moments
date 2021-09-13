package com.mdwairy.momentsapi.userinfo.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping
    public List<Friendship> findAll() {
        return friendshipService.findAll();
    }

    @GetMapping(params = {"dir"})
    public List<Friendship> findAllPending(@RequestParam String dir) {
        if (dir.equals("outgoing")) {
            return friendshipService.findAllSentAndPendingFriendships();
        } else if (dir.equals("incoming")) {
            return friendshipService.findAllReceivedAndPendingFriendships();
        } else {
            return friendshipService.findAll();
        }
    }

    @GetMapping("/friends")
    public List<Friendship> findAllFriends() {
        return friendshipService.findAllFriends();
    }

    @PostMapping("/{receiver}")
    public Friendship createNewFriendship(@PathVariable String receiver) {
        return friendshipService.createNewFriendship(receiver);
    }

    @PatchMapping("/{username}")
    public Friendship alterFriendship(@RequestBody Map<String, String> body, @PathVariable String username) {
        if (body.get("action").equalsIgnoreCase("accept")) {
            return friendshipService.acceptFriendship(username);
        } else if (body.get("action").equalsIgnoreCase("reject")) {
            friendshipService.rejectFriendship(username);
            return null;
        }
        return null;
    }

}

package repository;

import exception.NotFoundException;
import model.Post;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {
    private AtomicInteger newId = new AtomicInteger(0);
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(newId.getAndIncrement());
            posts.put(post.getId(), post);
        } else if (!posts.containsKey(post.getId())) {
            posts.put(post.getId(), post);
        } else {
            throw new NotFoundException("Post not found");
        }
        return post;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("Post with id " + id + " not found");
        }
    }
}
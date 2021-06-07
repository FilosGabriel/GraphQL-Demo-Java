package movie_graph.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import movie_graph.model.types.Adress;
import movie_graph.model.types.Post;
import movie_graph.model.types.User;

@Component
public class UsersData {
    private List<User> users;

    public static User extractDataUser(User user) {
        return User.newBuilder()
                   .id(user.getId())
                   .name(user.getName())
                   .birthday(user.getBirthday())
                   .address(user.getAddress())
                   .posts(user.getPosts())
                   .build();
    }

    @PostConstruct
    public void init() {
        Adress ad1 = new Adress("10", "US");
        Adress ad2 = new Adress("101", "RO");
        Adress ad3 = new Adress("102", "UK");
        Post p1 = new Post(UUID.randomUUID()
                               .toString(), "First post", "some random stuff", List.of());
        Post p2 = new Post(UUID.randomUUID()
                               .toString(), "First post", "some random stuff", List.of());
        Post p3 = new Post(UUID.randomUUID()
                               .toString(), "First post", "some random stuff", List.of());
        List<Post> posts = new ArrayList<>();
        User john = new User("1", "John", ad1, "12 august 2010", new ArrayList<>(Arrays.asList(p1)), Arrays.asList());
        User ana = new User("2", "Ana", ad2, "12 01,", new ArrayList<>(Arrays.asList(p2, p3)), Arrays.asList(john));
        User vlad = new User("3", "Vlad", ad3, "12 01,", new ArrayList<>(Arrays.asList()), Arrays.asList(john, ana));
        users = List.of(john, ana, vlad);
    }

    public List<User> getUsers() {
        return users;
    }
}

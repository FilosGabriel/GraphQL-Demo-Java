package movie_graph.fetcher;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import movie_graph.data.UsersData;
import movie_graph.model.DgsConstants;
import movie_graph.model.types.CreatePost;
import movie_graph.model.types.Post;
import movie_graph.model.types.User;

@DgsComponent
public class MutationsPosts {
    private final UsersData usersData;
    private final AtomicInteger idGenerator = new AtomicInteger(3);

    public MutationsPosts(UsersData usersData) {
        this.usersData = usersData;
    }

    @DgsMutation(field = DgsConstants.MUTATION.AddPosts)
    public User addPost(@InputArgument String id, @InputArgument("postInput") CreatePost postInput) {
        Post post = new Post();
        post.setId(String.valueOf(idGenerator.addAndGet(1)));
        post.setTitle(postInput.getTitle());
        post.setContent(postInput.getContent());
        post.setComments(List.of());
        Consumer<User> addPost = user -> user.getPosts()
                                             .add(post);
        Predicate<User> filterById = user -> user.getId()
                                                 .equals(id);
        return usersData.getUsers()
                        .stream()
                        .filter(filterById)
                        .findFirst()
                        .stream()
                        .peek(addPost)
                        .findFirst()
                        .orElseGet(() -> null);
    }
}

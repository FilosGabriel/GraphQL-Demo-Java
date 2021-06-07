package movie_graph.fetcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import movie_graph.data.UsersData;
import movie_graph.model.DgsConstants;
import movie_graph.model.types.Post;
import movie_graph.model.types.User;

@DgsComponent
public class FieldFetcher {
    private final UsersData usersData;

    public FieldFetcher(UsersData usersData) {
        this.usersData = usersData;
    }

    @DgsData(parentType = DgsConstants.USER.TYPE_NAME, field = DgsConstants.USER.Posts)
    public List<Post> getPostForUser(DgsDataFetchingEnvironment dfe) {
        User source = dfe.getSource();
        int size = dfe.getArgumentOrDefault("size", 3);
        return usersData.getUsers()
                        .stream()
                        .filter(user -> source.getId()
                                              .equals(user.getId()))
                        .map(User::getPosts)
                        .flatMap(Collection::stream)
                        .limit(size)
                        .collect(Collectors.toList());
    }

    @DgsData(parentType = DgsConstants.USER.TYPE_NAME, field = DgsConstants.USER.Followers)
    public List<User> getFollowers(DgsDataFetchingEnvironment dfe) {
        User source = dfe.getSource();
        return usersData.getUsers()
                        .stream()
                        .filter(user -> source.getId()
                                              .equals(user.getId()))
                        .map(User::getFollowers)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
    }
}

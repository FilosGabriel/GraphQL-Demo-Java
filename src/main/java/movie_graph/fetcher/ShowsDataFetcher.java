package movie_graph.fetcher;

import java.util.List;
import java.util.function.Predicate;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import movie_graph.data.UsersData;
import movie_graph.model.DgsConstants;
import movie_graph.model.types.User;

@DgsComponent
public class ShowsDataFetcher {
    private final UsersData usersData;

    public ShowsDataFetcher(UsersData usersData) {
        this.usersData = usersData;
    }

    @DgsQuery(field = DgsConstants.QUERY.User)
    public User findUserById(@InputArgument String id) {
        Predicate<User> filterById = user -> user.getId()
                                                 .equals(id);
        return usersData.getUsers()
                        .stream()
                        .filter(filterById)
                        .findFirst()
                        .map(UsersData::extractDataUser)
                        .orElse(null);
    }

    @DgsQuery(field = DgsConstants.QUERY.All)
    public List<User> getAllUsers() {
        return usersData.getUsers();
    }

}

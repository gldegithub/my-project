import org.leo.energy.common.utils.JsonUtil;
import org.leo.energy.service.boot.log.model.LogError;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.util.ObjectUtils;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @Author:gonglong
 * @Date:2022/4/24 14:07
 */
public class MonoFlusTest {
    public static void main(String[] args) {
/*        CompletableFuture<LogError> test = CompletableFuture.supplyAsync(() -> {
            LogError logError = new LogError();
            logError.setFileName("test");
            return logError;
        });
        ReactiveAdapter adapter = ReactiveAdapterRegistry.getSharedInstance().getAdapter(test.getClass());
        if (adapter != null) {
            adapter.toPublisher(test).subscribe(new ApplicationListenerMethodAdapter.EventPublicationSubscriber());
        }*/
    }

}

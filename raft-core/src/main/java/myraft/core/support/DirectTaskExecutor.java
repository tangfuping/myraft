package myraft.core.support;

import com.google.common.util.concurrent.FutureCallback;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author fptang
 * @date 2021/10/27
 */
public class DirectTaskExecutor implements TaskExecutor{
    @Nonnull
    @Override
    public Future<?> submit(@Nonnull Runnable task) {
        FutureTask<?> futureTask = new FutureTask<>(task, null);
        futureTask.run();
        return futureTask;
    }

    @Nonnull
    @Override
    public <V> Future<V> submit(@Nonnull Callable<V> task) {
        FutureTask<V> futureTask = new FutureTask<V>(task);
        futureTask.run();
        return futureTask;
    }

    @Override
    public void submit(@Nonnull Runnable task, @Nonnull FutureCallback<Object> callback) {

    }

    @Override
    public void submit(@Nonnull Runnable task, @Nonnull Collection<FutureCallback<Object>> callbacks) {

    }

    @Override
    public void shutdown() throws InterruptedException {

    }
}

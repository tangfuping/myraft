package myraft.core.support;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * @author fptang
 * @date 2021/10/27
 * @desc 异步单线程的实现
 */
public class SingleThreadTaskExecutor implements TaskExecutor {
    private final ExecutorService executorService;

    // 构造函数，默认
    public SingleThreadTaskExecutor() {
        this(Executors.defaultThreadFactory());
    }

    // 构造函数，指定名称
    public SingleThreadTaskExecutor(String name) {
        this(r -> new Thread(r, name));
    }

    // 构造函数，指定ThreadFactory
    private SingleThreadTaskExecutor(ThreadFactory threadFactory) {
        executorService = Executors.newSingleThreadExecutor(threadFactory);
    }

    @Nonnull
    @Override
    public Future<?> submit(@Nonnull Runnable task) {
        return executorService.submit(task);
    }

    @Nonnull
    @Override
    public <V> Future<V> submit(@Nonnull Callable<V> task) {
        return executorService.submit(task);
    }

    @Override
    public void submit(@Nonnull Runnable task, @Nonnull FutureCallback<Object> callback) {

    }

    @Override
    public void submit(@Nonnull Runnable task, @Nonnull Collection<FutureCallback<Object>> callbacks) {
        Preconditions.checkNotNull(task);
        Preconditions.checkNotNull(callbacks);
        executorService.submit(() -> {
            try {
                task.run();
                callbacks.forEach(c -> c.onSuccess(null));
            } catch (Exception e) {
                callbacks.forEach(c -> c.onFailure(e));
            }
        });
    }

    @Override
    public void shutdown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}

package pvt19grupp1.kunskapp.com.kunskapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    public static AppExecutors instance;
    public static AppExecutors getInstance() {
        if(instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    private Executor mBackgroundExecutor = Executors.newSingleThreadExecutor();

    public ScheduledExecutorService networkIO() {
        return mNetworkIO;
    }

}

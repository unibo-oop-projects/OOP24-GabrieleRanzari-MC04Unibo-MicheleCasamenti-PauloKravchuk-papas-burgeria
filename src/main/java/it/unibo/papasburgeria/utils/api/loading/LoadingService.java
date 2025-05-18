package it.unibo.papasburgeria.utils.api.loading;

/**
 * Service responsible for the execution of loading tasks in preparation of the game.
 */
public interface LoadingService {

    /**
     * Registers a task within the service, to be executed when calling {@link LoadingService#loadRegisteredTasks()}.
     * During or after the process of loading, calling this method will not be possible.
     *
     * @param displayName name used for displaying the task (log/view)
     * @param task        task to be executed
     */
    void registerTask(String displayName, Runnable task);

    /**
     * Loads all registered tasks within the service, can be called only once.
     */
    void loadRegisteredTasks();
}

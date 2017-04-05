package com.mstiehr_dev.gitbrowser.net.api;

import com.mstiehr_dev.gitbrowser.model.Task;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface TaskService
{

    @GET("/tasks/{id}/subtasks")
    List<Task> listSubTasks(@Path("id") String taskId);
}

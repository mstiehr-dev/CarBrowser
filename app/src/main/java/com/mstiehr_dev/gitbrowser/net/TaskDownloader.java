package com.mstiehr_dev.gitbrowser.net;

import com.mstiehr_dev.gitbrowser.net.api.TaskService;

import retrofit.RestAdapter;

public class TaskDownloader
{

    public static void download()
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("localhost:8080").build();

        TaskService taskService = restAdapter.create(TaskService.class);

        taskService.listSubTasks("1");
    }
}

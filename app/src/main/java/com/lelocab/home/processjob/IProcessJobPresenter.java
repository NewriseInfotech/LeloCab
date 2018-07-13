package com.lelocab.home.processjob;


/**
 * Created by admin on 1/7/2015.
 */
public interface IProcessJobPresenter {

    void startJobSearching(long jobId);

    void cancelJobSearching();

    void fetchSuitableJob();

    void setProcessView(IProcessJobView processView);

}

package com.lelocab.main;

import com.lelocab.notification.NotificationResponseModel;

/**
 * Created by ashish on 13-05-2017.
 */

public interface IMainActivityPresenter {
    void Logout();


    void GetCompleteRideDetail(NotificationResponseModel notificationResponseModel);
}

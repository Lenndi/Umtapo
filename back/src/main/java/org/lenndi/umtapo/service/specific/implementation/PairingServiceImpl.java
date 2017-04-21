package org.lenndi.umtapo.service.specific.implementation;

import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by axel on 09/04/17.
 */
@Service
public class PairingServiceImpl {

    private static final Integer PERIOD = 5000;
    private static Integer userId;
    private Integer deviceId;


    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param fUserId the user id
     */
    public void setUserIdFuture(Integer fUserId) {
        PairingServiceImpl.userId = fUserId;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                PairingServiceImpl.userId = null;
            }
        }, 0, PERIOD);
    }

    private void setUserIdNull() {
        userId = null;
    }

    /**
     * Gets device id.
     *
     * @return the device id
     */
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     * Sets device id.
     *
     * @param deviceId the device id
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
}

package org.lendi.umtapo.ingosi.service;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Connection MQTT Service.
 *
 * Created by axel on 14/04/17.
 */
@Service
public class MqttConnectionService implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = Logger.getLogger(MqttConnectionService.class);

    @Value("${mqtt.topic.main}")
    private String topic;
    @Value("${mqtt.qos}")
    private Integer qos;
    @Value("${mqtt.broker}")
    private String broker;
    @Value("${mqtt.client.id}")
    private String clientId;
    @Value("${mqtt.port}")
    private String port;
    private MemoryPersistence persistence = new MemoryPersistence();


    /**
     * Connect to MQTT broker.
     */
    private void connect() {

        try {
            MqttClient sampleClient = new MqttClient(broker + port, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            LOGGER.info("Connecting to topic: " + topic);
            LOGGER.info("Connecting to broker: " + broker + port);
            sampleClient.connect(connOpts);
            LOGGER.info("Connected");
        } catch (MqttException me) {
            LOGGER.info("reason " + me.getReasonCode());
            LOGGER.info("msg " + me.getMessage());
            LOGGER.info("loc " + me.getLocalizedMessage());
            LOGGER.info("cause " + me.getCause());
            LOGGER.info("excep " + me);
            me.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.connect();
    }
}

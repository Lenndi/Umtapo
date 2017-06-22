package org.lenndi.umtapo.ingosi.service;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.lenndi.umtapo.service.specific.implementation.PairingAndBorrowingServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Connection MQTT Service.
 * <p>
 * Created by axel on 14/04/17.
 */
@Service
public class MqttConnectionService implements ApplicationListener<ApplicationReadyEvent>, MqttCallback {

    private static final Logger LOGGER = Logger.getLogger(MqttConnectionService.class);

    @Value("${mqtt.topic.main}")
    private String topic;
    @Value("${mqtt.topic.pairing}")
    private String pairingTopic;
    @Value("${mqtt.qos}")
    private Integer qos;
    @Value("${mqtt.broker}")
    private String broker;
    @Value("${mqtt.client.id}")
    private String clientId;
    @Value("${mqtt.port}")
    private String port;
    private MemoryPersistence persistence = new MemoryPersistence();
    private final PairingAndBorrowingServiceImpl pairingAndBorrowingServiceImpl;

    /**
     * Instantiates a new Mqtt connection service.
     *
     * @param pairingAndBorrowingServiceImpl the pairing service
     */
    public MqttConnectionService(PairingAndBorrowingServiceImpl pairingAndBorrowingServiceImpl) {
        this.pairingAndBorrowingServiceImpl = pairingAndBorrowingServiceImpl;
    }


    /**
     * Connect to MQTT broker.
     */
    private void connect() {
        try {
            MqttClient sampleClient = new MqttClient(broker + port, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(this);
            LOGGER.info("Connecting to broker: " + broker + port);
            sampleClient.connect(connOpts);
            sampleClient.subscribe(pairingTopic);
            sampleClient.subscribe(topic);
            LOGGER.info("Connecting to topic: " + topic);
            LOGGER.info("Connected");
        } catch (final MqttException me) {
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

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Override
    public void connectionLost(Throwable throwable) {
    }

    @Override
    public void messageArrived(String topicName, MqttMessage mqttMessage) throws Exception {

        if (topicName.equals(this.pairingTopic)) {
            this.pairingAndBorrowingServiceImpl.setTagId(mqttMessage.toString());
        }
    }
}

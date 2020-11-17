package com.hazelcast.kubernetes;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.KubernetesConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

class ApplicationTest {


    @Test
    void shouldConnectToHazelcastCluster() {
        System.out.println("~~~~~~~~");
        String masterIp = System.getenv("KUBERNETES_MASTER").trim();
        String hardcodedValue = "https://35.241.202.149";
        System.out.println(masterIp);
        System.out.println(hardcodedValue);
        for (int i = 0; i < masterIp.length(); i++) {
            char c = masterIp.charAt(i);
//            char c1 = hardcodedValue.charAt(i);
            System.out.println("Char :" + c);
//            System.out.println("Hard :" + c1);
//            System.out.println(c == c1);
        }
        System.out.println("https://35.241.202.149");
        System.out.println(System.getenv("KUBERNETES_MASTER").trim().equals("https://35.241.202.149"));
        System.out.println(System.getenv("API_TOKEN"));
        System.out.println(System.getenv("CA_CERTIFICATE"));
        ClientConfig config = new ClientConfig();
        config.setInstanceName("dev");
        KubernetesConfig kubernetesConfig = new KubernetesConfig();
        kubernetesConfig.setEnabled(true);
        kubernetesConfig.setProperty("kubernetes-master", masterIp);
        kubernetesConfig.setProperty("kubernetes-master", "https://35.241.202.149");
        kubernetesConfig.setProperty("namespace", "default");
        kubernetesConfig.setProperty("api-token", System.getenv("API_TOKEN"));
        kubernetesConfig.setProperty("ca-certificate", System.getenv("CA_CERTIFICATE"));
        kubernetesConfig.setUsePublicIp(true);
        ClientNetworkConfig networkConfig = new ClientNetworkConfig().setKubernetesConfig(kubernetesConfig);
        config.setNetworkConfig(networkConfig);

        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(config);
        IMap<String, String> mapToPut = hazelcastInstance.getMap("map");
        mapToPut.put("key", "value");

        IMap<Object, Object> mapToGet = hazelcastInstance.getMap("map");
        assertEquals("value", mapToGet.get("key"));
    }
}
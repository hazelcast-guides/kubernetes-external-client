package main

import (
	"context"
	"fmt"
	"log"
	"math/rand"

	"github.com/hazelcast/hazelcast-go-client"
)

func main() {
	config := hazelcast.NewConfig()
	config.ClusterConfig.SetAddress("<EXTERNAL-IP>:5701")
	config.ClusterConfig.SmartRouting = false
	client, err := hazelcast.StartNewClientWithConfig(config)
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Successful connection!")
	log.Println("Starting to fill the map with random entries.")
	ctx := context.TODO()
	m, err := client.GetMap(ctx, "map")
	if err != nil {
		log.Fatal(err)
	}
	for {
		num := rand.Intn(100_000)
		key := fmt.Sprintf("key-%d", num)
		value := fmt.Sprintf("value-%d", num)
		if _, err = m.Put(ctx, key, value); err != nil {
			log.Println("ERR:", err.Error())
		} else {
			if mapSize, err := m.Size(ctx); err != nil {
				log.Println("ERR:", err.Error())
			} else {
				log.Println("Current map size:", mapSize)
			}
		}
	}
}

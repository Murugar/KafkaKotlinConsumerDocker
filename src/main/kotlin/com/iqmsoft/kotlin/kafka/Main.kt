package com.iqmsoft.kotlin.kafka

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

fun recursive(s: String, result: List<String>): List<String> {
    if (s.isEmpty()) {
        return result
    }

    val newResult = mutableListOf<String>()
    val prefix = s.last().toString()
    newResult.add(prefix)
    for (x in result) {
        newResult.add(prefix + x)
    }
    newResult.addAll(result)
    return recursive(
            s.substring(0, s.length - 1),
            newResult
    )
}




fun main(args: Array<String>) {
    // kafka simple consumer
    val consumerConfig = Properties()
    consumerConfig.put("zookeeper.connect", "192.168.99.100:2181")
    consumerConfig.put("bootstrap.servers", "192.168.99.100:9092")
    consumerConfig.put("group.id", "test")
    consumerConfig.put("key.deserializer", StringDeserializer::class.java.name)
    consumerConfig.put("value.deserializer", StringDeserializer::class.java.name)

    val consumer: Consumer<String, String> = KafkaConsumer(consumerConfig)

    consumer.subscribe(mutableListOf("test"))

    while (true) {
        val records = consumer.poll(Long.MAX_VALUE)
        for (record in records) {
            println("${record.offset()}: ${record.value()}")
        }
    }
}

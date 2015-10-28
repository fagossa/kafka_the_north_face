package fr.xebia.xebicon.kafka

import org.I0Itec.zkclient.ZkClient
import org.apache.kafka.clients.producer.KafkaProducer


//TODO STEP_1
object Producer {

  def main(args: Array[String]): Unit = {

    //créer un client Zookeeper
    val zkClient: ZkClient = connectToZookeeper()

    //récupérer les addresses des noeuds Kafka
    val connectionString: String = createBrokerListConnection(zkClient)

    //instanciation du KafkaProducer
    val producer: KafkaProducer[Any, Any] = createKafkaProducer(connectionString)
    
    //écriture
    produceData(producer)

    zkClient.close()
  }

  def connectToZookeeper(): ZkClient = {
    import kafka.utils.ZKStringSerializer

    new ZkClient("127.0.0.1:2181", 10000, 5000, ZKStringSerializer)
  }

  
  def createBrokerListConnection(zkClient: ZkClient): String = {
    import kafka.utils.ZkUtils
    import kafka.cluster.Broker

    def extractConnectionStringFrom(brokers: Seq[Broker]): Seq[String] =
      brokers.map(_.connectionString)

    def join(brokers: Seq[String]): String =
      brokers.mkString(",")

    def brokersFromZk: Seq[Broker] =
      ZkUtils.getAllBrokersInCluster(zkClient)

    join(
      extractConnectionStringFrom(
        brokersFromZk
      )
    )
  }

  def createKafkaProducer(connectionString:String): KafkaProducer[Any, Any] = {
    import scala.collection.convert.wrapAsJava._

    //TODO STEP_1_3
    ???
  }

  def produceData(producer: KafkaProducer[Any, Any]): Unit = {
    import org.apache.kafka.clients.producer.ProducerRecord
    import java.lang.management.ManagementFactory
    import java.time.Instant
    import java.util.concurrent.TimeUnit
    import java.util.concurrent.Future
    import org.apache.kafka.clients.producer.RecordMetadata

    def blockOn[T](javaFuture: Future[T]): T =
      javaFuture.get(5,TimeUnit.SECONDS)

    Stream.continually(Instant.now()).foreach { instant =>
      val averageSystemLoad = ManagementFactory.getOperatingSystemMXBean.getSystemLoadAverage

      def data = s"$instant: avg_load: $averageSystemLoad"
      
      //TODO STEP_1_4
      ???
      
      Thread.sleep(1000)
    }
  }

}
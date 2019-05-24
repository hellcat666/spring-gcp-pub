package springgcppub;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
public class SpringGcpPubApplication {

	  private static final Log LOGGER = LogFactory.getLog(SpringGcpPubApplication.class);

	  public static void main(String[] args) throws IOException {
	    SpringApplication.run(SpringGcpPubApplication.class, args);
	  }

	  /*
	  // Inbound channel adapter.

	  // tag::pubsubInputChannel[]
	  @Bean
	  public MessageChannel pubsubInputChannel() {
	    return new DirectChannel();
	  }
	  // end::pubsubInputChannel[]

	  // tag::messageChannelAdapter[]
	  @Bean
	  public PubSubInboundChannelAdapter messageChannelAdapter(
	      @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
	      PubSubTemplate pubSubTemplate) {
	    PubSubInboundChannelAdapter adapter =
	        new PubSubInboundChannelAdapter(pubSubTemplate, "led-control");
	    adapter.setOutputChannel(inputChannel);
	    adapter.setAckMode(AckMode.MANUAL);

	    return adapter;
	  }
	  // end::messageChannelAdapter[]

	  // tag::messageReceiver[]
	  @Bean
	  @ServiceActivator(inputChannel = "pubsubInputChannel")
	  public MessageHandler messageReceiver() {
	    return message -> {
	      LOGGER.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
	      AckReplyConsumer consumer =
	          (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
	      consumer.ack();
	    };
	  }
	  // end::messageReceiver[]
	  */
	  
	  // Outbound channel adapter

	  // tag::messageSender[]
	  @Bean
	  @ServiceActivator(inputChannel = "pubOutputChannel")
	  public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
	    return (MessageHandler) new PubSubMessageHandler(pubsubTemplate, "led-action");
	  }
	  // end::messageSender[]

	  // tag::messageGateway[]
	  @MessagingGateway(defaultRequestChannel = "pubOutputChannel")
	  public interface PubOutboundGateway {

	    void sendToPub(String text);
	  }
	  // end::messageGateway[]
}

package springgcppub;

import springgcppub.SpringGcpPubApplication.PubOutboundGateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

	  private static final Log LOGGER = LogFactory.getLog(PublishController.class);

	  // tag::autowireGateway[]
	  @Autowired
	  private PubOutboundGateway messagingGateway;
	  // end::autowireGateway[]

	  @GetMapping("/publishMessage")
	  public @ResponseBody String publishMessage(@RequestParam("message") String message) {
	    messagingGateway.sendToPub(message);
		LOGGER.info("SpringGcpPub - Message sent! : " + message);
	    return "SUCCESS: " + message;
	  }

}

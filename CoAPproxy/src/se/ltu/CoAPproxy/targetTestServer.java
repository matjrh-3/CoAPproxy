package se.ltu.CoAPproxy;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.proxy.resources.ForwardingResource;

public class targetTestServer {

  private CoapServer targetServerA;
  private int port;
  private ForwardingResource forwR;

  public targetTestServer(int udpPort, ForwardingResource fr) {
    this.port  = udpPort;
    this.forwR = fr;

    // Create CoAP Server on PORT with proxy resources from CoAP to CoAP and HTTP
    targetServerA = new CoapServer(port);
    targetServerA.add(forwR);
    targetServerA.add(new TargetResource("target"));
	targetServerA.start();
  }

  private static class TargetResource extends CoapResource {
	private int counter = 0;

	public TargetResource(String name) {
	  super(name);
	}
	@Override
	public void handleGET(CoapExchange exchange) {
	  exchange.respond("Response "+(++counter)+" from resource " + getName());
	}
	@Override
	public void handlePOST(CoapExchange exchange) {
	  counter = 0;
	  exchange.respond(ResponseCode.CONTENT);
	}
	@Override
	public void handlePUT(CoapExchange exchange) {
	  exchange.respond(ResponseCode.CONTENT);
	}
  }
}

package se.ltu.CoAPproxy;

import java.io.IOException;
import org.eclipse.californium.proxy.resources.ForwardingResource;
import org.eclipse.californium.proxy.resources.ProxyCoapClientResource;
import org.eclipse.californium.proxy.resources.ProxyHttpClientResource;
import org.eclipse.californium.proxy.ProxyHttpServer;
import org.eclipse.californium.proxy.DirectProxyCoAPResolver;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.CoapResource;
import se.ltu.CoAPproxy.targetTestServer;

public class CoAPproxy {

  private int httpPort = 8085;
  private int coapPort = 5685;

  public CoAPproxy() throws IOException {
	ForwardingResource coap2coap = new ProxyCoapClientResource("coap2coap");

	ProxyHttpServer httpServer = new ProxyHttpServer(httpPort);
	httpServer.setProxyCoapResolver(new DirectProxyCoAPResolver(coap2coap));
	System.out.println("CoAP resource \"target\" available over HTTP at: http://localhost:" + httpPort + "/proxy/coap://localhost:PORT/target");

//	Start internal coap test server
	targetTestServer tts = new targetTestServer(coapPort, coap2coap);
  }


  public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
    new CoAPproxy();
  }
}

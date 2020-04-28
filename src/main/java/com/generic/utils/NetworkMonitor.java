package com.generic.utils;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
/**
 * Monitor network traffic with BrowserMobProxy
 * @author Harshvardhan Yadav(Expleo)
 */
public class NetworkMonitor 
{
	private BrowserMobProxy proxy;
	
	public void startNetworkMonotorProxyServer() {
		proxy = new BrowserMobProxyServer();
		proxy.start(0);
 	}

	public void stopNetworkMonotorProxyServer() {
		proxy.stop();
	}
	
	public BrowserMobProxy getNetworkMonitorProxy() {
		return proxy;
	}
}
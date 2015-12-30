/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.insteonresthub.internal;

import java.util.HashMap;

import org.openhab.binding.insteonresthub.InsteonRestHubBindingProvider;
import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;


/**
 * This class is responsible for parsing the binding configuration.
 * 
 * @author Jonathan LaBroad
 * @since 1.8.0
 */
public class InsteonRestHubGenericBindingProvider extends AbstractGenericBindingProvider implements InsteonRestHubBindingProvider {

	/**
	 * {@inheritDoc}
	 */
	public String getBindingType() {
		return "insteonresthub";
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
		//if (!(item instanceof SwitchItem || item instanceof DimmerItem)) {
		//	throw new BindingConfigParseException("item '" + item.getName()
		//			+ "' is of type '" + item.getClass().getSimpleName()
		//			+ "', only Switch- and DimmerItems are allowed - please check your *.items configuration");
		//}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processBindingConfiguration(String context, Item item, String bindingConfig) throws BindingConfigParseException {
		super.processBindingConfiguration(context, item, bindingConfig);
		
		String parsedConfig[] = parseConfigString(bindingConfig);
		
		if (parsedConfig.length != 3) throw new
		BindingConfigParseException("item config must have addr:prodKey#feature format");
		String addr = parsedConfig[0];
		String [] params = parsedConfig[2].split(",");
		String feature = params[0];
		HashMap<String, String> args = new HashMap<String, String>();
		for (int i = 1; i < params.length; i++) {
			String [] kv = params[i].split("=");
			if (kv.length == 2) {
				args.put(kv[0],  kv[1]);
			} else {
				//logger.error("parameter {} does not have format a=b", params[i]);
			}
		}
		
		InsteonRestHubBindingConfig config = new InsteonRestHubBindingConfig(item.getName(), addr, feature, args);
		
		
		addBindingConfig(item, config);		
	}
	
	/**
	 * Parses binding configuration string. The config string has the format:
	 * 
	 * xx.xxx.xxx:productKey#feature,param1=yyy,param2=zzz
	 * 
	 * @param bindingConfig string with binding parameters
	 * @return String array with split arguments: [address,prodKey,features+params]
	 * @throws BindingConfigParseException if parameters are invalid
	 */
	private String[] parseConfigString(String bindingConfig) throws BindingConfigParseException {
		// the config string has the format
		//
		//  xx.xx.xx:productKey#feature
		//
		String shouldBe = "should be address:prodKey#feature, e.g. 28.c3.91:F00.00.01#switch,param=xxx";
		String[] segments = bindingConfig.split("#");
		if (segments.length != 2)
			throw new BindingConfigParseException("invalid item format: " + bindingConfig + ", " + shouldBe);
		String[] dev = segments[0].split(":");
		
		if (dev.length != 2) {
			throw new BindingConfigParseException("missing colon in item format: "
					+ bindingConfig + ", " + shouldBe);
		}
		String addr = dev[0];
		String [] retval = {addr, dev[1], segments[1]};
		//if (!InsteonAddress.s_isValid(addr)) {
		//	String errstr = "invalid insteon or X10 device address: " + addr +
		//				" in items file. Must have format AB.CD.EF or (for X10): H.U";
		//	logger.error(errstr);
		//	throw new BindingConfigParseException(errstr);
		//}
		return retval;
	}
	
	/**
	 * This is a helper class holding binding specific configuration details
	 * 
	 * @author Jonathan LaBroad
	 * @since 1.8.0
	 */
	public class InsteonRestHubBindingConfig implements BindingConfig {
		public InsteonRestHubBindingConfig(String name, String addr, String feature,
										   HashMap<String, String> params)
		{
			this.name = name;
			this.address = addr;
			this.feature = feature;
			this.params = params;
		}
		
		public String name;
		public String insteonID;
		public String address;
		public String feature;
		public HashMap<String,String> params;
	}
	
	@Override
	public InsteonRestHubBindingConfig getInsteonRestHubBindingConfig(String itemName) {
		return (InsteonRestHubBindingConfig) this.bindingConfigs.get(itemName);
	}
	
	
}

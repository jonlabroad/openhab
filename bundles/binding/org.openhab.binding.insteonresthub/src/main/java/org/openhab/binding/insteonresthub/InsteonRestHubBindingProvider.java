/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.insteonresthub;

import org.openhab.binding.insteonresthub.internal.InsteonRestHubGenericBindingProvider.InsteonRestHubBindingConfig;
import org.openhab.core.binding.BindingProvider;

/**
 * @author Jonathan LaBroad
 * @since 1.8.0
 */
public interface InsteonRestHubBindingProvider extends BindingProvider {
	/**
	 * Returns the binding configuration for the item with
	 * this name.
	 * @param itemName the name to get the binding configuration for.
	 * @return the binding configuration.
	 */
	public InsteonRestHubBindingConfig getInsteonRestHubBindingConfig(String itemName);
}

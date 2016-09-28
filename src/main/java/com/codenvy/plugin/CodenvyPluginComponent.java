/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.plugin;

/**
 * Plugin component module
 *
 * @author Stephane Tournie
 */
public interface CodenvyPluginComponent {
    /**
     * Get the name of the component
     *
     * @return the name of the component
     */
    String getName();
}

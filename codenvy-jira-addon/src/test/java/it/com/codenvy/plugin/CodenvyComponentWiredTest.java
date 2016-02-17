/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package it.com.codenvy.plugin;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;
import com.codenvy.plugin.CodenvyPluginComponent;

import static org.junit.Assert.assertEquals;

@RunWith(AtlassianPluginsTestRunner.class)
public class CodenvyComponentWiredTest {
    private final ApplicationProperties  applicationProperties;
    private final CodenvyPluginComponent codenvyPluginComponent;

    public CodenvyComponentWiredTest(ApplicationProperties applicationProperties, CodenvyPluginComponent codenvyPluginComponent) {
        this.applicationProperties = applicationProperties;
        this.codenvyPluginComponent = codenvyPluginComponent;
    }

    @Test
    public void testMyName() {
        assertEquals("names do not match!", "codenvyPluginComponent:" + applicationProperties.getDisplayName(), codenvyPluginComponent.getName());
    }
}

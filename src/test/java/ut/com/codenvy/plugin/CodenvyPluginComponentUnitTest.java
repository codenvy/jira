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
package ut.com.codenvy.plugin;

import org.junit.Test;

import com.codenvy.plugin.CodenvyPluginComponent;
import com.codenvy.plugin.CodenvyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class CodenvyPluginComponentUnitTest {
    @Test
    public void testGetName() {
        CodenvyPluginComponent component = new CodenvyPluginComponentImpl(null);
        assertEquals("names do not match!", "codenvyPluginComponent", component.getName());
    }
}

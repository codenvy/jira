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
package com.codenvy.plugin.customfield;

import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.security.JiraAuthenticationContext;


public class ReviewCustomField extends GenericTextCFType {

    public ReviewCustomField(CustomFieldValuePersister customFieldValuePersister, GenericConfigManager genericConfigManager,
                             TextFieldCharacterLengthValidator textFieldCharacterLengthValidator,
                             JiraAuthenticationContext jiraAuthenticationContext) {

        super(customFieldValuePersister, genericConfigManager, textFieldCharacterLengthValidator, jiraAuthenticationContext);
    }
}

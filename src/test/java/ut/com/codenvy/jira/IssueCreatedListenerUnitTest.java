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
package ut.com.codenvy.jira;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.FieldException;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.codenvy.jira.IssueCreatedListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IssueCreatedListenerUnitTest {

    private EventPublisher mockEventPublisher;
    private IssueService   mockIssueService;
    private FieldManager   mockFieldManager;
    private Appender       mockAppender;

    @Before
    public void setup() {
        mockEventPublisher = mock(EventPublisher.class);
        mockIssueService = mock(IssueService.class);
        mockFieldManager = mock(FieldManager.class);

        ApplicationUser mockUser = mock(ApplicationUser.class);

        MutableIssue mockIssue = mock(MutableIssue.class);
        when(mockIssue.getId()).thenReturn(1L);

        IssueService.IssueResult mockIssueResult = mock(IssueService.IssueResult.class);
        when(mockIssueResult.getIssue()).thenReturn(mockIssue);
        when(mockIssueService.getIssue(mockUser, "ISSUE-KEY")).thenReturn(mockIssueResult);

        IssueInputParameters mockIssueInputParameters = mock(IssueInputParameters.class);
        when(mockIssueService.newIssueInputParameters()).thenReturn(mockIssueInputParameters);

        ErrorCollection mockErrorCollection = mock(ErrorCollection.class);
        when(mockErrorCollection.hasAnyErrors()).thenReturn(false);

        IssueService.UpdateValidationResult mockResult = mock(IssueService.UpdateValidationResult.class);
        when(mockResult.getErrorCollection()).thenReturn(mockErrorCollection);

        when(mockIssueService.validateUpdate(mockUser, mockIssue.getId(), mockIssueInputParameters)).thenReturn(mockResult);

        IssueService.IssueResult mockUpdateIssueResult = mock(IssueService.IssueResult.class);
        when(mockIssueService.update(mockUser, mockResult)).thenReturn(mockUpdateIssueResult);

        // prepare log appender
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(
                ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        mockAppender = mock(Appender.class);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);
    }

    @Test
    public void testOnIssueEventEmptySettings() {
        PluginSettings mockPluginSettings = mock(PluginSettings.class);
        when(mockPluginSettings.get("codenvy.admin.instanceurl")).thenReturn("");
        when(mockPluginSettings.get("codenvy.admin.username")).thenReturn("");
        when(mockPluginSettings.get("codenvy.admin.password")).thenReturn("");
        PluginSettingsFactory mockPluginSettingsFactory = mock(PluginSettingsFactory.class);
        when(mockPluginSettingsFactory.createGlobalSettings()).thenReturn(mockPluginSettings);

        IssueCreatedListener issueCreatedListener =
                new IssueCreatedListener(mockEventPublisher, mockPluginSettingsFactory, mockIssueService, mockFieldManager);


        Issue mockIssue = mock(Issue.class);
        IssueEvent issueEvent = new IssueEvent(mockIssue, null, null, EventType.ISSUE_CREATED_ID);
        // call method to test
        issueCreatedListener.onIssueEvent(issueEvent);

        // check that log message is the expected one
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                LoggingEvent event = (LoggingEvent)argument;
                String levelString = event.getLevel().levelStr;
                String eventMessage = event.getFormattedMessage();
                return ("WARN".equals(levelString) && eventMessage.contains(
                        "At least one of codenvy URL (''), username ('') or password ('') is not set or empty."));
            }
        }));
    }

    @Test
    public void testOnIssueEventNullUser() {

        PluginSettings mockPluginSettings = mock(PluginSettings.class);
        when(mockPluginSettings.get("codenvy.admin.instanceurl")).thenReturn("http://unittest.codenvy.com");
        when(mockPluginSettings.get("codenvy.admin.username")).thenReturn("username");
        when(mockPluginSettings.get("codenvy.admin.password")).thenReturn("password");
        PluginSettingsFactory mockPluginSettingsFactory = mock(PluginSettingsFactory.class);
        when(mockPluginSettingsFactory.createGlobalSettings()).thenReturn(mockPluginSettings);

        IssueCreatedListener issueCreatedListener =
                new IssueCreatedListener(mockEventPublisher, mockPluginSettingsFactory, mockIssueService, mockFieldManager);

        Issue mockIssue = mock(Issue.class);
        Project mockIssueProject = mock(Project.class);
        when(mockIssueProject.getKey()).thenReturn("TEST");
        when(mockIssueProject.getName()).thenReturn("TEST");
        when(mockIssue.getProjectObject()).thenReturn(mockIssueProject);

        IssueEvent issueEvent = new IssueEvent(mockIssue, null, null, EventType.ISSUE_CREATED_ID);
        // call method to test
        issueCreatedListener.onIssueEvent(issueEvent);

        // check that log message is the expected one
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                LoggingEvent event = (LoggingEvent)argument;
                String levelString = event.getLevel().levelStr;
                String eventMessage = event.getFormattedMessage();
                return ("WARN".equals(levelString) && eventMessage.contains("No user given in issue event."));
            }
        }));
    }

    @Test
    public void testOnIssueEventCustomFieldsNull() throws FieldException {

        PluginSettings mockPluginSettings = mock(PluginSettings.class);
        when(mockPluginSettings.get("codenvy.admin.instanceurl")).thenReturn("http://unittest.codenvy.com");
        when(mockPluginSettings.get("codenvy.admin.username")).thenReturn("username");
        when(mockPluginSettings.get("codenvy.admin.password")).thenReturn("password");
        PluginSettingsFactory mockPluginSettingsFactory = mock(PluginSettingsFactory.class);
        when(mockPluginSettingsFactory.createGlobalSettings()).thenReturn(mockPluginSettings);

        Issue mockIssue = mock(Issue.class);
        Project mockIssueProject = mock(Project.class);
        when(mockIssueProject.getKey()).thenReturn("TEST");
        when(mockIssueProject.getName()).thenReturn("TEST");
        when(mockIssue.getProjectObject()).thenReturn(mockIssueProject);

        ApplicationUser mockUser = mock(ApplicationUser.class);
        when(mockFieldManager.getAvailableCustomFields(mockUser, mockIssue)).thenReturn(Collections.EMPTY_SET);

        IssueCreatedListener issueCreatedListener =
                new IssueCreatedListener(mockEventPublisher, mockPluginSettingsFactory, mockIssueService, mockFieldManager);

        IssueEvent issueEvent = new IssueEvent(mockIssue, null, mockUser, EventType.ISSUE_CREATED_ID);
        // call method to test
        issueCreatedListener.onIssueEvent(issueEvent);

        // check that log message is the expected one
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                LoggingEvent event = (LoggingEvent)argument;
                String levelString = event.getLevel().levelStr;
                String eventMessage = event.getFormattedMessage();
                return ("WARN".equals(levelString) &&
                        eventMessage.contains("Field Develop (null) and/or Review (null) are not available for issue null."));
            }
        }));
    }
}

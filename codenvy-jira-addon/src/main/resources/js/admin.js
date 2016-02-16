/*
 * Copyright (c) 2012-2016 Codenvy
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 */
// Javascript used on the Codenvy admin page
AJS.toInit(function() {
    var baseUrl = AJS.contextPath();

    function populateForm() {
        AJS.$.ajax({
            url: baseUrl + "/rest/codenvy-admin/1.0/",
            dataType: "json",
            success: function(config) {
                AJS.$("#instanceUrl").attr("value", config.instanceUrl);
                AJS.$("#username").attr("value", config.username);
                AJS.$("#password").attr("value", config.password);
            }
        });
    }
    function updateConfig() {
        AJS.$.ajax({
            url: baseUrl + "/rest/codenvy-admin/1.0/",
            type: "PUT",
            contentType: "application/json",
            data: '{ "instanceUrl": "' + AJS.$("#instanceUrl").attr("value") + '", "username": "' + AJS.$("#username").attr("value") + '", "password": "' + AJS.$("#password").attr("value") + '" }',
            processData: false
        }).done(function( data ) {
            alert("Codenvy data successfully saved.");
        });
    }
    populateForm();

    // Submit new Codenvy admin data
    AJS.$("#admin").submit(function(e) {
        e.preventDefault();
        updateConfig();
    });
});

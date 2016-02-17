# Codenvy JIRA Plugin
* Issue event listener that generates Develop and Review factories for each new (factory enabled) issue.
* Custom fields Develop and Review that display links to Codenvy workspaces
* Administration page to configure the Codenvy instance, username and password the plugin will use.

## Prerequisites
- Java 8
- JIRA >= 6.4

## Create project 'parent' factories
Each JIRA projects that will be factory enabled must have a parent factory configured in Codenvy.
This factory has to be named same as the key of the JIRA project.

## Install the plugin on your instance
1. As an admin go to Jira Administration > Add-ons page.
2. Click on _Manage add-ons_ on the left menu bar.
3. Click the _Upload add-on_ link at the top right side of the page.
4. Enter the location of the JAR.
5. Click _Upload_.

## Configure
1. As an admin go to Jira Administration > System page.
   Click on _Logging & Profiling_ on the left menu bar.
   Click on _Configure logging level for another package_.
   Add logging for _com.codenvy.jira_ with level INFO.
2. Create your projects (optional - projects may already exist).
3. As an admin go to Jira Administration > Add-ons page.
   Click on _Codenvy Administration_ at the bottom of the left menu bar.
   Configure Codenvy URL, username and password that will be used by the plugin.
   Click Save to persist the configuration.
4. As an admin go to Jira Administration > Issues > Custom Fields.
   Click on _Add Custom Field_.
   In the dialog window click on _Advanced_ in the left bar.
   Choose _Codenvy Develop Field_ and click Next.
   Name your field ('Develop' for instance) and click _Create_.
   Associate the field to the screens you want and click _Update_.
   Do the same to create a _Codenvy Review Field_.
5. Create a new issue.
   Once created go on the issue page and access your Develop or Review workspace in one click.

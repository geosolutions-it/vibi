# vibiingestion
MapStore application for VIBI web manager

To run this application, clone this repository inside the "application" folder of a MapStore instance.
Then run (or build) MapStore with the *application=\<folder_name>* environment variable.

### Example:

Get the code
> \MapStore\mapcomposer\app\applications> git clone __ADD_THIS_REPO_URL__

Run in debug mode

> \MapStore> ant debug -Dapplication=vibiingestion

Build the WAR package

> \MapStore> ant war -Dapplication=vibiingestion


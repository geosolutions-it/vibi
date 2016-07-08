# FrontEnd
MapStore application for VIBI web manager

To run this application, clone this repository inside the "application" folder of a MapStore instance.
Then run (or build) MapStore with the *application=\<folder_name>* environment variable.

### Example:
Get MapStore code
> git clone https://github.com/geosolutions-it/MapStore.git

Get VIBI frontend code
> cd MapStore\mapcomposer\app\applications  
> \MapStore\mapcomposer\app\applications> git clone https://github.com/geosolutions-it/vibi.git

Run in debug mode

> \MapStore> ant debug -Dapplication=vibi/frontend

Build the WAR package

> \MapStore> ant war -Dapplication=vibi/frontend


# BackEnd
The Backend is based on GeoStore, GeoBatch with some custom actions and a custom OpenSDI-Manager2

## GeoBatch

Build the WAR package

> cd backend  
> \backend> mvn clean install

## GeoStore

Follow the [GeoStore documentation](https://github.com/geosolutions-it/geostore/wiki/Building-instructions)

## OpenSDI-Manager2

Get OpenSDI code and move to the C047 branch

> git clone https://github.com/geosolutions-it/OpenSDI-Manager2.git --branch C047

Build the war package

> \OpenSDI-Manager2\src> mvn clean install

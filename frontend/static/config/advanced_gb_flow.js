{
            "ptype": "mxp_geobatch_flows",
            "geoBatchRestURL": "http://localhost:8082/geobatch/rest/",
            "geoStoreRestURL": "http://localhost:9191/geostore/rest/",
            "skipFlowsNotInRunConfigs": true,
            "showConsumersDetails": true,
            "forceOrder": true,
            "consumersPlugins": [
                {
                    "ptype": "importmetadata",
                    "wfsURL": "http://vibi.geo-solutions.it/geoserver/ows",
                    "metadataFeature": "import_metadata",
                    "metadataErrorsFeature": "import_metadata_errors",
                    "metadataNS": "vibi",
                    "wfsVersion": "1.1.0"
                }
            ],
            "autoOpen": true,
            "actionTarget": {
                "target": "north.tbar",
                "index": 1
            }
        },
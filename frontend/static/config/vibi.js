{
    "composerUrl": "",
    "socialUrl": "",
    "start": 0,
    "limit": 20,
    "geoStoreBase": "http://vibi.geo-solutions.it/geostore/rest/",
    "adminUrl": "http://vibi.geo-solutions.it/opensdi2-manager/",
    "msmTimeout": 30000,
    "mediaContent": "./externals/mapmanager/theme/media",
    "header": {
	   "html": "<div class='topbanner'><div id='left-banner'><img src='img/header_01.png' height='86' border='0' /> </div><div id='right-banner'><img src='img/header_02.png' height='86' style='float:right' border='0' /></div></div>",
	   "css": "<style type='text/css'>div.topbanner{background-image: none;background-color:white;background-position:center top;height:100%;}</style>",
	   "container": {
			"border": false,
			"header": false,
			"collapsible": true,
			"collapseMode":  "mini",
			"hideCollapseTool": true,
			"split": true,
			"animCollapse": false,
			"minHeight": 86,
			"maxHeight": 86,
			"height": 86
	   }
    },
    "ASSET": {
        "delete_icon": "./externals/mapmanager/theme/img/user_delete.png",
        "edit_icon": "./externals/mapmanager/theme/img/user_edit.png"
    },
    "locales": [
        [
            "en",
            "English"
        ],
        [
            "it",
            "Italiano"
        ],
        [
            "fr",
            "Français"
        ],
        [
            "de",
            "Deutsch"
        ],
        [
            "es",
            "Español"
        ]
    ],
    "tools": [
        {
            "ptype": "mxp_categoryinitializer",
            "neededCategories": [
                "ARCHIVEDRUNS",
                "ARCHIVEDLOGS"
            ]
        },
        {
            "ptype": "mxp_login",
            "pluginId": "loginTool",
            "actionTarget": {
                "target": "north.tbar",
                "index": 3
            },
            "forceLogin": true
        },
        {
            "ptype": "mxp_languageselector",
            "actionTarget": {
                "target": "north.tbar",
                "index": 7
            }
        }
    ],
    "adminTools": [
        {
            "ptype": "mxp_entity_manger",
            "notDuplicateOutputs": true,
            "buttonText": "Data",
            "setActiveOnOutput": true,
            "closable": false,
            "autoOpen": true,
            "showActionButton": false,
            "actionTarget": {
                "target": "north.tbar",
                "index": 1
            },
            "entities": [
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": true,
                    "basePath": "mvc/vibi/plot/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [
                        {
                            "header": "Plot #",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "Project Name",
                            "mapping": "project_name",
                            "name": "project_name"
                        },
                        {
                            "header": "Plot Name",
                            "mapping": "plot_name",
                            "name": "plot_name"
                        },
                        {
                            "header": "Label",
                            "mapping": "plot_label",
                            "name": "plot_label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "name": "monitoring_event"
                        }
                    ],
                    "createTitle": "Create a new Plot",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Plot",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "projectName",
                            "name": "project_name"
                        },
                        {
                            "mapping": "plotName",
                            "name": "plot_name"
                        },
                        {
                            "mapping": "plotLabel",
                            "name": "plot_label"
                        },
                        {
                            "mapping": "monitoringEvent",
                            "name": "monitoring_event"
                        }
                    ],
                    "iconCls": "vibi_plot_ic",
                    "id": "Plots",
                    "idProperty": "id",
                    "name": "Plot",
                    "pluralName": "Plots",
                    "restful": true,
                    "root": "data"
                }
            ]
        },
        {
            "ptype": "mxp_updater",
            "geoBatchRestURL":"http://vibi.geo-solutions.it/geobatch/rest/",
            "uploadUrl":"http://vibi.geo-solutions.it/opensdi2-manager/mvc/vibi/upload/?folder=/var/tomcats/geobatch/conf/GEOBATCH_CONFIG_DIR/vibi/input",
            "flowId":"mapper",
            "autoOpen": true,
            "closable": false,
            "showActionButton": false,
            "filters": [{ title : "Excel files", extensions : "xls,xlsx" }]
        },
        {
            "ptype": "mxp_usermanager",
            "loginManager": "loginTool",
            "actionTarget":{
              "target": "north.tbar",
              "index": 2
            }
        },
        {
            "ptype": "mxp_login",
            "pluginId": "loginTool",
            "actionTarget": {
                "target": "north.tbar",
                "index": 10
            }
        },
        {
            "ptype": "mxp_languageselector",
            "actionTarget": {
                "target": "north.tbar",
                "index": 20
            }
        }
    ],
    "loggedTools": [
        {
            "ptype": "mxp_entity_manger",
            "notDuplicateOutputs": true,
            "buttonText": "Data",
            "setActiveOnOutput": true,
            "closable": false,
            "autoOpen": true,
            "showActionButton": false,
            "entities": [
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": true,
                    "basePath": "mvc/vibi/plot/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [
                        {
                            "header": "Plot #",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "Project Name",
                            "mapping": "project_name",
                            "name": "project_name"
                        },
                        {
                            "header": "Plot Name",
                            "mapping": "plot_name",
                            "name": "plot_name"
                        },
                        {
                            "header": "Label",
                            "mapping": "plot_label",
                            "name": "plot_label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "name": "monitoring_event"
                        }
                    ],
                    "createTitle": "Create a new Plot",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Plot",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "projectName",
                            "name": "project_name"
                        },
                        {
                            "mapping": "plotName",
                            "name": "plot_name"
                        },
                        {
                            "mapping": "plotLabel",
                            "name": "plot_label"
                        },
                        {
                            "mapping": "monitoringEvent",
                            "name": "monitoring_event"
                        }
                    ],
                    "iconCls": "vibi_plot_ic",
                    "id": "Plots",
                    "idProperty": "id",
                    "name": "Plot",
                    "pluralName": "Plots",
                    "restful": true,
                    "root": "data"
                }
            ]
        },
        {
            "ptype": "mxp_updater",
            "geoBatchRestURL":"http://vibi.geo-solutions.it/geobatch/rest/",
            "uploadUrl":"http://vibi.geo-solutions.it/mvc/vibi/upload/?folder=/var/tomcats/geobatch/conf/GEOBATCH_CONFIG_DIR/vibi/input",
            "flowId":"mapper",
            "setActiveOnOutput": false,
            "autoOpen": true,
            "closable": false,
            "showActionButton": false,
            "autoRefreshState": true,
            "restrictToGroups": [ "uploaders" ],
            "filters": [{ title : "Excel files", extensions : "xls,xlsx" }]
        },
        {
            "ptype": "mxp_login",
            "pluginId": "loginTool",
            "actionTarget": {
                "target": "north.tbar",
                "index": 10
            }
        },
        {
            "ptype": "mxp_languageselector",
            "actionTarget": {
                "target": "north.tbar",
                "index": 20
            }
        }
    ],
    "embedLink": {
        "embeddedTemplateName": "viewer",
        "showDirectURL": false,
        "showQRCode": false,
        "qrCodeSize": 128,
        "appDownloadUrl": "http://demo.geo-solutions.it/share/mapstoremobile/MapStoreMobile.apk"
    }
}

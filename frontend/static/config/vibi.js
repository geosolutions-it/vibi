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
                    "canSearch": true,
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
                    "totalProperty" : "totalCount",
                    "name": "Plot",
                    "pluralName": "Plots",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/species/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch":true,
                    "columns": [
                      {
                          "header": "Veg Id",
                          "mapping": "veg_id",
                          "name": "veg_id"
                      },
                      {
                          "header": "# Scientific Name",
                          "mapping": "scientific_name",
                          "name": "scientific_name"
                      },
                      {
                          "header": "Acronym",
                          "mapping": "acronym",
                          "name": "acronym"
                      },
                      {
                          "header": "Authority",
                          "mapping": "authority",
                          "name": "authority"
                      },
                      {
                          "header": "Aofc",
                          "mapping": "cofc",
                          "name": "cofc"
                      },
                      {
                          "header": "Syn",
                          "mapping": "syn",
                          "name": "syn"
                      },
                      {
                          "header": "Common Name",
                          "mapping": "common_name",
                          "name": "common_name"
                      },
                      {
                          "header": "Family",
                          "mapping": "family",
                          "name": "family"
                      },
                      {
                          "header": "Fn",
                          "mapping": "fn",
                          "name": "fn"
                      },
                      {
                          "header": "Wet",
                          "mapping": "wet",
                          "name": "wet"
                      },
                      {
                          "header": "Form",
                          "mapping": "form",
                          "name": "form"
                      },
                      {
                          "header": "Habit",
                          "mapping": "habit",
                          "name": "habit"
                      },
                      {
                          "header": "Shade",
                          "mapping": "shade",
                          "name": "shade"
                      },
                      {
                          "header": "Usda Id",
                          "mapping": "usda_id",
                          "name": "usda_id"
                      },
                      {
                          "header": "Oh Tore",
                          "mapping": "oh_tore",
                          "name": "oh_tore"
                      },
                      {
                          "header": "Type",
                          "mapping": "type",
                          "name": "type"
                      },
                      {
                          "header": "Oh Status",
                          "mapping": "oh_status",
                          "name": "oh_status"
                      },
                      {
                          "header": "Emp",
                          "mapping": "emp",
                          "name": "emp"
                      },
                      {
                          "header": "Mw",
                          "mapping": "mw",
                          "name": "mw"
                      },
                      {
                          "header": "Ncne",
                          "mapping": "ncne",
                          "name": "ncne"
                      },
                      {
                          "header": "Notes",
                          "mapping": "notes",
                          "name": "notes"
                      }
                    ],
                    "createTitle": "Create a new Specie",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Specie",
                    "fields": [
                      {
                          "mapping": "vegId",
                          "name": "veg_id"
                      },
                      {
                          "mapping": "scientificName",
                          "name": "scientific_name"
                      },
                      {
                          "mapping": "acronym",
                          "name": "acronym"
                      },
                      {
                          "mapping": "authority",
                          "name": "authority"
                      },
                      {
                          "mapping": "cofc",
                          "name": "cofc"
                      },
                      {
                          "mapping": "syn",
                          "name": "syn"
                      },
                      {
                          "mapping": "commonName",
                          "name": "common_name"
                      },
                      {
                          "mapping": "family",
                          "name": "family"
                      },
                      {
                          "mapping": "fn",
                          "name": "fn"
                      },
                      {
                          "mapping": "wet",
                          "name": "wet"
                      },
                      {
                          "mapping": "form",
                          "name": "form"
                      },
                      {
                          "mapping": "habit",
                          "name": "habit"
                      },
                      {
                          "mapping": "shade",
                          "name": "shade"
                      },
                      {
                          "mapping": "usdaId",
                          "name": "usda_id"
                      },
                      {
                          "mapping": "ohTore",
                          "name": "oh_tore"
                      },
                      {
                          "mapping": "type",
                          "name": "type"
                      },
                      {
                          "mapping": "ohStatus",
                          "name": "oh_status"
                      },
                      {
                          "mapping": "emp",
                          "name": "emp"
                      },
                      {
                          "mapping": "mw",
                          "name": "mw"
                      },
                      {
                          "mapping": "ncne",
                          "name": "ncne"
                      },
                      {
                          "mapping": "notes",
                          "name": "notes"
                      },
                    ],
                    "iconCls": "vibi_species_ic",
                    "id": "Species",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Specie",
                    "pluralName": "Species",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleHerbaceous/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                      {
                          "header": "Plot No",
                          "mapping": "plot_no",
                          "name": "plot_no"
                      },
                      {
                          "header": "Module Id",
                          "mapping": "module_id",
                          "name": "module_id"
                      },
                      {
                          "header": "Corner",
                          "mapping": "corner",
                          "name": "corner"
                      },
                      {
                          "header": "Depth",
                          "mapping": "depth",
                          "name": "depth"
                      },
                      {
                          "header": "Species",
                          "mapping": "species",
                          "name": "species"
                      },
                      {
                          "header": "Cover Class Code",
                          "mapping": "cover_class_code",
                          "name": "cover_class_code"
                      }
                    ],
                    "createTitle": "Create a new Plot Module Herbaceous",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Plot Module Herbaceous",
                    "fields": [
                      {
                          "mapping": "plotNo",
                          "name": "plot_no"
                      },
                      {
                          "mapping": "moduleId",
                          "name": "module_id"
                      },
                      {
                          "mapping": "cornerId",
                          "name": "corner"
                      },
                      {
                          "mapping": "depth",
                          "name": "depth"
                      },
                      {
                          "mapping": "species",
                          "name": "species"
                      },
                      {
                          "mapping": "coverClassCode",
                          "name": "cover_class_code"
                      }
                    ],
                    "iconCls": "vibi_plot_module_herbaceous_ic",
                    "id": "Plots Modules Herbaceous",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Herbaceous",
                    "pluralName": "Plots Modules Herbaceous",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/herbaceous_relative_cover/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [],
                    "createTitle": "Create a new Herbaceous Relative Cover",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Relative Cover",
                    "fields": [],
                    "iconCls": "vibi_herbaceous_relative_cover_ic",
                    "id": "Herbaceous Relative Cover",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Herbaceous Relative Cover",
                    "pluralName": "Herbaceous Relative Cover",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plot_module_woody/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [],
                    "createTitle": "Create a new Plot Module Woody",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody",
                    "fields": [],
                    "iconCls": "vibi_plot_module_woody_ic",
                    "id": "Plot Module Woody",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody",
                    "pluralName": "Plots Modules Woody",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/woody_importance_value/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [],
                    "createTitle": "Create a new Woody Importance Value",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Woody Importance Value",
                    "fields": [],
                    "iconCls": "vibi_woody_importance_value_ic",
                    "id": "Woody Importance Value",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Woody Importance Value",
                    "pluralName": "Woody Importance Values",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/biomass/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [],
                    "createTitle": "Create a new Biomass",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass",
                    "fields": [],
                    "iconCls": "vibi_biomass_ic",
                    "id": "Biomass",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Biomass",
                    "pluralName": "Biomass",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
                },
                {

                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/metrics/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header" : "Plot No",
                            "mapping" : "plot_no",
                            "name" : "plot_no"
                        }, {
                            "header" : "Carex Metric Value",
                            "mapping" : "carex_metric_value",
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae Metric Value",
                            "mapping" : "cyperaceae_metric_value",
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot Metric Value",
                            "mapping" : "dicot_metric_value",
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade Metric Value",
                            "mapping" : "shade_metric_value",
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub Metric Value",
                            "mapping" : "shrub_metric_value",
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte Metric Value",
                            "mapping" : "hydrophyte_metric_value",
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP Metric Value",
                            "mapping" : "svp_metric_value",
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio Metric Value",
                            "mapping" : "ap_ratio_metric_value",
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI Metric Value",
                            "mapping" : "fqai_metric_value",
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte Metric Value",
                            "mapping" : "bryophyte_metric_value",
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte Metric Value",
                            "mapping" : "per_hydrophyte_metric_value",
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive Metric Value",
                            "mapping" : "sensitive_metric_value",
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant Metric Value",
                            "mapping" : "tolerant_metric_value",
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids Metric Value",
                            "mapping" : "invasive_graminoids_metric_value",
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree Metric Value",
                            "mapping" : "small_tree_metric_value",
                            "name" : "small_tree_metric_value"
                        }, {
                            "header" : "Subcanopy IV",
                            "mapping" : "subcanopy_iv",
                            "name" : "subcanopy_iv"
                        }, {
                            "header" : "Canopy IV",
                            "mapping" : "canopy_iv",
                            "name" : "canopy_iv"
                        }, {
                            "header" : "Stems HA Wetland Trees",
                            "mapping" : "stems_ha_wetland_trees",
                            "name" : "stems_ha_wetland_trees"
                        }, {
                            "header" : "Stems HA Wetland Shrubs",
                            "mapping" : "stems_ha_wetland_shrubs",
                            "name" : "stems_ha_wetland_shrubs"
                        }
                    ],
                    "createTitle": "Create new Metrics",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Metrics",
                    "fields": [
                        {
                            "mapping" : "plotNo",
                            "name" : "plot_no"
                        },
                        {
                            "mapping" : "carexMetricValue",
                            "name" : "carex_metric_value"
                        },
                        {
                            "mapping" : "cyperaceaeMetricValue",
                            "name" : "cyperaceae_metric_value"
                        },
                        {
                            "mapping" : "dicotMetricValue",
                            "name" : "dicot_metric_value"
                        },
                        {
                            "mapping" : "shadeMetricValue",
                            "name" : "shade_metric_value"
                        },
                        {
                            "mapping" : "shrubMetricValue",
                            "name" : "shrub_metric_value"
                        },
                        {
                            "mapping" : "hydrophyteMetricValue",
                            "name" : "hydrophyte_metric_value"
                        },
                        {
                            "mapping" : "svpMetricValue",
                            "name" : "svp_metric_value"
                        },
                        {
                            "mapping" : "apRatioMetricValue",
                            "name" : "ap_ratio_metric_value"
                        },
                        {
                            "mapping" : "fqaiMetricValue",
                            "name" : "fqai_metric_value"
                        },
                        {
                            "mapping" : "bryophyteMetricValue",
                            "name" : "bryophyte_metric_value"
                        },
                        {
                            "mapping" : "perHydrophyteMetricValue",
                            "name" : "per_hydrophyte_metric_value"
                        },
                        {
                            "mapping" : "sensitiveMetricValue",
                            "name" : "sensitive_metric_value"
                        },
                        {
                            "mapping" : "tolerantMetricValue",
                            "name" : "tolerant_metric_value"
                        },
                        {
                            "mapping" : "invasiveGraminoidsMetricValue",
                            "name" : "invasive_graminoids_metric_value"
                        },
                        {
                            "mapping" : "small_tree",
                            "name" : "small_tree_metric_value"
                        },
                        {
                            "mapping" : "subcanopyIv",
                            "name" : "subcanopy_iv"
                        },
                        {
                            "mapping" : "canopyIv",
                            "name" : "canopy_iv"
                        },
                        {
                            "mapping" : "steamsHaWetlandTrees",
                            "name" : "stems_ha_wetland_trees"
                        },
                        {
                            "mapping" : "steamsHaWetlandShrubs",
                            "name" : "stems_ha_wetland_shrubs"
                        }
                    ],
                    "iconCls": "vibi_plot_module_herbaceous_ic",
                    "id": "Metrics",
                    "idProperty": "id",
                    "name": "Metrics",
                    "pluralName": "Metrics",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults",
                        "query" : "keyword"
                    }
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
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/species/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Specie",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Specie",
                    "fields": [],
                    "iconCls": "vibi_species_ic",
                    "id": "Species",
                    "idProperty": "id",
                    "name": "Specie",
                    "pluralName": "Species",
                    "restful": true,
                    "root": "data"
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plot_module_herbaceous/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Plot Module Herbaceous",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Plot Module Herbaceous",
                    "fields": [],
                    "iconCls": "vibi_plot_module_herbaceous_ic",
                    "id": "Plots Modules Herbaceous",
                    "idProperty": "id",
                    "name": "Plot Module Herbaceous",
                    "pluralName": "Plots Modules Herbaceous",
                    "restful": true,
                    "root": "data"
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/herbaceous_relative_cover/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Herbaceous Relative Cover",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Relative Cover",
                    "fields": [],
                    "iconCls": "vibi_herbaceous_relative_cover_ic",
                    "id": "Herbaceous Relative Cover",
                    "idProperty": "id",
                    "name": "Herbaceous Relative Cover",
                    "pluralName": "Herbaceous Relative Cover",
                    "restful": true,
                    "root": "data"
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plot_module_woody/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Plot Module Woody",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody",
                    "fields": [],
                    "iconCls": "vibi_plot_module_woody_ic",
                    "id": "Plot Module Woody",
                    "idProperty": "id",
                    "name": "Plot Module Woody",
                    "pluralName": "Plots Modules Woody",
                    "restful": true,
                    "root": "data"
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/woody_importance_value/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Woody Importance Value",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Woody Importance Value",
                    "fields": [],
                    "iconCls": "vibi_woody_importance_value_ic",
                    "id": "Woody Importance Value",
                    "idProperty": "id",
                    "name": "Woody Importance Value",
                    "pluralName": "Woody Importance Values",
                    "restful": true,
                    "root": "data"
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/biomass/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Biomass",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass",
                    "fields": [],
                    "iconCls": "vibi_biomass_ic",
                    "id": "Biomass",
                    "idProperty": "id",
                    "name": "Biomass",
                    "pluralName": "Biomass",
                    "restful": true,
                    "root": "data"
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/metric_calculations/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [],
                    "createTitle": "Create a new Metric Calculation",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Metric Calculation",
                    "fields": [],
                    "iconCls": "vibi_metric_calculations_ic",
                    "id": "Metrics Calculations",
                    "idProperty": "id",
                    "name": "Metric Calculation",
                    "pluralName": "Metrics Calculations",
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

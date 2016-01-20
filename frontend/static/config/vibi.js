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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": true,
                    "basePath": "mvc/vibi/plot/",
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
                            "header": "Plot Label",
                            "mapping": "plot_label",
                            "name": "plot_label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "name": "monitoring_event"
                        },
                        {
                            "header": "Date Timer",
                            "mapping": "timestamptz",
                            "name": "timestamptz"
                        },
                        {
                            "header": "Party",
                            "mapping": "party",
                            "name": "party"
                        },
                        {
                            "header": "Plot Not Sampled",
                            "mapping": "plot_not_sampled",
                            "name": "plot_not_sampled"
                        },
                        {
                            "header": "Comment Plot Not Sampled",
                            "mapping": "commentplot_not_sampled",
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "header": "Sampling Quality",
                            "mapping": "sampling_quality",
                            "name": "sampling_quality"
                        },
                        {
                            "header": "Tax Accuracy Vascular",
                            "mapping": "tax_accuracy_vascular",
                            "name": "tax_accuracy_vascular"
                        },
                        {
                            "header": "Tax Accuracy Bryophytes",
                            "mapping": "tax_accuracy_bryophytes",
                            "name": "tax_accuracy_bryophytes"
                        },
                        {
                            "header": "Tax Accuracy Lichens",
                            "mapping": "tax_accuracy_lichens",
                            "name": "tax_accuracy_lichens"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "name": "authority"
                        },
                        {
                            "header": "State",
                            "mapping": "state",
                            "name": "state"
                        },
                        {
                            "header": "County",
                            "mapping": "county",
                            "name": "county"
                        },
                        {
                            "header": "Quadrangle",
                            "mapping": "quadrangle",
                            "name": "quadrangle"
                        },
                        {
                            "header": "Local Place Name",
                            "mapping": "local_place_name",
                            "name": "local_place_name"
                        },
                        {
                            "header": "Land Owner",
                            "mapping": "landowner",
                            "name": "landowner"
                        },
                        {
                            "header": "Xaxis Bearing Of Plot",
                            "mapping": "xaxis_bearing_of_plot",
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "header": "Enter Gps Location In Plot",
                            "mapping": "enter_gps_location_in_plot",
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "header": "Latitude",
                            "mapping": "latitude",
                            "name": "latitude"
                        },
                        {
                            "header": "Longitude",
                            "mapping": "longitude",
                            "name": "longitude"
                        },
                        {
                            "header": "Total Modules",
                            "mapping": "total_modules",
                            "name": "total_modules"
                        },
                        {
                            "header": "Intensive Modules",
                            "mapping": "intensive_modules",
                            "name": "intensive_modules"
                        },
                        {
                            "header": "Plot Configuration",
                            "mapping": "plot_configuration",
                            "name": "plot_configuration"
                        },
                        {
                            "header": "Plot Size For Cover Data Area Ha",
                            "mapping": "plot_size_for_cover_data_area_ha",
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "header": "Estimate Of Per Open Water Entire Site",
                            "mapping": "estimate_of_per_open_water_entire_site",
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "header": "Estimate Of Per Unvegetated Ow Entire Site",
                            "mapping": "estimate_of_perunvegetated_ow_entire_site",
                            "name": "estimate_of_perunvegetated_ow_entire_site"
                        },
                        {
                            "header": "Estimate Per Invasives Entire Site",
                            "mapping": "Estimate_per_invasives_entire_site",
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "header": "Center Line",
                            "mapping": "centerline",
                            "name": "centerline"
                        },
                        {
                            "header": "Oneo Plant",
                            "mapping": "oneo_plant",
                            "name": "oneo_plant"
                        },
                        {
                            "header": "Oneo Text",
                            "mapping": "oneo_text",
                            "name": "oneo_text"
                        },
                        {
                            "header": "Veg Class",
                            "mapping": "vegclass",
                            "name": "vegclass"
                        },
                        {
                            "header": "Veg Subclass",
                            "mapping": "vegsubclass",
                            "name": "vegsubclass"
                        },
                        {
                            "header": "Twoo Plant",
                            "mapping": "twoo_plant",
                            "name": "twoo_plant"
                        },
                        {
                            "header": "Hgm Class",
                            "mapping": "hgmclass",
                            "name": "hgmclass"
                        },
                        {
                            "header": "Hgm Subclass",
                            "mapping": "hgmsubclass",
                            "name": "hgmsubclass"
                        },
                        {
                            "header": "Twoo Hgm",
                            "mapping": "twoo_hgm",
                            "name": "twoo_hgm"
                        },
                        {
                            "header": "Hgm Group",
                            "mapping": "hgmgroup",
                            "name": "hgmgroup"
                        },
                        {
                            "header": "Oneo Class Code Mod Nature Serve",
                            "mapping": "oneo_class_code_mod_natureServe",
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "header": "Veg Class Wetlands Only",
                            "mapping": "veg_class_wetlands_only",
                            "name": "veg_class_wetlands_only"
                        },
                        {
                            "header": "Landform Type",
                            "mapping": "landform_type",
                            "name": "landform_type"
                        },
                        {
                            "header": "Homogeneity",
                            "mapping": "homogeneity",
                            "name": "homogeneity"
                        },
                        {
                            "header": "Stand Size",
                            "mapping": "stand_size",
                            "name": "stand_size"
                        },
                        {
                            "header": "Drainage",
                            "mapping": "drainage",
                            "name": "drainage"
                        },
                        {
                            "header": "Salinity",
                            "mapping": "salinity",
                            "name": "salinity"
                        },
                        {
                            "header": "Hydrologic Regime",
                            "mapping": "hydrologic_regime",
                            "name": "hydrologic_regime"
                        },
                        {
                            "header": "Oneo Disturbance Type",
                            "mapping": "oneo_disturbance_type",
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "header": "Oneo Disturbance Severity",
                            "mapping": "oneo_disturbance_severity",
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "header": "Oneo Disturbance Years Ago",
                            "mapping": "oneo_disturbance_years_ago",
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "header": "Oneo Distubance Per Of Plot",
                            "mapping": "oneo_distubance_per_of_plot",
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "header": "Oneo Disturbance Description",
                            "mapping": "oneo_disturbance_description",
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "header": "Twoo Disturbance Type",
                            "mapping": "twoo_disturbance_type",
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "header": "Twoo Disturbance Severity",
                            "mapping": "twoo_disturbance_severity",
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "header": "Twoo Disturbance Years Ago",
                            "mapping": "twoo_disturbance_years_ago",
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "header": "Twoo Distubance Per Of Plot",
                            "mapping": "twoo_distubance_per_of_plot",
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "header": "Twoo Disturbance Description",
                            "mapping": "twoo_disturbance_description",
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "header": "Threeo Disturbance Type",
                            "mapping": "threeo_disturbance_type",
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "header": "Threeo Disturbance Severity",
                            "mapping": "threeo_disturbance_severity",
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "header": "Threeo Disturbance Years Ago",
                            "mapping": "threeo_disturbance_years_ago",
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "header": "Threeo Distubance Per Of Plot",
                            "mapping": "threeo_distubance_per_of_plot",
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "header": "Threeo Disturbance Description",
                            "mapping": "threeo_disturbance_description",
                            "name": "threeo_disturbance_description"
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
                        },
                        {
                            "mapping": "dateTimer",
                            "name": "timestamptz"
                        },
                        {
                            "mapping": "party",
                            "name": "party"
                        },
                        {
                            "mapping": "plotNotSampled",
                            "name": "plot_not_sampled"
                        },
                        {
                            "mapping": "commentPlotNotSampled",
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "mapping": "samplingQuality",
                            "name": "sampling_quality"
                        },
                        {
                            "mapping": "taxAccuracyVascular",
                            "name": "tax_accuracy_vascular"
                        },
                        {
                            "mapping": "taxAccuracyBryophytes",
                            "name": "tax_accuracy_bryophytes"
                        },
                        {
                            "mapping": "taxAccuracyLichens",
                            "name": "tax_accuracy_lichens"
                        },
                        {
                            "mapping": "authority",
                            "name": "authority"
                        },
                        {
                            "mapping": "state",
                            "name": "state"
                        },
                        {
                            "mapping": "county",
                            "name": "county"
                        },
                        {
                            "mapping": "quadrangle",
                            "name": "quadrangle"
                        },
                        {
                            "mapping": "localPlaceName",
                            "name": "local_place_name"
                        },
                        {
                            "mapping": "landOwner",
                            "name": "landowner"
                        },
                        {
                            "mapping": "xaxisBearingOfPlot",
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "mapping": "enterGpsLocationInPlot",
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "mapping": "latitude",
                            "name": "latitude"
                        },
                        {
                            "mapping": "longitude",
                            "name": "longitude"
                        },
                        {
                            "mapping": "totalModule",
                            "name": "total_modules"
                        },
                        {
                            "mapping": "intensiveModules",
                            "name": "intensive_modules"
                        },
                        {
                            "mapping": "plotConfiguration",
                            "name": "plot_configuration"
                        },
                        {
                            "mapping": "plotSizeForCoverDataAreaHa",
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "mapping": "estimateOfPerOpenWaterEntireSite",
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "mapping": "estimateOfPerunvegetatedOwEntireSite",
                            "name": "estimate_of_perunvegetated_ow_entire_site"
                        },
                        {
                            "mapping": "estimatePerInvasivesEntireSite",
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "mapping": "centerline",
                            "name": "centerline"
                        },
                        {
                            "mapping": "oneoPlant",
                            "name": "oneo_plant"
                        },
                        {
                            "mapping": "oneoText",
                            "name": "oneo_text"
                        },
                        {
                            "mapping": "vegclass",
                            "name": "vegclass"
                        },
                        {
                            "mapping": "vegsubclass",
                            "name": "vegsubclass"
                        },
                        {
                            "mapping": "twooPlant",
                            "name": "twoo_plant"
                        },
                        {
                            "mapping": "hgmclass",
                            "name": "hgmclass"
                        },
                        {
                            "mapping": "hgmsubclass",
                            "name": "hgmsubclass"
                        },
                        {
                            "mapping": "twooHgm",
                            "name": "twoo_hgm"
                        },
                        {
                            "mapping": "hgmgroup",
                            "name": "hgmgroup"
                        },
                        {
                            "mapping": "oneoClassCodeModNatureServe",
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "mapping": "vegClassWetlandsOnly",
                            "name": "veg_class_wetlands_only"
                        },
                        {
                            "mapping": "landformType",
                            "name": "landform_type"
                        },
                        {
                            "mapping": "homogeneity",
                            "name": "homogeneity"
                        },
                        {
                            "mapping": "standSize",
                            "name": "stand_size"
                        },
                        {
                            "mapping": "drainage",
                            "name": "drainage"
                        },
                        {
                            "mapping": "salinity",
                            "name": "salinity"
                        },
                        {
                            "mapping": "hydrologicRegime",
                            "name": "hydrologic_regime"
                        },
                        {
                            "mapping": "oneoDisturbanceType",
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "mapping": "oneoDisturbanceSeverity",
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "mapping": "oneoDisturbanceYearsAgo",
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "mapping": "oneoDistubancePerOfPlot",
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "oneoDisturbanceDescription",
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "mapping": "twooDisturbanceType",
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "mapping": "twooDisturbanceSeverity",
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "mapping": "twooDisturbanceYearsAgo",
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "mapping": "twooDistubancePerOfPlot",
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "twooDisturbanceDescription",
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "mapping": "threeoDisturbanceType",
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "mapping": "threeoDisturbanceSeverity",
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "mapping": "threeoDisturbanceYearsAgo",
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "mapping": "threeoDistubancePerOfPlot",
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "threeoDisturbanceDescription",
                            "name": "threeo_disturbance_description"
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/species/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Scientific Name",
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
                            "header": "Cofc",
                            "mapping": "cofc",
                            "name": "cofc"
                        },
                        {
                            "header": "Tolerance",
                            "mapping": "tolerance",
                            "name": "tolerance"
                        },
                        {
                            "header": "Common Name",
                            "mapping": "common_name",
                            "name": "common_name"
                        },
                        {
                            "header": "Ind",
                            "mapping": "family",
                            "name": "family"
                        },
                        {
                            "header": "Hydro",
                            "mapping": "hydro",
                            "name": "hydro"
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
                            "header": "Groupp",
                            "mapping": "groupp",
                            "name": "groupp"
                        },
                        {
                            "header": "Nativity",
                            "mapping": "shade",
                            "name": "shade"
                        },
                        {
                            "header": "Code1",
                            "mapping": "code1",
                            "name": "code1"
                        },
                        {
                            "header": "Code2",
                            "mapping": "code2",
                            "name": "code2"
                        },
                        {
                            "header": "Code3",
                            "mapping": "code3",
                            "name": "code3"
                        },
                        {
                            "header": "Code4",
                            "mapping": "code4",
                            "name": "code4"
                        },
                        {
                            "header": "Code5",
                            "mapping": "code5",
                            "name": "code5"
                        }
                    ],
                    "createTitle": "Create a new Specie",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Specie",
                    "fields": [
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
                            "mapping": "tolerance",
                            "name": "tolerance"
                        },
                        {
                            "mapping": "commonName",
                            "name": "common_name"
                        },
                        {
                            "mapping": "ind",
                            "name": "family"
                        },
                        {
                            "mapping": "hydro",
                            "name": "hydro"
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
                            "mapping": "groupp",
                            "name": "groupp"
                        },
                        {
                            "mapping": "nativity",
                            "name": "shade"
                        },
                        {
                            "mapping": "code1",
                            "name": "code1"
                        },
                        {
                            "mapping": "code2",
                            "name": "code2"
                        },
                        {
                            "mapping": "code3",
                            "name": "code3"
                        },
                        {
                            "mapping": "code4",
                            "name": "code4"
                        },
                        {
                            "mapping": "code5",
                            "name": "code5"
                        }
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
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
                            "header": "Corner Id",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleHerbaceousInfo/",
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
                            "header": "Corner Id",
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "name": "depth"
                        },
                        {
                            "header": "Info",
                            "mapping": "info",
                            "name": "info"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "name": "cover_class_code"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Herbaceous Info",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Plot Module Herbaceous Info",
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
                          "mapping": "info",
                          "name": "info"
                        },
                        {
                          "mapping": "coverClassCode",
                          "name": "cover_class_code"
                        }
                    ],
                    "iconCls": "vibi_plot_module_herbaceous_info_ic",
                    "id": "Plots Modules Herbaceous Info",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Herbaceous Info",
                    "pluralName": "Plots Modules Herbaceous Info",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/herbaceousRelativeCover/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "name": "relative_cover"
                        }
                    ],
                    "createTitle": "Create a new Herbaceous Relative Cover",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Relative Cover",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "relativeCover",
                            "name": "relative_cover"
                        }
                    ],
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleWoodyRaw/",
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
                            "header": "Sub",
                            "mapping": "sub",
                            "name": "sub"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "name": "count"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Woody Raw",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody Raw",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "sub",
                            "name": "sub"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "dbhClass",
                            "name": "dbh_class"
                        },
                        {
                            "mapping": "dbhClassIndex",
                            "name": "dbh_class_index"
                        },
                        {
                            "mapping": "count",
                            "name": "count"
                        }
                    ],
                    "iconCls": "vibi_plot_module_woody_raw_ic",
                    "id": "Plot Module Woody Raw",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Raw",
                    "pluralName": "Plots Modules Woody Raw",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleWoodyDbh/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "name": "count"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Woody Dbh",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody Dbh",
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
                          "mapping": "species",
                          "name": "species"
                        },
                        {
                          "mapping": "dbhClass",
                          "name": "dbh_class"
                        },
                        {
                          "mapping": "dbhClassIndex",
                          "name": "dbh_class_index"
                        },
                        {
                          "mapping": "count",
                          "name": "count"
                        }
                    ],
                    "iconCls": "vibi_plot_module_woody_ic_dbh",
                    "id": "Plot Module Woody Dbh",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Dbh",
                    "pluralName": "Plots Modules Woody Dbh",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleWoodyDbhCm/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Dbh Cm",
                            "mapping": "dbh_cm",
                            "name": "dbh_cm"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Woody Dbh Cm",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody Dbh Cm",
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
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "dbhClass",
                            "name": "dbh_class"
                        },
                        {
                            "mapping": "dbhClassIndex",
                            "name": "dbh_class_index"
                        },
                        {
                            "mapping": "dbhCm",
                            "name": "dbh_cm"
                        }
                    ],
                    "iconCls": "vibi_plot_module_woody_ic_dbh_cm",
                    "id": "Plot Module Woody Dbh Cm",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Dbh Cm",
                    "pluralName": "Plots Modules Woody Dbh Cm",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/woodyImportanceValue/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "name": "canopy_iv"
                        }
                    ],
                    "createTitle": "Create a new Woody Importance Value",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Woody Importance Value",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "subcanopyIvPartial",
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "mapping": "subcanopyIvShade",
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "mapping": "canopyIv",
                            "name": "canopy_iv"
                        }
                    ],
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/biomass/",
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
                            "header": "Date Time",
                            "mapping": "date_time",
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Biomass Weight Grams",
                            "mapping": "biomass_weight_grams",
                            "name": "biomass_weight_grams"
                        },
                        {
                            "header": "Grams Per Square Meter",
                            "mapping": "grams_per_sq_meter",
                            "name": "grams_per_sq_meter"
                        }
                    ],
                    "createTitle": "Create a new Biomass",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass",
                    "fields": [
                        {
                            "mapping": "fid",
                            "name": "fid"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "dateTime",
                            "name": "date_time"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "mapping": "sampleId",
                            "name": "sample_id"
                        },
                        {
                            "mapping": "areaSampled",
                            "name": "area_sampled"
                        },
                        {
                            "mapping": "weightWithBag",
                            "name": "weight_with_bag"
                        },
                        {
                            "mapping": "bagWeight",
                            "name": "bag_weight"
                        },
                        {
                            "mapping": "biomassCollected",
                            "name": "biomass_collected"
                        },
                        {
                            "mapping": "biomassWeightGrams",
                            "name": "biomass_weight_grams"
                        },
                        {
                            "mapping": "gramsPerSqMeter",
                            "name": "grams_per_sq_meter"
                        }
                    ],
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/biomassRaw/",
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
                            "header": "Date Time",
                            "mapping": "date_time",
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "name": "biomass_collected"
                        }
                    ],
                    "createTitle": "Create a new Biomass Raw",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass Raw",
                    "fields": [
                        {
                            "mapping": "fid",
                            "name": "fid"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "dateTime",
                            "name": "date_time"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "mapping": "sampleId",
                            "name": "sample_id"
                        },
                        {
                            "mapping": "areaSampled",
                            "name": "area_sampled"
                        },
                        {
                            "mapping": "weightWithBag",
                            "name": "weight_with_bag"
                        },
                        {
                            "mapping": "bagWeight",
                            "name": "bag_weight"
                        },
                        {
                            "mapping": "biomassCollected",
                            "name": "biomass_collected"
                        }
                    ],
                    "iconCls": "vibi_biomass_raw_ic",
                    "id": "Biomass Raw",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Biomass Raw",
                    "pluralName": "Biomass Raw",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/classCodeModNatureServe/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Code",
                            "mapping": "code",
                            "name": "code"
                        },
                        {
                            "header": "Community Class",
                            "mapping": "community_class",
                            "name": "community_class"
                        }
                    ],
                    "createTitle": "Create a new Class Code Mod Nature Serve",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Class Code Mod Nature Serve",
                    "fields": [
                        {
                            "mapping": "code",
                            "name": "code"
                        },
                        {
                            "mapping": "communityClass",
                            "name": "community_class"
                        }
                    ],
                    "iconCls": "vibi_class_code_mod_nature_serve_ic",
                    "id": "Class Code Mod Nature Serve",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Class Code Mod Nature Serve",
                    "pluralName": "Class Code Mod Nature Serve",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/coverMidpointLookup/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Cover Code",
                            "mapping": "cover_code",
                            "name": "cover_code"
                        },
                        {
                            "header": "Midpoint",
                            "mapping": "midpoint",
                            "name": "midpoint"
                        }
                    ],
                    "createTitle": "Create a new Cover Midpoint",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Cover Midpoint",
                    "fields": [
                        {
                            "mapping": "coverCode",
                            "name": "cover_code"
                        },
                        {
                            "mapping": "midPoint",
                            "name": "midpoint"
                        }
                    ],
                    "iconCls": "vibi_cover_midpoint_ic",
                    "id": "Cover Midpoint",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Cover Midpoint",
                    "pluralName": "Cover Midpoint",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
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
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
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
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Stems HA Wetland Trees",
                            "mapping" : "stems_ha_wetland_trees",
                            "name" : "stems_ha_wetland_trees"
                        }, {
                            "header" : "Stems HA Wetland Shrubs",
                            "mapping" : "stems_ha_wetland_shrubs",
                            "name" : "stems_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "name": "per_bare_ground"
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
                            "mapping" : "vibiType",
                            "name" : "vibi_type"
                        }, 
                        {
                            "mapping" : "score",
                            "name" : "score"
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
                            "mapping" : "biomassMetricValue",
                            "name" : "biomass_metric_value"
                        },
                        {
                            "mapping" : "steamsHaWetlandTrees",
                            "name" : "stems_ha_wetland_trees"
                        },
                        {
                            "mapping" : "steamsHaWetlandShrubs",
                            "name" : "stems_ha_wetland_shrubs"
                        },
                        {
                          "mapping": "perUnvegetated",
                          "name": "per_unvegetated"
                        },
                        {
                          "mapping": "perButtonBush",
                          "name": "per_button_bush"
                        },
                        {
                          "mapping": "perPerennialNativeHydrophytes",
                          "name": "per_perennial_native_hydrophytes"
                        },
                        {
                          "mapping": "perAdventives",
                          "name": "per_adventives"
                        },
                        {
                          "mapping": "perPpenWater",
                          "name": "per_open_water"
                        },
                        {
                          "mapping": "perUnvegetatedOpenWater",
                          "name": "per_unvegetated_open_water"
                        },
                        {
                          "mapping": "perBareGround",
                          "name": "per_bare_ground"
                        }
                    ],
                    "iconCls": "vibi_metric_calculations_ic",
                    "id": "Metrics",
                    "idProperty": "id",
                    "name": "Metrics",
                    "totalProperty" : "totalCount",
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
            "filters": [{ "title" : "Excel files", "extensions" : "xls,xlsx" }]
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": true,
                    "basePath": "mvc/vibi/plot/",
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
                            "header": "Plot Label",
                            "mapping": "plot_label",
                            "name": "plot_label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "name": "monitoring_event"
                        },
                        {
                            "header": "Date Timer",
                            "mapping": "timestamptz",
                            "name": "timestamptz"
                        },
                        {
                            "header": "Party",
                            "mapping": "party",
                            "name": "party"
                        },
                        {
                            "header": "Plot Not Sampled",
                            "mapping": "plot_not_sampled",
                            "name": "plot_not_sampled"
                        },
                        {
                            "header": "Comment Plot Not Sampled",
                            "mapping": "commentplot_not_sampled",
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "header": "Sampling Quality",
                            "mapping": "sampling_quality",
                            "name": "sampling_quality"
                        },
                        {
                            "header": "Tax Accuracy Vascular",
                            "mapping": "tax_accuracy_vascular",
                            "name": "tax_accuracy_vascular"
                        },
                        {
                            "header": "Tax Accuracy Bryophytes",
                            "mapping": "tax_accuracy_bryophytes",
                            "name": "tax_accuracy_bryophytes"
                        },
                        {
                            "header": "Tax Accuracy Lichens",
                            "mapping": "tax_accuracy_lichens",
                            "name": "tax_accuracy_lichens"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "name": "authority"
                        },
                        {
                            "header": "State",
                            "mapping": "state",
                            "name": "state"
                        },
                        {
                            "header": "County",
                            "mapping": "county",
                            "name": "county"
                        },
                        {
                            "header": "Quadrangle",
                            "mapping": "quadrangle",
                            "name": "quadrangle"
                        },
                        {
                            "header": "Local Place Name",
                            "mapping": "local_place_name",
                            "name": "local_place_name"
                        },
                        {
                            "header": "Land Owner",
                            "mapping": "landowner",
                            "name": "landowner"
                        },
                        {
                            "header": "Xaxis Bearing Of Plot",
                            "mapping": "xaxis_bearing_of_plot",
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "header": "Enter Gps Location In Plot",
                            "mapping": "enter_gps_location_in_plot",
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "header": "Latitude",
                            "mapping": "latitude",
                            "name": "latitude"
                        },
                        {
                            "header": "Longitude",
                            "mapping": "longitude",
                            "name": "longitude"
                        },
                        {
                            "header": "Total Modules",
                            "mapping": "total_modules",
                            "name": "total_modules"
                        },
                        {
                            "header": "Intensive Modules",
                            "mapping": "intensive_modules",
                            "name": "intensive_modules"
                        },
                        {
                            "header": "Plot Configuration",
                            "mapping": "plot_configuration",
                            "name": "plot_configuration"
                        },
                        {
                            "header": "Plot Size For Cover Data Area Ha",
                            "mapping": "plot_size_for_cover_data_area_ha",
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "header": "Estimate Of Per Open Water Entire Site",
                            "mapping": "estimate_of_per_open_water_entire_site",
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "header": "Estimate Of Per Unvegetated Ow Entire Site",
                            "mapping": "estimate_of_perunvegetated_ow_entire_site",
                            "name": "estimate_of_perunvegetated_ow_entire_site"
                        },
                        {
                            "header": "Estimate Per Invasives Entire Site",
                            "mapping": "Estimate_per_invasives_entire_site",
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "header": "Center Line",
                            "mapping": "centerline",
                            "name": "centerline"
                        },
                        {
                            "header": "Oneo Plant",
                            "mapping": "oneo_plant",
                            "name": "oneo_plant"
                        },
                        {
                            "header": "Oneo Text",
                            "mapping": "oneo_text",
                            "name": "oneo_text"
                        },
                        {
                            "header": "Veg Class",
                            "mapping": "vegclass",
                            "name": "vegclass"
                        },
                        {
                            "header": "Veg Subclass",
                            "mapping": "vegsubclass",
                            "name": "vegsubclass"
                        },
                        {
                            "header": "Twoo Plant",
                            "mapping": "twoo_plant",
                            "name": "twoo_plant"
                        },
                        {
                            "header": "Hgm Class",
                            "mapping": "hgmclass",
                            "name": "hgmclass"
                        },
                        {
                            "header": "Hgm Subclass",
                            "mapping": "hgmsubclass",
                            "name": "hgmsubclass"
                        },
                        {
                            "header": "Twoo Hgm",
                            "mapping": "twoo_hgm",
                            "name": "twoo_hgm"
                        },
                        {
                            "header": "Hgm Group",
                            "mapping": "hgmgroup",
                            "name": "hgmgroup"
                        },
                        {
                            "header": "Oneo Class Code Mod Nature Serve",
                            "mapping": "oneo_class_code_mod_natureServe",
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "header": "Veg Class Wetlands Only",
                            "mapping": "veg_class_wetlands_only",
                            "name": "veg_class_wetlands_only"
                        },
                        {
                            "header": "Landform Type",
                            "mapping": "landform_type",
                            "name": "landform_type"
                        },
                        {
                            "header": "Homogeneity",
                            "mapping": "homogeneity",
                            "name": "homogeneity"
                        },
                        {
                            "header": "Stand Size",
                            "mapping": "stand_size",
                            "name": "stand_size"
                        },
                        {
                            "header": "Drainage",
                            "mapping": "drainage",
                            "name": "drainage"
                        },
                        {
                            "header": "Salinity",
                            "mapping": "salinity",
                            "name": "salinity"
                        },
                        {
                            "header": "Hydrologic Regime",
                            "mapping": "hydrologic_regime",
                            "name": "hydrologic_regime"
                        },
                        {
                            "header": "Oneo Disturbance Type",
                            "mapping": "oneo_disturbance_type",
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "header": "Oneo Disturbance Severity",
                            "mapping": "oneo_disturbance_severity",
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "header": "Oneo Disturbance Years Ago",
                            "mapping": "oneo_disturbance_years_ago",
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "header": "Oneo Distubance Per Of Plot",
                            "mapping": "oneo_distubance_per_of_plot",
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "header": "Oneo Disturbance Description",
                            "mapping": "oneo_disturbance_description",
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "header": "Twoo Disturbance Type",
                            "mapping": "twoo_disturbance_type",
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "header": "Twoo Disturbance Severity",
                            "mapping": "twoo_disturbance_severity",
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "header": "Twoo Disturbance Years Ago",
                            "mapping": "twoo_disturbance_years_ago",
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "header": "Twoo Distubance Per Of Plot",
                            "mapping": "twoo_distubance_per_of_plot",
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "header": "Twoo Disturbance Description",
                            "mapping": "twoo_disturbance_description",
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "header": "Threeo Disturbance Type",
                            "mapping": "threeo_disturbance_type",
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "header": "Threeo Disturbance Severity",
                            "mapping": "threeo_disturbance_severity",
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "header": "Threeo Disturbance Years Ago",
                            "mapping": "threeo_disturbance_years_ago",
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "header": "Threeo Distubance Per Of Plot",
                            "mapping": "threeo_distubance_per_of_plot",
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "header": "Threeo Disturbance Description",
                            "mapping": "threeo_disturbance_description",
                            "name": "threeo_disturbance_description"
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
                        },
                        {
                            "mapping": "dateTimer",
                            "name": "timestamptz"
                        },
                        {
                            "mapping": "party",
                            "name": "party"
                        },
                        {
                            "mapping": "plotNotSampled",
                            "name": "plot_not_sampled"
                        },
                        {
                            "mapping": "commentPlotNotSampled",
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "mapping": "samplingQuality",
                            "name": "sampling_quality"
                        },
                        {
                            "mapping": "taxAccuracyVascular",
                            "name": "tax_accuracy_vascular"
                        },
                        {
                            "mapping": "taxAccuracyBryophytes",
                            "name": "tax_accuracy_bryophytes"
                        },
                        {
                            "mapping": "taxAccuracyLichens",
                            "name": "tax_accuracy_lichens"
                        },
                        {
                            "mapping": "authority",
                            "name": "authority"
                        },
                        {
                            "mapping": "state",
                            "name": "state"
                        },
                        {
                            "mapping": "county",
                            "name": "county"
                        },
                        {
                            "mapping": "quadrangle",
                            "name": "quadrangle"
                        },
                        {
                            "mapping": "localPlaceName",
                            "name": "local_place_name"
                        },
                        {
                            "mapping": "landOwner",
                            "name": "landowner"
                        },
                        {
                            "mapping": "xaxisBearingOfPlot",
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "mapping": "enterGpsLocationInPlot",
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "mapping": "latitude",
                            "name": "latitude"
                        },
                        {
                            "mapping": "longitude",
                            "name": "longitude"
                        },
                        {
                            "mapping": "totalModule",
                            "name": "total_modules"
                        },
                        {
                            "mapping": "intensiveModules",
                            "name": "intensive_modules"
                        },
                        {
                            "mapping": "plotConfiguration",
                            "name": "plot_configuration"
                        },
                        {
                            "mapping": "plotSizeForCoverDataAreaHa",
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "mapping": "estimateOfPerOpenWaterEntireSite",
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "mapping": "estimateOfPerunvegetatedOwEntireSite",
                            "name": "estimate_of_perunvegetated_ow_entire_site"
                        },
                        {
                            "mapping": "estimatePerInvasivesEntireSite",
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "mapping": "centerline",
                            "name": "centerline"
                        },
                        {
                            "mapping": "oneoPlant",
                            "name": "oneo_plant"
                        },
                        {
                            "mapping": "oneoText",
                            "name": "oneo_text"
                        },
                        {
                            "mapping": "vegclass",
                            "name": "vegclass"
                        },
                        {
                            "mapping": "vegsubclass",
                            "name": "vegsubclass"
                        },
                        {
                            "mapping": "twooPlant",
                            "name": "twoo_plant"
                        },
                        {
                            "mapping": "hgmclass",
                            "name": "hgmclass"
                        },
                        {
                            "mapping": "hgmsubclass",
                            "name": "hgmsubclass"
                        },
                        {
                            "mapping": "twooHgm",
                            "name": "twoo_hgm"
                        },
                        {
                            "mapping": "hgmgroup",
                            "name": "hgmgroup"
                        },
                        {
                            "mapping": "oneoClassCodeModNatureServe",
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "mapping": "vegClassWetlandsOnly",
                            "name": "veg_class_wetlands_only"
                        },
                        {
                            "mapping": "landformType",
                            "name": "landform_type"
                        },
                        {
                            "mapping": "homogeneity",
                            "name": "homogeneity"
                        },
                        {
                            "mapping": "standSize",
                            "name": "stand_size"
                        },
                        {
                            "mapping": "drainage",
                            "name": "drainage"
                        },
                        {
                            "mapping": "salinity",
                            "name": "salinity"
                        },
                        {
                            "mapping": "hydrologicRegime",
                            "name": "hydrologic_regime"
                        },
                        {
                            "mapping": "oneoDisturbanceType",
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "mapping": "oneoDisturbanceSeverity",
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "mapping": "oneoDisturbanceYearsAgo",
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "mapping": "oneoDistubancePerOfPlot",
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "oneoDisturbanceDescription",
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "mapping": "twooDisturbanceType",
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "mapping": "twooDisturbanceSeverity",
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "mapping": "twooDisturbanceYearsAgo",
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "mapping": "twooDistubancePerOfPlot",
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "twooDisturbanceDescription",
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "mapping": "threeoDisturbanceType",
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "mapping": "threeoDisturbanceSeverity",
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "mapping": "threeoDisturbanceYearsAgo",
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "mapping": "threeoDistubancePerOfPlot",
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "threeoDisturbanceDescription",
                            "name": "threeo_disturbance_description"
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/species/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Scientific Name",
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
                            "header": "Cofc",
                            "mapping": "cofc",
                            "name": "cofc"
                        },
                        {
                            "header": "Tolerance",
                            "mapping": "tolerance",
                            "name": "tolerance"
                        },
                        {
                            "header": "Common Name",
                            "mapping": "common_name",
                            "name": "common_name"
                        },
                        {
                            "header": "Ind",
                            "mapping": "family",
                            "name": "family"
                        },
                        {
                            "header": "Hydro",
                            "mapping": "hydro",
                            "name": "hydro"
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
                            "header": "Groupp",
                            "mapping": "groupp",
                            "name": "groupp"
                        },
                        {
                            "header": "Nativity",
                            "mapping": "shade",
                            "name": "shade"
                        },
                        {
                            "header": "Code1",
                            "mapping": "code1",
                            "name": "code1"
                        },
                        {
                            "header": "Code2",
                            "mapping": "code2",
                            "name": "code2"
                        },
                        {
                            "header": "Code3",
                            "mapping": "code3",
                            "name": "code3"
                        },
                        {
                            "header": "Code4",
                            "mapping": "code4",
                            "name": "code4"
                        },
                        {
                            "header": "Code5",
                            "mapping": "code5",
                            "name": "code5"
                        }
                    ],
                    "createTitle": "Create a new Specie",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Specie",
                    "fields": [
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
                            "mapping": "tolerance",
                            "name": "tolerance"
                        },
                        {
                            "mapping": "commonName",
                            "name": "common_name"
                        },
                        {
                            "mapping": "ind",
                            "name": "family"
                        },
                        {
                            "mapping": "hydro",
                            "name": "hydro"
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
                            "mapping": "groupp",
                            "name": "groupp"
                        },
                        {
                            "mapping": "nativity",
                            "name": "shade"
                        },
                        {
                            "mapping": "code1",
                            "name": "code1"
                        },
                        {
                            "mapping": "code2",
                            "name": "code2"
                        },
                        {
                            "mapping": "code3",
                            "name": "code3"
                        },
                        {
                            "mapping": "code4",
                            "name": "code4"
                        },
                        {
                            "mapping": "code5",
                            "name": "code5"
                        }
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
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
                            "header": "Corner Id",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleHerbaceousInfo/",
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
                            "header": "Corner Id",
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "name": "depth"
                        },
                        {
                            "header": "Info",
                            "mapping": "info",
                            "name": "info"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "name": "cover_class_code"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Herbaceous Info",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Plot Module Herbaceous Info",
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
                          "mapping": "info",
                          "name": "info"
                        },
                        {
                          "mapping": "coverClassCode",
                          "name": "cover_class_code"
                        }
                    ],
                    "iconCls": "vibi_plot_module_herbaceous_info_ic",
                    "id": "Plots Modules Herbaceous Info",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Herbaceous Info",
                    "pluralName": "Plots Modules Herbaceous Info",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/herbaceousRelativeCover/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "name": "relative_cover"
                        }
                    ],
                    "createTitle": "Create a new Herbaceous Relative Cover",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Relative Cover",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "relativeCover",
                            "name": "relative_cover"
                        }
                    ],
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleWoodyRaw/",
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
                            "header": "Sub",
                            "mapping": "sub",
                            "name": "sub"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "name": "count"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Woody Raw",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody Raw",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "sub",
                            "name": "sub"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "dbhClass",
                            "name": "dbh_class"
                        },
                        {
                            "mapping": "dbhClassIndex",
                            "name": "dbh_class_index"
                        },
                        {
                            "mapping": "count",
                            "name": "count"
                        }
                    ],
                    "iconCls": "vibi_plot_module_woody_raw_ic",
                    "id": "Plot Module Woody Raw",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Raw",
                    "pluralName": "Plots Modules Woody Raw",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleWoodyDbh/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "name": "count"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Woody Dbh",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody Dbh",
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
                          "mapping": "species",
                          "name": "species"
                        },
                        {
                          "mapping": "dbhClass",
                          "name": "dbh_class"
                        },
                        {
                          "mapping": "dbhClassIndex",
                          "name": "dbh_class_index"
                        },
                        {
                          "mapping": "count",
                          "name": "count"
                        }
                    ],
                    "iconCls": "vibi_plot_module_woody_ic_dbh",
                    "id": "Plot Module Woody Dbh",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Dbh",
                    "pluralName": "Plots Modules Woody Dbh",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/plotModuleWoodyDbhCm/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Dbh Cm",
                            "mapping": "dbh_cm",
                            "name": "dbh_cm"
                        }
                    ],
                    "createTitle": "Create a new Plot Module Woody Dbh Cm",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Herbaceous Plot Module Woody Dbh Cm",
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
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "dbhClass",
                            "name": "dbh_class"
                        },
                        {
                            "mapping": "dbhClassIndex",
                            "name": "dbh_class_index"
                        },
                        {
                            "mapping": "dbhCm",
                            "name": "dbh_cm"
                        }
                    ],
                    "iconCls": "vibi_plot_module_woody_ic_dbh_cm",
                    "id": "Plot Module Woody Dbh Cm",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Dbh Cm",
                    "pluralName": "Plots Modules Woody Dbh Cm",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/woodyImportanceValue/",
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
                            "header": "Species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "name": "canopy_iv"
                        }
                    ],
                    "createTitle": "Create a new Woody Importance Value",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Woody Importance Value",
                    "fields": [
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "subcanopyIvPartial",
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "mapping": "subcanopyIvShade",
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "mapping": "canopyIv",
                            "name": "canopy_iv"
                        }
                    ],
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/biomass/",
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
                            "header": "Date Time",
                            "mapping": "date_time",
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Biomass Weight Grams",
                            "mapping": "biomass_weight_grams",
                            "name": "biomass_weight_grams"
                        },
                        {
                            "header": "Grams Per Square Meter",
                            "mapping": "grams_per_sq_meter",
                            "name": "grams_per_sq_meter"
                        }
                    ],
                    "createTitle": "Create a new Biomass",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass",
                    "fields": [
                        {
                            "mapping": "fid",
                            "name": "fid"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "dateTime",
                            "name": "date_time"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "mapping": "sampleId",
                            "name": "sample_id"
                        },
                        {
                            "mapping": "areaSampled",
                            "name": "area_sampled"
                        },
                        {
                            "mapping": "weightWithBag",
                            "name": "weight_with_bag"
                        },
                        {
                            "mapping": "bagWeight",
                            "name": "bag_weight"
                        },
                        {
                            "mapping": "biomassCollected",
                            "name": "biomass_collected"
                        },
                        {
                            "mapping": "biomassWeightGrams",
                            "name": "biomass_weight_grams"
                        },
                        {
                            "mapping": "gramsPerSqMeter",
                            "name": "grams_per_sq_meter"
                        }
                    ],
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/biomassRaw/",
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
                            "header": "Date Time",
                            "mapping": "date_time",
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "name": "biomass_collected"
                        }
                    ],
                    "createTitle": "Create a new Biomass Raw",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass Raw",
                    "fields": [
                        {
                            "mapping": "fid",
                            "name": "fid"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "dateTime",
                            "name": "date_time"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "corner",
                            "name": "corner"
                        },
                        {
                            "mapping": "sampleId",
                            "name": "sample_id"
                        },
                        {
                            "mapping": "areaSampled",
                            "name": "area_sampled"
                        },
                        {
                            "mapping": "weightWithBag",
                            "name": "weight_with_bag"
                        },
                        {
                            "mapping": "bagWeight",
                            "name": "bag_weight"
                        },
                        {
                            "mapping": "biomassCollected",
                            "name": "biomass_collected"
                        }
                    ],
                    "iconCls": "vibi_biomass_raw_ic",
                    "id": "Biomass Raw",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Biomass Raw",
                    "pluralName": "Biomass Raw",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/classCodeModNatureServe/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Code",
                            "mapping": "code",
                            "name": "code"
                        },
                        {
                            "header": "Community Class",
                            "mapping": "community_class",
                            "name": "community_class"
                        }
                    ],
                    "createTitle": "Create a new Class Code Mod Nature Serve",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Class Code Mod Nature Serve",
                    "fields": [
                        {
                            "mapping": "code",
                            "name": "code"
                        },
                        {
                            "mapping": "communityClass",
                            "name": "community_class"
                        }
                    ],
                    "iconCls": "vibi_class_code_mod_nature_serve_ic",
                    "id": "Class Code Mod Nature Serve",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Class Code Mod Nature Serve",
                    "pluralName": "Class Code Mod Nature Serve",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/coverMidpointLookup/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Cover Code",
                            "mapping": "cover_code",
                            "name": "cover_code"
                        },
                        {
                            "header": "Midpoint",
                            "mapping": "midpoint",
                            "name": "midpoint"
                        }
                    ],
                    "createTitle": "Create a new Cover Midpoint",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Cover Midpoint",
                    "fields": [
                        {
                            "mapping": "coverCode",
                            "name": "cover_code"
                        },
                        {
                            "mapping": "midPoint",
                            "name": "midpoint"
                        }
                    ],
                    "iconCls": "vibi_cover_midpoint_ic",
                    "id": "Cover Midpoint",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Cover Midpoint",
                    "pluralName": "Cover Midpoint",
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
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
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
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
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
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Stems HA Wetland Trees",
                            "mapping" : "stems_ha_wetland_trees",
                            "name" : "stems_ha_wetland_trees"
                        }, {
                            "header" : "Stems HA Wetland Shrubs",
                            "mapping" : "stems_ha_wetland_shrubs",
                            "name" : "stems_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "name": "per_bare_ground"
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
                            "mapping" : "vibiType",
                            "name" : "vibi_type"
                        }, 
                        {
                            "mapping" : "score",
                            "name" : "score"
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
                            "mapping" : "biomassMetricValue",
                            "name" : "biomass_metric_value"
                        },
                        {
                            "mapping" : "steamsHaWetlandTrees",
                            "name" : "stems_ha_wetland_trees"
                        },
                        {
                            "mapping" : "steamsHaWetlandShrubs",
                            "name" : "stems_ha_wetland_shrubs"
                        },
                        {
                          "mapping": "perUnvegetated",
                          "name": "per_unvegetated"
                        },
                        {
                          "mapping": "perButtonBush",
                          "name": "per_button_bush"
                        },
                        {
                          "mapping": "perPerennialNativeHydrophytes",
                          "name": "per_perennial_native_hydrophytes"
                        },
                        {
                          "mapping": "perAdventives",
                          "name": "per_adventives"
                        },
                        {
                          "mapping": "perPpenWater",
                          "name": "per_open_water"
                        },
                        {
                          "mapping": "perUnvegetatedOpenWater",
                          "name": "per_unvegetated_open_water"
                        },
                        {
                          "mapping": "perBareGround",
                          "name": "per_bare_ground"
                        }
                    ],
                    "iconCls": "vibi_metric_calculations_ic",
                    "id": "Metrics",
                    "idProperty": "id",
                    "name": "Metrics",
                    "totalProperty" : "totalCount",
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
            "uploadUrl":"http://vibi.geo-solutions.it/mvc/vibi/upload/?folder=/var/tomcats/geobatch/conf/GEOBATCH_CONFIG_DIR/vibi/input",
            "flowId":"mapper",
            "setActiveOnOutput": false,
            "autoOpen": true,
            "closable": false,
            "showActionButton": false,
            "autoRefreshState": true,
            "restrictToGroups": [ "uploaders" ],
            "filters": [{ "title" : "Excel files", "extensions" : "xls,xlsx" }]
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

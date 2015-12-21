{
    "composerUrl": "",
    "socialUrl": "",
    "start": 0,
    "limit": 20,
    "geoStoreBase": "http://localhost:9191/geostore/rest/",
    "adminUrl": "http://localhost:9192/opensdi2-manager/",
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
                        "limit" : "maxResults"
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
                        "limit" : "maxResults"
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
                        "limit" : "maxResults"
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
                    "columns": [
                        {
                            "header": "plotNo",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "relativeCover",
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
                        "limit" : "maxResults"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plot_module_woody_raw/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [
                        {
                            "header": "plotNo",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "sub",
                            "mapping": "sub",
                            "name": "sub"
                        },
                        {
                            "header": "module_id",
                            "mapping": "module_id",
                            "name": "module_id"
                        },
                        {
                            "header": "species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "dbhClass",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "dbhClassIndex",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "count",
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
                            "mapping": "module_id",
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
                    "iconCls": "vibi_plot_module_woody_ic",
                    "id": "Plot Module Woody Raw",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Raw",
                    "pluralName": "Plots Modules Woody Raw",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plot_module_woody_dbh/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [
                        {
                            "header": "plotNo",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "module_id",
                            "mapping": "module_id",
                            "name": "module_id"
                        },
                        {
                            "header": "species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "dbhClass",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "dbhClassIndex",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "count",
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
                          "mapping": "module_id",
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
                    "iconCls": "vibi_plot_module_woody_ic",
                    "id": "Plot Module Woody Dbh",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Dbh",
                    "pluralName": "Plots Modules Woody Dbh",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults"
                    }
                },
                {
                    "api": {},
                    "autoExpandColumn": "label",
                    "autoload": false,
                    "basePath": "mvc/vibi/plot_module_woody_dbh_cm/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "columns": [
                        {
                            "header": "plotNo",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "module_id",
                            "mapping": "module_id",
                            "name": "module_id"
                        },
                        {
                            "header": "species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "dbhClass",
                            "mapping": "dbh_class",
                            "name": "dbh_class"
                        },
                        {
                            "header": "dbhClassIndex",
                            "mapping": "dbh_class_index",
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "dbhCm",
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
                            "mapping": "module_id",
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
                    "iconCls": "vibi_plot_module_woody_ic",
                    "id": "Plot Module Woody Dbh",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Plot Module Woody Dbh Cm",
                    "pluralName": "Plots Modules Woody Dbh Cm",
                    "restful": true,
                    "root": "data",
                    "paramNames" : {
                        "start" : "firstResult",
                        "limit" : "maxResults"
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
                    "columns": [
                        {
                            "header": "plotNo",
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "header": "species",
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "header": "subcanopyIvPartial",
                            "mapping": "subcanopy_iv_partial",
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "subcanopyIvShade",
                            "mapping": "subcanopy_iv_shade",
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "canopyIv",
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
                        "limit" : "maxResults"
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
                        "limit" : "maxResults"
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
                        "limit" : "maxResults"
                    }
                }
            ]
        },
        {
            "ptype": "mxp_updater",
            "geoBatchRestURL":"http://vibi.geo-solutions.it/geobatch/rest/",
            "uploadUrl":"http://localhost:9192/opensdi2-manager/mvc/admin/updater/upload",
            "flowId":"mapper",
            "autoOpen": true,
            "closable": false,
            "showActionButton": false
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
                            "mapping": "plot_no",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "project_name",
                            "name": "project_name"
                        },
                        {
                            "mapping": "plot_name",
                            "name": "plot_name"
                        },
                        {
                            "mapping": "plot_label",
                            "name": "plot_label"
                        },
                        {
                            "mapping": "monitoring_event",
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
            "uploadUrl":"http://vibi.geo-solutions.it/opensdi2-manager/mvc/admin/updater/upload",
            "flowId":"mapper",
            "setActiveOnOutput": false,
            "autoOpen": true,
            "closable": false,
            "showActionButton": false,
            "autoRefreshState": true
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
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
    "footer":{
        "html": "<p xmlns:dct=\"http://purl.org/dc/terms/\" xmlns:vcard=\"http://www.w3.org/2001/vcard-rdf/3.0#\"> <a rel=\"license\" href=\"http://creativecommons.org/publicdomain/zero/1.0/\"><img src=\"https://licensebuttons.net/p/zero/1.0/88x31.png\" style=\"border-style: none; vertical-align: middle;\" alt=\"CC0\" /></a>To the extent possible under law,  <a rel=\"dct:publisher\" href=\"http://www.clevelandmetroparks.com\"><span property=\"dct:title\">Cleveland Metroparks</span></a> has waived all copyright and related or neighboring rights to this work. This work is published from:<span property=\"vcard:Country\" datatype=\"dct:ISO3166\" content=\"US\" about=\"http://www.clevelandmetroparks.com\">United States</span>.</p>",
        "container": {
            "border": false,
            "header": false,
            "collapsible": false,
            "hideCollapseTool": true,
            "split": true,
            "animCollapse": false,
            "minHeight": 32,
            "maxHeight": 32,
            "height": 32
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Project Name",
                            "mapping": "project_name",
                            "dataIndex": "project_name",
                            "sortable": true,
                            "name": "project_name"
                        },
                        {
                            "header": "Plot Name",
                            "mapping": "plot_name",
                            "dataIndex": "plot_name",
                            "sortable": true,
                            "name": "plot_name"
                        },
                        {
                            "header": "Plot Label",
                            "mapping": "plot_label",
                            "dataIndex": "plot_label",
                            "sortable": true,
                            "name": "plot_label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "dataIndex": "monitoring_event",
                            "sortable": true,
                            "name": "monitoring_event"
                        },
                        {
                            "header": "Date Timer",
                            "mapping": "timestamptz",
                            "dataIndex": "timestamptz",
                            "sortable": true,
                            "name": "timestamptz"
                        },
                        {
                            "header": "Party",
                            "mapping": "party",
                            "dataIndex": "party",
                            "sortable": true,
                            "name": "party"
                        },
                        {
                            "header": "Plot Not Sampled",
                            "mapping": "plot_not_sampled",
                            "dataIndex": "plot_not_sampled",
                            "sortable": true,
                            "name": "plot_not_sampled"
                        },
                        {
                            "header": "Comment Plot Not Sampled",
                            "mapping": "commentplot_not_sampled",
                            "dataIndex": "commentplot_not_sampled",
                            "sortable": true,
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "header": "Sampling Quality",
                            "mapping": "sampling_quality",
                            "dataIndex": "sampling_quality",
                            "sortable": true,
                            "name": "sampling_quality"
                        },
                        {
                            "header": "Tax Accuracy Vascular",
                            "mapping": "tax_accuracy_vascular",
                            "dataIndex": "tax_accuracy_vascular",
                            "sortable": true,
                            "name": "tax_accuracy_vascular"
                        },
                        {
                            "header": "Tax Accuracy Bryophytes",
                            "mapping": "tax_accuracy_bryophytes",
                            "dataIndex": "tax_accuracy_bryophytes",
                            "sortable": true,
                            "name": "tax_accuracy_bryophytes"
                        },
                        {
                            "header": "Tax Accuracy Lichens",
                            "mapping": "tax_accuracy_lichens",
                            "dataIndex": "tax_accuracy_lichens",
                            "sortable": true,
                            "name": "tax_accuracy_lichens"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "dataIndex": "authority",
                            "sortable": true,
                            "name": "authority"
                        },
                        {
                            "header": "State",
                            "mapping": "state",
                            "dataIndex": "state",
                            "sortable": true,
                            "name": "state"
                        },
                        {
                            "header": "County",
                            "mapping": "county",
                            "dataIndex": "county",
                            "sortable": true,
                            "name": "county"
                        },
                        {
                            "header": "Quadrangle",
                            "mapping": "quadrangle",
                            "dataIndex": "quadrangle",
                            "sortable": true,
                            "name": "quadrangle"
                        },
                        {
                            "header": "Local Place Name",
                            "mapping": "local_place_name",
                            "dataIndex": "local_place_name",
                            "sortable": true,
                            "name": "local_place_name"
                        },
                        {
                            "header": "Land Owner",
                            "mapping": "landowner",
                            "dataIndex": "landowner",
                            "sortable": true,
                            "name": "landowner"
                        },
                        {
                            "header": "Xaxis Bearing Of Plot",
                            "mapping": "xaxis_bearing_of_plot",
                            "dataIndex": "xaxis_bearing_of_plot",
                            "sortable": true,
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "header": "Enter Gps Location In Plot",
                            "mapping": "enter_gps_location_in_plot",
                            "dataIndex": "enter_gps_location_in_plot",
                            "sortable": true,
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "header": "Latitude",
                            "mapping": "latitude",
                            "dataIndex": "latitude",
                            "sortable": true,
                            "name": "latitude"
                        },
                        {
                            "header": "Longitude",
                            "mapping": "longitude",
                            "dataIndex": "longitude",
                            "sortable": true,
                            "name": "longitude"
                        },
                        {
                            "header": "Total Modules",
                            "mapping": "total_modules",
                            "dataIndex": "total_modules",
                            "sortable": true,
                            "name": "total_modules"
                        },
                        {
                            "header": "Intensive Modules",
                            "mapping": "intensive_modules",
                            "dataIndex": "intensive_modules",
                            "sortable": true,
                            "name": "intensive_modules"
                        },
                        {
                            "header": "Plot Configuration",
                            "mapping": "plot_configuration",
                            "dataIndex": "plot_configuration",
                            "sortable": true,
                            "name": "plot_configuration"
                        },
                        {
                            "header": "Plot Size For Cover Data Area Ha",
                            "mapping": "plot_size_for_cover_data_area_ha",
                            "dataIndex": "plot_size_for_cover_data_area_ha",
                            "sortable": true,
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "header": "Estimate Of Per Open Water Entire Site",
                            "mapping": "estimate_of_per_open_water_entire_site",
                            "dataIndex": "estimate_of_per_open_water_entire_site",
                            "sortable": true,
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "header": "Estimate Of Per Unvegetated Ow Entire Site",
                            "mapping": "estimate_of_perunvegetated_ow_entire_site",
                            "dataIndex": "estimate_of_perunvegetated_ow_entire_site",
                            "sortable": true,
                            "name": "estimate_of_perunvegetated_ow_entire_site"
                        },
                        {
                            "header": "Estimate Per Invasives Entire Site",
                            "mapping": "Estimate_per_invasives_entire_site",
                            "dataIndex": "Estimate_per_invasives_entire_site",
                            "sortable": true,
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "header": "Center Line",
                            "mapping": "centerline",
                            "dataIndex": "centerline",
                            "sortable": true,
                            "name": "centerline"
                        },
                        {
                            "header": "Oneo Plant",
                            "mapping": "oneo_plant",
                            "dataIndex": "oneo_plant",
                            "sortable": true,
                            "name": "oneo_plant"
                        },
                        {
                            "header": "Oneo Text",
                            "mapping": "oneo_text",
                            "dataIndex": "oneo_text",
                            "sortable": true,
                            "name": "oneo_text"
                        },
                        {
                            "header": "Veg Class",
                            "mapping": "vegclass",
                            "dataIndex": "vegclass",
                            "sortable": true,
                            "name": "vegclass"
                        },
                        {
                            "header": "Veg Subclass",
                            "mapping": "vegsubclass",
                            "dataIndex": "vegsubclass",
                            "sortable": true,
                            "name": "vegsubclass"
                        },
                        {
                            "header": "Twoo Plant",
                            "mapping": "twoo_plant",
                            "dataIndex": "twoo_plant",
                            "sortable": true,
                            "name": "twoo_plant"
                        },
                        {
                            "header": "Hgm Class",
                            "mapping": "hgmclass",
                            "dataIndex": "hgmclass",
                            "sortable": true,
                            "name": "hgmclass"
                        },
                        {
                            "header": "Hgm Subclass",
                            "mapping": "hgmsubclass",
                            "dataIndex": "hgmsubclass",
                            "sortable": true,
                            "name": "hgmsubclass"
                        },
                        {
                            "header": "Twoo Hgm",
                            "mapping": "twoo_hgm",
                            "dataIndex": "twoo_hgm",
                            "sortable": true,
                            "name": "twoo_hgm"
                        },
                        {
                            "header": "Hgm Group",
                            "mapping": "hgmgroup",
                            "dataIndex": "hgmgroup",
                            "sortable": true,
                            "name": "hgmgroup"
                        },
                        {
                            "header": "Oneo Class Code Mod Nature Serve",
                            "mapping": "oneo_class_code_mod_natureServe",
                            "dataIndex": "oneo_class_code_mod_natureServe",
                            "sortable": true,
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "header": "Veg Class Wetlands Only",
                            "mapping": "veg_class_wetlands_only",
                            "dataIndex": "veg_class_wetlands_only",
                            "sortable": true,
                            "name": "veg_class_wetlands_only"
                        },
                        {
                            "header": "Landform Type",
                            "mapping": "landform_type",
                            "dataIndex": "landform_type",
                            "sortable": true,
                            "name": "landform_type"
                        },
                        {
                            "header": "Homogeneity",
                            "mapping": "homogeneity",
                            "dataIndex": "homogeneity",
                            "sortable": true,
                            "name": "homogeneity"
                        },
                        {
                            "header": "Stand Size",
                            "mapping": "stand_size",
                            "dataIndex": "stand_size",
                            "sortable": true,
                            "name": "stand_size"
                        },
                        {
                            "header": "Drainage",
                            "mapping": "drainage",
                            "dataIndex": "drainage",
                            "sortable": true,
                            "name": "drainage"
                        },
                        {
                            "header": "Salinity",
                            "mapping": "salinity",
                            "dataIndex": "salinity",
                            "sortable": true,
                            "name": "salinity"
                        },
                        {
                            "header": "Hydrologic Regime",
                            "mapping": "hydrologic_regime",
                            "dataIndex": "hydrologic_regime",
                            "sortable": true,
                            "name": "hydrologic_regime"
                        },
                        {
                            "header": "Oneo Disturbance Type",
                            "mapping": "oneo_disturbance_type",
                            "dataIndex": "oneo_disturbance_type",
                            "sortable": true,
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "header": "Oneo Disturbance Severity",
                            "mapping": "oneo_disturbance_severity",
                            "dataIndex": "oneo_disturbance_severity",
                            "sortable": true,
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "header": "Oneo Disturbance Years Ago",
                            "mapping": "oneo_disturbance_years_ago",
                            "dataIndex": "oneo_disturbance_years_ago",
                            "sortable": true,
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "header": "Oneo Distubance Per Of Plot",
                            "mapping": "oneo_distubance_per_of_plot",
                            "dataIndex": "oneo_distubance_per_of_plot",
                            "sortable": true,
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "header": "Oneo Disturbance Description",
                            "mapping": "oneo_disturbance_description",
                            "dataIndex": "oneo_disturbance_description",
                            "sortable": true,
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "header": "Twoo Disturbance Type",
                            "mapping": "twoo_disturbance_type",
                            "dataIndex": "twoo_disturbance_type",
                            "sortable": true,
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "header": "Twoo Disturbance Severity",
                            "mapping": "twoo_disturbance_severity",
                            "dataIndex": "twoo_disturbance_severity",
                            "sortable": true,
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "header": "Twoo Disturbance Years Ago",
                            "mapping": "twoo_disturbance_years_ago",
                            "dataIndex": "twoo_disturbance_years_ago",
                            "sortable": true,
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "header": "Twoo Distubance Per Of Plot",
                            "mapping": "twoo_distubance_per_of_plot",
                            "dataIndex": "twoo_distubance_per_of_plot",
                            "sortable": true,
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "header": "Twoo Disturbance Description",
                            "mapping": "twoo_disturbance_description",
                            "dataIndex": "twoo_disturbance_description",
                            "sortable": true,
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "header": "Threeo Disturbance Type",
                            "mapping": "threeo_disturbance_type",
                            "dataIndex": "threeo_disturbance_type",
                            "sortable": true,
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "header": "Threeo Disturbance Severity",
                            "mapping": "threeo_disturbance_severity",
                            "dataIndex": "threeo_disturbance_severity",
                            "sortable": true,
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "header": "Threeo Disturbance Years Ago",
                            "mapping": "threeo_disturbance_years_ago",
                            "dataIndex": "threeo_disturbance_years_ago",
                            "sortable": true,
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "header": "Threeo Distubance Per Of Plot",
                            "mapping": "threeo_distubance_per_of_plot",
                            "dataIndex": "threeo_distubance_per_of_plot",
                            "sortable": true,
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "header": "Threeo Disturbance Description",
                            "mapping": "threeo_disturbance_description",
                            "dataIndex": "threeo_disturbance_description",
                            "sortable": true,
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
                            "name": "plot_not_sampled",
                            "type": "string"
                        },
                        {
                            "mapping": "commentPlotNotSampled",
                            "name": "commentplot_not_sampled",
                            "type": "string"
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
                            "name": "local_place_name",
                            "type": "string"
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
                            "name": "enter_gps_location_in_plot",
                            "type": "string"
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
                            "name": "plot_configuration",
                            "type": "string"
                        },
                        {
                            "mapping": "plotSizeForCoverDataAreaHa",
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "mapping": "estimateOfPerOpenWaterEntireSite",
                            "name": "estimate_of_per_open_water_entire_site",
                            "type": "string"
                        },
                        {
                            "mapping": "estimateOfPerunvegetatedOwEntireSite",
                            "name": "estimate_of_perunvegetated_ow_entire_site",
                            "type": "string"
                        },
                        {
                            "mapping": "estimatePerInvasivesEntireSite",
                            "name": "Estimate_per_invasives_entire_site",
                            "type": "string"
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
                            "name": "vegclass",
                            "type": "string"
                        },
                        {
                            "mapping": "vegsubclass",
                            "name": "vegsubclass",
                            "type": "string"
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
                            "name": "hgmgroup",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoClassCodeModNatureServe",
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "mapping": "vegClassWetlandsOnly",
                            "name": "veg_class_wetlands_only",
                            "type": "string"
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
                            "name": "drainage",
                            "type": "string"
                        },
                        {
                            "mapping": "salinity",
                            "name": "salinity",
                            "type": "string"
                        },
                        {
                            "mapping": "hydrologicRegime",
                            "name": "hydrologic_regime"
                        },
                        {
                            "mapping": "oneoDisturbanceType",
                            "name": "oneo_disturbance_type",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoDisturbanceSeverity",
                            "name": "oneo_disturbance_severity",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoDisturbanceYearsAgo",
                            "name": "oneo_disturbance_years_ago",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoDistubancePerOfPlot",
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "oneoDisturbanceDescription",
                            "name": "oneo_disturbance_description",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDisturbanceType",
                            "name": "twoo_disturbance_type",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDisturbanceSeverity",
                            "name": "twoo_disturbance_severity",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDisturbanceYearsAgo",
                            "name": "twoo_disturbance_years_ago",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDistubancePerOfPlot",
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "twooDisturbanceDescription",
                            "name": "twoo_disturbance_description",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDisturbanceType",
                            "name": "threeo_disturbance_type",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDisturbanceSeverity",
                            "name": "threeo_disturbance_severity",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDisturbanceYearsAgo",
                            "name": "threeo_disturbance_years_ago",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDistubancePerOfPlot",
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "threeoDisturbanceDescription",
                            "name": "threeo_disturbance_description",
                            "type": "string"
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
                            "header": "Veg Id",
                            "mapping": "veg_id",
                            "dataIndex": "veg_id",
                            "sortable": true,
                            "name": "veg_id"
                        },
                        {
                            "header": "Scientific Name",
                            "mapping": "scientific_name",
                            "dataIndex": "scientific_name",
                            "sortable": true,
                            "name": "scientific_name"
                        },
                        {
                            "header": "Acronym",
                            "mapping": "acronym",
                            "dataIndex": "acronym",
                            "sortable": true,
                            "name": "acronym"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "dataIndex": "authority",
                            "sortable": true,
                            "name": "authority"
                        },
                        {
                            "header": "Cofc",
                            "mapping": "cofc",
                            "dataIndex": "cofc",
                            "sortable": true,
                            "name": "cofc"
                        },
                        {
                            "header": "Syn",
                            "mapping": "syn",
                            "dataIndex": "syn",
                            "sortable": true,
                            "name": "syn"
                        },
                        {
                            "header": "Common Name",
                            "mapping": "common_name",
                            "dataIndex": "common_name",
                            "sortable": true,
                            "name": "common_name"
                        },
                        {
                            "header": "Family",
                            "mapping": "family",
                            "dataIndex": "family",
                            "sortable": true,
                            "name": "family"
                        },
                        {
                            "header": "Fn",
                            "mapping": "fn",
                            "dataIndex": "fn",
                            "sortable": true,
                            "name": "fn"
                        },
                        {
                            "header": "Wet",
                            "mapping": "wet",
                            "dataIndex": "wet",
                            "sortable": true,
                            "name": "wet"
                        },
                        {
                            "header": "Form",
                            "mapping": "form",
                            "dataIndex": "form",
                            "sortable": true,
                            "name": "form"
                        },
                        {
                            "header": "Habit",
                            "mapping": "habit",
                            "dataIndex": "habit",
                            "sortable": true,
                            "name": "habit"
                        },
                        {
                            "header": "Shade",
                            "mapping": "shade",
                            "dataIndex": "shade",
                            "sortable": true,
                            "name": "shade"
                        },
                        {
                            "header": "Usda Id",
                            "mapping": "usda_id",
                            "dataIndex": "usda_id",
                            "sortable": true,
                            "name": "usda_id"
                        },
                        {
                            "header": "Oh Tore",
                            "mapping": "oh_tore",
                            "dataIndex": "oh_tore",
                            "sortable": true,
                            "name": "oh_tore"
                        },
                        {
                            "header": "Type",
                            "mapping": "type",
                            "dataIndex": "type",
                            "sortable": true,
                            "name": "type"
                        },
                        {
                            "header": "Oh Status",
                            "mapping": "oh_status",
                            "dataIndex": "oh_status",
                            "sortable": true,
                            "name": "oh_status"
                        },
                        {
                            "header": "EMP",
                            "mapping": "emp",
                            "dataIndex": "emp",
                            "sortable": true,
                            "name": "emp"
                        },
                        {
                            "header": "MW",
                            "mapping": "mw",
                            "dataIndex": "mw",
                            "sortable": true,
                            "name": "mw"
                        },
                        {
                            "header": "NCNE",
                            "mapping": "ncne",
                            "dataIndex": "ncne",
                            "sortable": true,
                            "name": "ncne"
                        },
                        {
                            "header": "Notes",
                            "mapping": "notes",
                            "dataIndex": "notes",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Info",
                            "mapping": "info",
                            "dataIndex": "info",
                            "sortable": true,
                            "name": "info"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "dataIndex": "relative_cover",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Sub",
                            "mapping": "sub",
                            "dataIndex": "sub",
                            "sortable": true,
                            "name": "sub"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Dbh Cm",
                            "mapping": "dbh_cm",
                            "dataIndex": "dbh_cm",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "dataIndex": "subcanopy_iv_partial",
                            "sortable": true,
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "dataIndex": "subcanopy_iv_shade",
                            "sortable": true,
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "dataIndex": "canopy_iv",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "dataIndex": "Module Id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "name": "actual_or_derived"
                        },
                        {
                            "header": "Biomass Weight Grams",
                            "mapping": "biomass_weight_grams",
                            "dataIndex": "biomass_weight_grams",
                            "sortable": true,
                            "name": "biomass_weight_grams"
                        },
                        {
                            "header": "Grams Per Square Meter",
                            "mapping": "grams_per_sq_meter",
                            "dataIndex": "grams_per_sq_meter",
                            "sortable": true,
                            "name": "grams_per_sq_meter"
                        }
                    ],
                    "createTitle": "Create a new Biomass",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass",
                    "fields": [
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
                            "mapping": "actualOrDerived",
                            "name": "actual_or_derived"
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "dataIndex": "Module Id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "name": "actual_or_derived"
                        }
                    ],
                    "createTitle": "Create a new Biomass Raw",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Biomass Raw",
                    "fields": [
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
                            "mapping": "actualOrDerived",
                            "name": "actual_or_derived",
                            "type": "string"
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
                            "dataIndex": "code",
                            "sortable": true,
                            "name": "code"
                        },
                        {
                            "header": "Community Class",
                            "mapping": "community_class",
                            "dataIndex": "community_class",
                            "sortable": true,
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
                            "dataIndex": "cover_code",
                            "sortable": true,
                            "name": "cover_code"
                        },
                        {
                            "header": "Midpoint",
                            "mapping": "midpoint",
                            "dataIndex": "midpoint",
                            "sortable": true,
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
                            "dataIndex" : "plot_no",
                            "sortable": true,
                            "name" : "plot_no"
                        }, {
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "dataIndex" : "vibi_type",
                            "sortable": true,
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "dataIndex" : "score",
                            "sortable": true,
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "dataIndex" : "carex_metric_value",
                            "sortable": true,
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "dataIndex" : "cyperaceae_metric_value",
                            "sortable": true,
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "dataIndex" : "dicot_metric_value",
                            "sortable": true,
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "dataIndex" : "shade_metric_value",
                            "sortable": true,
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "dataIndex" : "shrub_metric_value",
                            "sortable": true,
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "dataIndex" : "hydrophyte_metric_value",
                            "sortable": true,
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "dataIndex" : "svp_metric_value",
                            "sortable": true,
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "dataIndex" : "ap_ratio_metric_value",
                            "sortable": true,
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "dataIndex" : "fqai_metric_value",
                            "sortable": true,
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "dataIndex" : "bryophyte_metric_value",
                            "sortable": true,
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "dataIndex" : "per_hydrophyte_metric_value",
                            "sortable": true,
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "dataIndex" : "sensitive_metric_value",
                            "sortable": true,
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "dataIndex" : "tolerant_metric_value",
                            "sortable": true,
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "dataIndex" : "invasive_graminoids_metric_value",
                            "sortable": true,
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
                            "mapping" : "small_tree_metric_value",
                            "dataIndex" : "small_tree_metric_value",
                            "sortable": true,
                            "name" : "small_tree_metric_value"
                        }, {
                            "header" : "Subcanopy IV",
                            "mapping" : "subcanopy_iv",
                            "dataIndex" : "subcanopy_iv",
                            "sortable": true,
                            "name" : "subcanopy_iv"
                        }, {
                            "header" : "Canopy IV",
                            "mapping" : "canopy_iv",
                            "dataIndex" : "canopy_iv",
                            "sortable": true,
                            "name" : "canopy_iv"
                        }, {
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "dataIndex" : "biomass_metric_value",
                            "sortable": true,
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Stems HA Wetland Trees",
                            "mapping" : "stems_ha_wetland_trees",
                            "dataIndex" : "stems_ha_wetland_trees",
                            "sortable": true,
                            "name" : "stems_ha_wetland_trees"
                        }, {
                            "header" : "Stems HA Wetland Shrubs",
                            "mapping" : "stems_ha_wetland_shrubs",
                            "dataIndex" : "stems_ha_wetland_shrubs",
                            "sortable": true,
                            "name" : "stems_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "dataIndex": "per_unvegetated",
                            "sortable": true,
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "dataIndex": "per_button_bush",
                            "sortable": true,
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "dataIndex": "per_perennial_native_hydrophytes",
                            "sortable": true,
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "dataIndex": "per_adventives",
                            "sortable": true,
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "dataIndex": "per_open_water",
                            "sortable": true,
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "dataIndex": "per_unvegetated_open_water",
                            "sortable": true,
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "dataIndex": "per_bare_ground",
                            "sortable": true,
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
                            "name" : "score",
                            "type": "string"
                        },
                        {
                            "mapping" : "carexMetricValue",
                            "name" : "carex_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "cyperaceaeMetricValue",
                            "name" : "cyperaceae_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "dicotMetricValue",
                            "name" : "dicot_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "shadeMetricValue",
                            "name" : "shade_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "shrubMetricValue",
                            "name" : "shrub_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "hydrophyteMetricValue",
                            "name" : "hydrophyte_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "svpMetricValue",
                            "name" : "svp_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "apRatioMetricValue",
                            "name" : "ap_ratio_metric_value",
                            "type": "string"
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
                            "name" : "invasive_graminoids_metric_value",
                            "type": "string"
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
            "geoStoreRestURL":"http://vibi.geo-solutions.it/geostore/rest/",
            "canArchive": true,
            "uploadUrl":"http://vibi.geo-solutions.it/opensdi2-manager/mvc/vibi/upload/",
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
                            "dataIndex": "plot_no",
                            "name": "plot_no",
                            "sortable": true
                        },
                        {
                            "header": "Project Name",
                            "mapping": "project_name",
                            "dataIndex": "project_name",
                            "sortable": true,
                            "name": "project_name"
                        },
                        {
                            "header": "Plot Name",
                            "mapping": "plot_name",
                            "dataIndex": "plot_name",
                            "sortable": true,
                            "name": "plot_name"
                        },
                        {
                            "header": "Plot Label",
                            "mapping": "plot_label",
                            "dataIndex": "plot_label",
                            "sortable": true,
                            "name": "plot_label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "dataIndex": "monitoring_event",
                            "sortable": true,
                            "name": "monitoring_event"
                        },
                        {
                            "header": "Date Timer",
                            "mapping": "timestamptz",
                            "dataIndex": "timestamptz",
                            "sortable": true,
                            "name": "timestamptz"
                        },
                        {
                            "header": "Party",
                            "mapping": "party",
                            "dataIndex": "party",
                            "sortable": true,
                            "name": "party"
                        },
                        {
                            "header": "Plot Not Sampled",
                            "mapping": "plot_not_sampled",
                            "dataIndex": "plot_not_sampled",
                            "sortable": true,
                            "name": "plot_not_sampled"
                        },
                        {
                            "header": "Comment Plot Not Sampled",
                            "mapping": "commentplot_not_sampled",
                            "dataIndex": "commentplot_not_sampled",
                            "sortable": true,
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "header": "Sampling Quality",
                            "mapping": "sampling_quality",
                            "dataIndex": "sampling_quality",
                            "sortable": true,
                            "name": "sampling_quality"
                        },
                        {
                            "header": "Tax Accuracy Vascular",
                            "mapping": "tax_accuracy_vascular",
                            "dataIndex": "tax_accuracy_vascular",
                            "sortable": true,
                            "name": "tax_accuracy_vascular"
                        },
                        {
                            "header": "Tax Accuracy Bryophytes",
                            "mapping": "tax_accuracy_bryophytes",
                            "dataIndex": "tax_accuracy_bryophytes",
                            "sortable": true,
                            "name": "tax_accuracy_bryophytes"
                        },
                        {
                            "header": "Tax Accuracy Lichens",
                            "mapping": "tax_accuracy_lichens",
                            "dataIndex": "tax_accuracy_lichens",
                            "sortable": true,
                            "name": "tax_accuracy_lichens"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "dataIndex": "authority",
                            "sortable": true,
                            "name": "authority"
                        },
                        {
                            "header": "State",
                            "mapping": "state",
                            "dataIndex": "state",
                            "sortable": true,
                            "name": "state"
                        },
                        {
                            "header": "County",
                            "mapping": "county",
                            "dataIndex": "county",
                            "sortable": true,
                            "name": "county"
                        },
                        {
                            "header": "Quadrangle",
                            "mapping": "quadrangle",
                            "dataIndex": "quadrangle",
                            "sortable": true,
                            "name": "quadrangle"
                        },
                        {
                            "header": "Local Place Name",
                            "mapping": "local_place_name",
                            "dataIndex": "local_place_name",
                            "sortable": true,
                            "name": "local_place_name"
                        },
                        {
                            "header": "Land Owner",
                            "mapping": "landowner",
                            "dataIndex": "landowner",
                            "sortable": true,
                            "name": "landowner"
                        },
                        {
                            "header": "Xaxis Bearing Of Plot",
                            "mapping": "xaxis_bearing_of_plot",
                            "dataIndex": "xaxis_bearing_of_plot",
                            "sortable": true,
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "header": "Enter Gps Location In Plot",
                            "mapping": "enter_gps_location_in_plot",
                            "dataIndex": "enter_gps_location_in_plot",
                            "sortable": true,
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "header": "Latitude",
                            "mapping": "latitude",
                            "dataIndex": "latitude",
                            "sortable": true,
                            "name": "latitude"
                        },
                        {
                            "header": "Longitude",
                            "mapping": "longitude",
                            "dataIndex": "longitude",
                            "sortable": true,
                            "name": "longitude"
                        },
                        {
                            "header": "Total Modules",
                            "mapping": "total_modules",
                            "dataIndex": "total_modules",
                            "sortable": true,
                            "name": "total_modules"
                        },
                        {
                            "header": "Intensive Modules",
                            "mapping": "intensive_modules",
                            "dataIndex": "intensive_modules",
                            "sortable": true,
                            "name": "intensive_modules"
                        },
                        {
                            "header": "Plot Configuration",
                            "mapping": "plot_configuration",
                            "dataIndex": "plot_configuration",
                            "sortable": true,
                            "name": "plot_configuration"
                        },
                        {
                            "header": "Plot Size For Cover Data Area Ha",
                            "mapping": "plot_size_for_cover_data_area_ha",
                            "dataIndex": "plot_size_for_cover_data_area_ha",
                            "sortable": true,
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "header": "Estimate Of Per Open Water Entire Site",
                            "mapping": "estimate_of_per_open_water_entire_site",
                            "dataIndex": "estimate_of_per_open_water_entire_site",
                            "sortable": true,
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "header": "Estimate Of Per Unvegetated Ow Entire Site",
                            "mapping": "estimate_of_perunvegetated_ow_entire_site",
                            "dataIndex": "estimate_of_perunvegetated_ow_entire_site",
                            "sortable": true,
                            "name": "estimate_of_perunvegetated_ow_entire_site"
                        },
                        {
                            "header": "Estimate Per Invasives Entire Site",
                            "mapping": "Estimate_per_invasives_entire_site",
                            "dataIndex": "Estimate_per_invasives_entire_site",
                            "sortable": true,
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "header": "Center Line",
                            "mapping": "centerline",
                            "dataIndex": "centerline",
                            "sortable": true,
                            "name": "centerline"
                        },
                        {
                            "header": "Oneo Plant",
                            "mapping": "oneo_plant",
                            "dataIndex": "oneo_plant",
                            "sortable": true,
                            "name": "oneo_plant"
                        },
                        {
                            "header": "Oneo Text",
                            "mapping": "oneo_text",
                            "dataIndex": "oneo_text",
                            "sortable": true,
                            "name": "oneo_text"
                        },
                        {
                            "header": "Veg Class",
                            "mapping": "vegclass",
                            "dataIndex": "vegclass",
                            "sortable": true,
                            "name": "vegclass"
                        },
                        {
                            "header": "Veg Subclass",
                            "mapping": "vegsubclass",
                            "dataIndex": "vegsubclass",
                            "sortable": true,
                            "name": "vegsubclass"
                        },
                        {
                            "header": "Twoo Plant",
                            "mapping": "twoo_plant",
                            "dataIndex": "twoo_plant",
                            "sortable": true,
                            "name": "twoo_plant"
                        },
                        {
                            "header": "Hgm Class",
                            "mapping": "hgmclass",
                            "dataIndex": "hgmclass",
                            "sortable": true,
                            "name": "hgmclass"
                        },
                        {
                            "header": "Hgm Subclass",
                            "mapping": "hgmsubclass",
                            "dataIndex": "hgmsubclass",
                            "sortable": true,
                            "name": "hgmsubclass"
                        },
                        {
                            "header": "Twoo Hgm",
                            "mapping": "twoo_hgm",
                            "dataIndex": "twoo_hgm",
                            "sortable": true,
                            "name": "twoo_hgm"
                        },
                        {
                            "header": "Hgm Group",
                            "mapping": "hgmgroup",
                            "dataIndex": "hgmgroup",
                            "sortable": true,
                            "name": "hgmgroup"
                        },
                        {
                            "header": "Oneo Class Code Mod Nature Serve",
                            "mapping": "oneo_class_code_mod_natureServe",
                            "dataIndex": "oneo_class_code_mod_natureServe",
                            "sortable": true,
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "header": "Veg Class Wetlands Only",
                            "mapping": "veg_class_wetlands_only",
                            "dataIndex": "veg_class_wetlands_only",
                            "sortable": true,
                            "name": "veg_class_wetlands_only"
                        },
                        {
                            "header": "Landform Type",
                            "mapping": "landform_type",
                            "dataIndex": "landform_type",
                            "sortable": true,
                            "name": "landform_type"
                        },
                        {
                            "header": "Homogeneity",
                            "mapping": "homogeneity",
                            "dataIndex": "homogeneity",
                            "sortable": true,
                            "name": "homogeneity"
                        },
                        {
                            "header": "Stand Size",
                            "mapping": "stand_size",
                            "dataIndex": "stand_size",
                            "sortable": true,
                            "name": "stand_size"
                        },
                        {
                            "header": "Drainage",
                            "mapping": "drainage",
                            "dataIndex": "drainage",
                            "sortable": true,
                            "name": "drainage"
                        },
                        {
                            "header": "Salinity",
                            "mapping": "salinity",
                            "dataIndex": "salinity",
                            "sortable": true,
                            "name": "salinity"
                        },
                        {
                            "header": "Hydrologic Regime",
                            "mapping": "hydrologic_regime",
                            "dataIndex": "hydrologic_regime",
                            "sortable": true,
                            "name": "hydrologic_regime"
                        },
                        {
                            "header": "Oneo Disturbance Type",
                            "mapping": "oneo_disturbance_type",
                            "dataIndex": "oneo_disturbance_type",
                            "sortable": true,
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "header": "Oneo Disturbance Severity",
                            "mapping": "oneo_disturbance_severity",
                            "dataIndex": "oneo_disturbance_severity",
                            "sortable": true,
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "header": "Oneo Disturbance Years Ago",
                            "mapping": "oneo_disturbance_years_ago",
                            "dataIndex": "oneo_disturbance_years_ago",
                            "sortable": true,
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "header": "Oneo Distubance Per Of Plot",
                            "mapping": "oneo_distubance_per_of_plot",
                            "dataIndex": "oneo_distubance_per_of_plot",
                            "sortable": true,
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "header": "Oneo Disturbance Description",
                            "mapping": "oneo_disturbance_description",
                            "dataIndex": "oneo_disturbance_description",
                            "sortable": true,
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "header": "Twoo Disturbance Type",
                            "mapping": "twoo_disturbance_type",
                            "dataIndex": "twoo_disturbance_type",
                            "sortable": true,
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "header": "Twoo Disturbance Severity",
                            "mapping": "twoo_disturbance_severity",
                            "dataIndex": "twoo_disturbance_severity",
                            "sortable": true,
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "header": "Twoo Disturbance Years Ago",
                            "mapping": "twoo_disturbance_years_ago",
                            "dataIndex": "twoo_disturbance_years_ago",
                            "sortable": true,
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "header": "Twoo Distubance Per Of Plot",
                            "mapping": "twoo_distubance_per_of_plot",
                            "dataIndex": "twoo_distubance_per_of_plot",
                            "sortable": true,
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "header": "Twoo Disturbance Description",
                            "mapping": "twoo_disturbance_description",
                            "dataIndex": "twoo_disturbance_description",
                            "sortable": true,
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "header": "Threeo Disturbance Type",
                            "mapping": "threeo_disturbance_type",
                            "dataIndex": "threeo_disturbance_type",
                            "sortable": true,
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "header": "Threeo Disturbance Severity",
                            "mapping": "threeo_disturbance_severity",
                            "dataIndex": "threeo_disturbance_severity",
                            "sortable": true,
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "header": "Threeo Disturbance Years Ago",
                            "mapping": "threeo_disturbance_years_ago",
                            "dataIndex": "threeo_disturbance_years_ago",
                            "sortable": true,
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "header": "Threeo Distubance Per Of Plot",
                            "mapping": "threeo_distubance_per_of_plot",
                            "dataIndex": "threeo_distubance_per_of_plot",
                            "sortable": true,
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "header": "Threeo Disturbance Description",
                            "mapping": "threeo_disturbance_description",
                            "dataIndex": "threeo_disturbance_description",
                            "sortable": true,
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
                            "name": "plot_not_sampled",
                            "type": "string"
                        },
                        {
                            "mapping": "commentPlotNotSampled",
                            "name": "commentplot_not_sampled",
                            "type": "string"
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
                            "name": "local_place_name",
                            "type": "string"
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
                            "name": "enter_gps_location_in_plot",
                            "type": "string"
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
                            "name": "plot_configuration",
                            "type": "string"
                        },
                        {
                            "mapping": "plotSizeForCoverDataAreaHa",
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "mapping": "estimateOfPerOpenWaterEntireSite",
                            "name": "estimate_of_per_open_water_entire_site",
                            "type": "string"
                        },
                        {
                            "mapping": "estimateOfPerunvegetatedOwEntireSite",
                            "name": "estimate_of_perunvegetated_ow_entire_site",
                            "type": "string"
                        },
                        {
                            "mapping": "estimatePerInvasivesEntireSite",
                            "name": "Estimate_per_invasives_entire_site",
                            "type": "string"
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
                            "name": "vegclass",
                            "type": "string"
                        },
                        {
                            "mapping": "vegsubclass",
                            "name": "vegsubclass",
                            "type": "string"
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
                            "name": "hgmgroup",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoClassCodeModNatureServe",
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "mapping": "vegClassWetlandsOnly",
                            "name": "veg_class_wetlands_only",
                            "type": "string"
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
                            "name": "drainage",
                            "type": "string"
                        },
                        {
                            "mapping": "salinity",
                            "name": "salinity",
                            "type": "string"
                        },
                        {
                            "mapping": "hydrologicRegime",
                            "name": "hydrologic_regime"
                        },
                        {
                            "mapping": "oneoDisturbanceType",
                            "name": "oneo_disturbance_type",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoDisturbanceSeverity",
                            "name": "oneo_disturbance_severity",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoDisturbanceYearsAgo",
                            "name": "oneo_disturbance_years_ago",
                            "type": "string"
                        },
                        {
                            "mapping": "oneoDistubancePerOfPlot",
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "oneoDisturbanceDescription",
                            "name": "oneo_disturbance_description",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDisturbanceType",
                            "name": "twoo_disturbance_type",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDisturbanceSeverity",
                            "name": "twoo_disturbance_severity",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDisturbanceYearsAgo",
                            "name": "twoo_disturbance_years_ago",
                            "type": "string"
                        },
                        {
                            "mapping": "twooDistubancePerOfPlot",
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "twooDisturbanceDescription",
                            "name": "twoo_disturbance_description",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDisturbanceType",
                            "name": "threeo_disturbance_type",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDisturbanceSeverity",
                            "name": "threeo_disturbance_severity",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDisturbanceYearsAgo",
                            "name": "threeo_disturbance_years_ago",
                            "type": "string"
                        },
                        {
                            "mapping": "threeoDistubancePerOfPlot",
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "mapping": "threeoDisturbanceDescription",
                            "name": "threeo_disturbance_description",
                            "type": "string"
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
                            "header": "Veg Id",
                            "mapping": "veg_id",
                            "dataIndex": "veg_id",
                            "sortable": true,
                            "name": "veg_id"
                        },
                        {
                            "header": "Scientific Name",
                            "mapping": "scientific_name",
                            "dataIndex": "scientific_name",
                            "sortable": true,
                            "name": "scientific_name"
                        },
                        {
                            "header": "Acronym",
                            "mapping": "acronym",
                            "dataIndex": "acronym",
                            "sortable": true,
                            "name": "acronym"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "dataIndex": "authority",
                            "sortable": true,
                            "name": "authority"
                        },
                        {
                            "header": "Cofc",
                            "mapping": "cofc",
                            "dataIndex": "cofc",
                            "sortable": true,
                            "name": "cofc"
                        },
                        {
                            "header": "Syn",
                            "mapping": "syn",
                            "dataIndex": "syn",
                            "sortable": true,
                            "name": "syn"
                        },
                        {
                            "header": "Common Name",
                            "mapping": "common_name",
                            "dataIndex": "common_name",
                            "sortable": true,
                            "name": "common_name"
                        },
                        {
                            "header": "Family",
                            "mapping": "family",
                            "dataIndex": "family",
                            "sortable": true,
                            "name": "family"
                        },
                        {
                            "header": "Fn",
                            "mapping": "fn",
                            "dataIndex": "fn",
                            "sortable": true,
                            "name": "fn"
                        },
                        {
                            "header": "Wet",
                            "mapping": "wet",
                            "dataIndex": "wet",
                            "sortable": true,
                            "name": "wet"
                        },
                        {
                            "header": "Form",
                            "mapping": "form",
                            "dataIndex": "form",
                            "sortable": true,
                            "name": "form"
                        },
                        {
                            "header": "Habit",
                            "mapping": "habit",
                            "dataIndex": "habit",
                            "sortable": true,
                            "name": "habit"
                        },
                        {
                            "header": "Shade",
                            "mapping": "shade",
                            "dataIndex": "shade",
                            "sortable": true,
                            "name": "shade"
                        },
                        {
                            "header": "Usda Id",
                            "mapping": "usda_id",
                            "dataIndex": "usda_id",
                            "sortable": true,
                            "name": "usda_id"
                        },
                        {
                            "header": "Oh Tore",
                            "mapping": "oh_tore",
                            "dataIndex": "oh_tore",
                            "sortable": true,
                            "name": "oh_tore"
                        },
                        {
                            "header": "Type",
                            "mapping": "type",
                            "dataIndex": "type",
                            "sortable": true,
                            "name": "type"
                        },
                        {
                            "header": "Oh Status",
                            "mapping": "oh_status",
                            "dataIndex": "oh_status",
                            "sortable": true,
                            "name": "oh_status"
                        },
                        {
                            "header": "EMP",
                            "mapping": "emp",
                            "dataIndex": "emp",
                            "sortable": true,
                            "name": "emp"
                        },
                        {
                            "header": "MW",
                            "mapping": "mw",
                            "dataIndex": "mw",
                            "sortable": true,
                            "name": "mw"
                        },
                        {
                            "header": "NCNE",
                            "mapping": "ncne",
                            "dataIndex": "ncne",
                            "sortable": true,
                            "name": "ncne"
                        },
                        {
                            "header": "Notes",
                            "mapping": "notes",
                            "dataIndex": "notes",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Info",
                            "mapping": "info",
                            "dataIndex": "info",
                            "sortable": true,
                            "name": "info"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "dataIndex": "relative_cover",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Sub",
                            "mapping": "sub",
                            "dataIndex": "sub",
                            "sortable": true,
                            "name": "sub"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Dbh Cm",
                            "mapping": "dbh_cm",
                            "dataIndex": "dbh_cm",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "dataIndex": "subcanopy_iv_partial",
                            "sortable": true,
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "dataIndex": "subcanopy_iv_shade",
                            "sortable": true,
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "dataIndex": "canopy_iv",
                            "sortable": true,
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "dataIndex": "Module Id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "name": "actual_or_derived"
                        },
                        {
                            "header": "Biomass Weight Grams",
                            "mapping": "biomass_weight_grams",
                            "dataIndex": "biomass_weight_grams",
                            "sortable": true,
                            "name": "biomass_weight_grams"
                        },
                        {
                            "header": "Grams Per Square Meter",
                            "mapping": "grams_per_sq_meter",
                            "dataIndex": "grams_per_sq_meter",
                            "sortable": true,
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
                            "mapping": "actualOrDerived",
                            "name": "actual_or_derived"
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
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "Module Id",
                            "dataIndex": "Module Id",
                            "sortable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "name": "actual_or_derived"
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
                        },
                        {
                            "mapping": "actualOrDerived",
                            "name": "actual_or_derived",
                            "type": "string"
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
                            "dataIndex": "code",
                            "sortable": true,
                            "name": "code"
                        },
                        {
                            "header": "Community Class",
                            "mapping": "community_class",
                            "dataIndex": "community_class",
                            "sortable": true,
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
                            "dataIndex": "cover_code",
                            "sortable": true,
                            "name": "cover_code"
                        },
                        {
                            "header": "Midpoint",
                            "mapping": "midpoint",
                            "dataIndex": "midpoint",
                            "sortable": true,
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
                            "dataIndex" : "plot_no",
                            "sortable": true,
                            "name" : "plot_no"
                        }, {
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "dataIndex" : "vibi_type",
                            "sortable": true,
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "dataIndex" : "score",
                            "sortable": true,
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "dataIndex" : "carex_metric_value",
                            "sortable": true,
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "dataIndex" : "cyperaceae_metric_value",
                            "sortable": true,
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "dataIndex" : "dicot_metric_value",
                            "sortable": true,
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "dataIndex" : "shade_metric_value",
                            "sortable": true,
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "dataIndex" : "shrub_metric_value",
                            "sortable": true,
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "dataIndex" : "hydrophyte_metric_value",
                            "sortable": true,
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "dataIndex" : "svp_metric_value",
                            "sortable": true,
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "dataIndex" : "ap_ratio_metric_value",
                            "sortable": true,
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "dataIndex" : "fqai_metric_value",
                            "sortable": true,
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "dataIndex" : "bryophyte_metric_value",
                            "sortable": true,
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "dataIndex" : "per_hydrophyte_metric_value",
                            "sortable": true,
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "dataIndex" : "sensitive_metric_value",
                            "sortable": true,
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "dataIndex" : "tolerant_metric_value",
                            "sortable": true,
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "dataIndex" : "invasive_graminoids_metric_value",
                            "sortable": true,
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
                            "mapping" : "small_tree_metric_value",
                            "dataIndex" : "small_tree_metric_value",
                            "sortable": true,
                            "name" : "small_tree_metric_value"
                        }, {
                            "header" : "Subcanopy IV",
                            "mapping" : "subcanopy_iv",
                            "dataIndex" : "subcanopy_iv",
                            "sortable": true,
                            "name" : "subcanopy_iv"
                        }, {
                            "header" : "Canopy IV",
                            "mapping" : "canopy_iv",
                            "dataIndex" : "canopy_iv",
                            "sortable": true,
                            "name" : "canopy_iv"
                        }, {
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "dataIndex" : "biomass_metric_value",
                            "sortable": true,
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Stems HA Wetland Trees",
                            "mapping" : "stems_ha_wetland_trees",
                            "dataIndex" : "stems_ha_wetland_trees",
                            "sortable": true,
                            "name" : "stems_ha_wetland_trees"
                        }, {
                            "header" : "Stems HA Wetland Shrubs",
                            "mapping" : "stems_ha_wetland_shrubs",
                            "dataIndex" : "stems_ha_wetland_shrubs",
                            "sortable": true,
                            "name" : "stems_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "dataIndex": "per_unvegetated",
                            "sortable": true,
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "dataIndex": "per_button_bush",
                            "sortable": true,
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "dataIndex": "per_perennial_native_hydrophytes",
                            "sortable": true,
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "dataIndex": "per_adventives",
                            "sortable": true,
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "dataIndex": "per_open_water",
                            "sortable": true,
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "dataIndex": "per_unvegetated_open_water",
                            "sortable": true,
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "dataIndex": "per_bare_ground",
                            "sortable": true,
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
                            "name" : "score",
                            "type": "string"
                        },
                        {
                            "mapping" : "carexMetricValue",
                            "name" : "carex_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "cyperaceaeMetricValue",
                            "name" : "cyperaceae_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "dicotMetricValue",
                            "name" : "dicot_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "shadeMetricValue",
                            "name" : "shade_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "shrubMetricValue",
                            "name" : "shrub_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "hydrophyteMetricValue",
                            "name" : "hydrophyte_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "svpMetricValue",
                            "name" : "svp_metric_value",
                            "type": "string"
                        },
                        {
                            "mapping" : "apRatioMetricValue",
                            "name" : "ap_ratio_metric_value",
                            "type": "string"
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
                            "name" : "invasive_graminoids_metric_value",
                            "type": "string"
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
            "geoStoreRestURL":"http://vibi.geo-solutions.it/geostore/rest/",
            "uploadUrl":"http://vibi.geo-solutions.it/opensdi2-manager/mvc/vibi/upload/",
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

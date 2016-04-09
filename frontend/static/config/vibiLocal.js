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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Project Name",
                            "mapping": "project_name",
                            "dataIndex": "project_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "project_name"
                        },
                        {
                            "header": "Plot Name",
                            "mapping": "plot_name",
                            "dataIndex": "plot_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_name"
                        },
                        {
                            "header": "Project Label",
                            "mapping": "Project_Label",
                            "dataIndex": "Project_Label",
                            "sortable": true,
                            "filterable": true,
                            "name": "Project_Label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "dataIndex": "monitoring_event",
                            "sortable": true,
                            "filterable": true,
                            "name": "monitoring_event"
                        },
                        {
                            "header": "Date Timer",
                            "mapping": "timestamptz",
                            "dataIndex": "timestamptz",
                            "sortable": true,
                            "filterable": true,
                            "name": "timestamptz"
                        },
                        {
                            "header": "Party",
                            "mapping": "party",
                            "dataIndex": "party",
                            "sortable": true,
                            "filterable": true,
                            "name": "party"
                        },
                        {
                            "header": "Plot Not Sampled",
                            "mapping": "plot_not_sampled",
                            "dataIndex": "plot_not_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_not_sampled"
                        },
                        {
                            "header": "Comment Plot Not Sampled",
                            "mapping": "commentplot_not_sampled",
                            "dataIndex": "commentplot_not_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "header": "Sampling Quality",
                            "mapping": "sampling_quality",
                            "dataIndex": "sampling_quality",
                            "sortable": true,
                            "filterable": true,
                            "name": "sampling_quality"
                        },
                        {
                            "header": "State",
                            "mapping": "state",
                            "dataIndex": "state",
                            "sortable": true,
                            "filterable": true,
                            "name": "state"
                        },
                        {
                            "header": "County",
                            "mapping": "county",
                            "dataIndex": "county",
                            "sortable": true,
                            "filterable": true,
                            "name": "county"
                        },
                        {
                            "header": "Quadrangle",
                            "mapping": "quadrangle",
                            "dataIndex": "quadrangle",
                            "sortable": true,
                            "filterable": true,
                            "name": "quadrangle"
                        },
                        {
                            "header": "Local Place Name",
                            "mapping": "local_place_name",
                            "dataIndex": "local_place_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "local_place_name"
                        },
                        {
                            "header": "Land Owner",
                            "mapping": "landowner",
                            "dataIndex": "landowner",
                            "sortable": true,
                            "filterable": true,
                            "name": "landowner"
                        },
                        {
                            "header": "Xaxis Bearing Of Plot",
                            "mapping": "xaxis_bearing_of_plot",
                            "dataIndex": "xaxis_bearing_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "header": "Enter Gps Location In Plot",
                            "mapping": "enter_gps_location_in_plot",
                            "dataIndex": "enter_gps_location_in_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "header": "Latitude",
                            "mapping": "latitude",
                            "dataIndex": "latitude",
                            "sortable": true,
                            "filterable": true,
                            "name": "latitude"
                        },
                        {
                            "header": "Longitude",
                            "mapping": "longitude",
                            "dataIndex": "longitude",
                            "sortable": true,
                            "filterable": true,
                            "name": "longitude"
                        },
                        {
                            "header": "Plot Placement",
                            "mapping": "plot_placement",
                            "dataIndex": "plot_placement",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_placement"
                        },
                        {
                            "header": "Total Modules",
                            "mapping": "total_modules",
                            "dataIndex": "total_modules",
                            "sortable": true,
                            "filterable": true,
                            "name": "total_modules"
                        },
                        {
                            "header": "Intensive Modules",
                            "mapping": "intensive_modules",
                            "dataIndex": "intensive_modules",
                            "sortable": true,
                            "filterable": true,
                            "name": "intensive_modules"
                        },
                        {
                            "header": "Plot Configuration",
                            "mapping": "plot_configuration",
                            "dataIndex": "plot_configuration",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_configuration"
                        },
                        {
                            "header": "Plot Configuration Other",
                            "mapping": "plot_configuration_other",
                            "dataIndex": "plot_configuration_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_configuration_other"
                        },
                        {
                            "header": "Plot Size For Cover Data Area Ha",
                            "mapping": "plot_size_for_cover_data_area_ha",
                            "dataIndex": "plot_size_for_cover_data_area_ha",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "header": "Estimate Of Per Open Water Entire Site",
                            "mapping": "estimate_of_per_open_water_entire_site",
                            "dataIndex": "estimate_of_per_open_water_entire_site",
                            "sortable": true,
                            "filterable": true,
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "header": "Estimate Of Per Unvegetated OW Entire Site",
                            "mapping": "Estimate_of_per_unvegetated_ow_entire_site",
                            "dataIndex": "Estimate_of_per_unvegetated_ow_entire_site",
                            "sortable": true,
                            "filterable": true,
                            "name": "Estimate_of_per_unvegetated_ow_entire_site"
                        },
                        {
                            "header": "Estimate Per Invasives Entire Site",
                            "mapping": "Estimate_per_invasives_entire_site",
                            "dataIndex": "Estimate_per_invasives_entire_site",
                            "sortable": true,
                            "filterable": true,
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "header": "Center Line",
                            "mapping": "centerline",
                            "dataIndex": "centerline",
                            "sortable": true,
                            "filterable": true,
                            "name": "centerline"
                        },
                        {
                            "header": "Veg Class",
                            "mapping": "vegclass",
                            "dataIndex": "vegclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "vegclass"
                        },
                        {
                            "header": "Veg Subclass",
                            "mapping": "vegsubclass",
                            "dataIndex": "vegsubclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "vegsubclass"
                        },
                        {
                            "header": "Hgm Class",
                            "mapping": "hgmclass",
                            "dataIndex": "hgmclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "hgmclass"
                        },
                        {
                            "header": "Hgm Subclass",
                            "mapping": "hgmsubclass",
                            "dataIndex": "hgmsubclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "hgmsubclass"
                        },
                        {
                            "header": "Twoo Hgm",
                            "mapping": "twoo_hgm",
                            "dataIndex": "twoo_hgm",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_hgm"
                        },
                        {
                            "header": "Hgm Group",
                            "mapping": "hgmgroup",
                            "dataIndex": "hgmgroup",
                            "sortable": true,
                            "filterable": true,
                            "name": "hgmgroup"
                        },
                        {
                            "header": "Oneo Class Code Mod Nature Serve",
                            "mapping": "oneo_class_code_mod_natureServe",
                            "dataIndex": "oneo_class_code_mod_natureServe",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "header": "Leap Land Cover Classification",
                            "mapping": "leap_landcover_classification",
                            "dataIndex": "leap_landcover_classification",
                            "sortable": true,
                            "filterable": true,
                            "name": "leap_landcover_classification"
                        },
                        {
                            "header": "Cowardin Classification",
                            "mapping": "cowardin_classification",
                            "dataIndex": "cowardin_classification",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_classification"
                        },
                        {
                            "header": "Cowardin Water Regime",
                            "mapping": "cowardin_water_regime",
                            "dataIndex": "cowardin_water_regime",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_water_regime"
                        },
                        {
                            "header": "Cowardin Special Modifier",
                            "mapping": "cowardin_special_modifier",
                            "dataIndex": "cowardin_special_modifier",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_special_modifier"
                        },
                        {
                            "header": "cowardin Special Modifier Other",
                            "mapping": "cowardin_special_modifier_other",
                            "dataIndex": "cowardin_special_modifier_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_special_modifier_other"
                        },
                        {
                            "header": "Landscape Position",
                            "mapping": "landscape_position",
                            "dataIndex": "landscape_position",
                            "sortable": true,
                            "filterable": true,
                            "name": "landscape_position"
                        },
                        {
                            "header": "Inland Landform",
                            "mapping": "inland_landform",
                            "dataIndex": "inland_landform",
                            "sortable": true,
                            "filterable": true,
                            "name": "inland_landform"
                        },
                        {
                            "header": "Water Flow Path",
                            "mapping": "water_flow_path",
                            "dataIndex": "water_flow_path",
                            "sortable": true,
                            "filterable": true,
                            "name": "water_flow_path"
                        },
                        {
                            "header": "Llww Modifiers",
                            "mapping": "llww_modifiers",
                            "dataIndex": "llww_modifiers",
                            "sortable": true,
                            "filterable": true,
                            "name": "llww_modifiers"
                        },
                        {
                            "header": "Llww Modifiers Other",
                            "mapping": "llww_modifiers_other",
                            "dataIndex": "llww_modifiers_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "llww_modifiers_other"
                        },
                        {
                            "header": "Landform Type",
                            "mapping": "landform_type",
                            "dataIndex": "landform_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "landform_type"
                        },
                        {
                            "header": "Landform Type Other",
                            "mapping": "landform_type_other",
                            "dataIndex": "landform_type_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "landform_type_other"
                        },
                        {
                            "header": "Homogeneity",
                            "mapping": "homogeneity",
                            "dataIndex": "homogeneity",
                            "sortable": true,
                            "filterable": true,
                            "name": "homogeneity"
                        },
                        {
                            "header": "Stand Size",
                            "mapping": "stand_size",
                            "dataIndex": "stand_size",
                            "sortable": true,
                            "filterable": true,
                            "name": "stand_size"
                        },
                        {
                            "header": "Drainage",
                            "mapping": "drainage",
                            "dataIndex": "drainage",
                            "sortable": true,
                            "filterable": true,
                            "name": "drainage"
                        },
                        {
                            "header": "Salinity",
                            "mapping": "salinity",
                            "dataIndex": "salinity",
                            "sortable": true,
                            "filterable": true,
                            "name": "salinity"
                        },
                        {
                            "header": "Hydrologic Regime",
                            "mapping": "hydrologic_regime",
                            "dataIndex": "hydrologic_regime",
                            "sortable": true,
                            "filterable": true,
                            "name": "hydrologic_regime"
                        },
                        {
                            "header": "Oneo Disturbance Type",
                            "mapping": "oneo_disturbance_type",
                            "dataIndex": "oneo_disturbance_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "header": "Oneo Disturbance Severity",
                            "mapping": "oneo_disturbance_severity",
                            "dataIndex": "oneo_disturbance_severity",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "header": "Oneo Disturbance Years Ago",
                            "mapping": "oneo_disturbance_years_ago",
                            "dataIndex": "oneo_disturbance_years_ago",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "header": "Oneo Distubance Per Of Plot",
                            "mapping": "oneo_distubance_per_of_plot",
                            "dataIndex": "oneo_distubance_per_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "header": "Oneo Disturbance Description",
                            "mapping": "oneo_disturbance_description",
                            "dataIndex": "oneo_disturbance_description",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "header": "Twoo Disturbance Type",
                            "mapping": "twoo_disturbance_type",
                            "dataIndex": "twoo_disturbance_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "header": "Twoo Disturbance Severity",
                            "mapping": "twoo_disturbance_severity",
                            "dataIndex": "twoo_disturbance_severity",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "header": "Twoo Disturbance Years Ago",
                            "mapping": "twoo_disturbance_years_ago",
                            "dataIndex": "twoo_disturbance_years_ago",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "header": "Twoo Distubance Per Of Plot",
                            "mapping": "twoo_distubance_per_of_plot",
                            "dataIndex": "twoo_distubance_per_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "header": "Twoo Disturbance Description",
                            "mapping": "twoo_disturbance_description",
                            "dataIndex": "twoo_disturbance_description",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "header": "Threeo Disturbance Type",
                            "mapping": "threeo_disturbance_type",
                            "dataIndex": "threeo_disturbance_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "header": "Threeo Disturbance Severity",
                            "mapping": "threeo_disturbance_severity",
                            "dataIndex": "threeo_disturbance_severity",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "header": "Threeo Disturbance Years Ago",
                            "mapping": "threeo_disturbance_years_ago",
                            "dataIndex": "threeo_disturbance_years_ago",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "header": "Threeo Distubance Per Of Plot",
                            "mapping": "threeo_distubance_per_of_plot",
                            "dataIndex": "threeo_distubance_per_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "header": "Threeo Disturbance Description",
                            "mapping": "threeo_disturbance_description",
                            "dataIndex": "threeo_disturbance_description",
                            "sortable": true,
                            "filterable": true,
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
                            "mapping": "projectLabel",
                            "name": "Project_Label"
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
                            "mapping": "plotPlacement",
                            "name": "plot_placement"
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
                            "mapping": "plotConfigurationOther",
                            "name": "plot_configuration_other"
                        },
                        {
                            "mapping": "estimateOfPerOpenWaterEntireSite",
                            "name": "estimate_of_per_open_water_entire_site",
                            "type": "string"
                        },
                        {
                            "mapping": "EstimateOfPerUnvegetatedOwEntireSite",
                            "name": "Estimate_of_per_unvegetated_ow_entire_site"
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
                            "mapping": "leapLandcoverClassification",
                            "name": "leap_landcover_classification"
                        },
                        {
                            "mapping": "cowardinClassification",
                            "name": "cowardin_classification"
                        },
                        {
                            "mapping": "cowardinWaterRegime",
                            "name": "cowardin_water_regime"
                        },
                        {
                            "mapping": "cowardinSpecialModifier",
                            "name": "cowardin_special_modifier"
                        },
                        {
                            "mapping": "cowardinSpecialModifierOther",
                            "name": "cowardin_special_modifier_other"
                        },
                        {
                            "mapping": "landscapePosition",
                            "name": "landscape_position"
                        },
                        {
                            "mapping": "inlandLandform",
                            "name": "inland_landform"
                        },
                        {
                            "mapping": "waterFlowPath",
                            "name": "water_flow_path"
                        },
                        {
                            "mapping": "llwwModifiers",
                            "name": "llww_modifiers"
                        },
                        {
                            "mapping": "llwwModifiersOther",
                            "name": "llww_modifiers_other"
                        },
                        {
                            "mapping": "landformType",
                            "name": "landform_type"
                        },
                        {
                            "mapping": "landformTypeOther",
                            "name": "landform_type_other"
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
                            "filterable": true,
                            "name": "veg_id"
                        },
                        {
                            "header": "Scientific Name",
                            "mapping": "scientific_name",
                            "dataIndex": "scientific_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "scientific_name"
                        },
                        {
                            "header": "Acronym",
                            "mapping": "acronym",
                            "dataIndex": "acronym",
                            "sortable": true,
                            "filterable": true,
                            "name": "acronym"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "dataIndex": "authority",
                            "sortable": true,
                            "filterable": true,
                            "name": "authority"
                        },
                        {
                            "header": "Cofc",
                            "mapping": "cofc",
                            "dataIndex": "cofc",
                            "sortable": true,
                            "filterable": true,
                            "name": "cofc"
                        },
                        {
                            "header": "Syn",
                            "mapping": "syn",
                            "dataIndex": "syn",
                            "sortable": true,
                            "filterable": true,
                            "name": "syn"
                        },
                        {
                            "header": "Common Name",
                            "mapping": "common_name",
                            "dataIndex": "common_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "common_name"
                        },
                        {
                            "header": "Family",
                            "mapping": "family",
                            "dataIndex": "family",
                            "sortable": true,
                            "filterable": true,
                            "name": "family"
                        },
                        {
                            "header": "Fn",
                            "mapping": "fn",
                            "dataIndex": "fn",
                            "sortable": true,
                            "filterable": true,
                            "name": "fn"
                        },
                        {
                            "header": "Wet",
                            "mapping": "wet",
                            "dataIndex": "wet",
                            "sortable": true,
                            "filterable": true,
                            "name": "wet"
                        },
                        {
                            "header": "Form",
                            "mapping": "form",
                            "dataIndex": "form",
                            "sortable": true,
                            "filterable": true,
                            "name": "form"
                        },
                        {
                            "header": "Habit",
                            "mapping": "habit",
                            "dataIndex": "habit",
                            "sortable": true,
                            "filterable": true,
                            "name": "habit"
                        },
                        {
                            "header": "Shade",
                            "mapping": "shade",
                            "dataIndex": "shade",
                            "sortable": true,
                            "filterable": true,
                            "name": "shade"
                        },
                        {
                            "header": "Usda Id",
                            "mapping": "usda_id",
                            "dataIndex": "usda_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "usda_id"
                        },
                        {
                            "header": "Oh Tore",
                            "mapping": "oh_tore",
                            "dataIndex": "oh_tore",
                            "sortable": true,
                            "filterable": true,
                            "name": "oh_tore"
                        },
                        {
                            "header": "Type",
                            "mapping": "type",
                            "dataIndex": "type",
                            "sortable": true,
                            "filterable": true,
                            "name": "type"
                        },
                        {
                            "header": "Oh Status",
                            "mapping": "oh_status",
                            "dataIndex": "oh_status",
                            "sortable": true,
                            "filterable": true,
                            "name": "oh_status"
                        },
                        {
                            "header": "EMP",
                            "mapping": "emp",
                            "dataIndex": "emp",
                            "sortable": true,
                            "filterable": true,
                            "name": "emp"
                        },
                        {
                            "header": "MW",
                            "mapping": "mw",
                            "dataIndex": "mw",
                            "sortable": true,
                            "filterable": true,
                            "name": "mw"
                        },
                        {
                            "header": "NCNE",
                            "mapping": "ncne",
                            "dataIndex": "ncne",
                            "sortable": true,
                            "filterable": true,
                            "name": "ncne"
                        },
                        {
                            "header": "Notes",
                            "mapping": "notes",
                            "dataIndex": "notes",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "filterable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "filterable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Info",
                            "mapping": "info",
                            "dataIndex": "info",
                            "sortable": true,
                            "filterable": true,
                            "name": "info"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "dataIndex": "relative_cover",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Sub",
                            "mapping": "sub",
                            "dataIndex": "sub",
                            "sortable": true,
                            "filterable": true,
                            "name": "sub"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Dbh Cm",
                            "mapping": "dbh_cm",
                            "dataIndex": "dbh_cm",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "dataIndex": "subcanopy_iv_partial",
                            "sortable": true,
                            "filterable": true,
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "dataIndex": "subcanopy_iv_shade",
                            "sortable": true,
                            "filterable": true,
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "dataIndex": "canopy_iv",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "filterable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "filterable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "filterable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "filterable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "filterable": true,
                            "name": "actual_or_derived"
                        },
                        {
                            "header": "Biomass Weight Grams",
                            "mapping": "biomass_weight_grams",
                            "dataIndex": "biomass_weight_grams",
                            "sortable": true,
                            "filterable": true,
                            "name": "biomass_weight_grams"
                        },
                        {
                            "header": "Grams Per Square Meter",
                            "mapping": "grams_per_sq_meter",
                            "dataIndex": "grams_per_sq_meter",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "filterable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "filterable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "filterable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "filterable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "code"
                        },
                        {
                            "header": "Community Class",
                            "mapping": "community_class",
                            "dataIndex": "community_class",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "cover_code"
                        },
                        {
                            "header": "Midpoint",
                            "mapping": "midpoint",
                            "dataIndex": "midpoint",
                            "sortable": true,
                            "filterable": true,
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
                    "basePath": "mvc/vibi/leapLandcoverClassification/",
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
                            "filterable": true,
                            "name": "code"
                        },
                        {
                            "header": "Description",
                            "mapping": "description",
                            "dataIndex": "description",
                            "sortable": true,
                            "filterable": true,
                            "name": "description"
                        }
                    ],
                    "createTitle": "Create a new Leap Landcover Classification",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Leap Landcover Classification",
                    "fields": [
                        {
                            "mapping": "code",
                            "name": "code"
                        },
                        {
                            "mapping": "description",
                            "name": "description"
                        }
                    ],
                    "iconCls": "vibi_leap_land_cover_calssification_ic",
                    "id": "Leap Landcover Classification",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Leap Landcover Classification",
                    "pluralName": "Leap Landcover Classifications",
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
                    "basePath": "mvc/vibi/fds1SpeciesMiscInfo/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Plot No",
                            "mapping": "plot_no",
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Voucher No",
                            "mapping": "voucher_no",
                            "dataIndex": "voucher_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "voucher_no"
                        },
                        {
                            "header": "Comment",
                            "mapping": "comment",
                            "dataIndex": "comment",
                            "sortable": true,
                            "filterable": true,
                            "name": "comment"
                        },
                        {
                            "header": "Browse Intensity",
                            "mapping": "browse_intensity",
                            "dataIndex": "browse_intensity",
                            "sortable": true,
                            "filterable": true,
                            "name": "browse_intensity"
                        },
                        {
                            "header": "Percent Flowering",
                            "mapping": "percent_flowering",
                            "dataIndex": "percent_flowering",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_flowering"
                        },
                        {
                            "header": "Percent Fruiting",
                            "mapping": "percent_fruiting",
                            "dataIndex": "percent_fruiting",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_fruiting"
                        }
                    ],
                    "createTitle": "Fds1 Species Misc Info",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Fds1 Species Misc Info",
                    "fields": [
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "voucherNo",
                            "name": "voucher_no"
                        },
                        {
                            "mapping": "comment",
                            "name": "comment"
                        },
                        {
                            "mapping": "browseIntensity",
                            "name": "browse_intensity"
                        },
                        {
                            "mapping": "percentFlowering",
                            "name": "percent_flowering"
                        },
                        {
                            "mapping": "percentFruiting",
                            "name": "percent_fruiting"
                        }
                    ],
                    "iconCls": "vibi_fds1_species_misc_info_ic",
                    "id": "Fds1 Species Misc Info",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Fds1 Species Misc Info",
                    "pluralName": "Fds1 Species Misc Info",
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
                    "basePath": "mvc/vibi/fds2SpeciesMiscInfo/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Plot No",
                            "mapping": "plot_no",
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Voucher No",
                            "mapping": "voucher_no",
                            "dataIndex": "voucher_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "voucher_no"
                        },
                        {
                            "header": "Comment",
                            "mapping": "comment",
                            "dataIndex": "comment",
                            "sortable": true,
                            "filterable": true,
                            "name": "comment"
                        },
                        {
                            "header": "Browse Intensity",
                            "mapping": "browse_intensity",
                            "dataIndex": "browse_intensity",
                            "sortable": true,
                            "filterable": true,
                            "name": "browse_intensity"
                        },
                        {
                            "header": "Percent Flowering",
                            "mapping": "percent_flowering",
                            "dataIndex": "percent_flowering",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_flowering"
                        },
                        {
                            "header": "Percent Fruiting",
                            "mapping": "percent_fruiting",
                            "dataIndex": "percent_fruiting",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_fruiting"
                        }
                    ],
                    "createTitle": "Fds2 Species Misc Info",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Fds2 Species Misc Info",
                    "fields": [
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "voucherNo",
                            "name": "voucher_no"
                        },
                        {
                            "mapping": "comment",
                            "name": "comment"
                        },
                        {
                            "mapping": "browseIntensity",
                            "name": "browse_intensity"
                        },
                        {
                            "mapping": "percentFlowering",
                            "name": "percent_flowering"
                        },
                        {
                            "mapping": "percentFruiting",
                            "name": "percent_fruiting"
                        }
                    ],
                    "iconCls": "vibi_fds2_species_misc_info_ic",
                    "id": "Fds2 Species Misc Info",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Fds2 Species Misc Info",
                    "pluralName": "Fds2 Species Misc Info",
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
                            "filterable": true,
                            "name" : "plot_no"
                        }, {
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "dataIndex" : "vibi_type",
                            "sortable": true,
                            "filterable": true,
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "dataIndex" : "score",
                            "sortable": true,
                            "filterable": true,
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "dataIndex" : "carex_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "dataIndex" : "cyperaceae_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "dataIndex" : "dicot_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "dataIndex" : "shade_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "dataIndex" : "shrub_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "dataIndex" : "hydrophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "dataIndex" : "svp_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "dataIndex" : "ap_ratio_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "dataIndex" : "fqai_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "dataIndex" : "bryophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "dataIndex" : "per_hydrophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "dataIndex" : "sensitive_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "dataIndex" : "tolerant_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "dataIndex" : "invasive_graminoids_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
                            "mapping" : "small_tree_metric_value",
                            "dataIndex" : "small_tree_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "small_tree_metric_value"
                        }, {
                            "header" : "Subcanopy IV",
                            "mapping" : "subcanopy_iv",
                            "dataIndex" : "subcanopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name" : "subcanopy_iv"
                        }, {
                            "header" : "Canopy IV",
                            "mapping" : "canopy_iv",
                            "dataIndex" : "canopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name" : "canopy_iv"
                        }, {
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "dataIndex" : "biomass_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Steams HA Wetland Trees",
                            "mapping" : "steams_ha_wetland_trees",
                            "dataIndex" : "steams_ha_wetland_trees",
                            "sortable": true,
                            "filterable": true,
                            "name" : "steams_ha_wetland_trees"
                        }, {
                            "header" : "Steams HA Wetland Shrubs",
                            "mapping" : "steams_ha_wetland_shrubs",
                            "dataIndex" : "steams_ha_wetland_shrubs",
                            "sortable": true,
                            "filterable": true,
                            "name" : "steams_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "dataIndex": "per_unvegetated",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "dataIndex": "per_button_bush",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "dataIndex": "per_perennial_native_hydrophytes",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "dataIndex": "per_adventives",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "dataIndex": "per_open_water",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "dataIndex": "per_unvegetated_open_water",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "dataIndex": "per_bare_ground",
                            "sortable": true,
                            "filterable": true,
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
                            "name" : "steams_ha_wetland_trees"
                        },
                        {
                            "mapping" : "steamsHaWetlandShrubs",
                            "name" : "steams_ha_wetland_shrubs"
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
                },
                {
                    "api": {},
                    "autoExpandColumn": false,
                    "minColumnWidth": 80,
                    "autoload": false,
                    "basePath": "mvc/vibi/altHerbaceousRelativeCover/",
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "dataIndex": "relative_cover",
                            "sortable": true,
                            "filterable": true,
                            "name": "relative_cover"
                        }
                    ],
                    "createTitle": "Create a new Alt Herbaceous Relative Cover",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Alt Herbaceous Relative Cover",
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
                    "id": "Alt Herbaceous Relative Cover",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Alt Herbaceous Relative Cover",
                    "pluralName": "Alt Herbaceous Relative Cover",
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
                    "basePath": "mvc/vibi/altWoodyImportanceValue/",
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "dataIndex": "subcanopy_iv_partial",
                            "sortable": true,
                            "filterable": true,
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "dataIndex": "subcanopy_iv_shade",
                            "sortable": true,
                            "filterable": true,
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "dataIndex": "canopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name": "canopy_iv"
                        }
                    ],
                    "createTitle": "Create a new Alt Woody Importance Value",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Alt Woody Importance Value",
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
                    "id": "Alt Woody Importance Value",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Alt Woody Importance Value",
                    "pluralName": "Alt Woody Importance Values",
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
                    "basePath": "mvc/vibi/altMetrics/",
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
                            "filterable": true,
                            "name" : "plot_no"
                        }, {
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "dataIndex" : "vibi_type",
                            "sortable": true,
                            "filterable": true,
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "dataIndex" : "score",
                            "sortable": true,
                            "filterable": true,
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "dataIndex" : "carex_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "dataIndex" : "cyperaceae_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "dataIndex" : "dicot_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "dataIndex" : "shade_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "dataIndex" : "shrub_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "dataIndex" : "hydrophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "dataIndex" : "svp_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "dataIndex" : "ap_ratio_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "dataIndex" : "fqai_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "dataIndex" : "bryophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "dataIndex" : "per_hydrophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "dataIndex" : "sensitive_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "dataIndex" : "tolerant_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "dataIndex" : "invasive_graminoids_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
                            "mapping" : "small_tree_metric_value",
                            "dataIndex" : "small_tree_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "small_tree_metric_value"
                        }, {
                            "header" : "Subcanopy IV",
                            "mapping" : "subcanopy_iv",
                            "dataIndex" : "subcanopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name" : "subcanopy_iv"
                        }, {
                            "header" : "Canopy IV",
                            "mapping" : "canopy_iv",
                            "dataIndex" : "canopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name" : "canopy_iv"
                        }, {
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "dataIndex" : "biomass_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Steams HA Wetland Trees",
                            "mapping" : "steams_ha_wetland_trees",
                            "dataIndex" : "steams_ha_wetland_trees",
                            "sortable": true,
                            "filterable": true,
                            "name" : "steams_ha_wetland_trees"
                        }, {
                            "header" : "Steams HA Wetland Shrubs",
                            "mapping" : "steams_ha_wetland_shrubs",
                            "dataIndex" : "steams_ha_wetland_shrubs",
                            "sortable": true,
                            "filterable": true,
                            "name" : "steams_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "dataIndex": "per_unvegetated",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "dataIndex": "per_button_bush",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "dataIndex": "per_perennial_native_hydrophytes",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "dataIndex": "per_adventives",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "dataIndex": "per_open_water",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "dataIndex": "per_unvegetated_open_water",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "dataIndex": "per_bare_ground",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_bare_ground"
                        }
                    ],
                    "createTitle": "Create new Alt Metrics",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Alt Metrics",
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
                            "name" : "steams_ha_wetland_trees"
                        },
                        {
                            "mapping" : "steamsHaWetlandShrubs",
                            "name" : "steams_ha_wetland_shrubs"
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
                    "id": "Alt Metrics",
                    "idProperty": "id",
                    "name": "Alt Metrics",
                    "totalProperty" : "totalCount",
                    "pluralName": "Alt Metrics",
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Project Name",
                            "mapping": "project_name",
                            "dataIndex": "project_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "project_name"
                        },
                        {
                            "header": "Plot Name",
                            "mapping": "plot_name",
                            "dataIndex": "plot_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_name"
                        },
                        {
                            "header": "Project Label",
                            "mapping": "Project_Label",
                            "dataIndex": "Project_Label",
                            "sortable": true,
                            "filterable": true,
                            "name": "Project_Label"
                        },
                        {
                            "header": "Monitoring Event",
                            "mapping": "monitoring_event",
                            "dataIndex": "monitoring_event",
                            "sortable": true,
                            "filterable": true,
                            "name": "monitoring_event"
                        },
                        {
                            "header": "Date Timer",
                            "mapping": "timestamptz",
                            "dataIndex": "timestamptz",
                            "sortable": true,
                            "filterable": true,
                            "name": "timestamptz"
                        },
                        {
                            "header": "Party",
                            "mapping": "party",
                            "dataIndex": "party",
                            "sortable": true,
                            "filterable": true,
                            "name": "party"
                        },
                        {
                            "header": "Plot Not Sampled",
                            "mapping": "plot_not_sampled",
                            "dataIndex": "plot_not_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_not_sampled"
                        },
                        {
                            "header": "Comment Plot Not Sampled",
                            "mapping": "commentplot_not_sampled",
                            "dataIndex": "commentplot_not_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "commentplot_not_sampled"
                        },
                        {
                            "header": "Sampling Quality",
                            "mapping": "sampling_quality",
                            "dataIndex": "sampling_quality",
                            "sortable": true,
                            "filterable": true,
                            "name": "sampling_quality"
                        },
                        {
                            "header": "State",
                            "mapping": "state",
                            "dataIndex": "state",
                            "sortable": true,
                            "filterable": true,
                            "name": "state"
                        },
                        {
                            "header": "County",
                            "mapping": "county",
                            "dataIndex": "county",
                            "sortable": true,
                            "filterable": true,
                            "name": "county"
                        },
                        {
                            "header": "Quadrangle",
                            "mapping": "quadrangle",
                            "dataIndex": "quadrangle",
                            "sortable": true,
                            "filterable": true,
                            "name": "quadrangle"
                        },
                        {
                            "header": "Local Place Name",
                            "mapping": "local_place_name",
                            "dataIndex": "local_place_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "local_place_name"
                        },
                        {
                            "header": "Land Owner",
                            "mapping": "landowner",
                            "dataIndex": "landowner",
                            "sortable": true,
                            "filterable": true,
                            "name": "landowner"
                        },
                        {
                            "header": "Xaxis Bearing Of Plot",
                            "mapping": "xaxis_bearing_of_plot",
                            "dataIndex": "xaxis_bearing_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "xaxis_bearing_of_plot"
                        },
                        {
                            "header": "Enter Gps Location In Plot",
                            "mapping": "enter_gps_location_in_plot",
                            "dataIndex": "enter_gps_location_in_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "enter_gps_location_in_plot"
                        },
                        {
                            "header": "Latitude",
                            "mapping": "latitude",
                            "dataIndex": "latitude",
                            "sortable": true,
                            "filterable": true,
                            "name": "latitude"
                        },
                        {
                            "header": "Longitude",
                            "mapping": "longitude",
                            "dataIndex": "longitude",
                            "sortable": true,
                            "filterable": true,
                            "name": "longitude"
                        },
                        {
                            "header": "Plot Placement",
                            "mapping": "plot_placement",
                            "dataIndex": "plot_placement",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_placement"
                        },
                        {
                            "header": "Total Modules",
                            "mapping": "total_modules",
                            "dataIndex": "total_modules",
                            "sortable": true,
                            "filterable": true,
                            "name": "total_modules"
                        },
                        {
                            "header": "Intensive Modules",
                            "mapping": "intensive_modules",
                            "dataIndex": "intensive_modules",
                            "sortable": true,
                            "filterable": true,
                            "name": "intensive_modules"
                        },
                        {
                            "header": "Plot Configuration",
                            "mapping": "plot_configuration",
                            "dataIndex": "plot_configuration",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_configuration"
                        },
                        {
                            "header": "Plot Configuration Other",
                            "mapping": "plot_configuration_other",
                            "dataIndex": "plot_configuration_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_configuration_other"
                        },
                        {
                            "header": "Plot Size For Cover Data Area Ha",
                            "mapping": "plot_size_for_cover_data_area_ha",
                            "dataIndex": "plot_size_for_cover_data_area_ha",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_size_for_cover_data_area_ha"
                        },
                        {
                            "header": "Estimate Of Per Open Water Entire Site",
                            "mapping": "estimate_of_per_open_water_entire_site",
                            "dataIndex": "estimate_of_per_open_water_entire_site",
                            "sortable": true,
                            "filterable": true,
                            "name": "estimate_of_per_open_water_entire_site"
                        },
                        {
                            "header": "Estimate Of Per Unvegetated OW Entire Site",
                            "mapping": "Estimate_of_per_unvegetated_ow_entire_site",
                            "dataIndex": "Estimate_of_per_unvegetated_ow_entire_site",
                            "sortable": true,
                            "filterable": true,
                            "name": "Estimate_of_per_unvegetated_ow_entire_site"
                        },
                        {
                            "header": "Estimate Per Invasives Entire Site",
                            "mapping": "Estimate_per_invasives_entire_site",
                            "dataIndex": "Estimate_per_invasives_entire_site",
                            "sortable": true,
                            "filterable": true,
                            "name": "Estimate_per_invasives_entire_site"
                        },
                        {
                            "header": "Center Line",
                            "mapping": "centerline",
                            "dataIndex": "centerline",
                            "sortable": true,
                            "filterable": true,
                            "name": "centerline"
                        },
                        {
                            "header": "Veg Class",
                            "mapping": "vegclass",
                            "dataIndex": "vegclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "vegclass"
                        },
                        {
                            "header": "Veg Subclass",
                            "mapping": "vegsubclass",
                            "dataIndex": "vegsubclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "vegsubclass"
                        },
                        {
                            "header": "Hgm Class",
                            "mapping": "hgmclass",
                            "dataIndex": "hgmclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "hgmclass"
                        },
                        {
                            "header": "Hgm Subclass",
                            "mapping": "hgmsubclass",
                            "dataIndex": "hgmsubclass",
                            "sortable": true,
                            "filterable": true,
                            "name": "hgmsubclass"
                        },
                        {
                            "header": "Twoo Hgm",
                            "mapping": "twoo_hgm",
                            "dataIndex": "twoo_hgm",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_hgm"
                        },
                        {
                            "header": "Hgm Group",
                            "mapping": "hgmgroup",
                            "dataIndex": "hgmgroup",
                            "sortable": true,
                            "filterable": true,
                            "name": "hgmgroup"
                        },
                        {
                            "header": "Oneo Class Code Mod Nature Serve",
                            "mapping": "oneo_class_code_mod_natureServe",
                            "dataIndex": "oneo_class_code_mod_natureServe",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_class_code_mod_natureServe"
                        },
                        {
                            "header": "Leap Land Cover Classification",
                            "mapping": "leap_landcover_classification",
                            "dataIndex": "leap_landcover_classification",
                            "sortable": true,
                            "filterable": true,
                            "name": "leap_landcover_classification"
                        },
                        {
                            "header": "Cowardin Classification",
                            "mapping": "cowardin_classification",
                            "dataIndex": "cowardin_classification",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_classification"
                        },
                        {
                            "header": "Cowardin Water Regime",
                            "mapping": "cowardin_water_regime",
                            "dataIndex": "cowardin_water_regime",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_water_regime"
                        },
                        {
                            "header": "Cowardin Special Modifier",
                            "mapping": "cowardin_special_modifier",
                            "dataIndex": "cowardin_special_modifier",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_special_modifier"
                        },
                        {
                            "header": "cowardin Special Modifier Other",
                            "mapping": "cowardin_special_modifier_other",
                            "dataIndex": "cowardin_special_modifier_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "cowardin_special_modifier_other"
                        },
                        {
                            "header": "Landscape Position",
                            "mapping": "landscape_position",
                            "dataIndex": "landscape_position",
                            "sortable": true,
                            "filterable": true,
                            "name": "landscape_position"
                        },
                        {
                            "header": "Inland Landform",
                            "mapping": "inland_landform",
                            "dataIndex": "inland_landform",
                            "sortable": true,
                            "filterable": true,
                            "name": "inland_landform"
                        },
                        {
                            "header": "Water Flow Path",
                            "mapping": "water_flow_path",
                            "dataIndex": "water_flow_path",
                            "sortable": true,
                            "filterable": true,
                            "name": "water_flow_path"
                        },
                        {
                            "header": "Llww Modifiers",
                            "mapping": "llww_modifiers",
                            "dataIndex": "llww_modifiers",
                            "sortable": true,
                            "filterable": true,
                            "name": "llww_modifiers"
                        },
                        {
                            "header": "Llww Modifiers Other",
                            "mapping": "llww_modifiers_other",
                            "dataIndex": "llww_modifiers_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "llww_modifiers_other"
                        },
                        {
                            "header": "Landform Type",
                            "mapping": "landform_type",
                            "dataIndex": "landform_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "landform_type"
                        },
                        {
                            "header": "Landform Type Other",
                            "mapping": "landform_type_other",
                            "dataIndex": "landform_type_other",
                            "sortable": true,
                            "filterable": true,
                            "name": "landform_type_other"
                        },
                        {
                            "header": "Homogeneity",
                            "mapping": "homogeneity",
                            "dataIndex": "homogeneity",
                            "sortable": true,
                            "filterable": true,
                            "name": "homogeneity"
                        },
                        {
                            "header": "Stand Size",
                            "mapping": "stand_size",
                            "dataIndex": "stand_size",
                            "sortable": true,
                            "filterable": true,
                            "name": "stand_size"
                        },
                        {
                            "header": "Drainage",
                            "mapping": "drainage",
                            "dataIndex": "drainage",
                            "sortable": true,
                            "filterable": true,
                            "name": "drainage"
                        },
                        {
                            "header": "Salinity",
                            "mapping": "salinity",
                            "dataIndex": "salinity",
                            "sortable": true,
                            "filterable": true,
                            "name": "salinity"
                        },
                        {
                            "header": "Hydrologic Regime",
                            "mapping": "hydrologic_regime",
                            "dataIndex": "hydrologic_regime",
                            "sortable": true,
                            "filterable": true,
                            "name": "hydrologic_regime"
                        },
                        {
                            "header": "Oneo Disturbance Type",
                            "mapping": "oneo_disturbance_type",
                            "dataIndex": "oneo_disturbance_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_type"
                        },
                        {
                            "header": "Oneo Disturbance Severity",
                            "mapping": "oneo_disturbance_severity",
                            "dataIndex": "oneo_disturbance_severity",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_severity"
                        },
                        {
                            "header": "Oneo Disturbance Years Ago",
                            "mapping": "oneo_disturbance_years_ago",
                            "dataIndex": "oneo_disturbance_years_ago",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_years_ago"
                        },
                        {
                            "header": "Oneo Distubance Per Of Plot",
                            "mapping": "oneo_distubance_per_of_plot",
                            "dataIndex": "oneo_distubance_per_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_distubance_per_of_plot"
                        },
                        {
                            "header": "Oneo Disturbance Description",
                            "mapping": "oneo_disturbance_description",
                            "dataIndex": "oneo_disturbance_description",
                            "sortable": true,
                            "filterable": true,
                            "name": "oneo_disturbance_description"
                        },
                        {
                            "header": "Twoo Disturbance Type",
                            "mapping": "twoo_disturbance_type",
                            "dataIndex": "twoo_disturbance_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_type"
                        },
                        {
                            "header": "Twoo Disturbance Severity",
                            "mapping": "twoo_disturbance_severity",
                            "dataIndex": "twoo_disturbance_severity",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_severity"
                        },
                        {
                            "header": "Twoo Disturbance Years Ago",
                            "mapping": "twoo_disturbance_years_ago",
                            "dataIndex": "twoo_disturbance_years_ago",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_years_ago"
                        },
                        {
                            "header": "Twoo Distubance Per Of Plot",
                            "mapping": "twoo_distubance_per_of_plot",
                            "dataIndex": "twoo_distubance_per_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_distubance_per_of_plot"
                        },
                        {
                            "header": "Twoo Disturbance Description",
                            "mapping": "twoo_disturbance_description",
                            "dataIndex": "twoo_disturbance_description",
                            "sortable": true,
                            "filterable": true,
                            "name": "twoo_disturbance_description"
                        },
                        {
                            "header": "Threeo Disturbance Type",
                            "mapping": "threeo_disturbance_type",
                            "dataIndex": "threeo_disturbance_type",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_disturbance_type"
                        },
                        {
                            "header": "Threeo Disturbance Severity",
                            "mapping": "threeo_disturbance_severity",
                            "dataIndex": "threeo_disturbance_severity",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_disturbance_severity"
                        },
                        {
                            "header": "Threeo Disturbance Years Ago",
                            "mapping": "threeo_disturbance_years_ago",
                            "dataIndex": "threeo_disturbance_years_ago",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_disturbance_years_ago"
                        },
                        {
                            "header": "Threeo Distubance Per Of Plot",
                            "mapping": "threeo_distubance_per_of_plot",
                            "dataIndex": "threeo_distubance_per_of_plot",
                            "sortable": true,
                            "filterable": true,
                            "name": "threeo_distubance_per_of_plot"
                        },
                        {
                            "header": "Threeo Disturbance Description",
                            "mapping": "threeo_disturbance_description",
                            "dataIndex": "threeo_disturbance_description",
                            "sortable": true,
                            "filterable": true,
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
                            "mapping": "projectLabel",
                            "name": "Project_Label"
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
                            "mapping": "plotPlacement",
                            "name": "plot_placement"
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
                            "mapping": "plotConfigurationOther",
                            "name": "plot_configuration_other"
                        },
                        {
                            "mapping": "estimateOfPerOpenWaterEntireSite",
                            "name": "estimate_of_per_open_water_entire_site",
                            "type": "string"
                        },
                        {
                            "mapping": "EstimateOfPerUnvegetatedOwEntireSite",
                            "name": "Estimate_of_per_unvegetated_ow_entire_site"
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
                            "mapping": "leapLandcoverClassification",
                            "name": "leap_landcover_classification"
                        },
                        {
                            "mapping": "cowardinClassification",
                            "name": "cowardin_classification"
                        },
                        {
                            "mapping": "cowardinWaterRegime",
                            "name": "cowardin_water_regime"
                        },
                        {
                            "mapping": "cowardinSpecialModifier",
                            "name": "cowardin_special_modifier"
                        },
                        {
                            "mapping": "cowardinSpecialModifierOther",
                            "name": "cowardin_special_modifier_other"
                        },
                        {
                            "mapping": "landscapePosition",
                            "name": "landscape_position"
                        },
                        {
                            "mapping": "inlandLandform",
                            "name": "inland_landform"
                        },
                        {
                            "mapping": "waterFlowPath",
                            "name": "water_flow_path"
                        },
                        {
                            "mapping": "llwwModifiers",
                            "name": "llww_modifiers"
                        },
                        {
                            "mapping": "llwwModifiersOther",
                            "name": "llww_modifiers_other"
                        },
                        {
                            "mapping": "landformType",
                            "name": "landform_type"
                        },
                        {
                            "mapping": "landformTypeOther",
                            "name": "landform_type_other"
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
                            "filterable": true,
                            "name": "veg_id"
                        },
                        {
                            "header": "Scientific Name",
                            "mapping": "scientific_name",
                            "dataIndex": "scientific_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "scientific_name"
                        },
                        {
                            "header": "Acronym",
                            "mapping": "acronym",
                            "dataIndex": "acronym",
                            "sortable": true,
                            "filterable": true,
                            "name": "acronym"
                        },
                        {
                            "header": "Authority",
                            "mapping": "authority",
                            "dataIndex": "authority",
                            "sortable": true,
                            "filterable": true,
                            "name": "authority"
                        },
                        {
                            "header": "Cofc",
                            "mapping": "cofc",
                            "dataIndex": "cofc",
                            "sortable": true,
                            "filterable": true,
                            "name": "cofc"
                        },
                        {
                            "header": "Syn",
                            "mapping": "syn",
                            "dataIndex": "syn",
                            "sortable": true,
                            "filterable": true,
                            "name": "syn"
                        },
                        {
                            "header": "Common Name",
                            "mapping": "common_name",
                            "dataIndex": "common_name",
                            "sortable": true,
                            "filterable": true,
                            "name": "common_name"
                        },
                        {
                            "header": "Family",
                            "mapping": "family",
                            "dataIndex": "family",
                            "sortable": true,
                            "filterable": true,
                            "name": "family"
                        },
                        {
                            "header": "Fn",
                            "mapping": "fn",
                            "dataIndex": "fn",
                            "sortable": true,
                            "filterable": true,
                            "name": "fn"
                        },
                        {
                            "header": "Wet",
                            "mapping": "wet",
                            "dataIndex": "wet",
                            "sortable": true,
                            "filterable": true,
                            "name": "wet"
                        },
                        {
                            "header": "Form",
                            "mapping": "form",
                            "dataIndex": "form",
                            "sortable": true,
                            "filterable": true,
                            "name": "form"
                        },
                        {
                            "header": "Habit",
                            "mapping": "habit",
                            "dataIndex": "habit",
                            "sortable": true,
                            "filterable": true,
                            "name": "habit"
                        },
                        {
                            "header": "Shade",
                            "mapping": "shade",
                            "dataIndex": "shade",
                            "sortable": true,
                            "filterable": true,
                            "name": "shade"
                        },
                        {
                            "header": "Usda Id",
                            "mapping": "usda_id",
                            "dataIndex": "usda_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "usda_id"
                        },
                        {
                            "header": "Oh Tore",
                            "mapping": "oh_tore",
                            "dataIndex": "oh_tore",
                            "sortable": true,
                            "filterable": true,
                            "name": "oh_tore"
                        },
                        {
                            "header": "Type",
                            "mapping": "type",
                            "dataIndex": "type",
                            "sortable": true,
                            "filterable": true,
                            "name": "type"
                        },
                        {
                            "header": "Oh Status",
                            "mapping": "oh_status",
                            "dataIndex": "oh_status",
                            "sortable": true,
                            "filterable": true,
                            "name": "oh_status"
                        },
                        {
                            "header": "EMP",
                            "mapping": "emp",
                            "dataIndex": "emp",
                            "sortable": true,
                            "filterable": true,
                            "name": "emp"
                        },
                        {
                            "header": "MW",
                            "mapping": "mw",
                            "dataIndex": "mw",
                            "sortable": true,
                            "filterable": true,
                            "name": "mw"
                        },
                        {
                            "header": "NCNE",
                            "mapping": "ncne",
                            "dataIndex": "ncne",
                            "sortable": true,
                            "filterable": true,
                            "name": "ncne"
                        },
                        {
                            "header": "Notes",
                            "mapping": "notes",
                            "dataIndex": "notes",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "filterable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner Id",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Depth",
                            "mapping": "depth",
                            "dataIndex": "depth",
                            "sortable": true,
                            "filterable": true,
                            "name": "depth"
                        },
                        {
                            "header": "Info",
                            "mapping": "info",
                            "dataIndex": "info",
                            "sortable": true,
                            "filterable": true,
                            "name": "info"
                        },
                        {
                            "header": "Cover Class Code",
                            "mapping": "cover_class_code",
                            "dataIndex": "cover_class_code",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Relative Cover",
                            "mapping": "relative_cover",
                            "dataIndex": "relative_cover",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Sub",
                            "mapping": "sub",
                            "dataIndex": "sub",
                            "sortable": true,
                            "filterable": true,
                            "name": "sub"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Count",
                            "mapping": "count",
                            "dataIndex": "count",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Dbh Class",
                            "mapping": "dbh_class",
                            "dataIndex": "dbh_class",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class"
                        },
                        {
                            "header": "Dbh Class Index",
                            "mapping": "dbh_class_index",
                            "dataIndex": "dbh_class_index",
                            "sortable": true,
                            "filterable": true,
                            "name": "dbh_class_index"
                        },
                        {
                            "header": "Dbh Cm",
                            "mapping": "dbh_cm",
                            "dataIndex": "dbh_cm",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Subcanopy IV Partial",
                            "mapping": "subcanopy_iv_partial",
                            "dataIndex": "subcanopy_iv_partial",
                            "sortable": true,
                            "filterable": true,
                            "name": "subcanopy_iv_partial"
                        },
                        {
                            "header": "Subcanopy IV Shade",
                            "mapping": "subcanopy_iv_shade",
                            "dataIndex": "subcanopy_iv_shade",
                            "sortable": true,
                            "filterable": true,
                            "name": "subcanopy_iv_shade"
                        },
                        {
                            "header": "Canopy IV",
                            "mapping": "canopy_iv",
                            "dataIndex": "canopy_iv",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "filterable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "filterable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "filterable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "filterable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "filterable": true,
                            "name": "actual_or_derived"
                        },
                        {
                            "header": "Biomass Weight Grams",
                            "mapping": "biomass_weight_grams",
                            "dataIndex": "biomass_weight_grams",
                            "sortable": true,
                            "filterable": true,
                            "name": "biomass_weight_grams"
                        },
                        {
                            "header": "Grams Per Square Meter",
                            "mapping": "grams_per_sq_meter",
                            "dataIndex": "grams_per_sq_meter",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Date Time",
                            "mapping": "date_time",
                            "dataIndex": "date_time",
                            "sortable": true,
                            "filterable": true,
                            "name": "date_time"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Corner",
                            "mapping": "corner",
                            "dataIndex": "corner",
                            "sortable": true,
                            "filterable": true,
                            "name": "corner"
                        },
                        {
                            "header": "Sample Id",
                            "mapping": "sample_id",
                            "dataIndex": "sample_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "sample_id"
                        },
                        {
                            "header": "Area Sampled",
                            "mapping": "area_sampled",
                            "dataIndex": "area_sampled",
                            "sortable": true,
                            "filterable": true,
                            "name": "area_sampled"
                        },
                        {
                            "header": "Weight With Bag",
                            "mapping": "weight_with_bag",
                            "dataIndex": "weight_with_bag",
                            "sortable": true,
                            "filterable": true,
                            "name": "weight_with_bag"
                        },
                        {
                            "header": "Bag Weight",
                            "mapping": "bag_weight",
                            "dataIndex": "bag_weight",
                            "sortable": true,
                            "filterable": true,
                            "name": "bag_weight"
                        },
                        {
                            "header": "Biomass Collected",
                            "mapping": "biomass_collected",
                            "dataIndex": "biomass_collected",
                            "sortable": true,
                            "filterable": true,
                            "name": "biomass_collected"
                        },
                        {
                            "header": "Actual Or Derived",
                            "mapping": "actual_or_derived",
                            "dataIndex": "actual_or_derived",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "code"
                        },
                        {
                            "header": "Community Class",
                            "mapping": "community_class",
                            "dataIndex": "community_class",
                            "sortable": true,
                            "filterable": true,
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
                            "filterable": true,
                            "name": "cover_code"
                        },
                        {
                            "header": "Midpoint",
                            "mapping": "midpoint",
                            "dataIndex": "midpoint",
                            "sortable": true,
                            "filterable": true,
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
                    "basePath": "mvc/vibi/leapLandcoverClassification/",
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
                            "filterable": true,
                            "name": "code"
                        },
                        {
                            "header": "Description",
                            "mapping": "description",
                            "dataIndex": "description",
                            "sortable": true,
                            "filterable": true,
                            "name": "description"
                        }
                    ],
                    "createTitle": "Create a new Leap Landcover Classification",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Leap Landcover Classification",
                    "fields": [
                        {
                            "mapping": "code",
                            "name": "code"
                        },
                        {
                            "mapping": "description",
                            "name": "description"
                        }
                    ],
                    "iconCls": "vibi_leap_land_cover_calssification_ic",
                    "id": "Leap Landcover Classification",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Leap Landcover Classification",
                    "pluralName": "Leap Landcover Classifications",
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
                    "basePath": "mvc/vibi/fds1SpeciesMiscInfo/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Plot No",
                            "mapping": "plot_no",
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Voucher No",
                            "mapping": "voucher_no",
                            "dataIndex": "voucher_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "voucher_no"
                        },
                        {
                            "header": "Comment",
                            "mapping": "comment",
                            "dataIndex": "comment",
                            "sortable": true,
                            "filterable": true,
                            "name": "comment"
                        },
                        {
                            "header": "Browse Intensity",
                            "mapping": "browse_intensity",
                            "dataIndex": "browse_intensity",
                            "sortable": true,
                            "filterable": true,
                            "name": "browse_intensity"
                        },
                        {
                            "header": "Percent Flowering",
                            "mapping": "percent_flowering",
                            "dataIndex": "percent_flowering",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_flowering"
                        },
                        {
                            "header": "Percent Fruiting",
                            "mapping": "percent_fruiting",
                            "dataIndex": "percent_fruiting",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_fruiting"
                        }
                    ],
                    "createTitle": "Fds1 Species Misc Info",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Fds1 Species Misc Info",
                    "fields": [
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "voucherNo",
                            "name": "voucher_no"
                        },
                        {
                            "mapping": "comment",
                            "name": "comment"
                        },
                        {
                            "mapping": "browseIntensity",
                            "name": "browse_intensity"
                        },
                        {
                            "mapping": "percentFlowering",
                            "name": "percent_flowering"
                        },
                        {
                            "mapping": "percentFruiting",
                            "name": "percent_fruiting"
                        }
                    ],
                    "iconCls": "vibi_fds1_species_misc_info_ic",
                    "id": "Fds1 Species Misc Info",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Fds1 Species Misc Info",
                    "pluralName": "Fds1 Species Misc Info",
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
                    "basePath": "mvc/vibi/fds2SpeciesMiscInfo/",
                    "canCreate": false,
                    "canDelete": false,
                    "canEdit": false,
                    "canSearch": true,
                    "columns": [
                        {
                            "header": "Species",
                            "mapping": "species",
                            "dataIndex": "species",
                            "sortable": true,
                            "filterable": true,
                            "name": "species"
                        },
                        {
                            "header": "Plot No",
                            "mapping": "plot_no",
                            "dataIndex": "plot_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "plot_no"
                        },
                        {
                            "header": "Module Id",
                            "mapping": "module_id",
                            "dataIndex": "module_id",
                            "sortable": true,
                            "filterable": true,
                            "name": "module_id"
                        },
                        {
                            "header": "Voucher No",
                            "mapping": "voucher_no",
                            "dataIndex": "voucher_no",
                            "sortable": true,
                            "filterable": true,
                            "name": "voucher_no"
                        },
                        {
                            "header": "Comment",
                            "mapping": "comment",
                            "dataIndex": "comment",
                            "sortable": true,
                            "filterable": true,
                            "name": "comment"
                        },
                        {
                            "header": "Browse Intensity",
                            "mapping": "browse_intensity",
                            "dataIndex": "browse_intensity",
                            "sortable": true,
                            "filterable": true,
                            "name": "browse_intensity"
                        },
                        {
                            "header": "Percent Flowering",
                            "mapping": "percent_flowering",
                            "dataIndex": "percent_flowering",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_flowering"
                        },
                        {
                            "header": "Percent Fruiting",
                            "mapping": "percent_fruiting",
                            "dataIndex": "percent_fruiting",
                            "sortable": true,
                            "filterable": true,
                            "name": "percent_fruiting"
                        }
                    ],
                    "createTitle": "Fds2 Species Misc Info",
                    "displayField": "label",
                    "editHeight": 270,
                    "editTitle": "Edit Fds2 Species Misc Info",
                    "fields": [
                        {
                            "mapping": "species",
                            "name": "species"
                        },
                        {
                            "mapping": "plotNo",
                            "name": "plot_no"
                        },
                        {
                            "mapping": "moduleId",
                            "name": "module_id"
                        },
                        {
                            "mapping": "voucherNo",
                            "name": "voucher_no"
                        },
                        {
                            "mapping": "comment",
                            "name": "comment"
                        },
                        {
                            "mapping": "browseIntensity",
                            "name": "browse_intensity"
                        },
                        {
                            "mapping": "percentFlowering",
                            "name": "percent_flowering"
                        },
                        {
                            "mapping": "percentFruiting",
                            "name": "percent_fruiting"
                        }
                    ],
                    "iconCls": "vibi_fds2_species_misc_info_ic",
                    "id": "Fds2 Species Misc Info",
                    "idProperty": "id",
                    "totalProperty" : "totalCount",
                    "name": "Fds2 Species Misc Info",
                    "pluralName": "Fds2 Species Misc Info",
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
                            "filterable": true,
                            "name" : "plot_no"
                        }, {
                            "header" : "Vibi Type",
                            "mapping" : "vibi_type",
                            "dataIndex" : "vibi_type",
                            "sortable": true,
                            "filterable": true,
                            "name" : "vibi_type"
                        }, {
                            "header" : "Vibi Score",
                            "mapping" : "score",
                            "dataIndex" : "score",
                            "sortable": true,
                            "filterable": true,
                            "name" : "score"
                        }, {
                            "header" : "Carex",
                            "mapping" : "carex_metric_value",
                            "dataIndex" : "carex_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "carex_metric_value"
                        }, {
                            "header" : "Cyperaceae",
                            "mapping" : "cyperaceae_metric_value",
                            "dataIndex" : "cyperaceae_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "cyperaceae_metric_value"
                        }, {
                            "header" : "Dicot",
                            "mapping" : "dicot_metric_value",
                            "dataIndex" : "dicot_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "dicot_metric_value"
                        }, {
                            "header" : "Shade",
                            "mapping" : "shade_metric_value",
                            "dataIndex" : "shade_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "shade_metric_value"
                        }, {
                            "header" : "Shrub",
                            "mapping" : "shrub_metric_value",
                            "dataIndex" : "shrub_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "shrub_metric_value"
                        }, {
                            "header" : "Hydrophyte",
                            "mapping" : "hydrophyte_metric_value",
                            "dataIndex" : "hydrophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "hydrophyte_metric_value"
                        }, {
                            "header" : "SVP",
                            "mapping" : "svp_metric_value",
                            "dataIndex" : "svp_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "svp_metric_value"
                        }, {
                            "header" : "Ap Ratio",
                            "mapping" : "ap_ratio_metric_value",
                            "dataIndex" : "ap_ratio_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "ap_ratio_metric_value"
                        }, {
                            "header" : "FQAI",
                            "mapping" : "fqai_metric_value",
                            "dataIndex" : "fqai_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "fqai_metric_value"
                        }, {
                            "header" : "Bryophyte",
                            "mapping" : "bryophyte_metric_value",
                            "dataIndex" : "bryophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "bryophyte_metric_value"
                        }, {
                            "header" : "Per Hydrophyte",
                            "mapping" : "per_hydrophyte_metric_value",
                            "dataIndex" : "per_hydrophyte_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "per_hydrophyte_metric_value"
                        }, {
                            "header" : "Sensitive",
                            "mapping" : "sensitive_metric_value",
                            "dataIndex" : "sensitive_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "sensitive_metric_value"
                        }, {
                            "header" : "Tolerant",
                            "mapping" : "tolerant_metric_value",
                            "dataIndex" : "tolerant_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "tolerant_metric_value"
                        }, {
                            "header" : "Invasive Graminoids",
                            "mapping" : "invasive_graminoids_metric_value",
                            "dataIndex" : "invasive_graminoids_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "invasive_graminoids_metric_value"
                        }, {
                            "header" : "Small Tree",
                            "mapping" : "small_tree_metric_value",
                            "dataIndex" : "small_tree_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "small_tree_metric_value"
                        }, {
                            "header" : "Subcanopy IV",
                            "mapping" : "subcanopy_iv",
                            "dataIndex" : "subcanopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name" : "subcanopy_iv"
                        }, {
                            "header" : "Canopy IV",
                            "mapping" : "canopy_iv",
                            "dataIndex" : "canopy_iv",
                            "sortable": true,
                            "filterable": true,
                            "name" : "canopy_iv"
                        }, {
                            "header" : "Biomass",
                            "mapping" : "biomass_metric_value",
                            "dataIndex" : "biomass_metric_value",
                            "sortable": true,
                            "filterable": true,
                            "name" : "biomass_metric_value"
                        }, {
                            "header" : "Steams HA Wetland Trees",
                            "mapping" : "steams_ha_wetland_trees",
                            "dataIndex" : "steams_ha_wetland_trees",
                            "sortable": true,
                            "filterable": true,
                            "name" : "steams_ha_wetland_trees"
                        }, {
                            "header" : "Steams HA Wetland Shrubs",
                            "mapping" : "steams_ha_wetland_shrubs",
                            "dataIndex" : "steams_ha_wetland_shrubs",
                            "sortable": true,
                            "filterable": true,
                            "name" : "steams_ha_wetland_shrubs"
                        },
                        {
                            "header": "Per Unvegetated",
                            "mapping": "per_unvegetated",
                            "dataIndex": "per_unvegetated",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_unvegetated"
                        },
                        {
                            "header": "Per Button Bush",
                            "mapping": "per_button_bush",
                            "dataIndex": "per_button_bush",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_button_bush"
                        },
                        {
                            "header": "Per Perennial Native Hydrophytes",
                            "mapping": "per_perennial_native_hydrophytes",
                            "dataIndex": "per_perennial_native_hydrophytes",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_perennial_native_hydrophytes"
                        },
                        {
                            "header": "Per Adventives",
                            "mapping": "per_adventives",
                            "dataIndex": "per_adventives",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_adventives"
                        },
                        {
                            "header": "Per PpenWater",
                            "mapping": "per_open_water",
                            "dataIndex": "per_open_water",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_open_water"
                        },
                        {
                            "header": "Per Unvegetated OpenWater",
                            "mapping": "per_unvegetated_open_water",
                            "dataIndex": "per_unvegetated_open_water",
                            "sortable": true,
                            "filterable": true,
                            "name": "per_unvegetated_open_water"
                        },
                        {
                            "header": "Per Bare Ground",
                            "mapping": "per_bare_ground",
                            "dataIndex": "per_bare_ground",
                            "sortable": true,
                            "filterable": true,
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
                            "name" : "steams_ha_wetland_trees"
                        },
                        {
                            "mapping" : "steamsHaWetlandShrubs",
                            "name" : "steams_ha_wetland_shrubs"
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
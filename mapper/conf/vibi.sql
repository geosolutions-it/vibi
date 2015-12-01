DROP TABLE IF EXISTS class_code_Mod_NatureServe CASCADE;
DROP TABLE IF EXISTS hgm_subclass CASCADE;
DROP TABLE IF EXISTS biomass CASCADE;
DROP TABLE IF EXISTS biomass_accuracy CASCADE;
DROP TABLE IF EXISTS corner CASCADE;
DROP TABLE IF EXISTS cover_midpoint_lookup CASCADE;
DROP TABLE IF EXISTS dbh_class CASCADE;
DROP TABLE IF EXISTS depth CASCADE;
DROP TABLE IF EXISTS disturbance_severity CASCADE;
DROP TABLE IF EXISTS disturbance_type CASCADE;
DROP TABLE IF EXISTS drainage CASCADE;
DROP TABLE IF EXISTS herbaceous_relative_cover CASCADE;
DROP TABLE IF EXISTS hgm_class CASCADE;
DROP TABLE IF EXISTS hgm_group CASCADE;
DROP TABLE IF EXISTS homogeneity CASCADE;
DROP TABLE IF EXISTS hydrologic_regime CASCADE;
DROP TABLE IF EXISTS landform_type CASCADE;
DROP TABLE IF EXISTS module CASCADE;
DROP TABLE IF EXISTS plant_comm_code CASCADE;
DROP TABLE IF EXISTS plot CASCADE;
DROP TABLE IF EXISTS plot_module_herbaceous CASCADE;
DROP TABLE IF EXISTS plot_module_woody CASCADE;
DROP TABLE IF EXISTS salinity CASCADE;
DROP TABLE IF EXISTS species CASCADE;
DROP TABLE IF EXISTS stand_size CASCADE;
DROP TABLE IF EXISTS veg_class CASCADE;
DROP TABLE IF EXISTS woody_importance_value CASCADE;
DROP TABLE IF EXISTS metric_calculations CASCADE;

CREATE TABLE plant_comm_code
(
  code text PRIMARY KEY,
  description text
)
;

CREATE TABLE veg_class
(
  veg_class text PRIMARY KEY
)
;

CREATE TABLE hgm_group
(
  hgm_group text PRIMARY KEY
)
;

CREATE TABLE hgm_class
(
  hgm_class text PRIMARY KEY
)
;

CREATE TABLE  hgm_subclass
(
  hgm_subclass text PRIMARY KEY
)
;

CREATE TABLE landform_type
(
  landform_type text  PRIMARY KEY
)
;

CREATE TABLE homogeneity
(
  homogeneity text  PRIMARY KEY
)
;

CREATE TABLE stand_size
(
  stand_size text  PRIMARY KEY
)
;

CREATE TABLE drainage
(
  drainage text PRIMARY KEY
)
;

CREATE TABLE salinity
(
  salinity text  PRIMARY KEY
)
;

CREATE TABLE hydrologic_regime
(
  hydrologic_regime text PRIMARY KEY
)
;

CREATE TABLE disturbance_type
(
  disturbance_type text PRIMARY KEY
)
;

CREATE TABLE disturbance_severity
(
  disturbance_severity text PRIMARY KEY
)
;

CREATE TABLE module
(
  module_id int4 PRIMARY KEY
)
;

CREATE TABLE corner
(
  corner int4 PRIMARY KEY
)
;

CREATE TABLE depth
(
  depth int4 PRIMARY KEY
)
;

 CREATE TABLE class_code_Mod_NatureServe --based on columns A-C in "Lookup Community" tab in spreadsheet
(
  code text PRIMARY KEY,
  veg_class text,
  veg_group text
)
;

CREATE TABLE cover_midpoint_lookup
(
  cover_code int4 PRIMARY KEY,
  midpoint numeric
)
;

CREATE TABLE dbh_class
(
  dbh_class text PRIMARY KEY
)
;

CREATE TABLE biomass_accuracy
(
  biomass_accuracy text PRIMARY KEY
)
;

-- species table below will be populated from most recent version of the Ohio EPA-maintained list
CREATE TABLE species
(
  veg_id int4,
  scientific_name text PRIMARY KEY,
  acronym text,
  authority text,
  cofc int4,
  syn text,
  common_name text,
  family text,
  fn int4,
  wet text,
  form text,
  habit text,
  shade text,
  usda_id text,
  oh_tore text,
  type text,
  oh_status text,
  emp text,
  mw text,
  ncne text,
  notes text
)
;

-- From "ENTER PLOT INFO" Tab. Ignore columns after column "BM"

CREATE TABLE plot
(
	Plot_No int4 PRIMARY KEY,
	Project_Name text,
	Plot_Name text,
	Plot_Label text,
	Monitoring_Event text,
	DateTimer timestamptz,
	Party text,
	Plot_Not_Sampled text,
	CommentPlot_not_sampled text,
	Sampling_Quality text,
	Tax_Accuracy_Vascular text,
	Tax_Accuracy_Bryophytes text,
	Tax_Accuracy_Lichens text,
	Authority text,
	State text,
	County text,
	Quadrangle text,
	Local_Place_Name text,
	Landowner text,
	Xaxis_bearing_of_plot int4,
	Enter_GPS_location_in_plot text,
	latitude numeric,
	longitude numeric,
	location geometry,
	Total_Modules int4,
	Intensive_Modules int4,
	Plot_Configuration text,
	Plot_size_for_cover_data_area_ha numeric,
	Estimate_of_per_open_water_entire_site numeric,
	Estimate_of_perunvegetated_ow_entire_site numeric,
	Estimate_per_invasives_entire_site numeric,
	centerline numeric,
	Oneo_plant text references plant_comm_code(code),
	oneo_text text,
	VEGclass text references veg_class(veg_class),
	VEGSubclass text,
	twoo_plant text references plant_comm_code(code),
	HGMClass text references hgm_class(hgm_class),
	HGMSubclass text references hgm_subclass(hgm_subclass),
	twoo_HGM text,
	HGMgroup text references hgm_group(hgm_group),
	oneo_class_code_Mod_NatureServe text references class_code_Mod_NatureServe(code),
--	oneo_community_Mod_NatureServe text references class_code_Mod_NatureServe(description), -- can be derived from code above
	Veg_Class_Wetlands_Only text references veg_class(veg_class),
	Landform_Type text references landform_type(landform_type),
	Homogeneity text references homogeneity(homogeneity),
	Stand_size text references stand_size(stand_size),
	Drainage text references drainage(drainage),
	Salinity text references salinity(salinity),
	Hydrologic_Regime text references hydrologic_regime(hydrologic_regime),
	oneo_Disturbance_Type text references disturbance_type(disturbance_type),
	oneo_Disturbance_Severity text references disturbance_severity(disturbance_severity),
	oneo_Disturbance_Years_ago int4,
	oneo_Distubance_per_of_plot int4,
	oneo_Disturbance_description text,
	twoo_Disturbance_Type text references disturbance_type(disturbance_type),
	twoo_Disturbance_Severity text references disturbance_severity(disturbance_severity),
	twoo_Disturbance_Years_ago int4,
	twoo_Distubance_per_of_plot int4,
	twoo_Disturbance_description text,
	threeo_Disturbance_Type text references disturbance_type(disturbance_type),
	threeo_Disturbance_Severity text references disturbance_severity(disturbance_severity),
	threeo_Disturbance_Years_ago int4,
	threeo_Distubance_per_of_plot int4,
	threeo_Disturbance_description text
)
;

CREATE TABLE plot_module_herbaceous
(
  fid text PRIMARY KEY,
  plot_no int4 references plot(plot_no),
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  depth integer references depth(depth),
  species text references species(scientific_name),
  cover_class_code integer references cover_midpoint_lookup(cover_code)
--  cover_class_midpoint numeric references cover_midpoint_lookup(midpoint)  -- this should be auto populated based on code above
)
;

--includes calculated field
CREATE TABLE herbaceous_relative_cover
(
  plot_no int4 references plot(plot_no),
  species text references species(scientific_name),
  relative_cover numeric -- this is calculated, see columns AP-AR on "Reduced FDS1" tab in spreadsheet
)
;

CREATE TABLE plot_module_woody
(
  plot_no int4 references plot(plot_no),
  module_id int4 references module(module_id),
  species text references species(scientific_name),
  dbh_class text references dbh_class(dbh_class),
  count int4,
  dbh_cm int4
)
;

--includes calculated fields
--calculations are done under "Reduced FDS2" tab in spreadsheet
CREATE TABLE woody_importance_value
(
  plot_no int4 references plot(plot_no),
  species text references species(scientific_name),
  subcanopy_IV_partial numeric, --  calculated for those species with "partial" designation in "shade" column in species lookup table
  subcanopy_IV_shade numeric,  --  calculated for those species with "shade" designation in "shade" column in species lookup table
  canopy_IV numeric --  calculated for those species with "tree" designation in "shade" column in species lookup table
)
;

--includes calculated fields
CREATE TABLE biomass
(
  plot_no int4 references plot(plot_no),
  DateTime timestamptz,
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  sample_id text,
  area_sampled numeric,
  weight_with_bag numeric,
  bag_weight numeric,
  actual_or_derived text references biomass_accuracy(biomass_accuracy), -- this lookup needs to be created
  biomass_weight_grams numeric, --calculated; see column N in "Enter Biomass" tab of spreadsheet
  grams_per_sq_meter numeric  --calculated; see column O in "Enter Biomass" tab of spreadsheet
)
;

-- one big table (or materialize view) that includes all metric calculations for each plot
-- if table, needs tied to trigger
-- if view, needs to be performant

CREATE TABLE metric_calculations
(
  plot_no int4 references plot(plot_no),
  carex_metric_value int, -- see row 6 column B of "Calculations" tab in  cyperaceae_metric_value int -- see row 7 column B of "Calculations" tab in spreadsheet
  dicot_metric_value int, -- see row 8 column B of "Calculations" tab in spreadsheet
  shade_metric_value int, -- see row 9 column B of "Calculations" tab in spreadsheet
  shrub_metric_value int, -- see row 10 column B of "Calculations" tab in spreadsheet
  hydrophyte_metric_value int, -- see row 11 column B of "Calculations" tab in spreadsheet
  SVP_metric_value int, -- see row 12 column B of "Calculations" tab in spreadsheet
  AP_ratio_metric_value numeric, -- see row 13 column B of "Calculations" tab in spreadsheet
  FQAI_metric_value numeric, -- see row 14 column B of "Calculations" tab in spreadsheet
  bryophyte_metric_value numeric, -- see row 15 column B of "Calculations" tab in spreadsheet
  sensitive_metric_value numeric, -- see row 17 column B of "Calculations" tab in spreadsheet
  tolerant_metric_value numeric, -- see row 18 column B of "Calculations" tab in spreadsheet
  invasive_graminoid_metric_value numeric, -- see row 19 column B of "Calculations" tab in spreadsheet
  small_tree_metric_value numeric, -- see row 20 column B of "Calculations" tab in spreadsheet
  subcanopy_IV numeric, --21
  canopy_IV numeric, --22
  biomass numeric,  --23
  stems_ha_wetland_trees int4, --24
  stems_ha_wetland_shrubs int4, --25
  per_unvegetated numeric, --26
  per_buttonbush numeric, --27
  per_perrenial_native_hydrophytes numeric, --28
  per_adventives numeric, --29
  per_open_water numeric, --30
  per_unvegetated_open_water numeric, --31
  per_bare_ground numeric,  --32
  vibi_score numeric -- Sum of scores as e.g. C33 in Calculations tab
)
;

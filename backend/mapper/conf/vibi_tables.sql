DROP TABLE IF EXISTS class_code_Mod_NatureServe CASCADE;
DROP TABLE IF EXISTS hgm_subclass CASCADE;
DROP TABLE IF EXISTS biomass_raw CASCADE;
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
DROP TABLE IF EXISTS plot_module_herbaceous_info CASCADE;
DROP TABLE IF EXISTS salinity CASCADE;
DROP TABLE IF EXISTS species CASCADE;
DROP TABLE IF EXISTS stand_size CASCADE;
DROP TABLE IF EXISTS veg_class CASCADE;
DROP TABLE IF EXISTS woody_importance_value CASCADE;
DROP TABLE IF EXISTS metric_calculations_orig CASCADE;
DROP TABLE IF EXISTS plot_module_woody_raw CASCADE;
DROP TABLE IF EXISTS authority CASCADE;
DROP TABLE IF EXISTS family CASCADE;
DROP TABLE IF EXISTS ind CASCADE;
DROP TABLE IF EXISTS form CASCADE;
DROP TABLE IF EXISTS habit CASCADE;
DROP TABLE IF EXISTS groupp CASCADE;
DROP TABLE IF EXISTS shade CASCADE;
DROP TABLE IF EXISTS nativity CASCADE;
DROP TABLE IF EXISTS CODE1 CASCADE;
DROP TABLE IF EXISTS CODE2 CASCADE;
DROP TABLE IF EXISTS CODE3 CASCADE;
DROP TABLE IF EXISTS CODE4 CASCADE;
DROP TABLE IF EXISTS CODE5 CASCADE;
DROP TABLE IF EXISTS reduced_fsd2_dbh_index_basal_area CASCADE;
DROP TABLE IF EXISTS rule CASCADE;

CREATE TABLE plant_comm_code (
  code text PRIMARY KEY,
  description text
);

CREATE TABLE veg_class (
  veg_class text PRIMARY KEY
);

CREATE TABLE hgm_group (
  hgm_group text PRIMARY KEY
);

CREATE TABLE hgm_class (
  hgm_class text PRIMARY KEY
);

CREATE TABLE  hgm_subclass (
  hgm_subclass text PRIMARY KEY
);

CREATE TABLE landform_type (
  landform_type text  PRIMARY KEY
);

CREATE TABLE homogeneity (
  homogeneity text  PRIMARY KEY
);

CREATE TABLE stand_size (
  stand_size text  PRIMARY KEY
);

CREATE TABLE drainage (
  drainage text PRIMARY KEY
);

CREATE TABLE salinity (
  salinity text  PRIMARY KEY
);

CREATE TABLE hydrologic_regime (
  hydrologic_regime text PRIMARY KEY
);

CREATE TABLE disturbance_type (
  disturbance_type text PRIMARY KEY
);

CREATE TABLE disturbance_severity (
  disturbance_severity text PRIMARY KEY
);

CREATE TABLE module (
  module_id int4 PRIMARY KEY
);

CREATE TABLE corner (
  corner int4 PRIMARY KEY
);

CREATE TABLE depth (
  depth int4 PRIMARY KEY
);

--based on columns A-C in "Lookup Community" tab in spreadsheet
CREATE TABLE class_code_Mod_NatureServe (
  code text PRIMARY KEY,
  community_class text
);

CREATE TABLE cover_midpoint_lookup (
  cover_code int4 PRIMARY KEY,
  midpoint numeric
);

CREATE TABLE dbh_class (
  dbh_class text PRIMARY KEY
);

CREATE TABLE biomass_accuracy (
  biomass_accuracy text PRIMARY KEY
);

CREATE TABLE authority (
  authority text PRIMARY KEY
);

CREATE TABLE family (
  family text PRIMARY KEY
);

CREATE TABLE ind (
  ind text PRIMARY KEY
);

CREATE TABLE form (
  form text PRIMARY KEY
);

CREATE TABLE habit (
  habit text PRIMARY KEY
);

CREATE TABLE groupp (
  groupp text PRIMARY KEY
);

CREATE TABLE shade (
  shade text PRIMARY KEY
);

CREATE TABLE nativity (
  nativity text PRIMARY KEY
);

CREATE TABLE CODE1 (
  CODE1 text PRIMARY KEY
);

CREATE TABLE CODE2 (
  CODE2 text PRIMARY KEY
);

CREATE TABLE CODE3 (
  CODE3 text PRIMARY KEY
);

CREATE TABLE CODE4 (
  CODE4 text PRIMARY KEY
);

CREATE TABLE CODE5 (
  CODE5 text PRIMARY KEY
);

-- species table below will be populated from most recent version of the Ohio EPA-maintained list
CREATE TABLE species (
  --  veg_id int4 PRIMARY KEY,
  scientific_name text PRIMARY KEY,
  acronym text,
  authority text, --REFERENCES authority(authority),
  cofc int4, -- wildcard ("*") in spreadsheet is null in table
  tolerance text,
  --  syn text,
  common_name text,
  family text, --REFERENCES family(family),
  --  fn int4,
  ind text, -- REFERENCES ind(ind),
  hydro text,
  form text REFERENCES form(form),
  habit text REFERENCES habit(habit),
  groupp text REFERENCES groupp(groupp),
  shade text REFERENCES shade(shade),
  --  usda_id text,
  --  oh_tore text,
  --  type text, -- equivalent to group
  nativity text REFERENCES nativity(nativity), -- oh_status
  --  emp text, -- separate regions in Ohio. To be used later.
  --  mw text, -- ditto
  --  ncne text, -- ditto
  --  notes text
  CODE1 text REFERENCES code1(code1),
  CODE2 text REFERENCES code2(code2),
  CODE3 text REFERENCES code3(code3),
  CODE4 text REFERENCES code4(code4),
  CODE5 text REFERENCES code5(code5)
);

-- From "ENTER PLOT INFO" Tab. Ignore columns after column "BM"
CREATE TABLE plot (
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
);

CREATE TABLE plot_module_herbaceous (
  fid text PRIMARY KEY,
  plot_no int4 references plot(plot_no),
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  depth integer references depth(depth),
  species text references species(scientific_name),
  cover_class_code integer references cover_midpoint_lookup(cover_code)
  --  cover_class_midpoint numeric references cover_midpoint_lookup(midpoint)  -- this should be auto populated based on code above
);

CREATE TABLE plot_module_herbaceous_info (
  fid text PRIMARY KEY,
  plot_no int4 references plot(plot_no),
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  depth integer references depth(depth),
  info text,
  cover_class_code integer references cover_midpoint_lookup(cover_code)
);

CREATE TABLE plot_module_woody_raw (
  fid text PRIMARY KEY,
  plot_no int4 references plot(plot_no),
  sub numeric,
  module_id int4 references module(module_id),
  species text references species(scientific_name),
  dbh_class text references dbh_class(dbh_class),
  dbh_class_index int4,
  count text
);

CREATE TABLE reduced_fsd2_dbh_index_basal_area (
  dbh_class_index int4,
  basal_area numeric
);

INSERT INTO reduced_fsd2_dbh_index_basal_area (dbh_class_index, basal_area) VALUES
  (0, 0.1963), (1, 0.1963), (2, 2.405), (3, 11.04), (4, 44.18), (5, 122.7), (6, 240.5),
  (7, 397.6), (8, 594), (9, 830), (10, 1104);

--includes calculated fields
CREATE TABLE biomass_raw (
  fid text PRIMARY KEY,
  plot_no int4 references plot(plot_no),
  date_time timestamptz,
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  sample_id int4,
  area_sampled numeric,
  weight_with_bag numeric,
  bag_weight numeric,
  biomass_collected text,
  actual_or_derived text references biomass_accuracy(biomass_accuracy) -- this lookup needs to be created
  --biomass_weight_grams numeric, --calculated; see column N in "Enter Biomass" tab of spreadsheet
  --grams_per_sq_meter numeric  --calculated; see column O in "Enter Biomass" tab of spreadsheet
);

CREATE TABLE rule (
  id int8 PRIMARY KEY,
  _user TEXT NOT NULL,
  _group TEXT NOT NULL,
  service TEXT NOT NULL,
  operation TEXT NOT NULL,
  entity TEXT NOT NULL,
  format TEXT NOT NULL,
  size INT8,
  UNIQUE (_user, _group, service, operation, entity, format)
);

INSERT INTO rule (id, _user, _group, service, operation, entity, format, size) VALUES
  (0, 'admin', '*', '*', '*','*', '*', -1);
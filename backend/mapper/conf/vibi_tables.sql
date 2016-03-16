DROP TABLE IF EXISTS class_code_Mod_NatureServe CASCADE;
DROP TABLE IF EXISTS hgm_subclass CASCADE;
DROP TABLE IF EXISTS biomass_raw CASCADE;
DROP TABLE IF EXISTS biomass_accuracy CASCADE;
DROP TABLE IF EXISTS code1 CASCADE;
DROP TABLE IF EXISTS code2 CASCADE;
DROP TABLE IF EXISTS code3 CASCADE;
DROP TABLE IF EXISTS code4 CASCADE;
DROP TABLE IF EXISTS code5 CASCADE;
DROP TABLE IF EXISTS corner CASCADE;
DROP TABLE IF EXISTS cover_midpoint_lookup CASCADE;
DROP TABLE IF EXISTS dbh_class CASCADE;
DROP TABLE IF EXISTS depth CASCADE;
DROP TABLE IF EXISTS disturbance_severity CASCADE;
DROP TABLE IF EXISTS disturbance_type CASCADE;
DROP TABLE IF EXISTS drainage CASCADE;
DROP TABLE IF EXISTS authority CASCADE;
DROP TABLE IF EXISTS family CASCADE;
DROP TABLE IF EXISTS groupp CASCADE;
DROP TABLE IF EXISTS herbaceous_relative_cover CASCADE;
DROP TABLE IF EXISTS fds1_species_misc_info CASCADE;
DROP TABLE IF EXISTS fds2_species_misc_info CASCADE;
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
DROP TABLE IF EXISTS plot_module_woody_raw CASCADE;
DROP TABLE IF EXISTS ind CASCADE;
DROP TABLE IF EXISTS form CASCADE;
DROP TABLE IF EXISTS habit CASCADE;
DROP TABLE IF EXISTS oh_status CASCADE;
DROP TABLE IF EXISTS shade CASCADE;
DROP TABLE IF EXISTS nativity CASCADE;
DROP TABLE IF EXISTS reduced_fds2_dbh_index_basal_area CASCADE;
DROP TABLE IF EXISTS rule CASCADE;

CREATE TABLE plant_comm_code (
  code text PRIMARY KEY,
  description text
); --may remove this table, not sure how it differs from other classification codes below


CREATE TABLE veg_class (
  veg_class text PRIMARY KEY
);

INSERT INTO veg_class VALUES ('emergent'), ('emergent-coastal'), ('forest'), ('shrub');

CREATE TABLE hgm_group (
  hgm_group text PRIMARY KEY
);

INSERT INTO hgm_group VALUES ('depression'), ('beaver impoundment'), ('human impoundment'), ('riverine headwater'), ('riverine mainstem'), ('riverine channel'), ('slope headwater'), ('slope mainstem'), ('slope isolated'), ('slope fringing'), ('fringe reservoir'), ('fringe lake'), ('open embayment'), ('closed embayment'), ('barrier-protected'), ('river mouth'), ('diked-managed'), ('diked-unmanaged'), ('diked-failed'), ('bog-strong'), ('bog-moderate'), ('bog-weak'), ('mitigation'), ('upland habitat');

CREATE TABLE hgm_class (
  hgm_class text PRIMARY KEY
);

INSERT INTO hgm_class VALUES ('depression'), ('impoundment'), ('riverine'), ('slope'), ('fringing'), ('coastal'), ('bog'), ('upland');

CREATE TABLE  hgm_subclass (
  hgm_subclass text PRIMARY KEY
);

INSERT INTO hgm_subclass VALUES ('surface water'), ('ground water'), ('beaver'), ('human'), ('headwater'), ('mainstem'), ('channel'), ('riverine');

CREATE TABLE landform_type (
  landform_type text  PRIMARY KEY
);

INSERT INTO landform_type VALUES ('active slope'), ('alluvial fan'), ('alluvial flat'), ('alluvial plain'), ('backswamp'), ('bar'), ('basin'), ('beach'), ('bluff'), ('braided channel or stream'), ('delta'), ('dome'), ('dune'), ('escarpment'), ('flat'), ('floodplain'), ('gorge'), ('hill'), ('hummock'), ('knob'), ('cliff'), ('crest'), ('levee'), ('mountain'), ('oxbow'), ('plain'), ('ravine'), ('ridge'), ('saddle'), ('shoulder'), ('sinkhole'), ('spit'), ('splay'), ('swale'), ('terrace'), ('valley'), ('other');

CREATE TABLE homogeneity (
  homogeneity text  PRIMARY KEY
);

INSERT INTO homogeneity VALUES ('homogeneous'), ('conspicuous trend across plot'), ('conspicuous inclusions'), ('irregular/pattern mosaic');

CREATE TABLE stand_size (
  stand_size text  PRIMARY KEY
);

INSERT INTO stand_size VALUES ('>1000'), ('>100'), ('10-100'), ('3-10'), ('1-3'), ('<1');

CREATE TABLE drainage (
  drainage text PRIMARY KEY
);

INSERT INTO drainage VALUES ('excessively drained'), ('somewhat excessively drained'), ('well drained'), ('moderately well drained'), ('somewhat poorly drained'), ('very poorly drained'), ('impermeable surface'); 

CREATE TABLE salinity (
  salinity text  PRIMARY KEY
);

INSERT INTO salinity VALUES ('freshwater'), ('upland');

CREATE TABLE hydrologic_regime (
  hydrologic_regime text PRIMARY KEY
);

INSERT INTO hydrologic_regime VALUES ('upland'), ('intermittent/seasonally saturated'), ('permanently/semipermanent'), ('occasionally flooded'), ('temporarily flooded'), ('intermittently flooded'), ('semipermanently flooded'), ('permanently flooded'), ('tidal/seiche flooded daily'), ('tidal/seiche flooded monthly'), ('tidal/seiche flooded irregular'), ('unknown'); 

CREATE TABLE disturbance_type (
  disturbance_type text PRIMARY KEY
);

INSERT INTO disturbance_type VALUES ('human'), ('natural'), ('fire'), ('clear-cut'), ('animal'), ('other'); 

CREATE TABLE disturbance_severity (
  disturbance_severity text PRIMARY KEY
);

INSERT INTO disturbance_severity VALUES ('low'), ('medium low'), ('medium'), ('medium high'), ('high'), ('very high'); 

CREATE TABLE module (
  module_id int4 PRIMARY KEY
);

INSERT INTO module VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);

CREATE TABLE corner (
  corner int4 PRIMARY KEY
);

INSERT INTO corner VALUES (1), (2), (3), (4);

CREATE TABLE depth (
  depth int4 PRIMARY KEY
);

INSERT INTO depth VALUES (1), (2), (3), (4);


--based on columns A-C in "Lookup Community" tab in spreadsheet
CREATE TABLE class_code_Mod_NatureServe (
  code text PRIMARY KEY,
  community_class text
);

INSERT INTO class_code_Mod_NatureServe (code, community_class) VALUES
('A01', 'Dry Oak Forest and Woodland'), ('A02', 'Dry-Mesic Oak Forest and Woodland'), ('B01', 'Hemlock Forest'), ('B02', 'Hemlock-Hardwood Forest'), ('C01', 'Beech Forest'), ('C02', 'Beech-Maple Forest'), ('C03', 'Sugar Maple Forest'), ('C04', 'Beech-Red Oak Forest'), ('D', 'Mixed Forest'), ('E', 'Mesophytic Forest'), ('F', 'Pine-Oak Rocky Woodland'), ('G01', 'Dry Acidic Glade and Barrens'), ('G02', 'Alkaline Glade and Woodland'), ('G03', 'Calcareous Glade and Barrens'), ('H01', 'Interior Deep Soil Oak Savanna'), ('H02', 'Oak Barrens'), ('I01', 'Dry-Mesic Prairie');  

CREATE TABLE cover_midpoint_lookup (
  cover_code int4 PRIMARY KEY,
  midpoint numeric
);

INSERT INTO cover_midpoint_lookup (cover_code, midpoint) VALUES
  (0, 0.00), (1, 0.0001), (2, 0.005), (3, 0.015), (4, 0.035), (5, 0.075), (6, 0.175),
  (7, 0.375), (8, 0.625), (9, 0.85), (10, 0.97);

CREATE TABLE dbh_class (
  dbh_class text PRIMARY KEY
);

INSERT INTO dbh_class VALUES ('0-<1cm'), ('1-<2.5cm'), ('2.5-<5cm'), ('5-<10cm'), ('10-<15cm'), ('15-<20cm'), ('20-<25cm'), ('25-<30cm'), ('30-<35cm'), ('35-<40cm'), ('shrub clump'), ('>40cm'); 

CREATE TABLE biomass_accuracy (
  biomass_accuracy text PRIMARY KEY
);

INSERT INTO biomass_accuracy VALUES ('actual'), ('derived');


CREATE TABLE ind (
  ind text PRIMARY KEY
);

INSERT INTO ind VALUES ('OBL'), ('FACW'), ('FAC'), ('FACU'), ('UPL');

CREATE TABLE form (
  form text PRIMARY KEY
);

INSERT INTO form VALUES ('forb'), ('tree'), ('grass'), ('shrub'), ('fern'), ('vine'), ('sm tree'), ('sedge'), ('ND'), ('bryo');

CREATE TABLE habit (
  habit text PRIMARY KEY
);

INSERT INTO habit VALUES ('AN'), ('PE'), ('BI'), ('W'), ('ND'), ('BR');


CREATE TABLE shade (
  shade text PRIMARY KEY
);

INSERT INTO shade VALUES ('advent'), ('bryo'), ('full'), ('ND'), ('partial'), ('shade'), ('tree');

CREATE TABLE oh_status (
  status text PRIMARY KEY
);

INSERT INTO oh_status VALUES ('adventive'), ('cryptogeni'), ('native');


 CREATE TABLE species
(
  veg_id integer NOT NULL,
  scientific_name text,
  acronym text,
  authority text,
  cofc integer,
  syn text,
  common_name text,
  family text,
  fn integer,
  wet text,
  form text references form(form),
  habit text references habit(habit),
  shade text references shade(shade),
  usda_id text,
  oh_tore text,
  type text,
  oh_status text references oh_status(status),
  emp text references ind(ind),
  mw text references ind(ind),
  ncne text references ind(ind),
  notes text,
  CONSTRAINT species_pkey PRIMARY KEY (scientific_name)
);
-- species table will be populated from most recent version of the Ohio EPA-maintained list
COPY species FROM 'H:/G_drive_Nat_Res_Reinier/Projects/FQAI_updates/OEPA_species_table_12_4_2015.csv' DELIMITER ',' CSV HEADER;

-- From "ENTER PLOT INFO" Tab. Ignore columns after column "BM"
CREATE TABLE plot (
  Plot_No text PRIMARY KEY,
  Project_Name text,
  Plot_Name text,
  Project_Label text,
  Monitoring_Event text,
  DateTimer timestamptz,
  Party text,
  Plot_Not_Sampled text,
  CommentPlot_not_sampled text,
  Sampling_Quality text,
  State text,
  County text,
  Quadrangle text,
  Local_Place_Name text,
  Landowner text,
  Xaxis_bearing_of_plot int4,
  Enter_GPS_location_in_plot text,
  latitude numeric,
  longitude numeric,
  plot_placement text,
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
  cowardin_classification text,
  cowardin_water_regime text,
  cowardin_special_modifier text,
  cowardin_special_modifier_other text,
  landscape_position text,
  inland_landform text,
  water_flow_path text,
  llww_modifiers text,
  llww_modifiers_other text,
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
  plot_no text references plot(plot_no),
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  depth integer references depth(depth),
  species text references species(scientific_name),
  cover_class_code integer references cover_midpoint_lookup(cover_code)
  --  cover_class_midpoint numeric references cover_midpoint_lookup(midpoint)  -- this should be auto populated based on code above
);

CREATE TABLE plot_module_herbaceous_info (
  fid text PRIMARY KEY,
  plot_no text references plot(plot_no),
  module_id int4 references module(module_id),
  corner int4 references corner(corner),
  depth integer references depth(depth),
  info text,
  cover_class_code integer references cover_midpoint_lookup(cover_code)
);

CREATE TABLE fds1_species_misc_info (
species text references species(scientific_name),
plot_no text references plot(plot_no),
module_id int4 references module(module_id),
voucher_no text,
comment text,
browse_intensity text,
percent_flowering text,
percent_fruiting text
);

CREATE TABLE plot_module_woody_raw (
  fid text PRIMARY KEY,
  plot_no text references plot(plot_no),
  sub numeric,
  module_id int4 references module(module_id),
  species text references species(scientific_name),
  dbh_class text references dbh_class(dbh_class),
  dbh_class_index int4,
  count text
);

CREATE TABLE fds2_species_misc_info (
species text references species(scientific_name),
plot_no text references plot(plot_no),
module_id int4 references module(module_id),
voucher_no text,
comment text,
browse_intensity text,
percent_flowering text,
percent_fruiting text
);

CREATE TABLE reduced_fds2_dbh_index_basal_area (
  dbh_class_index int4,
  basal_area numeric
);

INSERT INTO reduced_fds2_dbh_index_basal_area (dbh_class_index, basal_area) VALUES
  (0, 0.1963), (1, 0.1963), (2, 2.405), (3, 11.04), (4, 44.18), (5, 122.7), (6, 240.5),
  (7, 397.6), (8, 594), (9, 830), (10, 1104);

--includes calculated fields
CREATE TABLE biomass_raw (
  fid text PRIMARY KEY,
  plot_no text references plot(plot_no),
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

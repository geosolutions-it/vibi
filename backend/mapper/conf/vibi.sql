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

DROP MATERIALIZED VIEW IF EXISTS herbaceous_tot_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_site_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_relative_cover CASCADE;
DROP MATERIALIZED VIEW IF EXISTS plot_module_woody_dbh CASCADE;
DROP MATERIALIZED VIEW IF EXISTS plot_module_woody_dbh_cm CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_counts CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_counts_cm2 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_class_freq CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_tot_steams CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_tot_steams_all_spp CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_basal_cm2 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_basal_cm2_ha CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_basal_cm2_ha_tot CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_basal_cm2_ha_all_spp CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS woody_importance_value CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_den CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_rel_den CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_rel_den_calculations CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_sums_counts_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_avg_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fsd2_calculations_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_reduced_fsd1 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_reduced_fsd2_canopy CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_reduced_fsd2_steams CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_info_tot_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_info_tot_count CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_info_relative_cover CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_plot_module_herbaceous_info CASCADE;
DROP MATERIALIZED VIEW IF EXISTS metric_calculations CASCADE;

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
    veg_class text,
    veg_group text
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

CREATE TABLE authority
(
  authority text PRIMARY KEY
)
;

  CREATE TABLE family
(
  family text PRIMARY KEY
)
;

  CREATE TABLE ind
(
  ind text PRIMARY KEY
)
;

  CREATE TABLE form
(
  form text PRIMARY KEY
)
;

  CREATE TABLE habit
(
  habit text PRIMARY KEY
)
;

  CREATE TABLE groupp
(
  groupp text PRIMARY KEY
)
;

  CREATE TABLE shade
(
  shade text PRIMARY KEY
)
;

  CREATE TABLE nativity
(
  nativity text PRIMARY KEY
)
;

  CREATE TABLE CODE1
(
  CODE1 text PRIMARY KEY
)
;

  CREATE TABLE CODE2
(
  CODE2 text PRIMARY KEY
)
;

  CREATE TABLE CODE3
(
  CODE3 text PRIMARY KEY
)
;

  CREATE TABLE CODE4
(
  CODE4 text PRIMARY KEY
)
;

  CREATE TABLE CODE5
(
  CODE5 text PRIMARY KEY
)
;

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

CREATE MATERIALIZED VIEW herbaceous_tot_cov AS
    SELECT a.plot_no, a.species, sum(b.midpoint) as tot_cov
    FROM plot_module_herbaceous a
    INNER JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
    WHERE a.cover_class_code NOTNULL
    GROUP BY a.plot_no, a.species;

CREATE MATERIALIZED VIEW herbaceous_site_cov AS
    SELECT plot_no, sum(tot_cov) as site_cov
    FROM herbaceous_tot_cov
    GROUP BY plot_no;

CREATE MATERIALIZED VIEW herbaceous_relative_cover AS
    SELECT a.plot_no, a.species, (a.tot_cov / b.site_cov) as relative_cover
    FROM herbaceous_tot_cov a
    INNER JOIN herbaceous_site_cov b ON a.plot_no = b.plot_no;

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

CREATE MATERIALIZED VIEW plot_module_woody_dbh AS
	SELECT plot_no, module_id, species, dbh_class_index, dbh_class, count::numeric / sub AS count
	FROM plot_module_woody_raw
	WHERE dbh_class_index <= 10;

CREATE MATERIALIZED VIEW plot_module_woody_dbh_cm AS
	SELECT plot_no, module_id, species, dbh_class_index, dbh_class, (count::numeric / 2) ^ 2 * pi() AS dbh_cm
	FROM plot_module_woody_raw
	WHERE dbh_class_index > 10;

CREATE TABLE reduced_fsd2_dbh_index_basal_area (
    dbh_class_index int4,
    basal_area numeric
);

INSERT INTO reduced_fsd2_dbh_index_basal_area (dbh_class_index, basal_area) VALUES
(0, 0.1963), (1, 0.1963), (2, 2.405), (3, 11.04), (4, 44.18), (5, 122.7), (6, 240.5),
(7, 397.6), (8, 594), (9, 830), (10, 1104);

CREATE MATERIALIZED VIEW reduced_fsd2_counts AS
	SELECT plot_no, species, dbh_class_index, sum(count::numeric) as counts
	FROM plot_module_woody_dbh
	GROUP BY plot_no, species, dbh_class_index
	UNION
	SELECT plot_no, species, -1 as dbh_class_index, count(*) as counts
	FROM plot_module_woody_dbh_cm
	GROUP BY plot_no, species;

CREATE MATERIALIZED VIEW reduced_fsd2_counts_cm2 AS
	SELECT plot_no, species, dbh_class_index, sum(dbh_cm::numeric) as counts
	FROM plot_module_woody_dbh_cm
	GROUP BY plot_no, species, dbh_class_index;

CREATE MATERIALIZED VIEW reduced_fsd2_class_freq AS
	SELECT plot_no, species, count(*) as class_freq, count(*) / 12.0 as rel_class_freq
	FROM reduced_fsd2_counts
	GROUP BY plot_no, species;

CREATE MATERIALIZED VIEW reduced_fsd2_tot_steams AS
	SELECT b.plot_no, a.species, sum(a.counts) as tot_steams, (sum(a.counts) / b.plot_size_for_cover_data_area_ha) as tot_steams_ha
	FROM reduced_fsd2_counts a
	INNER JOIN plot AS b ON a.plot_no = b.plot_no
	GROUP BY b.plot_no, a.species;

CREATE MATERIALIZED VIEW reduced_fsd2_tot_steams_all_spp AS
	SELECT plot_no, sum(tot_steams_ha) as tot_steams_all_spp
	FROM reduced_fsd2_tot_steams
	GROUP BY plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_basal_cm2 AS
	SELECT a.plot_no, a.species, a.dbh_class_index, (a.counts * b.basal_area) as basal_cm2
	FROM reduced_fsd2_counts a
	INNER JOIN reduced_fsd2_dbh_index_basal_area b
	ON a.dbh_class_index = b.dbh_class_index
	WHERE a.dbh_class_index >= 0
	UNION
	SELECT * FROM reduced_fsd2_counts_cm2 as basal_cm2;

CREATE MATERIALIZED VIEW reduced_fsd2_basal_cm2_ha AS
	SELECT a.plot_no, a.species, a.dbh_class_index, (a.counts * b.basal_area) / c.plot_size_for_cover_data_area_ha AS basal_cm2_ha
	FROM reduced_fsd2_counts a
	INNER JOIN reduced_fsd2_dbh_index_basal_area b
	ON a.dbh_class_index = b.dbh_class_index
	INNER JOIN plot AS c
	ON a.plot_no = c.plot_no
	WHERE a.dbh_class_index >= 0
	UNION
	SELECT a.plot_no, a.species, a.dbh_class_index, a.counts / b.plot_size_for_cover_data_area_ha AS basal_cm2_ha
	FROM reduced_fsd2_counts_cm2 a
	INNER JOIN plot b
	ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_basal_cm2_ha_tot AS
	SELECT plot_no, species, sum(basal_cm2_ha) AS tot_cm2_ha
	FROM reduced_fsd2_basal_cm2_ha
	GROUP BY plot_no, species;

CREATE MATERIALIZED VIEW reduced_fsd2_basal_cm2_ha_all_spp AS
	SELECT plot_no, sum(basal_cm2_ha) AS  tot_cm2_all_spp
	FROM reduced_fsd2_basal_cm2_ha
	GROUP BY plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_iv AS
	SELECT a.plot_no, a.species, (d.tot_cm2_ha / e.tot_cm2_all_spp + b.tot_steams_ha / c.tot_steams_all_spp + a.rel_class_freq) / 3 AS iv
	FROM reduced_fsd2_class_freq a
	INNER JOIN reduced_fsd2_tot_steams b
	ON a.plot_no = b.plot_no AND a.species = b.species
	INNER JOIN reduced_fsd2_tot_steams_all_spp c
	ON a.plot_no = c.plot_no
	INNER JOIN reduced_fsd2_basal_cm2_ha_tot d
	ON a.plot_no = d.plot_no AND a.species = d.species
	INNER JOIN reduced_fsd2_basal_cm2_ha_all_spp e
	ON a.plot_no = e.plot_no;

CREATE MATERIALIZED VIEW  woody_importance_value AS
	SELECT a.plot_no, a.species,
	       CASE WHEN b.code5 = 'partial' THEN a.iv ELSE null END AS subcanopy_iv_partial,
	       CASE WHEN b.code5 = 'shade' THEN a.iv ELSE null END AS subcanopy_iv_shade,
	       CASE WHEN b.form = 'tree' THEN a.iv ELSE null END AS canopy_IV
	FROM reduced_fsd2_iv a
	INNER JOIN species b ON a.species = b.scientific_name;

--includes calculated fields
CREATE TABLE biomass (
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
);

CREATE MATERIALIZED VIEW reduced_fsd2_den AS
	SELECT a.plot_no, a.species, a.dbh_class_index, a.counts / b.plot_size_for_cover_data_area_ha AS counts_den
	FROM reduced_fsd2_counts a
	INNER JOIN plot b
	ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_rel_den AS
	SELECT a.plot_no, a.species, a.dbh_class_index,
	CASE WHEN a.counts_den > 0 THEN a.counts_den / b.tot_steams_all_spp ELSE 0 END AS counts_rel_den
	FROM reduced_fsd2_den a
	INNER JOIN reduced_fsd2_tot_steams_all_spp b
	ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_rel_den_calculations AS
	SELECT plot_no, sum(CASE WHEN dbh_class_index = 5 or dbh_class_index = 6 or dbh_class_index = 7 THEN counts_rel_den ELSE 0 END) AS small_tree
	FROM reduced_fsd2_rel_den
	GROUP BY plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_sums_counts_iv AS
	SELECT plot_no,
	sum(subcanopy_iv_partial) AS sum_subcanopy_iv_partial,
	sum(subcanopy_iv_shade) AS sum_subcanopy_iv_shade,
	sum(canopy_IV) AS sum_canopy_IV,
	count(subcanopy_iv_partial) AS count_subcanopy_iv_partial,
	count(subcanopy_iv_shade) AS count_subcanopy_iv_shade,
	count(canopy_IV) AS count_canopy_IV
	FROM woody_importance_value
	GROUP BY plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_avg_iv AS
	SELECT plot_no,
	CASE WHEN count_subcanopy_iv_partial > 0 THEN sum_subcanopy_iv_partial / count_subcanopy_iv_partial ELSE 0 END AS avg_subcanopy_iv_partial,
	CASE WHEN count_subcanopy_iv_shade > 0 THEN sum_subcanopy_iv_shade / count_subcanopy_iv_shade ELSE 0 END AS avg_subcanopy_iv_shade,
	CASE WHEN count_canopy_IV > 0 THEN sum_canopy_IV / count_canopy_IV ELSE 0 END AS avg_canopy_IV
	FROM reduced_fsd2_sums_counts_iv;

CREATE MATERIALIZED VIEW reduced_fsd2_calculations_iv AS
	SELECT a.plot_no,
	sum_subcanopy_iv_partial,
	sum_subcanopy_iv_shade,
	sum_canopy_IV,
	count_subcanopy_iv_partial,
	count_subcanopy_iv_shade,
	count_canopy_IV,
	avg_subcanopy_iv_partial,
	avg_subcanopy_iv_shade,
	avg_canopy_IV
	FROM reduced_fsd2_sums_counts_iv a
	INNER JOIN reduced_fsd2_avg_iv b
	ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW calculations_reduced_fsd1 AS
	SELECT a.plot_no,
	sum(CASE WHEN c.code1 = 'carex' THEN 1 ELSE 0 END) AS carex,
	sum(CASE WHEN c.code2 = 'cyper' THEN 1 ELSE 0 END) AS cyperaceae,
	sum(CASE WHEN c.code1 = 'natDI' THEN 1 ELSE 0 END) AS dicot,
	sum(CASE WHEN c.shade = 'shade' OR c.shade = 'partial' THEN 1 ELSE 0 END) AS shade,
	sum(CASE WHEN c.code2 = 'natwtldSH' THEN 1 ELSE 0 END) AS shrub,
	sum(CASE WHEN c.hydro = 'hydrophyte' THEN 1 ELSE 0 END) AS hydrophyte,
	sum(CASE WHEN c.groupp = 'SVP' THEN 1 ELSE 0 END) AS svp,
	CASE WHEN (sum(CASE WHEN c.habit = 'PE' THEN b.relative_cover ELSE 0 END)) > 0
	THEN (sum(CASE WHEN c.habit = 'AN' THEN 1.0 ELSE 0 END)) / (sum(CASE WHEN c.habit = 'PE' THEN 1.0 ELSE 0 END))
	ELSE 1 END AS ap_ratio,
	sum(CASE WHEN c.cofc >= 0 THEN c.cofc ELSE 0 END) / sqrt(sum(CASE WHEN c.cofc >= 0 THEN 1 ELSE 0 END)) AS fqai,
	sum(CASE WHEN c.groupp = 'bryo' THEN b.relative_cover ELSE 0 END) AS bryophyte,
	sum(CASE WHEN c.code3 = 'natshHYDRO' THEN b.relative_cover ELSE 0 END) AS per_hydrophyte,
	sum(CASE WHEN c.tolerance = 'sensitive' THEN b.relative_cover ELSE 0 END) AS sensitive,
	sum(CASE WHEN c.tolerance = 'tolerant' THEN b.relative_cover ELSE 0 END) AS tolerant,
	sum(CASE WHEN c.code1 = 'invgram' THEN b.relative_cover ELSE 0 END) AS invasive_graminoids,
	sum(CASE WHEN c.habit = 'AN' THEN b.relative_cover ELSE 0 END) AS habit_an_sum,
	sum(CASE WHEN c.code3 = 'BBS' THEN b.relative_cover ELSE 0 END) AS button_bush,
	sum(CASE WHEN c.code4 = 'PEnatHYD' THEN b.relative_cover ELSE 0 END) AS perennial_native_hydrophytes,
	sum(CASE WHEN c.nativity = 'adventive' THEN b.relative_cover ELSE 0 END) AS adventives
	FROM plot a
	INNER JOIN herbaceous_relative_cover b
	ON a.plot_no = b.plot_no
	INNER JOIN species c
	ON b.species = c.scientific_name
	GROUP BY a.plot_no, a.plot_name;

CREATE MATERIALIZED VIEW calculations_reduced_fsd2_canopy AS
	SELECT a.plot_no,
	b.small_tree,
	c.avg_subcanopy_iv_partial + c.avg_subcanopy_iv_shade AS subcanopy_iv,
	c.avg_canopy_IV AS canopy_iv
	FROM plot a
	INNER JOIN reduced_fsd2_rel_den_calculations b
	ON a.plot_no = b.plot_no
	INNER JOIN reduced_fsd2_calculations_iv c
	ON a.plot_no = c.plot_no;

CREATE MATERIALIZED VIEW calculations_reduced_fsd2_steams AS
	SELECT a.plot_no,
	sum(CASE WHEN c.code2 = 'natwtldTR' THEN b.tot_steams_ha ELSE 0 END) AS steams_wetland_trees,
	sum(CASE WHEN c.code2 = 'natwtldSH' THEN b.tot_steams_ha ELSE 0 END) AS steams_wetland_shrubs
	FROM plot a
	INNER JOIN reduced_fsd2_tot_steams b
	ON a.plot_no = b.plot_no
	INNER JOIN species c
	ON b.species = c.scientific_name
	GROUP BY a.plot_no;

CREATE MATERIALIZED VIEW herbaceous_info_tot_cov AS
    SELECT a.plot_no, a.info, sum(b.midpoint) as tot_cov
    FROM plot_module_herbaceous_info a
    INNER JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
    WHERE a.cover_class_code NOTNULL
    GROUP BY a.plot_no, a.info;

CREATE MATERIALIZED VIEW herbaceous_info_tot_count AS
    SELECT a.plot_no, a.info, sum(CASE WHEN b.midpoint > 0 THEN 1 ELSE 0 END) as tot_count
    FROM plot_module_herbaceous_info a
    INNER JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
    WHERE a.cover_class_code NOTNULL
    GROUP BY a.plot_no, a.info;

CREATE MATERIALIZED VIEW herbaceous_info_relative_cover AS
    SELECT a.plot_no, a.info, CASE WHEN a.tot_cov > 0 THEN a.tot_cov / b.tot_count ELSE 0 END AS relative_cover
    FROM herbaceous_info_tot_cov a
    INNER JOIN herbaceous_info_tot_count b ON a.plot_no = b.plot_no AND a.info = b.info;

CREATE MATERIALIZED VIEW calculations_plot_module_herbaceous_info AS
	SELECT plot_no,
	sum(CASE WHEN info = '%bare ground' OR info = '%litter cover' THEN relative_cover ELSE 0 END) AS unvegetated_partial,
	sum(CASE WHEN info = '%open water' THEN relative_cover ELSE 0 END) AS open_water,
	sum(CASE WHEN info = '%unvegetated open water' THEN relative_cover ELSE 0 END) AS unvegetated_open_water,
	sum(CASE WHEN info = '%bare ground' THEN relative_cover ELSE 0 END) AS bare_ground
	FROM herbaceous_info_relative_cover
	GROUP BY plot_no;

CREATE MATERIALIZED VIEW metric_calculations AS
	SELECT a.plot_no,
	carex AS carex_metric_value,
	cyperaceae AS cyperaceae_metric_value,
	dicot AS dicot_metric_value,
	shade AS shade_metric_value,
	shrub AS shrub_metric_value,
	hydrophyte AS hydrophyte_metric_value,
	svp AS svp_metric_value,
	ap_ratio AS ap_ratio_metric_value,
	fqai AS fqai_metric_value,
	bryophyte AS bryophyte_metric_value,
	per_hydrophyte AS per_hydrophyte_metric_value,
	sensitive AS sensitive_metric_value,
	tolerant AS tolerant_metric_value,
	invasive_graminoids AS invasive_graminoids_metric_value,
	small_tree AS small_tree_metric_value,
	subcanopy_iv,
	canopy_iv,
	steams_wetland_trees AS stems_ha_wetland_trees,
	steams_wetland_shrubs AS stems_ha_wetland_shrubs,
	unvegetated_partial + habit_an_sum AS per_unvegetated,
	button_bush AS per_button_bush,
	perennial_native_hydrophytes AS per_perennial_native_hydrophytes,
	adventives AS per_adventives,
	open_water AS per_open_water,
	unvegetated_open_water AS per_unvegetated_open_water,
	bare_ground AS per_bare_ground
	FROM calculations_reduced_fsd1 a
	INNER JOIN calculations_reduced_fsd2_canopy b
	ON a.plot_no = b.plot_no
	INNER JOIN calculations_reduced_fsd2_steams c
	ON a.plot_no = c.plot_no
	INNER JOIN calculations_plot_module_herbaceous_info d
	ON a.plot_no = d.plot_no;

-- one big table (or materialize view) that includes all metric calculations for each plot
-- if table, needs tied to trigger
-- if view, needs to be performant

CREATE TABLE metric_calculations_orig (
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
  );
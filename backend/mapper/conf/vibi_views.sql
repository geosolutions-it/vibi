CREATE EXTENSION IF NOT EXISTS tablefunc;

DROP MATERIALIZED VIEW IF EXISTS herbaceous_tot_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_site_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_relative_cover CASCADE;
DROP MATERIALIZED VIEW IF EXISTS plot_module_woody_dbh CASCADE;
DROP MATERIALIZED VIEW IF EXISTS plot_module_woody_dbh_cm CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_counts CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_counts_cm2 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_class_freq CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_tot_stems CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_tot_stems_all_spp CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_basal_cm2 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_basal_cm2_ha CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_basal_cm2_ha_tot CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_basal_cm2_ha_all_spp CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS woody_importance_value CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_den CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_rel_den CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_rel_den_calculations CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_sums_counts_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_avg_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS reduced_fds2_calculations_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_reduced_fds1 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_reduced_fds2_canopy CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_reduced_fds2_stems CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_info_tot_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_info_tot_count CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herbaceous_info_relative_cover CASCADE;
DROP MATERIALIZED VIEW IF EXISTS calculations_plot_module_herbaceous_info CASCADE;
DROP MATERIALIZED VIEW IF EXISTS biomass CASCADE;
DROP MATERIALIZED VIEW IF EXISTS biomass_info CASCADE;
DROP MATERIALIZED VIEW IF EXISTS biomass_count CASCADE;
DROP MATERIALIZED VIEW IF EXISTS biomass_tot CASCADE;
DROP MATERIALIZED VIEW IF EXISTS biomass_calculations CASCADE;
DROP MATERIALIZED VIEW IF EXISTS vibi_values CASCADE;
DROP MATERIALIZED VIEW IF EXISTS vibi_e_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS vibi_ecst_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS vibi_sh_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS vibi_f_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS metric_calculations CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_herbaceous_avg_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_herbaceous_site_cov CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_herbaceous_relative_cover CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_calculations_reduced_fds1 CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_reduced_fds2_freq CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_reduced_fds2_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_woody_importance_value CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_reduced_fds2_sums_counts_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_reduced_fds2_avg_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_reduced_fds2_calculations_iv CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_calculations_reduced_fds2_canopy CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_vibi_values CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_vibi_e_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_vibi_ecst_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_vibi_sh_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_vibi_f_index CASCADE;
DROP MATERIALIZED VIEW IF EXISTS alt_metric_calculations CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herb_rel_cover_acronym_joined CASCADE;
DROP MATERIALIZED VIEW IF EXISTS woody_iv_acronym_joined CASCADE;
DROP MATERIALIZED VIEW IF EXISTS herb_plot_x_species_matrix CASCADE;
DROP MATERIALIZED VIEW IF EXISTS woody_plot_x_species_matrix CASCADE;

CREATE MATERIALIZED VIEW herbaceous_tot_cov AS
  SELECT a.plot_id, a.species, sum(b.midpoint) as tot_cov
  FROM plot_module_herbaceous a
    LEFT OUTER JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  GROUP BY a.plot_id, a.group_id, a.species;
  
CREATE MATERIALIZED VIEW alt_herbaceous_avg_cov AS
  SELECT a.plot_id, a.species, avg(b.midpoint) as avg_cov
  FROM plot_module_herbaceous a
    LEFT JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  WHERE a.cover_class_code NOTNULL
  GROUP BY a.plot_id, a.group_id, a.species;

CREATE MATERIALIZED VIEW herbaceous_site_cov AS
  SELECT plot_id, sum(tot_cov) as site_cov
  FROM herbaceous_tot_cov
  GROUP BY plot_id;
  
CREATE MATERIALIZED VIEW alt_herbaceous_site_cov AS
  SELECT plot_id, sum(avg_cov) as site_cov
  FROM alt_herbaceous_avg_cov
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW herbaceous_relative_cover AS
  SELECT row_number() OVER () AS view_id, a.plot_id, a.species,
    CASE WHEN COALESCE(b.site_cov, 0.0) = 0 THEN 0 ELSE (a.tot_cov / b.site_cov) END as relative_cover
  FROM herbaceous_tot_cov a
    LEFT JOIN herbaceous_site_cov b ON a.plot_id = b.plot_id;
	
CREATE MATERIALIZED VIEW alt_herbaceous_relative_cover AS
  SELECT row_number() OVER () AS view_id, a.plot_id, a.species,
    CASE WHEN COALESCE(b.site_cov, 0.0) = 0 THEN 0 ELSE (a.avg_cov / b.site_cov) END as relative_cover
  FROM alt_herbaceous_avg_cov a
    LEFT JOIN alt_herbaceous_site_cov b ON a.plot_id = b.plot_id;

CREATE MATERIALIZED VIEW plot_module_woody_dbh AS
  SELECT row_number() OVER () AS view_id, plot_id, module_id, group_id, species, dbh_class_index, dbh_class, count::numeric / sub AS count
  FROM plot_module_woody_raw
  WHERE dbh_class_index <= 10;

CREATE MATERIALIZED VIEW plot_module_woody_dbh_cm AS
  SELECT row_number() OVER () AS view_id, plot_id, module_id, group_id, species, dbh_class_index, dbh_class, (count::numeric / 2) ^ 2 * pi() AS dbh_cm
  FROM plot_module_woody_raw
  WHERE dbh_class_index > 10;

CREATE MATERIALIZED VIEW reduced_fds2_counts AS
  SELECT plot_id, group_id, species, dbh_class_index, sum(count::numeric) as counts
  FROM plot_module_woody_dbh
  GROUP BY plot_id, group_id, species, dbh_class_index
  UNION
  SELECT plot_id, group_id, species, -1 as dbh_class_index, count(*) as counts
  FROM plot_module_woody_dbh_cm
  GROUP BY plot_id, group_id, species;

CREATE MATERIALIZED VIEW reduced_fds2_counts_cm2 AS
  SELECT plot_id, group_id, species, dbh_class_index, sum(dbh_cm::numeric) as counts
  FROM plot_module_woody_dbh_cm
  GROUP BY plot_id, group_id, species, dbh_class_index;

CREATE MATERIALIZED VIEW reduced_fds2_class_freq AS
  SELECT plot_id, species, count(*) as class_freq, count(*) / 12.0 as rel_class_freq
  FROM reduced_fds2_counts
  GROUP BY plot_id, group_id, species;
  
CREATE MATERIALIZED VIEW alt_reduced_fds2_freq AS 
  SELECT a.plot_id, a.species, count(DISTINCT a.module_id::numeric) / b.total_modules::numeric AS alt_rel_freq
  FROM plot_module_woody_raw a LEFT JOIN plot b ON a.plot_id = b.plot_id
  GROUP BY a.group_id, species, a.plot_id, b.total_modules;

CREATE MATERIALIZED VIEW reduced_fds2_tot_stems AS
  SELECT b.plot_id, a.species, sum(a.counts) as tot_stems, (sum(a.counts) / b.plot_size_for_cover_data_area_ha) as tot_stems_ha
  FROM reduced_fds2_counts a
    LEFT JOIN plot AS b ON a.plot_id = b.plot_id
  GROUP BY b.plot_id, a.group_id, a.species;

CREATE MATERIALIZED VIEW reduced_fds2_tot_stems_all_spp AS
  SELECT plot_id, sum(tot_stems_ha) as tot_stems_all_spp
  FROM reduced_fds2_tot_stems
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_basal_cm2 AS
  SELECT a.plot_id, a.group_id, a.species, a.dbh_class_index, (a.counts * b.basal_area) as basal_cm2
  FROM reduced_fds2_counts a
    LEFT JOIN reduced_fds2_dbh_index_basal_area b
      ON a.dbh_class_index = b.dbh_class_index
  WHERE a.dbh_class_index >= 0
  UNION
  SELECT * FROM reduced_fds2_counts_cm2 as basal_cm2;

CREATE MATERIALIZED VIEW reduced_fds2_basal_cm2_ha AS
  SELECT a.plot_id, a.group_id, a.species, a.dbh_class_index, (a.counts * b.basal_area) / c.plot_size_for_cover_data_area_ha AS basal_cm2_ha
  FROM reduced_fds2_counts a
    LEFT JOIN reduced_fds2_dbh_index_basal_area b
      ON a.dbh_class_index = b.dbh_class_index
    LEFT JOIN plot AS c
      ON a.plot_id = c.plot_id
  WHERE a.dbh_class_index >= 0
  UNION
  SELECT a.plot_id, a.group_id, a.species, a.dbh_class_index, a.counts / b.plot_size_for_cover_data_area_ha AS basal_cm2_ha
  FROM reduced_fds2_counts_cm2 a
    LEFT JOIN plot b
      ON a.plot_id = b.plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_basal_cm2_ha_tot AS
  SELECT plot_id, species, sum(basal_cm2_ha) AS tot_cm2_ha
  FROM reduced_fds2_basal_cm2_ha
  GROUP BY plot_id, group_id, species;

CREATE MATERIALIZED VIEW reduced_fds2_basal_cm2_ha_all_spp AS
  SELECT plot_id, sum(basal_cm2_ha) AS  tot_cm2_all_spp
  FROM reduced_fds2_basal_cm2_ha
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_iv AS
  SELECT a.plot_id, a.species, (d.tot_cm2_ha / e.tot_cm2_all_spp + b.tot_stems_ha / c.tot_stems_all_spp + a.rel_class_freq) / 3 AS iv
  FROM reduced_fds2_class_freq a
    LEFT JOIN reduced_fds2_tot_stems b
      ON a.plot_id = b.plot_id AND a.species = b.species
    LEFT JOIN reduced_fds2_tot_stems_all_spp c
      ON a.plot_id = c.plot_id
    LEFT JOIN reduced_fds2_basal_cm2_ha_tot d
      ON a.plot_id = d.plot_id AND a.species = d.species
    LEFT JOIN reduced_fds2_basal_cm2_ha_all_spp e
      ON a.plot_id = e.plot_id;
	  
CREATE MATERIALIZED VIEW alt_reduced_fds2_iv AS
  SELECT a.plot_id, a.species, (d.tot_cm2_ha / e.tot_cm2_all_spp + b.tot_stems_ha / c.tot_stems_all_spp + a.alt_rel_freq) / 3 AS iv
  FROM alt_reduced_fds2_freq a
    LEFT JOIN reduced_fds2_tot_stems b
      ON a.plot_id = b.plot_id AND a.species = b.species
    LEFT JOIN reduced_fds2_tot_stems_all_spp c
      ON a.plot_id = c.plot_id
    LEFT JOIN reduced_fds2_basal_cm2_ha_tot d
      ON a.plot_id = d.plot_id AND a.species = d.species
    LEFT JOIN reduced_fds2_basal_cm2_ha_all_spp e
      ON a.plot_id = e.plot_id;

CREATE MATERIALIZED VIEW  woody_importance_value AS
  SELECT row_number() OVER () AS view_id, a.plot_id, a.species,
    CASE WHEN b.shade = 'partial' THEN a.iv ELSE null END AS subcanopy_iv_partial,
    CASE WHEN b.shade = 'shade' THEN a.iv ELSE null END AS subcanopy_iv_shade,
    CASE WHEN b.form = 'tree' THEN a.iv ELSE null END AS canopy_IV
  FROM reduced_fds2_iv a
    LEFT JOIN species b ON a.species = b.scientific_name;
	
CREATE MATERIALIZED VIEW  alt_woody_importance_value AS
  SELECT row_number() OVER () AS view_id, a.plot_id, a.species,
    CASE WHEN b.shade = 'partial' THEN a.iv ELSE null END AS subcanopy_iv_partial,
    CASE WHEN b.shade = 'shade' THEN a.iv ELSE null END AS subcanopy_iv_shade,
    CASE WHEN b.form = 'tree' THEN a.iv ELSE null END AS canopy_IV
  FROM alt_reduced_fds2_iv a
    LEFT JOIN species b ON a.species = b.scientific_name;

CREATE MATERIALIZED VIEW biomass AS
  SELECT row_number() OVER () AS view_id, plot_id, date_time, module_id, corner, sample_id, area_sampled,
    weight_with_bag, bag_weight, biomass_collected, actual_or_derived,
    CASE WHEN COALESCE(weight_with_bag, 0.0) > 2
      THEN weight_with_bag - bag_weight ELSE COALESCE(weight_with_bag, 0.0)
    END AS biomass_weight_grams,
    CASE WHEN COALESCE(sample_id, 0.0) > 0.0 AND COALESCE(area_sampled, 0.0) > 0.0
      THEN (CASE WHEN COALESCE(weight_with_bag, 0.0) > 2
        THEN weight_with_bag - bag_weight ELSE COALESCE(weight_with_bag, 0.0) END) / area_sampled
    END AS grams_per_sq_meter
  FROM biomass_raw;

CREATE MATERIALIZED VIEW biomass_info AS
  SELECT plot_id, date_time, biomass_collected
  FROM biomass
  GROUP BY plot_id, date_time, biomass_collected;

CREATE MATERIALIZED VIEW biomass_count AS
  SELECT plot_id,
    sum(CASE WHEN COALESCE(sample_id, 0) > 0 THEN 1 ELSE 0 END) AS count
  FROM biomass
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW biomass_tot AS
  SELECT plot_id,
    sum(COALESCE(grams_per_sq_meter, 0)) AS tot
  FROM biomass
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW biomass_calculations AS
  SELECT a.plot_id,
    CASE WHEN COALESCE(b.count, 0) > 0
      THEN c.tot / b.count ELSE 0 END AS biomass
  FROM plot a
    LEFT JOIN biomass_count b
      ON a.plot_id = b.plot_id
    LEFT JOIN biomass_tot c
      ON a.plot_id = c.plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_den AS
  SELECT a.plot_id, a.species, a.dbh_class_index, a.counts / b.plot_size_for_cover_data_area_ha AS counts_den
  FROM reduced_fds2_counts a
    LEFT JOIN plot b
      ON a.plot_id = b.plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_rel_den AS
  SELECT a.plot_id, a.species, a.dbh_class_index,
    CASE WHEN a.counts_den > 0 THEN a.counts_den / b.tot_stems_all_spp ELSE 0 END AS counts_rel_den
  FROM reduced_fds2_den a
    LEFT JOIN reduced_fds2_tot_stems_all_spp b
      ON a.plot_id = b.plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_rel_den_calculations AS
  SELECT plot_id, sum(CASE WHEN dbh_class_index = 5 or dbh_class_index = 6 or dbh_class_index = 7 THEN counts_rel_den ELSE 0 END) AS small_tree
  FROM reduced_fds2_rel_den
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_sums_counts_iv AS
  SELECT plot_id,
    sum(subcanopy_iv_partial) AS sum_subcanopy_iv_partial,
    sum(subcanopy_iv_shade) AS sum_subcanopy_iv_shade,
    sum(canopy_IV) AS sum_canopy_IV,
    count(subcanopy_iv_partial) AS count_subcanopy_iv_partial,
    count(subcanopy_iv_shade) AS count_subcanopy_iv_shade,
    count(canopy_IV) AS count_canopy_IV
  FROM woody_importance_value
  GROUP BY plot_id;
  
CREATE MATERIALIZED VIEW alt_reduced_fds2_sums_counts_iv AS
  SELECT plot_id,
    sum(subcanopy_iv_partial) AS sum_subcanopy_iv_partial,
    sum(subcanopy_iv_shade) AS sum_subcanopy_iv_shade,
    sum(canopy_IV) AS sum_canopy_IV,
    count(subcanopy_iv_partial) AS count_subcanopy_iv_partial,
    count(subcanopy_iv_shade) AS count_subcanopy_iv_shade,
    count(canopy_IV) AS count_canopy_IV
  FROM alt_woody_importance_value
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW reduced_fds2_avg_iv AS
  SELECT plot_id,
    CASE WHEN count_subcanopy_iv_partial > 0 THEN sum_subcanopy_iv_partial / count_subcanopy_iv_partial ELSE 0 END AS avg_subcanopy_iv_partial,
    CASE WHEN count_subcanopy_iv_shade > 0 THEN sum_subcanopy_iv_shade / count_subcanopy_iv_shade ELSE 0 END AS avg_subcanopy_iv_shade,
    CASE WHEN count_canopy_IV > 0 THEN sum_canopy_IV / count_canopy_IV ELSE 0 END AS avg_canopy_IV
  FROM reduced_fds2_sums_counts_iv;
  
CREATE MATERIALIZED VIEW alt_reduced_fds2_avg_iv AS
  SELECT plot_id,
    CASE WHEN count_subcanopy_iv_partial > 0 THEN sum_subcanopy_iv_partial / count_subcanopy_iv_partial ELSE 0 END AS avg_subcanopy_iv_partial,
    CASE WHEN count_subcanopy_iv_shade > 0 THEN sum_subcanopy_iv_shade / count_subcanopy_iv_shade ELSE 0 END AS avg_subcanopy_iv_shade,
    CASE WHEN count_canopy_IV > 0 THEN sum_canopy_IV / count_canopy_IV ELSE 0 END AS avg_canopy_IV
  FROM alt_reduced_fds2_sums_counts_iv;

CREATE MATERIALIZED VIEW reduced_fds2_calculations_iv AS
  SELECT a.plot_id,
    sum_subcanopy_iv_partial,
    sum_subcanopy_iv_shade,
    sum_canopy_IV,
    count_subcanopy_iv_partial,
    count_subcanopy_iv_shade,
    count_canopy_IV,
    avg_subcanopy_iv_partial,
    avg_subcanopy_iv_shade,
    avg_canopy_IV
  FROM reduced_fds2_sums_counts_iv a
    LEFT JOIN reduced_fds2_avg_iv b
      ON a.plot_id = b.plot_id;
	  
CREATE MATERIALIZED VIEW alt_reduced_fds2_calculations_iv AS
  SELECT a.plot_id,
    sum_subcanopy_iv_partial,
    sum_subcanopy_iv_shade,
    sum_canopy_IV,
    count_subcanopy_iv_partial,
    count_subcanopy_iv_shade,
    count_canopy_IV,
    avg_subcanopy_iv_partial,
    avg_subcanopy_iv_shade,
    avg_canopy_IV
  FROM reduced_fds2_sums_counts_iv a
    LEFT JOIN alt_reduced_fds2_avg_iv b
      ON a.plot_id = b.plot_id;

CREATE MATERIALIZED VIEW calculations_reduced_fds1 AS
  SELECT a.plot_id,
    sum(CASE WHEN c.scientific_name SIMILAR TO '%Carex%' THEN 1.0 ELSE 0.0 END) AS carex,
    sum(CASE WHEN c.family = 'Cyperaceae' THEN 1.0 ELSE 0.0 END) AS cyperaceae,
    sum(CASE WHEN c.oh_status = 'native' AND c.type = 'DI' THEN 1.0 ELSE 0.0 END) AS dicot,
    sum(CASE WHEN c.shade = 'shade' OR c.shade = 'partial' THEN 1.0 ELSE 0.0 END) AS shade,
    sum(CASE WHEN c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' AND c.form = 'shrub' THEN 1.0 ELSE 0.0 END) AS shrub,
    sum(CASE WHEN c.ncne = 'FACW' OR c.ncne = 'OBL' THEN 1.0 ELSE 0.0 END) AS hydrophyte,
    sum(CASE WHEN c.type = 'SVP' THEN 1 ELSE 0.0 END) AS svp,
    CASE WHEN sum(CASE WHEN c.habit = 'PE' THEN b.relative_cover ELSE 0.0 END) > 0.0
         THEN sum(CASE WHEN c.habit = 'AN' THEN 1.0 ELSE 0.0 END) / sum(CASE WHEN c.habit = 'PE' THEN 1.0 ELSE 0.0 END)
         ELSE 1.0 END AS ap_ratio,
    CASE WHEN sum(CASE WHEN c.cofc >= 0.0 THEN 1.0 ELSE 0.0 END) > 0.0
         THEN sum(CASE WHEN c.cofc >= 0.0 THEN c.cofc ELSE 0.0 END) / sqrt(sum(CASE WHEN c.cofc >= 0.0 THEN 1.0 ELSE 0.0 END))
         ELSE 0.0 END AS fqai,
    sum(CASE WHEN c.form = 'bryo' THEN b.relative_cover ELSE 0.0 END) AS bryophyte,
    sum(CASE WHEN c.shade = 'shade' OR c.shade = 'partial' AND c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' THEN b.relative_cover ELSE 0.0 END) AS per_hydrophyte,
    sum(CASE WHEN c.cofc >= 6.0 THEN b.relative_cover ELSE 0.0 END) AS sensitive,
    sum(CASE WHEN c.cofc <= 2.0 THEN b.relative_cover ELSE 0.0 END) AS tolerant,
    sum(CASE WHEN c.scientific_name = 'Typha angustifolia' OR c.scientific_name = 'Typha x glauca' OR c.scientific_name = 'Phalaris arundinacea' OR c.scientific_name = 'Phragmites australis ssp. australis'  THEN b.relative_cover ELSE 0.0 END) AS invasive_graminoids,
    sum(CASE WHEN c.habit = 'AN' THEN b.relative_cover ELSE 0.0 END) AS habit_an_sum,
    sum(CASE WHEN c.scientific_name = 'Cephalanthus occidentalis' THEN b.relative_cover ELSE 0.0 END) AS button_bush,
    sum(CASE WHEN c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' AND c.habit = 'PE' THEN b.relative_cover ELSE 0.0 END) AS perennial_native_hydrophytes,
    sum(CASE WHEN c.oh_status = 'adventive' THEN b.relative_cover ELSE 0.0 END) AS adventives
  FROM plot a
  LEFT JOIN herbaceous_relative_cover b ON a.plot_id = b.plot_id
  LEFT JOIN species c ON b.species = c.scientific_name
  GROUP BY a.plot_id, a.plot_name;
  
CREATE MATERIALIZED VIEW alt_calculations_reduced_fds1 AS
  SELECT a.plot_id,
    sum(CASE WHEN c.scientific_name SIMILAR TO '%Carex%' THEN 1.0 ELSE 0.0 END) AS carex,
    sum(CASE WHEN c.family = 'Cyperaceae' THEN 1.0 ELSE 0.0 END) AS cyperaceae,
    sum(CASE WHEN c.oh_status = 'native' AND c.type = 'DI' THEN 1.0 ELSE 0.0 END) AS dicot,
    sum(CASE WHEN c.shade = 'shade' OR c.shade = 'partial' THEN 1.0 ELSE 0.0 END) AS shade,
    sum(CASE WHEN c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' AND c.form = 'shrub' THEN 1.0 ELSE 0.0 END) AS shrub,
    sum(CASE WHEN c.ncne = 'FACW' OR c.ncne = 'OBL' THEN 1.0 ELSE 0.0 END) AS hydrophyte,
    sum(CASE WHEN c.type = 'SVP' THEN 1 ELSE 0.0 END) AS svp,
    CASE WHEN sum(CASE WHEN c.habit = 'PE' THEN b.relative_cover ELSE 0.0 END) > 0.0
         THEN sum(CASE WHEN c.habit = 'AN' THEN 1.0 ELSE 0.0 END) / sum(CASE WHEN c.habit = 'PE' THEN 1.0 ELSE 0.0 END)
         ELSE 1.0 END AS ap_ratio,
    CASE WHEN sum(CASE WHEN c.cofc >= 0.0 THEN 1.0 ELSE 0.0 END) > 0.0
         THEN sum(CASE WHEN c.cofc >= 0.0 THEN c.cofc ELSE 0.0 END) / sqrt(sum(CASE WHEN c.cofc >= 0.0 THEN 1.0 ELSE 0.0 END))
         ELSE 0.0 END AS fqai,
    sum(CASE WHEN c.form = 'bryo' THEN b.relative_cover ELSE 0.0 END) AS bryophyte,
    sum(CASE WHEN c.shade = 'shade' OR c.shade = 'partial' AND c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' THEN b.relative_cover ELSE 0.0 END) AS per_hydrophyte,
    sum(CASE WHEN c.cofc >= 6.0 THEN b.relative_cover ELSE 0.0 END) AS sensitive,
    sum(CASE WHEN c.cofc <= 2.0 THEN b.relative_cover ELSE 0.0 END) AS tolerant,
    sum(CASE WHEN c.scientific_name = 'Typha angustifolia' OR c.scientific_name = 'Typha x glauca' OR c.scientific_name = 'Phalaris arundinacea' OR c.scientific_name = 'Phragmites australis ssp. australis'  THEN b.relative_cover ELSE 0.0 END) AS invasive_graminoids,
    sum(CASE WHEN c.habit = 'AN' THEN b.relative_cover ELSE 0.0 END) AS habit_an_sum,
    sum(CASE WHEN c.scientific_name = 'Cephalanthus occidentalis' THEN b.relative_cover ELSE 0.0 END) AS button_bush,
    sum(CASE WHEN c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' AND c.habit = 'PE' THEN b.relative_cover ELSE 0.0 END) AS perennial_native_hydrophytes,
    sum(CASE WHEN c.oh_status = 'adventive' THEN b.relative_cover ELSE 0.0 END) AS adventives
  FROM plot a
  LEFT JOIN alt_herbaceous_relative_cover b ON a.plot_id = b.plot_id
  LEFT JOIN species c ON b.species = c.scientific_name
  GROUP BY a.plot_id, a.plot_name;

CREATE MATERIALIZED VIEW calculations_reduced_fds2_canopy AS
  SELECT a.plot_id,
    b.small_tree,
    c.avg_subcanopy_iv_partial + c.avg_subcanopy_iv_shade AS subcanopy_iv,
    c.avg_canopy_IV AS canopy_iv
  FROM plot a
    LEFT JOIN reduced_fds2_rel_den_calculations b
      ON a.plot_id = b.plot_id
    LEFT JOIN reduced_fds2_calculations_iv c
      ON a.plot_id = c.plot_id;
	  
CREATE MATERIALIZED VIEW alt_calculations_reduced_fds2_canopy AS
  SELECT a.plot_id,
    b.small_tree,
    c.avg_subcanopy_iv_partial + c.avg_subcanopy_iv_shade AS subcanopy_iv,
    c.avg_canopy_IV AS canopy_iv
  FROM plot a
    LEFT JOIN reduced_fds2_rel_den_calculations b
      ON a.plot_id = b.plot_id
    LEFT JOIN alt_reduced_fds2_calculations_iv c
      ON a.plot_id = c.plot_id;

CREATE MATERIALIZED VIEW calculations_reduced_fds2_stems AS
  SELECT a.plot_id,
    sum(CASE WHEN c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' AND c.form = 'tree' THEN b.tot_stems_ha ELSE 0 END) AS stems_wetland_trees,
    sum(CASE WHEN c.oh_status = 'native' AND c.ncne = 'FACW' OR c.ncne = 'OBL' AND c.form = 'shrub' THEN b.tot_stems_ha ELSE 0 END) AS stems_wetland_shrubs
  FROM plot a
    LEFT JOIN reduced_fds2_tot_stems b
      ON a.plot_id = b.plot_id
    LEFT JOIN species c
      ON b.species = c.scientific_name
  GROUP BY a.plot_id;

CREATE MATERIALIZED VIEW herbaceous_info_tot_cov AS
  SELECT a.plot_id, a.info, sum(b.midpoint) as tot_cov
  FROM plot_module_herbaceous_info a
    LEFT JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  WHERE a.cover_class_code NOTNULL
  GROUP BY a.plot_id, a.info;

CREATE MATERIALIZED VIEW herbaceous_info_tot_count AS
  SELECT a.plot_id, a.info, sum(CASE WHEN b.midpoint > 0 THEN 1 ELSE 0 END) as tot_count
  FROM plot_module_herbaceous_info a
    LEFT JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  WHERE a.cover_class_code NOTNULL
  GROUP BY a.plot_id, a.info;

CREATE MATERIALIZED VIEW herbaceous_info_relative_cover AS
  SELECT a.plot_id, a.info, CASE WHEN a.tot_cov > 0 THEN a.tot_cov / b.tot_count ELSE 0 END AS relative_cover
  FROM herbaceous_info_tot_cov a
    LEFT JOIN herbaceous_info_tot_count b ON a.plot_id = b.plot_id AND a.info = b.info;

CREATE MATERIALIZED VIEW calculations_plot_module_herbaceous_info AS
  SELECT plot_id,
    sum(CASE WHEN info = '%bare ground' OR info = '%litter cover' THEN relative_cover ELSE 0 END) AS unvegetated_partial,
    sum(CASE WHEN info = '%open water' THEN relative_cover ELSE 0 END) AS open_water,
    sum(CASE WHEN info = '%unvegetated open water' THEN relative_cover ELSE 0 END) AS unvegetated_open_water,
    sum(CASE WHEN info = '%bare ground' THEN relative_cover ELSE 0 END) AS bare_ground
  FROM herbaceous_info_relative_cover
  GROUP BY plot_id;

CREATE MATERIALIZED VIEW vibi_values AS
  SELECT a.plot_id,
    'vibi_values'::text as vibi_type,
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
    biomass AS biomass_metric_value,
    stems_wetland_trees AS stems_ha_wetland_trees,
    stems_wetland_shrubs AS stems_ha_wetland_shrubs,
    unvegetated_partial + habit_an_sum AS per_unvegetated,
    button_bush AS per_button_bush,
    perennial_native_hydrophytes AS per_perennial_native_hydrophytes,
    adventives AS per_adventives,
    open_water AS per_open_water,
    unvegetated_open_water AS per_unvegetated_open_water,
    bare_ground AS per_bare_ground
  FROM plot a
    LEFT JOIN calculations_reduced_fds2_canopy b
      ON a.plot_id = b.plot_id
    LEFT JOIN calculations_reduced_fds2_stems c
      ON a.plot_id = c.plot_id
    LEFT JOIN calculations_plot_module_herbaceous_info d
      ON a.plot_id = d.plot_id
    LEFT JOIN calculations_reduced_fds1 e
      ON a.plot_id = e.plot_id
    LEFT JOIN biomass_calculations f
      ON a.plot_id = f.plot_id;
	  
CREATE MATERIALIZED VIEW alt_vibi_values AS
  SELECT a.plot_id,
    'vibi_values'::text as vibi_type,
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
    biomass AS biomass_metric_value,
    stems_wetland_trees AS stems_ha_wetland_trees,
    stems_wetland_shrubs AS stems_ha_wetland_shrubs,
    unvegetated_partial + habit_an_sum AS per_unvegetated,
    button_bush AS per_button_bush,
    perennial_native_hydrophytes AS per_perennial_native_hydrophytes,
    adventives AS per_adventives,
    open_water AS per_open_water,
    unvegetated_open_water AS per_unvegetated_open_water,
    bare_ground AS per_bare_ground
  FROM plot a
    LEFT JOIN alt_calculations_reduced_fds2_canopy b
      ON a.plot_id = b.plot_id
    LEFT JOIN calculations_reduced_fds2_stems c
      ON a.plot_id = c.plot_id
    LEFT JOIN calculations_plot_module_herbaceous_info d
      ON a.plot_id = d.plot_id
    LEFT JOIN alt_calculations_reduced_fds1 e
      ON a.plot_id = e.plot_id
    LEFT JOIN biomass_calculations f
      ON a.plot_id = f.plot_id;

CREATE MATERIALIZED VIEW vibi_e_index AS
  SELECT a.plot_id,
    'vibi_e_index'::text as vibi_type,
    metric_value(b.carex_metric_value::numeric, ARRAY[2.0, 4.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS carex_metric_value,
    null::numeric AS cyperaceae_metric_value,
    metric_value(b.dicot_metric_value::numeric, ARRAY[11.0, 18.0, 26.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS dicot_metric_value,
    null::numeric AS shade_metric_value,
    metric_value(b.shrub_metric_value::numeric, ARRAY[2.0, 3.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shrub_metric_value,
    metric_value(b.hydrophyte_metric_value::numeric, ARRAY[11.0, 21.0, 31.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS hydrophyte_metric_value,
    null::numeric AS svp_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.ap_ratio_metric_value::numeric, ARRAY[0.205, 0.325, 0.485], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[9.95, 14.35, 21.45], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    null::numeric AS bryophyte_metric_value,
    null::numeric AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0255, 0.1005, 0.1505], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.2005, 0.4005, 0.6005], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.invasive_graminoids_metric_value::numeric, ARRAY[0.0305, 0.1505, 0.3046], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS invasive_graminoids_metric_value,
    null::numeric AS small_tree_metric_value,
    null::numeric AS subcanopy_iv,
    null::numeric AS canopy_iv,
    CASE WHEN d.biomass_collected = 'NO-NAT' OR d.biomass_collected = 'NO-MIT' THEN 3
    WHEN d.biomass_collected = 'YES'
      THEN metric_value(b.biomass_metric_value, ARRAY[100.0, 201.0, 451.0, 801.0], ARRAY[0.0, 10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_id = c.plot_id
    LEFT JOIN biomass_info d
      ON a.plot_id = d.plot_id;
	  
CREATE MATERIALIZED VIEW alt_vibi_e_index AS
  SELECT a.plot_id,
    'vibi_e_index'::text as vibi_type,
    metric_value(b.carex_metric_value::numeric, ARRAY[2.0, 4.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS carex_metric_value,
    null::numeric AS cyperaceae_metric_value,
    metric_value(b.dicot_metric_value::numeric, ARRAY[11.0, 18.0, 26.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS dicot_metric_value,
    null::numeric AS shade_metric_value,
    metric_value(b.shrub_metric_value::numeric, ARRAY[2.0, 3.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shrub_metric_value,
    metric_value(b.hydrophyte_metric_value::numeric, ARRAY[11.0, 21.0, 31.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS hydrophyte_metric_value,
    null::numeric AS svp_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.ap_ratio_metric_value::numeric, ARRAY[0.205, 0.325, 0.485], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[9.95, 14.35, 21.45], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    null::numeric AS bryophyte_metric_value,
    null::numeric AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0255, 0.1005, 0.1505], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.2005, 0.4005, 0.6005], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.invasive_graminoids_metric_value::numeric, ARRAY[0.0305, 0.1505, 0.3046], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS invasive_graminoids_metric_value,
    null::numeric AS small_tree_metric_value,
    null::numeric AS subcanopy_iv,
    null::numeric AS canopy_iv,
    CASE WHEN d.biomass_collected = 'NO-NAT' OR d.biomass_collected = 'NO-MIT' THEN 3
    WHEN d.biomass_collected = 'YES'
      THEN metric_value(b.biomass_metric_value, ARRAY[100.0, 201.0, 451.0, 801.0], ARRAY[0.0, 10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN alt_vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN alt_herbaceous_site_cov c
      ON a.plot_id = c.plot_id
    LEFT JOIN biomass_info d
      ON a.plot_id = d.plot_id;

CREATE MATERIALIZED VIEW vibi_ecst_index AS
  SELECT a.plot_id,
    'vibi_ecst_index'::text as vibi_type,
    null::numeric AS carex_metric_value,
    metric_value(b.cyperaceae_metric_value::numeric, ARRAY[2.0, 4.0, 7.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS cyperaceae_metric_value,
    metric_value(b.dicot_metric_value::numeric, ARRAY[11.0, 18.0, 26.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS dicot_metric_value,
    null::numeric AS shade_metric_value,
    metric_value(b.shrub_metric_value::numeric, ARRAY[2.0, 3.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shrub_metric_value,
    metric_value(b.hydrophyte_metric_value::numeric, ARRAY[11.0, 21.0, 31.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS hydrophyte_metric_value,
    null::numeric AS svp_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.ap_ratio_metric_value::numeric, ARRAY[0.205, 0.325, 0.485], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[9.95, 14.35, 21.45], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    null::numeric AS bryophyte_metric_value,
    null::numeric AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0255, 0.1005, 0.1505], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.2005, 0.4005, 0.6005], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.invasive_graminoids_metric_value::numeric, ARRAY[0.0305, 0.1505, 0.3046], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS invasive_graminoids_metric_value,
    null::numeric AS small_tree_metric_value,
    null::numeric AS subcanopy_iv,
    null::numeric AS canopy_iv,
    CASE WHEN d.biomass_collected = 'NO-NAT' OR d.biomass_collected = 'NO-MIT' THEN 3
    WHEN d.biomass_collected = 'YES'
      THEN metric_value(b.biomass_metric_value, ARRAY[100.0, 201.0, 451.0, 801.0], ARRAY[0.0, 10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_id = c.plot_id
    LEFT JOIN biomass_info d
      ON a.plot_id = d.plot_id;
	  
CREATE MATERIALIZED VIEW alt_vibi_ecst_index AS
  SELECT a.plot_id,
    'vibi_ecst_index'::text as vibi_type,
    null::numeric AS carex_metric_value,
    metric_value(b.cyperaceae_metric_value::numeric, ARRAY[2.0, 4.0, 7.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS cyperaceae_metric_value,
    metric_value(b.dicot_metric_value::numeric, ARRAY[11.0, 18.0, 26.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS dicot_metric_value,
    null::numeric AS shade_metric_value,
    metric_value(b.shrub_metric_value::numeric, ARRAY[2.0, 3.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shrub_metric_value,
    metric_value(b.hydrophyte_metric_value::numeric, ARRAY[11.0, 21.0, 31.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS hydrophyte_metric_value,
    null::numeric AS svp_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.ap_ratio_metric_value::numeric, ARRAY[0.205, 0.325, 0.485], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[9.95, 14.35, 21.45], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    null::numeric AS bryophyte_metric_value,
    null::numeric AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0255, 0.1005, 0.1505], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.2005, 0.4005, 0.6005], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.invasive_graminoids_metric_value::numeric, ARRAY[0.0305, 0.1505, 0.3046], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS invasive_graminoids_metric_value,
    null::numeric AS small_tree_metric_value,
    null::numeric AS subcanopy_iv,
    null::numeric AS canopy_iv,
    CASE WHEN d.biomass_collected = 'NO-NAT' OR d.biomass_collected = 'NO-MIT' THEN 3
    WHEN d.biomass_collected = 'YES'
      THEN metric_value(b.biomass_metric_value, ARRAY[100.0, 201.0, 451.0, 801.0], ARRAY[0.0, 10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN alt_vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN alt_herbaceous_site_cov c
      ON a.plot_id = c.plot_id
    LEFT JOIN biomass_info d
      ON a.plot_id = d.plot_id;

CREATE MATERIALIZED VIEW vibi_sh_index AS
  SELECT a.plot_id,
    'vibi_sh_index'::text as vibi_type,
    metric_value(b.carex_metric_value::numeric, ARRAY[2.0, 4.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS carex_metric_value,
    null::numeric AS cyperaceae_metric_value,
    metric_value(b.dicot_metric_value::numeric, ARRAY[10.0, 15.0, 24.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS dicot_metric_value,
    null::numeric AS shade_metric_value,
    metric_value(b.shrub_metric_value::numeric, ARRAY[2.0, 3.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shrub_metric_value,
    metric_value(b.hydrophyte_metric_value::numeric, ARRAY[10.0, 15.0, 21.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS hydrophyte_metric_value,
    metric_value(b.svp_metric_value::numeric, ARRAY[1.0, 2.0, 3.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS svp_metric_value,
    null::numeric AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[9.95, 14.35, 21.45], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.bryophyte_metric_value::numeric, ARRAY[0.0105, 0.0305, 0.0605], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS bryophyte_metric_value,
    null::numeric AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0205, 0.0605, 0.1305], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.0505, 0.1005, 0.1505], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    null::numeric AS invasive_graminoids_metric_value,
    null::numeric AS small_tree_metric_value,
    metric_value(b.subcanopy_iv::numeric, ARRAY[0.0205, 0.0505, 0.1046], ARRAY[0.0, 3.0, 7.0, 10.0]) AS subcanopy_iv,
    null::numeric AS canopy_iv,
    null::numeric AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_id = c.plot_id
    LEFT JOIN biomass_info d
      ON a.plot_id = d.plot_id;
	  
CREATE MATERIALIZED VIEW alt_vibi_sh_index AS
  SELECT a.plot_id,
    'vibi_sh_index'::text as vibi_type,
    metric_value(b.carex_metric_value::numeric, ARRAY[2.0, 4.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS carex_metric_value,
    null::numeric AS cyperaceae_metric_value,
    metric_value(b.dicot_metric_value::numeric, ARRAY[10.0, 15.0, 24.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS dicot_metric_value,
    null::numeric AS shade_metric_value,
    metric_value(b.shrub_metric_value::numeric, ARRAY[2.0, 3.0, 5.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shrub_metric_value,
    metric_value(b.hydrophyte_metric_value::numeric, ARRAY[10.0, 15.0, 21.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS hydrophyte_metric_value,
    metric_value(b.svp_metric_value::numeric, ARRAY[1.0, 2.0, 3.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS svp_metric_value,
    null::numeric AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[9.95, 14.35, 21.45], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.bryophyte_metric_value::numeric, ARRAY[0.0105, 0.0305, 0.0605], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS bryophyte_metric_value,
    null::numeric AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0205, 0.0605, 0.1305], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.0505, 0.1005, 0.1505], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    null::numeric AS invasive_graminoids_metric_value,
    null::numeric AS small_tree_metric_value,
    metric_value(b.subcanopy_iv::numeric, ARRAY[0.0205, 0.0505, 0.1046], ARRAY[0.0, 3.0, 7.0, 10.0]) AS subcanopy_iv,
    null::numeric AS canopy_iv,
    null::numeric AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN alt_vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN alt_herbaceous_site_cov c
      ON a.plot_id = c.plot_id
    LEFT JOIN biomass_info d
      ON a.plot_id = d.plot_id;

CREATE MATERIALIZED VIEW vibi_f_index AS
  SELECT a.plot_id,
    'vibi_f_index'::text as vibi_type,
    null::numeric AS carex_metric_value,
    null::numeric AS cyperaceae_metric_value,
    null::numeric AS dicot_metric_value,
    metric_value(b.shade_metric_value::numeric, ARRAY[8.0, 14.0, 21.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shade_metric_value,
    null::numeric AS shrub_metric_value,
    null::numeric AS hydrophyte_metric_value,
    metric_value(b.svp_metric_value::numeric, ARRAY[1.0, 2.0, 3.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS svp_metric_value,
    null::numeric AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[14.05, 19.05, 24.05], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.bryophyte_metric_value::numeric, ARRAY[0.0105, 0.0305, 0.0605], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS bryophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.per_hydrophyte_metric_value::numeric, ARRAY[0.1046, 0.1505, 0.2805], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0355, 0.1205, 0.3050], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.1505, 0.3005, 0.4505], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    null::numeric AS invasive_graminoids_metric_value,
    CASE WHEN COALESCE(b.small_tree_metric_value, 0.0) > 0.0 THEN
      metric_value(b.small_tree_metric_value::numeric, ARRAY[0.1105, 0.2205, 0.3205], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS small_tree_metric_value,
    CASE WHEN COALESCE(b.subcanopy_iv, 0.0) > 0.0 THEN
      metric_value(b.subcanopy_iv::numeric, ARRAY[0.0205, 0.0725, 0.1305], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS subcanopy_iv,
    CASE WHEN COALESCE(b.canopy_iv, 0.0) > 0.0 THEN
      metric_value(b.canopy_iv::numeric, ARRAY[0.1405, 0.1705, 0.2105], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS canopy_iv,
    null::numeric AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_id = c.plot_id;
	  
CREATE MATERIALIZED VIEW alt_vibi_f_index AS
  SELECT a.plot_id,
    'vibi_f_index'::text as vibi_type,
    null::numeric AS carex_metric_value,
    null::numeric AS cyperaceae_metric_value,
    null::numeric AS dicot_metric_value,
    metric_value(b.shade_metric_value::numeric, ARRAY[8.0, 14.0, 21.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS shade_metric_value,
    null::numeric AS shrub_metric_value,
    null::numeric AS hydrophyte_metric_value,
    metric_value(b.svp_metric_value::numeric, ARRAY[1.0, 2.0, 3.0], ARRAY[0.0, 3.0, 7.0, 10.0]) AS svp_metric_value,
    null::numeric AS ap_ratio_metric_value,
    metric_value(b.fqai_metric_value::numeric, ARRAY[14.05, 19.05, 24.05], ARRAY[0.0, 3.0, 7.0, 10.0]) AS fqai_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.bryophyte_metric_value::numeric, ARRAY[0.0105, 0.0305, 0.0605], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS bryophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.per_hydrophyte_metric_value::numeric, ARRAY[0.1046, 0.1505, 0.2805], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS per_hydrophyte_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.sensitive_metric_value::numeric, ARRAY[0.0355, 0.1205, 0.3050], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS sensitive_metric_value,
    CASE WHEN COALESCE(c.site_cov, 0.0) > 0.1049 THEN
      metric_value(b.tolerant_metric_value::numeric, ARRAY[0.1505, 0.3005, 0.4505], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS tolerant_metric_value,
    null::numeric AS invasive_graminoids_metric_value,
    CASE WHEN COALESCE(b.small_tree_metric_value, 0.0) > 0.0 THEN
      metric_value(b.small_tree_metric_value::numeric, ARRAY[0.1105, 0.2205, 0.3205], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS small_tree_metric_value,
    CASE WHEN COALESCE(b.subcanopy_iv, 0.0) > 0.0 THEN
      metric_value(b.subcanopy_iv::numeric, ARRAY[0.0205, 0.0725, 0.1305], ARRAY[0.0, 3.0, 7.0, 10.0])
    ELSE 0.0 END AS subcanopy_iv,
    CASE WHEN COALESCE(b.canopy_iv, 0.0) > 0.0 THEN
      metric_value(b.canopy_iv::numeric, ARRAY[0.1405, 0.1705, 0.2105], ARRAY[10.0, 7.0, 3.0, 0.0])
    ELSE 0.0 END AS canopy_iv,
    null::numeric AS biomass_metric_value,
    null::numeric AS stems_ha_wetland_trees,
    null::numeric AS stems_ha_wetland_shrubs,
    null::numeric AS per_unvegetated,
    null::numeric AS per_button_bush,
    null::numeric AS per_perennial_native_hydrophytes,
    null::numeric AS per_adventives,
    null::numeric AS per_open_water,
    null::numeric AS per_unvegetated_open_water,
    null::numeric AS per_bare_ground
  FROM plot a
    LEFT JOIN alt_vibi_values b
      ON a.plot_id = b.plot_id
    LEFT JOIN alt_herbaceous_site_cov c
      ON a.plot_id = c.plot_id;

CREATE MATERIALIZED VIEW metric_calculations AS
  SELECT *, row_number() OVER () AS view_id FROM (
    SELECT *, null AS score FROM vibi_values
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM vibi_e_index
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM vibi_ecst_index
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM vibi_sh_index
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM vibi_f_index
  ) a;
  
CREATE MATERIALIZED VIEW alt_metric_calculations AS
  SELECT *, row_number() OVER () AS view_id FROM (
    SELECT *, null AS score FROM alt_vibi_values
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM alt_vibi_e_index
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM alt_vibi_ecst_index
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM alt_vibi_sh_index
    UNION
    SELECT *, COALESCE(carex_metric_value, 0.0) + COALESCE(cyperaceae_metric_value, 0.0) + COALESCE(dicot_metric_value, 0.0) +
              COALESCE(shade_metric_value, 0.0) + COALESCE(shrub_metric_value, 0.0) + COALESCE(hydrophyte_metric_value, 0.0) +
              COALESCE(svp_metric_value, 0.0) + COALESCE(ap_ratio_metric_value, 0.0) + COALESCE(fqai_metric_value, 0.0) +
              COALESCE(bryophyte_metric_value, 0.0) + COALESCE(per_hydrophyte_metric_value, 0.0) + COALESCE(sensitive_metric_value, 0.0) +
              COALESCE(tolerant_metric_value, 0.0) + COALESCE(invasive_graminoids_metric_value, 0.0) + COALESCE(small_tree_metric_value, 0.0) +
              COALESCE(subcanopy_iv, 0.0) + COALESCE(canopy_iv, 0.0) + COALESCE(biomass_metric_value, 0.0) + COALESCE(stems_ha_wetland_trees, 0.0) +
              COALESCE(stems_ha_wetland_shrubs, 0.0) + COALESCE(per_unvegetated, 0.0) + COALESCE(per_button_bush, 0.0) +
              COALESCE(per_perennial_native_hydrophytes, 0.0) + COALESCE(per_adventives, 0.0) + COALESCE(per_open_water, 0.0) +
              COALESCE(per_unvegetated_open_water, 0.0) AS score FROM alt_vibi_f_index
  ) a;
  
CREATE MATERIALIZED VIEW herb_rel_cover_acronym_joined AS (
SELECT a.plot_id AS plot_id, a.species, a.relative_cover, b.acronym FROM herbaceous_relative_cover a
LEFT JOIN species b ON a.species = b.scientific_name);

CREATE MATERIALIZED VIEW woody_iv_acronym_joined AS (
SELECT a.plot_id AS plot_id, a.species, a.iv, b.acronym FROM reduced_fds2_iv a
LEFT JOIN species b ON a.species = b.scientific_name);

CREATE MATERIALIZED VIEW herb_plot_x_species_matrix AS (SELECT * FROM crosstab(                 
       'SELECT plot_id, acronym, relative_cover
        FROM   herb_rel_cover_acronym_joined
        ORDER  BY 1'
        ,$$VALUES ('ACARHO'),('ACENEG'),('ACENIG'),('ACEPLA'),('ACERUB'),('ACESAC'),('ACESAR'),('ACER'),('ACHMIL'),('ACOCAL'),('ACTALB'),('ACTAEA'),('ACTARG'),('ADIPED'),('AEGPOD'),('AESGLA'),('AESHIP'),('AESCUL'),('AGRGRY'),('AGRPAR'),('AGRPUB'),('AGRIMO'),('AGRSTR'),('AGRCAP'),('AGRGIG'),('AGRHYE'),('AGRPER'),('AGROST'),('AGRSTO'),('AILALT'),('AJUREP'),('ALISUB'),('ALLPET'),('ALLCAN'),('ALLCER'),('ALLSAT'),('ALLIUM'),('ALLTRI'),('ALLVIN'),('ALNGLU'),('ALNINC'),('ALNUS'),('AMBART'),('AMBTRI'),('AMEARB'),('AMELAN'),('AMPBRE'),('AMPBRA'),('ANAARV'),('ANDGER'),('ANDVIRV'),('ANEQUI'),('ANEVIR'),('ANETHA'),('ANGATR'),('ANTARI'),('ANTODO'),('APIACE'),('APIAME'),('APOCAN'),('APOCYN'),('ARACAN'),('ARALAE'),('ARANUD'),('ARARAC'),('ARCLAP'),('ARCMIN'),('ARCTIUM'),('ARIDRA'),('ARITRIT'),('ARONIA'),('ARTVUL'),('ARTEMS'),('ASACAN'),('ASCINC'),('ASCLEP'),('ASCSUL'),('ASCSYR'),('ASCTUB'),('ASITRI'),('ASPPLA'),('ASTCOR'),('ASTLAN'),('ASTLAT'),('ASTLIN'),('ASTMAC'),('ASTNOV'),('ASTPIL'),('ASTPUN'),('ASTER'),('ASTUMB'),('ATHFEL'),('ATHTHE'),('BARBAR'),('BARVER'),('BARVUL'),('BERTHU'),('BETALL'),('BETLEN'),('BETPAP'),('BETPOP'),('BETULA'),('BIDFRO'),('BIDENS'),('BLECIL'),('BLEHIR'),('BLEPHI'),('BOECYL'),('BOTRYC'),('BOUCUR'),('BRAERE'),('BRASSI'),('BROCOM'),('BROINE'),('BROALT'),('BROPUB'),('BROMUS'),('CLTPAL'),('CALSEP'),('CALYST'),('CAMSCI'),('CAMRAD'),('CARDIP'),('CARDOU'),('CARHIR'),('CARPAR'),('CARPEN'),('CARDAM'),('CXALBU'),('CXAMPH'),('CXANNE'),('CXAPPA'),('CXARCT'),('CXBLAN'),('CXBROM'),('CXCARE'),('CXCPHLA'),('CXCOMM'),('CXCOMP'),('CXCONJ'),('CXCRINC'),('CXCRIS'),('CXDEBIR'),('CXDIGI'),('CXEMOR'),('CXFEST'),('CXFRAN'),('CXGLAU'),('CXGRCLE'),('CXGRCLI'),('CXGRAN'),('CXGRAY'),('CXGRIS'),('CXHIRS'),('CXHIRT'),('CXINTE'),('CXINTU'),('CXJAME'),('CXLACU'),('CXLAEV'),('CXLAXC'),('CXLAXF'),('CXLPTON'),('CXLUPL'),('CXLURI'),('CXNORM'),('CXOLIGC'),('CXPALL'),('CXPENS'),('CXPLNT'),('CXPRAS'),('CXRADI'),('CXROSE'),('CXSCOP'),('CAREX'),('CXSPAR'),('CXSTIP'),('CXSWAN'),('CXTRIB'),('CXVIRE'),('CXVULP'),('CXWILL'),('CXWOOD'),('CRPCAR'),('CARCOR'),('CARGLA'),('CARLAC'),('CAROVT'),('CARYA'),('CARTOM'),('CASDEN'),('CATBIG'),('CATALP'),('CATSPE'),('CAUTHA'),('CELORB'),('CELOCC'),('CENJAC'),('CENTAU'),('CENPUL'),('CEPOCC'),('CERAST'),('CERVUL'),('CERCAN'),('CHEGLA'),('CHEALB'),('CHENOP'),('CHRLEU'),('CHRAME'),('CICINT'),('CICMAC'),('CIMRAC'),('CINARU'),('CIRLUT'),('CIRALT'),('CIRARV'),('CIRMUT'),('CIRSIU'),('CIRVUL'),('CLEVIR'),('CLIVUL'),('COLCAN'),('CONMACU'),('CONAME'),('CONMAJ'),('CONARV'),('CONCAN'),('CORTRP'),('CORALT'),('CORAMO'),('CORFLO'),('CORRAC'),('CORSER'),('CORNUS'),('CORVAR'),('CRACRU'),('CRAMON'),('CRATAE'),('CRYCAN'),('CUCMAX'),('CUSGRO'),('CUSCUT'),('CYPERY'),('CYPESC'),('CYPSTR'),('CYSFRA'),('CYSPRO'),('DACGLO'),('DANCOM'),('DANTHO'),('DANSPI'),('DAUCAR'),('DAUCUS'),('DESCNA'),('DESCUS'),('DESGLA'),('DESPAN'),('DESMOD'),('DIAARM'),('DIGITI'),('DIGISC'),('DIGSAN'),('DIGITA'),('DIOVIL'),('DIPFUL'),('DIPSAC'),('DRYCAR'),('DRYCRI'),('DRYGOL'),('DRYINT'),('DRYMAR'),('DRYOPT'),('DUCIND'),('DULARU'),('ECHPUR'),('ECHCRU'),('ECHMUR'),('ECHINO'),('ECHLOB'),('ELAANG'),('ELAUMB'),('ELEACI'),('ELEOBT'),('ELEPAL'),('ELYCAN'),('ELYHYS'),('ELYMAC'),('ELYRIP'),('ELYMUS'),('ELYVIL'),('ELYVIR'),('ELYREP'),('EPIVIR'),('EPICIL'),('EPICOL'),('EPIHIR'),('EPIPAR'),('EPILOB'),('EPIHEL'),('EQUARV'),('EQUHYE'),('EQUISE'),('EREHIE'),('ERIANN'),('ERIPHI'),('ERIGER'),('ERISTR'),('EUOALA'),('EUOATR'),('EUOEUR'),('EUOFOR'),('EUOOBO'),('EUONYM'),('EUPARO'),('EUPMAC'),('EUPPER'),('EUPPUR'),('EUPRUG'),('EUPSER'),('EUPTOR'),('EUPHMA'),('EUPHOR'),('EUTGRA'),('FAGGRA'),('FESELA'),('FESOVI'),('FESPRA'),('FESRUB'),('FESTUC'),('FESSUB'),('FESTRA'),('FORINT'),('FRAGAR'),('FRAVESA'),('FRAVESV'),('FRAVIR'),('FRAAME'),('FRANIG'),('FRAPEN'),('FRAPRO'),('FRAXIN'),('GALQUA'),('GALAPA'),('GALASP'),('GALCIR'),('GALCON'),('GALLAN'),('GALMOL'),('GALOBT'),('GALODO'),('GALPAL'),('GALIUM'),('GALTIN'),('GALTFL'),('GAUPRO'),('GERMAC'),('GERROB'),('GERANI'),('GEUCAN'),('GEULAC'),('GEUM'),('GEUVER'),('GEUVIR'),('GINBIL'),('GLEHED'),('GLETRI'),('GLYSEP'),('GLYCER'),('GLYSTR'),('HACVIR'),('HAMVIR'),('HEDPUL'),('HEDHEL'),('HEDCAE'),('HELAUT'),('HELDEC'),('HELIAN'),('HELHEL'),('HEMFUL'),('HEPACU'),('HERLAN'),('HESMAT'),('HEUAME'),('HIECAE'),('HIEGRO'),('HIEPAN'),('HIERAC'),('HOLLAN'),('HUMJAP'),('HDRCAN'),('HYDCAN'),('HYDROP'),('HYDVIR'),('HYPMUT'),('HYPPER'),('HYPPRO'),('HYPPUN'),('HYPERI'),('HYPRAD'),('ILEOPA'),('IMPCAP'),('IMPPAL'),('IMPATI'),('IPOMOE'),('IRIPSE'),('IRIS'),('ISOBIT'),('JEFDIP'),('JUGNIG'),('JUNEFF'),('JUNMAR'),('JUNCUS'),('JUNTEN'),('JUNIPE'),('LACBIE'),('LACCAN'),('LACFLO'),('LACTUC'),('LAPCOM'),('LEEORY'),('LEEVIR'),('LEMMIN'),('LEOAUT'),('LEOCAR'),('LIATRIS'),('LIASPI'),('LICHEN'),('LIGVUL'),('LILCAN'),('LINBEN'),('LIQSTY'),('LIRTUL'),('LOBINF'),('LOBSIP'),('LOBELI'),('LOLPER'),('LOLSP'),('LONDIO'),('LONJAP'),('LONMAA'),('LONMOR'),('LONSEM'),('LONICE'),('LONTAT'),('LOTCOR'),('LUDPAL'),('LUNANN'),('LUZACU'),('LUZMUL'),('LUZULA'),('LYCOPO'),('LYCAME'),('LYCOPU'),('LYCUNI'),('LYCVIR'),('LYSCIL'),('LYSNUM'),('LYSTER'),('LYTSAL'),('MACPOM'),('MAGACU'),('MAICAN'),('MAIRAC'),('MAIANT'),('MATSTR'),('MEDLUP'),('MELALB'),('MELOFF'),('MELILO'),('MLIOFF'),('MENCAN'),('MENARV'),('MENTHA'),('MENSPI'),('MENPIP'),('METGLY'),('MICVIM'),('MILEFF'),('MIMALA'),('MIMRIN'),('MITREP'),('MITDIP'),('MONCLI'),('MONFIS'),('MONOTR'),('MNTUNI'),('MORALB'),('MORUS'),('MOSS'),('MUHFRO'),('MUHSCH'),('MUHTEN'),('MYOLAX'),('MYOSCO'),('MYOSOT'),('NARCIS'),('NELLUT'),('NEPCAT'),('NUPADV'),('NYSSYL'),('OENBIE'),('ONOSEN'),('OSMOCL'),('OSMOLO'),('OSMCIN'),('OSMCLA'),('OSTVIR'),('OXALIS'),('OXASTR'),('PACTER'),('PANCLA'),('PANFLE'),('PANLAN'),('PANLAT'),('PANLIN'),('PANICU'),('PANVIR'),('PARQUI'),('PARVIT'),('PELVIR'),('PENDIG'),('PENSTE'),('PENSED'),('PETHYB'),('PHAARU'),('PHICOR'),('PHLPRA'),('PHLDIV'),('PHRAUSAM'),('PHRAUSAU'),('PHYOPU'),('PHYAME'),('PICABI'),('PICEA'),('PILPUM'),('PILEA'),('PINNIG'),('PINRES'),('PINSPP'),('PINSTRN'),('PINSYL'),('PLANLA'),('PLANMA'),('PLANRU'),('PLANTA'),('PLAOCC'),('POAALS'),('POAANN'),('POACOM'),('POAPAL'),('POAPRA'),('POA'),('POASYL'),('POATRI'),('POACEA'),('PODPEL'),('POLREP'),('POLBIF'),('POLPUB'),('POLYGO'),('PLGARI'),('PLGAVI'),('PLGCES'),('PLGCON'),('PLGCUS'),('PLGHPR'),('PLGHPO'),('PLGLAP'),('PLGPER'),('PLGPUN'),('PLGSAG'),('PLGSCA'),('POLYGN'),('PLGVIR'),('POLVIG'),('POLACR'),('POPALB'),('POPDEL'),('POPGRA'),('POPULU'),('POROLE'),('POTNOR'),('POTREC'),('POTSIM'),('POTENT'),('PREALB'),('PREALT'),('PRESER'),('PRENAN'),('PROLAN'),('PRUVUL'),('PRUCER'),('PRUSER'),('PRUNUS'),('PRUVIR'),('PTEAQU'),('PYCMUT'),('PYCNAN'),('PYCTEN'),('PYRANG'),('PYRCOM'),('PYRCOR'),('PYRIOE'),('PYRMAL'),('PYRUS'),('QUEALB'),('QUEBIC'),('QUECOC'),('QUEMAC'),('QUEMUE'),('QUEPAL'),('QUERUB'),('QUERCU'),('QUEVEL'),('RANABO'),('RANACR'),('RANALL'),('RANFIC'),('RANHIS'),('RANREC'),('RANREP'),('RANUNC'),('RAPRAP'),('RATPIN'),('RHACAT'),('RHAFRA'),('RHAMNU'),('RHODOD'),('RHUS'),('RHUTYP'),('RIBAME'),('RIBCYN'),('RIBODO'),('RIBES'),('RICFLU'),('ROBPSE'),('RORIPP'),('ROSCAR'),('ROSMUL'),('ROSPAL'),('ROSSET'),('ROSA'),('RUBALL'),('RUBCAE'),('RUBFLA'),('RUBHIS'),('RUBIDAS'),('RUBOCC'),('RUBODO'),('RUBPEN'),('RUBPHO'),('RUBUS'),('RUDFUL'),('RUDLAC'),('RUDBEC'),('RUMACE'),('RUMCRI'),('RUMOBT'),('RUMEX'),('SAGLAT'),('SLXDIS'),('SLXFRA'),('SLXNIG'),('SALIX'),('SAMCAN'),('SAMPUB'),('SAMBUC'),('SANCAN'),('SANICAN'),('SANIGR'),('SANIMA'),('SANICU'),('SANITR'),('SAPOFF'),('SASALB'),('SCHSCOS'),('SCHTAB'),('SCIATR'),('SCICYP'),('SCIPOL'),('SCIRPU'),('SCRMAR'),('SCROPH'),('SCUINT'),('SCULAT'),('SCUTEL'),('SEDUM'),('SEDTER'),('SENAUR'),('SENOBO'),('SENECI'),('SETFAB'),('SETARI'),('SETVIR'),('SICANG'),('SLPPER'),('SISOFF'),('SISANG'),('SISMON'),('SISYRI'),('SMXHIS'),('SMXROT'),('SMILAX'),('SLMCAR'),('SLMDUL'),('SLMNIG'),('SOLBIC'),('SOLCAE'),('SOLCAN'),('SOLFLE'),('SOLGIG'),('SOLHIS'),('SOLJUN'),('SOLPAT'),('SOLRIG'),('SOLRUG'),('SOLIDA'),('SONASP'),('SORAUC'),('SORBUS'),('SORNUT'),('SPAEUR'),('SPHOBTO'),('SPIALA'),('SPIJAP'),('STATEN'),('STEMED'),('STELLA'),('SYMORB'),('SYMFOE'),('SYRVUL'),('TAROFF'),('TARAXA'),('TAXCAN'),('TEUCAN'),('THADAS'),('THAPUB'),('THALIC'),('THATRI'),('THEHEX'),('THENOV'),('THEPAL'),('THEPHE'),('TIACOR'),('TILAME'),('TILIA'),('TORARV'),('TORJAP'),('TORILI'),('TOXRAD'),('TRAPRA'),('TRIBOR'),('TRFAUR'),('TRFHYB'),('TRFPRA'),('TRFREP'),('TRIFOL'),('TRLCER'),('TRLGRA'),('TRLSES'),('TRILLI'),('TRIANG'),('TSUCAN'),('TUSFAR'),('TYPANG'),('TYPLAT'),('TYPHA'),('ULMAME'),('ULMPUM'),('ULMRUB'),('ULMUS'),('URTDIOD'),('URTIDIOP'),('UVUGRA'),('UVUPER'),('VACPAL'),('VACCIN'),('VERBLA'),('VERTHA'),('VERHAS'),('VERBEN'),('VERURT'),('VERALT'),('VERGIG'),('VERNON'),('VEROFF'),('VERSER'),('VERONI'),('VIBACE'),('VIBALN'),('VIBDEN'),('VIBLEN'),('VIBOPUO'),('VIBPLI'),('VIBPRU'),('VIBREC'),('VIBURN'),('VICTET'),('VINMIN'),('VIOCAN'),('VIOCON'),('VIOHAS'),('VIOPUB'),('VIOSOR'),('VIOLA'),('VIOSTR'),('VITAES'),('VITLAB'),('VITRIP'),('VITIS'),('WALFRA'),('XANSTR'),('ZIZAUR'),('ZIZIA'),('LAPCAN'),('TRIAUR')
$$) 
        AS ("plot_id" text, "ACARHO" numeric,"ACENEG" numeric,"ACENIG" numeric,"ACEPLA" numeric,"ACERUB" numeric,"ACESAC" numeric,"ACESAR" numeric,"ACER" numeric,"ACHMIL" numeric,"ACOCAL" numeric,"ACTALB" numeric,"ACTAEA" numeric,"ACTARG" numeric,"ADIPED" numeric,"AEGPOD" numeric,"AESGLA" numeric,"AESHIP" numeric,"AESCUL" numeric,"AGRGRY" numeric,"AGRPAR" numeric,"AGRPUB" numeric,"AGRIMO" numeric,"AGRSTR" numeric,"AGRCAP" numeric,"AGRGIG" numeric,"AGRHYE" numeric,"AGRPER" numeric,"AGROST" numeric,"AGRSTO" numeric,"AILALT" numeric,"AJUREP" numeric,"ALISUB" numeric,"ALLPET" numeric,"ALLCAN" numeric,"ALLCER" numeric,"ALLSAT" numeric,"ALLIUM" numeric,"ALLTRI" numeric,"ALLVIN" numeric,"ALNGLU" numeric,"ALNINC" numeric,"ALNUS" numeric,"AMBART" numeric,"AMBTRI" numeric,"AMEARB" numeric,"AMELAN" numeric,"AMPBRE" numeric,"AMPBRA" numeric,"ANAARV" numeric,"ANDGER" numeric,"ANDVIRV" numeric,"ANEQUI" numeric,"ANEVIR" numeric,"ANETHA" numeric,"ANGATR" numeric,"ANTARI" numeric,"ANTODO" numeric,"APIACE" numeric,"APIAME" numeric,"APOCAN" numeric,"APOCYN" numeric,"ARACAN" numeric,"ARALAE" numeric,"ARANUD" numeric,"ARARAC" numeric,"ARCLAP" numeric,"ARCMIN" numeric,"ARCTIUM" numeric,"ARIDRA" numeric,"ARITRIT" numeric,"ARONIA" numeric,"ARTVUL" numeric,"ARTEMS" numeric,"ASACAN" numeric,"ASCINC" numeric,"ASCLEP" numeric,"ASCSUL" numeric,"ASCSYR" numeric,"ASCTUB" numeric,"ASITRI" numeric,"ASPPLA" numeric,"ASTCOR" numeric,"ASTLAN" numeric,"ASTLAT" numeric,"ASTLIN" numeric,"ASTMAC" numeric,"ASTNOV" numeric,"ASTPIL" numeric,"ASTPUN" numeric,"ASTER" numeric,"ASTUMB" numeric,"ATHFEL" numeric,"ATHTHE" numeric,"BARBAR" numeric,"BARVER" numeric,"BARVUL" numeric,"BERTHU" numeric,"BETALL" numeric,"BETLEN" numeric,"BETPAP" numeric,"BETPOP" numeric,"BETULA" numeric,"BIDFRO" numeric,"BIDENS" numeric,"BLECIL" numeric,"BLEHIR" numeric,"BLEPHI" numeric,"BOECYL" numeric,"BOTRYC" numeric,"BOUCUR" numeric,"BRAERE" numeric,"BRASSI" numeric,"BROCOM" numeric,"BROINE" numeric,"BROALT" numeric,"BROPUB" numeric,"BROMUS" numeric,"CLTPAL" numeric,"CALSEP" numeric,"CALYST" numeric,"CAMSCI" numeric,"CAMRAD" numeric,"CARDIP" numeric,"CARDOU" numeric,"CARHIR" numeric,"CARPAR" numeric,"CARPEN" numeric,"CARDAM" numeric,"CXALBU" numeric,"CXAMPH" numeric,"CXANNE" numeric,"CXAPPA" numeric,"CXARCT" numeric,"CXBLAN" numeric,"CXBROM" numeric,"CXCARE" numeric,"CXCPHLA" numeric,"CXCOMM" numeric,"CXCOMP" numeric,"CXCONJ" numeric,"CXCRINC" numeric,"CXCRIS" numeric,"CXDEBIR" numeric,"CXDIGI" numeric,"CXEMOR" numeric,"CXFEST" numeric,"CXFRAN" numeric,"CXGLAU" numeric,"CXGRCLE" numeric,"CXGRCLI" numeric,"CXGRAN" numeric,"CXGRAY" numeric,"CXGRIS" numeric,"CXHIRS" numeric,"CXHIRT" numeric,"CXINTE" numeric,"CXINTU" numeric,"CXJAME" numeric,"CXLACU" numeric,"CXLAEV" numeric,"CXLAXC" numeric,"CXLAXF" numeric,"CXLPTON" numeric,"CXLUPL" numeric,"CXLURI" numeric,"CXNORM" numeric,"CXOLIGC" numeric,"CXPALL" numeric,"CXPENS" numeric,"CXPLNT" numeric,"CXPRAS" numeric,"CXRADI" numeric,"CXROSE" numeric,"CXSCOP" numeric,"CAREX" numeric,"CXSPAR" numeric,"CXSTIP" numeric,"CXSWAN" numeric,"CXTRIB" numeric,"CXVIRE" numeric,"CXVULP" numeric,"CXWILL" numeric,"CXWOOD" numeric,"CRPCAR" numeric,"CARCOR" numeric,"CARGLA" numeric,"CARLAC" numeric,"CAROVT" numeric,"CARYA" numeric,"CARTOM" numeric,"CASDEN" numeric,"CATBIG" numeric,"CATALP" numeric,"CATSPE" numeric,"CAUTHA" numeric,"CELORB" numeric,"CELOCC" numeric,"CENJAC" numeric,"CENTAU" numeric,"CENPUL" numeric,"CEPOCC" numeric,"CERAST" numeric,"CERVUL" numeric,"CERCAN" numeric,"CHEGLA" numeric,"CHEALB" numeric,"CHENOP" numeric,"CHRLEU" numeric,"CHRAME" numeric,"CICINT" numeric,"CICMAC" numeric,"CIMRAC" numeric,"CINARU" numeric,"CIRLUT" numeric,"CIRALT" numeric,"CIRARV" numeric,"CIRMUT" numeric,"CIRSIU" numeric,"CIRVUL" numeric,"CLEVIR" numeric,"CLIVUL" numeric,"COLCAN" numeric,"CONMACU" numeric,"CONAME" numeric,"CONMAJ" numeric,"CONARV" numeric,"CONCAN" numeric,"CORTRP" numeric,"CORALT" numeric,"CORAMO" numeric,"CORFLO" numeric,"CORRAC" numeric,"CORSER" numeric,"CORNUS" numeric,"CORVAR" numeric,"CRACRU" numeric,"CRAMON" numeric,"CRATAE" numeric,"CRYCAN" numeric,"CUCMAX" numeric,"CUSGRO" numeric,"CUSCUT" numeric,"CYPERY" numeric,"CYPESC" numeric,"CYPSTR" numeric,"CYSFRA" numeric,"CYSPRO" numeric,"DACGLO" numeric,"DANCOM" numeric,"DANTHO" numeric,"DANSPI" numeric,"DAUCAR" numeric,"DAUCUS" numeric,"DESCNA" numeric,"DESCUS" numeric,"DESGLA" numeric,"DESPAN" numeric,"DESMOD" numeric,"DIAARM" numeric,"DIGITI" numeric,"DIGISC" numeric,"DIGSAN" numeric,"DIGITA" numeric,"DIOVIL" numeric,"DIPFUL" numeric,"DIPSAC" numeric,"DRYCAR" numeric,"DRYCRI" numeric,"DRYGOL" numeric,"DRYINT" numeric,"DRYMAR" numeric,"DRYOPT" numeric,"DUCIND" numeric,"DULARU" numeric,"ECHPUR" numeric,"ECHCRU" numeric,"ECHMUR" numeric,"ECHINO" numeric,"ECHLOB" numeric,"ELAANG" numeric,"ELAUMB" numeric,"ELEACI" numeric,"ELEOBT" numeric,"ELEPAL" numeric,"ELYCAN" numeric,"ELYHYS" numeric,"ELYMAC" numeric,"ELYRIP" numeric,"ELYMUS" numeric,"ELYVIL" numeric,"ELYVIR" numeric,"ELYREP" numeric,"EPIVIR" numeric,"EPICIL" numeric,"EPICOL" numeric,"EPIHIR" numeric,"EPIPAR" numeric,"EPILOB" numeric,"EPIHEL" numeric,"EQUARV" numeric,"EQUHYE" numeric,"EQUISE" numeric,"EREHIE" numeric,"ERIANN" numeric,"ERIPHI" numeric,"ERIGER" numeric,"ERISTR" numeric,"EUOALA" numeric,"EUOATR" numeric,"EUOEUR" numeric,"EUOFOR" numeric,"EUOOBO" numeric,"EUONYM" numeric,"EUPARO" numeric,"EUPMAC" numeric,"EUPPER" numeric,"EUPPUR" numeric,"EUPRUG" numeric,"EUPSER" numeric,"EUPTOR" numeric,"EUPHMA" numeric,"EUPHOR" numeric,"EUTGRA" numeric,"FAGGRA" numeric,"FESELA" numeric,"FESOVI" numeric,"FESPRA" numeric,"FESRUB" numeric,"FESTUC" numeric,"FESSUB" numeric,"FESTRA" numeric,"FORINT" numeric,"FRAGAR" numeric,"FRAVESA" numeric,"FRAVESV" numeric,"FRAVIR" numeric,"FRAAME" numeric,"FRANIG" numeric,"FRAPEN" numeric,"FRAPRO" numeric,"FRAXIN" numeric,"GALQUA" numeric,"GALAPA" numeric,"GALASP" numeric,"GALCIR" numeric,"GALCON" numeric,"GALLAN" numeric,"GALMOL" numeric,"GALOBT" numeric,"GALODO" numeric,"GALPAL" numeric,"GALIUM" numeric,"GALTIN" numeric,"GALTFL" numeric,"GAUPRO" numeric,"GERMAC" numeric,"GERROB" numeric,"GERANI" numeric,"GEUCAN" numeric,"GEULAC" numeric,"GEUM" numeric,"GEUVER" numeric,"GEUVIR" numeric,"GINBIL" numeric,"GLEHED" numeric,"GLETRI" numeric,"GLYSEP" numeric,"GLYCER" numeric,"GLYSTR" numeric,"HACVIR" numeric,"HAMVIR" numeric,"HEDPUL" numeric,"HEDHEL" numeric,"HEDCAE" numeric,"HELAUT" numeric,"HELDEC" numeric,"HELIAN" numeric,"HELHEL" numeric,"HEMFUL" numeric,"HEPACU" numeric,"HERLAN" numeric,"HESMAT" numeric,"HEUAME" numeric,"HIECAE" numeric,"HIEGRO" numeric,"HIEPAN" numeric,"HIERAC" numeric,"HOLLAN" numeric,"HUMJAP" numeric,"HDRCAN" numeric,"HYDCAN" numeric,"HYDROP" numeric,"HYDVIR" numeric,"HYPMUT" numeric,"HYPPER" numeric,"HYPPRO" numeric,"HYPPUN" numeric,"HYPERI" numeric,"HYPRAD" numeric,"ILEOPA" numeric,"IMPCAP" numeric,"IMPPAL" numeric,"IMPATI" numeric,"IPOMOE" numeric,"IRIPSE" numeric,"IRIS" numeric,"ISOBIT" numeric,"JEFDIP" numeric,"JUGNIG" numeric,"JUNEFF" numeric,"JUNMAR" numeric,"JUNCUS" numeric,"JUNTEN" numeric,"JUNIPE" numeric,"LACBIE" numeric,"LACCAN" numeric,"LACFLO" numeric,"LACTUC" numeric,"LAPCOM" numeric,"LEEORY" numeric,"LEEVIR" numeric,"LEMMIN" numeric,"LEOAUT" numeric,"LEOCAR" numeric,"LIATRIS" numeric,"LIASPI" numeric,"LICHEN" numeric,"LIGVUL" numeric,"LILCAN" numeric,"LINBEN" numeric,"LIQSTY" numeric,"LIRTUL" numeric,"LOBINF" numeric,"LOBSIP" numeric,"LOBELI" numeric,"LOLPER" numeric,"LOLSP" numeric,"LONDIO" numeric,"LONJAP" numeric,"LONMAA" numeric,"LONMOR" numeric,"LONSEM" numeric,"LONICE" numeric,"LONTAT" numeric,"LOTCOR" numeric,"LUDPAL" numeric,"LUNANN" numeric,"LUZACU" numeric,"LUZMUL" numeric,"LUZULA" numeric,"LYCOPO" numeric,"LYCAME" numeric,"LYCOPU" numeric,"LYCUNI" numeric,"LYCVIR" numeric,"LYSCIL" numeric,"LYSNUM" numeric,"LYSTER" numeric,"LYTSAL" numeric,"MACPOM" numeric,"MAGACU" numeric,"MAICAN" numeric,"MAIRAC" numeric,"MAIANT" numeric,"MATSTR" numeric,"MEDLUP" numeric,"MELALB" numeric,"MELOFF" numeric,"MELILO" numeric,"MLIOFF" numeric,"MENCAN" numeric,"MENARV" numeric,"MENTHA" numeric,"MENSPI" numeric,"MENPIP" numeric,"METGLY" numeric,"MICVIM" numeric,"MILEFF" numeric,"MIMALA" numeric,"MIMRIN" numeric,"MITREP" numeric,"MITDIP" numeric,"MONCLI" numeric,"MONFIS" numeric,"MONOTR" numeric,"MNTUNI" numeric,"MORALB" numeric,"MORUS" numeric,"MOSS" numeric,"MUHFRO" numeric,"MUHSCH" numeric,"MUHTEN" numeric,"MYOLAX" numeric,"MYOSCO" numeric,"MYOSOT" numeric,"NARCIS" numeric,"NELLUT" numeric,"NEPCAT" numeric,"NUPADV" numeric,"NYSSYL" numeric,"OENBIE" numeric,"ONOSEN" numeric,"OSMOCL" numeric,"OSMOLO" numeric,"OSMCIN" numeric,"OSMCLA" numeric,"OSTVIR" numeric,"OXALIS" numeric,"OXASTR" numeric,"PACTER" numeric,"PANCLA" numeric,"PANFLE" numeric,"PANLAN" numeric,"PANLAT" numeric,"PANLIN" numeric,"PANICU" numeric,"PANVIR" numeric,"PARQUI" numeric,"PARVIT" numeric,"PELVIR" numeric,"PENDIG" numeric,"PENSTE" numeric,"PENSED" numeric,"PETHYB" numeric,"PHAARU" numeric,"PHICOR" numeric,"PHLPRA" numeric,"PHLDIV" numeric,"PHRAUSAM" numeric,"PHRAUSAU" numeric,"PHYOPU" numeric,"PHYAME" numeric,"PICABI" numeric,"PICEA" numeric,"PILPUM" numeric,"PILEA" numeric,"PINNIG" numeric,"PINRES" numeric,"PINSPP" numeric,"PINSTRN" numeric,"PINSYL" numeric,"PLANLA" numeric,"PLANMA" numeric,"PLANRU" numeric,"PLANTA" numeric,"PLAOCC" numeric,"POAALS" numeric,"POAANN" numeric,"POACOM" numeric,"POAPAL" numeric,"POAPRA" numeric,"POA" numeric,"POASYL" numeric,"POATRI" numeric,"POACEA" numeric,"PODPEL" numeric,"POLREP" numeric,"POLBIF" numeric,"POLPUB" numeric,"POLYGO" numeric,"PLGARI" numeric,"PLGAVI" numeric,"PLGCES" numeric,"PLGCON" numeric,"PLGCUS" numeric,"PLGHPR" numeric,"PLGHPO" numeric,"PLGLAP" numeric,"PLGPER" numeric,"PLGPUN" numeric,"PLGSAG" numeric,"PLGSCA" numeric,"POLYGN" numeric,"PLGVIR" numeric,"POLVIG" numeric,"POLACR" numeric,"POPALB" numeric,"POPDEL" numeric,"POPGRA" numeric,"POPULU" numeric,"POROLE" numeric,"POTNOR" numeric,"POTREC" numeric,"POTSIM" numeric,"POTENT" numeric,"PREALB" numeric,"PREALT" numeric,"PRESER" numeric,"PRENAN" numeric,"PROLAN" numeric,"PRUVUL" numeric,"PRUCER" numeric,"PRUSER" numeric,"PRUNUS" numeric,"PRUVIR" numeric,"PTEAQU" numeric,"PYCMUT" numeric,"PYCNAN" numeric,"PYCTEN" numeric,"PYRANG" numeric,"PYRCOM" numeric,"PYRCOR" numeric,"PYRIOE" numeric,"PYRMAL" numeric,"PYRUS" numeric,"QUEALB" numeric,"QUEBIC" numeric,"QUECOC" numeric,"QUEMAC" numeric,"QUEMUE" numeric,"QUEPAL" numeric,"QUERUB" numeric,"QUERCU" numeric,"QUEVEL" numeric,"RANABO" numeric,"RANACR" numeric,"RANALL" numeric,"RANFIC" numeric,"RANHIS" numeric,"RANREC" numeric,"RANREP" numeric,"RANUNC" numeric,"RAPRAP" numeric,"RATPIN" numeric,"RHACAT" numeric,"RHAFRA" numeric,"RHAMNU" numeric,"RHODOD" numeric,"RHUS" numeric,"RHUTYP" numeric,"RIBAME" numeric,"RIBCYN" numeric,"RIBODO" numeric,"RIBES" numeric,"RICFLU" numeric,"ROBPSE" numeric,"RORIPP" numeric,"ROSCAR" numeric,"ROSMUL" numeric,"ROSPAL" numeric,"ROSSET" numeric,"ROSA" numeric,"RUBALL" numeric,"RUBCAE" numeric,"RUBFLA" numeric,"RUBHIS" numeric,"RUBIDAS" numeric,"RUBOCC" numeric,"RUBODO" numeric,"RUBPEN" numeric,"RUBPHO" numeric,"RUBUS" numeric,"RUDFUL" numeric,"RUDLAC" numeric,"RUDBEC" numeric,"RUMACE" numeric,"RUMCRI" numeric,"RUMOBT" numeric,"RUMEX" numeric,"SAGLAT" numeric,"SLXDIS" numeric,"SLXFRA" numeric,"SLXNIG" numeric,"SALIX" numeric,"SAMCAN" numeric,"SAMPUB" numeric,"SAMBUC" numeric,"SANCAN" numeric,"SANICAN" numeric,"SANIGR" numeric,"SANIMA" numeric,"SANICU" numeric,"SANITR" numeric,"SAPOFF" numeric,"SASALB" numeric,"SCHSCOS" numeric,"SCHTAB" numeric,"SCIATR" numeric,"SCICYP" numeric,"SCIPOL" numeric,"SCIRPU" numeric,"SCRMAR" numeric,"SCROPH" numeric,"SCUINT" numeric,"SCULAT" numeric,"SCUTEL" numeric,"SEDUM" numeric,"SEDTER" numeric,"SENAUR" numeric,"SENOBO" numeric,"SENECI" numeric,"SETFAB" numeric,"SETARI" numeric,"SETVIR" numeric,"SICANG" numeric,"SLPPER" numeric,"SISOFF" numeric,"SISANG" numeric,"SISMON" numeric,"SISYRI" numeric,"SMXHIS" numeric,"SMXROT" numeric,"SMILAX" numeric,"SLMCAR" numeric,"SLMDUL" numeric,"SLMNIG" numeric,"SOLBIC" numeric,"SOLCAE" numeric,"SOLCAN" numeric,"SOLFLE" numeric,"SOLGIG" numeric,"SOLHIS" numeric,"SOLJUN" numeric,"SOLPAT" numeric,"SOLRIG" numeric,"SOLRUG" numeric,"SOLIDA" numeric,"SONASP" numeric,"SORAUC" numeric,"SORBUS" numeric,"SORNUT" numeric,"SPAEUR" numeric,"SPHOBTO" numeric,"SPIALA" numeric,"SPIJAP" numeric,"STATEN" numeric,"STEMED" numeric,"STELLA" numeric,"SYMORB" numeric,"SYMFOE" numeric,"SYRVUL" numeric,"TAROFF" numeric,"TARAXA" numeric,"TAXCAN" numeric,"TEUCAN" numeric,"THADAS" numeric,"THAPUB" numeric,"THALIC" numeric,"THATRI" numeric,"THEHEX" numeric,"THENOV" numeric,"THEPAL" numeric,"THEPHE" numeric,"TIACOR" numeric,"TILAME" numeric,"TILIA" numeric,"TORARV" numeric,"TORJAP" numeric,"TORILI" numeric,"TOXRAD" numeric,"TRAPRA" numeric,"TRIBOR" numeric,"TRFAUR" numeric,"TRFHYB" numeric,"TRFPRA" numeric,"TRFREP" numeric,"TRIFOL" numeric,"TRLCER" numeric,"TRLGRA" numeric,"TRLSES" numeric,"TRILLI" numeric,"TRIANG" numeric,"TSUCAN" numeric,"TUSFAR" numeric,"TYPANG" numeric,"TYPLAT" numeric,"TYPHA" numeric,"ULMAME" numeric,"ULMPUM" numeric,"ULMRUB" numeric,"ULMUS" numeric,"URTDIOD" numeric,"URTIDIOP" numeric,"UVUGRA" numeric,"UVUPER" numeric,"VACPAL" numeric,"VACCIN" numeric,"VERBLA" numeric,"VERTHA" numeric,"VERHAS" numeric,"VERBEN" numeric,"VERURT" numeric,"VERALT" numeric,"VERGIG" numeric,"VERNON" numeric,"VEROFF" numeric,"VERSER" numeric,"VERONI" numeric,"VIBACE" numeric,"VIBALN" numeric,"VIBDEN" numeric,"VIBLEN" numeric,"VIBOPUO" numeric,"VIBPLI" numeric,"VIBPRU" numeric,"VIBREC" numeric,"VIBURN" numeric,"VICTET" numeric,"VINMIN" numeric,"VIOCAN" numeric,"VIOCON" numeric,"VIOHAS" numeric,"VIOPUB" numeric,"VIOSOR" numeric,"VIOLA" numeric,"VIOSTR" numeric,"VITAES" numeric,"VITLAB" numeric,"VITRIP" numeric,"VITIS" numeric,"WALFRA" numeric,"XANSTR" numeric,"ZIZAUR" numeric,"ZIZIA" numeric,"LAPCAN" numeric,"TRIAUR" numeric

));


CREATE MATERIALIZED VIEW woody_plot_x_species_matrix AS (SELECT * FROM crosstab(                 
       'SELECT plot_id, acronym, iv
        FROM   woody_iv_acronym_joined
        ORDER  BY 1'
        ,$$VALUES ('ACARHO'),('ACENEG'),('ACENIG'),('ACEPLA'),('ACERUB'),('ACESAC'),('ACESAR'),('ACER'),('ACHMIL'),('ACOCAL'),('ACTALB'),('ACTAEA'),('ACTARG'),('ADIPED'),('AEGPOD'),('AESGLA'),('AESHIP'),('AESCUL'),('AGRGRY'),('AGRPAR'),('AGRPUB'),('AGRIMO'),('AGRSTR'),('AGRCAP'),('AGRGIG'),('AGRHYE'),('AGRPER'),('AGROST'),('AGRSTO'),('AILALT'),('AJUREP'),('ALISUB'),('ALLPET'),('ALLCAN'),('ALLCER'),('ALLSAT'),('ALLIUM'),('ALLTRI'),('ALLVIN'),('ALNGLU'),('ALNINC'),('ALNUS'),('AMBART'),('AMBTRI'),('AMEARB'),('AMELAN'),('AMPBRE'),('AMPBRA'),('ANAARV'),('ANDGER'),('ANDVIRV'),('ANEQUI'),('ANEVIR'),('ANETHA'),('ANGATR'),('ANTARI'),('ANTODO'),('APIACE'),('APIAME'),('APOCAN'),('APOCYN'),('ARACAN'),('ARALAE'),('ARANUD'),('ARARAC'),('ARCLAP'),('ARCMIN'),('ARCTIUM'),('ARIDRA'),('ARITRIT'),('ARONIA'),('ARTVUL'),('ARTEMS'),('ASACAN'),('ASCINC'),('ASCLEP'),('ASCSUL'),('ASCSYR'),('ASCTUB'),('ASITRI'),('ASPPLA'),('ASTCOR'),('ASTLAN'),('ASTLAT'),('ASTLIN'),('ASTMAC'),('ASTNOV'),('ASTPIL'),('ASTPUN'),('ASTER'),('ASTUMB'),('ATHFEL'),('ATHTHE'),('BARBAR'),('BARVER'),('BARVUL'),('BERTHU'),('BETALL'),('BETLEN'),('BETPAP'),('BETPOP'),('BETULA'),('BIDFRO'),('BIDENS'),('BLECIL'),('BLEHIR'),('BLEPHI'),('BOECYL'),('BOTRYC'),('BOUCUR'),('BRAERE'),('BRASSI'),('BROCOM'),('BROINE'),('BROALT'),('BROPUB'),('BROMUS'),('CLTPAL'),('CALSEP'),('CALYST'),('CAMSCI'),('CAMRAD'),('CARDIP'),('CARDOU'),('CARHIR'),('CARPAR'),('CARPEN'),('CARDAM'),('CXALBU'),('CXAMPH'),('CXANNE'),('CXAPPA'),('CXARCT'),('CXBLAN'),('CXBROM'),('CXCARE'),('CXCPHLA'),('CXCOMM'),('CXCOMP'),('CXCONJ'),('CXCRINC'),('CXCRIS'),('CXDEBIR'),('CXDIGI'),('CXEMOR'),('CXFEST'),('CXFRAN'),('CXGLAU'),('CXGRCLE'),('CXGRCLI'),('CXGRAN'),('CXGRAY'),('CXGRIS'),('CXHIRS'),('CXHIRT'),('CXINTE'),('CXINTU'),('CXJAME'),('CXLACU'),('CXLAEV'),('CXLAXC'),('CXLAXF'),('CXLPTON'),('CXLUPL'),('CXLURI'),('CXNORM'),('CXOLIGC'),('CXPALL'),('CXPENS'),('CXPLNT'),('CXPRAS'),('CXRADI'),('CXROSE'),('CXSCOP'),('CAREX'),('CXSPAR'),('CXSTIP'),('CXSWAN'),('CXTRIB'),('CXVIRE'),('CXVULP'),('CXWILL'),('CXWOOD'),('CRPCAR'),('CARCOR'),('CARGLA'),('CARLAC'),('CAROVT'),('CARYA'),('CARTOM'),('CASDEN'),('CATBIG'),('CATALP'),('CATSPE'),('CAUTHA'),('CELORB'),('CELOCC'),('CENJAC'),('CENTAU'),('CENPUL'),('CEPOCC'),('CERAST'),('CERVUL'),('CERCAN'),('CHEGLA'),('CHEALB'),('CHENOP'),('CHRLEU'),('CHRAME'),('CICINT'),('CICMAC'),('CIMRAC'),('CINARU'),('CIRLUT'),('CIRALT'),('CIRARV'),('CIRMUT'),('CIRSIU'),('CIRVUL'),('CLEVIR'),('CLIVUL'),('COLCAN'),('CONMACU'),('CONAME'),('CONMAJ'),('CONARV'),('CONCAN'),('CORTRP'),('CORALT'),('CORAMO'),('CORFLO'),('CORRAC'),('CORSER'),('CORNUS'),('CORVAR'),('CRACRU'),('CRAMON'),('CRATAE'),('CRYCAN'),('CUCMAX'),('CUSGRO'),('CUSCUT'),('CYPERY'),('CYPESC'),('CYPSTR'),('CYSFRA'),('CYSPRO'),('DACGLO'),('DANCOM'),('DANTHO'),('DANSPI'),('DAUCAR'),('DAUCUS'),('DESCNA'),('DESCUS'),('DESGLA'),('DESPAN'),('DESMOD'),('DIAARM'),('DIGITI'),('DIGISC'),('DIGSAN'),('DIGITA'),('DIOVIL'),('DIPFUL'),('DIPSAC'),('DRYCAR'),('DRYCRI'),('DRYGOL'),('DRYINT'),('DRYMAR'),('DRYOPT'),('DUCIND'),('DULARU'),('ECHPUR'),('ECHCRU'),('ECHMUR'),('ECHINO'),('ECHLOB'),('ELAANG'),('ELAUMB'),('ELEACI'),('ELEOBT'),('ELEPAL'),('ELYCAN'),('ELYHYS'),('ELYMAC'),('ELYRIP'),('ELYMUS'),('ELYVIL'),('ELYVIR'),('ELYREP'),('EPIVIR'),('EPICIL'),('EPICOL'),('EPIHIR'),('EPIPAR'),('EPILOB'),('EPIHEL'),('EQUARV'),('EQUHYE'),('EQUISE'),('EREHIE'),('ERIANN'),('ERIPHI'),('ERIGER'),('ERISTR'),('EUOALA'),('EUOATR'),('EUOEUR'),('EUOFOR'),('EUOOBO'),('EUONYM'),('EUPARO'),('EUPMAC'),('EUPPER'),('EUPPUR'),('EUPRUG'),('EUPSER'),('EUPTOR'),('EUPHMA'),('EUPHOR'),('EUTGRA'),('FAGGRA'),('FESELA'),('FESOVI'),('FESPRA'),('FESRUB'),('FESTUC'),('FESSUB'),('FESTRA'),('FORINT'),('FRAGAR'),('FRAVESA'),('FRAVESV'),('FRAVIR'),('FRAAME'),('FRANIG'),('FRAPEN'),('FRAPRO'),('FRAXIN'),('GALQUA'),('GALAPA'),('GALASP'),('GALCIR'),('GALCON'),('GALLAN'),('GALMOL'),('GALOBT'),('GALODO'),('GALPAL'),('GALIUM'),('GALTIN'),('GALTFL'),('GAUPRO'),('GERMAC'),('GERROB'),('GERANI'),('GEUCAN'),('GEULAC'),('GEUM'),('GEUVER'),('GEUVIR'),('GINBIL'),('GLEHED'),('GLETRI'),('GLYSEP'),('GLYCER'),('GLYSTR'),('HACVIR'),('HAMVIR'),('HEDPUL'),('HEDHEL'),('HEDCAE'),('HELAUT'),('HELDEC'),('HELIAN'),('HELHEL'),('HEMFUL'),('HEPACU'),('HERLAN'),('HESMAT'),('HEUAME'),('HIECAE'),('HIEGRO'),('HIEPAN'),('HIERAC'),('HOLLAN'),('HUMJAP'),('HDRCAN'),('HYDCAN'),('HYDROP'),('HYDVIR'),('HYPMUT'),('HYPPER'),('HYPPRO'),('HYPPUN'),('HYPERI'),('HYPRAD'),('ILEOPA'),('IMPCAP'),('IMPPAL'),('IMPATI'),('IPOMOE'),('IRIPSE'),('IRIS'),('ISOBIT'),('JEFDIP'),('JUGNIG'),('JUNEFF'),('JUNMAR'),('JUNCUS'),('JUNTEN'),('JUNIPE'),('LACBIE'),('LACCAN'),('LACFLO'),('LACTUC'),('LAPCOM'),('LEEORY'),('LEEVIR'),('LEMMIN'),('LEOAUT'),('LEOCAR'),('LIATRIS'),('LIASPI'),('LICHEN'),('LIGVUL'),('LILCAN'),('LINBEN'),('LIQSTY'),('LIRTUL'),('LOBINF'),('LOBSIP'),('LOBELI'),('LOLPER'),('LOLSP'),('LONDIO'),('LONJAP'),('LONMAA'),('LONMOR'),('LONSEM'),('LONICE'),('LONTAT'),('LOTCOR'),('LUDPAL'),('LUNANN'),('LUZACU'),('LUZMUL'),('LUZULA'),('LYCOPO'),('LYCAME'),('LYCOPU'),('LYCUNI'),('LYCVIR'),('LYSCIL'),('LYSNUM'),('LYSTER'),('LYTSAL'),('MACPOM'),('MAGACU'),('MAICAN'),('MAIRAC'),('MAIANT'),('MATSTR'),('MEDLUP'),('MELALB'),('MELOFF'),('MELILO'),('MLIOFF'),('MENCAN'),('MENARV'),('MENTHA'),('MENSPI'),('MENPIP'),('METGLY'),('MICVIM'),('MILEFF'),('MIMALA'),('MIMRIN'),('MITREP'),('MITDIP'),('MONCLI'),('MONFIS'),('MONOTR'),('MNTUNI'),('MORALB'),('MORUS'),('MOSS'),('MUHFRO'),('MUHSCH'),('MUHTEN'),('MYOLAX'),('MYOSCO'),('MYOSOT'),('NARCIS'),('NELLUT'),('NEPCAT'),('NUPADV'),('NYSSYL'),('OENBIE'),('ONOSEN'),('OSMOCL'),('OSMOLO'),('OSMCIN'),('OSMCLA'),('OSTVIR'),('OXALIS'),('OXASTR'),('PACTER'),('PANCLA'),('PANFLE'),('PANLAN'),('PANLAT'),('PANLIN'),('PANICU'),('PANVIR'),('PARQUI'),('PARVIT'),('PELVIR'),('PENDIG'),('PENSTE'),('PENSED'),('PETHYB'),('PHAARU'),('PHICOR'),('PHLPRA'),('PHLDIV'),('PHRAUSAM'),('PHRAUSAU'),('PHYOPU'),('PHYAME'),('PICABI'),('PICEA'),('PILPUM'),('PILEA'),('PINNIG'),('PINRES'),('PINSPP'),('PINSTRN'),('PINSYL'),('PLANLA'),('PLANMA'),('PLANRU'),('PLANTA'),('PLAOCC'),('POAALS'),('POAANN'),('POACOM'),('POAPAL'),('POAPRA'),('POA'),('POASYL'),('POATRI'),('POACEA'),('PODPEL'),('POLREP'),('POLBIF'),('POLPUB'),('POLYGO'),('PLGARI'),('PLGAVI'),('PLGCES'),('PLGCON'),('PLGCUS'),('PLGHPR'),('PLGHPO'),('PLGLAP'),('PLGPER'),('PLGPUN'),('PLGSAG'),('PLGSCA'),('POLYGN'),('PLGVIR'),('POLVIG'),('POLACR'),('POPALB'),('POPDEL'),('POPGRA'),('POPULU'),('POROLE'),('POTNOR'),('POTREC'),('POTSIM'),('POTENT'),('PREALB'),('PREALT'),('PRESER'),('PRENAN'),('PROLAN'),('PRUVUL'),('PRUCER'),('PRUSER'),('PRUNUS'),('PRUVIR'),('PTEAQU'),('PYCMUT'),('PYCNAN'),('PYCTEN'),('PYRANG'),('PYRCOM'),('PYRCOR'),('PYRIOE'),('PYRMAL'),('PYRUS'),('QUEALB'),('QUEBIC'),('QUECOC'),('QUEMAC'),('QUEMUE'),('QUEPAL'),('QUERUB'),('QUERCU'),('QUEVEL'),('RANABO'),('RANACR'),('RANALL'),('RANFIC'),('RANHIS'),('RANREC'),('RANREP'),('RANUNC'),('RAPRAP'),('RATPIN'),('RHACAT'),('RHAFRA'),('RHAMNU'),('RHODOD'),('RHUS'),('RHUTYP'),('RIBAME'),('RIBCYN'),('RIBODO'),('RIBES'),('RICFLU'),('ROBPSE'),('RORIPP'),('ROSCAR'),('ROSMUL'),('ROSPAL'),('ROSSET'),('ROSA'),('RUBALL'),('RUBCAE'),('RUBFLA'),('RUBHIS'),('RUBIDAS'),('RUBOCC'),('RUBODO'),('RUBPEN'),('RUBPHO'),('RUBUS'),('RUDFUL'),('RUDLAC'),('RUDBEC'),('RUMACE'),('RUMCRI'),('RUMOBT'),('RUMEX'),('SAGLAT'),('SLXDIS'),('SLXFRA'),('SLXNIG'),('SALIX'),('SAMCAN'),('SAMPUB'),('SAMBUC'),('SANCAN'),('SANICAN'),('SANIGR'),('SANIMA'),('SANICU'),('SANITR'),('SAPOFF'),('SASALB'),('SCHSCOS'),('SCHTAB'),('SCIATR'),('SCICYP'),('SCIPOL'),('SCIRPU'),('SCRMAR'),('SCROPH'),('SCUINT'),('SCULAT'),('SCUTEL'),('SEDUM'),('SEDTER'),('SENAUR'),('SENOBO'),('SENECI'),('SETFAB'),('SETARI'),('SETVIR'),('SICANG'),('SLPPER'),('SISOFF'),('SISANG'),('SISMON'),('SISYRI'),('SMXHIS'),('SMXROT'),('SMILAX'),('SLMCAR'),('SLMDUL'),('SLMNIG'),('SOLBIC'),('SOLCAE'),('SOLCAN'),('SOLFLE'),('SOLGIG'),('SOLHIS'),('SOLJUN'),('SOLPAT'),('SOLRIG'),('SOLRUG'),('SOLIDA'),('SONASP'),('SORAUC'),('SORBUS'),('SORNUT'),('SPAEUR'),('SPHOBTO'),('SPIALA'),('SPIJAP'),('STATEN'),('STEMED'),('STELLA'),('SYMORB'),('SYMFOE'),('SYRVUL'),('TAROFF'),('TARAXA'),('TAXCAN'),('TEUCAN'),('THADAS'),('THAPUB'),('THALIC'),('THATRI'),('THEHEX'),('THENOV'),('THEPAL'),('THEPHE'),('TIACOR'),('TILAME'),('TILIA'),('TORARV'),('TORJAP'),('TORILI'),('TOXRAD'),('TRAPRA'),('TRIBOR'),('TRFAUR'),('TRFHYB'),('TRFPRA'),('TRFREP'),('TRIFOL'),('TRLCER'),('TRLGRA'),('TRLSES'),('TRILLI'),('TRIANG'),('TSUCAN'),('TUSFAR'),('TYPANG'),('TYPLAT'),('TYPHA'),('ULMAME'),('ULMPUM'),('ULMRUB'),('ULMUS'),('URTDIOD'),('URTIDIOP'),('UVUGRA'),('UVUPER'),('VACPAL'),('VACCIN'),('VERBLA'),('VERTHA'),('VERHAS'),('VERBEN'),('VERURT'),('VERALT'),('VERGIG'),('VERNON'),('VEROFF'),('VERSER'),('VERONI'),('VIBACE'),('VIBALN'),('VIBDEN'),('VIBLEN'),('VIBOPUO'),('VIBPLI'),('VIBPRU'),('VIBREC'),('VIBURN'),('VICTET'),('VINMIN'),('VIOCAN'),('VIOCON'),('VIOHAS'),('VIOPUB'),('VIOSOR'),('VIOLA'),('VIOSTR'),('VITAES'),('VITLAB'),('VITRIP'),('VITIS'),('WALFRA'),('XANSTR'),('ZIZAUR'),('ZIZIA'),('LAPCAN'),('TRIAUR')
$$) 
        AS ("plot_id" text, "ACARHO" numeric,"ACENEG" numeric,"ACENIG" numeric,"ACEPLA" numeric,"ACERUB" numeric,"ACESAC" numeric,"ACESAR" numeric,"ACER" numeric,"ACHMIL" numeric,"ACOCAL" numeric,"ACTALB" numeric,"ACTAEA" numeric,"ACTARG" numeric,"ADIPED" numeric,"AEGPOD" numeric,"AESGLA" numeric,"AESHIP" numeric,"AESCUL" numeric,"AGRGRY" numeric,"AGRPAR" numeric,"AGRPUB" numeric,"AGRIMO" numeric,"AGRSTR" numeric,"AGRCAP" numeric,"AGRGIG" numeric,"AGRHYE" numeric,"AGRPER" numeric,"AGROST" numeric,"AGRSTO" numeric,"AILALT" numeric,"AJUREP" numeric,"ALISUB" numeric,"ALLPET" numeric,"ALLCAN" numeric,"ALLCER" numeric,"ALLSAT" numeric,"ALLIUM" numeric,"ALLTRI" numeric,"ALLVIN" numeric,"ALNGLU" numeric,"ALNINC" numeric,"ALNUS" numeric,"AMBART" numeric,"AMBTRI" numeric,"AMEARB" numeric,"AMELAN" numeric,"AMPBRE" numeric,"AMPBRA" numeric,"ANAARV" numeric,"ANDGER" numeric,"ANDVIRV" numeric,"ANEQUI" numeric,"ANEVIR" numeric,"ANETHA" numeric,"ANGATR" numeric,"ANTARI" numeric,"ANTODO" numeric,"APIACE" numeric,"APIAME" numeric,"APOCAN" numeric,"APOCYN" numeric,"ARACAN" numeric,"ARALAE" numeric,"ARANUD" numeric,"ARARAC" numeric,"ARCLAP" numeric,"ARCMIN" numeric,"ARCTIUM" numeric,"ARIDRA" numeric,"ARITRIT" numeric,"ARONIA" numeric,"ARTVUL" numeric,"ARTEMS" numeric,"ASACAN" numeric,"ASCINC" numeric,"ASCLEP" numeric,"ASCSUL" numeric,"ASCSYR" numeric,"ASCTUB" numeric,"ASITRI" numeric,"ASPPLA" numeric,"ASTCOR" numeric,"ASTLAN" numeric,"ASTLAT" numeric,"ASTLIN" numeric,"ASTMAC" numeric,"ASTNOV" numeric,"ASTPIL" numeric,"ASTPUN" numeric,"ASTER" numeric,"ASTUMB" numeric,"ATHFEL" numeric,"ATHTHE" numeric,"BARBAR" numeric,"BARVER" numeric,"BARVUL" numeric,"BERTHU" numeric,"BETALL" numeric,"BETLEN" numeric,"BETPAP" numeric,"BETPOP" numeric,"BETULA" numeric,"BIDFRO" numeric,"BIDENS" numeric,"BLECIL" numeric,"BLEHIR" numeric,"BLEPHI" numeric,"BOECYL" numeric,"BOTRYC" numeric,"BOUCUR" numeric,"BRAERE" numeric,"BRASSI" numeric,"BROCOM" numeric,"BROINE" numeric,"BROALT" numeric,"BROPUB" numeric,"BROMUS" numeric,"CLTPAL" numeric,"CALSEP" numeric,"CALYST" numeric,"CAMSCI" numeric,"CAMRAD" numeric,"CARDIP" numeric,"CARDOU" numeric,"CARHIR" numeric,"CARPAR" numeric,"CARPEN" numeric,"CARDAM" numeric,"CXALBU" numeric,"CXAMPH" numeric,"CXANNE" numeric,"CXAPPA" numeric,"CXARCT" numeric,"CXBLAN" numeric,"CXBROM" numeric,"CXCARE" numeric,"CXCPHLA" numeric,"CXCOMM" numeric,"CXCOMP" numeric,"CXCONJ" numeric,"CXCRINC" numeric,"CXCRIS" numeric,"CXDEBIR" numeric,"CXDIGI" numeric,"CXEMOR" numeric,"CXFEST" numeric,"CXFRAN" numeric,"CXGLAU" numeric,"CXGRCLE" numeric,"CXGRCLI" numeric,"CXGRAN" numeric,"CXGRAY" numeric,"CXGRIS" numeric,"CXHIRS" numeric,"CXHIRT" numeric,"CXINTE" numeric,"CXINTU" numeric,"CXJAME" numeric,"CXLACU" numeric,"CXLAEV" numeric,"CXLAXC" numeric,"CXLAXF" numeric,"CXLPTON" numeric,"CXLUPL" numeric,"CXLURI" numeric,"CXNORM" numeric,"CXOLIGC" numeric,"CXPALL" numeric,"CXPENS" numeric,"CXPLNT" numeric,"CXPRAS" numeric,"CXRADI" numeric,"CXROSE" numeric,"CXSCOP" numeric,"CAREX" numeric,"CXSPAR" numeric,"CXSTIP" numeric,"CXSWAN" numeric,"CXTRIB" numeric,"CXVIRE" numeric,"CXVULP" numeric,"CXWILL" numeric,"CXWOOD" numeric,"CRPCAR" numeric,"CARCOR" numeric,"CARGLA" numeric,"CARLAC" numeric,"CAROVT" numeric,"CARYA" numeric,"CARTOM" numeric,"CASDEN" numeric,"CATBIG" numeric,"CATALP" numeric,"CATSPE" numeric,"CAUTHA" numeric,"CELORB" numeric,"CELOCC" numeric,"CENJAC" numeric,"CENTAU" numeric,"CENPUL" numeric,"CEPOCC" numeric,"CERAST" numeric,"CERVUL" numeric,"CERCAN" numeric,"CHEGLA" numeric,"CHEALB" numeric,"CHENOP" numeric,"CHRLEU" numeric,"CHRAME" numeric,"CICINT" numeric,"CICMAC" numeric,"CIMRAC" numeric,"CINARU" numeric,"CIRLUT" numeric,"CIRALT" numeric,"CIRARV" numeric,"CIRMUT" numeric,"CIRSIU" numeric,"CIRVUL" numeric,"CLEVIR" numeric,"CLIVUL" numeric,"COLCAN" numeric,"CONMACU" numeric,"CONAME" numeric,"CONMAJ" numeric,"CONARV" numeric,"CONCAN" numeric,"CORTRP" numeric,"CORALT" numeric,"CORAMO" numeric,"CORFLO" numeric,"CORRAC" numeric,"CORSER" numeric,"CORNUS" numeric,"CORVAR" numeric,"CRACRU" numeric,"CRAMON" numeric,"CRATAE" numeric,"CRYCAN" numeric,"CUCMAX" numeric,"CUSGRO" numeric,"CUSCUT" numeric,"CYPERY" numeric,"CYPESC" numeric,"CYPSTR" numeric,"CYSFRA" numeric,"CYSPRO" numeric,"DACGLO" numeric,"DANCOM" numeric,"DANTHO" numeric,"DANSPI" numeric,"DAUCAR" numeric,"DAUCUS" numeric,"DESCNA" numeric,"DESCUS" numeric,"DESGLA" numeric,"DESPAN" numeric,"DESMOD" numeric,"DIAARM" numeric,"DIGITI" numeric,"DIGISC" numeric,"DIGSAN" numeric,"DIGITA" numeric,"DIOVIL" numeric,"DIPFUL" numeric,"DIPSAC" numeric,"DRYCAR" numeric,"DRYCRI" numeric,"DRYGOL" numeric,"DRYINT" numeric,"DRYMAR" numeric,"DRYOPT" numeric,"DUCIND" numeric,"DULARU" numeric,"ECHPUR" numeric,"ECHCRU" numeric,"ECHMUR" numeric,"ECHINO" numeric,"ECHLOB" numeric,"ELAANG" numeric,"ELAUMB" numeric,"ELEACI" numeric,"ELEOBT" numeric,"ELEPAL" numeric,"ELYCAN" numeric,"ELYHYS" numeric,"ELYMAC" numeric,"ELYRIP" numeric,"ELYMUS" numeric,"ELYVIL" numeric,"ELYVIR" numeric,"ELYREP" numeric,"EPIVIR" numeric,"EPICIL" numeric,"EPICOL" numeric,"EPIHIR" numeric,"EPIPAR" numeric,"EPILOB" numeric,"EPIHEL" numeric,"EQUARV" numeric,"EQUHYE" numeric,"EQUISE" numeric,"EREHIE" numeric,"ERIANN" numeric,"ERIPHI" numeric,"ERIGER" numeric,"ERISTR" numeric,"EUOALA" numeric,"EUOATR" numeric,"EUOEUR" numeric,"EUOFOR" numeric,"EUOOBO" numeric,"EUONYM" numeric,"EUPARO" numeric,"EUPMAC" numeric,"EUPPER" numeric,"EUPPUR" numeric,"EUPRUG" numeric,"EUPSER" numeric,"EUPTOR" numeric,"EUPHMA" numeric,"EUPHOR" numeric,"EUTGRA" numeric,"FAGGRA" numeric,"FESELA" numeric,"FESOVI" numeric,"FESPRA" numeric,"FESRUB" numeric,"FESTUC" numeric,"FESSUB" numeric,"FESTRA" numeric,"FORINT" numeric,"FRAGAR" numeric,"FRAVESA" numeric,"FRAVESV" numeric,"FRAVIR" numeric,"FRAAME" numeric,"FRANIG" numeric,"FRAPEN" numeric,"FRAPRO" numeric,"FRAXIN" numeric,"GALQUA" numeric,"GALAPA" numeric,"GALASP" numeric,"GALCIR" numeric,"GALCON" numeric,"GALLAN" numeric,"GALMOL" numeric,"GALOBT" numeric,"GALODO" numeric,"GALPAL" numeric,"GALIUM" numeric,"GALTIN" numeric,"GALTFL" numeric,"GAUPRO" numeric,"GERMAC" numeric,"GERROB" numeric,"GERANI" numeric,"GEUCAN" numeric,"GEULAC" numeric,"GEUM" numeric,"GEUVER" numeric,"GEUVIR" numeric,"GINBIL" numeric,"GLEHED" numeric,"GLETRI" numeric,"GLYSEP" numeric,"GLYCER" numeric,"GLYSTR" numeric,"HACVIR" numeric,"HAMVIR" numeric,"HEDPUL" numeric,"HEDHEL" numeric,"HEDCAE" numeric,"HELAUT" numeric,"HELDEC" numeric,"HELIAN" numeric,"HELHEL" numeric,"HEMFUL" numeric,"HEPACU" numeric,"HERLAN" numeric,"HESMAT" numeric,"HEUAME" numeric,"HIECAE" numeric,"HIEGRO" numeric,"HIEPAN" numeric,"HIERAC" numeric,"HOLLAN" numeric,"HUMJAP" numeric,"HDRCAN" numeric,"HYDCAN" numeric,"HYDROP" numeric,"HYDVIR" numeric,"HYPMUT" numeric,"HYPPER" numeric,"HYPPRO" numeric,"HYPPUN" numeric,"HYPERI" numeric,"HYPRAD" numeric,"ILEOPA" numeric,"IMPCAP" numeric,"IMPPAL" numeric,"IMPATI" numeric,"IPOMOE" numeric,"IRIPSE" numeric,"IRIS" numeric,"ISOBIT" numeric,"JEFDIP" numeric,"JUGNIG" numeric,"JUNEFF" numeric,"JUNMAR" numeric,"JUNCUS" numeric,"JUNTEN" numeric,"JUNIPE" numeric,"LACBIE" numeric,"LACCAN" numeric,"LACFLO" numeric,"LACTUC" numeric,"LAPCOM" numeric,"LEEORY" numeric,"LEEVIR" numeric,"LEMMIN" numeric,"LEOAUT" numeric,"LEOCAR" numeric,"LIATRIS" numeric,"LIASPI" numeric,"LICHEN" numeric,"LIGVUL" numeric,"LILCAN" numeric,"LINBEN" numeric,"LIQSTY" numeric,"LIRTUL" numeric,"LOBINF" numeric,"LOBSIP" numeric,"LOBELI" numeric,"LOLPER" numeric,"LOLSP" numeric,"LONDIO" numeric,"LONJAP" numeric,"LONMAA" numeric,"LONMOR" numeric,"LONSEM" numeric,"LONICE" numeric,"LONTAT" numeric,"LOTCOR" numeric,"LUDPAL" numeric,"LUNANN" numeric,"LUZACU" numeric,"LUZMUL" numeric,"LUZULA" numeric,"LYCOPO" numeric,"LYCAME" numeric,"LYCOPU" numeric,"LYCUNI" numeric,"LYCVIR" numeric,"LYSCIL" numeric,"LYSNUM" numeric,"LYSTER" numeric,"LYTSAL" numeric,"MACPOM" numeric,"MAGACU" numeric,"MAICAN" numeric,"MAIRAC" numeric,"MAIANT" numeric,"MATSTR" numeric,"MEDLUP" numeric,"MELALB" numeric,"MELOFF" numeric,"MELILO" numeric,"MLIOFF" numeric,"MENCAN" numeric,"MENARV" numeric,"MENTHA" numeric,"MENSPI" numeric,"MENPIP" numeric,"METGLY" numeric,"MICVIM" numeric,"MILEFF" numeric,"MIMALA" numeric,"MIMRIN" numeric,"MITREP" numeric,"MITDIP" numeric,"MONCLI" numeric,"MONFIS" numeric,"MONOTR" numeric,"MNTUNI" numeric,"MORALB" numeric,"MORUS" numeric,"MOSS" numeric,"MUHFRO" numeric,"MUHSCH" numeric,"MUHTEN" numeric,"MYOLAX" numeric,"MYOSCO" numeric,"MYOSOT" numeric,"NARCIS" numeric,"NELLUT" numeric,"NEPCAT" numeric,"NUPADV" numeric,"NYSSYL" numeric,"OENBIE" numeric,"ONOSEN" numeric,"OSMOCL" numeric,"OSMOLO" numeric,"OSMCIN" numeric,"OSMCLA" numeric,"OSTVIR" numeric,"OXALIS" numeric,"OXASTR" numeric,"PACTER" numeric,"PANCLA" numeric,"PANFLE" numeric,"PANLAN" numeric,"PANLAT" numeric,"PANLIN" numeric,"PANICU" numeric,"PANVIR" numeric,"PARQUI" numeric,"PARVIT" numeric,"PELVIR" numeric,"PENDIG" numeric,"PENSTE" numeric,"PENSED" numeric,"PETHYB" numeric,"PHAARU" numeric,"PHICOR" numeric,"PHLPRA" numeric,"PHLDIV" numeric,"PHRAUSAM" numeric,"PHRAUSAU" numeric,"PHYOPU" numeric,"PHYAME" numeric,"PICABI" numeric,"PICEA" numeric,"PILPUM" numeric,"PILEA" numeric,"PINNIG" numeric,"PINRES" numeric,"PINSPP" numeric,"PINSTRN" numeric,"PINSYL" numeric,"PLANLA" numeric,"PLANMA" numeric,"PLANRU" numeric,"PLANTA" numeric,"PLAOCC" numeric,"POAALS" numeric,"POAANN" numeric,"POACOM" numeric,"POAPAL" numeric,"POAPRA" numeric,"POA" numeric,"POASYL" numeric,"POATRI" numeric,"POACEA" numeric,"PODPEL" numeric,"POLREP" numeric,"POLBIF" numeric,"POLPUB" numeric,"POLYGO" numeric,"PLGARI" numeric,"PLGAVI" numeric,"PLGCES" numeric,"PLGCON" numeric,"PLGCUS" numeric,"PLGHPR" numeric,"PLGHPO" numeric,"PLGLAP" numeric,"PLGPER" numeric,"PLGPUN" numeric,"PLGSAG" numeric,"PLGSCA" numeric,"POLYGN" numeric,"PLGVIR" numeric,"POLVIG" numeric,"POLACR" numeric,"POPALB" numeric,"POPDEL" numeric,"POPGRA" numeric,"POPULU" numeric,"POROLE" numeric,"POTNOR" numeric,"POTREC" numeric,"POTSIM" numeric,"POTENT" numeric,"PREALB" numeric,"PREALT" numeric,"PRESER" numeric,"PRENAN" numeric,"PROLAN" numeric,"PRUVUL" numeric,"PRUCER" numeric,"PRUSER" numeric,"PRUNUS" numeric,"PRUVIR" numeric,"PTEAQU" numeric,"PYCMUT" numeric,"PYCNAN" numeric,"PYCTEN" numeric,"PYRANG" numeric,"PYRCOM" numeric,"PYRCOR" numeric,"PYRIOE" numeric,"PYRMAL" numeric,"PYRUS" numeric,"QUEALB" numeric,"QUEBIC" numeric,"QUECOC" numeric,"QUEMAC" numeric,"QUEMUE" numeric,"QUEPAL" numeric,"QUERUB" numeric,"QUERCU" numeric,"QUEVEL" numeric,"RANABO" numeric,"RANACR" numeric,"RANALL" numeric,"RANFIC" numeric,"RANHIS" numeric,"RANREC" numeric,"RANREP" numeric,"RANUNC" numeric,"RAPRAP" numeric,"RATPIN" numeric,"RHACAT" numeric,"RHAFRA" numeric,"RHAMNU" numeric,"RHODOD" numeric,"RHUS" numeric,"RHUTYP" numeric,"RIBAME" numeric,"RIBCYN" numeric,"RIBODO" numeric,"RIBES" numeric,"RICFLU" numeric,"ROBPSE" numeric,"RORIPP" numeric,"ROSCAR" numeric,"ROSMUL" numeric,"ROSPAL" numeric,"ROSSET" numeric,"ROSA" numeric,"RUBALL" numeric,"RUBCAE" numeric,"RUBFLA" numeric,"RUBHIS" numeric,"RUBIDAS" numeric,"RUBOCC" numeric,"RUBODO" numeric,"RUBPEN" numeric,"RUBPHO" numeric,"RUBUS" numeric,"RUDFUL" numeric,"RUDLAC" numeric,"RUDBEC" numeric,"RUMACE" numeric,"RUMCRI" numeric,"RUMOBT" numeric,"RUMEX" numeric,"SAGLAT" numeric,"SLXDIS" numeric,"SLXFRA" numeric,"SLXNIG" numeric,"SALIX" numeric,"SAMCAN" numeric,"SAMPUB" numeric,"SAMBUC" numeric,"SANCAN" numeric,"SANICAN" numeric,"SANIGR" numeric,"SANIMA" numeric,"SANICU" numeric,"SANITR" numeric,"SAPOFF" numeric,"SASALB" numeric,"SCHSCOS" numeric,"SCHTAB" numeric,"SCIATR" numeric,"SCICYP" numeric,"SCIPOL" numeric,"SCIRPU" numeric,"SCRMAR" numeric,"SCROPH" numeric,"SCUINT" numeric,"SCULAT" numeric,"SCUTEL" numeric,"SEDUM" numeric,"SEDTER" numeric,"SENAUR" numeric,"SENOBO" numeric,"SENECI" numeric,"SETFAB" numeric,"SETARI" numeric,"SETVIR" numeric,"SICANG" numeric,"SLPPER" numeric,"SISOFF" numeric,"SISANG" numeric,"SISMON" numeric,"SISYRI" numeric,"SMXHIS" numeric,"SMXROT" numeric,"SMILAX" numeric,"SLMCAR" numeric,"SLMDUL" numeric,"SLMNIG" numeric,"SOLBIC" numeric,"SOLCAE" numeric,"SOLCAN" numeric,"SOLFLE" numeric,"SOLGIG" numeric,"SOLHIS" numeric,"SOLJUN" numeric,"SOLPAT" numeric,"SOLRIG" numeric,"SOLRUG" numeric,"SOLIDA" numeric,"SONASP" numeric,"SORAUC" numeric,"SORBUS" numeric,"SORNUT" numeric,"SPAEUR" numeric,"SPHOBTO" numeric,"SPIALA" numeric,"SPIJAP" numeric,"STATEN" numeric,"STEMED" numeric,"STELLA" numeric,"SYMORB" numeric,"SYMFOE" numeric,"SYRVUL" numeric,"TAROFF" numeric,"TARAXA" numeric,"TAXCAN" numeric,"TEUCAN" numeric,"THADAS" numeric,"THAPUB" numeric,"THALIC" numeric,"THATRI" numeric,"THEHEX" numeric,"THENOV" numeric,"THEPAL" numeric,"THEPHE" numeric,"TIACOR" numeric,"TILAME" numeric,"TILIA" numeric,"TORARV" numeric,"TORJAP" numeric,"TORILI" numeric,"TOXRAD" numeric,"TRAPRA" numeric,"TRIBOR" numeric,"TRFAUR" numeric,"TRFHYB" numeric,"TRFPRA" numeric,"TRFREP" numeric,"TRIFOL" numeric,"TRLCER" numeric,"TRLGRA" numeric,"TRLSES" numeric,"TRILLI" numeric,"TRIANG" numeric,"TSUCAN" numeric,"TUSFAR" numeric,"TYPANG" numeric,"TYPLAT" numeric,"TYPHA" numeric,"ULMAME" numeric,"ULMPUM" numeric,"ULMRUB" numeric,"ULMUS" numeric,"URTDIOD" numeric,"URTIDIOP" numeric,"UVUGRA" numeric,"UVUPER" numeric,"VACPAL" numeric,"VACCIN" numeric,"VERBLA" numeric,"VERTHA" numeric,"VERHAS" numeric,"VERBEN" numeric,"VERURT" numeric,"VERALT" numeric,"VERGIG" numeric,"VERNON" numeric,"VEROFF" numeric,"VERSER" numeric,"VERONI" numeric,"VIBACE" numeric,"VIBALN" numeric,"VIBDEN" numeric,"VIBLEN" numeric,"VIBOPUO" numeric,"VIBPLI" numeric,"VIBPRU" numeric,"VIBREC" numeric,"VIBURN" numeric,"VICTET" numeric,"VINMIN" numeric,"VIOCAN" numeric,"VIOCON" numeric,"VIOHAS" numeric,"VIOPUB" numeric,"VIOSOR" numeric,"VIOLA" numeric,"VIOSTR" numeric,"VITAES" numeric,"VITLAB" numeric,"VITRIP" numeric,"VITIS" numeric,"WALFRA" numeric,"XANSTR" numeric,"ZIZAUR" numeric,"ZIZIA" numeric,"LAPCAN" numeric,"TRIAUR" numeric

));

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

CREATE MATERIALIZED VIEW herbaceous_tot_cov AS
  SELECT a.plot_no, a.species, sum(b.midpoint) as tot_cov
  FROM plot_module_herbaceous a
    LEFT JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  WHERE a.cover_class_code NOTNULL
  GROUP BY a.plot_no, a.species;

CREATE MATERIALIZED VIEW herbaceous_site_cov AS
  SELECT plot_no, sum(tot_cov) as site_cov
  FROM herbaceous_tot_cov
  GROUP BY plot_no;

CREATE MATERIALIZED VIEW herbaceous_relative_cover AS
  SELECT a.plot_no, a.species, (a.tot_cov / b.site_cov) as relative_cover
  FROM herbaceous_tot_cov a
    LEFT JOIN herbaceous_site_cov b ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW plot_module_woody_dbh AS
  SELECT plot_no, module_id, species, dbh_class_index, dbh_class, count::numeric / sub AS count
  FROM plot_module_woody_raw
  WHERE dbh_class_index <= 10;

CREATE MATERIALIZED VIEW plot_module_woody_dbh_cm AS
  SELECT plot_no, module_id, species, dbh_class_index, dbh_class, (count::numeric / 2) ^ 2 * pi() AS dbh_cm
  FROM plot_module_woody_raw
  WHERE dbh_class_index > 10;

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
    LEFT JOIN plot AS b ON a.plot_no = b.plot_no
  GROUP BY b.plot_no, a.species;

CREATE MATERIALIZED VIEW reduced_fsd2_tot_steams_all_spp AS
  SELECT plot_no, sum(tot_steams_ha) as tot_steams_all_spp
  FROM reduced_fsd2_tot_steams
  GROUP BY plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_basal_cm2 AS
  SELECT a.plot_no, a.species, a.dbh_class_index, (a.counts * b.basal_area) as basal_cm2
  FROM reduced_fsd2_counts a
    LEFT JOIN reduced_fsd2_dbh_index_basal_area b
      ON a.dbh_class_index = b.dbh_class_index
  WHERE a.dbh_class_index >= 0
  UNION
  SELECT * FROM reduced_fsd2_counts_cm2 as basal_cm2;

CREATE MATERIALIZED VIEW reduced_fsd2_basal_cm2_ha AS
  SELECT a.plot_no, a.species, a.dbh_class_index, (a.counts * b.basal_area) / c.plot_size_for_cover_data_area_ha AS basal_cm2_ha
  FROM reduced_fsd2_counts a
    LEFT JOIN reduced_fsd2_dbh_index_basal_area b
      ON a.dbh_class_index = b.dbh_class_index
    LEFT JOIN plot AS c
      ON a.plot_no = c.plot_no
  WHERE a.dbh_class_index >= 0
  UNION
  SELECT a.plot_no, a.species, a.dbh_class_index, a.counts / b.plot_size_for_cover_data_area_ha AS basal_cm2_ha
  FROM reduced_fsd2_counts_cm2 a
    LEFT JOIN plot b
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
    LEFT JOIN reduced_fsd2_tot_steams b
      ON a.plot_no = b.plot_no AND a.species = b.species
    LEFT JOIN reduced_fsd2_tot_steams_all_spp c
      ON a.plot_no = c.plot_no
    LEFT JOIN reduced_fsd2_basal_cm2_ha_tot d
      ON a.plot_no = d.plot_no AND a.species = d.species
    LEFT JOIN reduced_fsd2_basal_cm2_ha_all_spp e
      ON a.plot_no = e.plot_no;

CREATE MATERIALIZED VIEW  woody_importance_value AS
  SELECT a.plot_no, a.species,
    CASE WHEN b.code5 = 'partial' THEN a.iv ELSE null END AS subcanopy_iv_partial,
    CASE WHEN b.code5 = 'shade' THEN a.iv ELSE null END AS subcanopy_iv_shade,
    CASE WHEN b.form = 'tree' THEN a.iv ELSE null END AS canopy_IV
  FROM reduced_fsd2_iv a
    LEFT JOIN species b ON a.species = b.scientific_name;


CREATE MATERIALIZED VIEW biomass AS
  SELECT plot_no, date_time, module_id, corner, sample_id, area_sampled,
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
  SELECT plot_no, date_time, biomass_collected
  FROM biomass
  GROUP BY plot_no, date_time, biomass_collected;

CREATE MATERIALIZED VIEW biomass_count AS
  SELECT plot_no,
    sum(CASE WHEN COALESCE(sample_id, 0) > 0 THEN 1 ELSE 0 END) AS count
  FROM biomass
  GROUP BY plot_no;

CREATE MATERIALIZED VIEW biomass_tot AS
  SELECT plot_no,
    sum(COALESCE(grams_per_sq_meter, 0)) AS tot
  FROM biomass
  GROUP BY plot_no;

CREATE MATERIALIZED VIEW biomass_calculations AS
  SELECT a.plot_no,
    CASE WHEN COALESCE(b.count, 0) > 0
      THEN c.tot / b.count ELSE 0 END AS biomass
  FROM plot a
    LEFT JOIN biomass_count b
      ON a.plot_no = b.plot_no
    LEFT JOIN biomass_tot c
      ON a.plot_no = c.plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_den AS
  SELECT a.plot_no, a.species, a.dbh_class_index, a.counts / b.plot_size_for_cover_data_area_ha AS counts_den
  FROM reduced_fsd2_counts a
    LEFT JOIN plot b
      ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW reduced_fsd2_rel_den AS
  SELECT a.plot_no, a.species, a.dbh_class_index,
    CASE WHEN a.counts_den > 0 THEN a.counts_den / b.tot_steams_all_spp ELSE 0 END AS counts_rel_den
  FROM reduced_fsd2_den a
    LEFT JOIN reduced_fsd2_tot_steams_all_spp b
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
    LEFT JOIN reduced_fsd2_avg_iv b
      ON a.plot_no = b.plot_no;

CREATE MATERIALIZED VIEW calculations_reduced_fsd1 AS
  SELECT a.plot_no,
    sum(CASE WHEN c.code1 = 'carex' THEN 1.0 ELSE 0.0 END) AS carex,
    sum(CASE WHEN c.code2 = 'cyper' THEN 1.0 ELSE 0.0 END) AS cyperaceae,
    sum(CASE WHEN c.code1 = 'natDI' THEN 1.0 ELSE 0.0 END) AS dicot,
    sum(CASE WHEN c.shade = 'shade' OR c.shade = 'partial' THEN 1.0 ELSE 0.0 END) AS shade,
    sum(CASE WHEN c.code2 = 'natwtldSH' THEN 1.0 ELSE 0.0 END) AS shrub,
    sum(CASE WHEN c.hydro = 'hydrophyte' THEN 1.0 ELSE 0.0 END) AS hydrophyte,
    sum(CASE WHEN c.groupp = 'SVP' THEN 1 ELSE 0.0 END) AS svp,
    CASE WHEN sum(CASE WHEN c.habit = 'PE' THEN b.relative_cover ELSE 0.0 END) > 0.0
         THEN sum(CASE WHEN c.habit = 'AN' THEN 1.0 ELSE 0.0 END) / sum(CASE WHEN c.habit = 'PE' THEN 1.0 ELSE 0.0 END)
         ELSE 1.0 END AS ap_ratio,
    CASE WHEN sum(CASE WHEN c.cofc >= 0.0 THEN 1.0 ELSE 0.0 END) > 0.0
         THEN sum(CASE WHEN c.cofc >= 0.0 THEN c.cofc ELSE 0.0 END) / sqrt(sum(CASE WHEN c.cofc >= 0.0 THEN 1.0 ELSE 0.0 END))
         ELSE 0.0 END AS fqai,
    sum(CASE WHEN c.groupp = 'bryo' THEN b.relative_cover ELSE 0.0 END) AS bryophyte,
    sum(CASE WHEN c.code3 = 'natshHYDRO' THEN b.relative_cover ELSE 0.0 END) AS per_hydrophyte,
    sum(CASE WHEN c.tolerance = 'sensitive' THEN b.relative_cover ELSE 0.0 END) AS sensitive,
    sum(CASE WHEN c.tolerance = 'tolerant' THEN b.relative_cover ELSE 0.0 END) AS tolerant,
    sum(CASE WHEN c.code1 = 'invgram' THEN b.relative_cover ELSE 0.0 END) AS invasive_graminoids,
    sum(CASE WHEN c.habit = 'AN' THEN b.relative_cover ELSE 0.0 END) AS habit_an_sum,
    sum(CASE WHEN c.code3 = 'BBS' THEN b.relative_cover ELSE 0.0 END) AS button_bush,
    sum(CASE WHEN c.code4 = 'PEnatHYD' THEN b.relative_cover ELSE 0.0 END) AS perennial_native_hydrophytes,
    sum(CASE WHEN c.nativity = 'adventive' THEN b.relative_cover ELSE 0.0 END) AS adventives
  FROM plot a
  LEFT JOIN herbaceous_relative_cover b ON a.plot_no = b.plot_no
  LEFT JOIN species c ON b.species = c.scientific_name
  GROUP BY a.plot_no, a.plot_name;

CREATE MATERIALIZED VIEW calculations_reduced_fsd2_canopy AS
  SELECT a.plot_no,
    b.small_tree,
    c.avg_subcanopy_iv_partial + c.avg_subcanopy_iv_shade AS subcanopy_iv,
    c.avg_canopy_IV AS canopy_iv
  FROM plot a
    LEFT JOIN reduced_fsd2_rel_den_calculations b
      ON a.plot_no = b.plot_no
    LEFT JOIN reduced_fsd2_calculations_iv c
      ON a.plot_no = c.plot_no;

CREATE MATERIALIZED VIEW calculations_reduced_fsd2_steams AS
  SELECT a.plot_no,
    sum(CASE WHEN c.code2 = 'natwtldTR' THEN b.tot_steams_ha ELSE 0 END) AS steams_wetland_trees,
    sum(CASE WHEN c.code2 = 'natwtldSH' THEN b.tot_steams_ha ELSE 0 END) AS steams_wetland_shrubs
  FROM plot a
    LEFT JOIN reduced_fsd2_tot_steams b
      ON a.plot_no = b.plot_no
    LEFT JOIN species c
      ON b.species = c.scientific_name
  GROUP BY a.plot_no;

CREATE MATERIALIZED VIEW herbaceous_info_tot_cov AS
  SELECT a.plot_no, a.info, sum(b.midpoint) as tot_cov
  FROM plot_module_herbaceous_info a
    LEFT JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  WHERE a.cover_class_code NOTNULL
  GROUP BY a.plot_no, a.info;

CREATE MATERIALIZED VIEW herbaceous_info_tot_count AS
  SELECT a.plot_no, a.info, sum(CASE WHEN b.midpoint > 0 THEN 1 ELSE 0 END) as tot_count
  FROM plot_module_herbaceous_info a
    LEFT JOIN cover_midpoint_lookup b ON a.cover_class_code = b.cover_code
  WHERE a.cover_class_code NOTNULL
  GROUP BY a.plot_no, a.info;

CREATE MATERIALIZED VIEW herbaceous_info_relative_cover AS
  SELECT a.plot_no, a.info, CASE WHEN a.tot_cov > 0 THEN a.tot_cov / b.tot_count ELSE 0 END AS relative_cover
  FROM herbaceous_info_tot_cov a
    LEFT JOIN herbaceous_info_tot_count b ON a.plot_no = b.plot_no AND a.info = b.info;

CREATE MATERIALIZED VIEW calculations_plot_module_herbaceous_info AS
  SELECT plot_no,
    sum(CASE WHEN info = '%bare ground' OR info = '%litter cover' THEN relative_cover ELSE 0 END) AS unvegetated_partial,
    sum(CASE WHEN info = '%open water' THEN relative_cover ELSE 0 END) AS open_water,
    sum(CASE WHEN info = '%unvegetated open water' THEN relative_cover ELSE 0 END) AS unvegetated_open_water,
    sum(CASE WHEN info = '%bare ground' THEN relative_cover ELSE 0 END) AS bare_ground
  FROM herbaceous_info_relative_cover
  GROUP BY plot_no;

CREATE MATERIALIZED VIEW vibi_values AS
  SELECT a.plot_no,
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
    steams_wetland_trees AS stems_ha_wetland_trees,
    steams_wetland_shrubs AS stems_ha_wetland_shrubs,
    unvegetated_partial + habit_an_sum AS per_unvegetated,
    button_bush AS per_button_bush,
    perennial_native_hydrophytes AS per_perennial_native_hydrophytes,
    adventives AS per_adventives,
    open_water AS per_open_water,
    unvegetated_open_water AS per_unvegetated_open_water,
    bare_ground AS per_bare_ground
  FROM plot a
    LEFT JOIN calculations_reduced_fsd2_canopy b
      ON a.plot_no = b.plot_no
    LEFT JOIN calculations_reduced_fsd2_steams c
      ON a.plot_no = c.plot_no
    LEFT JOIN calculations_plot_module_herbaceous_info d
      ON a.plot_no = d.plot_no
    LEFT JOIN calculations_reduced_fsd1 e
      ON a.plot_no = e.plot_no
    LEFT JOIN biomass_calculations f
      ON a.plot_no = f.plot_no;


CREATE MATERIALIZED VIEW vibi_e_index AS
  SELECT a.plot_no,
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
      ON a.plot_no = b.plot_no
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_no = c.plot_no
    LEFT JOIN biomass_info d
      ON a.plot_no = d.plot_no;

CREATE MATERIALIZED VIEW vibi_ecst_index AS
  SELECT a.plot_no,
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
      ON a.plot_no = b.plot_no
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_no = c.plot_no
    LEFT JOIN biomass_info d
      ON a.plot_no = d.plot_no;

CREATE MATERIALIZED VIEW vibi_sh_index AS
  SELECT a.plot_no,
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
      ON a.plot_no = b.plot_no
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_no = c.plot_no
    LEFT JOIN biomass_info d
      ON a.plot_no = d.plot_no;

CREATE MATERIALIZED VIEW vibi_f_index AS
  SELECT a.plot_no,
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
      ON a.plot_no = b.plot_no
    LEFT JOIN herbaceous_site_cov c
      ON a.plot_no = c.plot_no;

CREATE MATERIALIZED VIEW metric_calculations AS
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
            COALESCE(per_unvegetated_open_water, 0.0) AS score FROM vibi_f_index;

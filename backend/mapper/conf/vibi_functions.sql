DROP FUNCTION IF EXISTS refresh_calculations() CASCADE;
DROP FUNCTION IF EXISTS metric_value(numeric, numeric[], numeric[]) CASCADE;

CREATE OR REPLACE FUNCTION metric_value(value numeric, metrics_index numeric[], metrics_values numeric[])
  RETURNS numeric LANGUAGE plpgsql AS $$
DECLARE
  index int := 0;
  length int := array_length(metrics_index, 1);
BEGIN
  IF value IS NULL THEN
    RETURN NULL;
  END IF;
  WHILE index <= length LOOP
    IF metrics_index[index] > value THEN
      RETURN metrics_values[index];
    END IF;
    index := index + 1;
  END LOOP;
  RETURN metrics_values[length + 1];
END
$$;

CREATE OR REPLACE FUNCTION refresh_calculations() RETURNS void AS $$
BEGIN
  REFRESH MATERIALIZED VIEW plot_module_woody_dbh;
  REFRESH MATERIALIZED VIEW plot_module_woody_dbh_cm;
  REFRESH MATERIALIZED VIEW reduced_fds2_counts;
  REFRESH MATERIALIZED VIEW reduced_fds2_tot_stems;
  REFRESH MATERIALIZED VIEW calculations_reduced_fds2_stems;
  REFRESH MATERIALIZED VIEW reduced_fds2_counts_cm2;
  REFRESH MATERIALIZED VIEW reduced_fds2_basal_cm2_ha;
  REFRESH MATERIALIZED VIEW reduced_fds2_basal_cm2_ha_tot;
  REFRESH MATERIALIZED VIEW reduced_fds2_basal_cm2_ha_all_spp;
  REFRESH MATERIALIZED VIEW reduced_fds2_class_freq;
  REFRESH MATERIALIZED VIEW reduced_fds2_tot_stems_all_spp;
  REFRESH MATERIALIZED VIEW reduced_fds2_den;
  REFRESH MATERIALIZED VIEW reduced_fds2_rel_den;
  REFRESH MATERIALIZED VIEW reduced_fds2_rel_den_calculations;
  REFRESH MATERIALIZED VIEW reduced_fds2_iv;
  REFRESH MATERIALIZED VIEW woody_importance_value;
  REFRESH MATERIALIZED VIEW reduced_fds2_sums_counts_iv;
  REFRESH MATERIALIZED VIEW reduced_fds2_avg_iv;
  REFRESH MATERIALIZED VIEW reduced_fds2_calculations_iv;
  REFRESH MATERIALIZED VIEW calculations_reduced_fds2_canopy;
  REFRESH MATERIALIZED VIEW herbaceous_tot_cov;
  REFRESH MATERIALIZED VIEW herbaceous_site_cov;
  REFRESH MATERIALIZED VIEW herbaceous_relative_cover;
  REFRESH MATERIALIZED VIEW calculations_reduced_fds1;
  REFRESH MATERIALIZED VIEW herbaceous_info_tot_cov;
  REFRESH MATERIALIZED VIEW herbaceous_info_tot_count;
  REFRESH MATERIALIZED VIEW herbaceous_info_relative_cover;
  REFRESH MATERIALIZED VIEW calculations_plot_module_herbaceous_info;
  REFRESH MATERIALIZED VIEW biomass;
  REFRESH MATERIALIZED VIEW biomass_info;
  REFRESH MATERIALIZED VIEW biomass_count;
  REFRESH MATERIALIZED VIEW biomass_tot;
  REFRESH MATERIALIZED VIEW biomass_calculations;
  REFRESH MATERIALIZED VIEW vibi_values;
  REFRESH MATERIALIZED VIEW vibi_e_index;
  REFRESH MATERIALIZED VIEW vibi_ecst_index;
  REFRESH MATERIALIZED VIEW vibi_sh_index;
  REFRESH MATERIALIZED VIEW vibi_f_index;
  REFRESH MATERIALIZED VIEW metric_calculations;
  REFRESH MATERIALIZED VIEW alt_herbaceous_avg_cov;
  REFRESH MATERIALIZED VIEW alt_herbaceous_site_cov;
  REFRESH MATERIALIZED VIEW alt_herbaceous_relative_cover;
  REFRESH MATERIALIZED VIEW alt_calculations_reduced_fds1;
  REFRESH MATERIALIZED VIEW alt_reduced_fds2_freq;
  REFRESH MATERIALIZED VIEW alt_reduced_fds2_iv;
  REFRESH MATERIALIZED VIEW alt_woody_importance_value;
  REFRESH MATERIALIZED VIEW alt_reduced_fds2_sums_counts_iv;
  REFRESH MATERIALIZED VIEW alt_reduced_fds2_avg_iv;
  REFRESH MATERIALIZED VIEW alt_reduced_fds2_calculations_iv;
  REFRESH MATERIALIZED VIEW alt_calculations_reduced_fds2_canopy;
  REFRESH MATERIALIZED VIEW alt_vibi_values;
  REFRESH MATERIALIZED VIEW alt_vibi_e_index;
  REFRESH MATERIALIZED VIEW alt_vibi_ecst_index;
  REFRESH MATERIALIZED VIEW alt_vibi_sh_index;
  REFRESH MATERIALIZED VIEW alt_vibi_f_index;
  REFRESH MATERIALIZED VIEW alt_metric_calculations;
  REFRESH MATERIALIZED VIEW herb_rel_cover_acronym_joined;
  REFRESH MATERIALIZED VIEW woody_iv_acronym_joined;
  REFRESH MATERIALIZED VIEW herb_plot_x_species_matrix;
  REFRESH MATERIALIZED VIEW woody_plot_x_species_matrix;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION isFACW(p plot, s species)
  RETURNS boolean LANGUAGE plpgsql AS $$
BEGIN
  IF p.location = 'EMP' THEN
    RETURN s.emp = 'FACW';
  ELSEIF p.location = 'NCNE' THEN
    RETURN s.ncne = 'FACW';
  ELSEIF p.location = 'MW' THEN
    RETURN s.mw = 'FACW';
  END IF;
  RETURN false;
END
$$;

CREATE OR REPLACE FUNCTION isOBL(p plot, s species)
  RETURNS boolean LANGUAGE plpgsql AS $$
BEGIN
  IF p.location = 'EMP' THEN
    RETURN s.emp = 'OBL';
  ELSEIF p.location = 'NCNE' THEN
    RETURN s.ncne = 'OBL';
  ELSEIF p.location = 'MW' THEN
    RETURN s.mw = 'OBL';
  END IF;
  RETURN false;
END
$$;


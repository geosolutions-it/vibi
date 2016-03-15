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
  REFRESH MATERIALIZED VIEW reduced_fds2_tot_steams;
  REFRESH MATERIALIZED VIEW calculations_reduced_fds2_steams;
  REFRESH MATERIALIZED VIEW reduced_fds2_counts_cm2;
  REFRESH MATERIALIZED VIEW reduced_fds2_basal_cm2_ha;
  REFRESH MATERIALIZED VIEW reduced_fds2_basal_cm2_ha_tot;
  REFRESH MATERIALIZED VIEW reduced_fds2_basal_cm2_ha_all_spp;
  REFRESH MATERIALIZED VIEW reduced_fds2_class_freq;
  REFRESH MATERIALIZED VIEW reduced_fds2_tot_steams_all_spp;
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
END;
$$ LANGUAGE plpgsql;

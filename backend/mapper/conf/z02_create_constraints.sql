ALTER TABLE plot ADD CONSTRAINT plot_drainage_fkey FOREIGN KEY (drainage)
      REFERENCES drainage (drainage) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE plot ADD CONSTRAINT plot_hgmclass_fkey FOREIGN KEY (hgmclass)
    REFERENCES hgm_class (hgm_class) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_hgmgroup_fkey FOREIGN KEY (hgmgroup)
    REFERENCES hgm_group (hgm_group) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_hgmsubclass_fkey FOREIGN KEY (hgmsubclass)
    REFERENCES hgm_subclass (hgm_subclass) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_homogeneity_fkey FOREIGN KEY (homogeneity)
    REFERENCES homogeneity (homogeneity) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_hydrologic_regime_fkey FOREIGN KEY (hydrologic_regime)
    REFERENCES hydrologic_regime (hydrologic_regime) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_landform_type_fkey FOREIGN KEY (landform_type)
    REFERENCES landform_type (landform_type) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_leap_landcover_classification_fkey FOREIGN KEY (leap_landcover_classification)
    REFERENCES leap_landcover_classification (code) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_oneo_class_code_mod_natureserve_fkey FOREIGN KEY (oneo_class_code_mod_natureserve)
    REFERENCES class_code_mod_natureserve (code) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_oneo_disturbance_severity_fkey FOREIGN KEY (oneo_disturbance_severity)
    REFERENCES disturbance_severity (disturbance_severity) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_oneo_disturbance_type_fkey FOREIGN KEY (oneo_disturbance_type)
    REFERENCES disturbance_type (disturbance_type) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_salinity_fkey FOREIGN KEY (salinity)
    REFERENCES salinity (salinity) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_stand_size_fkey FOREIGN KEY (stand_size)
    REFERENCES stand_size (stand_size) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_threeo_disturbance_severity_fkey FOREIGN KEY (threeo_disturbance_severity)
    REFERENCES disturbance_severity (disturbance_severity) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_threeo_disturbance_type_fkey FOREIGN KEY (threeo_disturbance_type)
    REFERENCES disturbance_type (disturbance_type) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_twoo_disturbance_severity_fkey FOREIGN KEY (twoo_disturbance_severity)
    REFERENCES disturbance_severity (disturbance_severity) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_twoo_disturbance_type_fkey FOREIGN KEY (twoo_disturbance_type)
    REFERENCES disturbance_type (disturbance_type) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
ALTER TABLE plot ADD CONSTRAINT plot_vegclass_fkey FOREIGN KEY (vegclass)
    REFERENCES veg_class (veg_class) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
    
    
ALTER TABLE plot_module_woody_raw ADD CONSTRAINT plot_module_woody_raw_dbh_class_fkey FOREIGN KEY (dbh_class)
    REFERENCES dbh_class (dbh_class) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;
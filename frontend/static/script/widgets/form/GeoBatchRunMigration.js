/*
 *  Copyright (C) 2014 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
Ext.ns("mxp.widgets");

/**
 * 
 */
mxp.widgets.GeoBatchRunMigration = Ext.extend(Ext.Panel, {
    iconCls:'update_manager_ic',
    /** api: xtype[geobatch_run_migration]
     */
    xtype:'geobatch_run_migration',
    
    /** api: config[baseDir]
     * ``string`` baseDir to concatenate to the dir from the file browser
     * 
     * e.g. 
     * baseDir: "/var/data" 
     * fileId from Server "/csv/myFile.csv"
     * forwarded file path: "/var/data/csv/myFile.csv"
     */
    baseDir:'',
	
	flowId: null,
    
    events: [
        /** public event[success]
         * Fired when the flow starts successful
         *
         * arguments: 
         * ``string`` the id of the consumer
         */
        'success',
        /** event[fail]
         * Fired when the flow failed to run
         *
         * arguments:
         * ``string`` the server response
         */
        'fail'],
    layout:'fit',
    autoScroll:false,
    // i18n
    runButtonText: "Run",
    successText: "Success",
    errorText:"Error",
    runSuccessText: "The workflow has been started successfully<br/>",
    //end of i18n
    
    initComponent: function() {
        
        var proxy= new GeoExt.data.ProtocolProxy({ 
            protocol: new OpenLayers.Protocol.WFS({ 
                url: this.wfsURL, 
                featureType: this.partnerFeature, 
                readFormat: new OpenLayers.Format.GeoJSON(),
                featureNS: this.partnerNS, 
                outputFormat: "application/json",
                version: this.wfsVersion
            }) 
        });
		
        var partnerStore= new GeoExt.data.FeatureStore({ 
              id: "partnerStore",
              fields: [
				"codice_partner",
				"partner_it"
			  ],
              proxy: proxy, 
              autoLoad: true 
        });
        partnerStore.on('load', function(store, records, options) {
			if(records.length > 0) {
				this.partner.setValue(records[0].get('codice_partner'));
				this.run.setDisabled(false);
			}
		}, this);
        this.items = [{
            xtype:'form',
			items: [{
				xtype:'combo',
				ref:'../partner',
				store: partnerStore,
				mode:'remote',
				valueField:'codice_partner',
				displayField:'partner_it',
				width: 150,
				triggerAction: 'all',
				fieldLabel: 'Partner',
				forceSelection: true,
				listeners: {
					select: function(combo, record, index) {
						this.run.setDisabled(false);
					},
					scope: this
				}
			}]
        }];
        this.buttons = [{
            ref:'../run',
            text:this.runButtonText,
            disabled:true,
            iconCls:'update_manager_ic',
            handler: this.runLocal,
			scope: this
        }];
        mxp.widgets.GeoBatchRunMigration.superclass.initComponent.call(this, arguments);
       
    },
    
	isForm: function() {
		return true;
	},
	
    runLocal: function(){
		var filter = new OpenLayers.Filter.Comparison({
			type : OpenLayers.Filter.Comparison.LIKE,
			property : "nome_file",
			value : this.partner.getValue()+ '_C_Grafo*_ORIG'
		});
        var proxy= new GeoExt.data.ProtocolProxy({ 
            protocol: new OpenLayers.Protocol.WFS({ 
                url: this.wfsURL, 
                featureType: this.metadataFeature, 
                readFormat: new OpenLayers.Format.GeoJSON(),
                featureNS: this.metadataNS, 
                filter: filter, 
                outputFormat: "application/json",
                version: this.wfsVersion
            }) 
        });
		
        var metadataStore= new GeoExt.data.FeatureStore({ 
              id: "metadataStore",
              fields: [
				"partner",
				"bersaglio",
				"nome_file",
				{name: "nr_rec_shape", type:"int"},
				{name: "nr_rec_storage", type:"int"},
				{name: "nr_rec_scartati", type:"int"},
				{name: "nr_rec_scartati_siig", type:"int"},
				{name: "data", type: 'date'},
				{name: "data_creazione", type: 'date'},
				{name: "data_chiusura_a", type: 'date'},
				{name: "data_chiusura_b", type: 'date'},
				{name: "data_chiusura_c", type: 'date'},
				{name: "data_elab", type: 'date'},
				{name: "data_imp_storage", type: 'date'},
				{name: "data_imp_siig", type: 'date'},
				"flg_tipo_imp",
				"fk_processo",
				"errors"
			  ],
              proxy: proxy , 
              autoLoad: true 
        });
		metadataStore.on('load', function(store, records, options) {
			if(records.length === 1) {
				var fileName = records[0].get('nome_file').substring(0,2) + '_' + records[0].get('nome_file').substring(11,19)+'.run';
				Ext.Ajax.request({
				   url: this.geoBatchRestURL + 'flows/' + this.flowId +'/run?fileName=' + fileName, 
				   method: 'POST',
				   headers:{
					  'Content-Type' : 'application/xml',
					  'Accept' : this.acceptTypes_,
					  'Authorization' : this.authorization_ //TODO
				   },
				   xmlData:'<runInfo><file>' + fileName + '</file></runInfo>',
				   scope: this,
				   success: function(response, opts){
						//var data = self.afterFind( Ext.util.JSON.decode(response.responseText) ); 
						this.fireEvent('success',response);
						this.onSuccess(response, opts);
				   },
				   failure: function(response, opts){
						this.fireEvent('fail',response);
						this.onFailure(response);
				   }
				});
			} else {
				alert('error');
			}
		}, this);
	
        /**/

    },
    /**
     * private method[onFailure]
     * manage the negative response of Run call
     */
    onFailure : function(response){
        Ext.Msg.show({
            title: this.errorText,
            msg: response.statusText + "(status " + response.status + "):  " + response.responseText,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });	
    },
    /**
     * private method[onSuccess]
     * manage positive response of Run call (ID of the consumer)
     */
    onSuccess : function(response){
        Ext.Msg.show({
            title: this.successText,
            //msg: this.runSuccessPreText + response.responseText,
            msg: this.runSuccessText,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.INFO  
        });	
    }
    
});
Ext.reg(mxp.widgets.GeoBatchRunMigration.prototype.xtype, mxp.widgets.GeoBatchRunMigration);
/*
This file is part of Ext JS 3.4

Copyright (c) 2011-2013 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as
published by the Free Software Foundation and appearing in the file LICENSE included in the
packaging of this file.

Please review the following information to ensure the GNU General Public License version 3.0
requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department
at http://www.sencha.com/contact.

Build date: 2013-04-03 15:07:25
*/
Ext.ns('Ext.ux.grid');

/**
 * @class Ext.ux.grid.ImportMetadataPlugin
 * @extends Ext.util.Observable
 * Plugin (ptype = 'importmetadata') that adds an action column
 * to show import metadata from destination / lose metadata tables.
 *
 * @ptype importmetadata
 */
Ext.ux.grid.ImportMetadataPlugin = Ext.extend(Ext.util.Observable, {
    id : 'importmetadata',
    
	metadataTooltip: 'Show Metadata',
	showErrorTitle: 'Show Error',

    constructor: function(config){
        Ext.apply(this, config);

        this.addEvents({
            
        });

        Ext.ux.grid.ImportMetadataPlugin.superclass.constructor.call(this);
    },

    init : function(grid){
        this.grid = grid;
		this.grid.colModel.config.push(new Ext.grid.ActionColumn({
			id:'9',
			width: 50,
			scope:this,
			handler: this.showMetadata,
            items: [
                {
                    iconCls:'metadata_ic',
					width:25,
					tooltip: this.metadataTooltip,
					getClass: function(v, meta, rec) {
						return 'x-grid-center-icon action_column_btn';
					}
                }
            ]
		}));
		//this.grid.reconfigure(this.grid.store, this.grid.colModel);
    },
	
	showError: function(grid, rowIndex, colIndex){
		var record = grid.store.getAt(rowIndex);
		Ext.Msg.show({
		   title: this.showErrorTitle,
		   msg: record.get('descr_errore'),
		   buttons: Ext.Msg.OK
		}); 
	},
	
	/**
     *    private: method[showMetadata] show the metadata for current import
     *      * grid : the grid
     *      * rowIndex: the index of the row 
     *      * colIndex: the actioncolumn index
     */
    showMetadata: function(grid, rowIndex, colIndex){
        var record =  grid.getStore().getAt(rowIndex);
        var filename = record.get('details').events[0];
		filename = filename.substring(0, filename.lastIndexOf('.'));
        var me = this;
		
		var filter = new OpenLayers.Filter.Comparison({
			type : OpenLayers.Filter.Comparison.EQUAL_TO,
			property : "nome_file",
			value : filename,
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
		
		proxy= new GeoExt.data.ProtocolProxy({ 
            protocol: new OpenLayers.Protocol.WFS({ 
                url: this.wfsURL, 
                featureType: this.metadataErrorsFeature, 
                readFormat: new OpenLayers.Format.GeoJSON(),
                featureNS: this.metadataNS, 
                filter: filter, 
                outputFormat: "application/json",
                version: this.wfsVersion
            }) 
        });

		var errorsStore= new GeoExt.data.FeatureStore({ 
              id: "errorsStore",
              fields: [
				"id_tracciamento","progressivo","codice_log","descr_errore","id_tematico_shape_orig"
			  ],
              proxy: proxy , 
              autoLoad: true 
        });
		
		var tpl = new Ext.XTemplate(
			'<tpl for=".">',
				'<div class="metadata_main">',
				'<div class="metadata_item"><label>Partner:</label><span>{partner}</span></div>',
				'<div class="metadata_item"><label>Filename:</label><span>{nome_file}</span></div>',
				'<div class="metadata_item"><label>Bersaglio:</label><span>{bersaglio}</span></div>',
				'<div class="metadata_item"><label>Record Importati:</label><span>{nr_rec_storage}/{nr_rec_shape} ({nr_rec_scartati} skipped for errors)</span></div>',
				//'<div class="metadata_item"><label>Errori:</label><span>{errors}</span></div>',
				'<div class="metadata_item"><label>Data Creazione:</label><span>{data_creazione:date("d-m-Y H:i:s")}</span></div>',
				'<div class="metadata_item"><label>Data Importazione:</label><span>{data_imp_storage:date("d-m-Y H:i:s")}</span></div>',
				'<div class="metadata_item"><label>Fine Importazione:</label><span>{data_chiusura_a:date("d-m-Y H:i:s")}</span></div>',
				'<div class="metadata_item"><label>Fine Elaborazione:</label><span>{data_chiusura_b:date("d-m-Y H:i:s")}</span></div>',
				'<div class="metadata_item"><label>Fine Migrazione:</label><span>{data_chiusura_c:date("d-m-Y H:i:s")}</span></div>',
				'<div class="metadata_item"><label>Elenco Errori:</label><span></span></div>',
				'</div>',
			'</tpl>',
			'<div class="x-clear"></div>'
		);
		
        var win = new Ext.Window({
			iconCls:'metadata_ic',
			title:this.metadataTooltip,
			width: 700,
			height: 600, 
			minWidth:250,
			minHeight:200,
			layout:'fit',
			autoScroll:false,
			closeAction:'hide',
			maximizable: true, 
			modal:true,
			resizable:true,
			draggable:true,
			tbar:[/*{
				text:this.refreshText,
				iconCls:'refresh_ic',
				handler: function(btn){
					win.refreshLog();
				} 
			}*/],
			items: [
			{
				xtype: 'container',
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				items:[
					{
						xtype: 'dataview',
						store: metadataStore,
						tpl: tpl,
						height: 210,
						multiSelect: true,
						overClass:'x-view-over',
						itemSelector:'div.thumb-wrap',
						emptyText: 'No images to display'
					},
					{
						xtype: 'grid',
						flex:1,
						store: errorsStore,
						autoExpandColumn: 'errore',
						viewConfig: {
							forceFit: true
						},
						columns: [
							{header:'Codice Elemento', width: 25,dataIndex: 'id_tematico_shape_orig', sortable: true},
							{header:'Tipo Errore', width: 50, dataIndex: 'codice_log', sortable: true},
							{id: 'errore', header:'Descrizione Errore', dataIndex: 'descr_errore'},
							{
								xtype:'actioncolumn',
								width: 15,
								handler: this.showError,
								scope: this,
								items:[ {
									iconCls:'information_ic',
									width:25,
									scope:this,
									getClass: function(v, meta, rec) {
									  
										return 'x-grid-center-icon action_column_btn';
									  
									}
								}]
						}
						]
					}
				]
			}
			],
			listeners: {
				scope: this,
				afterrender : function(win){
					//win.refreshLog();
					 
				}
			}
        });
        win.show();
    }

    
});

Ext.preg('importmetadata', Ext.ux.grid.ImportMetadataPlugin);

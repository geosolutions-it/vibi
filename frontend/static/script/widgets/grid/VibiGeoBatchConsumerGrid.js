/*
 *  Copyright (C) 2015 - 2016 GeoSolutions S.A.S.
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

mxp.widgets.VibiGeoBatchConsumerGrid = Ext.extend(mxp.widgets.GeoBatchConsumerGrid, {

    adminUrl: 'http://vibi.geo-solutions.it/opensdi2-manager',

    tooltipDownload: 'Download File',
    
    errorDownloadingFile: 'Error Downloading file.',

    initComponent: function() {

        this.resourceManager = new GeoStore.Resource({
            authorization: this.auth,
            url: this.geoStoreRestURL + 'resources'
        });

        // create the Data Store, depending on mode (active -> GeoBatch, archived -> GeoStore)
        if (this.mode === 'active') {
            this.store = this.createGeoBatchConsumersStore();
        } else if (this.mode === 'archived') {
            this.store = this.createGeoStoreConsumersStore();
        }

        this.listeners = {
            activate: function() {
                this.autoRefresh();
            },
            scope: this
        };

        var expander;

        if (this.showDetails) {
            expander = this.createDetailsExpander();
        }

        this.plugins = (this.plugins || []).concat(this.showDetails ? [expander] : []);

        if (this.mode === 'active') {
            this.getSelectionModel().on({
                rowselect: this.checkCanArchive,
                rowdeselect: this.checkCanArchive,
                scope: this
            });
        }

        this.tbar = [{
            iconCls: 'refresh_ic',
            xtype: 'button',
            text: this.refreshText,
            scope: this,
            handler: function() {
                this.store.load();
            }
        }, {
            iconCls: 'auto_refresh_ic',
            xtype: 'button',
            hidden: this.mode === 'archived',
            text: this.autoRefreshText,
            pressed: true,
            enableToggle: true,
            scope: this,
            toggleHandler: function(btn, state) {
                this.autoRefreshState = state;
                if (state) {
                    this.autoRefresh();
                }
            }
        }, "->", {
            iconCls: 'archive_ic',
            xtype: 'button',
            ref: '../archive',
            hidden: this.mode === 'archived' || !this.canArchive,
            text: this.archiveText,
            disabled: true,
            scope: this,
            handler: function() {
                Ext.Msg.confirm(
                    this.titleConfirmArchiveMsg,
                    this.confirmArchiveText,
                    function(btn) {
                        if (btn == 'yes') {
                            this.archiveSelected();
                        }
                    },
                    this
                );
            }
        }, {
            iconCls: 'broom_ic',
            xtype: 'button',
            text: this.clearFinishedText,
            hidden: this.mode === 'archived',
            scope: this,
            handler: function() {
                var me = this;
                Ext.Msg.confirm(
                    this.titleConfirmClearMsg,
                    this.confirmClearText,
                    function(btn) {
                        if (btn == 'yes') {
                            me.clearFinished();

                        }
                    });
            }
        }];



        if (this.GWCRestURL) {
            this.bbar = [{
                text: this.GWCButtonLabel,
                iconCls: 'gwc_ic',
                handler: this.showGWCGridWin,
                scope: this
            }]
        }

        this.columns = (this.showDetails ? [expander] : []).concat([{
                id: 'uuid',
                header: "ID",
                width: 220,
                dataIndex: 'uuid',
                sortable: true,
                hidden: true
            }, {
                id: 'status',
                header: this.statusText,
                width: 100,
                dataIndex: 'status',
                sortable: true
            }, {
                id: 'startDate',
                header: this.startDateText,
                width: 180,
                dataIndex: 'startDate',
                sortable: true
            }, {
                id: 'file',
                header: this.fileText,
                dataIndex: 'details',
                width: 180,
                renderer: function(val) {
                    if (val && val.events && val.events.length > 0) {
                        var name = val.events[0];
                        var matches = name.match(/_uuid_(.+)/);
                        if (matches) {
                            return matches[1];
                        }
                        return name;
                    }
                    return '';
                }
            }, {
                id: 'task',
                header: this.taskText,
                dataIndex: 'details',
                width: 180,
                renderer: function(val) {
                    return (val && val.progress && val.progress.length > 0) ? (val.progress[0].task || '') : ''
                }
            }, {
                id: 'progress',
                header: this.progressText,
                dataIndex: 'details',
                width: 180,
                renderer: function(val, metaData, record, rowIndex, colIndex, store) {
                    if (val && val.progress && val.progress.length > 0) {
                        var progress = Math.round((val.progress[0].progress || 0) * 100) / 100;
                        var id = Ext.id();

                        return '<span id="progressbar-' + id + '">' +
                            this.fakeProgress
                            .replace(/\{progress\}/g, progress)
                            .replace(/\{barwidth\}/g, (168.0 / 100.0 * progress))
                            .replace(/\{textwidth\}/g, Math.max((168.0 / 100.0 * progress) - 9, 0)) + '</span>';
                    } else {
                        return '';
                    }

                },
                scope: this
            }, {
                xtype: 'actioncolumn',
                width: 35,
                items: [{
                    iconCls: 'delete_ic',
                    width: 25,
                    tooltip: this.tooltipDelete,
                    handler: this.confirmCleanRow,
                    scope: this,
                    getClass: function(v, meta, rec) {
                        if (rec.get('status') == 'RUNNING') {
                            return 'x-hide-display';
                        }
                        return 'x-grid-center-icon action_column_btn';

                    }
                }]
            }, {
                xtype: 'actioncolumn',
                width: 35,
                tooltip: this.tooltipLog,
                handler: this.checkLog,
                scope: this,
                items: [{
                    iconCls: 'information_ic',
                    width: 25,
                    tooltip: this.tooltipLog,
                    scope: this,
                    getClass: function(v, meta, rec) {
                        return 'x-grid-center-icon action_column_btn';
                    }
                }]
            }, {
                xtype: 'actioncolumn',
                width: 35,
                tooltip: this.tooltipDownload,
                handler: this.downloadFile,
                scope: this,
                items: [{
                    iconCls: 'inbox-download_ic',
                    width: 25,
                    tooltip: this.tooltipDownload,
                    getClass: function(v, meta, rec) {
                        return 'x-grid-center-icon action_column_btn';
                    }
                }]
            }]),
            mxp.widgets.GeoBatchConsumerGrid.superclass.initComponent.call(this, arguments);

    },
    /**
     * Call back to start the file download
     * BasicAuth will be used for authentication
     */
    downloadFile: function(grid, rowIndex, colIndex, item , e) {
        
        var runningBtnRef = e.target;
        if(runningBtnRef.classList.contains('ic_loading')){
            // Prevent multiple downloads of the same file
            return;
        }
        
        var record = grid.getStore().getAt(rowIndex);
        var name = record.get('details').events[0];
        //window.open(this.adminUrl + "/mvc/vibi/download?file=" + name);
        var fileDownloadURL = grid.adminUrl + "/mvc/vibi/download?file=" + name;

        var xhr = new XMLHttpRequest();
        // open() accepts username and password parameters but it never seemed to work.
        xhr.open('GET', fileDownloadURL, true);
        // Manually set the authorization header, seems to work.
        xhr.setRequestHeader("Authorization", grid.auth);
        xhr.responseType = 'blob';
        xhr.onload = function (e) {
            runningBtnRef.classList.remove('ic_loading');
            if(xhr.status == 200){
                saveAs(xhr.response, name);
            }else{
                // Notify the user something went wrong
                Ext.Msg.show({
                       msg: grid.errorDownloadingFile + '<br>Status: '+ xhr.status,
                       buttons: Ext.Msg.OK,
                       icon: Ext.MessageBox.ERROR
                    });
            }
        }
        //xhr.onprogress = function(e){if (e.lengthComputable) {console.log(e.loaded);}};
        runningBtnRef.classList.add('ic_loading');
        xhr.send();
    }
});

Ext.reg(mxp.widgets.VibiGeoBatchConsumerGrid.prototype.xtype, mxp.widgets.VibiGeoBatchConsumerGrid);

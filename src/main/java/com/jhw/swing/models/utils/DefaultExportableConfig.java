/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.utils;

import com.clean.core.domain.DomainObject;
import com.jhw.utils.export.excel.ExcelListWriter;
import com.jhw.utils.export.excel.ExportableConfigExcel;
import com.jhw.utils.export.utils.ExportableConfig;
import com.jhw.utils.file.PersonalizationFiles;
import com.jhw.module.util.personalization.services.PersonalizationHandler;
import com.jhw.swing.models.detail._MaterialPanelDetailDragDrop;
import com.jhw.utils.export.json.ExportableConfigJSON;
import com.jhw.utils.export.json.JSONListWriter;
import java.io.File;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DefaultExportableConfig<T extends DomainObject> implements ExportableConfig<T>, ExportableConfigExcel<T>, ExportableConfigJSON<T> {

    private final _MaterialPanelDetailDragDrop detail;

    public DefaultExportableConfig(_MaterialPanelDetailDragDrop detail) {
        this.detail = detail;
    }

    @Override
    public List<T> getValuesList() {
        return detail.getList();
    }

    @Override
    public Object[] getRowObjectExport(T t) {
        return detail.getRowObject(t);
    }

    @Override
    public String[] getColumnNamesExport() {
        return detail.getColumnNames();
    }

    @Override
    public File getFolder() {
        return new File(PersonalizationHandler.getString(PersonalizationFiles.KEY_TEMP_FOLDER));
    }

    @Override
    public String getFileName() {
        return detail.getHeaderText();
    }

    //-----------------<EXCEL>-----------------
    @Override
    public ExcelListWriter.builder exportExcelBuilder() {
        return ExcelListWriter.builder().config(this);
    }

    @Override
    public void personalizeBuilder(ExcelListWriter.builder bldr) {
    }
    //-----------------</EXCEL>-----------------

    //-----------------<JSON>-----------------
    @Override
    public JSONListWriter.builder exportJSONBuilder() {
        return new JSONListWriter.builder().config(this);
    }
    //-----------------</JSON>-----------------
}

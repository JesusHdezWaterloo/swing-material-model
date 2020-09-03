/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.detail;

import com.clean.core.app.services.ExceptionHandler;
import com.clean.core.domain.DomainObject;
import com.jhw.excel.utils.DomainListFileReader;
import com.jhw.excel.utils.ExcelListWriter;
import com.jhw.swing.material.components.filechooser.FileDropHandler;
import com.jhw.swing.material.components.table.Column;
import com.jhw.swing.material.standards.MaterialIcons;
import static com.jhw.utils.others.SDF.SDF_ALL;
import com.jhw.utils.services.ConverterService;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 * @param <T>
 */
public abstract class _MaterialPanelDetailDragDrop<T extends DomainObject> extends _MaterialPanelDetail<T> {

    private final DomainListFileReader<T> reader;

    public _MaterialPanelDetailDragDrop(DomainListFileReader<T> reader) {
        this(reader, new Column[]{});
    }

    public _MaterialPanelDetailDragDrop(DomainListFileReader<T> reader, Column... arr) {
        super(arr);
        this.reader = reader;
        personalize();
    }

    public void setButtonAddTransferConsumer(DomainListFileReader<T> reader) {
        header.getButtonAdd().setTransferHandler(new FileDropHandler((List<File> t) -> {
            try {
                insertAll(reader.read(t));
            } catch (Exception e) {
                ExceptionHandler.handleException(e);
            }
        }));
    }

    public void insertAll(List<T> newDomains) {
        for (T newDomain : newDomains) {
        }
    }

    public Object[] getRowObjectExport(T object) {
        return getRowObject(object);
    }

    public String[] getColumnNamesExport() {
        return getColumnNames();
    }

    private void personalize() {
        this.addOptionElement(new AbstractAction("Exportar a Excel", MaterialIcons.EXCEL.deriveIcon(24f)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExportToExcelAction();
            }
        });

        setButtonAddTransferConsumer(reader);
    }

    public ExcelListWriter.builder exportBuilder() {
        return ExcelListWriter.builder();
    }

    private void onExportToExcelAction() {
        try {
            exportBuilder().fileName(header.getHeaderText() + " " + SDF_ALL.format(new Date()))
                    .setColumns(this::getColumnNamesExport)
                    .values(ConverterService.convert(list, this::getRowObjectExport))
                    .write().open();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
}

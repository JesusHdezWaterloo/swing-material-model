package com.jhw.swing.models.example;

import com.root101.clean.core.app.services.NotificationHandler;
import com.root101.clean.core.app.services.NotificationsGeneralType;
import com.root101.swing.material.components.container.MaterialContainersFactory;
import com.root101.swing.material.components.container.layout.VerticalLayoutComponent;
import com.root101.swing.material.components.container.layout.VerticalLayoutContainer;
import com.root101.swing.material.components.datepicker.MaterialDatePickerIcon;
import com.root101.swing.material.components.datepicker.MaterialDatePickersFactory;
import com.root101.swing.material.components.filechooser.MaterialFileChooser;
import com.root101.swing.material.components.filechooser.MaterialFileChoosersFactory;
import com.root101.swing.material.components.textarea.MaterialTextArea;
import com.root101.swing.material.components.textfield.MaterialFormatedTextFieldIcon;
import com.root101.swing.material.components.textfield.MaterialTextFactory;
import com.root101.swing.material.components.textfield.MaterialTextFieldIcon;
import com.root101.swing.material.standards.MaterialIcons;
import com.jhw.swing.models.input.panels.ModelPanel;
import com.jhw.swing.models.input.popup_selection.InputPopupSelection;
import com.jhw.swing.prepared.textarea.MaterialPreparedTextAreaFactory;
import com.jhw.swing.prepared.textfield.MaterialPreparedTextFactory;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com) 5/4/2020
 */
public class CargoInputView extends ModelPanel<CargoModel> {

    public CargoInputView(CargoModel model) {
        super(model);
        initComponents();
        personalize();
        update();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setHeader("Cargo");
        textFieldNombre = MaterialTextFactory.buildIcon();
        textAreaDescripcion = MaterialPreparedTextAreaFactory.buildDescripcion();
        fileChooserPanel = MaterialFileChoosersFactory.buildIcon();
        cargoICBS1 = new com.jhw.swing.models.example.CargoICBS();

        textFieldNombre.setHint("Nombre del cargo");
        textFieldNombre.setLabel("Cargo *");
        textFieldNombre.setIcon(MaterialIcons.EDIT);

        textFieldMoney = MaterialPreparedTextFactory.buildFormatedMoneyIcon();
        textFieldMoney.setLabel("money");
        textFieldMoney.setHint("extra hint");

        datePicker = MaterialDatePickersFactory.buildIcon();

        popupIconICBS = new CargoICBSPopup();
        popupIconICBS.update();
        VerticalLayoutContainer.builder v = VerticalLayoutContainer.builder();

        //HorizontalLayoutContainer.builder hlc = HorizontalLayoutContainer.builder((int) cargoICBS1.getPreferredSize().getHeight());
        //hlc.add(HorizontalLayoutComponent.builder(textFieldNombre).build());
        //hlc.add(HorizontalLayoutComponent.builder(cargoICBS1).gapLeft(10).build());
        //v.add(hlc.build());
        v.add(textFieldMoney);
        v.add(cargoICBS1);
        v.add(popupIconICBS);
        v.add(fileChooserPanel);
        v.add(datePicker);

        v.add(MaterialContainersFactory.buildPanelTransparent());
        v.add(textFieldNombre);
        v.add(VerticalLayoutComponent.builder(textAreaDescripcion).resize(true).build());

        JButton b = new JButton("bu");
        b.addActionListener((ActionEvent e) -> {
            cargoICBS1.setObject(CargoModel.getCargos().get(3));
        });
        v.add(b);
        
        this.setComponent(v.build());
    }

    // Variables declaration - do not modify
    private InputPopupSelection popupIconICBS;
    private com.jhw.swing.models.example.CargoICBS cargoICBS1;
    private MaterialDatePickerIcon datePicker;
    private MaterialFormatedTextFieldIcon textFieldMoney;
    private MaterialFileChooser fileChooserPanel;
    private MaterialTextArea textAreaDescripcion;
    private MaterialTextFieldIcon<String> textFieldNombre;
    // End of variables declaration                   

    @Override
    public void update() {
        cargoICBS1.update();
        popupIconICBS.update();
    }

    private void personalize() {
        if (model == null) {
            setHeader("Nuevo Cargo");
        } else {
            setHeader("Editar Cargo");

            textFieldNombre.setObject(getOldModel().getNombreCargo());
            textAreaDescripcion.setObject(getOldModel().getDescripcion());
        }
    }

    @Override
    public CargoModel getNewModel() {
        String nombre = textFieldNombre.getObject();
        String desc = textAreaDescripcion.getObject();
        JOptionPane.showMessageDialog(null, popupIconICBS.getObject());

        CargoModel cargo;
        if (getOldModel() != null) {
            cargo = getOldModel();
            cargo.setNombreCargo(nombre);
            cargo.setDescripcion(desc);
        } else {
            cargo = new CargoModel(nombre, desc);
        }
        cargo.setIdCargo(new Random().nextInt(10000) + 100);
        return cargo;
    }

    @Override
    public CargoModel onDeleteAction() {
        try {
            CargoModel cargo = getOldModel();
            if (cargo != null && cargo.getIdCargo() != null) {
                CargoModel.deleteCargo(cargo);
                return cargo;
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return null;
    }

    @Override
    public CargoModel onCreateAction() {
        try {
            CargoModel cargo = getNewModel();
            if (getOldModel() == null) {
                CargoModel.addCargo(cargo);
                return cargo;
            } else {
                CargoModel.editCargo(cargo);
                return cargo;
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return null;
    }

    @Override
    public CargoModel onPostCreateAction(CargoModel obj) {
        NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "Post Create ok");
        return super.onPostCreateAction(obj);
    }

    @Override
    public CargoModel onPostDeleteAction(CargoModel obj) {
        NotificationHandler.showNotification(NotificationsGeneralType.NOTIFICATION_INFO, "Post delete ok");
        return super.onPostDeleteAction(obj);
    }

    @Override
    public boolean onCancelAction() {
        return true;
    }

    private void onButtonOpenFolderActionPerformed() {
        try {
            //hacer esto en metodo aparte general 
            File f = new File("");
            f.mkdir();
            Desktop.getDesktop().open(f);
        } catch (Exception ex) {
        }
    }
}

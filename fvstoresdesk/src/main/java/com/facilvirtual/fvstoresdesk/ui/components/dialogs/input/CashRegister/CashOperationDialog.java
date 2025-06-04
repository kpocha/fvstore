package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class CashOperationDialog extends AbstractFVDialog {
    protected static Logger logger = LoggerFactory.getLogger("CashOperationDialog");
    private Employee cashier;
    private WorkstationConfig workstationConfig;
    private CashOperation.Type operationType;
    private boolean ok = false;

    public CashOperationDialog(Shell parent, int style) {
        super(parent);
        setShellStyle(style);
    }

    public void setCashier(Employee cashier) {
        this.cashier = cashier;
    }

    public void setWorkstationConfig(WorkstationConfig workstationConfig) {
        this.workstationConfig = workstationConfig;
    }

    public void setOperationType(CashOperation.Type operationType) {
        this.operationType = operationType;
    }

    public boolean isOk() {
        return ok;
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(SWTResourceManager.getColor(240, 240, 240));
        container.setLayout(new GridLayout(2, false));

        Label lblTitle = new Label(container, SWT.NONE);
        lblTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        lblTitle.setBackground(container.getBackground());
        lblTitle.setText(operationType == CashOperation.Type.OPEN ? "Abrir Caja" : "Cerrar Caja");

        Label lblAmount = new Label(container, SWT.NONE);
        lblAmount.setBackground(container.getBackground());
        lblAmount.setText("Monto:");

        Text txtAmount = new Text(container, SWT.BORDER);
        txtAmount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Label lblDescription = new Label(container, SWT.NONE);
        lblDescription.setBackground(container.getBackground());
        lblDescription.setText("Descripción:");

        Text txtDescription = new Text(container, SWT.BORDER);
        txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button btnOk = new Button(container, SWT.PUSH);
        btnOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        btnOk.setText("Aceptar");
        btnOk.addListener(SWT.Selection, e -> {
            try {
                double amount = Double.parseDouble(txtAmount.getText());
                String description = txtDescription.getText();

                CashOperation operation = new CashOperation();
                operation.setOperationType(operationType);
                operation.setAmount(amount);
                operation.setDescription(description);
                operation.setCashierName(cashier.getFullName());
                operation.setCashNumber(workstationConfig.getCashNumber());
                operation.setDate(new Date());

                // TODO: Save operation to database

                ok = true;
                close();
            } catch (NumberFormatException ex) {
                logger.error("Error al procesar el monto", ex);
                MessageDialog.openError(getShell(), "Error", "El monto debe ser un número válido");
            } catch (Exception ex) {
                logger.error("Error al procesar la operación", ex);
                MessageDialog.openError(getShell(), "Error", "Error al procesar la operación: " + ex.getMessage());
            }
        });

        Button btnCancel = new Button(container, SWT.PUSH);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        btnCancel.setText("Cancelar");
        btnCancel.addListener(SWT.Selection, e -> close());

        return container;
    }
} 
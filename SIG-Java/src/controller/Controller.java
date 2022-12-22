package controller;

import models.Invoice;
import models.InvoiceItem;
import models.InvoicesTableModel;
import models.ItemsTableModel;
import view.Layout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Controller  implements ActionListener, ListSelectionListener {

     Layout layout ;

    //public DefaultTableModel csv_data =new DefaultTableModel();

    public Controller(Layout layout)
    {
        this.layout=layout;


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "loadFile":
                loadFile();
                break;
            case "saveFile":
                saveFile();
                break;
            case "createInvoice":
                createNewInvoice();
                break;
            case "deleteInvoice":
                deleteInvoice();
                break;
            case "save":
                save();
                break;
            case "cancel":
                cancel();
                break;

        }

    }
    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (layout.getInvoicesTable().getSelectedRow() != -1) {
            Invoice currentInvoice = layout.getInvoices().get(layout.getInvoicesTable().getSelectedRow());
            layout.getInvoiceNumberLbl2().setText(String.valueOf(currentInvoice.getInvoiceNumber()));
            layout.getInvoiceDateTf().setText(currentInvoice.getInvoiceDate());
            layout.getCustomerNameTf().setText(currentInvoice.getCustomerName());
            layout.getInvoiceTotalLbl2().setText(String.valueOf(currentInvoice.getInvoiceTotal()));
            ItemsTableModel itemsTableModel = new ItemsTableModel(currentInvoice.getItems());
            layout.getInvoiceItems().setModel(itemsTableModel);

        }

    }


    public void loadFile()
    {
        ArrayList<Invoice> allInvoices =new ArrayList<>();
        JFileChooser fc = new JFileChooser();

        try{


            if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {


                File invoicesFile = fc.getSelectedFile();
                Path invoicespath = Paths.get(invoicesFile.getAbsolutePath());


                ArrayList<String> invoices= (ArrayList<String>) Files.readAllLines(invoicespath);
                for(int i=0;i<invoices.size();i++)
                {

                    String[] invoiceData =invoices.get(i).split(",");
                    Invoice invoiceSelected = new Invoice();
                    invoiceSelected.setInvoiceNumber(Integer.parseInt(invoiceData[0]));
                    invoiceSelected.setInvoiceDate(invoiceData[1]);
                    invoiceSelected.setCustomerName(invoiceData[2]);
                    allInvoices.add(invoiceSelected);

                }
                JFileChooser fc2 =new JFileChooser();
                if (fc2.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
                    File itemFile = fc2.getSelectedFile();
                    Path itemPath = Paths.get(itemFile.getAbsolutePath());

                    ArrayList<String> items=(ArrayList<String>) Files.readAllLines(itemPath);
                    for(int i=0;i<items.size();i++)
                    {


                            String[] itemData = items.get(i).split(",");



                            InvoiceItem item = new InvoiceItem();
                            item.setItemNo(Integer.parseInt(itemData[0]));
                            item.setItemName(itemData[1]);
                            item.setItemPrice(Double.parseDouble(itemData[2]));
                            item.setItemCount(Integer.parseInt(itemData[3]));


                        for (Invoice invoice : allInvoices) {
                            if (invoice.getInvoiceNumber() == Integer.parseInt(itemData[0])) {
                                item.setInvoice(invoice);
                                invoice.getItems().add(item);
                                break;
                            }
                        }




                    }
                }

                layout.setInvoices(allInvoices);
                InvoicesTableModel invoicesTableModel = new InvoicesTableModel(allInvoices);
                layout.setInvoicesTableModel(invoicesTableModel);
                layout.getInvoicesTable().setModel(invoicesTableModel);
                layout.getInvoicesTableModel().fireTableDataChanged();

                }



        }catch(Exception e)
        {
            e.printStackTrace();
        }




    }
    public void saveFile()
    {


    }
    public void createNewInvoice()
    {



    }
    public void deleteInvoice()
    {




    }
    public void save()
    {




    }
    public void cancel()
    {




    }




}

package com.secmatters.demo.challenge.gui.service;

import com.google.common.collect.Lists;
import com.secmatters.demo.challenge.backend.entity.ICustomer;
import com.secmatters.demo.challenge.backend.entity.beans.Customer;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class CustomerViewPage extends DataViewPage<ICustomer>{

    protected class OwnPanel extends GenericPanel<ICustomer> {

        public OwnPanel(String id, IModel<ICustomer> model) {
            super(id, new CompoundPropertyModel<>(model));
            add(new TextField<>("name").add(StringValidator.maximumLength(30)));
            add(new TextField<>("addressline1").add(StringValidator.maximumLength(30)));
            add(new TextField<>("city").add(StringValidator.maximumLength(25)));
            add(new TextField<>("state").add(StringValidator.maximumLength(2)));
            add(new TextField<>("phone").add(StringValidator.maximumLength(12)));
            add(new TextField<String>("email").add(StringValidator.maximumLength(40)).add(EmailAddressValidator.getInstance()));
            add(new TextField<>("creditLimit").setType(Integer.class));
            add(new DropDownChoice<>("zip", dao.getMicroMarkets(), new ChoiceRenderer<>("zipCode")).setRequired(true));
            add(new DropDownChoice<>("discountCode", dao.getDiscountCode(), new ChoiceRenderer<>("discountCode")).setRequired(true));
        }
    }

    @Override
    protected Iterator<ICustomer> newItemIterator() {
        if (dataProvider.getSort() == null) {
            return dao.getCustomers().iterator();
        } else {
            return dao.getCustomers(dataProvider.getSort().getProperty(), dataProvider.getSort().isAscending()).iterator();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<IColumn<ICustomer, String>> newColumns() {
        return Lists.newArrayList(
            new PropertyColumn<ICustomer, String>(Model.of("Customer ID"), "customerId", "customerId") {
                @Override
                public String getCssClass() {
                    return "numeric";
                }
            },
            new PropertyColumn<ICustomer, String>(Model.of("Name"), "name", "name") {
                @Override
                public void populateItem(Item<ICellPopulator<ICustomer>> item, String componentId, IModel<ICustomer> rowModel) {
                    item.add(newEditFragment(componentId, getDataModel(rowModel), rowModel));
                }
            },
            new PropertyColumn<>(Model.of("Address"), "addressline1"),
            new PropertyColumn<>(Model.of("City"), "city", "city"),
            new PropertyColumn<>(Model.of("Zip code"), "zip.zipCode", "zip.zipCode")
        );
    }

    @Override
    protected String newTitle() {
        return "Customer view";
    }

    @Override
    protected void removeItem(ICustomer item) {
        dao.remove(item);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected GenericPanel<ICustomer> newFieldsPanel(String id, IModel<ICustomer> model) {
        return new OwnPanel(id, model);
    }

    @Override
    protected ICustomer newItem() {
        return new Customer();
    }

    @Override
    protected ICustomer cloneItem(ICustomer item) {
        return new Customer((Customer)item);
    }

    @Override
    protected ICustomer addItem(ICustomer item) {
        return dao.addCustomer(item.getName(), item.getDiscountCode().getDiscountCode(), item.getZip().getZipCode());
    }

    @Override
    protected void editItem(ICustomer item) {
        dao.merge(item);
    }
}

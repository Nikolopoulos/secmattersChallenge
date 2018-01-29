package com.secmatters.demo.challenge.gui.service;

import com.google.common.collect.Iterators;
import com.secmatters.demo.challenge.backend.service.IChallengeDAO;
import com.secmatters.demo.challenge.gui.ChallengeApplication;
import com.secmatters.demo.challenge.gui.auth.AuthenticatedWebPage;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import java.io.Serializable;
import java.util.*;
import javax.persistence.EntityTransaction;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class DataViewPage<T extends Serializable> extends AuthenticatedWebPage {

    public enum State {
        EDIT,
        ADD
    }

    protected IChallengeDAO dao = ChallengeApplication.get().getDao();
    protected final Set<T> selected = new HashSet<>();
    protected final ViewDataProvider dataProvider = new ViewDataProvider();
    protected final AjaxLink removeLink;
    protected final DataTable dataTable;
    protected final EditModal editModal;

    protected class Selector extends Fragment {

        public Selector(String id, IModel<Boolean> model) {
            super(id, "selector", DataViewPage.this, model);
            add(new AjaxCheckBox("checkbox", model) {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    Selector.this.onUpdate(target);
                }
            });
        }

        protected void onUpdate(AjaxRequestTarget target) {
        }
    }

    protected class LinkFragment extends Fragment {

        public LinkFragment(String id, IModel<?> model) {
            super(id, "headerLink", DataViewPage.this, model);
            add(new AjaxLink("link") {
                @Override
                protected void onInitialize() {
                    super.onInitialize();
                    setBody(model);
                }

                @Override
                public void onClick(AjaxRequestTarget target) {
                    LinkFragment.this.onClick(target);
                }
            });
        }

        protected void onClick(AjaxRequestTarget target) {
        }
    }

    protected class EditModal extends Modal<T> {

        protected Form<T> form;
        private State state;
        private FeedbackPanel feedbackPanel;
        private final AjaxButton submitButton;

        public EditModal(String id) {
            super(id, new Model<>(null));
            setUseCloseHandler(true);
            add(feedbackPanel = new FeedbackPanel("feedbackMessage"));
            feedbackPanel.setOutputMarkupPlaceholderTag(true);
            add(form = new Form<>("form", getModel()));
            form.add(newFieldsPanel("fieldsPanel", getModel()));
            show(false);
            setCloseOnEscapeKey(true);
            addButton(submitButton = new AjaxButton(BUTTON_MARKUP_ID, Model.of("Submit"), form) {
                @Override
                @SuppressWarnings("unchecked")
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    onFormSubmit(target, state, (T)form.getModelObject());
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    super.onError(target, form);
                    target.add(feedbackPanel);
                }
            });
            form.setDefaultButton(submitButton);
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        @Override
        protected void onClose(AjaxRequestTarget target) {
            super.onClose(target);
            setModelObject(null);
        }
    }

    protected class ViewDataProvider extends SortableDataProvider<T, String> {

        @Override
        public Iterator<? extends T> iterator(long first, long count) {
            Iterator<T> it = newItemIterator();
            Iterators.advance(it, (int) first);
            return Iterators.limit(it, (int) count);
        }

        @Override
        public long size() {
            return Iterators.size(newItemIterator());
        }

        @Override
        public IModel<T> model(T object) {
            return new Model<>(object);
        }

    }

    public DataViewPage() {
        this(true);
    }

    public DataViewPage(boolean withSelector) {
        List<IColumn<T, String>> columns = new ArrayList<>();
        if (withSelector) {
            columns.add(new AbstractColumn<T, String>(Model.of("Selected")) {
                Component headerLink;

                @Override
                public Component getHeader(String componentId) {
                    return headerLink = new LinkFragment(
                            componentId,
                            new AbstractReadOnlyModel<String>() {
                                @Override
                                public String getObject() {
                                    return selected.isEmpty()
                                            ? "Select all"
                                            : "Select none";
                                }
                            }
                    ) {
                        @Override
                        protected void onInitialize() {
                            super.onInitialize();
                            setOutputMarkupId(true);
                        }

                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            if (selected.isEmpty()) {
                                Iterators.addAll(selected, newItemIterator());
                            } else {
                                selected.clear();
                            }
                            DataTable parent = findParent(DataTable.class);
                            if (parent != null) {
                                target.add(parent);
                            }
                            target.add(removeLink);
                        }
                    };
                }

                @Override
                public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
                    cellItem.add(new Selector(
                            componentId,
                            new Model<Boolean>() {
                        @Override
                        public Boolean getObject() {
                            return selected.contains(rowModel.getObject());
                        }

                        @Override
                        public void setObject(Boolean object) {
                            if (object) {
                                selected.add(rowModel.getObject());
                            } else {
                                selected.remove(rowModel.getObject());
                            }
                        }
                    }
                    ) {
                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            target.add(headerLink);
                            target.add(removeLink);
                        }
                    });
                }
            });
        }
        columns.addAll(newColumns());
        dataTable = new DefaultDataTable<>("dataTable", columns, dataProvider, 10);
        add(dataTable.setOutputMarkupId(true));
        add(new Label("title", newTitle()));
        add(removeLink = new AjaxLink<Void>("removeLink") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                setOutputMarkupPlaceholderTag(true);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(!selected.isEmpty());
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                EntityTransaction trx = dao.getTransaction();
                try {
                    trx.begin();
                    selected.forEach((item) -> {
                        removeItem(item);
                    });
                    selected.clear();
                    trx.commit();
                } catch (Exception ex) {
                    if (trx != null && trx.isActive()) {
                        trx.rollback();
                    }
                    throw ex;
                }
                target.add(removeLink);
                target.add(dataTable);
                target.add(this);
            }
        });
        add(editModal = new EditModal("editModal"));
        add(new AjaxLink<Void>("addLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                editModal.setModelObject(newItem());
                editModal.setState(State.ADD);
                editModal.show(target);
                target.add(editModal);
            }
        });
    }

    protected abstract Iterator<T> newItemIterator();

    protected abstract List<IColumn<T, String>> newColumns();

    protected abstract String newTitle();

    protected abstract void removeItem(T item);

    protected abstract GenericPanel<T> newFieldsPanel(String id, IModel<T> model);

    protected abstract T newItem();

    protected abstract T cloneItem(T item);

    protected abstract T addItem(T item);

    protected abstract void editItem(T item);

    protected void onFormSubmit(AjaxRequestTarget target, State state, T item) {
        EntityTransaction trx = dao.getTransaction();
        try {
            trx.begin();
            if (state == State.ADD) {
                addItem(item);
            } else {
                editItem(item);
            }
            trx.commit();
        } catch (Exception ex) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            throw ex;
        }
        target.add(dataTable);
        target.add(removeLink);
        editModal.close(target);
    };

    protected LinkFragment newEditFragment(String id, IModel<?> labelModel, IModel<T> model) {
        return new LinkFragment(id, labelModel) {
            @Override
            protected void onClick(AjaxRequestTarget target) {
                selected.remove(model.getObject());
                editModal.setModelObject(cloneItem(model.getObject()));
                editModal.setState(State.EDIT);
                editModal.show(target);
                target.add(editModal);
            }
        };
    }

}

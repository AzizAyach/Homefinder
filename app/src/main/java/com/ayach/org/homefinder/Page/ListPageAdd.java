package com.ayach.org.homefinder.Page;

import android.content.Context;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.PageList;

/**
 * Created by aziz on 31/12/2015.
 */
public class ListPageAdd extends AbstractWizardModel{

    public ListPageAdd(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList( new PageName(this,"Name of Logement").setRequired(true),
                new PageLocation(this,"Location").setRequired(true),
                new PageAttr(this,"Attribut").setRequired(true),
                new PagePrice(this,"Price").setRequired(true),
        new PagePhoto(this,"Photo").setRequired(true),
        new PageMap(this,"Locations").setRequired(true));
    }
}

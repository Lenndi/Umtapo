package org.lendi.umtapo.solr.repository;

import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.PartialUpdate;

public class SolrBorrowerRepositoryImpl implements SolrBorrowerRepositoryCustom {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void update(BorrowerDocument borrowerDocument) {
        PartialUpdate update = new PartialUpdate("id", borrowerDocument.getId());
        update.setValueOfField("name", borrowerDocument.getName());
        update.setValueOfField("comment", borrowerDocument.getComment());
        update.setValueOfField("birthday", borrowerDocument.getBirthday());
        update.setValueOfField("quota", borrowerDocument.getQuota());
        update.setValueOfField("emailOptin", borrowerDocument.getEmailOptin());
        update.setValueOfField("addressId", borrowerDocument.getAddressId());
        update.setValueOfField("address1", borrowerDocument.getAddress1());
        update.setValueOfField("address2", borrowerDocument.getAddress2());
        update.setValueOfField("zip", borrowerDocument.getZip());
        update.setValueOfField("city", borrowerDocument.getCity());
        update.setValueOfField("phone", borrowerDocument.getPhone());
        update.setValueOfField("email", borrowerDocument.getEmail());

        this.solrTemplate.saveBean(update);
        this.solrTemplate.commit();
    }
}

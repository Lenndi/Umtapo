package org.lendi.umtapo.entity.record.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified record, inspired by Dublin Core format.
 */
public class SimpleRecord {
    private Title title;
    private Creator creator;
    private Subject subject;
    private Description description;
    private Publisher publisher;
    private List<Creator> contributors;
    private SimpleRecordDate date;
    private Type type;
    private Identifier identifier;
    private Source source;
    private Language language;
    private Coverage coverage;
    private Right right;

    public SimpleRecord() {
        this.contributors = new ArrayList<>();
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Creator> getContributors() {
        return contributors;
    }

    public void setContributors(List<Creator> contributors) {
        this.contributors = contributors;
    }

    public void addContributor(Creator contributor) {
        this.contributors.add(contributor);
    }

    public SimpleRecordDate getDate() {
        return date;
    }

    public void setDate(SimpleRecordDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }
}

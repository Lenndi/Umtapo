package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * Simplified record, inspired by Dublin Core format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class SimpleRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Title title;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Creator creator;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Subject subject;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Description description;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Publisher publisher;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creator> contributors;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private SimpleRecordDate date;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Type type;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Identifier identifier;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Source source;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Language language;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Coverage coverage;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Right right;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Instantiates a new Simple record.
     */
    public SimpleRecord() {
        this.contributors = new ArrayList<>();
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator the creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Gets publisher.
     *
     * @return the publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets publisher.
     *
     * @param publisher the publisher
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets contributors.
     *
     * @return the contributors
     */
    public List<Creator> getContributors() {
        return contributors;
    }

    /**
     * Sets contributors.
     *
     * @param contributors the contributors
     */
    public void setContributors(List<Creator> contributors) {
        this.contributors = contributors;
    }

    /**
     * Add contributor.
     *
     * @param contributor the contributor
     */
    public void addContributor(Creator contributor) {
        this.contributors.add(contributor);
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public SimpleRecordDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(SimpleRecordDate date) {
        this.date = date;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Gets coverage.
     *
     * @return the coverage
     */
    public Coverage getCoverage() {
        return coverage;
    }

    /**
     * Sets coverage.
     *
     * @param coverage the coverage
     */
    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    /**
     * Gets right.
     *
     * @return the right
     */
    public Right getRight() {
        return right;
    }

    /**
     * Sets right.
     *
     * @param right the right
     */
    public void setRight(Right right) {
        this.right = right;
    }
}

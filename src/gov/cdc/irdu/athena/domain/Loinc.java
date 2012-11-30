package gov.cdc.irdu.athena.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Loinc extends DomainObject {
	
	public enum ClassType {
		Laboratory, Clinical, Claims, Surveys;
		// class type values in database are 1 based -- not 0 based
		public static ClassType get(int i) {
			return values()[i-1];
		}
	}

	private static final long serialVersionUID = 173418648485668992L;

	@Column(nullable = false, length = 7)
    private String code;

    @Column(length = 255)
    private String component;

    @Column(length = 30)
    private String property;

    @Column(length = 15)
    private String timeAspect;

    @Column(length = 100)
    private String system;

    @Column(length = 30)
    private String scaleType;

    @Column(length = 50)
    private String methodType;

    @Column(length = 20)
    private String classification;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date lastChangedDate;

    @Column(length = 3)
    private String changeType;

    private String comments;

    @Column(length = 11)
    private String status;

    @Column(length = 7)
    private String mapTo;

    @Column(length = 255)
    private String consumerName;

    private String reference;

    @Column(length = 50)
    private String exactComponentSynonym;

    @Column(length = 13)
    private String molarMass;

    private Integer classType;

    @Column(length = 255)
    private String formula;

    @Column(length = 20)
    private String species;

    private String exampleAnswers;

    @Column(length = 50)
    private String baseName;

    @Column(length = 20)
    private String naaccrId;

    @Column(length = 10)
    private String codeTable;

    private Boolean root;

    private String surveyQuestionText;

    @Column(length = 50)
    private String surveyQuestionSource;

    private Boolean unitsRequired;

    @Column(length = 30)
    private String submittedUnits;

    @Column(length = 40)
    private String shortName;

    @Column(length = 15)
    private String orderObservation;

    private Boolean cdiscCommonTests;

    @Column(length = 50)
    private String hl7FieldSubfieldId;

    private String externalCopyrightNotice;

    @Column(length = 255)
    private String exampleUnits;

    private Double inpcPercentage;

    @Column(length = 255)
    private String longCommonName;

    @Column(length = 255)
    private String hl7V2Datatype;

    @Column(length = 255)
    private String hl7V3Datatype;

    private String curatedRangeAndUnits;

    @Column(length = 255)
    private String documentSection;

    private String definitionDescriptionHelp;

    @Column(length = 255)
    private String exampleUcumUnits;

    @Column(length = 255)
    private String exampleSiUcumUnits;

    @Column(length = 9)
    private String statusReason;

    private String statusText;

    private String specialExplanation;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<RelatedName> relatedNames = new HashSet<RelatedName>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<AcsSynonym> acsSynonyms = new HashSet<AcsSynonym>();

	public String getCode() {
        return this.code;
    }

	public void setCode(String code) {
        this.code = code;
    }

	public String getComponent() {
        return this.component;
    }

	public void setComponent(String component) {
        this.component = component;
    }

	public String getProperty() {
        return this.property;
    }

	public void setProperty(String property) {
        this.property = property;
    }

	public String getTimeAspect() {
        return this.timeAspect;
    }

	public void setTimeAspect(String timeAspect) {
        this.timeAspect = timeAspect;
    }

	public String getSystem() {
        return this.system;
    }

	public void setSystem(String system) {
        this.system = system;
    }

	public String getScaleType() {
        return this.scaleType;
    }

	public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

	public String getMethodType() {
        return this.methodType;
    }

	public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

	public String getClassification() {
        return this.classification;
    }

	public void setClassification(String classification) {
        this.classification = classification;
    }

	public Date getLastChangedDate() {
        return this.lastChangedDate;
    }

	public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

	public String getChangeType() {
        return this.changeType;
    }

	public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

	public String getComments() {
        return this.comments;
    }

	public void setComments(String comments) {
        this.comments = comments;
    }

	public String getStatus() {
        return this.status;
    }

	public void setStatus(String status) {
        this.status = status;
    }

	public String getMapTo() {
        return this.mapTo;
    }

	public void setMapTo(String mapTo) {
        this.mapTo = mapTo;
    }

	public String getConsumerName() {
        return this.consumerName;
    }

	public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

	public String getReference() {
        return this.reference;
    }

	public void setReference(String reference) {
        this.reference = reference;
    }

	public String getExactComponentSynonym() {
        return this.exactComponentSynonym;
    }

	public void setExactComponentSynonym(String exactComponentSynonym) {
        this.exactComponentSynonym = exactComponentSynonym;
    }

	public String getMolarMass() {
        return this.molarMass;
    }

	public void setMolarMass(String molarMass) {
        this.molarMass = molarMass;
    }

	public Integer getClassType() {
        return this.classType;
    }

	public void setClassType(Integer classType) {
        this.classType = classType;
    }

	public String getFormula() {
        return this.formula;
    }

	public void setFormula(String formula) {
        this.formula = formula;
    }

	public String getSpecies() {
        return this.species;
    }

	public void setSpecies(String species) {
        this.species = species;
    }

	public String getExampleAnswers() {
        return this.exampleAnswers;
    }

	public void setExampleAnswers(String exampleAnswers) {
        this.exampleAnswers = exampleAnswers;
    }

	public Set<AcsSynonym> getAcsSynonyms() {
        return this.acsSynonyms;
    }

	public void setAcsSynonyms(Set<AcsSynonym> acsSynonyms) {
        this.acsSynonyms = acsSynonyms;
    }

	public String getBaseName() {
        return this.baseName;
    }

	public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

	public String getNaaccrId() {
        return this.naaccrId;
    }

	public void setNaaccrId(String naaccrId) {
        this.naaccrId = naaccrId;
    }

	public String getCodeTable() {
        return this.codeTable;
    }

	public void setCodeTable(String codeTable) {
        this.codeTable = codeTable;
    }

	public Boolean getRoot() {
        return this.root;
    }

	public void setRoot(Boolean root) {
        this.root = root;
    }

	public String getSurveyQuestionText() {
        return this.surveyQuestionText;
    }

	public void setSurveyQuestionText(String surveyQuestionText) {
        this.surveyQuestionText = surveyQuestionText;
    }

	public String getSurveyQuestionSource() {
        return this.surveyQuestionSource;
    }

	public void setSurveyQuestionSource(String surveyQuestionSource) {
        this.surveyQuestionSource = surveyQuestionSource;
    }

	public Boolean getUnitsRequired() {
        return this.unitsRequired;
    }

	public void setUnitsRequired(Boolean unitsRequired) {
        this.unitsRequired = unitsRequired;
    }

	public String getSubmittedUnits() {
        return this.submittedUnits;
    }

	public void setSubmittedUnits(String submittedUnits) {
        this.submittedUnits = submittedUnits;
    }

	public String getShortName() {
        return this.shortName;
    }

	public void setShortName(String shortName) {
        this.shortName = shortName;
    }

	public String getOrderObservation() {
        return this.orderObservation;
    }

	public void setOrderObservation(String orderObservation) {
        this.orderObservation = orderObservation;
    }

	public Boolean getCdiscCommonTests() {
        return this.cdiscCommonTests;
    }

	public void setCdiscCommonTests(Boolean cdiscCommonTests) {
        this.cdiscCommonTests = cdiscCommonTests;
    }

	public String getHl7FieldSubfieldId() {
        return this.hl7FieldSubfieldId;
    }

	public void setHl7FieldSubfieldId(String hl7FieldSubfieldId) {
        this.hl7FieldSubfieldId = hl7FieldSubfieldId;
    }

	public String getExternalCopyrightNotice() {
        return this.externalCopyrightNotice;
    }

	public void setExternalCopyrightNotice(String externalCopyrightNotice) {
        this.externalCopyrightNotice = externalCopyrightNotice;
    }

	public String getExampleUnits() {
        return this.exampleUnits;
    }

	public void setExampleUnits(String exampleUnits) {
        this.exampleUnits = exampleUnits;
    }

	public Double getInpcPercentage() {
        return this.inpcPercentage;
    }

	public void setInpcPercentage(Double inpcPercentage) {
        this.inpcPercentage = inpcPercentage;
    }

	public String getLongCommonName() {
        return this.longCommonName;
    }

	public void setLongCommonName(String longCommonName) {
        this.longCommonName = longCommonName;
    }

	public String getHl7V2Datatype() {
        return this.hl7V2Datatype;
    }

	public void setHl7V2Datatype(String hl7V2Datatype) {
        this.hl7V2Datatype = hl7V2Datatype;
    }

	public String getHl7V3Datatype() {
        return this.hl7V3Datatype;
    }

	public void setHl7V3Datatype(String hl7V3Datatype) {
        this.hl7V3Datatype = hl7V3Datatype;
    }

	public String getCuratedRangeAndUnits() {
        return this.curatedRangeAndUnits;
    }

	public void setCuratedRangeAndUnits(String curatedRangeAndUnits) {
        this.curatedRangeAndUnits = curatedRangeAndUnits;
    }

	public String getDocumentSection() {
        return this.documentSection;
    }

	public void setDocumentSection(String documentSection) {
        this.documentSection = documentSection;
    }

	public String getDefinitionDescriptionHelp() {
        return this.definitionDescriptionHelp;
    }

	public void setDefinitionDescriptionHelp(String definitionDescriptionHelp) {
        this.definitionDescriptionHelp = definitionDescriptionHelp;
    }

	public String getExampleUcumUnits() {
        return this.exampleUcumUnits;
    }

	public void setExampleUcumUnits(String exmpleUcumUnits) {
        this.exampleUcumUnits = exmpleUcumUnits;
    }

	public String getExampleSiUcumUnits() {
        return this.exampleSiUcumUnits;
    }

	public void setExampleSiUcumUnits(String exmpleSiUcumUnits) {
        this.exampleSiUcumUnits = exmpleSiUcumUnits;
    }

	public String getStatusReason() {
        return this.statusReason;
    }

	public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

	public String getStatusText() {
        return this.statusText;
    }

	public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

	public String getSpecialExplanation() {
        return this.specialExplanation;
    }

	public void setSpecialExplanation(String specialExplanation) {
        this.specialExplanation = specialExplanation;
    }

	public Set<RelatedName> getRelatedNames() {
        return this.relatedNames;
    }

	public void setRelatedNames(Set<RelatedName> relatedNames) {
        this.relatedNames = relatedNames;
    }

}

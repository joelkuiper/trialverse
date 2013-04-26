package org.drugis.trialverse.study;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Data;

import org.drugis.common.hibernate.PostgresEnumConverter;
import org.drugis.trialverse.concept.Concept;
import org.drugis.trialverse.core.EntityLinkResolver;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@TypeDefs({
	@TypeDef(name="measurementType", typeClass=PostgresEnumConverter.class,
			parameters = {@Parameter(name="enumClassName", value="org.drugis.trialverse.study.MeasurementType")}),
	@TypeDef(name="variableType", typeClass=PostgresEnumConverter.class,
		parameters = {@Parameter(name="enumClassName", value="org.drugis.trialverse.study.VariableType")})
})
@Data public class StudyVariable {
	@JsonUnwrapped private @EmbeddedId StudyVariablePK studyVariablePK;
	@Column private Boolean isPrimary;
	@ManyToOne private Concept unitConcept;
	@Column @Type(type="measurementType") private MeasurementType measurementType;
	@Column @Type(type="variableType") private VariableType variableType;

	@Embeddable
	@Data private static class StudyVariablePK implements Serializable {
		private static final long serialVersionUID = -4052311975385835101L;

		@JsonIgnore private Long studyId;
		@ManyToOne private Concept variableConcept;
	}

	public URI getVariableConcept() {
		return EntityLinkResolver.getInstance().getLinkForEntity(studyVariablePK.variableConcept);
	}

	public URI getUnitConcept() {
		return EntityLinkResolver.getInstance().getLinkForEntity(unitConcept);
	}
}

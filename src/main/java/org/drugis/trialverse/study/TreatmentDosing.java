package org.drugis.trialverse.study;

import java.io.Serializable;
import java.net.URI;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;

import org.drugis.trialverse.concept.Concept;
import org.drugis.trialverse.core.EntityLinkResolver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@Data class TreatmentDosing {
	@JsonUnwrapped @EmbeddedId private TreatmentDosingPK treatmentDosingPK;
	@Column private Double minDose;
	@Column private Double maxDose;
	@Column private String scaleModifier;
	@OneToOne private Concept unitConcept;

	@Embeddable
	@Data private static class TreatmentDosingPK implements Serializable {
		private static final long serialVersionUID = 3201662940151856371L;
		@JsonIgnore Long treatmentId;
		Time plannedTime;
	}

	public URI getUnitConcept() {
		return EntityLinkResolver.getInstance().getLinkForEntity(unitConcept);
	}
}
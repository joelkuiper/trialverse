package org.drugis.trialverse.study;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.drugis.trialverse.CachedQueryTemplateFactory.QueryTemplate;
import org.drugis.trialverse.QueryTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class StudyRepositoryImpl implements StudyRepositoryCustom {
	@PersistenceContext private EntityManager d_em;
	@Autowired private QueryTemplateFactory queryTemplateFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<Study> findStudies(
			UUID indication,
			List<UUID> variables,
			List<UUID> treatments) {
		QueryTemplate template = this.queryTemplateFactory.buildQueryTemplate("/studiesQuery.template.sql");
		List<Study> results = d_em.createNativeQuery(
				template.getTemplate(),
				Study.class)
				.setParameter("indication", indication.toString())
				.getResultList();
		return results;
	}
}

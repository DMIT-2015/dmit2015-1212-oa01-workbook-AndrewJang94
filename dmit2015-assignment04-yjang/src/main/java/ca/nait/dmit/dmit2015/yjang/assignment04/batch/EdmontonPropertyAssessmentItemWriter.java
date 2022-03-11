package ca.nait.dmit.dmit2015.yjang.assignment04.batch;

import ca.nait.dmit.dmit2015.yjang.assignment04.entity.EdmontonPropertyAssessment;
import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Named
public class EdmontonPropertyAssessmentItemWriter extends AbstractItemWriter {
    @Inject
    private JobContext _jobContext;

    @PersistenceContext(unitName = "mssql-jpa-pu")
    private EntityManager _entityManager;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (Object currentItem : items) {
            EdmontonPropertyAssessment entity = (EdmontonPropertyAssessment) currentItem;
            _entityManager.persist(entity);
        }

    }
}

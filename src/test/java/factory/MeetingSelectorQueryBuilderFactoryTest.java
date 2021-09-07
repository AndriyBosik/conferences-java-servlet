package factory;

import com.conferences.config.MeetingFilterSelector;
import com.conferences.decorator.MeetingFutureSelectorQueryBuilderDecorator;
import com.conferences.decorator.MeetingPastSelectorQueryBuilderDecorator;
import com.conferences.factory.MeetingSelectorQueryBuilderFactory;
import com.conferences.handler.abstraction.IQueryBuilder;
import com.conferences.handler.implementation.QueryBuilder;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeetingSelectorQueryBuilderFactoryTest {

    @Test
    public void shouldReturnMeetingPastSelectorQueryBuilderDecorator() {
        IQueryBuilder queryBuilder = MeetingSelectorQueryBuilderFactory.getInstance().getDecorator(new QueryBuilder(), MeetingFilterSelector.PAST);
        assertEquals(MeetingPastSelectorQueryBuilderDecorator.class, queryBuilder.getClass());
    }

    @Test
    public void shouldReturnMeetingFutureSelectorQueryBuilderDecorator() {
        IQueryBuilder queryBuilder = MeetingSelectorQueryBuilderFactory.getInstance().getDecorator(new QueryBuilder(), MeetingFilterSelector.FUTURE);
        assertEquals(MeetingFutureSelectorQueryBuilderDecorator.class, queryBuilder.getClass());
    }

    @Test
    public void shouldReturnSameInstance() {
        IQueryBuilder qb = new QueryBuilder();
        IQueryBuilder queryBuilder = MeetingSelectorQueryBuilderFactory.getInstance().getDecorator(qb, MeetingFilterSelector.ALL);
        assertSame(qb, queryBuilder);
    }
}

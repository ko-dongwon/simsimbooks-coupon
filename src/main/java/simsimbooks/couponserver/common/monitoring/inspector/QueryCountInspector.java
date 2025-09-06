package simsimbooks.couponserver.common.monitoring.inspector;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import lombok.extern.slf4j.Slf4j;
import simsimbooks.couponserver.common.monitoring.request.RequestQueryContext;
import simsimbooks.couponserver.common.monitoring.request.RequestQueryContextHolder;
@Slf4j
public class QueryCountInspector implements StatementInspector {
	@Override
	public String inspect(String sql) {
		RequestQueryContext context = RequestQueryContextHolder.getContext();

		if (context != null)  context.incrementQueryCount(sql);

		return sql;
	}
}

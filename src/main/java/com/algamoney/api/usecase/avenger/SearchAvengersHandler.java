package com.algamoney.api.usecase.avenger;

import com.algamoney.api.usecase.avenger.dynamodb.entity.Avenger;
import com.algamoney.api.usecase.avenger.exception.AvengerNotFoundException;
import com.algamoney.api.usecase.avenger.lambda.response.HandlerResponse;
import com.algamoney.api.usecase.dao.AvengerDao;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class SearchAvengersHandler implements RequestHandler<String, HandlerResponse> {
	
	private AvengerDao dao = new AvengerDao();

	@Override
	public HandlerResponse handleRequest(final String id, final Context context) {
		
		context.getLogger().log("[#] - Searching avenger y id:" + id);
		
		final Avenger avengerFound = dao.search(id);

		if(avengerFound == null) {
            throw new AvengerNotFoundException("[NotFound] - Avenger id: " + id);
        }
		
		final HandlerResponse response = HandlerResponse
				.builder()
				.setObjectBody(avengerFound)
				.build();
		
		context.getLogger().log("[#] - Avenger found");
		
		return response;
	}
}

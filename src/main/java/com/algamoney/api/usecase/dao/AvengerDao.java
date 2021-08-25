package com.algamoney.api.usecase.dao;

import com.algamoney.api.usecase.avenger.dynamodb.entity.Avenger;
import com.algamoney.api.usecase.avenger.repository.DynamoDBManager;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class AvengerDao {
	
	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();
    
	/**
     * 
     * @param id
     * @return
     */
	public Avenger search(String id) {
		return mapper.load(Avenger.class, id);
	}

    /**
     * 
     * @param avenger
     * @return
     */
	public Avenger create(Avenger avenger) {
		mapper.save(avenger);
		return avenger;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void remove(String id) {
		mapper.delete(id);
	}
	
	/**
	 * 
	 * @param avenger
	 * @return 
	 */
	public Avenger update(Avenger avenger) {
		return create(avenger);
	}
	
	

}
